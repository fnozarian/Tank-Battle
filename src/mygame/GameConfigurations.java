package mygame;

import com.jme3.app.Application;
import java.util.Properties;
import com.jme3.app.SimpleApplication;
import com.jme3.light.DirectionalLight;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.shadow.BasicShadowRenderer;

/**
 *
 * @author MR Blue
 */
public class GameConfigurations {

    private static GameConfigurations gameConfs;
    private SimpleApplication app;
    private Properties prop;
    private Properties defaultProp;
    
    private GameConfigurations(Application app) {
        this.app = (SimpleApplication) app;
        setDefaultProps();
        prop = (Properties)defaultProp.clone();
    }

    public static synchronized GameConfigurations getInstance(Application app) {
        if (null == gameConfs) {
            gameConfs = new GameConfigurations(app);
        }
        return gameConfs;
    }

    private void setDefaultProps() {

        defaultProp = new Properties();


        //Screen Properties
        float screenWidth = app.getContext().getSettings().getWidth();
        float screenHeight = app.getContext().getSettings().getHeight();

        //HUD Properties
        float weaponListTileWidth = screenWidth / 10;
        float weaponListTitleHeight = screenHeight / 10;
        float crossHairWidth = screenWidth / 20;
        float crossHairHeight = screenHeight / 20;
        float leftMargin = (float) (screenWidth * 0.008);
        float topMargin = (float) (screenHeight * 0.9);
        float weaponVerticalAlignmentSize = (float) (screenWidth * 0.05);
        float textVerticalAlignmentSize = (float) (weaponVerticalAlignmentSize * 0.01);
        float weaponHorizontalAlignmentSize = (float) (screenHeight * 0.05);
        float textHorizontalAlignmentSize = (float) (screenWidth * 0.02);
        float textLeftMargin = (float) (leftMargin + (leftMargin * 0.2));
        float topMarginWeaponDiff = (float) (1.5 * (screenHeight - topMargin));
        float crossHairX = ((screenWidth - crossHairWidth) / 2);
        float crossHairY = ((screenHeight - crossHairHeight) / 2 );
        
        //Sotring to defaultProp
        defaultProp.setProperty("screenWidth", toString(screenWidth));
        defaultProp.setProperty("screenHeight", toString(screenHeight));
        defaultProp.setProperty("weaponListTileWidth", toString(weaponListTileWidth));
        defaultProp.setProperty("weaponListTitleHeight", toString(weaponListTitleHeight));
        defaultProp.setProperty("crossHairWidth", toString(crossHairWidth));
        defaultProp.setProperty("crossHairHeight", toString(crossHairHeight));
        defaultProp.setProperty("leftMargin", toString(leftMargin));
        defaultProp.setProperty("topMargin", toString(topMargin));
        defaultProp.setProperty("weaponVerticalAlignmentSize", toString(weaponVerticalAlignmentSize));
        defaultProp.setProperty("textVerticalAlignmentSize", toString(textVerticalAlignmentSize));
        defaultProp.setProperty("weaponHorizontalAlignmentSize", toString(weaponHorizontalAlignmentSize));
        defaultProp.setProperty("textHorizontalAlignmentSize", toString(textHorizontalAlignmentSize));
        defaultProp.setProperty("textLeftMargin", toString(textLeftMargin));
        defaultProp.setProperty("topMarginWeaponDiff", toString(topMarginWeaponDiff));
        defaultProp.setProperty("crossHairX", toString(crossHairX));
        defaultProp.setProperty("crossHairY", toString(crossHairY));
        
        defaultProp.setProperty("displayStateView", toString(false));
        defaultProp.setProperty("displayFps", toString(false));
        
        
        //Weapons settings
        ////Rocket
        defaultProp.setProperty("rocketName", "Rocket");
        defaultProp.setProperty("rocketThumbPath", "Interface/Weapons/Rocket/Thumb.png");
        defaultProp.setProperty("rocketCrossHairPath", "Interface/Weapons/Rocket/CrossHair.png");
        defaultProp.setProperty("rocketShootShoundPath", "Sounds/Weapons/Rocket/Shoot.wav");
        defaultProp.setProperty("rocketBulletCount", "20");
        ////Plasma
        defaultProp.setProperty("plasmaName", "Plasma");
        defaultProp.setProperty("plasmaThumbPath", "Interface/Weapons/Plasma/Thumb.png");
        defaultProp.setProperty("plasmaCrossHair", "Interface/Weapons/Plasma/CrossHair.png");
        defaultProp.setProperty("plasmaShoundPath", "Sounds/Weapons/Plasma/Shoot.wav");

        defaultProp.setProperty("rocketName", "Plasma");//key string sounds wrong . might be PlasmaName same issue in following keys
        defaultProp.setProperty("rocketThumbPath", "Interface/Weapons/Plasma/Thumb.png");
        defaultProp.setProperty("rocketCrossHair", "Interface/Weapons/Plasma/CrossHair.png");
        defaultProp.setProperty("rocketShoundPath", "Sounds/Weapons/Plasma/Shoot.wav");

        defaultProp.setProperty("plasmaBulletCount", "20");
        
       
        //FireBehaviours settings
        ////SimpleFireBehaviour
        defaultProp.setProperty("SimpleFireBehaviourBulletsPerFire", "1");
        ////TripleFireBehaviour
        defaultProp.setProperty("TripleFireBehaviourBulletsPerFire", "3");
        
    }

    private String toString(float x) {
        return Float.toString(x);
    }

    private String toString(boolean x) {
        return Boolean.toString(x);
    }

    /**
     * @param key the key to be placed into this property list.
     * @param value the value corresponding to key.
     * @return the previous value of the specified key in this property list, or
     * null if it did not have one.
     */
    public Object setProperty(String key, String value) {
        return prop.setProperty(key, value);
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key. More formally, if this map contains
     * a mapping from a key k to a value v such that (key.equals(k)), then this
     * method returns v; otherwise it returns null. (There can be at most one
     * such mapping.)
     *
     * @param key the property key.
     * @return the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key
     */
    public Object getProperty(String key) {
        return prop.getProperty(key);
    }
}
