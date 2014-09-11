package computc;

import org.newdawn.slick.SlickException;

import computc.entities.Hero;
import computc.worlds.Dungeon;
import computc.worlds.RandomZeldaesqueDungeon;

public class GameData
{
	public Hero hero;
	public Dungeon dungeon;
	
	public void instantiate() throws SlickException
	{
		this.dungeon = new RandomZeldaesqueDungeon();
		this.hero = new Hero(dungeon, dungeon.getRoom(2, 2), 5, 1);
	}
}
