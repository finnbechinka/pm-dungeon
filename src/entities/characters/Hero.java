package entities.characters;

import java.util.ArrayList;
import java.util.Random;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;

import de.fhbielefeld.pmdungeon.vorgaben.graphic.Animation;
import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IAnimatable;
import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IEntity;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;
import entities.items.Armor;
import entities.items.Item;
import entities.items.ItemState;
import entities.items.Weapon;

/**
 * Represents the games hero.
 * 
 * @author Finn Bechinka
 *
 */
public class Hero extends Character implements IAnimatable, IEntity{
	private ArrayList<Item> inventory = new ArrayList<Item>();
	private int inventorySize = 5;
	private Weapon weaponSlot = null;
	private Armor armorSlot = null;
	private int selectedSlot = -1;
	
		
	/**
	 * Constructor.
	 * 
	 * Animations get build.
	 * The hero state gets initialised as IDLE.
	 */
	public Hero() {
		super(100, .1f);
		this.hp = baseHp;
		this.movementSpeed = baseMovementSpeed;
	}
	
	@Override
	protected void createAnimations() {
		ArrayList<Texture> idle = new ArrayList<>();
		idle.add(new Texture("./assets/textures/characters/hero/knight_idle_f0.png"));
		idle.add(new Texture("./assets/textures/characters/hero/knight_idle_f1.png"));
		idle.add(new Texture("./assets/textures/characters/hero/knight_idle_f2.png"));
		idle.add(new Texture("./assets/textures/characters/hero/knight_idle_f3.png"));
		animations.put("idle", new Animation(idle,8));
		
		ArrayList<Texture> runLeft = new ArrayList<>();
		runLeft.add(new Texture("./assets/textures/characters/hero/knight_run_left_f0.png"));
		runLeft.add(new Texture("./assets/textures/characters/hero/knight_run_left_f1.png"));
		runLeft.add(new Texture("./assets/textures/characters/hero/knight_run_left_f2.png"));
		runLeft.add(new Texture("./assets/textures/characters/hero/knight_run_left_f3.png"));
		animations.put("runLeft", new Animation(runLeft, 8));
		
		ArrayList<Texture> runRight = new ArrayList<>();
		runRight.add(new Texture("./assets/textures/characters/hero/knight_run_right_f0.png"));
		runRight.add(new Texture("./assets/textures/characters/hero/knight_run_right_f1.png"));
		runRight.add(new Texture("./assets/textures/characters/hero/knight_run_right_f2.png"));
		runRight.add(new Texture("./assets/textures/characters/hero/knight_run_right_f3.png"));
		animations.put("runRight", new Animation(runRight, 8));
		
		ArrayList<Texture> attacking = new ArrayList<>();
		attacking.add(new Texture("./assets/textures/characters/hero/hero_run_left_1.png"));
		attacking.add(new Texture("./assets/textures/characters/hero/hero_idle_1.png"));
		attacking.add(new Texture("./assets/textures/characters/hero/hero_run_right_1.png"));
		animations.put("attacking_lmao", new Animation(attacking, 8));
	}

	/**
	 * Returns the appropriate animation depending on the heros state.
	 * 
	 * @return the appropriate animation depending on the heros state
	 */
	@Override
	public Animation getActiveAnimation() {
		if(state == CharacterState.ATTACKING){
			return animations.get("attacking_lmao");
		}else if(state == CharacterState.RUNNING_FORWARDS || state == CharacterState.RUNNING_RIGHT){
			return animations.get("runRight");
		}else if(state == CharacterState.RUNNING_BACKWARDS || state == CharacterState.RUNNING_LEFT){
			return animations.get("runLeft");
		}else {
			return animations.get("idle");
		}
	}

