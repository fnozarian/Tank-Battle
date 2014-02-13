package mygame;

import java.util.ArrayList;
import com.jme3.audio.AudioNode;
import com.jme3.bullet.collision.PhysicsCollisionObject;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.control.CameraControl;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.renderer.Camera;
import com.jme3.asset.AssetManager;

public class Tank {

    //public VehicleControl vehicleControl; //swap with PhysicsHoverControl
    PhysicsHoverControl vehicleControl;
    private final float accelerationForce = 1000.0f;
    private final float brakeForce = 100.0f;
    private float steeringValue = 0;
    private float accelerationValue = 0;
    
    private float compValue = 0.2f; //(lower than damp!)
    private float dampValue = 0.3f;
    private final float mass = 400;
    float stiffness = 120.0f;//200=f1 car

    private ArrayList<Weapon> Weapons;
    private Weapon activeWeapon;
    private int health;
    CollisionShape colShape;
    AudioNode tankIdleSound;
    
    private Node tankNode;
    private Geometry tankBody;
    
    CameraNode camNode;
    AssetManager assetManager;
    
    public Tank(AssetManager assetManager,Camera cam) {
        
        this.assetManager = assetManager;
        
        //Configuring Model
        tankBody = (Geometry)assetManager.loadModel("Models/HoverTank/Tank2.mesh.xml");
        colShape = CollisionShapeFactory.createDynamicMeshShape(tankBody);
        tankBody.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        tankBody.setLocalTranslation(new Vector3f(-60, 14, -23));
        tankBody.setLocalRotation(new Quaternion(new float[]{0, 0.01f, 0}));
        
        //Configuring camera
        camNode = new CameraNode("camNode", cam);
        //Setting the direction to Spatial to camera, this means the camera will copy the movements of the Node
        camNode.setControlDir(CameraControl.ControlDirection.SpatialToCamera);
        camNode.setLocalTranslation(0f, 4f, -12f);
        tankNode.attachChild(camNode);
        
        //Configuring vehicle control
        vehicleControl = new PhysicsHoverControl(colShape, 500);
        vehicleControl.setCollisionGroup(PhysicsCollisionObject.COLLISION_GROUP_02);
        tankNode.addControl(vehicleControl);
        
        //Configuring tank sound
        tankIdleSound = new AudioNode(assetManager, "Sounds/propeller-plane-idle.wav", false);
        tankIdleSound.setLooping(true);
        tankNode.attachChild(tankIdleSound);
        tankIdleSound.play();
        
        //Setting Default Weapon
        tankNode.attachChild(activeWeapon.getWeaponNode());
        
    }

    void accelerate(float force) {
    }

    void brake(float force) {
    }

    void steer(float value) {
    }

//    void fire() {
//        activeWeapon.fire();
//    }
    
    void switchWeapon(Weapon weapon) {
        activeWeapon = weapon;
    }
    void decreaseHealth(int point) {
    }

    void increaseHealth(int point) {
    }
}
        
        

        
