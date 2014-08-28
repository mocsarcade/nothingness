package computc;

import java.awt.Point;
import java.util.LinkedList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class World
{
	private int xmin;
	private int ymin;
	private int xmax;
	private int ymax;
	
	public Hero hero;
	private TiledMap map;
	private LinkedList<Enemy> enemies;
	
	public World() throws SlickException
	{
		this.hero = new Hero(this, 4, 0);
		this.map = new TiledMap("res/world.tmx");
		this.enemies = new LinkedList<Enemy>();
		
		for(Point point : new Point[] {new Point(1, 6), new Point(4, 4), new Point(8, 4)})
		{
		 	this.enemies.add(new Thug(this, point.x, point.y));
		}
	}
	
	public void update(Input input, int delta)
	{
		if(Hero.nextArea && hero.direction == Direction.NORTH)
		this.setPosition(Adventure.SCREEN_WIDTH - hero.getX() - 500 , Adventure.SCREEN_HEIGHT - hero.getY() - 50);
		
		if(Hero.nextArea && hero.direction == Direction.SOUTH)
		this.setPosition(Adventure.SCREEN_WIDTH - hero.getX() - 500 , Adventure.SCREEN_HEIGHT - hero.getY() - 700);
		
		if(Hero.nextArea && hero.direction == Direction.EAST)
		this.setPosition(Adventure.SCREEN_WIDTH - hero.getX() - 850 , Adventure.SCREEN_HEIGHT - hero.getY() - 500);
		
		if(Hero.nextArea && hero.direction == Direction.WEST)
		this.setPosition(Adventure.SCREEN_WIDTH - hero.getX() - 50, Adventure.SCREEN_HEIGHT - hero.getY() - 500);
		
		this.hero.update(input, delta);
		
	}
	
	public void render(Graphics graphics)
	{
		this.map.render(graphics, camera);

		this.hero.render(graphics);
		
		for(Enemy enemy : this.enemies)
		{
			enemy.render(graphics);
		}
	}
	
	/*public void setPosition(double x, double y) 
	{
		cameraTweaker = 0.005;
		
		this.x += (x - this.x) * cameraTweaker;
		this.y += (y - this.y) * cameraTweaker;
		
		loadMap();
		fixBounds();
		
	}
	
	public void loadMap()
	{
		xmin = (Adventure.SCREEN_WIDTH) - getPixelWidth();
		xmax = 0;
		ymin = (Adventure.SCREEN_HEIGHT) - getPixelHeight();
		ymax = 0;
	}
	
	
	private void fixBounds() 
	{
		if(x < xmin) x = xmin;      
		if(y < ymin) y = ymin;
		if (x > xmax) x= xmax;
		if (y> ymax) y = ymax;
	}*/
}