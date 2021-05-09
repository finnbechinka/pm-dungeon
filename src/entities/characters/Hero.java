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
import entities.items.Weapon;
import program.Controller;

/**
 * Represents the games hero.
 * 
 * @author Finn Bechinka
 *
 */
public class Hero extends Character implements IAnimatable, IEntity {
	private Item[] inventory = new Item[5];
	private Weapon weaponSlot = null;
	private Armor armorSlot = null;
	private int selectedSlot = 0;
	private Chest currentChest = null;
	private Bag<?> currentBag = null;
	private Slot prevLMB = null;
	private int lvl = 1;
	private int exp = 0;
	private int neededExp = 0;
	private int tpCooldown = 0;

	/**
	 * Constructor.
	 * 
	 * Animations get build. The hero state gets initialised as IDLE.
	 */
	public Hero(Controller mc) {
		super(200, .1f);
		this.hp = baseHp;
		this.movementSpeed = baseMovementSpeed;
		this.mc = (Controller) mc;
		mc.updateHudHp(this.hp);
	}
	
	public void giveExp(int exp) {
		this.exp += exp;
		checkForLevelUp();
	}
	
	public void checkForLevelUp() {
		if(exp >= 50 && lvl == 1) {
			lvl = 2;
			neededExp = 100;
			this.baseHp += 10;
			hp = baseHp;
		}
		if(exp >= 100 && lvl == 2) {
			lvl = 3;
			neededExp = 150;
			this.baseHp += 10;
			hp = baseHp;
		}
		if(exp >= 150 && lvl == 3) {
			lvl = 4;
			neededExp = 200;
			this.baseHp += 10;
			hp = baseHp;
		}
		if(exp >= 200 && lvl == 4) {
			lvl = 5;
			neededExp = 250;
			this.baseHp += 10;
			hp = baseHp;
		}
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
	@Override
	public void update() {
		if (hp <= 0) {
			log.info("GAME OVER");
			state = CharacterState.DEAD;
		} else {
			//mc.updateHudHp(this.hp);
			//mc.updateHudLvl(this.lvl, this.exp, this.neededExp);
			mc.hotfixTesthud(hp, lvl, exp, neededExp);
			
			animationTimer--;
			attackCooldown--;
			tpCooldown--;

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
			}

			if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
				int clickx = Gdx.input.getX();
				int clicky = Gdx.input.getY();
				Slot tmpLMB = null;
				if (clickx > 233 && clickx < 263 && clicky > 405 && clicky < 427) {
					tmpLMB = Slot.INVENTORY1;
				} else if (clickx > 269 && clickx < 302 && clicky > 405 && clicky < 427) {
					tmpLMB = Slot.INVENTORY2;
				} else if (clickx > 308 && clickx < 340 && clicky > 405 && clicky < 427) {
					tmpLMB = Slot.INVENTORY3;
				} else if (clickx > 347 && clickx < 379 && clicky > 405 && clicky < 427) {
					tmpLMB = Slot.INVENTORY4;
				} else if (clickx > 386 && clickx < 417 && clicky > 405 && clicky < 427) {
					tmpLMB = Slot.INVENTORY5;
				}

				if (currentChest != null) {
					if (clickx > 532 && clickx < 560 && clicky > 120 && clicky < 140) {
						tmpLMB = Slot.CHEST1;
					} else if (clickx > 560 && clickx < 598 && clicky > 120 && clicky < 140) {
						tmpLMB = Slot.CHEST2;
					} else if (clickx > 531 && clickx < 560 && clicky > 97 && clicky < 115) {
						tmpLMB = Slot.CHEST3;
					} else if (clickx > 567 && clickx < 598 && clicky > 97 && clicky < 115) {
						tmpLMB = Slot.CHEST4;
					}
				}

				if (currentBag != null) {
					if (clickx > 70 && clickx < 87 && clicky > 120 && clicky < 137) {
						tmpLMB = Slot.BAG1;
					} else if (clickx > 92 && clickx < 112 && clicky > 120 && clicky < 137) {
						tmpLMB = Slot.BAG2;
					} else if (clickx > 118 && clickx < 138 && clicky > 120 && clicky < 137) {
						tmpLMB = Slot.BAG3;
					}
				}

				if (prevLMB == null) {
					prevLMB = tmpLMB;
				} else {
					Slot currentLMB = tmpLMB;
					// check if previous selection was a chest slot
					if (prevLMB == Slot.CHEST1 || prevLMB == Slot.CHEST2 || prevLMB == Slot.CHEST3
							|| prevLMB == Slot.CHEST4) {
						// check if current selection is a inventory slot
						if (currentLMB == Slot.INVENTORY1 || currentLMB == Slot.INVENTORY2
								|| currentLMB == Slot.INVENTORY3 || currentLMB == Slot.INVENTORY4
								|| currentLMB == Slot.INVENTORY5) {
							int chestIndex = Integer.parseInt(prevLMB.toString());
							int inventoryIndex = Integer.parseInt(currentLMB.toString());
							// check if chest slot contains item
							if (currentChest.openChest()[chestIndex] != null) {
								// check if inventory slot already contain a item
								if (inventory[inventoryIndex] != null) {
									// swap items
									Item swappedItem = inventory[inventoryIndex];
									mc.hih.removeHudItem(inventoryIndex);
									inventory[inventoryIndex] = currentChest.openChest()[chestIndex];
									currentChest.openChest()[chestIndex] = swappedItem;
									currentChest.openChest();
									mc.hih.addHudItem(inventoryIndex, inventory[inventoryIndex]);

								} else {
									// inventory slot did not contain a item
									// take item from chest into inventory
									inventory[inventoryIndex] = currentChest.openChest()[chestIndex];
									currentChest.openChest()[chestIndex] = null;
									currentChest.openChest();
									mc.hih.addHudItem(inventoryIndex, inventory[inventoryIndex]);
								}
							}
						}
					}
					// check if previous selection was a inventory slot
					if (prevLMB == Slot.INVENTORY1 || prevLMB == Slot.INVENTORY2 || prevLMB == Slot.INVENTORY3
							|| prevLMB == Slot.INVENTORY4 || prevLMB == Slot.INVENTORY5) {
						int inventoryIndex = Integer.parseInt(prevLMB.toString());
						// check if current selection is a chest slot
						if (currentLMB == Slot.CHEST1 || currentLMB == Slot.CHEST2 || currentLMB == Slot.CHEST3
								|| currentLMB == Slot.CHEST4) {
							int chestIndex = Integer.parseInt(currentLMB.toString());
							// check if inventory slot contains a item
							if (inventory[inventoryIndex] != null) {
								// check if chest slot contains a item
								if (currentChest.openChest()[chestIndex] != null) {
									Item swappedItem = currentChest.openChest()[chestIndex];
									currentChest.openChest()[chestIndex] = inventory[inventoryIndex];
									currentChest.openChest();
									mc.hih.removeHudItem(inventoryIndex);
									inventory[inventoryIndex] = swappedItem;
									mc.hih.addHudItem(inventoryIndex, inventory[inventoryIndex]);
								} else {
									currentChest.openChest()[chestIndex] = inventory[inventoryIndex];
									currentChest.openChest();
									mc.hih.removeHudItem(inventoryIndex);
									inventory[inventoryIndex] = null;
								}
							}
						}else if(currentLMB == Slot.BAG1 || currentLMB == Slot.BAG2 || currentLMB == Slot.BAG3) {
							int bagIndex = Integer.parseInt(currentLMB.toString());
							if(currentBag.getInventory()[bagIndex] != null) {
								
							}else {
								
							}
						}
					}
					prevLMB = null;
				}
			}

			if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
				System.out.println("\nx: " + Gdx.input.getX());
				System.out.println("y: " + Gdx.input.getY());
				int clickx = Gdx.input.getX();
				int clicky = Gdx.input.getY();

				if (clickx > 233 && clickx < 263 && clicky > 405 && clicky < 427) {
					System.out.println("inv slot 1");
				} else if (clickx > 269 && clickx < 302 && clicky > 405 && clicky < 427) {
					System.out.println("inv slot 2");
				} else if (clickx > 308 && clickx < 340 && clicky > 405 && clicky < 427) {
					System.out.println("inv slot 3");
				} else if (clickx > 347 && clickx < 379 && clicky > 405 && clicky < 427) {
					System.out.println("inv slot 4");
				} else if (clickx > 386 && clickx < 417 && clicky > 405 && clicky < 427) {
					System.out.println("inv slot 5");
				}

				if (currentBag != null) {
					if (clickx > 70 && clickx < 87 && clicky > 120 && clicky < 137) {
						System.out.println("bag slot 1");
					} else if (clickx > 92 && clickx < 112 && clicky > 120 && clicky < 137) {
						System.out.println("bag slot 2");
					} else if (clickx > 118 && clickx < 138 && clicky > 120 && clicky < 137) {
						System.out.println("bag slot 3");
					}
				}
			}

