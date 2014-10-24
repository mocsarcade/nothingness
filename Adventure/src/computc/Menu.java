package computc;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import computc.cameras.Camera;
import computc.entities.Hero;
import computc.worlds.dungeons.Dungeon;
import computc.worlds.rooms.Room;

public class Menu
{
	private GameData gamedata;
	
	private Image heart;
	private Image arrow = new Image("res/arrowSpriteSheet.png");
	private Image arrowGuiPic = new Image("res/arrowGuiPic.png");
	
	public Menu(GameData gamedata) throws SlickException
	{
		this.gamedata = gamedata;
		
		this.heart = Game.assets.getImage("res/heart.png");
	}
	
	public void render(Graphics graphics, Camera camera)
	{
		graphics.setColor(Color.black);
		graphics.fillRect(0, Room.HEIGHT, Room.WIDTH, Menu.HEIGHT);
		
		int MAP_WIDTH = 5, MAP_HEIGHT = 5;
		int UNIT = 12, MARGIN = 3, OFFSET = 29, MARKER = 6;
		
		for(int i = 0; i < MAP_WIDTH; i++)
		{
			for(int j = 0; j < MAP_HEIGHT; j++)
			{
				int dx = (int)(Math.floor((float)(this.gamedata.hero.getRoomyX()) / MAP_WIDTH));
				int dy = (int)(Math.floor((float)(this.gamedata.hero.getRoomyY()) / MAP_HEIGHT));
				
				int rx = (dx * MAP_WIDTH) + i;
				int ry = (dy * MAP_HEIGHT) + j;
				
				int x = OFFSET + MARGIN + (i * (UNIT + MARGIN));
				int y = OFFSET + MARGIN + (j * (UNIT + MARGIN));
				
				Room room = this.gamedata.dungeon.getRoom(rx, ry);
				
				if(room != null && room.hasVisited)
				{
					graphics.setColor(Color.lightGray);
					graphics.fillRoundRect(x, y, UNIT, UNIT, 3);
					
					/*if(room.hasNorthernRoom()) {graphics.fillRect(x + (UNIT / 2) - 1, y - MARGIN, MARGIN, MARGIN);}
					if(room.hasSouthernRoom()) {graphics.fillRect(x + (UNIT / 2) - 1, y + UNIT, MARGIN, MARGIN);}
					if(room.hasEasternRoom()) {graphics.fillRect(x + UNIT, y + (UNIT / 2) - 1, MARGIN, MARGIN);}
					if(room.hasWesternRoom()) {graphics.fillRect(x - MARGIN, y + (UNIT / 2) - 1, MARGIN, MARGIN);}*/
					
					if(rx == this.gamedata.hero.getRoomyX()
					&& ry == this.gamedata.hero.getRoomyY())
					{
						graphics.setColor(Color.white);
						graphics.fillOval(x + (UNIT / 2) - (MARKER / 2), y + (UNIT / 2) - (MARKER / 2), MARKER, MARKER);
					}
				}
				else
				{
					graphics.setColor(Color.darkGray);
					graphics.fillRoundRect(x, y, UNIT, UNIT, 3);
				}
				
				if(rx == this.gamedata.dungeon.lastRoom.getRoomyX()
				&& ry == this.gamedata.dungeon.lastRoom.getRoomyY())
				{
					graphics.setColor(new Color(0, 0, this.gamedata.dungeon.lastRoom.blueness));
					graphics.fillOval(x + (UNIT / 2) - (MARKER / 2), y + (UNIT / 2) - (MARKER / 2), MARKER, MARKER);
				}
			}
		}
		
		graphics.setColor(Color.white);
		graphics.drawString("Hit M to", UNIT * 3, UNIT * 9);
		graphics.drawString("open Map", UNIT * 3, UNIT * 10);
		
		for(int h = 0; h < this.gamedata.hero.getHealth(); h++)
		{
			heart.draw(400 + (70 * h), 1);
		}
		
		// draw arrow count HUD
		

			arrowGuiPic.draw(-25, 425);

		graphics.setColor(Color.white);

		graphics.drawString(String.valueOf(this.gamedata.hero.arrowCount), 30, 540);
		
		graphics.setColor(Color.yellow);
		graphics.drawString(String.valueOf(this.gamedata.hero.coinage), 665, 540);
	}
	
	public static final int HEIGHT = 128;
}