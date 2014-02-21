package mygame;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.audio.AudioNode;
import com.jme3.font.BitmapText;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.RenderFontJme;
import com.jme3.scene.Node;
import com.jme3.ui.Picture;
import java.util.Hashtable;
import java.util.Properties;

public class Weapon {

    private GameConfigurations confs;
    private Node rootNode;
    private SimpleApplication app;
    private FireBehaviour fireBehaviour;
    private BulletBuilder bulletBuilder;
    private int bulletCount;
    private Node weaponNode;
    private AudioNode weaponSound;
    private Picture weaponThumb;
    private String weaponName;
    private Picture crossHair;
    
    public void setFireBehaviour(FireBehaviour fireBehaviour) {
        this.fireBehaviour = fireBehaviour;
    }

    public void setBulletCreator(BulletBuilder bulletBuilder) {
        this.bulletBuilder = bulletBuilder;
    }

    public Weapon(Application app, int bulletCount, BulletBuilder bulletCreator, FireBehaviour fireBehaviour, String fireSound) {// fireSound should be mono
        confs = GameConfigurations.getInstance(app);
        
        this.app = (SimpleApplication)app;
        this.rootNode = this.app.getRootNode();
        this.bulletCount = bulletCount;
        this.bulletBuilder = bulletCreator;
        this.fireBehaviour = fireBehaviour;
        weaponSound = new AudioNode(app.getAssetManager(), fireSound, false);
        weaponSound.setPositional(true);
        weaponNode = new Node();
        weaponNode.attachChild(weaponSound);

    }
    /**
     * 
     * @param fireDirection Specify the direction of firing 
     */
    public void fire(Vector3f fireDirection) {
        if (bulletCount != 0) {
            fireBehaviour.fire(weaponNode.getWorldTranslation(), fireDirection, bulletBuilder,this);
           // bulletCount--;
            weaponSound.play();
        } else {
            noBullet();
        }
    }

    private void noBullet() {
        // play sound of no bullet for example or something else
    }

    public int getBulletCount() {
        return bulletCount;
    }

    public void setBulletCount(int bulletCount) {
        this.bulletCount = bulletCount;
    }

    public Node getWeaponNode() {
        return weaponNode;
    }

    /**
     * @return the weaponThumb
     */
    public Picture getWeaponThumb() {
        return weaponThumb;
    }

    /**
     * @return the weaponName
     */
    public String getWeaponName() {
        return weaponName;
    }

    /**
     * @return the crossHair
     */
    public Picture getCrossHair() {
        return crossHair;
    }
    
    
}

