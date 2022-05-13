package entities.characters;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;

import de.fhbielefeld.pmdungeon.vorgaben.graphic.Animation;

public class SnakeMonster extends Monster {

	public SnakeMonster() {
		super(75, .125f);
		this.minRange = 1.5;
		this.maxRange = 2.5;
		this.hp = baseHp;
		this.movementSpeed = baseMovementSpeed;
		log.info("new snake monster instance created");
	}

	@Override
	protected void createAnimations() {
		ArrayList<Texture> idle = new ArrayList<>();
		ArrayList<Texture> runLeft = new ArrayList<>();
		ArrayList<Texture> runRight = new ArrayList<>();

		idle.add(new Texture("./assets/textures/characters/snake/snake_right_1.png"));
		idle.add(new Texture("./assets/textures/characters/snake/snake_left_1.png"));

		runRight.add(new Texture("./assets/textures/characters/snake/snake_right_1.png"));
		runRight.add(new Texture("./assets/textures/characters/snake/snake_right_2.png"));
		runRight.add(new Texture("./assets/textures/characters/snake/snake_right_1.png"));
		runRight.add(new Texture("./assets/textures/characters/snake/snake_right_3.png"));
		runRight.add(new Texture("./assets/textures/characters/snake/snake_right_1.png"));
		runRight.add(new Texture("./assets/textures/characters/snake/snake_right_4.png"));
		runRight.add(new Texture("./assets/textures/characters/snake/snake_right_1.png"));
		runRight.add(new Texture("./assets/textures/characters/snake/snake_right_5.png"));

		runLeft.add(new Texture("./assets/textures/characters/snake/snake_left_1.png"));
		runLeft.add(new Texture("./assets/textures/characters/snake/snake_left_2.png"));
		runLeft.add(new Texture("./assets/textures/characters/snake/snake_left_1.png"));
		runLeft.add(new Texture("./assets/textures/characters/snake/snake_left_3.png"));
		runLeft.add(new Texture("./assets/textures/characters/snake/snake_left_1.png"));
		runLeft.add(new Texture("./assets/textures/characters/snake/snake_left_4.png"));
		runLeft.add(new Texture("./assets/textures/characters/snake/snake_left_1.png"));
		runLeft.add(new Texture("./assets/textures/characters/snake/snake_left_5.png"));

		animations.put("idle", new Animation(idle, 8));
		animations.put("runLeft", new Animation(runLeft, 8));
		animations.put("runRight", new Animation(runRight, 8));
	}

	public boolean isDead() {
		if (state == CharacterState.DEAD) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void update() {
		if (hp <= 0) {
			state = CharacterState.DEAD;
			log.info("this monster is now dead");
		} else {
			attackCooldown--;
			randomMovement();
		}

		this.draw();
	}
}
