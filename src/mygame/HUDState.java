package mygame;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.font.BitmapText;
import com.jme3.ui.Picture;
import com.jme3.app.SimpleApplication;
import com.jme3.font.BitmapFont;
import com.jme3.scene.Node;
import java.util.ArrayList;
import javax.swing.text.StyledEditorKit;

/**
 *
 * @author MR Blue
 */
public class HUDState extends AbstractAppState implements Observer {

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
    private Picture crossHair;
    private Picture weaponThumb;
    private BitmapFont defaultFont;
    private Tank playerTank;
    private Node weaponListNode;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        confs = GameConfigurations.getInstance(this.app);
        defaultFont = app.getAssetManager().loadFont("Interface/Fonts/Default.fnt");
        weaponListNode = new Node();
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

    private void updateWeaponList(Tank t,ArrayList<Weapon> weapons) {
        //if(weaponListNode == null ) return;
        weaponListNode.detachAllChildren();
        weaponListNode.setLocalTranslation(2   , 2,2);
//        ArrayList<Weapon> weapons = new ArrayList<Weapon>();
//        for (Object o : objects) {
//            weapons.add((Weapon) o);
//        }
        
        //////Draw Weapon List
        for (Weapon weapon : weapons) {
            
            //Calculate each weapon position
            float weaponThumbHight = topMargin - weaponListLabel.getHeight() - topMarginWeaponDiff - (weapons.indexOf(weapon) * weaponVerticalAlignmentSize);

            if (weapon.equals(t.getActiveWeapon())) {//if this weapon in active weapon show specific icon,e.g Borde weapon thumb with red color!
                //Draw each weapon thumb
                weaponThumb = weapon.getWeaponThumb(true);
            } else {
                weaponThumb = weapon.getWeaponThumb(false);
            }
            weaponThumb.setWidth(weaponListTileWidth);
            weaponThumb.setHeight(weaponListTitleHeight);
            weaponThumb.setPosition(leftMargin, weaponThumbHight);
            weaponListNode.attachChild(weaponThumb);

        }
        this.app.getGuiNode().attachChild(weaponListNode);
        //////Draw CrossHair for Active Weapon of Tank
        // updateCrossHair(playerTank.getActiveWeapon());
    }

    private void updateHealth(int newHealth) {
        System.out.println("Health Has Updated! New Health is: " + newHealth);
    }

    private void updateCrossHair(Weapon weapon) {
        if (weapon == null) {
            BitmapText noWeaponText = new BitmapText(defaultFont);
            noWeaponText.setText("Notic! No Weapon Available!");
            app.getGuiNode().attachChild(noWeaponText);
        } else {

            //Draw crossHair for active weapon
            crossHair = weapon.getCrossHair();
            if (crossHair == null || app.getGuiNode() == null) {
                return;
            }
            crossHair.setWidth(crossHairWidth);
            crossHair.setHeight(crossHairHeight);
            crossHair.setPosition(crossHairX, crossHairY);
            app.getGuiNode().attachChild(crossHair);
        }
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
        crossHairX = tof("crossHairX");
        crossHairY = tof("crossHairY");
    }

    private float tof(String floatStringKey) {
        return Float.parseFloat(confs.getProperty(floatStringKey).toString());
    }

    public void update(Observable o, Object arg) {
        if (arg instanceof Integer) { //Health has chnged
            updateHealth(((Integer) arg).intValue());
        } else if (arg instanceof Object[]) { //weapons has changed
            updateWeaponList((Tank)o,((Tank)o).getWeapons());
        } else if (arg instanceof Weapon) { //Active weapon has changed
            updateWeaponList((Tank)o,((Tank)o).getWeapons());
        }
    }
}
