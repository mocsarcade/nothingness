
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

import computc.Game;
import computc.GameData;
import computc.Menu;
import computc.cameras.Camera;
import computc.cameras.RoomFollowingCamera;
import computc.entities.Arrow;
import computc.entities.Hero;
import computc.entities.OldMan;
import computc.worlds.Dungeon;
import computc.worlds.PredesignedDungeon;
import computc.worlds.RandomRoguelikeDungeon;
import computc.worlds.Tile;

public class MainGameState extends BasicGameState
{
	public GameData gamedata;
	public Camera camera;
	public Menu menu;
	
	public MainGameState(GameData gamedata)
	{
		this.gamedata = gamedata;
	}
	
	public void init(GameContainer container, StateBasedGame game) throws SlickException
	{
		Tile.images.put("wall", new Image("./res/wall.png"));
		Tile.images.put("floor", new Image("./res/floor.png"));
		Tile.images.put("northern arrow", new Image("./res/north.png"));
		Tile.images.put("southern arrow", new Image("./res/south.png"));
		Tile.images.put("eastern arrow", new Image("./res/east.png"));
		Tile.images.put("western arrow", new Image("./res/west.png"));
		
		this.gamedata.instantiate();
		
		this.menu = new Menu(this.gamedata.dungeon, this.gamedata.hero);
		this.camera = new RoomFollowingCamera(this.gamedata);
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException
	{
		Input input = container.getInput();
		
		this.gamedata.hero.update(input, delta);
		this.menu.update(input, game);
		this.camera.update(input, delta);
		
		this.gamedata.hero.checkAttack(this.gamedata.dungeon.getAllEnemies());
		this.gamedata.dungeon.update(delta);
	}
	
	public void render(GameContainer container, StateBasedGame game, Graphics graphics) throws SlickException
	{
		this.gamedata.dungeon.render(graphics, this.camera);
		this.gamedata.hero.render(graphics, this.camera);
		this.menu.render(graphics, this.camera);
	}
	
	@Override
	public void keyPressed(int k, char c)
	{
		if(k == Input.KEY_B)
		{
			this.gamedata.hero.setSwinging();
		}
	}
	
	@Override
	public void keyReleased(int k, char c)
	{
		
		if(k == Input.KEY_UP)
		{
		}
		if(k == Input.KEY_DOWN)
		{
		}
		if(k == Input.KEY_LEFT)
		{
		}
		if(k == Input.KEY_RIGHT)
		{
		}
		
		if(k == Input.KEY_SPACE)
		{
			if(this.gamedata.hero.arrowCount != 0)
			{
				this.gamedata.hero.arrowCount -= 1;
				Arrow arrow;
				try 
				{
					arrow = new Arrow(this.gamedata.dungeon, this.gamedata.hero.getRoom(), this.gamedata.hero.getTileyX(), this.gamedata.hero.getTileyY(), this.gamedata.hero.getDirection());
					arrow.setPosition(this.gamedata.hero.getX(), this.gamedata.hero.getY());
					this.gamedata.hero.arrows.add(arrow);
				} catch (SlickException e) 
				{
					e.printStackTrace();
				}
				
			}
		}
	}
	
	public int getID()
	{
		return MainGameState.ID;
	}
	
	public static final int ID = 0;

}