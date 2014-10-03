package computc.worlds.tiles;

import java.io.IOException;
import java.util.ArrayList;

import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

import computc.Game;

public class TileSubSet
{
	private ArrayList<Image> images = new ArrayList<Image>();
	private Color color = new Color(255, 211, 248);

	public TileSubSet(String tilesubsetSource) throws IOException, JDOMException
	{
		this(new SAXBuilder().build(tilesubsetSource).getRootElement());
	}
	
	public TileSubSet(Element tilesubsetElement) throws JDOMException
	{
		Element imageElement = tilesubsetElement.getChild("image");
		String imageSource = imageElement.getAttributeValue("source");
		Image image = Game.assets.getImage(imageSource);
		int ts = Tile.SIZE;
		for(int tx = 0; tx < image.getWidth() / Tile.SIZE; tx++)
			for(int ty = 0; ty < image.getHeight() / Tile.SIZE; ty++)
				this.images.add(image.getSubImage(tx * ts, ty * ts, ts, ts));
		
		Element tilesubsetColorElement = tilesubsetElement.getChild("color");
		int red = tilesubsetColorElement.getAttribute("red").getIntValue();
		int green = tilesubsetColorElement.getAttribute("green").getIntValue();
		int blue = tilesubsetColorElement.getAttribute("blue").getIntValue();
		this.color = new Color(red, green, blue);
	}
	
	public Image getImage(int index)
	{
		if(index < this.images.size())
		{
			return this.images.get(index);
		}
		else
		{
			return this.images.get(this.images.size() - 1);
		}
	}
	
	public Image getRandomImage()
	{
		return this.images.get(Game.random.nextInt(this.images.size()));
	}
	
	public Color getColor()
	{
		return this.color;
	}
}