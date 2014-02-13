package mygame;

import com.jme3.audio.AudioNode;
import com.jme3.scene.Node;

public abstract class Weapon{
	FireBehaviour fireBehaviour;
	int bulletCount;
        AudioNode sound;
        private Node weaponNode;

	public Weapon(){
            sound = new AudioNode(assetManager,"Sounds/weapon1.wav",false);
	}
	public void fire(){
		if (bulletCount!=0){
			fireBehaviour.fire();
			bulletCount--;
		} else noBullet();
	}
	private void noBullet()
	{
		// play sound of no bullet for example or something else
	}
        
        public Node getWeaponNode() {
            return weaponNode;
        }
        

}
