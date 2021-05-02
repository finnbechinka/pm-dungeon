package entities.items;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;

import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IDrawable;
import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IEntity;

public class Bag<T> extends Item implements IDrawable, IEntity {
	private ArrayList<Item> inventory = new ArrayList<>();
	private int inventorySize = 3;
	public final Class<T> typeParameterClass;

	public Bag(Class<T> typeParameterClass) {
		this.typeParameterClass = typeParameterClass;
		this.texture = new Texture("./assets/textures/items/crate.png");
	}

	public ArrayList<Item> getInventory() {
		return this.inventory;
	}

	public int getBagSize() {
		return this.inventorySize;
	}
	
	public boolean insert(Item item) {
		if(item.getClass().getSuperclass() == typeParameterClass) {
			inventory.add(item);
			return true;
		}else {
			return false;
		}
	}
}
