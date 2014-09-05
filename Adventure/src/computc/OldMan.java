package computc;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class OldMan extends Entity
{
	private Animation animation;
	
	public OldMan(Dungeon dungeon, int tx, int ty) throws SlickException
	{
		super(dungeon, tx, ty);
		
		this.image = new Image("res/ancient.png").getSubImage(1, 1, 240, 104);
		this.animation =  new Animation(new SpriteSheet(this.image, 60, 104), 300);
	}
	
	public void render(Graphics graphics, Camera camera)
	{
		int x = (int)(this.getX()) - this.getHalfWidth() - camera.getX();
		int y = (int)(this.getY()) - this.getHalfHeight() - camera.getY();
		
		this.animation.draw(x, y);
	}
}