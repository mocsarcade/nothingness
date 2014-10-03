package computc.worlds.rooms;

import java.util.ArrayList;
import java.util.List;
import java.awt.Point;
import java.io.IOException;

import org.jdom2.Element;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.DataConversionException;

public class RoomLayout
{
	private int[][] tileGIDs = new int[Room.TILEY_WIDTH][Room.TILEY_HEIGHT];
	public ArrayList<Point> thugs = new ArrayList<Point>();
	public ArrayList<Point> maniacs = new ArrayList<Point>();
	public ArrayList<Point> loafers = new ArrayList<Point>();
	
	public RoomLayout(String roomSource) throws IOException, JDOMException
	{
		this(new SAXBuilder().build(roomSource).getRootElement());
	}
	
	public RoomLayout(Element roomElement) throws JDOMException
	{
		for(Element layerElement : roomElement.getChildren("layer"))
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
						this.tileGIDs[tx][ty] = gid;
					}
				}
			}
			else if(layerElement.getAttributeValue("name").equals("enemies"))
			{
				List<Element> tileElements = layerElement.getChild("data").getChildren();
				
				for(int tx = 0; tx < Room.TILEY_WIDTH; tx++)
				{
					for(int ty = 0; ty < Room.TILEY_HEIGHT; ty++)
					{
						Element tileElement = tileElements.get(ty * Room.TILEY_WIDTH + tx);
						int gid = tileElement.getAttribute("gid").getIntValue();
						if(gid == 3) this.thugs.add(new Point(tx, ty));
						if(gid == 6) this.loafers.add(new Point(tx, ty));
						if(gid == 7) this.maniacs.add(new Point(tx, ty));
					}
				}
			}
		}
	}
	
	public int getTileGID(int tx, int ty)
	{
		return this.tileGIDs[tx][ty];
	}
	
	public int getKeyX()
	{
		return Room.TILEY_WIDTH / 2;
	}
	
	public int getKeyY()
	{
		return Room.TILEY_HEIGHT / 2;
	}
}