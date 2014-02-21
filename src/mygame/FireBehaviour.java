package mygame;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

public abstract class FireBehaviour {

    protected SimpleApplication app;
    protected Node rootNode;

    public FireBehaviour(Application app) {
        this.app = (SimpleApplication) app;
        this.rootNode = this.app.getRootNode();
    }

    public abstract void fire(Vector3f location, Vector3f direction, BulletBuilder bulletCreator);
}
