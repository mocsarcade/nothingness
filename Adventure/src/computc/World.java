package computc;

import java.awt.Point;
import java.util.LinkedList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class World extends TiledRoom
{
	
	// bounds
	private int xmin;
	private int ymin;
	private int xmax;
	private int ymax;
	
	public Hero hero;
	//private Room room;
	//private LinkedList<Enemy> enemies;
	
	public World(String tmx) throws SlickException
	{
		super(tmx);
		this.hero = new Hero(this, 0, 0);
		hero.setPosition(100, 100);
		
		this.loadRoom();
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
		render(x, y);

		this.hero.render(graphics);
	}
	
	
	public void setPosition(double x, double y) 
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
	}
	
}