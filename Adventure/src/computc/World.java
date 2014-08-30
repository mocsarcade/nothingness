package computc;

import java.awt.Point;
import java.util.LinkedList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class World
{
	public Hero hero;
	public Dungeon dungeon;
	public Camera camera;
	
	public World() throws SlickException
	{
		this.dungeon = new Dungeon();
		this.hero = new Hero(this, 5, 0);
		this.camera = new Camera(hero);
	}
	
	public void update(Input input, int delta)
	{
		this.camera.update(delta);
		this.hero.update(input, delta);
	}
	
	public void render(Graphics graphics)
	{
		this.dungeon.render(graphics, camera);
		this.hero.render(graphics, camera);
	}
}