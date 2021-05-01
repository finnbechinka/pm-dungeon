package program;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.DungeonWorld;
import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.tiles.Tile;
import de.fhbielefeld.pmdungeon.vorgaben.game.Controller.MainController;
import entities.Hero;
import entities.Monster;
import entities.*;

public class Controller extends MainController{
	private Hero hero;
	private ArrayList<Monster> monsters = new ArrayList<>();
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
			Tile heroTile = levelController.getDungeon().getTileAt((int)hero.getPosition().x, (int)hero.getPosition().y);
			for(Monster m : monsters) {
				if(m.isDead()) {
					entityController.removeEntity(m);
				}else {
					Tile monsterTile = levelController.getDungeon().getTileAt((int)m.getPosition().x, (int)m.getPosition().y);
					if(monsterTile == heroTile) {
						m.damage(hero.attack());
						hero.damage(m.attack());
					}
				}
				
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
		spawnMonsters();
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
	
	public void spawnMonsters() {
		monsters.clear();
		monsters.add(new SlimeMonster());
		monsters.add(new SnakeMonster());
		monsters.add(new SlimeMonster());
		
		for(Monster m : monsters) {
			entityController.addEntity(m);
			m.setLevel(levelController.getDungeon());
		}
		
	}
}
