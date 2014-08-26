package computc;

import java.awt.Point;
import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;


public class TiledWorld extends TiledMap
{
	// position
	private int x;
	private int y;
	
	
	private ArrayList<Enemy> enemies;
	private Hero hero;
	private Thug thug;
	private Tile[][] tiles;
	
	public TiledWorld(String tmx) throws SlickException
	{
		super(tmx);
		populateEnemies();
		
		tiles = new Tile[this.getWidth()][this.getHeight()];

		for(int tx = 0; tx < this.getWidth(); tx++)
		{
			for(int ty = 0; ty < this.getHeight(); ty++)
			{
				int tid = this.getTileId(tx, ty, 0);
				
				tiles[tx][ty] = new Tile();
				tiles[tx][ty].collider = this.getTileProperty(tid, "collider", "false").equals("false");
			}
		}
		
		
		
		hero = new Hero(this, 5, 0);
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
	
	public void render(Graphics g)
	{
		this.render(x, y);
		hero.render(g);
		
		for(int i = 0; i < enemies.size(); i++ ) {
			enemies.get(i).render(g);
		}
	}
	
	private void populateEnemies() throws SlickException
	{
		enemies = new ArrayList<Enemy>();
		
		Thug thug;
		Point[] points = new Point[] {new Point(1, 6), new Point(4, 4), new Point(8, 4)};
		
		for(int i = 0; i < points.length; i++) 
		{
			thug = new Thug(this, points[i].x, points[i].y);
			
			enemies.add(thug);
		}
		
		thug = new Thug(this, 5, 7);

	}
	
	
	public int getX()
	{
	return x;
	}
	
	public int getY()
	{
		return y;
	}
}