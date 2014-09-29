package computc.entities;

import java.util.HashSet;
import java.util.Set;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.RevoluteJointDef;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import computc.cameras.Camera;
import computc.worlds.Room;

public class Chain 
{
	protected float x;
	protected float y;
	
	private int meterToPixel;
	private int roomLoadingCooldown;
	
	public  Set<Body> bodies = new HashSet<Body>();
	public  Set<Body> staticBodies = new HashSet<Body>();
	 
	// box2d BodyDefinitions
	BodyDef playerBodyDef, linkBodyDef, lastLinkBodyDef, wallBodyDef;
	public Body playerBody, linkBody, lastLinkBody, wallBody;
	FixtureDef chainProperties, wallProperties;
	RevoluteJointDef joint;
	 
	 // box2d shapes
	PolygonShape playerBoxShape, chainLinkShape;
	CircleShape wallCollisionShape;
	 
	private float anchorY;
	private Vec2 anchor;
	
	private Vec2 box2dPlayerPosition;
	 
	public Entity entity;
	
	Image chainLink = new Image("res/links2.png");
	
	public Chain(World world, Entity entity) throws SlickException
	{
		this.entity = entity;
		
		this.meterToPixel = 30;
		
		this.x = (entity.getX() + entity.getHalfWidth())/30;
		this.y = (entity.getY() + entity.getHalfWidth())/30;
		
		setupChain(world);
		
		// converts box2d position to hero's position on screen
		box2dPlayerPosition = new Vec2(this.entity.getRoomPositionX()/30, this.entity.getRoomPositionY()/30);	

	}
	public void render(Graphics graphics, Camera camera)
	{
		if(!this.entity.dungeon.getDebugDraw())
		{
			// draw chain - chain link rotation needs work
			for(Body body: bodies)
			{
				if(body.getType() == BodyType.DYNAMIC || body.getType() == BodyType.STATIC) 
				{
					Vec2 bodyPosition = body.getPosition().mul(30);
					if(!entity.roomTransition)
					{
					chainLink.draw(bodyPosition.x, bodyPosition.y);
					}
					chainLink.setRotation((float) Math.toDegrees(body.getAngle()));
				}
			}
		}
		
		// draw debug mode
		else this.entity.dungeon.rigidBodyDebugDraw(bodies, staticBodies);
		
		if(camera.getX() != this.entity.getRoom().getX() || camera.getY() != this.entity.getRoom().getY())
		{
			if(roomLoadingCooldown <= 0)
			{
				entity.newRoom = true;
				roomLoadingCooldown = 200;
				
			}
			roomLoadingCooldown--;
			
			entity.roomTransition = true;
			lastLinkBody.setType(BodyType.STATIC);
		}
		else 
		{
			roomLoadingCooldown = 0;	
			entity.roomTransition = false;
			lastLinkBody.setType(BodyType.DYNAMIC);
			
		}
		// converts box2d position to hero's position on screen
		box2dPlayerPosition = new Vec2(this.entity.getLocalX(camera)/30, this.entity.getLocalY(camera)/30);	
	}
	
	public void update(Input input, int delta)
	{							
		if(entity.roomTransition)
		{
			for(Body body: bodies)
			{
				body.setTransform(box2dPlayerPosition, 0);
			}
		}
		
		// making sure the chain behaves properly
		for(Body body: bodies)
			{
			for(int i = 0; i < bodies.size(); i++)
				{
				
				if(body.getPosition().x > playerBody.getPosition().x + (bodies.size() - 1))
					{
						body.setType(BodyType.KINEMATIC);
						Vec2 fixedPosition = new Vec2(playerBody.getPosition().x + i, playerBody.getPosition().y);
						body.setTransform(fixedPosition, 0);
						body.setType(BodyType.DYNAMIC);
					}
				
				if(body.getPosition().x < playerBody.getPosition().x - (bodies.size() - 1))
					{
						body.setType(BodyType.KINEMATIC);
						Vec2 fixedPosition = new Vec2(playerBody.getPosition().x - i, playerBody.getPosition().y);
						body.setTransform(fixedPosition, 0);
						body.setType(BodyType.DYNAMIC);
					}
				
				if(body.getPosition().y > playerBody.getPosition().y + (bodies.size() - 1))
					{
						body.setType(BodyType.KINEMATIC);
						Vec2 fixedPosition = new Vec2(playerBody.getPosition().x, playerBody.getPosition().y + i);
						body.setTransform(fixedPosition, 0);
						body.setType(BodyType.DYNAMIC);
					}
				
				if(body.getPosition().y < playerBody.getPosition().y - (bodies.size() - 1))
					{
						body.setType(BodyType.KINEMATIC);
						Vec2 fixedPosition = new Vec2(playerBody.getPosition().x, playerBody.getPosition().y - i);
						body.setTransform(fixedPosition, 0);
						body.setType(BodyType.DYNAMIC);
					}
				}
			}
		
		if(entity.dx > 0 || entity.dx < 0 || entity.dy > 0 || entity.dy < 0)
		{
			
			if(entity.newRoom)
			{
			loadRoomRigidBodies(entity.getWorld());
			entity.newRoom = false;
			}
			
		}
	}
	
