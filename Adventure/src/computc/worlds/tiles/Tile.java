
package computc.worlds.tiles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import computc.cameras.Camera;
import computc.worlds.rooms.Room;

public class Tile
{
	protected Room room;
	
	protected int tx;
	protected int ty;
	
	protected Image image;
	protected Color color;
	
	public Tile(Room room, int tx, int ty, String type)
	{
		this.room = room;
		
		this.tx = tx;
		this.ty = ty;

		this.image = this.room.dungeon.tileTemplates.get(type).getImage();
		this.color = this.room.dungeon.tileTemplates.get(type).getColor();
	}
	
	public void update(int delta)
	{
		return;
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
		
		final int UNIT = Tile.SIZE / 8;
		
		graphics.setColor(this.color);
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

	/*
	 * Returns whether an entity can
	 * move here without collision.
	 */
	public boolean canMoveHere()
	{
		return true;
	}
	
	public final static int SIZE = 64;
}