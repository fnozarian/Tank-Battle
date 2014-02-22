package mygame;

import com.jme3.app.Application;
import com.jme3.audio.AudioNode;
import com.jme3.math.Vector3f;
import com.jme3.ui.Picture;

/**
 *
 * @author MR Blue
 */
public class PlasmaWeapon extends Weapon{

    public PlasmaWeapon(Application app) {
        super(app);
    }

    @Override
    protected void preInitWeapon() {
        weaponName =  confs.getProperty("plasmaName").toString();
        shootSound = new AudioNode(app.getAssetManager(),confs.getProperty("plasmaShootShoundPath").toString(), false);
        shootSound.setPositional(true);
        weaponThumb = new Picture(weaponName);
        weaponThumb.setImage(app.getAssetManager(), confs.getProperty("plasmaThumbPath").toString(), true);
        crossHair = new Picture(weaponName);
        crossHair.setImage(app.getAssetManager(), confs.getProperty("plasmaCrossHairPath").toString(), true);
        bulletCount = Integer.parseInt(confs.getProperty("plasmaBulletCount").toString());
        fireBehaviourLeft = new SimpleFireBehaviour(app);
        fireBehaviourRight = new TripleFireBehaviour(app);
    }

    @Override
    protected BulletBuilder makeBulletBuilder() {
        return new PlasmaBulletBuilder(app);
    }
   
    
}
