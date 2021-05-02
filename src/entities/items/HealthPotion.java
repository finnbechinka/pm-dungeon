package entities.items;

import com.badlogic.gdx.graphics.Texture;

import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IDrawable;
import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IEntity;

public class HealthPotion extends Potion implements IDrawable, IEntity {

	public HealthPotion() {
		this.texture = new Texture("./assets/textures/items/healthPotion.png");
	}

	@Override
	public void effect() {
		// TODO Auto-generated method stub

	}

}
