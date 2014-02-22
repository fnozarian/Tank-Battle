package mygame;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

public abstract class FireBehaviour {

    protected SimpleApplication app;
    protected Node rootNode;
    protected int bulletsPerFire;
    protected GameConfigurations confs;
    protected Weapon weapon;
    

    public FireBehaviour(Application app) {
        this.app = (SimpleApplication) app;
        this.rootNode = this.app.getRootNode();
        confs = GameConfigurations.getInstance(app);
        initBulletsPerFire();
        
    }
        private void updateBulletCount(Weapon weapon){
        weapon.decreaseBulletCount(bulletsPerFire);
        
    }
    final public void fire(Weapon weapon,Vector3f location, Vector3f direction, BulletBuilder bulletCreator){
        updateBulletCount(weapon);//then decrease bullet count
        shoot(weapon,location,direction,bulletCreator);// first shoot the bullet
        
    }
    protected abstract void shoot(Weapon weapon,Vector3f location, Vector3f direction, BulletBuilder bulletCreator);
    protected abstract void initBulletsPerFire();
}
