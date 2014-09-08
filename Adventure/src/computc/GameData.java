package computc;

import org.newdawn.slick.SlickException;

import computc.entities.Hero;
import computc.worlds.Dungeon;
import computc.worlds.LinearRandomDungeon;

public class GameData
{
	public Hero hero;
	public Dungeon dungeon;
	
	public GameData() throws SlickException
	{
		this.dungeon = new LinearRandomDungeon();
		this.hero = new Hero(dungeon, dungeon.getRoom(2, 2), 5, 1);
	}
}
