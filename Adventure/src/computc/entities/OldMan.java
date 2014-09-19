package computc.entities;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import computc.cameras.Camera;
import computc.worlds.Dungeon;
import computc.worlds.Room;
import computc.worlds.Tile;

public class OldMan extends Entity
{
	private Animation animation;
	
	public OldMan(Dungeon dungeon, Room room, float x, float y) throws SlickException
	{
		super(dungeon, room, x, y);
		
		this.image = new Image("res/ancient.png").getSubImage(1, 1, 240, 104);
		this.animation =  new Animation(new SpriteSheet(this.image, 60, 104), 300);
	}
	
	public void update(int delta)
	{
		/*if(hero.getRoomyX() == 3 && hero.getRoomyY() == 1 && this.y < Tile.SIZE * 14.5)
		{
			this.y += .005 * delta;
		}*/
	}
	
	public void render(Graphics graphics, Camera camera)
	{
		int x = (int)(this.getX()) - camera.getX();
		int y = (int)(this.getY()) - camera.getY();
		
		this.animation.draw(x, y);
	}
}