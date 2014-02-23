package mygame;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import java.util.ArrayList;
import com.jme3.audio.AudioNode;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.PhysicsCollisionObject;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Node;
import com.jme3.scene.control.CameraControl;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.math.Matrix3f;
import com.jme3.scene.Spatial;

public class Tank extends Node implements PhysicsCollisionListener, Observable {

    private boolean changed = false;
    private ArrayList<Observer> observers;
    private final float mass = 500;
    private ArrayList<Weapon> weapons; //Observable !
    private Weapon activeWeapon; //Observable !
    private int health; //Observable !
    private CollisionShape colShape;
    private AudioNode tankIdleSound;
    private Spatial tankBody;
    private CameraNode camNode;
    private SimpleApplication app;
    private PhysicsHoverControl vehicleControl;

    public Tank(String name, Application app, Vector3f location, Quaternion direction) {

        this.app = (SimpleApplication) app;
        setName(name);

        observers = new ArrayList<Observer>();

        setLocalTranslation(location);
        setLocalRotation(direction);
        //initialize members
        weapons = new ArrayList<Weapon>();
        health = 100;
        //Configuring Model
        tankBody = this.app.getAssetManager().loadModel("Models/HoverTank/Tank2.mesh.xml");
        colShape = CollisionShapeFactory.createDynamicMeshShape(tankBody);
        // tankBody.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        tankBody.setLocalTranslation(new Vector3f(0, 0, 0));///-60, 14, -23

        attachChild(tankBody);

        //Configuring vehicle control
        vehicleControl = new PhysicsHoverControl(colShape, mass);
        vehicleControl.setCollisionGroup(PhysicsCollisionObject.COLLISION_GROUP_02);
        addControl(vehicleControl);

        //Configuring tank sound
        tankIdleSound = new AudioNode(this.app.getAssetManager(), "Sounds/propeller-plane-idle.wav", false);
        tankIdleSound.setLooping(true);
        attachChild(tankIdleSound);
        tankIdleSound.play();

        this.app.getRootNode().attachChild(this);
        getPhysicsSpace().add(this);



        getPhysicsSpace().addCollisionListener(this);
    }

    void setAsPlayer() {
        //Configuring camera
        camNode = new CameraNode("camNode", this.app.getCamera());
        //Setting the direction to Spatial to camera, this means the camera will copy the movements of the Node
        camNode.setControlDir(CameraControl.ControlDirection.SpatialToCamera);
        camNode.setLocalTranslation(0f, 4f, -12f);
        attachChild(camNode);
    }

    void accelerate(float force) {
        vehicleControl.accelerate(force);
    }

    void brake(float force) {
    }

    void steer(float value) {
        vehicleControl.steer(value);
    }

    public void fire(boolean isLeftFiring) {
        Vector3f fireDirection = getWorldRotation().getRotationColumn(2);
        fireDirection.setY(-0.024F);
        try {
            getActiveWeapon().fire(fireDirection, isLeftFiring);
        } catch (Exception e) {
            System.err.println("no activeWeapon found!");
        }

    }

    public void switchWeapon(boolean upward) { //Observable Method
        int nextWeaponIndex;
        int activeWeaponIndex = weapons.indexOf(activeWeapon);
        int weaponsSize = weapons.size();
        if (activeWeaponIndex == -1) {//this means there is no weapon in weapon list
            System.err.println("There is No Weapon in Weapon List");
            return;
        }
        if (upward) {
            nextWeaponIndex = (activeWeaponIndex + 1) % weaponsSize;
        } else {
            nextWeaponIndex = (activeWeaponIndex - 1 == -1) ? weaponsSize - 1 : activeWeaponIndex - 1;
        }
        activeWeapon = weapons.get(nextWeaponIndex);
        setChanged();
        notifyObserver(activeWeapon);
    }

    void decreaseHealth(int point) {
        if (point >= health) {
            health = 0;
        } else {
            health -= point;
        }
        if (health == 0) {
            System.err.println("Game over " + this.name);
        }
        setChanged();
        notifyObserver(health);
    }

    void increaseHealth(int point) {
        setChanged();
        notifyObserver(health);
    }

