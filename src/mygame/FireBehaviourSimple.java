/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.Application;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;

/**
 *
 * @author vahid
 */
public class FireBehaviourSimple extends FireBehaviour {

    public FireBehaviourSimple(Application app, Node rootNode) {
        super(app, rootNode);
        // BulletSimple b = new BulletSimple(app);
    }

    @Override
    void fire(Vector3f location, Vector3f direction,BulletCreator bulletCreator) {
        Bullet b = bulletCreator.buildBullet(direction);
        b.setLocalTranslation(location);
        b.addControl();
        rootNode.attachChild(b.getGeometry());

        

    }
}
