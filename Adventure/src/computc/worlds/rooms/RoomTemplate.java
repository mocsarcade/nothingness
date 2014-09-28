package computc.worlds.rooms;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import computc.Game;
import computc.worlds.tiles.Tile;

public class RoomTemplate
{
	private int tiles[][] = new int[Room.TILEY_WIDTH][Room.TILEY_HEIGHT];
	private ArrayList<Point> enemies = new ArrayList<Point>();
	private ArrayList<Point> chests = new ArrayList<Point>();
	
	public RoomTemplate(String filepath)
	{
		try
		{
			Document document = new SAXBuilder().build(filepath);
			Element mapElement = document.getRootElement();
			
			for(Element layerElement : mapElement.getChildren("layer"))
			{
				if(layerElement.getAttributeValue("name").equals("tiles"))
				{
					List<Element> tileElements = layerElement.getChild("data").getChildren();
					
					for(int tx = 0; tx < Room.TILEY_WIDTH; tx++)
					{
						for(int ty = 0; ty < Room.TILEY_HEIGHT; ty++)
						{
							Element tileElement = tileElements.get(ty * Room.TILEY_WIDTH + tx);
							int gid = tileElement.getAttribute("gid").getIntValue();
							this.tiles[tx][ty] = gid;
						}
					}
				}
				else if(layerElement.getAttributeValue("name").equals("spawns"))
				{
					List<Element> tileElements = layerElement.getChild("data").getChildren();
					
					for(int tx = 0; tx < Room.TILEY_WIDTH; tx++)
					{
						for(int ty = 0; ty < Room.TILEY_HEIGHT; ty++)
						{
							Element tileElement = tileElements.get(ty * Room.TILEY_WIDTH + tx);
							int gid = tileElement.getAttribute("gid").getIntValue();
							
							if(gid == 3)
							{
								Point point = new Point(tx, ty);
								this.enemies.add(point);
							}
							else if(gid == 4)
							{
								Point point = new Point(tx, ty);
								this.chests.add(point);
							}
						}
					}
				}
			}
			
			List<Element> tilelayer = document.getRootElement().getChild("layer").getChild("data").getChildren();
			//List<Element> objectgroup = tmx.getRootElement().getChild("objectgroup").getChildren();
			
			for(int tx = 0; tx < Room.TILEY_WIDTH; tx++)
			{
				for(int ty = 0; ty < Room.TILEY_HEIGHT; ty++)
				{
					Element element = tilelayer.get(ty * Room.TILEY_WIDTH + tx);
					int gid = element.getAttribute("gid").getIntValue();
					tiles[tx][ty] = gid;
				}
			}
			
			/*for(Element element : objectgroup)
			{
				if(element.getAttribute("gid").getIntValue() == 4)
				{
					int x = element.getAttribute("x").getIntValue() + (48 / 2);
					int y = element.getAttribute("y").getIntValue() - (48 / 2);
					
					this.dungeon.enemies.add(new Thug(this.dungeon, this, x, y));
				}
				else if(element.getAttribute("gid").getIntValue() == 5)
				{
					this.keyX = element.getAttribute("x").getIntValue() + (64 / 2);
					this.keyY = element.getAttribute("y").getIntValue() - (32 / 2);
				}
				else if(element.getAttribute("gid").getIntValue() == 6)
				{
					int x = element.getAttribute("x").getIntValue() + (16 / 2);
					int y = element.getAttribute("y").getIntValue() - (16 / 2);
					
					this.dungeon.coins.add(new Coin(this.dungeon, this, x, y));
				}
			}*/
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
	}

	public int getTileID(int tx, int ty)
	{
		return this.tiles[tx][ty];
	}

	public Point getRandomChestSpawnpoint()
	{
		return this.chests.get(Game.random.nextInt(this.chests.size()));
	}
}