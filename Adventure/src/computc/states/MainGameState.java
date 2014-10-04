
package computc.states;

import java.util.HashSet;
import java.util.Set;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.lwjgl.input.Mouse;
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
import computc.entities.Chain;
import computc.entities.Coin;
import computc.entities.Hero;
import computc.entities.Key;
import computc.entities.OldMan;
import computc.worlds.dungeons.Dungeon;
import computc.worlds.rooms.Room;
import computc.worlds.tiles.Tile;

public class MainGameState extends BasicGameState
{
	public GameData gamedata;
	
	public Menu menu;
	public RoomFollowingCamera camera;
	
	private int gravityCoolDown; //chain
	
	public MainGameState(GameData gamedata)
	{
		this.gamedata = gamedata;
	}
	
	private Animation textBox;
	
	public void init(GameContainer container, StateBasedGame game) throws SlickException
	{
		Coin.IMAGE = new Image("./res/coin.png");
		
		this.textBox = new Animation(new SpriteSheet(new Image("res/largeTextBox.png"), 585, 100), 100);
		
		this.gamedata.instantiate();
		
		this.camera = new RoomFollowingCamera(this.gamedata);
		this.menu = new Menu(gamedata);
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException
	{
		Input input = container.getInput();
		
		this.gamedata.hero.update(input, delta);
		this.camera.update(input, delta);
		
		this.gamedata.dungeon.update(delta);
		
		this.gamedata.hero.checkAttack(this.gamedata.dungeon.getAllEnemies());
		//this.gamedata.hero.checkPickup(this.gamedata.dungeon.keys);
		
		if(input.isKeyDown(Input.KEY_M))
		{
			game.enterState(1);
		}
		
		if(this.gamedata.hero.isDead())
		{
			this.gamedata.instantiate();
		}
		
		if(this.gamedata.hero.getRoomyX() == this.gamedata.dungeon.lastRoom.getRoomyX()
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
		}
		
		//  makes the chain movement less floaty
		if(input.isKeyDown(Input.KEY_UP))
		{
			this.gamedata.hero.getWorld().setGravity(new Vec2(0, 1f));
			
		}
		else if(input.isKeyDown(Input.KEY_DOWN))
		{
			this.gamedata.hero.getWorld().setGravity(new Vec2(0, -1f));
		}

		if(input.isKeyDown(Input.KEY_LEFT))
		{
			this.gamedata.hero.getWorld().setGravity(new Vec2(1f, 0));
		}
		else if(input.isKeyDown(Input.KEY_RIGHT))
		{
			this.gamedata.hero.getWorld().setGravity(new Vec2(-1f, 0));
		}
		
		for(Body body: this.gamedata.hero.chain.bodies)
		{
			body.setLinearDamping(10);
		}
		
		if(gravityCoolDown != 0)
		{
			gravityCoolDown--;
		}
		
		// sets the camera to peek into adjacent rooms
		if(this.gamedata.hero.getPeekTimer() > 1000)
		{
			this.camera.setPeeking(this.gamedata.hero.getDirection());
		}
	}

	private String greeting = "You made it to the end! I hope you enjoyed the game; we've";
	private String greeting2 = "worked very hard on it. Follow us at mocsarcade.tumblr.com!";
	private float counter, counter2;
	
	public void render(GameContainer container, StateBasedGame game, Graphics graphics) throws SlickException
	{
		this.gamedata.dungeon.render(graphics, this.camera);
		this.gamedata.hero.render(graphics, this.camera);
		this.menu.render(graphics, camera);
		
		if(this.gamedata.hero.getRoomyX() == this.gamedata.dungeon.lastRoom.getRoomyX()
		&& this.gamedata.hero.getRoomyY() == this.gamedata.dungeon.lastRoom.getRoomyY())
		{
			textBox.draw(Room.WIDTH/11, Room.HEIGHT/11);
			textBox.setLooping(false);
			
			int xCoord = (int) (Room.WIDTH/11 + 12);
			int yCoord = (int) (Room.HEIGHT/11 + 12);
			int xCoord2 = (int) (Room.WIDTH/11 + 12);
			int yCoord2 = (int) (Room.HEIGHT/11 + 32);
			
			String greeting2temp = greeting2;
			graphics.setColor(Color.white);
			graphics.drawString(greeting.substring(0, (int)(Math.min(counter, greeting.length()))), xCoord, yCoord);
			graphics.drawString(greeting2temp.substring(0, (int)(Math.min(counter2, greeting2temp.length()))), xCoord2, yCoord2);
		}
	}
	
	@Override
	public void keyPressed(int k, char c)
	{
		if(k == Input.KEY_B)
		{
			this.gamedata.hero.setSwinging();
		}
		
		// prepare swinging chain attack
		if(k == Input.KEY_W)
		{
			if(Mouse.getX() > this.gamedata.hero.getRoomPositionX())
			{
			  Vec2 mousePosition = new Vec2(Mouse.getX() + 10000, Mouse.getY()).mul(0.5f).mul(1/30f);
			  Vec2 playerPosition = new Vec2(this.gamedata.hero.chain.playerBody.getPosition());
			  Vec2 force = mousePosition.sub(playerPosition);
			  this.gamedata.hero.chain.lastLinkBody.applyForce(force,  this.gamedata.hero.chain.lastLinkBody.getPosition());
			}
			else
			{
				Vec2 mousePosition = new Vec2(Mouse.getX() - 10000, Mouse.getY()).mul(0.5f).mul(1/30f);
				Vec2 playerPosition = new Vec2(this.gamedata.hero.chain.playerBody.getPosition());
				Vec2 force = mousePosition.sub(playerPosition);
				this.gamedata.hero.chain.lastLinkBody.applyForce(force,  this.gamedata.hero.chain.lastLinkBody.getPosition());
			}
		}
	}
	
	
	@Override
	public void keyReleased(int k, char c)
	{
		
		if(k == Input.KEY_UP)
		{
			this.camera.turnOffPeeking();
			this.gamedata.hero.resetPeekTimer();
		}
		if(k == Input.KEY_DOWN)
		{
			this.camera.turnOffPeeking();
			this.gamedata.hero.resetPeekTimer();
		}
		if(k == Input.KEY_LEFT)
		{
			this.camera.turnOffPeeking();
			this.gamedata.hero.resetPeekTimer();
		}
		if(k == Input.KEY_RIGHT)
		{
			this.camera.turnOffPeeking();
			this.gamedata.hero.resetPeekTimer();
		}
		
		if(k == Input.KEY_E)
		{
			this.camera.setEarthQuake(this.gamedata.hero.getDirection());
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
		
		// swinging chain attack
		if(k == Input.KEY_W)
		{
			this.gamedata.hero.setChainAttack();
			
			if(Mouse.getX() > this.gamedata.hero.getRoomPositionX())
			{
			  Vec2 mousePosition = new Vec2(Mouse.getX() - 1000000, Mouse.getY()).mul(0.5f).mul(1/30f);
			  Vec2 playerPosition = new Vec2(this.gamedata.hero.chain.playerBody.getPosition());
			  Vec2 force = mousePosition.sub(playerPosition);
			  this.gamedata.hero.chain.lastLinkBody.applyForce(force,  this.gamedata.hero.chain.lastLinkBody.getPosition());
			}
			else
			{
				Vec2 mousePosition = new Vec2(Mouse.getX() + 1000000, Mouse.getY()).mul(0.5f).mul(1/30f);
				Vec2 playerPosition = new Vec2(this.gamedata.hero.chain.playerBody.getPosition());
				Vec2 force = mousePosition.sub(playerPosition);
				this.gamedata.hero.chain.lastLinkBody.applyForce(force,  this.gamedata.hero.chain.lastLinkBody.getPosition());
			}
		}
		
	}
	
	public int getID()
	{
		return MainGameState.ID;
	}
	
	public static final int ID = 0;

}