package computc.worlds;

import computc.Direction;

public class Door
{
	private Integer position;
	private Direction direction;
	
	public Door(Direction direction, Integer position)
	{
		this.direction = direction;
		this.position = position;
	}
	
	public Direction getDirection()
	{
		return this.direction;
	}
	
	public Integer getPosition()
	{
		return this.position;
	}
}