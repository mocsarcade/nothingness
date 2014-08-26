package computc;

import java.awt.Point;
import java.util.LinkedList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class World
{
	public Hero hero;
	//private Room room;
	//private LinkedList<Enemy> enemies;
	
	public World() throws SlickException
	{
		this.hero = new Hero(this, 5, 0);
	}
	
	public void update(Input input, int delta)
	{
		this.hero.update(input, delta);
	}
	
	public void render(Graphics graphics)
	{
		this.hero.render(graphics);
	}
}