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
    private Tank tank1;
    private Tank tank2;
    private Tank defaultPlayerTank;//
    private AbstractAppState hudState;
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;

        hudState = new HUDState();
        stateManager.attach(hudState);
        
        initTank();
        initWeapon();
        initKeys();

        tank1.registerObserver((Observer)hudState);
        
        
        
        
    }

    private void initTank() {
        //build the tank
        tank1 = new Tank(this.app, new Vector3f(0, 0, 0), new Quaternion(new float[]{0, 0.01f, 0}));
        tank2 = new Tank(this.app, new Vector3f(0, 0, 40), new Quaternion(new float[]{0, 1.5f, 0}));
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
        app.getInputManager().addMapping("Lefts", new KeyTrigger(KeyInput.KEY_A));
        app.getInputManager().addMapping("Rights", new KeyTrigger(KeyInput.KEY_D));
        app.getInputManager().addMapping("Ups", new KeyTrigger(KeyInput.KEY_W));
        app.getInputManager().addMapping("Downs", new KeyTrigger(KeyInput.KEY_S));
        app.getInputManager().addMapping("leftFire", new KeyTrigger(KeyInput.KEY_O), new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        app.getInputManager().addMapping("rightFire", new KeyTrigger(KeyInput.KEY_P), new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
        app.getInputManager().addMapping("WheelUp", new MouseAxisTrigger(MouseInput.AXIS_WHEEL, false));
        app.getInputManager().addMapping("WheelDown", new MouseAxisTrigger(MouseInput.AXIS_WHEEL, true));
        app.getInputManager().addMapping("Reset", new KeyTrigger(KeyInput.KEY_RETURN));
        app.getInputManager().addListener(this, "Lefts");
        app.getInputManager().addListener(this, "Rights");
        app.getInputManager().addListener(this, "Ups");
        app.getInputManager().addListener(this, "Downs");
        app.getInputManager().addListener(this, "leftFire");
        app.getInputManager().addListener(this, "rightFire");
        app.getInputManager().addListener(this, "WheelUp");
        app.getInputManager().addListener(this, "WheelDown");
        app.getInputManager().addListener(this, "Reset");
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
