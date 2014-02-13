package mygame;

import com.jme3.app.Application;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

public abstract class FireBehaviour {

    Bullet bullet;
    Application app;
    Node rootNode;
    
    public FireBehaviour(Application app,Node rootNode) {
        this.app = app;
        this.rootNode = rootNode;
    }

    abstract void fire(Vector3f direction, Vector3f location);
}
