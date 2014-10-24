package computc;

import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.SlickException;

import computc.worlds.rooms.Room;
import computc.states.MainGameState;
import computc.states.DungeonMapGameState;
import computc.states.TitleScreen;
import computc.states.ToNextLevelGameState;
import computc.states.YouDiedGameState;
import computc.states.TutorialState;
import computc.states.YouWonGameState;

public class Game extends StateBasedGame
{
	public static boolean reset;
	public static boolean devmode = false;

	
	public Game()
	{
		super(Game.TITLE + " " + Game.VERSION);
	}
	
	public void initStatesList(GameContainer container) throws SlickException
	{
		GameData gamedata = new GameData();
		
		this.addState(new TitleScreen(gamedata));
        this.addState(new MainGameState(gamedata));
		this.addState(new TutorialState(gamedata));
        this.addState(new DungeonMapGameState(gamedata));
        this.addState(new ToNextLevelGameState(gamedata));
        this.addState(new YouDiedGameState(gamedata));
        this.addState(new YouWonGameState());
        
		/*if(Game.devmode)
		{
			gamedata.level = 0;
		}*/
	}
	
	public static void main(String[] args) throws SlickException
	{
		if(args.length > 0 && args[0].equals("devmode"))
		{
			System.out.println("entering devmode");
			Game.devmode = true;
		}
		
		AppGameContainer container = new AppGameContainer(new Game());
		container.setDisplayMode(Game.WIDTH, Game.HEIGHT, false);
		container.setTargetFrameRate(60);
		container.start();
	}
	
	public static final String TITLE = "Game";
	public static final String VERSION = "v0.3.0";
	
	public static final int WIDTH = Room.WIDTH;
	public static final int HEIGHT = Room.HEIGHT;
	
	public static Random random = new Random();
	public static AssetManager assets = new AssetManager();
	public static String difficulty = "EASY";
}