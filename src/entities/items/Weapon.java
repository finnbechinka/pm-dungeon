package entities.items;

import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IDrawable;
import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IEntity;

public class Weapon extends Item implements IDrawable, IEntity{
	protected double dmg;
	protected int attackCooldown;
	protected int remainingCooldown;
	
	public Weapon(double dmg, int attackCooldown) {
		this.dmg = dmg;
		this.attackCooldown = attackCooldown;
	}

}