	/**
	 * TODO because I don't know yet
	 */
	@Override
	public boolean deleteable() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Gets called once per frame.
	 * 
	 * Checks for player movement input and updates the heros position if the movement is valid.
	 */
	@Override
	public void update() {
		if(hp <= 0) {
			log.info("GAME OVER");
			state = CharacterState.DEAD;
		}else {
			animationTimer--;
			attackCooldown--;
			
			//set movement speed
			if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
				log.finest("Input: SHIFT_LEFT");
				movementSpeed = 0.175f;
			}else {
				movementSpeed = 0.1f;
			}
			
			if(Gdx.input.isKeyJustPressed(Input.Keys.I)) {
				System.out.println("weapon slot: " + weaponSlot);
				System.out.println("armor slot: " + armorSlot);
				for(Item i : inventory) {
					System.out.println("slot " + (inventory.indexOf(i) + 1)  + ": " + i);
				}
				String selectedItem = "none";
				if(selectedSlot != -1 && inventory.size()-1 >= selectedSlot) {
					selectedItem = inventory.get(selectedSlot).getClass().toString();
				}
				System.out.println(selectedItem);
			}
			
			if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_1) && inventory.size() >= 1) {
				selectedSlot = 0;
			}else if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_2) && inventory.size() >= 2) {
				selectedSlot = 1;
			}else if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_3) && inventory.size() >= 3) {
				selectedSlot = 2;
			}else if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_4) && inventory.size() >= 4) {
				selectedSlot = 3;
			}else if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_5) && inventory.size() >= 5) {
				selectedSlot = 4;
			}else if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_0)) {
				selectedSlot = -1;
			}
			
			if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
				if(weaponSlot != null && attackCooldown <= 0) {
					System.out.println("WHY THO");
					attackCooldown = weaponSlot.getAttackCooldown();
				}
				mc.heroAttack();
				this.setState(CharacterState.ATTACKING);
			}
			
			if(Gdx.input.isKeyJustPressed(Input.Keys.G)) {
				if(selectedSlot != -1) {
					inventory.get(selectedSlot).setLevel(level);
					inventory.get(selectedSlot).setPosition(new Point(this.getPosition().x, this.getPosition().y));
					inventory.get(selectedSlot).setState(ItemState.ON_GROUND);
					mc.getItemsList().add(inventory.get(selectedSlot));
					inventory.remove(selectedSlot);
				}
			}
			
			if(Gdx.input.isKeyJustPressed(Input.Keys.E)){
				Item swappedItem = null;
				float heroX = this.position.x;
				float heroY = this.position.y;
				for(Item i : mc.getItemsList()) {
					float itemX = i.getPosition().x;
					float itemY = i.getPosition().y;
					if(heroX + 1 >= itemX && heroX -1 <= itemX && heroY + 1 >= itemY && heroY - 1 <= itemY) {
						if(!inventory.contains(i) && inventory.size() < inventorySize && selectedSlot == -1) {
							inventory.add(i);
							i.setState(ItemState.IN_INVENTORY);
							break;
						}else if(!inventory.contains(i) && selectedSlot != -1) {
							swappedItem = i;
							break;
						}
					}
				}
				if(swappedItem != null) {
					inventory.get(selectedSlot).setLevel(level);
					inventory.get(selectedSlot).setPosition(new Point(swappedItem.getPosition().x, swappedItem.getPosition().y));
					inventory.get(selectedSlot).setState(ItemState.ON_GROUND);
					mc.getItemsList().add(inventory.get(selectedSlot));
					inventory.set(selectedSlot, swappedItem);
					swappedItem.setState(ItemState.IN_INVENTORY);
				} else if(inventory.size() > 0 && selectedSlot != -1) {
					if(inventory.get(selectedSlot).getClass().getSuperclass() == entities.items.Weapon.class) {
						if(weaponSlot == null) {
							weaponSlot = (Weapon) inventory.get(selectedSlot);
							inventory.remove(selectedSlot);							
						}else {
							Weapon tmpWeapon = (Weapon) inventory.get(selectedSlot);
							inventory.set(selectedSlot, weaponSlot);
							weaponSlot = tmpWeapon;
						}
					}else if(inventory.get(selectedSlot).getClass().getSuperclass() == entities.items.Armor.class) {
						if(armorSlot == null) {
							armorSlot = (Armor) inventory.get(selectedSlot);
							inventory.remove(selectedSlot);							
						}else {
							Armor tmpArmor = (Armor) inventory.get(selectedSlot);
							inventory.set(selectedSlot, armorSlot);
							armorSlot = tmpArmor;
						}
					}
				}
			}
			
			//if player tries to move and movement is valid update position
			this.setState(CharacterState.IDLE);
			Point newPosition = new Point(this.position);
			if(Gdx.input.isKeyPressed(Input.Keys.W)) {
				log.finest("Input: W");
				this.setState(CharacterState.RUNNING_FORWARDS);
				newPosition.y += movementSpeed;			
			}
			if(Gdx.input.isKeyPressed(Input.Keys.S)) {
				log.finest("Input: S");
				this.setState(CharacterState.RUNNING_BACKWARDS);
				newPosition.y -= movementSpeed;			
			}
			if(Gdx.input.isKeyPressed(Input.Keys.D)) {
				log.finest("Input: D");
				this.setState(CharacterState.RUNNING_RIGHT);
				newPosition.x += movementSpeed;			
			}
			if(Gdx.input.isKeyPressed(Input.Keys.A)) {
				log.finest("Input: A");
				this.setState(CharacterState.RUNNING_LEFT);
				newPosition.x -= movementSpeed;			
			}
			
			if(level.isTileAccessible(newPosition)) {
				this.position = newPosition;				
			}
		}
		
		this.draw();
	}
	
	@Override
	public void setState(CharacterState state) {
		if(animationTimer <= 0) {
			this.state = state;
			if(state == CharacterState.ATTACKING) {
				animationTimer = 24;
			}
		}
	}

	@Override
	public double attack() {
		log.info("hero attacks");
		Random rdm = new Random();
		if(rdm.nextBoolean() && attackCooldown <= 0) {
			if(weaponSlot != null) {
				return weaponSlot.getDmg() * 1.5;
			}else {
				
				attackCooldown = 20;
				return 10;
			}
		}else if(attackCooldown <= 0) {
			if(weaponSlot != null) {
				return weaponSlot.getDmg();
			}else {
				
				attackCooldown = 10;
				return 5;
			}
		}else {
			return 0;
		}
	}
	
	@Override
	public void damage(double dmg) {
		if(dmg > 0) {
			if(armorSlot != null) {
				this.hp -= dmg * armorSlot.getDmgMod();
				log.info("HERO TOOK " + dmg * armorSlot.getDmgMod()+ " DMG, HE NOW HAS " + this.hp + " HP!");	
			}else {
				this.hp -= dmg ;
				log.info("HERO TOOK " + dmg + " DMG, HE NOW HAS " + this.hp + " HP!");				
			}
			Point newPosition = new Point(this.position);
			float knockbackDist = 1f;
			do {
				Random direction = new Random();
				switch(direction.nextInt(4)) {
				case 0:
					newPosition.y += knockbackDist;
					break;
				case 1: 
					newPosition.y -= knockbackDist;
					break;
				case 2:
					newPosition.x += knockbackDist;
					break;
				case 3:
					newPosition.x -= knockbackDist;
					break;
				}
			}while(!level.isTileAccessible(newPosition));
			this.position = newPosition;
		}
	}

	@Override
	public boolean isDead() {
		if(state == CharacterState.DEAD) {
			return true;
		}else {
			return false;
		}
	}
	
	public void setHp(double hp) {
		this.hp = hp;
	}
	
	public double getBaseHp() {
		return this.baseHp;
	}
	
	public ArrayList<Item> getItems(){
		return this.inventory;
	}
}
