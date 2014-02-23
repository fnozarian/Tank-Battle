package mygame;

import com.jme3.app.Application;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.BloomFilter;
import com.jme3.scene.Mesh;
import com.jme3.scene.shape.Torus;
/**
 *
 * @author MR Blue
 */
public class PlasmaBulletBuilder extends BulletBuilder {

    public PlasmaBulletBuilder(Application app) {
        super(app);
    }

    @Override
    protected void initMesh() {
        Torus b = new Torus(20,20,1f, 2f);//32, 32, 0.2f, true, false
        
        //Oops!
        mesh = (Mesh) b;
    }

    @Override
    protected void initMaterial() {

        Material mat = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Green);
        mat.setColor("GlowColor", ColorRGBA.Green);
        FilterPostProcessor fpp = new FilterPostProcessor(app.getAssetManager());
        BloomFilter bloom = new BloomFilter(BloomFilter.GlowMode.Objects);
        fpp.addFilter(bloom);
        app.getViewPort().addProcessor(fpp);
        material = mat;
    }

    @Override
    protected void initPower() {
        power = 5;
    }

    @Override
    protected void buildBulletControl(Vector3f velocityDirection) {
        BoxCollisionShape bulletCollisionShape = new BoxCollisionShape(new Vector3f(1, 1, 1));
        bulletControl = new BombControl(app.getAssetManager(), bulletCollisionShape, 20f);
        bulletControl.setLinearVelocity((velocityDirection).mult(350));
        app.getStateManager().getState(BulletAppState.class).getPhysicsSpace().add(bulletControl);
    }
}
