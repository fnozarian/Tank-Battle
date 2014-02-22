package mygame;

import com.jme3.app.Application;
import com.jme3.math.Vector3f;

/**
 *
 * @author vahid
 */
public class SimpleFireBehaviour extends FireBehaviour {

    public SimpleFireBehaviour(Application app) {
        super(app);
    }

    @Override
    public void shoot(Weapon weapon,Vector3f location, Vector3f direction,BulletBuilder bulletCreator) {
        Bullet b = bulletCreator.buildBullet(direction);
        b.setLocalTranslation(location);
        b.addControl();
        rootNode.attachChild(b.getGeometry());
    }

    @Override
    protected void initBulletsPerFire() {
         bulletsPerFire = Integer.parseInt(confs.getProperty("SimpleFireBehaviourBulletsPerFire").toString());
    }

}
