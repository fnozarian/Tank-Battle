package mygame;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;

/**
 *
 * @author vahid
 */
public class Bullet {

    private Geometry geometry;
    private int power;
    private RigidBodyControl bulletControl;

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;

    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
        geometry.setUserData("power", power);
    }

    public void setMaterial(Material material) {
        geometry.setMaterial(material);
    }

    @Override
    public Bullet clone() {
        try {
            return (Bullet) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    public void addControl() {
        geometry.addControl(bulletControl);

    }

    public void setLocalTranslation(Vector3f location) {
        geometry.setLocalTranslation(location);
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setBulletControl(RigidBodyControl bulletControl) {
        this.bulletControl = bulletControl;
    }

    public RigidBodyControl getBulletControl() {
        return bulletControl;
    }
}