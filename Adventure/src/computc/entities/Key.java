package computc.entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import computc.Game;
import computc.cameras.Camera;
import computc.worlds.dungeons.Dungeon;
import computc.worlds.rooms.Room;

public class Key extends Entity
{
	private float speed = 1f;
	public boolean pickedup;
	public Hero target;

	public Key(Dungeon dungeon, Room room, int tx, int ty)
	{
		super(dungeon, room, tx, ty);
		this.image = Game.assets.getImage("./res/key.png");
	}
	
	public void renderOnMap(Graphics graphics, Camera camera)
	{
		int x = (int)((this.getX() - this.getHalfWidth()) / 8) - camera.getX();
		int y = (int)((this.getY()  - this.getHalfHeight()) / 8) - camera.getY();
		int w = this.getWidth() / 8;
		int h = this.getHeight() / 8;
		
		graphics.setColor(Color.yellow);
		graphics.fillRoundRect(x, y, w, h, 2);
	}
}