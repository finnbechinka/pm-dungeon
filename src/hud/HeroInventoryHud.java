package hud;

import com.badlogic.gdx.graphics.Texture;

import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IHUDElement;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;
import entities.items.Item;
import program.Controller;

public class HeroInventoryHud implements IHUDElement{
	private Point position = new Point(2.4f, .5f);
	private HudItem[] items = new HudItem[5];
	private Controller mc;
	
	public HeroInventoryHud(Controller mc) {
		this.mc = mc;
	}
	
	@Override
	public Point getPosition() {
		return this.position;
	}

	@Override
	public Texture getTexture() {
		return new Texture("./assets/hud/playerinventory.png");
	}
	
	@Override
	public float getWidth() {
		return 2f;
	}
	
	@Override
	public float getHeight() {
		return 25f;
	}
	
	public void addHudItem(int pos, Item item) {
		HudItem huditem = new HudItem(item.getTexture());
		items[pos] = huditem;
		mc.addHudElement(huditem);
		if(pos == 0) {
			huditem.setPosition(new Point(2.5f, .575f));
		}else if(pos == 1) {
			huditem.setPosition(new Point(2.9f, .575f));
		}else if(pos == 2) {
			huditem.setPosition(new Point(3.3f, .575f));
		}else if(pos == 3) {
			huditem.setPosition(new Point(3.7f, .575f));
		}else if(pos == 4) {
			huditem.setPosition(new Point(4.1f, .575f));
		}
	}
	
	public void removeHudItem(int pos) {
		mc.removeHudElement(items[pos]);
		items[pos] = null;
	}
}
