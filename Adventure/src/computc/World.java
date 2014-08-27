package computc;

import java.awt.Point;
import java.util.LinkedList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class World 
{
	private TiledRoom tiledRoom;
	public Hero hero;
	//private Room room;
	//private LinkedList<Enemy> enemies;
	
	public World(String tmx) throws SlickException
	{
		
		this.hero = new Hero(this, 5, 0);
		
		try 
		{
		tiledRoom = new TiledRoom("res/world.tmx");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	public void update(Input input, int delta)
	{
		this.hero.update(input, delta);
		tiledRoom.setPosition(Adventure.SCREEN_WIDTH/ 2 - hero.getX(), Adventure.SCREEN_HEIGHT/ 2 - hero.getY() );
	}
	
	public void render(Graphics graphics)
	{
		this.tiledRoom.render();
		this.hero.render(graphics);
	}
	
	public void moveRoom() 
	{
//		tiledRoom.setPosition()
	}
}