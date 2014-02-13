/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.scene.Node;

/**
 *
 * @author vahid
 */
public class WeaponA extends Weapon{
    public WeaponA(AssetManager assetManager,Node rootNode,BulletAppState bulletAppState,int bulletCount){
        super(assetManager,rootNode,bulletAppState, bulletCount);
         this.fireBehaviour = new FireBehaviourA(assetManager,rootNode,bulletAppState);
    }
}
