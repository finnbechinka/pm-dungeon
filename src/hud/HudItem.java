package hud;

import com.badlogic.gdx.graphics.Texture;

import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IHUDElement;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;

public class HudItem implements IHUDElement{
	private Texture texture = null;
	private Point position = new Point(1f,1f);
	
	public HudItem(Texture t) {
		this.texture = t;
	}
	
	public void setPosition(Point pos) {
		this.position = pos;
	}
	
	@Override
	public Point getPosition() {
		return position;
	}

	@Override
	public Texture getTexture() {
		return texture;
	}
	
	@Override
	public float getWidth() {
		return .2f;
	}
	
	@Override
	public float getHeight() {
		return 2.5f;
	}
}
