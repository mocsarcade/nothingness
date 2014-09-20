package computc;

import org.newdawn.slick.SlickException;

import computc.entities.Hero;
import computc.worlds.dungeons.Dungeon;
import computc.worlds.dungeons.DungeonException;
import computc.worlds.dungeons.FiveRoomDungeon;
import computc.worlds.dungeons.OneRoomDungeon;
import computc.worlds.dungeons.RandomRoguelikeDungeon;
import computc.worlds.dungeons.RandomZeldaesqueDungeon;

public class GameData
{
	public Hero hero;
	public Dungeon dungeon;
	public Menu menu;
	
	public void instantiate() throws SlickException
	{
		/*this.dungeon = null;
		while(this.dungeon == null)
		{
			try
			{
				this.dungeon = new RandomZeldaesqueDungeon();
			}
			catch(DungeonException exception)
			{
				exception.printStackTrace();
			}
		}*/
		
		this.dungeon = new OneRoomDungeon();
		
		this.hero = new Hero(dungeon, 5, 4);
		this.menu = new Menu(this.dungeon, this.hero);
	}
}
