package entities;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.List;

import de.fhbielefeld.pmdungeon.vorgaben.graphic.Animation;
import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IAnimatable;
import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IEntity;

import entities.characters.Character;

public class SpikeTrap extends Trap implements IAnimatable, IEntity{
	private Animation activeAnim;
	private Animation inactiveAnim;
	private Animation triggeredAnim;
	private Animation currentAnim;
	private int triggeredCounter = 32;
	
	public SpikeTrap() {
		ArrayList<Texture> active = new ArrayList<Texture>();
		active.add(new Texture("./assets/textures/traps/floor_spikes_anim_f0.png"));
		activeAnim = new Animation(active, 30);
		
		ArrayList<Texture> inactive = new ArrayList<Texture>();
		inactive.add(new Texture("./assets/textures/traps/floor_spikes_anim_f3.png"));
		inactiveAnim = new Animation(inactive, 30);
		
		ArrayList<Texture> triggered = new ArrayList<Texture>();
		triggered.add(new Texture("./assets/textures/traps/floor_spikes_anim_f0.png"));
		triggered.add(new Texture("./assets/textures/traps/floor_spikes_anim_f1.png"));
		triggered.add(new Texture("./assets/textures/traps/floor_spikes_anim_f2.png"));
		triggered.add(new Texture("./assets/textures/traps/floor_spikes_anim_f3.png"));
		triggeredAnim = new Animation(triggered, 8);
		
		currentAnim = activeAnim;
	}
	
	@Override
	public Animation getActiveAnimation() {
		return currentAnim;
	}
	
	@Override
	public void activate(Character c) {
		c.damage(30);
		currentAnim = triggeredAnim;
		active = false;
	}
	
	@Override
	public void update() {
		if(currentAnim.equals(triggeredAnim)) {
			if(triggeredCounter <= 0) {
				currentAnim = inactiveAnim;
			}else if(triggeredCounter > 0) {
				triggeredCounter--;
			}
		}
		
		if(visible) {
			this.drawWithScaling(.75f, .75f);
		}
	}

}
