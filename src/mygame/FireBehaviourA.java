/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.Application;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

/**
 *
 * @author vahid
 */
public class FireBehaviourA extends FireBehaviour {

    private float timePerFire;
    private int bulletsPerPeriod;
    private float stopTime;

    public FireBehaviourA(Application app, Node rootNode) {
        super(app, rootNode);

        bullet = new BulletA(app, rootNode);

        this.timePerFire = 0;
        this.bulletsPerPeriod = 0;
        this.stopTime = 0;
    }

    @Override
    void fire(Vector3f direction, Vector3f location) {

        bullet.builtBullet();

        //weaponSound1.play();

    }
}
