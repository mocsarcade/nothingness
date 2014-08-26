package computc;

import java.awt.Point;
import java.util.LinkedList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;


public class TiledWorld extends TiledMap
{
	public Hero hero;
	
	private Tile[][] tiles;
	private LinkedList<Enemy> enemies;
	
	public TiledWorld(String tmx) throws SlickException
	{
		super(tmx);
		
		this.tiles = new Tile[this.getWidth()][this.getHeight()];

		for(int tx = 0; tx < this.getWidth(); tx++)
		{
			for(int ty = 0; ty < this.getHeight(); ty++)
			{
				int tid = this.getTileId(tx, ty, 0);
				
				this.tiles[tx][ty] = new Tile();
				this.tiles[tx][ty].collider = this.getTileProperty(tid, "collider", "false").equals("false");
			}
		}
		
		this.hero = new Hero(this, 5, 0);
		this.populateEnemies();
	}
	
	public int getPixelWidth()
	{
		return this.getWidth() * this.getTileWidth();
	}
	
	public int getPixelHeight()
	{
		return this.getHeight() * this.getTileHeight();
	}
	
	public void update(Input input, int delta)
	{
		hero.update(input, delta);
	}
	
	public void render(Graphics graphics)
	{
		this.render(0, 0);
		hero.render(graphics);
		
		for(int i = 0; i < enemies.size(); i++ )
		{
			enemies.get(i).render(graphics);
		}
	}
	
	private void populateEnemies() throws SlickException
	{
		enemies = new LinkedList<Enemy>();
		
		Point[] points = {new Point(1, 6), new Point(5, 4), new Point(8, 5)};
		
		for(int i = 0; i < points.length; i++) 
		{
			enemies.add(new Thug(this, points[i].x, points[i].y));
		}
	}
}