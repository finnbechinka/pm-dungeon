package entities.items;

import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IDrawable;
import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IEntity;

public abstract class Weapon extends Item implements IDrawable, IEntity {
	protected double dmg;
	protected int attackCooldown;
	protected int remainingCooldown;

	public Weapon(double dmg, int attackCooldown) {
		this.dmg = dmg;
		this.attackCooldown = attackCooldown;
	}

	public int getAttackCooldown() {
		return this.attackCooldown;
	}

	public double getDmg() {
		return dmg;
	}

}
