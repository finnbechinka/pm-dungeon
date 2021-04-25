package program;

import de.fhbielefeld.pmdungeon.vorgaben.game.Controller.MainController;

public class Controller extends MainController{
	private Hero hero;
	private Monster monster1, monster2, monster3;
	
	@Override
	protected void setup() {
		hero = new Hero();
		monster1 = new Monster(MonsterType.RUNNER);
		monster2 = new Monster(MonsterType.TANK);
		monster3 = new Monster(MonsterType.TANK);
		entityController.addEntity(monster1);
		entityController.addEntity(monster2);
		entityController.addEntity(monster3);
		entityController.addEntity(hero);
		camera.follow(hero);
	}
	
	@Override
	protected void beginFrame() {
		
	}
	
	@Override
	protected void endFrame() {
		if(levelController.checkForTrigger(hero.getPosition())) {
			levelController.triggerNextStage();
		}
	}
	
	@Override
	public void onLevelLoad() {
		hero.setLevel(levelController.getDungeon());
		monster1.setLevel(levelController.getDungeon());
		monster2.setLevel(levelController.getDungeon());
		monster3.setLevel(levelController.getDungeon());
	}
}
