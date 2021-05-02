package entities.items;

import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IDrawable;
import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IEntity;

public abstract class Armor extends Item implements IDrawable, IEntity {
	protected double damageModifier;

	public Armor(double dmgMod) {
		this.damageModifier = dmgMod;
	}

	public double getDmgMod() {
		return this.damageModifier;
	}
}
