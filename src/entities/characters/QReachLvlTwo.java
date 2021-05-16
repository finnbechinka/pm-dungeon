package entities.characters;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;

import de.fhbielefeld.pmdungeon.vorgaben.graphic.Animation;
import program.Controller;
import quests.IQuest;

public class QReachLvlTwo extends Character implements IQuest{
	boolean questComplete = false;
	boolean deletable = false;
	
	public QReachLvlTwo(double baseHp, float baseMovementSpeed, Controller mc) {
		super(baseHp, baseMovementSpeed);
		this.mc = mc;
	}
	
	public boolean isQuestComplete() {
		return this.questComplete;
	}
	
	@Override
	public Animation getActiveAnimation() {
		return animations.get("idle");
	}


	@Override
	public boolean deleteable() {
		return deletable;
	}

	@Override
	public void update() {
		this.draw();
	}

	@Override
	public boolean isDead() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void createAnimations() {
		ArrayList<Texture> frames = new ArrayList<Texture>();
		frames.add(new Texture("./assets/textures/characters/wizard/wizzard_m_idle_anim_f0.png"));
		frames.add(new Texture("./assets/textures/characters/wizard/wizzard_m_idle_anim_f1.png"));
		frames.add(new Texture("./assets/textures/characters/wizard/wizzard_m_idle_anim_f2.png"));
		frames.add(new Texture("./assets/textures/characters/wizard/wizzard_m_idle_anim_f3.png"));
		animations.put("idle", new Animation(frames, 8));
	}

	@Override
	public void updateQuest() {
		if(mc.getHero().getLvl() >= 2 && !questComplete) {
			this.questComplete = true;
			log.info("reach lvl 2 quest complete");
			mc.getHero().giveExp(100);
			this.deletable = true;
			log.info("the hero was awarded 100 exp");
		}
	}
	
	@Override
	public void damage(double dmg) {
		//Do nothing cause this npc is not supposed to be killed
	}
	
	public double attack() {
		//Do nothing cause this npc is not supposed to be killed
		return 0;
	}

	@Override
	public String questDialog() {
		return "Quest: reach lvl 2";
	}
	
	

}
