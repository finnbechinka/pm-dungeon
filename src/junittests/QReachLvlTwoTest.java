package junittests;

import org.junit.jupiter.api.Assertions;

import entities.characters.QReachLvlTwo;
import program.Controller;

import org.junit.jupiter.api.Test;

import de.fhbielefeld.pmdungeon.desktop.DesktopLauncher;

class QReachLvlTwoTest {
	
	@Test
	void lvlNotHighEnough() {
		Controller mc = new Controller();
		DesktopLauncher.run(mc);
		QReachLvlTwo q = new QReachLvlTwo(0,0,mc);
		q.updateQuest();
		Assertions.assertFalse(q.isQuestComplete());
	}
	
	@Test
	void exactlyRequiredLvl() {
		Controller mc = new Controller();
		DesktopLauncher.run(mc);
		QReachLvlTwo q = new QReachLvlTwo(0,0,mc);
		mc.getHero().giveExp(50);
		q.updateQuest();
		Assertions.assertTrue(q.isQuestComplete());
	}
	
	@Test
	void overRequiredLvl() {
		Controller mc = new Controller();
		DesktopLauncher.run(mc);
		QReachLvlTwo q = new QReachLvlTwo(0,0,mc);
		mc.getHero().giveExp(250);
		q.updateQuest();
		Assertions.assertTrue(q.isQuestComplete());
	}

}
