package computc.cameras;

import java.io.FileInputStream;
import java.util.Properties;

import org.newdawn.slick.Input;

import computc.Utility;
import computc.worlds.rooms.Room;

public class InputPollingCamera extends Camera
{
	
	public InputPollingCamera()
	{
		this.setSpeed(0.5f);
	}
	
	public void update(Input input, int delta)
	{
		if(input.isKeyDown(computc.Utility.getKey("up")))
		{
			this.decreaseY(this.getSpeed() * delta);
		}
		else if(input.isKeyDown(computc.Utility.getKey("down")))
		{
			this.increaseY(this.getSpeed() * delta);
		}
		
		if(input.isKeyDown(computc.Utility.getKey("right")))
		{
			this.increaseX(this.getSpeed() * delta);
		}
		else if(input.isKeyDown(computc.Utility.getKey("left")))
		{
			this.decreaseX(this.getSpeed() * delta);
		}
	}
}