package mygame;

import com.jme3.app.Application;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author vahid
 */
public abstract class BulletCreator {

    protected Mesh mesh;
    protected Material material;
    protected int power;
    protected Application app;

    abstract Mesh instantiaceMesh();

    abstract Material instantiaceMaterial();

    abstract int instantiacePower();

    abstract RigidBodyControl buildBulletControl(Vector3f direction);

    public BulletCreator(Application app) {
        this.app = app;
        instantiateBulletProperties();
    }

    private void instantiateBulletProperties() {
        mesh = instantiaceMesh();
        material = instantiaceMaterial();
        power = instantiacePower();

    }

    final Bullet buildBullet(Vector3f direction) {
        Bullet bullet = new Bullet();
        //apply bullet properties
        bullet.setGeometry(new Geometry("bullet", mesh));
        bullet.setMaterial(material);
        bullet.setPower(power);
        RigidBodyControl bulletControl = buildBulletControl(direction);
        bullet.setBulletControl(bulletControl);//this line does not appy the control. it only stores the control
        return bullet;
    }
}