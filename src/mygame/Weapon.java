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

    private SimpleApplication app;
    private Node weaponNode;
    private FireBehaviour fireBehaviour;
    private BulletBuilder bulletCreator;
    private int bulletCount;
    private AudioNode sound;
    private GameConfigurations confs;
    private Node rootNode;
    
    public void setFireBehaviour(FireBehaviour fireBehaviour) {
        this.fireBehaviour = fireBehaviour;
    }

    public void setBulletCreator(BulletBuilder bulletCreator) {
        this.bulletCreator = bulletCreator;
    }

    //AudioNode sound;
    //   private Node weaponNode;
//    public Weapon(AssetManager assetManager, Node rootNode, BulletAppState bulletAppState, int bulletCount) {
//        //       sound = new AudioNode(assetManager,"Sounds/weapon1.wav",false);
//        this.assetManager = assetManager;
//        this.rootNode = rootNode;
//        this.bulletAppState = bulletAppState;
//        this.bulletCount = bulletCount;
//    }
    public Weapon(Application app, int bulletCount, BulletBuilder bulletCreator, FireBehaviour fireBehaviour, String fireSound) {// fireSound should be mono
        confs = GameConfigurations.getInstance(app);
        
        this.app = (SimpleApplication)app;
        this.rootNode = this.app.getRootNode();
        this.bulletCount = bulletCount;
        this.bulletCreator = bulletCreator;
        this.fireBehaviour = fireBehaviour;
        sound = new AudioNode(app.getAssetManager(), fireSound, false);
        sound.setPositional(true);
        weaponNode = new Node();
        weaponNode.attachChild(sound);

    }
    /**
     * 
     * @param fireDirection Specify the direction of firing 
     */
    public void fire(Vector3f fireDirection) {
        if (bulletCount != 0) {
            fireBehaviour.fire(weaponNode.getWorldTranslation(), fireDirection, bulletCreator);
            bulletCount--;
            sound.play();
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
    
    protected void initCrossHairs() {
        
        float crossHairWidth = tof("crossHairWidth");
        float crossHairHeight= tof("crossHairHeight");
        float screenWidth = tof("screenWidth");
        float screenHeight = tof("screenHeight");
        float leftMargin = tof("leftMargin");
        float topMargin = tof("topMargin");
        float topMarginWeaponDiff = tof("topMarginWeaponDiff");
        float weaponListTileWidth = tof("weaponListTileWidth");
        float weaponListTitleHeight = tof("weaponListTitleHeight");
        float textLeftMargin = tof("textLeftMargin");
        float weaponVerticalAlignmentSize = tof("weaponVerticalAlignmentSize");
        
        Picture crossHair = new Picture("CorssHair");
        crossHair.setImage(app.getAssetManager(), "Interface/CrossHairs/circle-02-whole.png", true);
        crossHair.setWidth(crossHairWidth);
        crossHair.setHeight(crossHairHeight);
        crossHair.setPosition((screenWidth - crossHairWidth) / 2, (screenHeight - crossHairHeight) / 2);
        app.getGuiNode().attachChild(crossHair);
        
        BitmapText weaponListTitle = new BitmapText(app.getAssetManager().loadFont("Interface/Fonts/Default.fnt"), false);
        weaponListTitle.setText("Weapons"); // crosshairs
        weaponListTitle.setLocalTranslation(leftMargin,topMargin , 0);
        app.getGuiNode().attachChild(weaponListTitle);

        //Weapon 1
        float heightW1 = topMargin - weaponListTitle.getHeight() - topMarginWeaponDiff;
        Picture weapon1 = new Picture("Gun");
        weapon1.setImage(app.getAssetManager(), "Interface/Guns/rocket.png", true);
        weapon1.setWidth(weaponListTileWidth);
        weapon1.setHeight(weaponListTitleHeight);
        weapon1.setPosition(leftMargin,heightW1);
        app.getGuiNode().attachChild(weapon1);
        //Text weapon 1
        BitmapText weapon1Text = new BitmapText(app.getAssetManager().loadFont("Interface/Fonts/Default.fnt"), false);
        weapon1Text.setText("Rocket"); // crosshairs
        weapon1Text.setLocalTranslation(leftMargin+textLeftMargin,heightW1  , 0);
        app.getGuiNode().attachChild(weapon1Text);
        //*******************************************
        //Weapon 2
        float heightW2 = heightW1 - 2* weaponVerticalAlignmentSize;
        Picture weapon2 = new Picture("Gun2");
        weapon2.setImage(app.getAssetManager(), "Interface/Guns/plasma.png", true);
        weapon2.setWidth(weaponListTileWidth);
        weapon2.setHeight(weaponListTitleHeight);
        weapon2.setPosition(leftMargin,heightW2);
        app.getGuiNode().attachChild(weapon2);
        //Text weapon 1
        BitmapText weapon2Text = new BitmapText(app.getAssetManager().loadFont("Interface/Fonts/Default.fnt"), false);
        weapon2Text.setText("Plasma"); // crosshairs
        weapon2Text.setLocalTranslation(leftMargin+textLeftMargin,heightW2  , 0);
        app.getGuiNode().attachChild(weapon2Text);
        
        
        
    }
    private float tof(String floatStringKey){
        return Float.parseFloat(confs.getProperty(floatStringKey).toString());
    }
}

