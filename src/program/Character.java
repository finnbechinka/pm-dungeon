package program;

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
	private static Logger log;
	private Point position;
	private DungeonWorld level;
	private CharacterState state;
	private HashMap<String, Animation> animations;
	
	public Character() {
		setupLogger();
		createAnimations();
		state = CharacterState.IDLE;
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

	protected abstract void createAnimations();
	
	public void setLevel(DungeonWorld level) {
		this.level = level;
		setRandomPosition();
		log.info("new level set");
	}
	
	public void setRandomPosition() {
		this.position = new Point(level.getRandomPointInDungeon());
	}
	
	public Point getPosition() {
		return this.position;
	}
	
	
}