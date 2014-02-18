package mygame;

import com.jme3.app.Application;
import com.jme3.asset.TextureKey;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Mesh;
import com.jme3.scene.shape.Sphere;
import com.jme3.texture.Texture;
import mygame.BulletCreator;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author vahid
 */
public class BulletCreatorSimple extends BulletCreator {

    public BulletCreatorSimple(Application app) {
        super(app);
    }

    @Override
    Mesh instantiaceMesh() {
        Sphere s = new Sphere(32, 32, 0.2f, true, false);
        s.setTextureMode(Sphere.TextureMode.Projected);
        return s;
    }

    @Override
    Material instantiaceMaterial() {
        Material mat;
        mat = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        TextureKey key2 = new TextureKey("Textures/Terrain/Rock/Rock.PNG");
        key2.setGenerateMips(true);
        Texture tex2 = app.getAssetManager().loadTexture(key2);
        mat.setTexture("ColorMap", tex2);
        return mat;
    }

    @Override
    int instantiacePower() {
        return 1;
    }

    @Override
    RigidBodyControl buildBulletControl(Vector3f direction) {
        SphereCollisionShape bulletCollisionShape = new SphereCollisionShape(0.4f);
        RigidBodyControl bulletControl;
        bulletControl = new BombControl(app.getAssetManager(), bulletCollisionShape, 0.001f);
        bulletControl.setLinearVelocity((direction).mult(55));
        app.getStateManager().getState(BulletAppState.class).getPhysicsSpace().add(bulletControl);
        return bulletControl;
    }
}
