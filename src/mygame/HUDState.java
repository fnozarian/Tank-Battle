package mygame;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.font.BitmapText;
import com.jme3.ui.Picture;
import java.util.ArrayList;
import com.jme3.app.SimpleApplication;

/**
 *
 * @author MR Blue
 */
public class HUDState extends AbstractAppState {

    private SimpleApplication app;
    private GameConfigurations confs;
    //HUD Settings
    private float crossHairWidth;
    private float crossHairHeight;
    private float leftMargin;
    private float topMargin;
    private float topMarginWeaponDiff;
    private float weaponListTileWidth;
    private float weaponListTitleHeight;
    private float textLeftMargin;
    private float weaponVerticalAlignmentSize;
    private float crossHairX;
    private float crossHairY;
    private BitmapText weaponListLabel;
    private BitmapText weaponNameLabel;
    private Picture crossHair;
    private Picture weaponThumb;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        confs = GameConfigurations.getInstance(this.app);

        initHUDSettings();
        prepareHUD();

    }

    @Override
    public void update(float tpf) {
        //TODO: implement behavior during runtime
    }

    @Override
    public void cleanup() {
        super.cleanup();
        //TODO: clean up what you initialized in the initialize method,
        //e.g. remove all spatials from rootNode
        //this is called on the OpenGL thread after the AppState has been detached
    }

    protected void drawPlayerInfo(Player player) {

        //Draw HUD screen
        Tank playerTank = player.getTank();
        ArrayList<Weapon> tankWeapons = playerTank.getWeapons();

        //////Draw Weapon List
        for (Weapon weapon : tankWeapons) {

            //Calculate each weapon position
            float weaponThumbHight = topMargin - weaponListLabel.getHeight() - topMarginWeaponDiff;

            if (weapon.equals(playerTank.getActiveWeapon())) {//if this weapon in active weapon show specific icon,e.g Borde weapon thumb with red color!
                //@TODO
            }

            //Draw each weapon thumb
            weaponThumb = weapon.getWeaponThumb(); 
            weaponThumb.setWidth(weaponListTileWidth);
            weaponThumb.setHeight(weaponListTitleHeight);
            weaponThumb.setPosition(leftMargin, weaponThumbHight);
            app.getGuiNode().attachChild(weaponThumb);

            //Draw each weapon name blow the thumb
            weaponNameLabel = new BitmapText(app.getAssetManager().loadFont("Interface/Fonts/Default.fnt"), false);
            weaponNameLabel.setText(weapon.getWeaponName());
            weaponNameLabel.setLocalTranslation(leftMargin + textLeftMargin, weaponThumbHight, 0);
            app.getGuiNode().attachChild(weaponNameLabel);

        }
        //////Draw CrossHair for Active Weapon of Tank
        drawCrossHair(playerTank.getActiveWeapon());

    }

    private void drawCrossHair(Weapon weapon) {

        //Draw crossHair for active weapon
        crossHair = weapon.getCrossHair();
        //crossHair.setImage(app.getAssetManager(), "Interface/CrossHairs/circle-02-whole.png", true); //should go to weapon init
        crossHair.setWidth(crossHairWidth);
        crossHair.setHeight(crossHairHeight);
        crossHair.setPosition(crossHairX,crossHairY);
        app.getGuiNode().attachChild(crossHair);
    }

    private void prepareHUD() {

        //Draw a 'Weapon' Title for weapon list
        weaponListLabel = new BitmapText(app.getAssetManager().loadFont("Interface/Fonts/Default.fnt"), false);
        weaponListLabel.setText("Weapons"); // crosshairs
        weaponListLabel.setLocalTranslation(leftMargin, topMargin, 0);
        app.getGuiNode().attachChild(weaponListLabel);

        //Draw Health
        //...

        //Draw Score
        //...

    }

    //Getting HUD Setting Values from GameConfiguration
    private void initHUDSettings() {
        crossHairWidth = tof("crossHairWidth");
        crossHairHeight = tof("crossHairHeight");
        leftMargin = tof("leftMargin");
        topMargin = tof("topMargin");
        topMarginWeaponDiff = tof("topMarginWeaponDiff");
        weaponListTileWidth = tof("weaponListTileWidth");
        weaponListTitleHeight = tof("weaponListTitleHeight");
        textLeftMargin = tof("textLeftMargin");
        weaponVerticalAlignmentSize = tof("weaponVerticalAlignmentSize");
        crossHairX  = tof("crossHairX");
        crossHairY  = tof("crossHairY");
    }

    private float tof(String floatStringKey) {
        return Float.parseFloat(confs.getProperty(floatStringKey).toString());
    }

    private void switchWeapon(Weapon weapon) {
        //Switch crossHair
        //...
        //Switch Active Weapon in Weapon List
        //...
    }
}
