package computc;

import java.util.LinkedList;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Game extends BasicGame
{
	public Hero hero;
	public Camera camera;
	public Dungeon dungeon;
	public Thug firstThug;
	public Thug secondThug;
	
	public Game()
	{
		super(GAME_TITLE);
	}
	
	public void init(GameContainer container) throws SlickException
	{
		this.dungeon = new Dungeon();
		this.hero = new Hero(dungeon, 5, 1);
		this.camera = new Camera(hero);
		this.firstThug = new Thug(0, 0, 1, 1);
		this.secondThug = new Thug(0, 1, 1, 1);
	}
	
	public void update(GameContainer container, int delta) throws SlickException
	{
		Input input = container.getInput();
		
		this.hero.update(input, delta);
		this.dungeon.update(delta);
		this.firstThug.update(delta);
		this.secondThug.update(delta);
		this.camera.update(delta);
	}
	
	public void render(GameContainer container, Graphics graphics) throws SlickException
	{
		this.dungeon.render(graphics, camera);
		this.hero.render(graphics, camera);
		this.firstThug.render(graphics, camera);
		this.secondThug.render(graphics, camera);
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
	
	public static final String GAME_TITLE = "We don't need no title.";
}