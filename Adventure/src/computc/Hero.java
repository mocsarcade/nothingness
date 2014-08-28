package computc;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Hero extends Entity
{
	public static boolean nextArea;
	private boolean dead = false;
	private int health;
	private int maxHealth;
	
	public Hero(World world, int tx, int ty) throws SlickException
	{
		super(world, tx, ty);
		
		this.image = new Image("res/hero.png");
		
		moveSpeed = .15f;
		health = 5;
	}
	
	public void update(Input input, int delta)
	{
		float step = this.moveSpeed * delta;
		
		if(input.isKeyDown(Input.KEY_UP))
		{
			this.direction = Direction.NORTH;
			this.y -= step;
		}
		else if(input.isKeyDown(Input.KEY_DOWN))
		{
			this.direction = Direction.SOUTH;
			this.y += step;
		}
		
		if(input.isKeyDown(Input.KEY_LEFT))
		{
			this.direction = Direction.WEST;
			this.x -= step;
		}
		else if(input.isKeyDown(Input.KEY_RIGHT))
		{
			this.direction = Direction.EAST;
			this.x += step;
		}
	}
}