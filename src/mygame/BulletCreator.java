package mygame;

import com.jme3.app.Application;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;

/**
 *
 * @author vahid
 */
public abstract class BulletCreator {

    protected Mesh mesh;
    protected Material material;
    protected int power;
    protected Application app;

    protected abstract Mesh initMesh();

    protected abstract Material initMaterial();

    protected abstract int initPower();

    protected abstract RigidBodyControl buildBulletControl(Vector3f direction);

    public BulletCreator(Application app) {
        this.app = app;
        initBulletProperties();
    }

    private void initBulletProperties() {
        mesh = initMesh();
        material = initMaterial();
        power = initPower();

    }

    final Bullet buildBullet(Vector3f velocityDirection) {
        Bullet bullet = new Bullet();
        //apply bullet properties
        bullet.setGeometry(new Geometry("bullet", mesh));
        bullet.setMaterial(material);
        bullet.setPower(power);
        RigidBodyControl bulletControl = buildBulletControl(velocityDirection);
        bullet.setBulletControl(bulletControl);//this line does not appy the control. it only stores the control
        return bullet;
    }
}