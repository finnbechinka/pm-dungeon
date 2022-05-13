package program;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.DungeonWorld;
import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.tiles.Tile;
import de.fhbielefeld.pmdungeon.vorgaben.game.Controller.EntityController;
import de.fhbielefeld.pmdungeon.vorgaben.game.Controller.LevelController;
import de.fhbielefeld.pmdungeon.vorgaben.game.Controller.MainController;
import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IHUDElement;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;
import entities.Chest;
import entities.SpikeTrap;
import entities.Trap;
import entities.characters.Hero;
import entities.characters.Monster;
import entities.characters.QReachLvlTwo;
import entities.characters.SlimeMonster;
import entities.characters.SnakeMonster;
import entities.items.Bag;
import entities.items.Chestplate;
import entities.items.HealthPotion;
import entities.items.Item;
import entities.items.Spear;
import entities.items.Sword;
import hud.HeroEquipmentHud;
import hud.HeroInventoryHud;
import quests.IQuest;
import entities.characters.Character;

public class Controller extends MainController {
	private Hero hero;
	private ArrayList<Monster> monsters = new ArrayList<>();
	private ArrayList<Item> items = new ArrayList<>();
	private ArrayList<Chest> chests = new ArrayList<>();
	private ArrayList<Bag<?>> bags = new ArrayList<>();
	private ArrayList<IQuest> questGivers = new ArrayList<>();
	private DungeonWorld startLevel = null;
	private Label hpLabel = null;
	private Label lvlLabel = null;
	private Label hotfixlabel = null;
	public HeroInventoryHud hih = new HeroInventoryHud(this);
	public HeroEquipmentHud heh = new HeroEquipmentHud(this);
	private ArrayList<Trap> traps = new ArrayList<>();

	@Override
	protected void setup() {
		hero = new Hero(this);
		hero.setMainController(this);
		entityController.addEntity(hero);
		camera.follow(hero);
		hud.addHudElement(hih);
		hud.addHudElement(heh);
	}
	
	public void addHudElement(IHUDElement element) {
		hud.addHudElement(element);
	}
	
	public void removeHudElement(IHUDElement element) {
		hud.removeHudElement(element);
	}
	
