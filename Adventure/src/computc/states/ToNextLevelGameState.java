package computc.states;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import computc.Game;
import computc.GameData;

public class ToNextLevelGameState extends BasicGameState
{
	private GameData gamedata;

	public ToNextLevelGameState(GameData gamedata)
	{
		this.gamedata = gamedata;
	}
	public Image screen = Game.assets.getImage("./res/textScreens/Floor-Cleared.png");
	private int cursor_time = 0;
	
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
		else if(input.isKeyDown(Input.KEY_ESCAPE))
		{
			game.enterState(TitleScreen.ID, new FadeOutTransition(Color.black, 100), new FadeInTransition(Color.black, 100));
		}
		
		cursor_time += delta;
	}
	
	public void render(GameContainer container, StateBasedGame game, Graphics graphics) throws SlickException
	{
		this.screen.draw(0, 0);

		graphics.setColor(Color.white);
		graphics.drawString(this.gamedata.hero.monsters_killed + "", 280, 200);
		graphics.drawString(this.gamedata.hero.coinage + "", 280, 250);
		
		if(cursor_time % 1000 < 750)
		{
			graphics.fillOval(175, 500, 10, 10);
		}
		else
		{
			graphics.drawOval(175, 500, 10, 10);
		}
	}
	
	public int getID()
	{
		return ToNextLevelGameState.ID;
	}
	
	public static final int ID = 4;
}