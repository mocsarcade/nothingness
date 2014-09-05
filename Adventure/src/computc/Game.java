package computc;

import java.awt.Point;
import java.util.LinkedList;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Game extends BasicGame
{
	public Hero hero;
	public Dungeon dungeon;
	public Camera camera;
	public Menu menu;
	public LinkedList<Thug> thugs = new LinkedList<Thug>();
	
	public Game()
	{
		super(Game.TITLE);
	}
	
	public void init(GameContainer container) throws SlickException
	{
		this.dungeon = new Dungeon();
		this.hero = new Hero(dungeon, dungeon.getRoom(3, 0), 5, 1);
		this.camera = new Camera(hero);
		this.menu = new Menu(dungeon, hero);
		
		Point[] thug_positions_in_tiley_coordinates = new Point[] {
			new Point(34, 5),
			new Point(42, 2),
			new Point(39, 5),
			new Point(46, 2),
			new Point(52, 6),
			new Point(47, 13),
			new Point(50, 15)
		};
		
		for(Point point : thug_positions_in_tiley_coordinates)
		{
			thugs.add(new Thug(this.dungeon, point.x, point.y));
		}

		Tile.WALL_IMAGE = new Image("./res/wall.png");
		Tile.FLOOR_IMAGE = new Image("./res/floor.png");
	}
	
	public void update(GameContainer container, int delta) throws SlickException
	{
		Input input = container.getInput();
		
		this.hero.update(input, delta);
		
		for(Thug thug : this.thugs)
		{
			thug.update(delta);
		}
		
		this.camera.update(delta);
	}
	
	public void render(GameContainer container, Graphics graphics) throws SlickException
	{
		this.dungeon.render(graphics, camera);
		
		for(Thug thug : this.thugs)
		{
			thug.render(graphics, camera);
		}
		
		this.hero.render(graphics, camera);
		this.menu.render(graphics, camera);
	}
	
	public static void main(String[] args)
	{
		try
		{
			AppGameContainer container = new AppGameContainer(new Game());
			container.setDisplayMode(Room.WIDTH, Room.HEIGHT, false);
			container.start();
		}
		catch(Exception error)
		{
			System.out.println(error.getMessage());
		}
	}
	
	public static final String TITLE = "We don't need no title.";
}