package mygame;

import com.jme3.app.Application;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;

/**
 *
 * @author vahid
 */
public class TripleFireBehaviour extends FireBehaviour {

    public TripleFireBehaviour(Application app) {
        super(app);
    }

    @Override
    public void shoot(Weapon weapon,Vector3f location, Vector3f direction,BulletBuilder bulletBuilder) {
        Bullet b1 = bulletBuilder.buildBullet(direction.normalize());
       //  Quaternion roll180 = new Quaternion(); 
      //   roll180.fromAngleAxis( FastMath.PI , new Vector3f(0,0,1) ); 
         
    //roll180.fromAngleAxis( FastMath.PI , new Vector3f(0,0,1) ); 
        
        Bullet b2 = bulletBuilder.buildBullet(direction.normalize().add(new Vector3f(0.2f,0,0)));
        Bullet b3 = bulletBuilder.buildBullet(direction.normalize().add(new Vector3f(-0.2f,0,0)));
        b1.setLocalTranslation(location);
        b2.setLocalTranslation(location.add(direction.normalize().mult(8)));
        b3.setLocalTranslation(location.add(direction.normalize().mult(16)));
        b1.addControl();
        b2.addControl();
        b3.addControl();
        rootNode.attachChild(b1.getGeometry());
        rootNode.attachChild(b2.getGeometry());
        rootNode.attachChild(b3.getGeometry());
      //  b.setLocalTranslation(location);
      //  b.addControl();
      //  rootNode.attachChild(b.getGeometry());
    }

    @Override
    protected void initBulletsPerFire() {
         bulletsPerFire = Integer.parseInt(confs.getProperty("TripleFireBehaviourBulletsPerFire").toString());
    }

}
