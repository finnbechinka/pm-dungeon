package entities;

import java.util.ArrayList;
import java.util.Random;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;

import de.fhbielefeld.pmdungeon.vorgaben.graphic.Animation;
import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IAnimatable;
import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IEntity;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;

/**
 * Represents the games hero.
 * 
 * @author Finn Bechinka
 *
 */
public class Hero extends Character implements IAnimatable, IEntity{
		
	/**
	 * Constructor.
	 * 
	 * Animations get build.
	 * The hero state gets initialised as IDLE.
	 */
	public Hero() {
		super(100, .1f);
		this.hp = baseHp;
		this.movementSpeed = baseMovementSpeed;
	}
	
	@Override
	protected void createAnimations() {
		ArrayList<Texture> idle = new ArrayList<>();
		idle.add(new Texture("./assets/textures/characters/hero/hero_idle_1.png"));
		idle.add(new Texture("./assets/textures/characters/hero/hero.png"));
		idle.add(new Texture("./assets/textures/characters/hero/hero_idle_2.png"));
		idle.add(new Texture("./assets/textures/characters/hero/hero.png"));
		idle.add(new Texture("./assets/textures/characters/hero/hero_idle_1.png"));
		animations.put("idle", new Animation(idle,8));
		
		ArrayList<Texture> runLeft = new ArrayList<>();
		runLeft.add(new Texture("./assets/textures/characters/hero/hero_run_left_1.png"));
		runLeft.add(new Texture("./assets/textures/characters/hero/hero_run_left_2.png"));
		animations.put("runLeft", new Animation(runLeft, 8));
		
		ArrayList<Texture> runRight = new ArrayList<>();
		runRight.add(new Texture("./assets/textures/characters/hero/hero_run_right_1.png"));
		runRight.add(new Texture("./assets/textures/characters/hero/hero_run_right_2.png"));
		animations.put("runRight", new Animation(runRight, 8));
	}

	/**
	 * Returns the appropriate animation depending on the heros state.
	 * 
	 * @return the appropriate animation depending on the heros state
	 */
	@Override
	public Animation getActiveAnimation() {
		if(state == CharacterState.RUNNING_FORWARDS || state == CharacterState.RUNNING_RIGHT){
			return animations.get("runRight");
		}else if(state == CharacterState.RUNNING_BACKWARDS || state == CharacterState.RUNNING_LEFT){
			return animations.get("runLeft");
		}else{
			return animations.get("idle");
		}
	}

	/**
	 * TODO because I don't know yet
	 */
	@Override
	public boolean deleteable() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Gets called once per frame.
	 * 
	 * Checks for player movement input and updates the heros position if the movement is valid.
	 */
	@Override
	public void update() {
		if(hp <= 0) {
			log.info("GAME OVER");
			state = CharacterState.DEAD;
		}else {
			attackCooldown--;
			
			//set movement speed
			if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
				log.finest("Input: SHIFT_LEFT");
				movementSpeed = 0.175f;
			}else {
				movementSpeed = 0.1f;
			}
			
			//if player tries to move and movement is valid update position
			state = CharacterState.IDLE;
			Point newPosition = new Point(this.position);
			if(Gdx.input.isKeyPressed(Input.Keys.W)) {
				log.finest("Input: W");
				state = CharacterState.RUNNING_FORWARDS;
				newPosition.y += movementSpeed;			
			}
			if(Gdx.input.isKeyPressed(Input.Keys.S)) {
				log.finest("Input: S");
				state = CharacterState.RUNNING_BACKWARDS;
				newPosition.y -= movementSpeed;			
			}
			if(Gdx.input.isKeyPressed(Input.Keys.D)) {
				log.finest("Input: D");
				state = CharacterState.RUNNING_RIGHT;
				newPosition.x += movementSpeed;			
			}
			if(Gdx.input.isKeyPressed(Input.Keys.A)) {
				log.finest("Input: A");
				state = CharacterState.RUNNING_LEFT;
				newPosition.x -= movementSpeed;			
			}
			
			if(level.isTileAccessible(newPosition)) {
				this.position = newPosition;				
			}
		}
		
		this.draw();
	}

	@Override
	public int attack() {
		Random rdm = new Random();
		if(rdm.nextBoolean() && attackCooldown <= 0) {
			attackCooldown = 20;
			//heal(25);
			return 50;
		}else if(attackCooldown <= 0) {
			attackCooldown = 10;
			return 10;
		}else {
			attackCooldown = 5;
			return 0;
		}
	}
	
	@Override
	public void damage(int dmg) {
		if(dmg > 0) {
			this.hp -= dmg;
			log.info("HERO TOOK " + dmg + " DMG, HE NOW HAS " + this.hp + " HP!");
			Point newPosition = new Point(this.position);
			float knockbackDist = 1f;
			do {
				Random direction = new Random();
				switch(direction.nextInt(4)) {
				case 0:
					newPosition.y += knockbackDist;
					break;
				case 1: 
					newPosition.y -= knockbackDist;
					break;
				case 2:
					newPosition.x += knockbackDist;
					break;
				case 3:
					newPosition.x -= knockbackDist;
					break;
				}
			}while(!level.isTileAccessible(newPosition));
			this.position = newPosition;
		}
	}

	@Override
	public boolean isDead() {
		if(state == CharacterState.DEAD) {
			return true;
		}else {
			return false;
		}
	}
	
	public void setHp(double hp) {
		this.hp = hp;
	}
	
	public double getBaseHp() {
		return this.baseHp;
	}
}
