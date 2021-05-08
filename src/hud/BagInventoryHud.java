package hud;

import com.badlogic.gdx.graphics.Texture;

import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IHUDElement;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;
import program.Controller;

public class BagInventoryHud implements IHUDElement{
	private Point position = new Point(.7f, 3.5f);
	private HudItem[] items = new HudItem[3];
	private Controller mc;
	
	public BagInventoryHud(Controller mc) {
		this.mc = mc;
	}
	
	@Override
	public Point getPosition() {
		return this.position;
	}

	@Override
	public Texture getTexture() {
		return new Texture("./assets/hud/baginventory.png");
	}
	
	@Override
	public float getWidth() {
		return .775f;
	}
	
	@Override
	public float getHeight() {
		return 13f;
	}
	
	public void addHudItem(int pos, HudItem item) {
		items[pos] = item;
		mc.addHudElement(item);
		if(pos == 0) {
			item.setPosition(new Point(.75f, 3.575f));
		}else if(pos == 1) {
			item.setPosition(new Point(1.2f, 3.575f));
		}else if(pos == 2) {
			item.setPosition(new Point(1.6f, 3.775f));
		}
	}
	
	public void removeHudItem(int pos) {
		mc.removeHudElement(items[pos]);
		items[pos] = null;
	}

}
