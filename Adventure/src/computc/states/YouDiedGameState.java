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

public class YouDiedGameState extends BasicGameState
{
	private GameData gamedata;

	public YouDiedGameState(GameData gamedata)
	{
		this.gamedata = gamedata;
	}
	
	public Image screen = Game.assets.getImage("./res/textScreens/You-Lose.png");
	private int cursor = 0;
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
			if(this.cursor == 0)
			{
				this.gamedata.instantiate();
				game.enterState(MainGameState.ID, new FadeOutTransition(Color.black, 100), new FadeInTransition(Color.black, 1000));
			}
			else if(this.cursor == 1)
			{
				game.enterState(TitleScreen.ID, new FadeOutTransition(Color.black, 100), new FadeInTransition(Color.black, 100));
			}
		}
		else if(input.isKeyDown(Input.KEY_ESCAPE))
		{
			game.enterState(TitleScreen.ID, new FadeOutTransition(Color.black, 100), new FadeInTransition(Color.black, 100));
		}
		
		cursor_time += delta;
		if(Game.assets.lowHealth.isPlaying())
		{
			Game.assets.lowHealth.stop();
		}
	}
	
	public void keyPressed(int keycode, char character)
	{
		if(keycode == Input.KEY_W || keycode == Input.KEY_UP)
		{
			this.cursor  -= 1;
			if(this.cursor < 0)
				this.cursor = 0;
		}
		
		if(keycode == Input.KEY_S || keycode == Input.KEY_DOWN)
		{
			this.cursor += 1;
			if(this.cursor > 1)
				this.cursor = 1;
		}
	}
	
	public void render(GameContainer container, StateBasedGame game, Graphics graphics) throws SlickException
	{
		this.screen.draw(0, 0);

		graphics.setColor(Color.white);
		graphics.drawString(this.gamedata.level + "", 280, 200);
		graphics.drawString(this.gamedata.hero.monsters_killed + "", 280, 260);
		graphics.drawString(this.gamedata.hero.coinage + "", 280, 310);
		
		if(cursor_time % 1000 < 750)
		{
			graphics.fillOval(215, 420+(75*cursor), 10, 10);
		}
		else
		{
			graphics.drawOval(215, 420+(75*cursor), 10, 10);
		}
	}
	
	public int getID()
	{
		return YouDiedGameState.ID;
	}
	
	public static final int ID = 55;
}