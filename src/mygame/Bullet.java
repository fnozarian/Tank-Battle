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
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;

/**
 *
 * @author vahid
 */
public abstract class Bullet {

    Geometry shape;
    Material mat;
    int power;
    AudioNode collisionVoice;
    AudioNode fireVoice;
    AnimChannel channel;
    AnimControl control;
    Application app;
    Vector3f direction;
    Node rootNode;
    
    public Bullet(Application app,Node rootNode) {
        this.app = (SimpleApplication)app;
        this.rootNode = rootNode;
    }

    public abstract Geometry builtBullet();
}
