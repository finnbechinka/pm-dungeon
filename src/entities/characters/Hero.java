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
import entities.Chest;
import entities.items.Armor;
import entities.items.Bag;
import entities.items.Item;
import entities.items.ItemState;
import entities.items.Potion;
import entities.items.Weapon;

/**
 * Represents the games hero.
 * 
 * @author Finn Bechinka
 *
 */
public class Hero extends Character implements IAnimatable, IEntity {
	private ArrayList<Item> inventory = new ArrayList<Item>();
	private int inventorySize = 3;
	private Weapon weaponSlot = null;
	private Armor armorSlot = null;
	private int selectedSlot = 0;
	private Chest currentChest = null;
	private Bag<?> currentBag = null;

	/**
	 * Constructor.
	 * 
	 * Animations get build. The hero state gets initialised as IDLE.
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
		animations.put("idle", new Animation(idle, 8));

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
		if (state == CharacterState.ATTACKING) {
			return animations.get("attacking_lmao");
		} else if (state == CharacterState.RUNNING_FORWARDS || state == CharacterState.RUNNING_RIGHT) {
			return animations.get("runRight");
		} else if (state == CharacterState.RUNNING_BACKWARDS || state == CharacterState.RUNNING_LEFT) {
			return animations.get("runLeft");
		} else {
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
	 * Checks for player movement input and updates the heros position if the
	 * movement is valid.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void update() {
		if (hp <= 0) {
			log.info("GAME OVER");
			state = CharacterState.DEAD;
		} else {
			animationTimer--;
			attackCooldown--;

			// set movement speed
			if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
				log.finest("Input: SHIFT_LEFT");
				movementSpeed = 0.175f;
			} else {
				movementSpeed = 0.1f;
			}

			if (Gdx.input.isKeyJustPressed(Input.Keys.I)) {
				System.out.println("weapon slot: " + weaponSlot);
				System.out.println("armor slot: " + armorSlot);
				for (Item i : inventory) {
					System.out.println("slot " + (inventory.indexOf(i) + 1) + ": " + i);
					if(i.getClass() == Bag.class) {
						Bag<?> b = (Bag<?>) i;
						for(Item j : b.getInventory()) {
							System.out.println("--- slot " + (b.getInventory().indexOf(j) + 1) +": " + j);
						}
					}
				}
				String selectedItem = "none";
				if (selectedSlot != -1 && inventory.size() - 1 >= selectedSlot) {
					selectedItem = inventory.get(selectedSlot).getClass().toString();
				}
				System.out.println(selectedItem);
			}

			if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
				selectedSlot = 0;
			} else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
				selectedSlot = 1;
			} else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
				selectedSlot = 2;
			} else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)) {
				selectedSlot = 3;
			} else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_5)) {
				selectedSlot = 4;
			} else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_6)) {
				selectedSlot = 5;
			} else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_7)) {
				selectedSlot = 6;
			} else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_8)) {
				selectedSlot = 7;
			} else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_9)) {
				selectedSlot = 8;
			} else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_0)) {
				//selectedSlot = -1;
			}

			if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
				if (weaponSlot != null && attackCooldown <= 0) {
					System.out.println("WHY THO");
					attackCooldown = weaponSlot.getAttackCooldown();
				}
				mc.heroAttack();
				this.setState(CharacterState.ATTACKING);
			}

			if (Gdx.input.isKeyJustPressed(Input.Keys.G)) {
				if (inventory.size() >= selectedSlot + 1) {
					inventory.get(selectedSlot).setLevel(level);
					inventory.get(selectedSlot).setPosition(new Point(this.getPosition().x, this.getPosition().y));
					inventory.get(selectedSlot).setState(ItemState.ON_GROUND);
					mc.getItemsList().add(inventory.get(selectedSlot));
					inventory.remove(selectedSlot);
				}
			}
			
			if(Gdx.input.isKeyJustPressed(Input.Keys.O)) {
				if(!inventory.isEmpty() && inventory.size() >= selectedSlot+1 && inventory.get(selectedSlot).getClass() == Bag.class) {
					if(currentBag != null) {
						currentBag = null;
					}else {
						currentBag = (Bag<?>) inventory.get(selectedSlot);
						System.out.println("Bag inventory:");
						for(Item i : currentBag.getInventory()) {
							System.out.println("slot " + (inventory.indexOf(i) + 1) + ": " + i);
						}
					}
				}
			}

			if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
				if(currentBag != null) {
					if(!currentBag.getInventory().isEmpty() && currentBag.getInventory().size() >= selectedSlot+1) {
						if(inventory.size() < inventorySize) {
							inventory.add(currentBag.getInventory().get(selectedSlot));
							currentBag.getInventory().remove(selectedSlot);
							System.out.println("Hero inventory:");
							for (Item i : inventory) {
								System.out.println("slot " + (inventory.indexOf(i) + 1) + ": " + i);
							}
						}
					}
				}else if(currentChest != null) {
					float chestX = currentChest.getPosition().x;
					float chestY = currentChest.getPosition().y;
					if (this.position.x + 1 >= chestX && this.position.x - 1 <= chestX && this.position.y + 1 >= chestY
							&& this.position.y - 1 <= chestY) {
						ArrayList<Item> chestInv = currentChest.openChest();
						if (chestInv.isEmpty()) {
							currentChest = null;
						} else if (chestInv.size() >= selectedSlot + 1) {
							if (inventory.size() < inventorySize) {
								inventory.add(chestInv.get(selectedSlot));
								chestInv.remove(selectedSlot);
								System.out.println("Hero inventory:");
								for (Item i : inventory) {
									System.out.println("slot " + (inventory.indexOf(i) + 1) + ": " + i);
								}
								System.out.println("Inventory of the chest:");
								for (Item i : chestInv) {
									System.out.println("slot " + (chestInv.indexOf(i) + 1) + ": " + i);
								}
							}
						}
					} else {
						System.out.println("chest is empty");
						currentChest = null;
					}
				}
				if (currentChest == null) {
					boolean itemFound = false;
					Item swappedItem = null;
					float heroX = this.position.x;
					float heroY = this.position.y;
					for (Item i : mc.getItemsList()) {
						float itemX = i.getPosition().x;
						float itemY = i.getPosition().y;
						if (heroX + 1 >= itemX && heroX - 1 <= itemX && heroY + 1 >= itemY && heroY - 1 <= itemY) {
							boolean itemStashedInBag = false;
							if(!inventory.isEmpty() && inventory.size() >= selectedSlot+1) {
								if(inventory.get(selectedSlot).getClass() == Bag.class) {
									Bag<?> bag = (Bag<?>) inventory.get(selectedSlot);
									Class<?> c = bag.typeParameterClass;
									if(c == i.getClass().getSuperclass()) {
										if(bag.getBagSize() >= bag.getInventory().size()) {
											bag.insert(i);
											itemStashedInBag = true;
											itemFound = true;
											i.setState(ItemState.IN_INVENTORY);
											break;
										}
									}
								}
							}
							
							if (!itemStashedInBag && inventory.size() < inventorySize) {
								inventory.add(i);
								i.setState(ItemState.IN_INVENTORY);
								itemFound = true;
								break;
							} else if (inventory.size() >= selectedSlot + 1) {
								swappedItem = i;
								itemFound = true;
								break;
							}
						}
					}
					if (swappedItem != null) {
						inventory.get(selectedSlot).setLevel(level);
						inventory.get(selectedSlot)
								.setPosition(new Point(swappedItem.getPosition().x, swappedItem.getPosition().y));
						inventory.get(selectedSlot).setState(ItemState.ON_GROUND);
						mc.getItemsList().add(inventory.get(selectedSlot));
						inventory.set(selectedSlot, swappedItem);
						swappedItem.setState(ItemState.IN_INVENTORY);
					} else if (inventory.size() >= selectedSlot + 1) {
						if (inventory.get(selectedSlot).getClass().getSuperclass() == Weapon.class) {
							if (weaponSlot == null) {
								weaponSlot = (Weapon) inventory.get(selectedSlot);
								inventory.remove(selectedSlot);
							} else {
								Weapon tmpWeapon = (Weapon) inventory.get(selectedSlot);
								inventory.set(selectedSlot, weaponSlot);
								weaponSlot = tmpWeapon;
							}
						} else if (inventory.get(selectedSlot).getClass().getSuperclass() == Armor.class) {
							if (armorSlot == null) {
								armorSlot = (Armor) inventory.get(selectedSlot);
								inventory.remove(selectedSlot);
							} else {
								Armor tmpArmor = (Armor) inventory.get(selectedSlot);
								inventory.set(selectedSlot, armorSlot);
								armorSlot = tmpArmor;
							}
						}else if(inventory.get(selectedSlot).getClass() == Bag.class) {
							currentBag = (Bag<?>) inventory.get(selectedSlot);
						}
					}

					if (!itemFound) {
						for (Chest c : mc.getChestList()) {
							float chestX = c.getPosition().x;
							float chestY = c.getPosition().y;
							if (heroX + 1 >= chestX && heroX - 1 <= chestX && heroY + 1 >= chestY
									&& heroY - 1 <= chestY) {
								ArrayList<Item> chestInventory = c.openChest();
								if (!chestInventory.isEmpty()) {
									System.out.println("Inventory of the chest:");
									for (Item i : chestInventory) {
										System.out.println("slot " + (chestInventory.indexOf(i) + 1) + ": " + i);
									}
									currentChest = c;
								} else {
									System.out.println("chest is empty");
								}
							}
						}
					}
				}

			}

			// if player tries to move and movement is valid update position
			this.setState(CharacterState.IDLE);
			Point newPosition = new Point(this.position);
			if (Gdx.input.isKeyPressed(Input.Keys.W)) {
				log.finest("Input: W");
				this.setState(CharacterState.RUNNING_FORWARDS);
				newPosition.y += movementSpeed;
			}
			if (Gdx.input.isKeyPressed(Input.Keys.S)) {
				log.finest("Input: S");
				this.setState(CharacterState.RUNNING_BACKWARDS);
				newPosition.y -= movementSpeed;
			}
			if (Gdx.input.isKeyPressed(Input.Keys.D)) {
				log.finest("Input: D");
				this.setState(CharacterState.RUNNING_RIGHT);
				newPosition.x += movementSpeed;
			}
			if (Gdx.input.isKeyPressed(Input.Keys.A)) {
				log.finest("Input: A");
				this.setState(CharacterState.RUNNING_LEFT);
				newPosition.x -= movementSpeed;
			}

			if (level.isTileAccessible(newPosition)) {
				this.position = newPosition;
			}
		}

		this.draw();
	}

	@Override
	public void setState(CharacterState state) {
		if (animationTimer <= 0) {
			this.state = state;
			if (state == CharacterState.ATTACKING) {
				animationTimer = 24;
			}
		}
	}

	@Override
	public double attack() {
		log.info("hero attacks");
		Random rdm = new Random();
		if (rdm.nextBoolean() && attackCooldown <= 0) {
			if (weaponSlot != null) {
				return weaponSlot.getDmg() * 1.5;
			} else {

				attackCooldown = 20;
				return 10;
			}
		} else if (attackCooldown <= 0) {
			if (weaponSlot != null) {
				return weaponSlot.getDmg();
			} else {

				attackCooldown = 10;
				return 5;
			}
		} else {
			return 0;
		}
	}

	@Override
	public void damage(double dmg) {
		if (dmg > 0) {
			if (armorSlot != null) {
				this.hp -= dmg * armorSlot.getDmgMod();
				log.info("HERO TOOK " + dmg * armorSlot.getDmgMod() + " DMG, HE NOW HAS " + this.hp + " HP!");
			} else {
				this.hp -= dmg;
				log.info("HERO TOOK " + dmg + " DMG, HE NOW HAS " + this.hp + " HP!");
			}
			Point newPosition = new Point(this.position);
			float knockbackDist = 1f;
			do {
				Random direction = new Random();
				switch (direction.nextInt(4)) {
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
			} while (!level.isTileAccessible(newPosition));
			this.position = newPosition;
		}
	}

	@Override
	public boolean isDead() {
		if (state == CharacterState.DEAD) {
			return true;
		} else {
			return false;
		}
	}

	public void setHp(double hp) {
		this.hp = hp;
	}

	public double getBaseHp() {
		return this.baseHp;
	}

	public ArrayList<Item> getItems() {
		return this.inventory;
	}
}
