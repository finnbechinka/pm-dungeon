package entities.items;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;

import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IDrawable;
import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IEntity;
import hud.BagInventoryHud;
import hud.HudItem;
import program.Controller;

public class Bag<T extends Item> extends Item implements IDrawable, IEntity {
	@SuppressWarnings("unchecked")
	private T[] inventory = (T[]) new Item[3];
	private BagInventoryHud bih = null;
	private Controller mc;

	public Bag(Controller mc) {
		this.mc = mc;
		this.texture = new Texture("./assets/textures/items/crate.png");
	}

	public T[] getInventory() {
		return this.inventory;
	}
	
	public void showHud() {
		if(bih == null) {
			bih = new BagInventoryHud(mc);
			for(int i = 0; i < inventory.length; i++) {
				if(inventory[i] != null)
				bih.addHudItem(i, new HudItem(inventory[i].getTexture()));
			}
		}else {
			bih.removeHudItem(0);
			bih.removeHudItem(1);
			bih.removeHudItem(2);
			for(int i = 0; i < inventory.length; i++) {
				if(inventory[i] != null)
				bih.addHudItem(i, new HudItem(inventory[i].getTexture()));
			}
		}
		mc.addHudElement(bih);
	}
	
	public void removeHud() {
		if(bih != null) {
			bih.removeHudItem(0);
			bih.removeHudItem(1);
			bih.removeHudItem(2);
			mc.removeHudElement(this.bih);
		}
	}
	
	public void insert(int pos, Item item) {
		inventory[pos] = (T) item;
	}
}
