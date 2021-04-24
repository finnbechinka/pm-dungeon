package program;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;

import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.DungeonWorld;
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
public class Hero implements IAnimatable, IEntity{
	private HashMap<String, Animation> animations = new HashMap<>();
	private Point position;
	private DungeonWorld level;
	private HeroState state;
	
	/**
	 * Constructor.
	 * 
	 * Animations get build.
	 * The hero state gets initialised as IDLE.
	 */
	public Hero() {
		createAnimations();
		state = HeroState.IDLE;
	}
	
	private void createAnimations() {
		ArrayList<Texture> idle = new ArrayList<>();
		idle.add(new Texture("./assets/textures/characters/hero_idle_1.png"));
		idle.add(new Texture("./assets/textures/characters/hero.png"));
		idle.add(new Texture("./assets/textures/characters/hero_idle_2.png"));
		idle.add(new Texture("./assets/textures/characters/hero.png"));
		idle.add(new Texture("./assets/textures/characters/hero_idle_1.png"));
		animations.put("idle", new Animation(idle,8));
		
		ArrayList<Texture> runLeft = new ArrayList<>();
		runLeft.add(new Texture("./assets/textures/characters/hero_run_left_1.png"));
		runLeft.add(new Texture("./assets/textures/characters/hero_run_left_2.png"));
		animations.put("runLeft", new Animation(runLeft, 8));
		
		ArrayList<Texture> runRight = new ArrayList<>();
		runRight.add(new Texture("./assets/textures/characters/hero_run_right_1.png"));
		runRight.add(new Texture("./assets/textures/characters/hero_run_right_2.png"));
		animations.put("runRight", new Animation(runRight, 8));
	}
	
	/**
	 * Sets the level the hero is in.
	 * Also calls {@link #findRandomPostion()}.
	 * 
	 * @param level - of type DungeonWorld
	 * 
	 */
	public void setLevel(DungeonWorld level) {
		this.level = level;
		findRandomPostion();
	}
	
	/**
	 * Finds and sets a random valid position in the dungeon for the hero.
	 */
	public void findRandomPostion(){
		this.position = new Point(level.getRandomPointInDungeon());
	}
	
	/**
	 * Returns the current position of the hero.
	 * 
	 * @return the current position of the hero
	 */
	@Override
	public Point getPosition() {
		return this.position;
	}

	/**
	 * Returns the appropriate animation depending on the heros state.
	 * 
	 * @return the appropriate animation depending on the heros state
	 */
	@Override
	public Animation getActiveAnimation() {
		if(state == HeroState.RUNNING_FORWARDS || state == HeroState.RUNNING_RIGHT){
			return animations.get("runRight");
		}else if(state == HeroState.RUNNING_BACKWARDS || state == HeroState.RUNNING_LEFT){
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
		//set movement speed
		float movementSpeed;
		if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
			movementSpeed = 0.175f;
		}else {
			movementSpeed = 0.1f;
		}
		
		//if player tries to move and movement is valid update position
		Point newPosition = new Point(this.position);
		if(Gdx.input.isKeyPressed(Input.Keys.W)) {
			state = HeroState.RUNNING_FORWARDS;
			newPosition.y += movementSpeed;			
		}else if(Gdx.input.isKeyPressed(Input.Keys.S)) {
			state = HeroState.RUNNING_BACKWARDS;
			newPosition.y -= movementSpeed;			
		}else if(Gdx.input.isKeyPressed(Input.Keys.D)) {
			state = HeroState.RUNNING_RIGHT;
			newPosition.x += movementSpeed;			
		}else if(Gdx.input.isKeyPressed(Input.Keys.A)) {
			state = HeroState.RUNNING_LEFT;
			newPosition.x -= movementSpeed;			
		}else {
			state = HeroState.IDLE;
		}
		if(level.isTileAccessible(newPosition))
			this.position = newPosition;
		
		this.draw();
	}

}