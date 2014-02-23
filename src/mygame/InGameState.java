package mygame;

import com.jme3.animation.LoopMode;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapText;
import com.jme3.app.SimpleApplication;
import com.jme3.audio.AudioNode;
import com.jme3.cinematic.MotionPath;
import com.jme3.cinematic.MotionPathListener;
import com.jme3.cinematic.events.MotionEvent;
import com.jme3.font.BitmapFont;
import com.jme3.input.ChaseCamera;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.InputListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.control.CameraControl.ControlDirection;
import com.jme3.ui.Picture;
import com.jme3.util.SkyFactory;

/**
 *
 * @author MR Blue
 */
public class InGameState extends AbstractAppState implements ActionListener, AnalogListener {

    private SimpleApplication app;
    private Tank tank1;
    private Tank tank2;
    private Tank defaultPlayerTank;//
    private Camera cam;
    private MotionPath path;
    private MotionEvent cameraMotionControl;
    private CameraNode camNode;
    

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.app.getRootNode().attachChild(SkyFactory.createSky(
        this.app.getAssetManager(),"Textures/Sky/Bright/BrightSky.dds", false));
        

        initTank();
        initWeapon();
        initKeys();
        initSun();
        initSound();
        initmotionCamera();
        
    }

    private void initTank() {
        //build the tank
        //build tank(S)
        tank1 = new Tank("Vahid",this.app, new Vector3f(0, 0, 0), new Quaternion(new float[]{0, 0.01f, 0}));
        tank2 = new Tank("Farzad",this.app, new Vector3f(0, 0, 40), new Quaternion(new float[]{0, 1.5f, 0}));

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
        app.getInputManager().addMapping("startGame", new KeyTrigger(KeyInput.KEY_SPACE));
        app.getInputManager().addListener(this, "Lefts");
        app.getInputManager().addListener(this, "Rights");
        app.getInputManager().addListener(this, "Ups");
        app.getInputManager().addListener(this, "Downs");
        app.getInputManager().addListener(this, "leftFire");
        app.getInputManager().addListener(this, "rightFire");
        app.getInputManager().addListener(this, "WheelUp");
        app.getInputManager().addListener(this, "WheelDown");
        app.getInputManager().addListener(this, "Reset");
        app.getInputManager().addListener(this, "startGame");
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
        }else if (name.equals("startGame")){
        cameraMotionControl.stop();
        CameraNode tankCamNode = (CameraNode) defaultPlayerTank.getChild("camNode");
        camNode.setEnabled(false);
        tankCamNode.setEnabled(true);
        tankCamNode.setCamera(cam);
   
        }
    }

    public void onAnalog(String name, float value, float tpf) {
       
    }

    private void initmotionCamera() {
        cam = this.app.getCamera();
        cam.setLocation(new Vector3f(8.4399185f, 11.189463f, 14.267577f));
         camNode = new CameraNode("Motion cam", cam);
        camNode.setControlDir(ControlDirection.SpatialToCamera);
        camNode.setEnabled(false);
         path = new MotionPath();
        //path.setCycle(true);
        //path.addWayPoint(new Vector3f(20, 150, 0));
        path.addWayPoint(new Vector3f(0, 150, -20));
        path.addWayPoint(new Vector3f(20, 100, 0));
        path.addWayPoint(new Vector3f(0, 40, 20));
        path.addWayPoint(new Vector3f(-20, 30, 0));
        path.addWayPoint(new Vector3f(0, 30, -20));
        
        path.setCurveTension(1f);
       // path.enableDebugShape(app.getAssetManager(), this.app.getRootNode());

        cameraMotionControl = new MotionEvent(camNode, path);
        cameraMotionControl.setLoopMode(LoopMode.Loop);
        //cameraMotionControl.setDuration(15f);
        cameraMotionControl.setLookAt(defaultPlayerTank.getWorldTranslation(), Vector3f.UNIT_Y);
        cameraMotionControl.setDirectionType(MotionEvent.Direction.LookAt);

        this.app.getRootNode().attachChild(camNode);
        


        path.addListener(new MotionPathListener() {

            public void onWayPointReach(MotionEvent control, int wayPointIndex) {
                if (path.getNbWayPoints() == wayPointIndex + 1) {
                    System.out.println(control.getSpatial().getName() + " Finish!!!");
                } else {
                     System.out.println(control.getSpatial().getName() + " Reached way point " + wayPointIndex);
                }
                //wayPointsText.setLocalTranslation((cam.getWidth() - wayPointsText.getLineWidth()) / 2, cam.getHeight(), 0);
            }
        });
      //  cam,setEnabled(initialized);
        camNode.setEnabled(true);
        cameraMotionControl.play();
        
        
       /* 

        flyCam.setEnabled(false);
        chaser = new ChaseCamera(cam, Tank1);
        chaser.registerWithInput(inputManager);
        chaser.setSmoothMotion(true);
        chaser.setMaxDistance(50);
        chaser.setDefaultDistance(50);
        initInputs();
        * */
    }

    private void initSun() {
        DirectionalLight sun = new DirectionalLight();
sun.setColor(ColorRGBA.White);
sun.setDirection(new Vector3f(-.5f,-.5f,-.5f).normalizeLocal());
this.app.getRootNode().addLight(sun);
    }

    private void initSound() {
     AudioNode  audio_nature = new AudioNode(this.app.getAssetManager(), "Sounds/bienvenida (1).mp3", true);
    audio_nature.setLooping(true);  // activate continuous playing
    audio_nature.setPositional(true);   
    audio_nature.setVolume(3);
    this.app.getRootNode().attachChild(audio_nature);
    audio_nature.play(); // play continuously!
    }
}
