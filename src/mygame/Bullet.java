/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;

/**
 *
 * @author vahid
 */
public abstract class Bullet {
    Mesh m;
    Geometry shape; //geometry of bullet
    Material mat; // material of bullet
    RigidBodyControl bulletControl;
    int power; //power of bullet
    AudioNode collisionVoice;
    AudioNode fireVoice;
    AnimChannel channel;
    AnimControl control;
    Application app;
    Vector3f direction;
    //Node rootNode;
    
    public Bullet(Application app) {
        this.app = app;

    }

    public abstract Geometry buildBullet(Vector3f location);

    public RigidBodyControl getBulletControl() {
        return bulletControl;
    }
    
}
