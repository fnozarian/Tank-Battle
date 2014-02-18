package mygame;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapText;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.control.VehicleControl;
import com.jme3.font.BitmapFont;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Matrix3f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;

/**
 *
 * @author MR Blue
 */
public class InGameState extends AbstractAppState implements ActionListener {

    private SimpleApplication app;
    private Node              rootNode;
    private AssetManager      assetManager;
    private AppStateManager   stateManager;
    private InputManager inputManager;
    private BitmapFont guiFont;
    private Tank tank1;
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
        
        tank1 = new Tank(this.app);
        testWeapon = new Weapon(app, rootNode, 200,new BulletCreatorSimple(this.app),new FireBehaviourSimple(this.app,rootNode),"Sounds/weapon1.wav");
        tank1.getTankNode().attachChild(testWeapon.getWeaponNode());
        testWeapon.getWeaponNode().setLocalTranslation((new Vector3f(0,4,-2)));

        rootNode.attachChild(tank1.getTankNode());
        getPhysicsSpace().add(tank1.getTankNode());

        setupKeys();
        initCrossHairs();


    }

    private PhysicsSpace getPhysicsSpace() {
        return stateManager.getState(BulletAppState.class).getPhysicsSpace();
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);

    }

    protected void initCrossHairs() {

        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        BitmapText ch = new BitmapText(guiFont, false);
        ch.setSize(guiFont.getCharSet().getRenderedSize() * 2);
        ch.setText("+"); // crosshairs
        ch.setLocalTranslation( // center
                app.getContext().getSettings().getWidth() / 2 - guiFont.getCharSet().getRenderedSize() / 3 * 2,
                app.getContext().getSettings().getHeight() / 2 + ch.getLineHeight() / 2, 0);
        app.getGuiNode().attachChild(ch);
    }

    private void setupKeys() {
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

        //Oops!
        //Configuring controling actions
        PhysicsHoverControl control = tank1.getVehicleControl();

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
                System.out.println("Reset");
                control.setPhysicsLocation(new Vector3f(-140, 14, -23));
                control.setPhysicsRotation(new Matrix3f());
                control.clearForces();
            } else {
            }
        } else if (name.equals("Space") && isPressed) {
            
            testWeapon.fire(tank1.getTankNode().getWorldRotation().getRotationColumn(2));         

        }
    }
}
