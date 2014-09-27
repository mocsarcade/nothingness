
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
import computc.entities.Coin;
import computc.entities.Hero;
import computc.entities.Key;
import computc.entities.OldMan;
import computc.worlds.dungeons.Dungeon;
import computc.worlds.dungeons.PredesignedDungeon;
import computc.worlds.dungeons.RandomRoguelikeDungeon;
import computc.worlds.rooms.Room;
import computc.worlds.tiles.Tile;

public class MainGameState extends BasicGameState
{
	public GameData gamedata;
	public Camera camera;
	
	
	private Animation textBox;
	
	public MainGameState(GameData gamedata)
	{
		this.gamedata = gamedata;
	}
	
	public void init(GameContainer container, StateBasedGame game) throws SlickException
	{
		Key.IMAGE = new Image("./res/key.png");
		Coin.IMAGE = new Image("./res/coin.png");
		
		this.textBox = new Animation(new SpriteSheet(new Image("res/largeTextBox.png"), 585, 100), 100);
		
		this.gamedata.instantiate();
		
		this.camera = new RoomFollowingCamera(this.gamedata);
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException
	{
		Input input = container.getInput();
		
		this.gamedata.hero.update(input, delta);
		this.gamedata.menu.update(input, game);
		this.camera.update(input, delta);
		
		this.gamedata.dungeon.update(delta);
		
		this.gamedata.hero.checkAttack(this.gamedata.dungeon.getAllEnemies());
		this.gamedata.hero.checkPickup(this.gamedata.dungeon.keys);
		this.gamedata.hero.checkGetCoin();
		
		if(this.gamedata.hero.isDead())
		{
			this.gamedata.instantiate();
		}
		
		/*if(this.gamedata.hero.getRoomyX() == this.gamedata.dungeon.lastRoom.getRoomyX()
		&& this.gamedata.hero.getRoomyY() == this.gamedata.dungeon.lastRoom.getRoomyY())
		{
			if((int)(counter) < greeting.length())
			{
				counter += delta * 0.025;
			}
			else
			{
				counter2 += delta * 0.025;
			}
		}*/
	}

	private String greeting = "You've won! Congratulations! Thanks for playing! Enjoy the";
	private String greeting2 = "donuts, and join us at our next party! Your score was ";
	private float counter, counter2;
	
	public void render(GameContainer container, StateBasedGame game, Graphics graphics) throws SlickException
	{
		this.gamedata.dungeon.render(graphics, this.camera);
		this.gamedata.hero.render(graphics, this.camera);
		this.gamedata.dungeon.renderKeys(graphics, camera);
		//this.gamedata.menu.render(graphics, this.camera);
		
		/*if(this.gamedata.hero.getRoomyX() == this.gamedata.dungeon.lastRoom.getRoomyX()
		&& this.gamedata.hero.getRoomyY() == this.gamedata.dungeon.lastRoom.getRoomyY())
		{
			textBox.draw(Room.WIDTH/11, Room.HEIGHT/11);
			textBox.setLooping(false);
			
			int xCoord = (int) (Room.WIDTH/11 + 12);
			int yCoord = (int) (Room.HEIGHT/11 + 12);
			int xCoord2 = (int) (Room.WIDTH/11 + 12);
			int yCoord2 = (int) (Room.HEIGHT/11 + 32);
			
			String greeting2temp = greeting2 + this.gamedata.hero.coinage + ".";
			graphics.setColor(Color.white);
			graphics.drawString(greeting.substring(0, (int)(Math.min(counter, greeting.length()))), xCoord, yCoord);
			graphics.drawString(greeting2temp.substring(0, (int)(Math.min(counter2, greeting2temp.length()))), xCoord2, yCoord2);
		}*/
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