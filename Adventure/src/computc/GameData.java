package computc;

import org.newdawn.slick.SlickException;

import computc.entities.Hero;
import computc.worlds.Dungeon;
import computc.worlds.DungeonException;
import computc.worlds.OneRoomDungeon;
import computc.worlds.FiveRoomDungeon;
import computc.worlds.RandomRoguelikeDungeon;
import computc.worlds.RandomZeldaesqueDungeon;

public class GameData
{
	public Hero hero;
	public Dungeon dungeon;
	public Menu menu;
	
	public void instantiate() throws SlickException
	{
		this.dungeon = null;
		while(this.dungeon == null)
		{
			try
			{
				this.dungeon = new RandomZeldaesqueDungeon(this);
			}
			catch(DungeonException exception)
			{
				exception.printStackTrace();
			}
		}
		
		this.hero = new Hero(dungeon, 5, 4);
		this.menu = new Menu(this.dungeon, this.hero);
	}
}
