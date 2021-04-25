package program;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import com.badlogic.gdx.graphics.Texture;

import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.DungeonWorld;
import de.fhbielefeld.pmdungeon.vorgaben.graphic.Animation;
import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IAnimatable;
import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IEntity;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;

public class Monster implements IAnimatable, IEntity{
	private final static Logger log = Logger.getLogger(Hero.class.getName());
	private HashMap<String, Animation> animations = new HashMap<>();
	private Point position;
	private DungeonWorld level;
	private MonsterState state;
	private MonsterType type;
	private int updateCounter = 0;
	private int hp;
	private float movementSpeed;
	
	public Monster(MonsterType monsterType) {
		type = monsterType;
		setupLogger();
		createAnimations();
		determineMonsterStats();
		state = MonsterState.IDLE;
	}
	
	private void determineMonsterStats() {
		if(type == MonsterType.TANK) {
			this.hp = 175;
			this.movementSpeed = 0.069f;
		} else if(type == MonsterType.RUNNER) {
			this.hp = 75;
			this.movementSpeed = 0.125f;
		}else {
			this.state = MonsterState.DEAD;
		}
	}
	
	private void setupLogger() {
		/*
		 * LOGGING LEVELS FOR REFERENCE
		 * 
		 * SEVERE
		 * WARNING
		 * INFO
		 * CONFIG
		 * FINE
		 * FINER
		 * FINEST
		 * 
		 * AND ALL/OFF
		 */
		log.setUseParentHandlers(false);
		
		LogManager.getLogManager().reset();
		log.setLevel(Level.ALL);
		
		ConsoleHandler consoleHandler = new ConsoleHandler();
		consoleHandler.setLevel(Level.INFO);
		log.addHandler(consoleHandler);
		
		Date currDate = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy_hh-mm-ss");
		String fileName = "./logs/log_" + dateFormat.format(currDate).toString() + ".log";
		try {
			FileHandler fileHandler = new FileHandler(fileName);
			fileHandler.setLevel(Level.CONFIG);
			log.addHandler(fileHandler);
			log.fine("file logger started");
			
		} catch (IOException e) {
			log.severe("problem while trying to start the file logger");
			e.printStackTrace();
		}
		
	}

	private void createAnimations() {
		ArrayList<Texture> idle = new ArrayList<>();
		ArrayList<Texture> runLeft = new ArrayList<>();
		ArrayList<Texture> runRight = new ArrayList<>();
		if(type == MonsterType.TANK) {
			idle.add(new Texture("./assets/textures/characters/slime/slime_right_1.png"));
			idle.add(new Texture("./assets/textures/characters/slime/slime_left_1.png"));
			
			runRight.add(new Texture("./assets/textures/characters/slime/slime_right_1.png"));
			runRight.add(new Texture("./assets/textures/characters/slime/slime_right_2.png"));
			runRight.add(new Texture("./assets/textures/characters/slime/slime_right_1.png"));
			runRight.add(new Texture("./assets/textures/characters/slime/slime_right_3.png"));
			runRight.add(new Texture("./assets/textures/characters/slime/slime_right_1.png"));
			runRight.add(new Texture("./assets/textures/characters/slime/slime_right_4.png"));
			runRight.add(new Texture("./assets/textures/characters/slime/slime_right_1.png"));
			
			runLeft.add(new Texture("./assets/textures/characters/slime/slime_left_1.png"));
			runLeft.add(new Texture("./assets/textures/characters/slime/slime_left_2.png"));
			runLeft.add(new Texture("./assets/textures/characters/slime/slime_left_1.png"));
			runLeft.add(new Texture("./assets/textures/characters/slime/slime_left_3.png"));
			runLeft.add(new Texture("./assets/textures/characters/slime/slime_left_1.png"));
			runLeft.add(new Texture("./assets/textures/characters/slime/slime_left_4.png"));
			runLeft.add(new Texture("./assets/textures/characters/slime/slime_left_1.png"));
		}else if(type == MonsterType.RUNNER) {
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
			runRight.add(new Texture("./assets/textures/characters/snake/snake_right_1.png"));
			
			runLeft.add(new Texture("./assets/textures/characters/snake/snake_left_1.png"));
			runLeft.add(new Texture("./assets/textures/characters/snake/snake_left_2.png"));
			runLeft.add(new Texture("./assets/textures/characters/snake/snake_left_1.png"));
			runLeft.add(new Texture("./assets/textures/characters/snake/snake_left_3.png"));
			runLeft.add(new Texture("./assets/textures/characters/snake/snake_left_1.png"));
			runLeft.add(new Texture("./assets/textures/characters/snake/snake_left_4.png"));
			runLeft.add(new Texture("./assets/textures/characters/snake/snake_left_1.png"));
			runLeft.add(new Texture("./assets/textures/characters/snake/snake_left_5.png"));
			runLeft.add(new Texture("./assets/textures/characters/snake/snake_left_1.png"));
			
		}
		animations.put("idle", new Animation(idle,8));
		animations.put("runLeft", new Animation(runLeft, 8));
		animations.put("runRight", new Animation(runRight, 8));
	}
	
	@Override
	public Point getPosition() {
		return this.position;
	}

	@Override
	public boolean deleteable() {
		if(state == MonsterState.DEAD) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public void update() {		
		if(updateCounter == 0) {
			Random direction = new Random();
			switch(direction.nextInt(5)) {
			case 0: 
				state = MonsterState.IDLE;
				break;
			case 1: 
				state = MonsterState.RUNNING_FORWARDS;
				break;
			case 2: 
				state = MonsterState.RUNNING_BACKWARDS;
				break;
			case 3: 
				state = MonsterState.RUNNING_RIGHT;
				break;
			case 4: 
				state = MonsterState.RUNNING_LEFT;
				break;
			}
			updateCounter = direction.nextInt(20)+10;
		}else {
			updateCounter--;
		}
		Point newPosition = new Point(this.position);
		if(state == MonsterState.RUNNING_FORWARDS) {
			newPosition.y += movementSpeed;
		}else if(state == MonsterState.RUNNING_BACKWARDS) {
			newPosition.y -= movementSpeed;
		}else if(state == MonsterState.RUNNING_RIGHT) {
			newPosition.x += movementSpeed;
		}else if(state == MonsterState.RUNNING_LEFT) {
			newPosition.x -= movementSpeed;
		}
		if(level.isTileAccessible(newPosition)) this.position = newPosition;
		
				
		this.draw();
	}

	@Override
	public Animation getActiveAnimation() {
		if(state == MonsterState.RUNNING_FORWARDS || state == MonsterState.RUNNING_RIGHT){
			return animations.get("runRight");
		}else if(state == MonsterState.RUNNING_BACKWARDS || state == MonsterState.RUNNING_LEFT){
			return animations.get("runLeft");
		}else{
			return animations.get("idle");
		}
	}

	/**
	 * Sets the level the monster is in.
	 * Also calls {@link #findRandomPostion()}.
	 * 
	 * @param level - of type DungeonWorld
	 * 
	 */
	public void setLevel(DungeonWorld level) {
		this.level = level;
		findRandomPostion();
		log.info("new level loaded");
	}
	
	/**
	 * Finds and sets a random valid position in the dungeon for the monster.
	 */
	public void findRandomPostion(){
		this.position = new Point(level.getRandomPointInDungeon());
	}
}
