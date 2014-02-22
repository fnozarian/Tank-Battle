package mygame;

import com.jme3.app.Application;
import com.jme3.audio.AudioNode;
import com.jme3.math.Vector3f;
import com.jme3.ui.Picture;

/**
 *
 * @author MR Blue
 */
public class RocketWeapon extends Weapon{
    
    
    
    public RocketWeapon(Application app) {
        super(app);
        
    }

    @Override
    protected void preInitWeapon() {//can encapsulate this in a config class and use factory method pattern !
        
        weaponName =  confs.getProperty("rocketName").toString();
        shootSound = new AudioNode(app.getAssetManager(),confs.getProperty("rocketShootShoundPath").toString(), false);
        shootSound.setPositional(true);
        weaponThumb = new Picture(weaponName);
        weaponThumb.setImage(app.getAssetManager(), confs.getProperty("rocketThumbPath").toString(), true);
        crossHair = new Picture(weaponName);
    //    crossHair.setImage(app.getAssetManager(), confs.getProperty("rocketCrossHairPath").toString(), true);
        bulletCount = Integer.parseInt(confs.getProperty("rocketBulletCount").toString());
        fireBehaviourLeft = new TripleFireBehaviour(app);
    }

    @Override
    protected BulletBuilder makeBulletBuilder() {
        return new RocketBulletBuilder(app);
    }

}
