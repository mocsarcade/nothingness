package computc.worlds.tiles;

import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

import computc.Game;

public class TileType
{
	private Color color;
	private ArrayList<Image> images;
	
	public TileType(ArrayList<Image> images, Color color)
	{
		this.color = color;
		this.images = images;
	}
	
	public Color getColor()
	{
		return this.color;
	}
	
	public Image getImage()
	{
		return this.images.get(Game.randomness.nextInt(this.images.size()));
	}
	
	public Image getImage(int index)
	{
		return this.images.get(index);
	}
}