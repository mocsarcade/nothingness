package computc;

import java.awt.Point;
import java.util.LinkedList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class World
{
	public Hero hero;
	public TiledRoom room;
	public LinkedList<Enemy> enemies;
	
	public World() throws SlickException
	{
		this.room = new TiledRoom();
		
		this.hero = new Hero(this, 5, 0);
		
		this.enemies = new LinkedList<Enemy>();
		
		for(Point point : new Point[] {new Point(1, 6), new Point(5, 4), new Point(8, 5)})
		{
		 	this.enemies.add(new Thug(this, point.x, point.y));
		}
	}
	
	public void update(Input input, int delta)
	{
		/*if(Hero.nextArea && hero.direction == Direction.NORTH)
		this.setPosition(Adventure.SCREEN_WIDTH - hero.getX() - 500 , Adventure.SCREEN_HEIGHT - hero.getY() - 50);
		
		if(Hero.nextArea && hero.direction == Direction.SOUTH)
		this.setPosition(Adventure.SCREEN_WIDTH - hero.getX() - 500 , Adventure.SCREEN_HEIGHT - hero.getY() - 700);
		
		if(Hero.nextArea && hero.direction == Direction.EAST)
		this.setPosition(Adventure.SCREEN_WIDTH - hero.getX() - 850 , Adventure.SCREEN_HEIGHT - hero.getY() - 500);
		
		if(Hero.nextArea && hero.direction == Direction.WEST)
		this.setPosition(Adventure.SCREEN_WIDTH - hero.getX() - 50, Adventure.SCREEN_HEIGHT - hero.getY() - 500);*/
		
		this.hero.update(input, delta);
		
	}
	
	public void render(Graphics graphics)
	{
		this.room.render(graphics);

		this.hero.render(graphics);
		
		for(Enemy enemy : this.enemies)
		{
			enemy.render(graphics);
		}
	}
}