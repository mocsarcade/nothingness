
package computc.worlds.tiles;

import java.util.HashMap;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.Graphics;

import computc.cameras.Camera;
import computc.worlds.rooms.Room;

public class Tile
{
	private Room room;
	
	private int tx;
	private int ty;
	
	public boolean isBlocked;
	public boolean isStairs;
	
	public Image image;

	public boolean locked;
	
	public Tile(Room room, int tx, int ty, String imagename)
	{
		this.room = room;
		
		this.tx = tx;
		this.ty = ty;
		
		this.image = Tile.images.get(imagename);
	}
	
	public void render(Graphics graphics, Camera camera)
	{
		int x = this.getX() - camera.getX();
		int y = this.getY() - camera.getY();
		
		this.image.draw(x, y);
	}
	
	public void renderOnMap(Graphics graphics, Camera camera)
	{
		int x = (this.getX() / 8) - camera.getX();
		int y = (this.getY() / 8) - camera.getY();
		
		if(this.isBlocked)
		{
			if(this.locked)
			{
				graphics.setColor(Color.yellow);
			}
			else
			{
				graphics.setColor(Color.lightGray);
			}
		}
		else if(this.isStairs)
		{
			graphics.setColor(Color.cyan);
		}
		else
		{
			graphics.setColor(Color.gray);
		}
		
		final int UNIT = Tile.SIZE / 8;
		graphics.fillRect(x, y, UNIT, UNIT);
	}
	
	/*
	 * Returns the horizontal position
	 * of this tile in units of pixels
	 * and relative to the dungeon.
	 * 
	 * @units_of		pixels
	 * @relative_to		dungeon
	 */
	public int getX()
	{
		return this.getTileyX() * Tile.SIZE + this.room.getX();
	}
	
	/*
	 * Returns the vertical position
	 * of this tile in units of pixels
	 * and relative to the dungeon.
	 * 
	 * @units_of		pixels
	 * @relative_to		dungeon
	 */
	public int getY()
	{
		return this.getTileyY() * Tile.SIZE + this.room.getY();
	}
	
	/*
	 * Returns the horizontal position
	 * of this tile in units of tiles
	 * and relative to the room.
	 * 
	 * @units_of		tiles
	 * @relative_to		room
	 */
	public int getTileyX()
	{
		return this.tx;
	}
	
	/*
	 * Returns the vertical position
	 * of this tile in units of tiles
	 * and relative to the room.
	 * 
	 * @units_of		tiles
	 * @relative_to		room
	 */
	public int getTileyY()
	{
		return this.ty;
	}

	/*
	 * Returns the horizontal dimension
	 * of this tile in units of pixels.
	 * 
	 * @units_of		pixels
	 */
	public int getWidth()
	{
		return Tile.SIZE;
	}
	
	/*
	 * Returns the vertical dimension
	 * of this tile in units of pixels.
	 * 
	 * @units_of		pixels
	 */
	public int getHeight()
	{
		return Tile.SIZE;
	}
	
	public void lock()
	{
		this.image = Tile.images.get("door");
		this.isBlocked = true;
		this.locked = true;
	}
	
	public void unlock()
	{
		this.image = Tile.images.get("floor");
		this.isBlocked = false;
		this.locked = false;
	}
	
	public static HashMap<String, Image> images = new HashMap<String, Image>();
	
	public final static int SIZE = 64;

}