	public void setupChain(World world)
	{
		// setup Player's box2d body 
			playerBodyDef = new BodyDef();
			playerBodyDef.type = BodyType.STATIC;
			playerBody = world.createBody(playerBodyDef);
			playerBoxShape = new PolygonShape();
			playerBoxShape.setAsBox(0.8f, 0.8f);
			playerBody.createFixture(playerBoxShape, 0.0f);
			
			chainLinkShape = new PolygonShape();
			chainLinkShape.setAsBox(0.5f, 0.060f);
				
		// setup chain Properties (FixtureDef)
			chainProperties = new FixtureDef();
			chainProperties.shape = chainLinkShape;
			chainProperties.density = 1.0f;
			chainProperties.friction = 0.002f;
							
		// joint setup
			joint = new RevoluteJointDef();
			joint.collideConnected = false;
			anchorY = entity.getRoomPositionY()/30 - 10f;
			Body prevBody = playerBody;
			bodies.add(playerBody);
			
			// make chain links
			for (float i = this.entity.getRoomPositionX()/30 - 12; i < this.entity.getRoomPositionY()/30 - 6; i++)
				{
					if(i >= this.entity.getRoomPositionY()/30 - 8)
					{
						lastLinkBodyDef = new BodyDef();
						lastLinkBodyDef.type = BodyType.DYNAMIC;
						lastLinkBodyDef.position.set(0.2f + i, anchorY);
						lastLinkBody = world.createBody(lastLinkBodyDef);
						lastLinkBody.createFixture(chainProperties);
						anchor = new Vec2(i, anchorY);
									
						joint.initialize(prevBody, lastLinkBody, anchor);
						world.createJoint(joint);
						prevBody = linkBody;
						bodies.add(lastLinkBody);
					}
					else
					{
					linkBodyDef = new BodyDef();
					linkBodyDef.type = BodyType.DYNAMIC;
					linkBodyDef.position.set(1f + i, anchorY);
					linkBody = world.createBody(linkBodyDef);	
					linkBody.createFixture(chainProperties);
					anchor = new Vec2(i, anchorY);
								
					// initialize joint
					joint.initialize(prevBody, linkBody, anchor);
					world.createJoint(joint);
					prevBody = linkBody;
					bodies.add(linkBody);
					}
				}
			System.out.println("the chain size is: " + bodies.size());
	}
	
	
	// give walls rigidbodies for the chain to collide with
	public void loadRoomRigidBodies(World world)
	{
		destroyRoomRigidBodies(world);
		
		for (int i = 0; i < Room.TILEY_WIDTH; i++)
		{
			for(int j = 0; j < Room.TILEY_HEIGHT; j++) 
			{
				if(entity.getRoom().getTile((float)64*i, (float)64*j).isBlocked)
				{
				wallBodyDef = new BodyDef();
				wallBodyDef.type = BodyType.STATIC;
				wallCollisionShape = new CircleShape();
				wallCollisionShape.setRadius(1.1f);
				wallBody = world.createBody(wallBodyDef);
				wallProperties = new FixtureDef();
				wallProperties.density = 1;
				wallProperties.restitution = .3f;
				wallProperties.friction = 0f;
				wallProperties.shape = wallCollisionShape;
				wallBody.createFixture(wallProperties);
				Vec2 roomPosition = new Vec2((2.15f * i) + 1,(2.15f * j) + 1);
				wallBody.setTransform(roomPosition, 0);
				staticBodies.add(wallBody);
				}
			}
		}
	}
	
	// destroy all static rigidBodies
	public void destroyRoomRigidBodies(World world)
	{	
		for(Body body: staticBodies)
		{
			world.destroyBody(body);
		}
		staticBodies.clear();
	}

}