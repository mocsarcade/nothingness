package computc.states;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import computc.Game;
import computc.GameData;

public class TitleScreen extends BasicGameState
{
	private GameData gamedata;
	
	Animation titleScreen, logoScreen;
	int titleScreenDuration = 200;
	int[] logoScreenDuration = {1000, 200, 200, 200, 200, 200, 200, 200};
	int[] logoScreenFrames = {0, 0, 1, 0, 2, 0, 3, 0, 0, 1, 1, 1, 2, 1, 3, 1};
	public int cursor = 0;
	public int cursor_time = 0;
	public int logoTimer;
	public int logoStop = 4500;
	
	private Image splashScreen = Game.assets.getImage("res/textScreens/splashScreen.png");

	public TitleScreen(GameData gamedata)
	{
		this.gamedata = gamedata;
		
		Image[] menu = { Game.assets.getImage("res/textScreens/Main-Menu-3.png"), Game.assets.getImage("res/textScreens/Main-Menu-4.png"), Game.assets.getImage("res/textScreens/Main-Menu-5.png"), Game.assets.getImage("res/textScreens/Main-Menu-6.png"), Game.assets.getImage("res/textScreens/Main-Menu-7.png"), Game.assets.getImage("res/textScreens/Main-Menu-8.png"), Game.assets.getImage("res/textScreens/Main-Menu-9.png"), Game.assets.getImage("res/textScreens/Main-Menu-10.png"), Game.assets.getImage("res/textScreens/Main-Menu-11.png"), Game.assets.getImage("res/textScreens/Main-Menu-12.png"), Game.assets.getImage("res/textScreens/Main-Menu-13.png"), Game.assets.getImage("res/textScreens/Main-Menu-14.png"), Game.assets.getImage("res/textScreens/Main-Menu-15.png"), Game.assets.getImage("res/textScreens/Main-Menu-16.png"), Game.assets.getImage("res/textScreens/Main-Menu-17.png"), Game.assets.getImage("res/textScreens/Main-Menu-18.png"), Game.assets.getImage("res/textScreens/Main-Menu-19.png"), Game.assets.getImage("res/textScreens/Main-Menu-20.png"), Game.assets.getImage("res/textScreens/Main-Menu-21.png"), Game.assets.getImage("res/textScreens/Main-Menu-22.png"), Game.assets.getImage("res/textScreens/Main-Menu-23.png"), Game.assets.getImage("res/textScreens/Main-Menu-24.png"), Game.assets.getImage("res/textScreens/Main-Menu-25.png"), Game.assets.getImage("res/textScreens/Main-Menu-26.png"), Game.assets.getImage("res/textScreens/Main-Menu-27.png"), Game.assets.getImage("res/textScreens/Main-Menu-28.png")};
		
		titleScreen = new Animation(menu, titleScreenDuration, true);
		logoScreen = new Animation(new SpriteSheet(splashScreen, 704, 576), logoScreenFrames, logoScreenDuration);
		logoScreen.setPingPong(true);
		titleScreen.setLooping(false);
	}
	
	public void init(GameContainer container, StateBasedGame game) throws SlickException
	{
		//code goes here.
	}
	
	public void enter(GameContainer container, StateBasedGame game)
	{
		this.cursor = 0;
		
		if(this.gamedata.level > 0)
		{
			this.gamedata.level = 0;
		}
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException
	{
		Input input = container.getInput();
		
		if(logoTimer < logoStop)
		{
			logoTimer += delta;
		}
		
		if(this.titleScreen.isStopped())
		{
			if(input.isKeyDown(Input.KEY_ENTER) || input.isKeyDown(Input.KEY_SPACE))
			{
				Game.assets.playSoundEffectWithoutRepeat("menuSelect");
				if(this.cursor == 2)
				{
					System.exit(0);
				}
				else
				{
					this.gamedata.instantiate();
					
					if(this.cursor == 1)
					{
						Game.difficulty = "HARD";
					}
					else if(this.cursor == 0)
					{
						Game.difficulty = "EASY";
					}
					
					if(this.gamedata.level < 0)
					{
						game.enterState(TutorialState.ID, new FadeOutTransition(Color.black, 100), new FadeInTransition(Color.black, 1000));
					}
					else
					{
						game.enterState(MainGameState.ID, new FadeOutTransition(Color.black, 100), new FadeInTransition(Color.black, 1000));
					}
				}
			}
		}
		
		if(logoTimer > logoStop)
		{
			titleScreen.update(delta);
			cursor_time += delta;
		}
	}
	
	public void render(GameContainer container, StateBasedGame game, Graphics graphics) throws SlickException
	{
		if(logoTimer < logoStop)
		{
			logoScreen.draw(0, 0);
		}
		
		else
		{
		
			graphics.setColor(Color.white);
		
			titleScreen.draw(0, 0);
		
			if(titleScreen.isStopped())
			{
				graphics.setColor(Color.white);
			
				if(cursor_time % 1000 < 750)
				{
					graphics.fillOval(75, 335+(65*cursor), 10, 10);
				}
				else
				{
					graphics.drawOval(75, 335+(65*cursor), 10, 10);
				}
			
				if(this.cursor == 0)
				{
					graphics.drawString("Play the game with savepoints to start each level.", 110, 360+(65*cursor));
				}
				else if(this.cursor == 1)
				{
					graphics.drawString("Play the game with permadeath; you die, you start over!", 110, 360+(65*cursor));
				}
				else if(this.cursor == 2)
				{
					graphics.drawString("Exit the game. :(", 110, 365+(65*cursor));
				}
			}
		}
	}
	
	public void keyPressed(int keycode, char character)
	{
		if(keycode == computc.Utility.getKey("up"))
		{
			Game.assets.playSoundEffectWithoutRepeat("menuNavigate");
			this.cursor -= 1;
			if(this.cursor < 0)
				this.cursor = 0;
		}
		
		if(keycode == computc.Utility.getKey("down"))
		{
			Game.assets.playSoundEffectWithoutRepeat("menuNavigate");
			this.cursor += 1;
			if(this.cursor > 2)
				this.cursor = 2;
		}
		
		if(keycode == Input.KEY_ESCAPE)
		{
			System.exit(0);
		}
	}
	
	public int getLogoTimer()
	{
		return this.logoTimer;
	}
	
	public int getLogoStop()
	{
		return logoStop;
	}
	
	public int getID()
	{
		return TitleScreen.ID;
	}
	
	public static final int ID = 0;
}