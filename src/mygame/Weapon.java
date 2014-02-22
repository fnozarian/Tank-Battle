package mygame;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.audio.AudioNode;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.ui.Picture;

public abstract class Weapon {

    protected GameConfigurations confs;
    protected Node rootNode;
    protected SimpleApplication app;
    protected FireBehaviour fireBehaviourLeft; //for left mouse button
    protected FireBehaviour fireBehaviourRight;//for right mouse button
    protected BulletBuilder bulletCreator;
    protected int bulletCount;
    protected Node weaponNode;
    protected AudioNode shootSound;
    protected Picture weaponThumb;
    protected String weaponName;
    protected Picture crossHair;
    
    public Weapon(Application app) { // This is Templete Method that shows a skeleton of weapon creation process!
        this.app = (SimpleApplication) app;

        confs = GameConfigurations.getInstance(app);//Singleton Pattern
        preInitWeapon(); // defer some settings to subclasses, with this we force 
        bulletCreator = makeBulletBuilder(); // this is Factory Method thath defer instanciation to weapon subclasses
        
        postInitWeapon(); // initializing processes that shared among subclasses
    }

    /**
     *
     * @param fireDirection Specify the direction of firing
     */
    public final void fire(Vector3f fireDirection,boolean isLeftFiring) {
        if (bulletCount != 0) {
            if(isLeftFiring){
                fireBehaviourLeft.fire(this,weaponNode.getWorldTranslation(), fireDirection, bulletCreator);
            }else{
                fireBehaviourRight.fire(this,weaponNode.getWorldTranslation(), fireDirection, bulletCreator);
            }
            shootSound.play();
        } else {
            noBullet();
        }
    }

    protected void noBullet() {
        // play sound of no bullet for example or something else
        System.err.println("no bullet");
    }

    public int getBulletCount() {
        return bulletCount;
    }

    public void setBulletCount(int bulletCount) {
        this.bulletCount = bulletCount;
    }

    public Node getWeaponNode() {
        return weaponNode;
    }

    /**
     * @return the weaponThumb
     */
    public Picture getWeaponThumb() {
        return weaponThumb;
    }

    /**
     * @return the weaponName
     */
    public String getWeaponName() {
        return weaponName;
    }

    /**
     * @return the crossHair
     */
    public Picture getCrossHair() {
        return crossHair;
    }

    protected abstract void preInitWeapon();

    protected abstract BulletBuilder makeBulletBuilder();  //Factory Method!
    
    private void postInitWeapon(){
        
        this.rootNode = this.app.getRootNode();
        weaponNode = new Node();
        weaponNode.attachChild(shootSound);
        //...
    }
    public void decreaseBulletCount(int number){
        bulletCount -= number;
        if (bulletCount<0) bulletCount =0;
    }
}
