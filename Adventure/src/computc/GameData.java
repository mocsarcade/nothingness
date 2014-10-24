package computc;

import org.newdawn.slick.SlickException;

import computc.entities.Hero;
import computc.worlds.dungeons.Dungeon;
import computc.worlds.dungeons.DungeonException;
import computc.worlds.dungeons.FiveRoomDungeon;
import computc.worlds.dungeons.OneRoomDungeon;
import computc.worlds.dungeons.RandomDungeon;

public class GameData
{
	public Hero hero;
	public Dungeon dungeon;
	
	public int level = -1;
	
	public void instantiate() throws SlickException
	{
		this.dungeon = null;
		
		while(this.dungeon == null)
		{
			try
			{
				if(level < 0)
				{
					this.dungeon = new OneRoomDungeon(this);
				}
				else
				{
					this.dungeon = new RandomDungeon(this);
				}
			}
			catch(DungeonException exception)
			{
				//code goes here.
			}
		}
		
		this.dungeon.initiate();
		
		this.hero = new Hero(this.dungeon, 5, 4);
	}
}
