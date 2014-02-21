package mygame;

import com.jme3.app.Application;
import com.jme3.asset.TextureKey;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Mesh;
import com.jme3.scene.shape.Sphere;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;

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
        Box b = new Box(1f, 1f, 1f);//32, 32, 0.2f, true, false
        //Oops!
        mesh = (Mesh) b;
    }

    @Override
    protected void initMaterial() {

        material = new Material(app.getAssetManager(),
                "Common/MatDefs/Misc/Unshaded.j3md");
        Texture cube1Tex = app.getAssetManager().loadTexture(
                "Interface/Logo/Monkey.jpg");
        material.setTexture("ColorMap", cube1Tex);
    }

    @Override
    protected void initPower() {
        power = 2;
    }

    @Override
    protected void buildBulletControl(Vector3f velocityDirection) {
        BoxCollisionShape bulletCollisionShape = new BoxCollisionShape(new Vector3f(1,1,1));
        bulletControl = new BombControl(app.getAssetManager(), bulletCollisionShape, 10f);
        bulletControl.setLinearVelocity((velocityDirection).mult(350));
        app.getStateManager().getState(BulletAppState.class).getPhysicsSpace().add(bulletControl);
    }
}
