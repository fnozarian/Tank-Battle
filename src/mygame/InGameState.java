
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
import com.jme3.math.Matrix3f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

/**
 *
 * @author MR Blue
 */
public class InGameState extends AbstractAppState implements ActionListener{

    private SimpleApplication app;
    private Node              rootNode;
    private AssetManager      assetManager;
    private AppStateManager   stateManager;
    private InputManager inputManager;
    private BitmapFont guiFont;
    private Tank tank1;
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app); 
        
        this.app = (SimpleApplication)app;
        this.rootNode = this.app.getRootNode();
        this.assetManager = this.app.getAssetManager();
        this.stateManager = this.app.getStateManager();
        this.guiFont = this.assetManager.loadFont("Interface/Fonts/Default.fnt");
        
        
        setupKeys();
        initCrossHairs();
        
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);
        
    }
    
    protected void initCrossHairs() {
        
        guiFont =  assetManager.loadFont("Interface/Fonts/Default.fnt");
        BitmapText ch = new BitmapText(guiFont, false);
        ch.setSize(guiFont.getCharSet().getRenderedSize() * 2);
        ch.setText("+"); // crosshairs
        ch.setLocalTranslation( // center
                settings.getWidth() / 2 - guiFont.getCharSet().getRenderedSize() / 3 * 2,
                settings.getHeight() / 2 + ch.getLineHeight() / 2, 0);
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
        
        if (name.equals("Lefts")) {
            hoverControl.steer(isPressed ? 50f : 0);
        } else if (name.equals("Rights")) {
            hoverControl.steer(isPressed ? -50f : 0);
        } else if (name.equals("Ups")) {
            hoverControl.accelerate(isPressed ? 100f : 0);
        } else if (name.equals("Downs")) {
            hoverControl.accelerate(isPressed ? -100f : 0);
        } else if (name.equals("Reset")) {
            if (isPressed) {
                System.out.println("Reset");
                hoverControl.setPhysicsLocation(new Vector3f(-140, 14, -23));
                hoverControl.setPhysicsRotation(new Matrix3f());
                hoverControl.clearForces();
            } else {
            }
        } else if (name.equals("Space") && isPressed) {
            //makeMissile();
        }
    }
    
    
}
