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
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.InputListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.ui.Picture;

/**
 *
 * @author MR Blue
 */
public class InGameState extends AbstractAppState implements ActionListener, AnalogListener {

    private SimpleApplication app;
    private AssetManager assetManager;
    private InputManager inputManager;
    private Tank tank1;
    private Tank tank2;
    private Tank defaultPlayerTank;//

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.assetManager = this.app.getAssetManager();
        this.inputManager = this.app.getInputManager();

        initTank();
        initWeapon();
        initKeys();
    }

    private void initTank() {
        //build the tank
        //build tank(S)
        tank1 = new Tank(this.app, new Vector3f(0, 0, 0), new Quaternion(new float[]{0, 0.01f, 0}));
        tank2 = new Tank(this.app, new Vector3f(0, 0, 40), new Quaternion(new float[]{0, 1.5f, 0}));

        tank1.setAsPlayer();
        tank1.attachToWorld(new Vector3f(200, 200, 200), new Quaternion(new float[]{0, 0.01f, 0}));
        //add necessary weapons to tank

        //set default tank for player
        setPlayerTank(tank1);// in order to take care of camera, keys, etc 
    }

    private void initWeapon() {
        //add necessary weapons to tank(S)
        tank1.addWeapon(new RocketWeapon(app));
        tank1.addWeapon(new PlasmaWeapon(app));
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);

    }

    //also init gun shape
    private void initKeys() {
        inputManager.addMapping("Lefts", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("Rights", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("Ups", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("Downs", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("leftFire", new KeyTrigger(KeyInput.KEY_O), new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addMapping("rightFire", new KeyTrigger(KeyInput.KEY_P), new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
        inputManager.addMapping("WheelUp", new MouseAxisTrigger(MouseInput.AXIS_WHEEL, false));
        inputManager.addMapping("WheelDown", new MouseAxisTrigger(MouseInput.AXIS_WHEEL, true));
        inputManager.addMapping("Reset", new KeyTrigger(KeyInput.KEY_RETURN));
        inputManager.addListener(this, "Lefts");
        inputManager.addListener(this, "Rights");
        inputManager.addListener(this, "Ups");
        inputManager.addListener(this, "Downs");
        inputManager.addListener(this, "leftFire");
        inputManager.addListener(this, "rightFire");
        inputManager.addListener(this, "WheelUp");
        inputManager.addListener(this, "WheelDown");
        inputManager.addListener(this, "Reset");
    }

    protected void setPlayerTank(Tank tank) {

        tank.setAsPlayer();
        defaultPlayerTank = tank;

    }

    public void onAction(String name, boolean isPressed, float tpf) {

        //Configuring controling actions
        if (name.equals("Lefts")) {
            defaultPlayerTank.steer(isPressed ? 50f : 0);
        } else if (name.equals("Rights")) {
            defaultPlayerTank.steer(isPressed ? -50f : 0);
        } else if (name.equals("Ups")) {
            defaultPlayerTank.accelerate(isPressed ? 100f : 0);
        } else if (name.equals("Downs")) {
            defaultPlayerTank.accelerate(isPressed ? -100f : 0);
        } else if (name.equals("Reset")) {
            if (isPressed) {
                defaultPlayerTank.resetTank();
            } else {
            }
        } else if (name.equals("leftFire") && isPressed) {
            defaultPlayerTank.fire(true);
        } else if (name.equals("rightFire") && isPressed) {
            defaultPlayerTank.fire(false);
        }else if (name.equals("WheelUp")) {
            defaultPlayerTank.switchWeapon(true);
        } else if (name.equals("WheelDown")) {
            defaultPlayerTank.switchWeapon(false);
        }
    }

    public void onAnalog(String name, float value, float tpf) {
       
    }
}
