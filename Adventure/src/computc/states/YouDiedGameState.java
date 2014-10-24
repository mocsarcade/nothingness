package computc.states;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import computc.GameData;

public class YouDiedGameState extends BasicGameState
{
	private GameData gamedata;

	public YouDiedGameState(GameData gamedata)
	{
		this.gamedata = gamedata;
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
			this.gamedata.instantiate();
			game.enterState(MainGameState.ID, new FadeOutTransition(Color.black, 100), new FadeInTransition(Color.black, 1000));
		}
	}
	
	public void render(GameContainer container, StateBasedGame game, Graphics graphics) throws SlickException
	{
		graphics.setColor(Color.white);
		graphics.drawString("You died! Hit space to try again.", 20, 20);
	}
	
	public int getID()
	{
		return YouDiedGameState.ID;
	}
	
	public static final int ID = 5;
}