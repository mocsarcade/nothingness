package computc;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Ancient extends Entity{
	
	private Image oldMan = new Image("res/ancient.png");
	Image ancientWalkingDown = oldMan.getSubImage(1, 1, 240, 104);
	Image oldManIdle = oldMan.getSubImage(1, 1, 60, 104);
	static Animation ancient, down, idle;
	private Image textBox;
	
	public Ancient(Dungeon dungeon, int tx, int ty) throws SlickException
	{
	super(dungeon, (int)(Math.floor(tx / Room.WIDTH)), (int)(Math.floor(ty / Room.HEIGHT)), tx - ((int)(Math.floor(tx / Room.WIDTH)) * Room.TILEY_WIDTH), ty - ((int)(Math.floor(ty / Room.HEIGHT)) * Room.TILEY_HEIGHT));
	
	this.image = ancientWalkingDown;
	
	this.down = new Animation(new SpriteSheet(ancientWalkingDown, 60, 104), 300);
	this.idle = new Animation(new SpriteSheet(oldManIdle, 60, 104), 200);
	
	ancient = down;
	}
	
	public void update(int delta, Camera camera)
	{
	setPosition(Tile.SIZE * 38, Tile.SIZE * 12);
	
	System.out.println("the old man's x :" + this.x + " the old man's y: " + this.y);
	}
	
	public void render(Graphics graphics, Camera camera)
	{
			int x = (int)(this.getX()) - (this.getWidth() / 2) - camera.getX();
			int y = (int)(this.getY()) - (this.getHeight() / 2) - camera.getY();
			
			ancient.draw(x, y);
			
			if(this.isOnScreen(camera) && y < Tile.SIZE * 15)
			{
				
			}
	}
	
}
