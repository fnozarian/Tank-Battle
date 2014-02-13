package mygame;

import com.jme3.app.Application;
import com.jme3.scene.Node;
/**
 *
 * @author vahid
 */
public class WeaponA extends Weapon {

    public WeaponA(Application app,Node rootNode,FireBehaviour fireBehaviour, int bulletCount) {
        super(fireBehaviour,bulletCount);
        this.fireBehaviour = new FireBehaviourA(app,rootNode);
    }
}
