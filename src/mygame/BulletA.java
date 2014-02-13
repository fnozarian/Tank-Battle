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
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;
import com.jme3.texture.Texture;

/**
 *
 * @author vahid
 */
public class BulletA extends Bullet {

    public BulletA(Application app, Node rootNode) {
        super(app, rootNode);
    }

    public Geometry builtBullet() {
        //set geometry of bullet
        Sphere s = new Sphere(32, 32, 0.4f, true, false);
        s.setTextureMode(Sphere.TextureMode.Projected);
        Geometry bullet = new Geometry("bullet", s);
        // set matterial of bullet
        mat = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        TextureKey key2 = new TextureKey("Textures/Terrain/Rock/Rock.PNG");
        key2.setGenerateMips(true);
        Texture tex2 = app.getAssetManager().loadTexture(key2);
        mat.setTexture("ColorMap", tex2);
        bullet.setMaterial(mat);
        bullet.setShadowMode(ShadowMode.CastAndReceive);

        // ################### be nazare man ina bayad inja bashan ta tu fire behavior chon marbutan be sakhtane bullet #############
        SphereCollisionShape bulletCollisionShape = new SphereCollisionShape(0.4f);
        RigidBodyControl bulletControl = new BombControl(app.getAssetManager(), bulletCollisionShape, 0.001f);
        bulletControl.setLinearVelocity(direction.mult(25));
        bullet.setLocalTranslation(app.getCamera().getLocation());
        bullet.addControl(bulletControl);
        rootNode.attachChild(bullet);
        app.getStateManager().getState(BulletAppState.class).getPhysicsSpace().add(bulletControl);

        return bullet;
    }
}
