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

public class YouWonGameState extends BasicGameState
{
	public Image[] menu = new Image[30];
	Animation screen;
	
	public YouWonGameState()
	{
		for(int i = 1; i <= 30; i++)
		{
			this.menu[i-1] = Game.assets.getImage("./res/textScreens/You-Win-" + i + ".png");
		}
		
		screen = new Animation(menu, 200);
		screen.setLooping(false);
	}
	
	public void init(GameContainer container, StateBasedGame game) throws SlickException
	{
		//code goes here
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException
	{
		Input input = container.getInput();
		
		if(input.isKeyDown(Input.KEY_ENTER) || input.isKeyDown(Input.KEY_SPACE))
		{
			game.enterState(TitleScreen.ID, new FadeOutTransition(Color.black, 100), new FadeInTransition(Color.black, 1000));
		}
		else if(input.isKeyDown(Input.KEY_ESCAPE))
		{
			game.enterState(TitleScreen.ID, new FadeOutTransition(Color.black, 100), new FadeInTransition(Color.black, 100));
		}
		
		this.screen.update(delta);
	}
	
	public void render(GameContainer container, StateBasedGame game, Graphics graphics) throws SlickException
	{
		this.screen.draw(0, 0);
	}
	
	public int getID()
	{
		return YouWonGameState.ID;
	}
	
	public final static int ID = 5;
}
