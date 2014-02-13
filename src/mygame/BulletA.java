/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.asset.TextureKey;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Sphere;
import com.jme3.texture.Texture;

/**
 *
 * @author vahid
 */
public class BulletA extends Bullet{
    public BulletA(AssetManager assetManager){
        super(assetManager);
        // set matterial of bullet
        mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        TextureKey key2 = new TextureKey("Textures/Terrain/Rock/Rock.PNG");
        key2.setGenerateMips(true);
        Texture tex2 = assetManager.loadTexture(key2);
        mat.setTexture("ColorMap", tex2);

 
    }
    public  Geometry builtBullet()
    {   
    Sphere s = new Sphere(32, 32, 0.4f, true, false);
    s.setTextureMode(Sphere.TextureMode.Projected);
    Geometry bullet = new Geometry("bullet",s);
    bullet.setMaterial(mat);
    bullet.setShadowMode(ShadowMode.CastAndReceive);

    return bullet;
    }

   
}
