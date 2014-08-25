package computc;

import java.util.LinkedList;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Adventure extends BasicGame
{
	public Adventure()
	{
		super("We don't have a name yet.");
	}
	
	public void init(GameContainer container) throws SlickException
	{
		//code goes here
	}
	
	public void update(GameContainer container, int delta) throws SlickException
	{
		//code goes here
	}
	
	public void render(GameContainer container, Graphics graphics) throws SlickException
	{
		//code goes here
	}
	
	public static void main(String[] args)
	{
		try
		{
			AppGameContainer container = new AppGameContainer(new Adventure());
			container.setDisplayMode(SCREEN_WIDTH * TILE_SIZE, SCREEN_HEIGHT * TILE_SIZE, false);
			container.start();
		}
		catch(Exception error)
		{
			System.out.println(error.getMessage());
		}
	}
	
	public static final int SCREEN_WIDTH = 11;
	public static final int SCREEN_HEIGHT = 7;
	public static final int TILE_SIZE = 64;
}

// I made a change to the correct fork this time. Yay!
