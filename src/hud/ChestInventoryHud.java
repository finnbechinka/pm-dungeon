package hud;

import com.badlogic.gdx.graphics.Texture;

import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IHUDElement;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;
import entities.items.Item;
import program.Controller;

public class ChestInventoryHud implements IHUDElement{
	private Point position = new Point(5.5f, 3.5f);
	private HudItem[] items = new HudItem[4];
	private Controller mc;
	
	public ChestInventoryHud(Controller mc) {
		this.mc = mc;
	}
	
	@Override
	public Point getPosition() {
		return this.position;
	}

	@Override
	public Texture getTexture() {
		return new Texture("./assets/hud/chestinventory.png");
	}
	
	@Override
	public float getWidth() {
		return .775f;
	}
	
	@Override
	public float getHeight() {
		return 25;
	}
	
	public void addHudItem(int pos, Item item) {
		HudItem huditem = new HudItem(item.getTexture());
		items[pos] = huditem;
		mc.addHudElement(huditem);
		if(pos == 0) {
			huditem.setPosition(new Point(5.6f, 3.575f));
		}else if(pos == 1) {
			huditem.setPosition(new Point(6f, 3.575f));
		}else if(pos == 2) {
			huditem.setPosition(new Point(5.6f, 3.775f));
		}else if(pos == 3) {
			huditem.setPosition(new Point(6f, 3.775f));
		}
	}
	
	public void removeHudItem(int pos) {
		mc.removeHudElement(items[pos]);
		items[pos] = null;
	}

}
