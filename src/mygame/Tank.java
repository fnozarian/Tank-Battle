package mygame;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import java.util.ArrayList;
import com.jme3.audio.AudioNode;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.PhysicsCollisionObject;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Node;
import com.jme3.scene.control.CameraControl;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Matrix3f;
import com.jme3.scene.Spatial;

public class Tank {


    private final float accelerationForce = 1000.0f;
    private final float brakeForce = 100.0f;
    private float steeringValue = 0;
    private float accelerationValue = 0;
    private float compValue = 0.2f; //(lower than damp!)
    private float dampValue = 0.3f;
    private final float mass = 400;
    private float stiffness = 120.0f;//200=f1 car
    private ArrayList<Weapon> weapons;
    private Weapon activeWeapon;
    private int health;
    private CollisionShape colShape;
    private AudioNode tankIdleSound;
    private Node tankNode;
    private Spatial tankBody;
    private CameraNode camNode;
    private SimpleApplication app;
    private PhysicsHoverControl vehicleControl;

    public Tank(Application app,Vector3f location,Quaternion direction) {

        this.app = (SimpleApplication) app;
        tankNode = new Node();
        tankNode.setLocalTranslation(location);
        tankNode.setLocalRotation(direction);
        
        //initialize members
        weapons = new ArrayList<Weapon>();
        int health =100;


        //Configuring Model
        tankBody = this.app.getAssetManager().loadModel("Models/HoverTank/Tank2.mesh.xml");
        colShape = CollisionShapeFactory.createDynamicMeshShape(tankBody);
        tankBody.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        tankBody.setLocalTranslation(new Vector3f(0, 0, 0));///-60, 14, -23
        
        tankNode.attachChild(tankBody);

        //Configuring vehicle control
        vehicleControl = new PhysicsHoverControl(colShape, 500);
        vehicleControl.setCollisionGroup(PhysicsCollisionObject.COLLISION_GROUP_02);
        tankNode.addControl(vehicleControl);

        //Configuring tank sound
        tankIdleSound = new AudioNode(this.app.getAssetManager(), "Sounds/propeller-plane-idle.wav", false);
        tankIdleSound.setLooping(true);
        tankNode.attachChild(tankIdleSound);
        tankIdleSound.play();

        
        //add Tank to scene
        this.app.getRootNode().attachChild(tankNode);
        getPhysicsSpace().add(tankNode);

    }
    void setAsPlayer(){
        //Configuring camera
        camNode = new CameraNode("camNode", this.app.getCamera());
        //Setting the direction to Spatial to camera, this means the camera will copy the movements of the Node
        camNode.setControlDir(CameraControl.ControlDirection.SpatialToCamera);
        camNode.setLocalTranslation(0f, 4f, -12f);
        tankNode.attachChild(camNode);
    }

    void accelerate(float force) {
        vehicleControl.accelerate(force);
    }

    void brake(float force) {
    }

    void steer(float value) {
        vehicleControl.steer(value);
    }

    public void fire() {
        Vector3f fireDirection = tankNode.getWorldRotation().getRotationColumn(2);
        fireDirection.setY(-0.024F);
        try {
            activeWeapon.fire(fireDirection);
        } catch (Exception e) {
            System.err.println("no activeWeapon found!");
        }
        
    }

    public void switchWeapon(Weapon weapon) {
        activeWeapon = weapons.get(weapons.indexOf(weapon));
    }

    void decreaseHealth(int point) {
    }

    void increaseHealth(int point) {
    }
    //must be destroied ?

    public Node getTankNode() {
        return tankNode;
    }

    /**
     * Reset the place of tank to it's initial state
     */
    public void resetTank() {
        vehicleControl.setPhysicsLocation(new Vector3f(-140, 14, -23));// inja kojas? :D miofte pain 
        vehicleControl.setPhysicsRotation(new Matrix3f());
        vehicleControl.clearForces();
    }

    public void addWeapon(Weapon weapon) {
        //add weapon to tank
        weapons.add(weapon);
        tankNode.attachChild(weapon.getWeaponNode());
        //handle activeWeapon
        if (weapons.size() == 1) {
            activeWeapon = weapon;
        }
        //set translation of weapon related to tank
        weapon.getWeaponNode().setLocalTranslation((new Vector3f(0, 2f, 6.52f)));
        //do something in game e.g show a gun in w
    }

    public void removeWeapon(Weapon weapon) {
        //handle activeWeapon
        if (activeWeapon.equals(weapon)) {
            if (weapons.size() == 1) {
                activeWeapon = null;
            } else {
                activeWeapon = weapons.get(0);
            }
        }
        //remove weapon from tank
        weapons.remove(weapon);
        tankNode.detachChild(weapon.getWeaponNode());

    }
    private PhysicsSpace getPhysicsSpace() {
        return this.app.getStateManager().getState(BulletAppState.class).getPhysicsSpace();
    }
    public void setVehicleControl(PhysicsHoverControl vehicleControl) {
        this.vehicleControl = vehicleControl;
    }
    
}
