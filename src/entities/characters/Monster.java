package entities.characters;

import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.utils.Array;

import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.tiles.Tile;
import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.tiles.Tile.Direction;
import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.tiles.TileHeuristic;
import de.fhbielefeld.pmdungeon.vorgaben.graphic.Animation;
import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IAnimatable;
import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IEntity;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;

public abstract class Monster extends Character implements IAnimatable, IEntity {
	private int movementCooldown;

	public Monster(double baseHp, float baseMovementSpeed) {
		super(baseHp, baseMovementSpeed);
		this.minRange = 0;
		this.maxRange = 1.1;
		movementCooldown = 30;
	}

	@Override
	public boolean deleteable() {
		// idk but this shit aint working right
		if(state == CharacterState.DEAD) {
			return true;
		}else {
			return false;
		}
	}

	protected void randomMovement() {
		if (movementCooldown <= 0) {
			Random direction = new Random();
			switch (direction.nextInt(5)) {
			case 0:
				state = CharacterState.IDLE;
				break;
			case 1:
				state = CharacterState.RUNNING_FORWARDS;
				break;
			case 2:
				state = CharacterState.RUNNING_BACKWARDS;
				break;
			case 3:
				state = CharacterState.RUNNING_RIGHT;
				break;
			case 4:
				state = CharacterState.RUNNING_LEFT;
				break;
			}
			movementCooldown = direction.nextInt(20) + 10;
		} else {
			movementCooldown--;
		}
		Point newPosition = new Point(this.position);
		if (state == CharacterState.RUNNING_FORWARDS) {
			newPosition.y += movementSpeed;
		} else if (state == CharacterState.RUNNING_BACKWARDS) {
			newPosition.y -= movementSpeed;
		} else if (state == CharacterState.RUNNING_RIGHT) {
			newPosition.x += movementSpeed;
		} else if (state == CharacterState.RUNNING_LEFT) {
			newPosition.x -= movementSpeed;
		}
		if (level.isTileAccessible(newPosition))
			this.position = newPosition;
	}

	@Override
	public Animation getActiveAnimation() {
		if (state == CharacterState.RUNNING_FORWARDS || state == CharacterState.RUNNING_RIGHT) {
			return animations.get("runRight");
		} else if (state == CharacterState.RUNNING_BACKWARDS || state == CharacterState.RUNNING_LEFT) {
			return animations.get("runLeft");
		} else {
			return animations.get("idle");
		}
	}

	@Override
	public double attack() {
		Random rdm = new Random();
		if (rdm.nextBoolean() && attackCooldown <= 0) {
			log.info("this monster attacks");
			attackCooldown = 20;
			return 25;
		} else {
			attackCooldown = 10;
			return 0;
		}
	}

	protected void lookForHero() {
		Tile myTile = this.level.getTileAt((int)this.position.x, (int)this.position.y);
		Tile heroTile = this.level.getTileAt((int)mc.getHero().getPosition().x, (int)mc.getHero().getPosition().y);
		System.out.println(this.checkForObstruction(myTile, heroTile));

	}

	protected boolean checkForObstruction(Tile start, Tile end) {
		
		return true;
	}
}
