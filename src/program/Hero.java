package program;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;

import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.DungeonWorld;
import de.fhbielefeld.pmdungeon.vorgaben.graphic.Animation;
import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IAnimatable;
import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IEntity;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;

public class Hero implements IAnimatable, IEntity{
	private Animation idleAnimation;
	private Animation runLeftAnimation;
	private Animation runRightAnimation;
	private Point position;
	private DungeonWorld level;
	
	
	public Hero() {
		ArrayList<Texture> idle = new ArrayList<>();
		idle.add(new Texture("C:\\Users\\oooo\\eclipse-workspace\\PM-Dungeon\\assets\\textures\\characters\\hero_idle_1.png"));
		idle.add(new Texture("C:\\Users\\oooo\\eclipse-workspace\\PM-Dungeon\\assets\\textures\\characters\\hero.png"));
		idle.add(new Texture("C:\\Users\\oooo\\eclipse-workspace\\PM-Dungeon\\assets\\textures\\characters\\hero_idle_2.png"));
		idle.add(new Texture("C:\\Users\\oooo\\eclipse-workspace\\PM-Dungeon\\assets\\textures\\characters\\hero.png"));
		idle.add(new Texture("C:\\Users\\oooo\\eclipse-workspace\\PM-Dungeon\\assets\\textures\\characters\\hero_idle_1.png"));
		idleAnimation = new Animation(idle,8);
		
		ArrayList<Texture> runLeft = new ArrayList<>();
		runLeft.add(new Texture("C:\\Users\\oooo\\eclipse-workspace\\PM-Dungeon\\assets\\textures\\characters\\hero_run_left_1.png"));
		runLeft.add(new Texture("C:\\Users\\oooo\\eclipse-workspace\\PM-Dungeon\\assets\\textures\\characters\\hero_run_left_2.png"));
		runLeftAnimation = new Animation(runLeft, 8);
		
		ArrayList<Texture> runRight = new ArrayList<>();
		runRight.add(new Texture("C:\\Users\\oooo\\eclipse-workspace\\PM-Dungeon\\assets\\textures\\characters\\hero_run_right_1.png"));
		runRight.add(new Texture("C:\\Users\\oooo\\eclipse-workspace\\PM-Dungeon\\assets\\textures\\characters\\hero_run_right_2.png"));
		runRightAnimation = new Animation(runRight, 8);
	}
	
	public void setLevel(DungeonWorld level) {
		this.level = level;
		findRandomPostion();
	}
	
	public void findRandomPostion(){
		this.position = new Point(level.getRandomPointInDungeon());
	}
	
	@Override
	public Point getPosition() {
		return this.position;
	}

	@Override
	public Animation getActiveAnimation() {
		if(Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.W)){
			return runRightAnimation;
		}else if(Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.S)){
			return runLeftAnimation;
		}else {
			return idleAnimation;
		}
	}

	@Override
	public boolean deleteable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void update() {
		Point newPosition = new Point(this.position);
		float movementSpeed = 0.1f;
		if(Gdx.input.isKeyPressed(Input.Keys.W))
			newPosition.y += movementSpeed;
		if(Gdx.input.isKeyPressed(Input.Keys.S))
			newPosition.y -= movementSpeed;
		if(Gdx.input.isKeyPressed(Input.Keys.D))
			newPosition.x += movementSpeed;
		if(Gdx.input.isKeyPressed(Input.Keys.A))
			newPosition.x -= movementSpeed;
		if(level.isTileAccessible(newPosition))
			this.position = newPosition;
		this.draw();
	}

}
