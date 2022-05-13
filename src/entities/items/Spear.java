package entities.items;

import com.badlogic.gdx.graphics.Texture;

public class Spear extends Weapon{
	public Spear() {
		super(200, 40, 75, 1.5, 3);
		this.texture = new Texture("./assets/textures/items/weapon_spear.png");
		this.yscale = .75f;
		this.xscale = .25f;
	}
}
