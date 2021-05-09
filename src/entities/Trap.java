package entities;

import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.DungeonWorld;
import de.fhbielefeld.pmdungeon.vorgaben.graphic.Animation;
import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IAnimatable;
import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IEntity;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;

public abstract class Trap implements IAnimatable, IEntity{
	protected Point pos;
	protected DungeonWorld level;
	protected boolean active = true;
	protected boolean visible = true;
	
	@Override
	public Point getPosition() {
		return this.pos;
	}

	@Override
	public Animation getActiveAnimation() {
		return null;
	}

	@Override
	public void update() {
	this.drawWithScaling(.5f, .5f);
	}

	@Override
	public boolean deleteable() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void setLevel(DungeonWorld level) {
		this.level = level;
		setRandomPosition();
	}

	public void setPosition(Point p) {
		this.pos = p;
	}

	public void setRandomPosition() {
		this.pos = new Point(level.getRandomPointInDungeon());
	}
	
	public boolean isActive() {
		return active;
	}
	
	public void setVisible(boolean v) {
		this.visible = v;
	}
	
	public abstract void activate(entities.characters.Character c);
}
