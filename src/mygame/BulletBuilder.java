package mygame;

import com.jme3.app.Application;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;

/**
 *
 * @author vahid & Farzad :D
 */
public abstract class BulletBuilder {

    protected Bullet bullet;
    //these props 
    protected Mesh mesh;
    protected Material material;
    protected int power;
    protected Application app;
    protected RigidBodyControl bulletControl;

    protected abstract void initMesh();

    protected abstract void initMaterial();

    protected abstract void initPower();

    protected abstract void buildBulletControl(Vector3f velocityDirection);

    public BulletBuilder(Application app) {
        this.app = app;
        initMesh();
        initMaterial();
        initPower();
    }

    //All non-shareable thing goes here
    final Bullet buildBullet(Vector3f velocityDirection) {

        bullet = new Bullet();
        buildBulletControl(velocityDirection);

        //apply bullet properties
        bullet.setGeometry(new Geometry("bullet", mesh));
        bullet.setMaterial(material);
        bullet.setPower(power);
        bullet.setBulletControl(bulletControl);
        return bullet;
    }
}