/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.asset.AssetManager;
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
public class FireBehaviourA extends FireBehaviour{
    private float time_per_fire;
private int bullets_per_period;
private float stop_time;
public FireBehaviourA(AssetManager assetManager, Node rootNode, BulletAppState bulletAppState)
       
{       
        super(assetManager , rootNode,bulletAppState);
        bullet = new BulletA(assetManager);
	this.time_per_fire = 0;
	this.bullets_per_period = 0;
	this.stop_time = 0;
       
}
	@Override
	void fire(Vector3f direction, Vector3f location) {

        SphereCollisionShape bulletCollisionShape = new SphereCollisionShape(0.4f);
        RigidBodyControl bulletNode = new BombControl(assetManager, bulletCollisionShape, 0.001f);
        
        bulletNode.setLinearVelocity(direction.mult(25));//////
        Geometry bull = bullet.builtBullet();
        bull = bullet.builtBullet();
        bull.setLocalTranslation(location);
        bull.addControl(bulletNode);

        rootNode.attachChild(bull);
        bulletAppState.getPhysicsSpace().add(bulletNode);
        
        //weaponSound1.play();
		
	}

}
