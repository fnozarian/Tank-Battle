package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.light.DirectionalLight;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.shadow.BasicShadowRenderer;
import com.jme3.app.state.AbstractAppState;
import com.jme3.font.BitmapFont;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.queue.RenderQueue;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.MessageBox;
import de.lessvoid.nifty.screen.ScreenController;

public class Main extends SimpleApplication {

    private AbstractAppState inGameState;
    private BulletAppState bulletAppState;
    private GameConfigurations confs;

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        //Initialize GameConfigurations
        confs = GameConfigurations.getInstance(this);
        
        configSettings();

        //Configuring Game States
        bulletAppState = new BulletAppState();
        bulletAppState.setThreadingType(BulletAppState.ThreadingType.PARALLEL);
        inGameState = new InGameState();
        stateManager.attach(bulletAppState);
        PhysicsTestHelper.createPhysicsTestWorld(rootNode, assetManager, bulletAppState.getPhysicsSpace());
        stateManager.attach(inGameState);
    }

    @Override
    public void simpleUpdate(float tpf) {
    }

    private void configSettings() {

        //App Settings
        setDisplayStatView(Boolean.parseBoolean(confs.getProperty("displayStateView").toString()));
        setDisplayFps(Boolean.parseBoolean(confs.getProperty("displayFps").toString()));


        //What are these?!
        rootNode.setShadowMode(RenderQueue.ShadowMode.Off);
        BasicShadowRenderer bsr = new BasicShadowRenderer(assetManager, 256);
        bsr.setDirection(new Vector3f(-1, -1, -1).normalizeLocal());
        viewPort.addProcessor(bsr);


        //Configuring Lights
        DirectionalLight dl = new DirectionalLight();
        dl.setDirection(new Vector3f(0.5f, -0.1f, 0.3f).normalizeLocal());
        rootNode.addLight(dl);

        //Configuring camera
        cam.setFrustumFar(150f);//whats this ?!
        flyCam.setMoveSpeed(10); // is this necessary ?!
        flyCam.setEnabled(false);
    }
}
