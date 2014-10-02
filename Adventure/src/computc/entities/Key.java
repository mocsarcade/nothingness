package computc.entities;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import computc.Game;
import computc.cameras.Camera;
import computc.worlds.dungeons.Dungeon;
import computc.worlds.rooms.Room;

public class Key extends Entity
{
	private float speed = 1f;

	public Key(Dungeon dungeon, Room room, int tx, int ty)
	{
		super(dungeon, room, tx, ty);
		this.image = Game.assets.getImage("./res/key.png");
	}
}