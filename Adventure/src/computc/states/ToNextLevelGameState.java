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

public class ToNextLevelGameState extends BasicGameState
{
	private GameData gamedata;

	public ToNextLevelGameState(GameData gamedata)
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
		this.gamedata.instantiate();
		
		if(input.isKeyDown(Input.KEY_SPACE))
		{
			MainGameState maingame = (MainGameState) game.getState(MainGameState.ID);
			maingame.camera.setToTargetX();
			maingame.camera.setToTargetY();
			
			game.enterState(2, new FadeOutTransition(Color.black, 100), new FadeInTransition(Color.black, 1000));
		}
	}
	
	public void render(GameContainer container, StateBasedGame game, Graphics graphics) throws SlickException
	{
		graphics.setColor(Color.white);
		graphics.drawString("Hit spacebar to go to next level!", 20, 20);
	}
	
	public int getID()
	{
		return ToNextLevelGameState.ID;
	}
	
	public static final int ID = 4;
}