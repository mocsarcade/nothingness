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
	private Image arrowSheet = Game.assets.getImage("res/arrowAnimationSpriteSheet.png");
	private Image heartSheet = Game.assets.getImage("res/collectibleHeartSheet.png");
	private Image groundedHeart = heartSheet.getSubImage(1, 188, 256, 64);
	private Image coinSheet = Game.assets.getImage("res/coinSpriteSheet.png");
	
	
	private Animation key, coin, arrow, heart, heartSpin;
	private int type;
	
	private boolean pickedup;
	
	public HashMap<Integer, String> collectibles = new HashMap<Integer, String>();
	
	public Commodity(Dungeon dungeon, float x, float y, int type)
	{
		super(dungeon, x, y);
		this.type = type;
		
		arrow = new Animation(new SpriteSheet(arrowSheet, 64, 64), 100);
		heart = new Animation(new SpriteSheet(heartSheet, 64, 64), 200);
		heartSpin = new Animation(new SpriteSheet(groundedHeart, 64 ,64), 200);
		coin = new Animation(new SpriteSheet(coinSheet, 64, 64), 200);
		
		collectibles.put(0, "res/key.png");
		collectibles.put(1, "res/coin.png");
		collectibles.put(2, "res/singleArrow.png");
		collectibles.put(3, "res/heart.png");
		
		this.image = Game.assets.getImage(collectibles.get(type));
		
		if(type==0)
		{
			Game.assets.playSoundEffectWithoutRepeat("keyDrop");
		}
		else if(type==1)
		{
			//Game.assets.playSoundEffectWithoutRepeat("coinDrop");
		}
		else if(type==2)
		{
			//Game.assets.playSoundEffectWithoutRepeat("arrowDrop");
		}
		System.out.println("item dropped");
	}
	
	public void render(Graphics graphics, Camera camera)
	{
		if(this.type == 2)
		{
			arrow.draw(this.x - this.getHalfWidth() - camera.getX(), this.y - this.getHalfHeight() - camera.getY());
		}
		else if(this.type == 3)
		{
			if(!heart.isStopped())
			{
				heart.setLooping(false);
				heart.draw(this.x - this.getHalfWidth() - camera.getX(), this.y - this.getHalfHeight() - camera.getY());
			}
			else
			{
				heartSpin.draw(this.x - this.getHalfWidth() - camera.getX(), this.y - this.getHalfHeight() - camera.getY());
			}
		}
		else if(this.type == 1)
		{
			coin.draw(this.x - this.getHalfWidth() - camera.getX(), this.y - this.getHalfHeight() - camera.getY() - 20);
		}
		
		else super.render(graphics, camera);
	}
	
	@Override
	public void update(int delta)
	{
		if(pickedup)
		{
			this.setX(this.dungeon.gamedata.hero.getX());
			this.setY(this.dungeon.gamedata.hero.getY());
		}
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
