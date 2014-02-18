package mygame;

import com.jme3.app.Application;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

public abstract class FireBehaviour {

    Application app;
    Node rootNode;

    public FireBehaviour(Application app, Node rootNode) {

        this.app = app;
        this.rootNode = rootNode;
        
    }

    abstract void fire(Vector3f location, Vector3f direction, BulletCreator bulletCreator);
}
