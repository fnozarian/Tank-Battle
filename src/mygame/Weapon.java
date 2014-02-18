package mygame;
///////v
import com.jme3.app.Application;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.bullet.BulletAppState;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

public class Weapon {

    Application app;
    Node rootNode;
    private Node weaponNode;
    private FireBehaviour fireBehaviour;
    private BulletCreator bulletCreator;
    private int bulletCount;
    private AudioNode sound;

    public void setFireBehaviour(FireBehaviour fireBehaviour) {
        this.fireBehaviour = fireBehaviour;
    }

    public void setBulletCreator(BulletCreator bulletCreator) {
        this.bulletCreator = bulletCreator;
    }

    //AudioNode sound;
    //   private Node weaponNode;
//    public Weapon(AssetManager assetManager, Node rootNode, BulletAppState bulletAppState, int bulletCount) {
//        //       sound = new AudioNode(assetManager,"Sounds/weapon1.wav",false);
//        this.assetManager = assetManager;
//        this.rootNode = rootNode;
//        this.bulletAppState = bulletAppState;
//        this.bulletCount = bulletCount;
//    }
    public Weapon(Application app, Node rootNode, int bulletCount, BulletCreator bulletCreator, FireBehaviour fireBehaviour, String fireSound) {// fireSound should be mono

        this.app = app;
        this.rootNode = rootNode;
        this.bulletCount = bulletCount;
        this.bulletCreator = bulletCreator;
        this.fireBehaviour = fireBehaviour;
        sound = new AudioNode(app.getAssetManager(), fireSound, false);
        sound.setPositional(true);
        weaponNode = new Node();
        weaponNode.attachChild(sound);

    }

    public void fire(Vector3f tankDirection) {
        if (bulletCount != 0) {
            fireBehaviour.fire(weaponNode.getWorldTranslation(), tankDirection, bulletCreator);
            bulletCount--;
            sound.play();
        } else {
            noBullet();
        }
    }

    private void noBullet() {
        // play sound of no bullet for example or something else
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
}
