package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;


public abstract class FireBehaviour {
    Bullet bullet;
    AssetManager assetManager;
    Node rootNode;
    BulletAppState bulletAppState;
    public FireBehaviour(AssetManager assetManager,Node rootNode,BulletAppState bulletAppState){
        this.assetManager = assetManager;
        this.rootNode = rootNode;
        this.bulletAppState =bulletAppState;

        
    }
abstract void fire(Vector3f direction, Vector3f location);
}
