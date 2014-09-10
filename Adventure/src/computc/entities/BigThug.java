package computc.entities;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import computc.worlds.Dungeon;

public class BigThug extends Thug
{
	public BigThug(Dungeon dungeon, int tx, int ty) throws SlickException
	{
		super(dungeon, tx, ty);
		
		this.maximumVelocity = 0.02f;
		this.image = new Image("res/bigthug.png");
		this.damage = 2;
	}
}