package mygame;

import com.jme3.app.Application;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

/**
 *
 * @author vahid
 */
public class FireBehaviourSimple extends FireBehaviour {

    public FireBehaviourSimple(Application app) {
        super(app);
    }

    @Override
    public void fire(Vector3f location, Vector3f direction,BulletBuilder bulletCreator) {
        Bullet b = bulletCreator.buildBullet(direction);
        b.setLocalTranslation(location);
        b.addControl();
        rootNode.attachChild(b.getGeometry());

        

    }
}
