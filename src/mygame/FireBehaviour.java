package mygame;

import com.jme3.app.Application;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

public abstract class FireBehaviour {

    protected Application app;
    protected Node rootNode;

    public FireBehaviour(Application app, Node rootNode) {

        this.app = app;
        this.rootNode = rootNode;
        
    }

    public abstract void fire(Vector3f location, Vector3f direction, BulletCreator bulletCreator);
}
