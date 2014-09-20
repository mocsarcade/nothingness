package computc;

import java.util.LinkedList;

import computc.worlds.dungeons.Dungeon;
import computc.worlds.dungeons.DungeonException;
import computc.worlds.dungeons.OneRoomDungeon;

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
				this.dungeon = new OneRoomDungeon(this);
			}
			catch(DungeonException exception)
			{
				//code goes here
			}
		}
	}
}