package entities.items;

import com.badlogic.gdx.graphics.Texture;

import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IDrawable;
import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IEntity;

public class Chestplate extends Armor implements IDrawable, IEntity {

	public Chestplate() {
		super(0.25);
		this.texture = new Texture("./assets/textures/items/chestplate.png");
	}

}
