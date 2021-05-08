package entities;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.Texture;

import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.DungeonWorld;
import de.fhbielefeld.pmdungeon.vorgaben.graphic.Animation;
import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IAnimatable;
import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IEntity;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;
import entities.items.Chestplate;
import entities.items.HealthPotion;
import entities.items.Item;
import entities.items.Sword;
import hud.ChestInventoryHud;
import hud.HudItem;
import program.Controller;

public class Chest implements IAnimatable, IEntity {
	private Item[] inventory = new Item[4];
	private Point position;
	private DungeonWorld level;
	private Animation closedAnimation;
	private Animation openingAnimation;
	private Animation openAnimation;
	private int animationTimer = 0;
	private Animation activeAnimation;
	private ChestState state;
	private Controller mc;
	private ChestInventoryHud cih = null;

	public Chest(Controller mc) {
		this.mc = mc;
		generateRandomItems();

		state = ChestState.CLOSED;

		ArrayList<Texture> closed = new ArrayList<>();
		closed.add(new Texture("./assets/textures/chest/chest_empty_open_anim_f0.png"));
		closedAnimation = new Animation(closed, 8);

		ArrayList<Texture> opening = new ArrayList<>();
		opening.add(new Texture("./assets/textures/chest/chest_empty_open_anim_f0.png"));
		opening.add(new Texture("./assets/textures/chest/chest_empty_open_anim_f1.png"));
		opening.add(new Texture("./assets/textures/chest/chest_empty_open_anim_f2.png"));
		openingAnimation = new Animation(opening, 8);

		ArrayList<Texture> open = new ArrayList<>();
		open.add(new Texture("./assets/textures/chest/chest_empty_open_anim_f2.png"));
		openAnimation = new Animation(open, 8);
	}
	
	private void generateRandomItems() {
		for(int i = 0; i < inventory.length; i++) {
			Random rdm = new Random();
			switch(rdm.nextInt(4)) {
			case 0:
				inventory[i] = new Sword();
				break;
			case 1:
				inventory[i] = new Chestplate();
				break;
			case 2:
				inventory[i] = new HealthPotion();
				break;
			case 3:
				inventory[i] = null;
				break;
			}
		}
	}

	public void setLevel(DungeonWorld level) {
		this.level = level;
		this.position = level.getRandomPointInDungeon();
	}

	@Override
	public boolean deleteable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void update() {
		animationTimer--;
		this.drawWithScaling(.75f, .75f);
	}

	@Override
	public Point getPosition() {
		return this.position;
	}

	@Override
	public Animation getActiveAnimation() {
		if (animationTimer <= 0) {
			if (state == ChestState.CLOSED) {
				activeAnimation = closedAnimation;
				animationTimer = 0;
			} else if (state == ChestState.OPEN) {
				activeAnimation = openAnimation;
				animationTimer = 0;
			}
		}

		return activeAnimation;
	}

	public Item[] openChest() {
		if (state == ChestState.CLOSED) {
			activeAnimation = openingAnimation;
			animationTimer = 24;
		}
		
		if(cih == null) {
			cih = new ChestInventoryHud(mc);
			for(int i = 0; i < inventory.length; i++) {
				if(inventory[i] != null) {
					cih.addHudItem(i, inventory[i]);
				}
			}
		}else {
			cih.removeHudItem(0);
			cih.removeHudItem(1);
			cih.removeHudItem(2);
			cih.removeHudItem(3);
			for(int i = 0; i < inventory.length; i++) {
				if(inventory[i] != null) {
					cih.addHudItem(i, inventory[i]);
				}
			}
		}
		
		mc.addHudElement(cih);

		state = ChestState.OPEN;

		return inventory;
	}
	
	public void removeHud() {
		if(cih != null) {
			cih.removeHudItem(0);
			cih.removeHudItem(1);
			cih.removeHudItem(2);
			cih.removeHudItem(3);
			mc.removeHudElement(this.cih);
		}
	}

	public ChestState getState() {
		return this.state;
	}
}
