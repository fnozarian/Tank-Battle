/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.Application;
import com.jme3.asset.TextureKey;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;
import com.jme3.texture.Texture;

/**
 *
 * @author vahid
 */
public class BulletSimple extends Bullet {

    public BulletSimple(Application app) {
        super(app);

        //instantiate m (mesh of object) 
        Sphere s = new Sphere(32, 32, 0.2f, true, false);
        s.setTextureMode(Sphere.TextureMode.Projected);
        m = s;
        //instantiate mat (material of object) 
        mat = new Material(super.app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        TextureKey key2 = new TextureKey("Textures/Terrain/Rock/Rock.PNG");
        key2.setGenerateMips(true);
        Texture tex2 = super.app.getAssetManager().loadTexture(key2);
        mat.setTexture("ColorMap", tex2);
        // instantiate power
        power = 1;



    }

    public Geometry buildBullet(Vector3f location) {
        //apply mesh to shape
        shape = new Geometry("bullet", m);
        // apply mat 
        shape.setMaterial(mat);
        //apply Shadow
        shape.setShadowMode(ShadowMode.CastAndReceive);
        // instantiate bulletControl
        SphereCollisionShape bulletCollisionShape = new SphereCollisionShape(0.4f);
        bulletControl = new BombControl(super.app.getAssetManager(), bulletCollisionShape, 0.001f);
        bulletControl.setLinearVelocity((new Vector3f(0, 0, 1)).mult(35));
        super.app.getStateManager().getState(BulletAppState.class).getPhysicsSpace().add(bulletControl);

        shape.setLocalTranslation(location);
        shape.addControl(bulletControl);
        //set initiating location
        
        
        return shape;
    }
}
