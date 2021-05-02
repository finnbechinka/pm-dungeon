package program;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.DungeonWorld;
import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.tiles.Tile;
import de.fhbielefeld.pmdungeon.vorgaben.game.Controller.EntityController;
import de.fhbielefeld.pmdungeon.vorgaben.game.Controller.MainController;
import entities.Chest;
import entities.characters.CharacterState;
import entities.characters.Hero;
import entities.characters.Monster;
import entities.characters.SlimeMonster;
import entities.characters.SnakeMonster;
import entities.items.Bag;
import entities.items.Chestplate;
import entities.items.HealthPotion;
import entities.items.Item;
import entities.items.Sword;
import entities.items.Weapon;

public class Controller extends MainController {
	private Hero hero;
	private ArrayList<Monster> monsters = new ArrayList<>();
	private ArrayList<Item> items = new ArrayList<>();
	private ArrayList<Chest> chests = new ArrayList<>();
	private ArrayList<Bag<?>> bags = new ArrayList<>();
	private DungeonWorld startLevel = null;

	@Override
	protected void setup() {
		hero = new Hero();
		hero.setMainController(this);
		entityController.addEntity(hero);
		camera.follow(hero);
	}

	public ArrayList<Item> getItemsList() {
		return items;
	}

	public ArrayList<Chest> getChestList() {
		return chests;
	}

	public ArrayList<?> getBags() {
		return this.bags;
	}
	
	public EntityController getEntityController() {
		return this.entityController;
	}

	@Override
	protected void beginFrame() {
		
	}

	@Override
	protected void endFrame() {
		if (hero.isDead()) {
			this.restartGame();
		} else if (levelController.checkForTrigger(hero.getPosition())) {
			levelController.triggerNextStage();
		} else {
			Tile heroTile = levelController.getDungeon().getTileAt((int) hero.getPosition().x,
					(int) hero.getPosition().y);
			for (Monster m : monsters) {
				if (m.isDead()) {
					entityController.removeEntity(m);
				} else {
					Tile monsterTile = levelController.getDungeon().getTileAt((int) m.getPosition().x,
							(int) m.getPosition().y);
					if (monsterTile == heroTile) {
						hero.damage(m.attack());
					}
				}

			}
		}
	}

	@Override
	public void onLevelLoad() {
		if (startLevel == null) {
			startLevel = levelController.getDungeon();
		}
		hero.setLevel(levelController.getDungeon());
		entityController.getList().clear();
		entityController.addEntity(hero);
		spawnMonsters();

		Bag<Weapon> wBag = new Bag<>(Weapon.class);
		entityController.addEntity(wBag);
		wBag.setLevel(levelController.getDungeon());
		
		bags.add(wBag);
		items.add(wBag);

		Chest chest = new Chest(3);
		entityController.addEntity(chest);
		chest.setLevel(levelController.getDungeon());

		chests.add(chest);

		Item sword = new Sword();
		entityController.addEntity(sword);
		sword.setLevel(levelController.getDungeon());

		Item chestplate = new Chestplate();
		entityController.addEntity(chestplate);
		chestplate.setLevel(levelController.getDungeon());

		Item potion = new HealthPotion();
		entityController.addEntity(potion);
		potion.setLevel(levelController.getDungeon());

		items.add(sword);
		items.add(chestplate);
		items.add(potion);
	}

	public void restartGame() {
		try {
			levelController.loadDungeon(startLevel);
			hero.setState(CharacterState.IDLE);
			hero.setHp(hero.getBaseHp());
			hero.getItems().clear();
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

		for (Monster m : monsters) {
			entityController.addEntity(m);
			m.setLevel(levelController.getDungeon());
		}

	}

	public void heroAttack() {
		Tile heroTile = levelController.getDungeon().getTileAt((int) hero.getPosition().x, (int) hero.getPosition().y);
		for (Monster m : monsters) {
			Tile monsterTile = levelController.getDungeon().getTileAt((int) m.getPosition().x, (int) m.getPosition().y);
			if (monsterTile == heroTile) {
				m.damage(hero.attack());
			}

		}
	}

}
