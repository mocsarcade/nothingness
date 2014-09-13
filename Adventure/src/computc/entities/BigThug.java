package computc.entities;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import computc.worlds.Dungeon;
import computc.worlds.Room;

public class BigThug extends Thug
{
	public BigThug(Dungeon dungeon, Room room, float x, float y) throws SlickException
	{
		super(dungeon, room, x, y);
		
		this.maximumVelocity = 0.02f;
		this.image = new Image("res/bigthug.png");
		this.damage = 2;
	}
}