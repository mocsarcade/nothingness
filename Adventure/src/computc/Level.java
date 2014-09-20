package computc;

import java.util.LinkedList;

import computc.worlds.dungeons.Dungeon;
import computc.worlds.dungeons.DungeonException;
import computc.worlds.dungeons.OneRoomDungeon;
import computc.worlds.dungeons.RandomZeldaesqueDungeon;

public class Level
{
	public Dungeon dungeon;
	private LinkedList<String> layouts = new LinkedList<String>();
	
	public Level()
	{
		layouts.add("./res/rooms/example.room.tmx");
		
		while(this.dungeon == null)
		{
			try
			{
				this.dungeon = new RandomZeldaesqueDungeon(this);
			}
			catch(DungeonException exception)
			{
				//code goes here
			}
		}
	}
	
	public String getLayout()
	{
		return this.layouts.get(0);
	}
}