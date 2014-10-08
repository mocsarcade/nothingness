package computc.entities;

import computc.Game;
import computc.worlds.rooms.Room;
import computc.worlds.dungeons.Dungeon;

public class Ladder extends Entity
{
	public Ladder(Dungeon dungeon, Room room, int tx, int ty)
	{
		super(dungeon, room, tx, ty);
		this.image = Game.assets.getImage("./res/ladder.png");
	}
}