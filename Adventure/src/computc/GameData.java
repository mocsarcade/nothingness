package computc;

import org.newdawn.slick.SlickException;

import computc.entities.Hero;
import computc.worlds.Dungeon;
import computc.worlds.HandfulOfRoomsDungeon;

public class GameData
{
	public Hero hero;
	public Dungeon dungeon;
	
	public void instantiate() throws SlickException
	{
		this.dungeon = new HandfulOfRoomsDungeon();
		this.hero = new Hero(dungeon, 5, 4);
	}
}
