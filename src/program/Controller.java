package program;

import de.fhbielefeld.pmdungeon.vorgaben.game.Controller.MainController;

public class Controller extends MainController{
	private Hero hero;
	
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
		if(levelController.checkForTrigger(hero.getPosition())) {
			levelController.triggerNextStage();
		}
	}
	
	@Override
	public void onLevelLoad() {
		hero.setLevel(levelController.getDungeon());
	}
}
