package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.light.DirectionalLight;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.shadow.BasicShadowRenderer;
import com.jme3.app.state.AbstractAppState;

public class Main extends SimpleApplication {

    private AbstractAppState inGameState;
    private AbstractAppState mainScreenState;
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
        inGameState = new InGameState();
        mainScreenState = new MainScreenState();
        mainScreenState.setEnabled(false);//we jump into game at first in debug level
        bulletAppState = new BulletAppState();
        bulletAppState.setThreadingType(BulletAppState.ThreadingType.PARALLEL);
        stateManager.attach(inGameState);
        stateManager.attach(mainScreenState);
        stateManager.attach(bulletAppState);

        //What are these?!
        rootNode.setShadowMode(ShadowMode.Off);
        bsr = new BasicShadowRenderer(assetManager, 256);
        bsr.setDirection(new Vector3f(-1, -1, -1).normalizeLocal());
        viewPort.addProcessor(bsr);

        PhysicsTestHelper.createPhysicsTestWorld(rootNode, assetManager, bulletAppState.getPhysicsSpace());

        //Configuring Lights
        DirectionalLight dl = new DirectionalLight();
        dl.setDirection(new Vector3f(0.5f, -0.1f, 0.3f).normalizeLocal());
        rootNode.addLight(dl);
        
        //Configuring camera
        cam.setFrustumFar(150f);//whats this ?!
        flyCam.setMoveSpeed(10); // is this necessary ?!
        flyCam.setEnabled(false);
        
    }
    @Override
    public void simpleUpdate(float tpf) {
    }
}
