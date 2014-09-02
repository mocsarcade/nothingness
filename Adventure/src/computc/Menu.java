package computc;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class Menu
{
	private Hero hero;
	private Dungeon dungeon;
	
	public Menu(Dungeon dungeon, Hero hero)
	{
		this.hero = hero;
		this.dungeon = dungeon;
	}
	
	public void render(Graphics graphics, Camera camera)
	{
		graphics.setColor(Color.black);
		graphics.fillRect(0, Room.HEIGHT, Room.WIDTH, Menu.HEIGHT);

		for(int rx = 0; rx < 9; rx++)
		{
			for(int ry = 0; ry < 5; ry++)
			{
				int UNIT = 22, MARGIN = 3;
				int x = MARGIN + (rx * (UNIT + MARGIN));
				int y = (Room.HEIGHT + MARGIN) + (ry * (UNIT + MARGIN));
				
				Room room = this.dungeon.getRoom(rx, ry);
				
				if(room != null	&& room.visited)
				{
					graphics.setColor(Color.lightGray);
					graphics.fillRect(x, y, UNIT, UNIT);

					if(room.hasNorthernRoom()) {graphics.fillRect(x + (UNIT / 2) - 1, y - MARGIN, MARGIN, MARGIN);}
					if(room.hasSouthernRoom()) {graphics.fillRect(x + (UNIT / 2) - 1, y + UNIT, MARGIN, MARGIN);}
					if(room.hasEasternRoom()) {graphics.fillRect(x + UNIT, y + (UNIT / 2) - 1, MARGIN, MARGIN);}
					if(room.hasWesternRoom()) {graphics.fillRect(x - MARGIN, y + (UNIT / 2) - 1, MARGIN, MARGIN);}
					
					if(rx == this.hero.getRoomyX()
					&& ry == this.hero.getRoomyY())
					{
						graphics.setColor(Color.white);
						graphics.fillOval(x + 3, y + 3, 16, 16);
					}
				}
				else
				{
					graphics.setColor(Color.darkGray);
					graphics.fillRect(x, y, UNIT, UNIT);
				}
			}
		}
	}
	
	public static final int HEIGHT = 128;
}