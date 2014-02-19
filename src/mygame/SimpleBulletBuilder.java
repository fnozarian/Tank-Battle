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

/**
 *
 * @author vahid
 */
public class SimpleBulletBuilder extends BulletBuilder {

    public SimpleBulletBuilder(Application app) {
        super(app);
    }

    @Override
    protected void initMesh() {
        Sphere s = new Sphere(32, 32, 0.2f, true, false);
        s.setTextureMode(Sphere.TextureMode.Projected);
        //Oops!
        mesh = (Mesh)s;
    }

    @Override
    protected void initMaterial() {
        material = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        TextureKey key2 = new TextureKey("Textures/Terrain/Rock/Rock.PNG");
        key2.setGenerateMips(true);
        Texture tex2 = app.getAssetManager().loadTexture(key2);
        material.setTexture("ColorMap", tex2);
    }

    @Override
    protected void initPower() {
        power = 1;
    }

    /**
     * Build Bullet And Also Add to State Manager
     * @param velocityDirection 
     */
    @Override
    protected void buildBulletControl(Vector3f velocityDirection) {
        SphereCollisionShape bulletCollisionShape = new SphereCollisionShape(0.4f);
        bulletControl = new BombControl(app.getAssetManager(), bulletCollisionShape, 0.001f);
        bulletControl.setLinearVelocity((velocityDirection).mult(355));
        app.getStateManager().getState(BulletAppState.class).getPhysicsSpace().add(bulletControl);
    }
}
