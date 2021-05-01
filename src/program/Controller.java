package program;

import java.lang.reflect.InvocationTargetException;

import de.fhbielefeld.pmdungeon.desktop.DesktopLauncher;
import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.DungeonWorld;
import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.tiles.Tile;
import de.fhbielefeld.pmdungeon.vorgaben.game.Controller.MainController;
import entities.Hero;
import entities.Monster;
import entities.*;

public class Controller extends MainController{
	private Hero hero;
	private Monster monster1, monster2, monster3;
	private DungeonWorld startLevel = null;
	
	@Override
	protected void setup() {
		hero = new Hero();
		entityController.addEntity(hero);
		camera.follow(hero);
	}
	
	@Override
	protected void beginFrame() {
		
	}
	
	@Override
	protected void endFrame() {
		if(hero.isDead()) {
			this.restartGame();
		}else if(levelController.checkForTrigger(hero.getPosition())) {
			levelController.triggerNextStage();
		}else {
			if(monster1.isDead()) {
				entityController.removeEntity(monster1);
			}
			if(monster2.isDead()) {
				entityController.removeEntity(monster2);
			}
			if(monster3.isDead()) {
				entityController.removeEntity(monster3);
			}
			Tile heroTile = levelController.getDungeon().getTileAt((int)hero.getPosition().x, (int)hero.getPosition().y);
			Tile monster1Tile = levelController.getDungeon().getTileAt((int)monster1.getPosition().x, (int)monster1.getPosition().y);
			Tile monster2Tile = levelController.getDungeon().getTileAt((int)monster2.getPosition().x, (int)monster2.getPosition().y);
			Tile monster3Tile = levelController.getDungeon().getTileAt((int)monster3.getPosition().x, (int)monster3.getPosition().y);
			
			if(heroTile == monster1Tile) {
				monster1.damage(hero.attack());
				hero.damage(monster1.attack());
			}
			if(heroTile == monster2Tile) {
				monster2.damage(hero.attack());
				hero.damage(monster2.attack());
			}
			if(heroTile == monster3Tile) {
				monster3.damage(hero.attack());
				hero.damage(monster3.attack());
			}
		}
	}
	
	@Override
	public void onLevelLoad() {
		if(startLevel == null) {
			startLevel = levelController.getDungeon();
		}
		hero.setLevel(levelController.getDungeon());
		entityController.getList().clear();
		entityController.addEntity(hero);
		monster1 = new SlimeMonster();
		monster2 = new SnakeMonster();
		monster3 = new SnakeMonster();
		entityController.addEntity(monster1);
		entityController.addEntity(monster2);
		entityController.addEntity(monster3);
		monster1.setLevel(levelController.getDungeon());
		monster2.setLevel(levelController.getDungeon());
		monster3.setLevel(levelController.getDungeon());
	}
	
	public void restartGame() {
		try {
			levelController.loadDungeon(startLevel);
			hero.setState(CharacterState.IDLE);
			hero.setHp(hero.getBaseHp());
		} catch (InvocationTargetException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
