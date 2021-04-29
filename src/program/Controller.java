package program;

import de.fhbielefeld.pmdungeon.desktop.DesktopLauncher;
import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.tiles.Tile;
import de.fhbielefeld.pmdungeon.vorgaben.game.Controller.MainController;
import entities.Hero;
import entities.Monster;
import entities.*;

public class Controller extends MainController{
	private Hero hero;
	private Monster monster1, monster2, monster3;
	
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
		if(monster1.isDead()) {
			entityController.removeEntity(monster1);
		}
		if(monster2.isDead()) {
			entityController.removeEntity(monster2);
		}
		if(monster3.isDead()) {
			entityController.removeEntity(monster3);
		}
		if(hero.isDead()) {
			levelController.getDungeon().dispose();
			entityController.getList().clear();
			DesktopLauncher.run(new Controller());
			this.dispose();
		}else if(levelController.checkForTrigger(hero.getPosition())) {
			levelController.triggerNextStage();
		}else {
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
}
