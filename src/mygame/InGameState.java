package mygame;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapText;
import com.jme3.app.SimpleApplication;
import com.jme3.font.BitmapFont;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.ui.Picture;

/**
 *
 * @author MR Blue
 */
public class InGameState extends AbstractAppState implements ActionListener {

    private SimpleApplication app;
    private Node rootNode;
    private AssetManager assetManager;
    private AppStateManager stateManager;
    private InputManager inputManager;
    private BitmapFont guiFont;
    private Tank tank1;
    private Tank tank2;
    private Weapon testWeapon;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.rootNode = this.app.getRootNode();
        this.assetManager = this.app.getAssetManager();
        this.stateManager = this.app.getStateManager();
        this.inputManager = this.app.getInputManager();
        this.guiFont = this.assetManager.loadFont("Interface/Fonts/Default.fnt");

        initTank();
        initWeapon();
        initKeys();
        initCrossHairs();
    }

    private void initTank() {

        tank1 = new Tank(this.app);
        
    }

    private void initWeapon() {
//        //build all weapons 
//        testWeapon = new Weapon(app, rootNode, 200, new BulletCreatorSimple(this.app), new FireBehaviourSimple(this.app, rootNode), "Sounds/weapon1.wav");
//
//        //attach needed weapons to tank
//        tank1.addWeapon(testWeapon);
//      
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);

    }

    //also init gun shape
    protected void initCrossHairs() {
        
        app.setDisplayStatView(false); app.setDisplayFps(false);

        float screenWidth = app.getContext().getSettings().getWidth();
        float screenHeight = app.getContext().getSettings().getHeight();
        float weaponListTileWidth = screenWidth / 10;
        float weaponListTitleHeight= screenHeight/ 10;
        float crossHairWidth = screenWidth / 15;
        float crossHairHeight= screenHeight/ 15;
        float leftMargin = (float)(screenWidth * 0.008);
        float topMargin = (float)(screenHeight * 0.9);
        float weaponVerticalAlignmentSize = (float)(screenWidth * 0.05);
        float textVerticalAlignmentSize = (float)(weaponVerticalAlignmentSize * 0.01);
        float weaponHorizontalAlignmentSize = (float)(screenHeight * 0.05);
        float textHorizontalAlignmentSize = (float)(screenWidth * 0.02);
        float textLeftMargin = (float)(leftMargin + (leftMargin*0.2));
        float topMarginWeaponDiff = (float)(1.5 * (screenHeight - topMargin)); /// 2;
        
        
        Picture crossHair = new Picture("CorssHair");
        crossHair.setImage(assetManager, "Interface/CrossHairs/circle-02-whole.png", true);
        crossHair.setWidth(crossHairWidth);
        crossHair.setHeight(crossHairHeight);
        crossHair.setPosition((screenWidth - crossHairWidth) / 2, (screenHeight - crossHairHeight) / 2);
        app.getGuiNode().attachChild(crossHair);
        
        BitmapText weaponListTitle = new BitmapText(guiFont, false);
        weaponListTitle.setText("Weapons"); // crosshairs
        weaponListTitle.setLocalTranslation(leftMargin,topMargin , 0);
        app.getGuiNode().attachChild(weaponListTitle);

        //Weapon 1
        float heightW1 = topMargin - weaponListTitle.getHeight() - topMarginWeaponDiff;
        Picture weapon1 = new Picture("Gun");
        weapon1.setImage(assetManager, "Interface/Guns/rocket.png", true);
        weapon1.setWidth(weaponListTileWidth);
        weapon1.setHeight(weaponListTitleHeight);
        weapon1.setPosition(leftMargin,heightW1);
        app.getGuiNode().attachChild(weapon1);
        //Text weapon 1
        BitmapText weapon1Text = new BitmapText(guiFont, false);
        weapon1Text.setText("Rocket"); // crosshairs
        weapon1Text.setLocalTranslation(leftMargin+textLeftMargin,heightW1  , 0);
        app.getGuiNode().attachChild(weapon1Text);
        //*******************************************
        //Weapon 2
        float heightW2 = heightW1 - 2* weaponVerticalAlignmentSize;
        Picture weapon2 = new Picture("Gun2");
        weapon2.setImage(assetManager, "Interface/Guns/plasma.png", true);
        weapon2.setWidth(weaponListTileWidth);
        weapon2.setHeight(weaponListTitleHeight);
        weapon2.setPosition(leftMargin,heightW2);
        app.getGuiNode().attachChild(weapon2);
        //Text weapon 1
        BitmapText weapon2Text = new BitmapText(guiFont, false);
        weapon2Text.setText("Plasma"); // crosshairs
        weapon2Text.setLocalTranslation(leftMargin+textLeftMargin,heightW2  , 0);
        app.getGuiNode().attachChild(weapon2Text);
        
        
        
    }

    private void initKeys() {
        inputManager.addMapping("Lefts", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("Rights", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("Ups", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("Downs", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("Space", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addMapping("Reset", new KeyTrigger(KeyInput.KEY_RETURN));
        inputManager.addListener(this, "Lefts");
        inputManager.addListener(this, "Rights");
        inputManager.addListener(this, "Ups");
        inputManager.addListener(this, "Downs");
        inputManager.addListener(this, "Space");
        inputManager.addListener(this, "Reset");
    }

    public void onAction(String name, boolean isPressed, float tpf) {

        //Configuring controling actions
        if (name.equals("Lefts")) {
            tank1.steer(isPressed ? 50f : 0);
        } else if (name.equals("Rights")) {
            tank1.steer(isPressed ? -50f : 0);
        } else if (name.equals("Ups")) {
            tank1.accelerate(isPressed ? 100f : 0);
        } else if (name.equals("Downs")) {
            tank1.accelerate(isPressed ? -100f : 0);
        } else if (name.equals("Reset")) {
            if (isPressed) {
                tank1.resetTank();
            } else {
            }
        } else if (name.equals("Space") && isPressed) {
            tank1.fire();

        }
    }
}
