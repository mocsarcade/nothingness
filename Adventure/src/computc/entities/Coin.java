package computc.entities;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import computc.cameras.Camera;
import computc.worlds.Dungeon;
import computc.worlds.Room;

public class Coin extends Entity
{
	public boolean pickedup;

	public Coin(Dungeon dungeon, Room room, float x, float y)
	{
		super(dungeon, room, x, y);
		this.image = Coin.IMAGE;
	}
	
	public void render(Graphics graphics, Camera camera)
	{
		if(this.pickedup == false)
		{
			super.render(graphics, camera);
		}
	}
	
	public static Image IMAGE;
}