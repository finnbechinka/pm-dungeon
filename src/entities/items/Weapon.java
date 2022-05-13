package entities.items;

import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IDrawable;
import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IEntity;

public abstract class Weapon extends Item implements IDrawable, IEntity {
	protected double dmg;
	protected int attackCooldown;
	protected int remainingCooldown;
	protected double minRange;
	protected double maxRange;
	protected int accuracy;

	public Weapon(double dmg, int attackCooldown, int acc, double minRange, double maxRange) {
		this.dmg = dmg;
		this.attackCooldown = attackCooldown;
		this.minRange = minRange;
		this.maxRange = maxRange;
		this.accuracy = acc;
	}

	public int getAttackCooldown() {
		return this.attackCooldown;
	}

	public double getDmg() {
		return dmg;
	}
	
	public double getMinRange() {
		return this.minRange;
	}
	
	public double getMaxRange() {
		return this.maxRange;
	}
	
	public int getAccuracy() {
		return this.accuracy;
	}

}