			if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
				if (weaponSlot != null && attackCooldown <= 0) {
					System.out.println("WHY THO");
					attackCooldown = weaponSlot.getAttackCooldown();
				}
				mc.heroAttack();
				this.setState(CharacterState.ATTACKING);
			}

			//drop item in currently selected inventory slot
			if (Gdx.input.isKeyJustPressed(Input.Keys.G)) {
				if (inventory.length >= selectedSlot + 1) {
					if(inventory[selectedSlot] != null) {
						inventory[selectedSlot].setLevel(level);
						inventory[selectedSlot].setPosition(new Point(this.getPosition().x, this.getPosition().y));
						inventory[selectedSlot].setState(ItemState.ON_GROUND);
						mc.getItemsList().add(inventory[selectedSlot]);
						mc.hih.removeHudItem(selectedSlot);
						inventory[selectedSlot] = null;
					}
				}
			}

			if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
				//open or close a bag
				if(inventory[selectedSlot] != null) {
					if(inventory[selectedSlot].getClass() == Bag.class && currentBag == null) {
						currentBag = (Bag<?>) inventory[selectedSlot];
						currentBag.showHud();
					}else {
						currentBag.removeHud();
						currentBag = null;
					}
				}else if(currentBag != null){
					currentBag.removeHud();
					currentBag = null;
				}
			}

			if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
				boolean itemFound = false;
				Item swappedItem = null;
				float heroX = this.position.x;
				float heroY = this.position.y;
				for (Item i : mc.getItemsList()) {
					float itemX = i.getPosition().x;
					float itemY = i.getPosition().y;
					if (heroX + 1 >= itemX && heroX - 1 <= itemX && heroY + 1 >= itemY && heroY - 1 <= itemY) {
						itemFound = true;
						if (inventory.length >= selectedSlot + 1) {
							if (inventory[selectedSlot] == null) {
								inventory[selectedSlot] = i;
							} else {
								swappedItem = inventory[selectedSlot];
								swappedItem.setLevel(level);
								swappedItem.setPosition(new Point(position.x, position.y));
								swappedItem.setState(ItemState.ON_GROUND);
								mc.getItemsList().add(swappedItem);
								mc.hih.removeHudItem(selectedSlot);
								inventory[selectedSlot] = i;
							}
							i.setState(ItemState.IN_INVENTORY);
							mc.hih.addHudItem(selectedSlot, i);
							mc.getItemsList().remove(i);
							break;
						}
					}
				}

				if (!itemFound) {
					for (Chest c : mc.getChestList()) {
						float chestX = c.getPosition().x;
						float chestY = c.getPosition().y;
						if (heroX + 1 >= chestX && heroX - 1 <= chestX && heroY + 1 >= chestY && heroY - 1 <= chestY) {
							c.openChest();
							currentChest = c;
						}
					}
				}
			}
			
			if(lvl >= 2 && Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
				movementSpeed = 0.175f;
			}else {
				movementSpeed = baseMovementSpeed;
			}
			// if player tries to move and movement is valid update position
			this.setState(CharacterState.IDLE);
			Point newPosition = new Point(this.position);
			if (Gdx.input.isKeyPressed(Input.Keys.W)) {
				log.finest("Input: W");
				this.setState(CharacterState.RUNNING_FORWARDS);
				if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT) && lvl >= 5 && tpCooldown <= 0) {
					newPosition.y += 3;
					if(level.isTileAccessible(newPosition)) {
						tpCooldown = 300;
					}else {
						newPosition.y -= 3;
					}
				}
				newPosition.y += movementSpeed;
			}
			if (Gdx.input.isKeyPressed(Input.Keys.S)) {
				log.finest("Input: S");
				this.setState(CharacterState.RUNNING_BACKWARDS);
				if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT) && lvl >= 5 && tpCooldown <= 0) {
					newPosition.y -= 3;
					if(level.isTileAccessible(newPosition)) {
						tpCooldown = 300;
					}else {
						newPosition.y += 3;
					}
				}
				newPosition.y -= movementSpeed;
			}
			if (Gdx.input.isKeyPressed(Input.Keys.D)) {
				log.finest("Input: D");
				this.setState(CharacterState.RUNNING_RIGHT);
				if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT) && lvl >= 5 && tpCooldown <= 0) {
					newPosition.x += 3;
					if(level.isTileAccessible(newPosition)) {
						tpCooldown = 300;
					}else {
						newPosition.x -= 3;
					}
				}
				newPosition.x += movementSpeed;
			}
			if (Gdx.input.isKeyPressed(Input.Keys.A)) {
				log.finest("Input: A");
				this.setState(CharacterState.RUNNING_LEFT);
				if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT) && lvl >= 5 && tpCooldown <= 0) {
					newPosition.x -= 3;
					if(level.isTileAccessible(newPosition)) {
						tpCooldown = 300;
					}else {
						newPosition.x += 3;
					}
				}
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
				return 100;
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

	public Item[] getItems() {
		return this.inventory;
	}
}
