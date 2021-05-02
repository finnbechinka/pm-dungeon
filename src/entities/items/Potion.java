package entities.items;

import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IDrawable;
import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IEntity;

public abstract class Potion extends Item implements IDrawable, IEntity {

	public abstract void effect();
}
