package computc;

import org.newdawn.slick.Input;

public class InputPollingCamera extends Camera
{
	public InputPollingCamera()
	{
		this.setSpeed(0.5f);
	}
	
	public void update(Input input, int delta)
	{
		if(input.isKeyDown(Input.KEY_UP))
		{
			this.decreaseY(this.getSpeed() * delta);
		}
		else if(input.isKeyDown(Input.KEY_DOWN))
		{
			this.increaseY(this.getSpeed() * delta);
		}
		
		if(input.isKeyDown(Input.KEY_RIGHT))
		{
			this.increaseX(this.getSpeed() * delta);
		}
		else if(input.isKeyDown(Input.KEY_LEFT))
		{
			this.decreaseX(this.getSpeed() * delta);
		}
	}
}