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

public class Chest implements IAnimatable, IEntity {
	private int inventorySize;
	private Point position;
	private DungeonWorld level;
	private Animation closedAnimation;
	private Animation openingAnimation;
	private Animation openAnimation;
	private int animationTimer = 0;
	private Animation activeAnimation;
	private ChestState state;
	private ArrayList<Item> inventory = new ArrayList<>();

	public Chest(int inventorySize) {
		this.inventorySize = inventorySize;

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
		for(int i = 0; i < inventorySize; i++) {
			Random rdm = new Random();
			switch(rdm.nextInt(3)) {
			case 0:
				inventory.add(new Sword());
				break;
			case 1:
				inventory.add(new Chestplate());
				break;
			case 2:
				inventory.add(new HealthPotion());
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

	public ArrayList<Item> openChest() {
		if (state == ChestState.CLOSED) {
			activeAnimation = openingAnimation;
			animationTimer = 24;
		}

		state = ChestState.OPEN;

		return inventory;
	}

	public ChestState getState() {
		return this.state;
	}
}
