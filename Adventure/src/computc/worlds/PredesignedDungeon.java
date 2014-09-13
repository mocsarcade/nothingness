package computc.worlds;

import java.awt.Point;
import java.util.LinkedList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.tiled.TiledMap;

import computc.cameras.Camera;
import computc.entities.BigThug;
import computc.entities.Enemy;
import computc.entities.OldMan;
import computc.entities.Thug;

public class PredesignedDungeon extends Dungeon
{
	public Point[] thug_positions_in_tiley_coordinates;
	public LinkedList<Enemy> thugs;
	public OldMan oldman;
	
	private Image menuBox;
	private Image largeTextBox;
	private int counter, counter2;
	Animation textBox;
	public Color textColor = Color.white;
	private boolean nextLevel = false;
	
	public PredesignedDungeon() throws SlickException
	{
		TiledMap tiled = new TiledMap("./res/dungeons/prototype.dungeon.tmx");
		
		int ROOMY_WIDTH = 9;
		int ROOMY_HEIGHT = 5;
		
		for(int rx = 0; rx < ROOMY_WIDTH; rx++)
		{
			for(int ry = 0; ry < ROOMY_HEIGHT; ry++)
			{
				Room room = new Room(this, rx, ry, null);
				
				for(int tx = 0; tx < Room.TILEY_WIDTH; tx++)
				{
					for(int ty = 0; ty < Room.TILEY_HEIGHT; ty++)
					{
						int rxtx = (rx * Room.TILEY_WIDTH) + tx;
						int ryty = (ry * Room.TILEY_HEIGHT) + ty;
						int tid = tiled.getTileId(rxtx, ryty, 0);
						Tile tile = new Tile(room, tx, ty, tid);
						
						room.setTile(tx, ty, tile);
					}
				}
				
				this.addRoom(room);
			}
		}
		
		for(int rx = 0; rx < ROOMY_WIDTH; rx++)
		{
			for(int ry = 0; ry < ROOMY_HEIGHT; ry++)
			{
				Room room = this.getRoom(rx, ry);
				
				if(!room.getTile(Room.TILEY_WIDTH / 2, 0).isBlocked)
				{
					room.connectNorthernRoom(this.getRoom(rx, ry - 1));
				}
				if(!room.getTile(Room.TILEY_WIDTH / 2, Room.TILEY_HEIGHT - 1).isBlocked)
				{
					room.connectSouthernRoom(this.getRoom(rx, ry + 1));
				}
				if(!room.getTile(Room.TILEY_WIDTH - 1, Room.TILEY_HEIGHT / 2).isBlocked)
				{
					room.connectEasternRoom(this.getRoom(rx + 1, ry));
				}
				if(!room.getTile(0, Room.TILEY_HEIGHT / 2).isBlocked)
				{
					room.connectWesternRoom(this.getRoom(rx - 1, ry));
				}
			}
		}
		
		this.thugs = new LinkedList<Enemy>();
		
		thug_positions_in_tiley_coordinates = new Point[] {
				new Point(34, 5),
				new Point(42, 2),
				new Point(39, 5),
				new Point(46, 2),
				new Point(52, 6),
				new Point(48, 1),
				new Point(47, 13),
				new Point(50, 15),
				new Point(59, 12),
				new Point(60, 14),
				new Point(61, 12),
				new Point(57, 20),
				new Point(63, 24),
				new Point(57, 24),
				new Point(49, 21),
				new Point(49, 25)
			};
		
		for(Point point : thug_positions_in_tiley_coordinates)
		{
			thugs.add(new Thug(this, point.x, point.y));
		}
		
		thugs.add(new BigThug(this, 36, 23));
		this.oldman = new OldMan(this, 38, 12);
		
		this.menuBox = new Image("res/textBox.png");
		this.largeTextBox = new Image("res/largeTextBox.png");
		this.textBox = new Animation(new SpriteSheet(largeTextBox, 585, 100), 100);
	}
	
	public void update(int delta)
	{
		for(int i = 0; i < thugs.size(); i++)
		{
			Enemy e = thugs.get(i);
			e.update(delta);
				if(e.isDead())
				{
					thugs.remove(i);
					i--;
				}
		}
		
		this.oldman.update(delta);
		
		//hero.checkAttack(dungeon.thugs);
		
		/*if(dungeon.getTile(hero.getX(), hero.getY()).isStairs)
		{
			nextLevel = true;
		}*/
	}
	
	public void render(Graphics graphics, Camera camera)
	{
		super.render(graphics, camera);
		
		for(Enemy thug : this.thugs)
		{
			thug.render(graphics, camera);
		}
		
		this.oldman.render(graphics, camera);
	}
}