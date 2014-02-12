/*
 * Copyright (c) 2009-2012 jMonkeyEngine
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of 'jMonkeyEngine' nor the names of its contributors
 *   may be used to endorse or promote products derived from this software
 *   without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.TextureKey;
import com.jme3.audio.AudioNode;
import com.jme3.bounding.BoundingBox;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.PhysicsCollisionObject;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.control.VehicleControl;
import com.jme3.bullet.objects.VehicleWheel;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.font.BitmapText;
import com.jme3.input.ChaseCamera;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.math.Matrix3f;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.CameraControl;
import com.jme3.scene.shape.Sphere;
import com.jme3.shadow.BasicShadowRenderer;
import com.jme3.texture.Texture;

public class TestFancyCar extends SimpleApplication implements ActionListener {

    private BulletAppState bulletAppState;
    private VehicleControl player;
    private VehicleWheel fr, fl, br, bl;
    private Node node_fr, node_fl, node_br, node_bl;
    private float wheelRadius;
    private float steeringValue = 0;
    private float accelerationValue = 0;
    private Spatial spaceCraft;
    private PhysicsHoverControl hoverControl;
    private AudioNode tankIdleSound;
    private AudioNode weaponSound1;
    private static Sphere bullet;
    private static SphereCollisionShape bulletCollisionShape;
    private Material mat2;
    private CameraNode camNode;
    
    public static void main(String[] args) {
        TestFancyCar app = new TestFancyCar();
        app.start();
    }
    private BasicShadowRenderer bsr;

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

    @Override
    public void simpleInitApp() {
        
        bulletAppState = new BulletAppState();
        bulletAppState.setThreadingType(BulletAppState.ThreadingType.PARALLEL);
        stateManager.attach(bulletAppState);
        
        bullet = new Sphere(32, 32, 0.4f, true, false);
        bullet.setTextureMode(Sphere.TextureMode.Projected);
        bulletCollisionShape = new SphereCollisionShape(0.4f);
        
        initMaterial();
        initCrossHairs();

        //What are these?!
        rootNode.setShadowMode(ShadowMode.Off);
        bsr = new BasicShadowRenderer(assetManager, 256);
        bsr.setDirection(new Vector3f(-1, -1, -1).normalizeLocal());
        viewPort.addProcessor(bsr);
        
//        if (settings.getRenderer().startsWith("LWJGL")) {
//            BasicShadowRenderer bsr = new BasicShadowRenderer(assetManager, 512);
//            bsr.setDirection(new Vector3f(-0.5f, -0.3f, -0.3f).normalizeLocal());
//            viewPort.addProcessor(bsr);
//        }
        cam.setFrustumFar(150f);
        flyCam.setMoveSpeed(10);

        setupKeys();
        PhysicsTestHelper.createPhysicsTestWorld(rootNode, assetManager, bulletAppState.getPhysicsSpace());
//        setupFloor();
        buildPlayer();

        DirectionalLight dl = new DirectionalLight();
        dl.setDirection(new Vector3f(-0.5f, -1f, -0.3f).normalizeLocal());
        rootNode.addLight(dl);

        dl = new DirectionalLight();
        dl.setDirection(new Vector3f(0.5f, -0.1f, 0.3f).normalizeLocal());
        rootNode.addLight(dl);
    }

    private PhysicsSpace getPhysicsSpace() {
        return bulletAppState.getPhysicsSpace();
    }

//    public void setupFloor() {
//        Material mat = assetManager.loadMaterial("Textures/Terrain/BrickWall/BrickWall.j3m");
//        mat.getTextureParam("DiffuseMap").getTextureValue().setWrap(WrapMode.Repeat);
////        mat.getTextureParam("NormalMap").getTextureValue().setWrap(WrapMode.Repeat);
////        mat.getTextureParam("ParallaxMap").getTextureValue().setWrap(WrapMode.Repeat);
//
//        Box floor = new Box(Vector3f.ZERO, 140, 1f, 140);
//        floor.scaleTextureCoordinates(new Vector2f(112.0f, 112.0f));
//        Geometry floorGeom = new Geometry("Floor", floor);
//        floorGeom.setShadowMode(ShadowMode.Receive);
//        floorGeom.setMaterial(mat);
//
//        PhysicsNode tb = new PhysicsNode(floorGeom, new MeshCollisionShape(floorGeom.getMesh()), 0);
//        tb.setLocalTranslation(new Vector3f(0f, -6, 0f));
////        tb.attachDebugShape(assetManager);
//        rootNode.attachChild(tb);
//        getPhysicsSpace().add(tb);
//    }
    private Geometry findGeom(Spatial spatial, String name) {
        if (spatial instanceof Node) {
            Node node = (Node) spatial;
            for (int i = 0; i < node.getQuantity(); i++) {
                Spatial child = node.getChild(i);
                Geometry result = findGeom(child, name);
                if (result != null) {
                    return result;
                }
            }
        } else if (spatial instanceof Geometry) {
            if (spatial.getName().startsWith(name)) {
                return (Geometry) spatial;
            }
        }
        return null;
    }

    private void buildPlayer() {
        float stiffness = 120.0f;//200=f1 car
        float compValue = 0.2f; //(lower than damp!)
        float dampValue = 0.3f;
        final float mass = 400;

        spaceCraft = assetManager.loadModel("Models/HoverTank/Tank2.mesh.xml");
        CollisionShape colShape = CollisionShapeFactory.createDynamicMeshShape(spaceCraft);
        spaceCraft.setShadowMode(ShadowMode.CastAndReceive);
        spaceCraft.setLocalTranslation(new Vector3f(-60, 14, -23));
        spaceCraft.setLocalRotation(new Quaternion(new float[]{0, 0.01f, 0}));

        hoverControl = new PhysicsHoverControl(colShape, 500);
        hoverControl.setCollisionGroup(PhysicsCollisionObject.COLLISION_GROUP_02);
        spaceCraft.addControl(hoverControl);

        camNode = new CameraNode("camNode", cam);
        //Setting the direction to Spatial to camera, this means the camera will copy the movements of the Node
        camNode.setControlDir(CameraControl.ControlDirection.SpatialToCamera);
        camNode.setLocalTranslation(0f, 4f, -12f);
        Node spaceCraftNode = (Node) spaceCraft;
        spaceCraftNode.attachChild(camNode);

        tankIdleSound = new AudioNode(assetManager, "Sounds/propeller-plane-idle.wav", false);
        tankIdleSound.setLooping(true);
        spaceCraftNode.attachChild(tankIdleSound);
        tankIdleSound.play();
        
        weaponSound1 = new AudioNode(assetManager,"Sounds/weapon1.wav",false);
        spaceCraftNode.attachChild(weaponSound1);
        
        rootNode.attachChild(spaceCraftNode);
        getPhysicsSpace().add(hoverControl);

        flyCam.setEnabled(false);
    }

    public void onAction(String binding, boolean value, float tpf) {
        if (binding.equals("Lefts")) {
            hoverControl.steer(value ? 50f : 0);
        } else if (binding.equals("Rights")) {
            hoverControl.steer(value ? -50f : 0);
        } else if (binding.equals("Ups")) {
            hoverControl.accelerate(value ? 100f : 0);
        } else if (binding.equals("Downs")) {
            hoverControl.accelerate(value ? -100f : 0);
        } else if (binding.equals("Reset")) {
            if (value) {
                System.out.println("Reset");
                hoverControl.setPhysicsLocation(new Vector3f(-140, 14, -23));
                hoverControl.setPhysicsRotation(new Matrix3f());
                hoverControl.clearForces();
            } else {
            }
        } else if (binding.equals("Space") && value) {
            makeMissile();
        }
    }

    private void makeMissile() {
        Geometry bulletg = new Geometry("bullet", bullet);
        bulletg.setMaterial(mat2);
        bulletg.setShadowMode(ShadowMode.CastAndReceive);
        bulletg.setLocalTranslation(camNode.getCamera().getLocation());

        SphereCollisionShape bulletCollisionShape = new SphereCollisionShape(0.4f);
        RigidBodyControl bulletNode = new BombControl(assetManager, bulletCollisionShape, 0.001f);
        bulletNode.setLinearVelocity(camNode.getCamera().getDirection().mult(25));
        bulletg.addControl(bulletNode);
        rootNode.attachChild(bulletg);
        getPhysicsSpace().add(bulletNode);
        
        weaponSound1.play();
    }
    
    public void initMaterial() {
        mat2 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        TextureKey key2 = new TextureKey("Textures/Terrain/Rock/Rock.PNG");
        key2.setGenerateMips(true);
        Texture tex2 = assetManager.loadTexture(key2);
        mat2.setTexture("ColorMap", tex2);
    }

    protected void initCrossHairs() {
        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        BitmapText ch = new BitmapText(guiFont, false);
        ch.setSize(guiFont.getCharSet().getRenderedSize() * 2);
        ch.setText("+"); // crosshairs
        ch.setLocalTranslation( // center
                settings.getWidth() / 2 - guiFont.getCharSet().getRenderedSize() / 3 * 2,
                settings.getHeight() / 2 + ch.getLineHeight() / 2, 0);
        guiNode.attachChild(ch);
    }

    @Override
    public void simpleUpdate(float tpf) {
    }
}
