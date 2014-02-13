package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

public abstract class Weapon {

    FireBehaviour fireBehaviour;
    int bulletCount;
    
    //AudioNode sound;
    //   private Node weaponNode;
//    public Weapon(AssetManager assetManager, Node rootNode, BulletAppState bulletAppState, int bulletCount) {
//        //       sound = new AudioNode(assetManager,"Sounds/weapon1.wav",false);
//        this.assetManager = assetManager;
//        this.rootNode = rootNode;
//        this.bulletAppState = bulletAppState;
//        this.bulletCount = bulletCount;
//    }

    public Weapon(FireBehaviour fireBehaviour, int bulletCount) {
        //       sound = new AudioNode(assetManager,"Sounds/weapon1.wav",false);
        
        this.fireBehaviour = fireBehaviour;
        this.bulletCount = bulletCount;
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
