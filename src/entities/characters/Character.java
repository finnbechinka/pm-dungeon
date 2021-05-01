package entities.characters;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.DungeonWorld;
import de.fhbielefeld.pmdungeon.vorgaben.graphic.Animation;
import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IAnimatable;
import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IEntity;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;

public abstract class Character implements IAnimatable, IEntity{
	protected static Logger log;
	protected Point position;
	protected DungeonWorld level;
	protected CharacterState state;
	protected HashMap<String, Animation> animations = new HashMap<>();
	protected double hp;
	protected final double baseHp;
	protected final float baseMovementSpeed;
	protected float movementSpeed;
	protected int attackCooldown = 30;
	
	public Character(double baseHp, float baseMovementSpeed) {
		setupLogger();
		this.baseHp = baseHp;
		this.baseMovementSpeed = baseMovementSpeed;
		createAnimations();
		state = CharacterState.IDLE;
	}
	
	public void setState(CharacterState state) {
		this.state = state;
	}
	
	public abstract boolean isDead();
	
	protected void setupLogger() {
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
		
		Character.log = Logger.getLogger(Character.class.getName());
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

	protected abstract void createAnimations();
	
	/**
	 * Sets the level the character is in.
	 * Also calls {@link #findRandomPostion()}.
	 * 
	 * @param level - of type DungeonWorld
	 * 
	 */
	public void setLevel(DungeonWorld level) {
		this.level = level;
		setRandomPosition();
		log.info("new level set");
	}
	
	/**
	 * Finds and sets a random valid position in the level for the character.
	 */
	public void setRandomPosition() {
		this.position = new Point(level.getRandomPointInDungeon());
	}
	
	/**
	 * Returns the current position of the character.
	 * 
	 * @return the current position of the character
	 */
	@Override
	public Point getPosition() {
		return this.position;
	}
	
	
	@Override
	public Animation getActiveAnimation() {
		return null;
	}
	
	public void heal(int hp) {
		this.hp += hp;
	}
	
	public void damage(int dmg) {
		this.hp -= dmg;
	}
	
	public abstract int attack();
	
	
}