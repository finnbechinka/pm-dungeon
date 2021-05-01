package entities.items;

import com.badlogic.gdx.graphics.Texture;

import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.DungeonWorld;
import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IDrawable;
import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IEntity;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;

public abstract class Item implements IDrawable, IEntity{
	protected Point position;
	protected DungeonWorld level;
	protected Texture texture;
	protected ItemState state;
	
	@Override
	public Point getPosition() {
		return this.position;
	}
	
	@Override
	public Texture getTexture() {
		return this.texture;
	}
	
	@Override
	public void update() {
		if(this.state == ItemState.ON_GROUND) {
			this.drawWithScaling(.5f, .5f);
		}
	}
	
	@Override
	public boolean deleteable() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void setLevel(DungeonWorld level) {
		this.level = level;
		if(level != null) {
			setRandomPosition();
			this.state = ItemState.ON_GROUND;
		}
	}
	
	public void setPosition(Point p) {
		this.position = p;
	}
	
	public void setRandomPosition() {
		this.position = new Point(level.getRandomPointInDungeon());
	}
	
	public void setState(ItemState state) {
		this.state = state;
	}
	
	public ItemState getState() {
		return this.state;
	}
}