	public ArrayList<IQuest> getQuestGivers(){
		return this.questGivers;
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
	
	public Hero getHero() {
		return this.hero;
	}

	@Override
	protected void beginFrame() {
		
	}

	@Override
	protected void endFrame() {
		if (hero.isDead()) {
			this.restartGame();
		} else if (levelController.checkForTrigger(hero.getPosition())) {
			for(Chest c : chests) {
				c.removeHud();
			}
			levelController.triggerNextStage();
		} else {
			Tile heroTile = levelController.getDungeon().getTileAt((int) hero.getPosition().x,
					(int) hero.getPosition().y);
			Monster deadMonster = null;
			
			for(Trap t : traps) {
				if(t.isActive()) {
					Tile trapTile = levelController.getDungeon().getTileAt((int) t.getPosition().x, (int) t.getPosition().y);
					if(trapTile == heroTile) {
						t.activate(hero);
					}
				}
			}
			
			for (Monster m : monsters) {
				if (m.isDead()) {
					deadMonster = m;
					entityController.removeEntity(m);
				} else {
					if (this.isInRange(m, hero)) {
						hero.damage(m.attack());
					}
				}
			}
			if(deadMonster != null) {
				hero.giveExp(50);
				monsters.remove(deadMonster);
			}
		}
	}

	@Override
	public void onLevelLoad() {
		if (startLevel == null) {
			startLevel = levelController.getDungeon();
		}
		entityController.getList().clear();
		chests.clear();
		traps.clear();
		questGivers.clear();
		
		QReachLvlTwo reachLvlTwoQuest = new QReachLvlTwo(100, 0, this);
		questGivers.add(reachLvlTwoQuest);
		entityController.addEntity(reachLvlTwoQuest);
		reachLvlTwoQuest.setLevel(levelController.getDungeon());
		
		Trap spike1 = new SpikeTrap();
		traps.add(spike1);
		entityController.addEntity(spike1);
		spike1.setLevel(levelController.getDungeon());

//		Bag<Weapon> wBag = new Bag<>(this);
//		entityController.addEntity(wBag);
//		wBag.setLevel(levelController.getDungeon());
//		
//		bags.add(wBag);
//		items.add(wBag);

		Chest chest = new Chest(this);
		entityController.addEntity(chest);
		chest.setLevel(levelController.getDungeon());

		chests.add(chest);
		
		Item spear = new Spear();
		entityController.addEntity(spear);
		spear.setLevel(levelController.getDungeon());

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
		items.add(spear);
		
		spawnMonsters();
		
		hero.setLevel(levelController.getDungeon());
		entityController.addEntity(hero);
	}

	public void restartGame() {
		try {
			levelController.loadDungeon(startLevel);
			hero.setHp(hero.getBaseHp());
			hero.getItems()[0] = null;
			hero.getItems()[1] = null;
			hero.getItems()[2] = null;
			hero.getItems()[3] = null;
			hero.getItems()[4] = null;
		} catch (InvocationTargetException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int spawnMonsters() {
		monsters.clear();
		monsters.add(new SlimeMonster());
		monsters.add(new SnakeMonster());
		monsters.add(new SlimeMonster());

		int x = 0;
		x = extracted(x);
		
		return x;
	}

	private int extracted(int x) {
		for (Monster m : monsters) {
			entityController.addEntity(m);
			m.setLevel(levelController.getDungeon());
			m.setMainController(this);
			x++;
		}
		return x;
	}

	public void heroAttack() {
		for (Monster m : monsters) {
			if (this.isInRange(hero, m)) {
				m.damage(hero.attack());
			}

		}
	}
	
	public void hotfixTesthud(double hp, int lvl, int exp, int neededExp) {
		int hpInt = (int) Math.ceil(hp);
		String hpString = Integer.toString(hpInt);
		String lvlString = (Integer.toString(lvl) + " (" + Integer.toString(exp) + "/" + Integer.toString(neededExp) + ")");
		String hotfixstring = lvlString + "\n\n\n\n\n\n\n\n\n\n\n\n" + hpString;
		
		if(hotfixlabel != null) {
			textHUD.removeText(hotfixlabel);
			
		}
		
		hotfixlabel = textHUD.drawText(hotfixstring, "./assets/fonts/ARCADE.TTF", Color.RED, 30, 50, 50, 25, 200);
	}
	
	public void updateHudHp(double hp) {
		int hpInt = (int) Math.ceil(hp);
		String hpString = Integer.toString(hpInt);
		if(hpLabel != null) {
			textHUD.removeText(hpLabel);
		}
		hpLabel = textHUD.drawText(hpString, "./assets/fonts/ARCADE.TTF", Color.RED, 30, 50, 50, 25, 25);
	}
	
	public void updateHudLvl(int lvl, int exp, int neededExp) {
		String lvlString = (Integer.toString(lvl) + " (" + Integer.toString(exp) + "/" + Integer.toString(neededExp) + ")");
		if(lvlLabel != null) {
			textHUD.removeText(lvlLabel);
		}
		lvlLabel = textHUD.drawText(lvlString, "./assets/fonts/ARCADE.TTF", Color.RED, 30, 50, 50, 25, 400);
	}
	
	public boolean isInRange(Character attacker, Character defender) {
		double distance;
		Point aPos = attacker.getPosition();
		Point dPos = defender.getPosition();
		distance = Math.sqrt(Math.pow(dPos.x - aPos.x, 2) + Math.pow(dPos.y - aPos.y, 2));
		
		return (distance >= attacker.getMinRange() && distance <= attacker.getMaxRange()) ? true : false;
	}

}
