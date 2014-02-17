package mygame;
///////v
import com.jme3.app.Application;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

public class Weapon {
    Application app;
    Node rootNode;
    private FireBehaviour fireBehaviour;
    private Bullet bullet;
    private int bulletCount;

    public void setFireBehaviour(FireBehaviour fireBehaviour) {
        this.fireBehaviour = fireBehaviour;
    }

    public void setBullet(Bullet bullet) {
        this.bullet = bullet;
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

    public Weapon(Application app,Node rootNode,int bulletCount) {
        //       sound = new AudioNode(assetManager,"Sounds/weapon1.wav",false);
      this.app = app;
      this.rootNode = rootNode;
      this.bulletCount = bulletCount;
      this.fireBehaviour = new FireBehaviourSimple(app,rootNode, bullet);// default
     // this.bullet = new BulletSimple();

    }
    public void fire() {
        if (bulletCount != 0) {
            fireBehaviour.fire(new Vector3f(1, -1, 1), new Vector3f(1, -1, 1));
            bulletCount--;
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
    
}