    /**
     * Reset the place of tank to it's initial state
     */
    public void resetTank() {
        vehicleControl.setPhysicsLocation(new Vector3f(-140, 14, -23));// inja kojas? :D miofte pain 
        vehicleControl.setPhysicsRotation(new Matrix3f());
        vehicleControl.clearForces();
    }

    public void addWeapon(Weapon weapon) { //Note! This method is observable!
        //add weapon to tank
        weapons.add(weapon);
        attachChild(weapon.getWeaponNode());
        //handle activeWeapon
        if (weapons.size() == 1) {
            activeWeapon = weapon;
            setChanged();
            notifyObserver(activeWeapon);
        }
        //set translation of weapon related to tank
        weapon.getWeaponNode().setLocalTranslation((new Vector3f(0, 2f, 8f)));

        setChanged();
        notifyObserver(weapons.toArray());

    }

    public void removeWeapon(Weapon weapon) {
        //handle activeWeapon
        if (getActiveWeapon().equals(weapon)) {
            if (weapons.size() == 1) {
                activeWeapon = null;
            } else {
                activeWeapon = weapons.get(0);
            }
            setChanged();
            notifyObserver(activeWeapon);
        }
        //remove weapon from tank
        weapons.remove(weapon);
        detachChild(weapon.getWeaponNode());

        setChanged();
        notifyObserver(weapons.toArray());

    }

    private PhysicsSpace getPhysicsSpace() {
        return this.app.getStateManager().getState(BulletAppState.class).getPhysicsSpace();
    }

    public void setVehicleControl(PhysicsHoverControl vehicleControl) {
        this.vehicleControl = vehicleControl;
    }

    /**
     * @return the weapons
     */
    public ArrayList<Weapon> getWeapons() {
        return weapons;
    }

    /**
     * @return the activeWeapon
     */
    public Weapon getActiveWeapon() {
        return activeWeapon;
    }

    public int getHealth() {
        return health;
    }

    public void collision(PhysicsCollisionEvent event) {


        System.err.println(event.getNodeB());
        System.err.println(event.getNodeA().getName());
        if ((event.getNodeB() != null) && (event.getNodeA() != null)) {

            if (((event.getNodeA().getClass().equals(Tank.class)) && (event.getNodeB().getName().equals("bullet")))
                    || ((event.getNodeB().getClass().equals(Tank.class)) && (event.getNodeA().getName().equals("bullet")))) {
                System.err.println("event:" + event.toString());
                Tank t;
                int power;
                if (event.getNodeA().getClass().equals(Tank.class)) {
                    t = (Tank) event.getNodeA();
                    power = event.getNodeB().getUserData("power");

                } else {
                    t = (Tank) event.getNodeB();
                    power = event.getNodeA().getUserData("power");
                }
                //   System.err.println("Power: " + power);
                //  System.err.println("Health: " + t.getHealth());
                //  System.err.println("------------------------------");
                t.decreaseHealth(power);

            }
        }
    }

     

    public synchronized void registerObserver(Observer o) {
        if (o == null) {
            throw new NullPointerException();
        }
        if (!observers.contains(o)) {
            observers.add(o);
        }
    }

    public synchronized void removeObserver(Observer o) {
        observers.remove(o);
    }

    public synchronized void setChanged() {
        changed = true;
    }

    public synchronized void clearChanged() {
        changed = false;
    }

    public synchronized boolean hasChanged() {
        return changed;
    }

    public void notifyObserver(Object arg) {
        /*
         * a temporary array buffer, used as a snapshot of the state of
         * current Observers.
         */
        Object[] arrLocal;

        synchronized (this) {
            /* We don't want the Observer doing callbacks into
             * arbitrary code while holding its own Monitor.
             * The code where we extract each Observable from
             * the Vector and store the state of the Observer
             * needs synchronization, but notifying observers
             * does not (should not).  The worst result of any
             * potential race-condition here is that:
             * 1) a newly-added Observer will miss a
             *   notification in progress
             * 2) a recently unregistered Observer will be
             *   wrongly notified when it doesn't care
             */
            if (!changed) {
                return;
            }
            arrLocal = observers.toArray();
            clearChanged();
        }

        for (int i = arrLocal.length - 1; i >= 0; i--) {
            ((Observer) arrLocal[i]).update(this, arg);

        }
    }
}
