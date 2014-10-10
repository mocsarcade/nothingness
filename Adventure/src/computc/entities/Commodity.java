package computc.entities;

import java.util.HashMap;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

import computc.Game;
import computc.cameras.Camera;
import computc.worlds.dungeons.Dungeon;
import computc.worlds.rooms.Room;

public class Commodity extends Entity
{
	private Image arrowSheet = Game.assets.getImage("res/arrowSpriteSheet.png");
	private Image arrows = arrowSheet.getSubImage(192, 0, 64, 64);
	
	private Animation key, coin, arrow;
	private int type;
	
	public HashMap<Integer, String> collectibles = new HashMap<Integer, String>();
	
	public Commodity(Dungeon dungeon, float x, float y, int type)
	{
		super(dungeon, x, y);
		this.type = type;
		
		arrow = new Animation(new SpriteSheet(arrows, 32, 32), 200);
		
		collectibles.put(0, "res/key.png");
		collectibles.put(1, "res/coin.png");
		collectibles.put(2, "res/singleArrow.png");
		
		this.image = Game.assets.getImage(collectibles.get(type));
		
		System.out.println("this happened");
		
	}
	
	public void render(Graphics graphics, Camera camera)
	{
		if(this.type == 2)
		{
			arrow.draw(this.x - this.getHalfWidth() - camera.getX(), this.y - this.getHalfHeight() - camera.getY());
		}
		
		else super.render(graphics, camera);
	}
	
	@Override
	public void update(int delta)
	{
		System.out.println("this is happening");
	}
	
	public HashMap<Integer, String> getCollectibles()
	{
		return collectibles;
	}
	
	public int getType()
	{
		return this.type;
	}
	
}
