package hud;

import com.badlogic.gdx.graphics.Texture;

import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IHUDElement;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;
import entities.items.Item;
import program.Controller;

public class HeroEquipmentHud implements IHUDElement{
	private Point position = new Point(4.75f, .5f);
	private HudItem weapon = null;
	private HudItem armor = null;
	private Controller mc;
	
	public HeroEquipmentHud(Controller mc) {
		this.mc = mc;
	}
	
	@Override
	public Point getPosition() {
		return this.position;
	}

	@Override
	public Texture getTexture() {
		return new Texture("./assets/hud/playerinventory3.png");
	}
	
	@Override
	public float getWidth() {
		return .8f;
	}
	
	@Override
	public float getHeight() {
		return 10.25f;
	}
	
	public void changeArmorHudItem(Item item) {
		HudItem huditem = new HudItem(item.getTexture());
		armor = huditem;
		mc.addHudElement(huditem);
		huditem.setPosition(new Point(4.85f, .575f));
	}
	
	public void changeWeaponHudItem(Item item) {
		HudItem huditem = new HudItem(item.getTexture());
		weapon = huditem;
		mc.addHudElement(huditem);
		huditem.setPosition(new Point(5.25f, .575f));
	}
	
	public void removeArmorHudItem() {
		mc.removeHudElement(armor);
		armor = null;
	}
	
	public void removeWeaponHudItem() {
		mc.removeHudElement(weapon);
		weapon = null;
	}
}
