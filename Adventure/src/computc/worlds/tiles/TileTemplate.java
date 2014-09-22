package computc.worlds.tiles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

import computc.Game;

public class TileTemplate
{
	private String name;
	private ArrayList<Image> images = new ArrayList<Image>();
	private Color color;
	
	public boolean passability = true;
	
	public TileTemplate(String filepath)
	{
		try
		{
			Document document = new SAXBuilder().build(filepath);
			Element tileElement = document.getRootElement();
			
			this.name = tileElement.getAttributeValue("name");
			
			Element imageElement = tileElement.getChild("image");
			String source = imageElement.getAttributeValue("source");
			SpriteSheet spritesheet = new SpriteSheet(source, 64, 64);
			for(int sx = 0; sx < spritesheet.getHorizontalCount(); sx++)
				for(int sy = 0; sy < spritesheet.getVerticalCount(); sy++)
					this.images.add(spritesheet.getSprite(sx, sy));
			
			Element colorElement = tileElement.getChild("color");
			int red = colorElement.getAttribute("red").getIntValue();
			int green = colorElement.getAttribute("green").getIntValue();
			int blue = colorElement.getAttribute("blue").getIntValue();
			this.color = new Color(red, green, blue);
			
			for(Element propertyElement : tileElement.getChildren("property"))
			{
				if(propertyElement.getAttribute("passability") != null)
				{
					System.out.println("!");
					this.passability = propertyElement.getAttribute("passability").getBooleanValue();
				}
			}
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public Image getImage()
	{
		return this.images.get(Game.randomness.nextInt(this.images.size()));
	}
	
	public Image getImage(int index)
	{
		return this.images.get(index);
	}
	
	public Color getColor()
	{
		return this.color;
	}
}