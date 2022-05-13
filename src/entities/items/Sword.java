package entities.items;

import com.badlogic.gdx.graphics.Texture;

public class Sword extends Weapon {

	public Sword() {
		super(150, 30, 90, 0, 2);
		this.texture = new Texture("./assets/textures/items/rsz_weapon_regular_sword.png");
	}

}
