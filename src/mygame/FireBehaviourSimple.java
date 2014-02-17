/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.Application;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;

/**
 *
 * @author vahid
 */
public class FireBehaviourSimple extends FireBehaviour {


    public FireBehaviourSimple(Application app,Node rootNode,Bullet bullet) {
        super(app,rootNode,bullet);
       // BulletSimple b = new BulletSimple(app);
    }

    @Override
    void fire(Vector3f direction, Vector3f location) {
        
        
            Geometry b = bullet.buildBullet(new Vector3f(0,2,0));
            rootNode.attachChild(b);


       // weaponSound1.play();

    }
}
