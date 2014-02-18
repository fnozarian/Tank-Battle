package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.light.DirectionalLight;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.shadow.BasicShadowRenderer;
import com.jme3.app.state.AbstractAppState;
import com.jme3.niftygui.NiftyJmeDisplay;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.ScreenController;

public class Main extends SimpleApplication {

    private AbstractAppState inGameState;
    private ScreenController startScreenControler;
    private BulletAppState bulletAppState;
    private BasicShadowRenderer bsr;
    private float steeringValue = 0;
    private float accelerationValue = 0;

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {

        //Configuring Game States
        bulletAppState = new BulletAppState();
        bulletAppState.setThreadingType(BulletAppState.ThreadingType.PARALLEL);
        inGameState = new InGameState();

        startScreenControler = new StartScreenState(this);
        NiftyJmeDisplay display = new NiftyJmeDisplay(assetManager, inputManager, audioRenderer, viewPort); //create jme-nifty-processor
        guiViewPort.addProcessor(display); //add it to your gui-viewport so that the processor will start working
        Nifty nifty = display.getNifty();
        nifty.fromXml("Interface/Start.xml", "hud", startScreenControler);









        //What are these?!
        rootNode.setShadowMode(ShadowMode.Off);
        bsr = new BasicShadowRenderer(assetManager, 256);
        bsr.setDirection(new Vector3f(-1, -1, -1).normalizeLocal());
        viewPort.addProcessor(bsr);
        stateManager.attach(bulletAppState);
        PhysicsTestHelper.createPhysicsTestWorld(rootNode, assetManager, bulletAppState.getPhysicsSpace());

        //Configuring Lights
        DirectionalLight dl = new DirectionalLight();
        dl.setDirection(new Vector3f(0.5f, -0.1f, 0.3f).normalizeLocal());
        rootNode.addLight(dl);

        //Configuring camera
        cam.setFrustumFar(150f);//whats this ?!
        flyCam.setMoveSpeed(10); // is this necessary ?!
        flyCam.setEnabled(false);

        stateManager.attach(inGameState);
        stateManager.attach((AbstractAppState)startScreenControler);

    }

    @Override
    public void simpleUpdate(float tpf) {
    }
}
