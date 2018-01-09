package org.minhvu.operationrebound;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Player
{
	private static BufferedImage image = Game.getInstance().getChararcters().getSprite(164,	88,	49,	43);

	private Point location;
	private int speed;

	private boolean uppressed;
	private boolean downpressed;
	private boolean leftpressed;
	private boolean rightpressed;

	private int ammo;
	private int maxAmmo;
	private int reloadTime;
	private long reloadStart;

	public Player()
	{
		location = new Point((Game.getInstance().getWidth() - image.getWidth(Game.getInstance())) / 2, Game.getInstance().getHeight() - 200);
		speed = 4;

		uppressed = false;
		downpressed = false;
		leftpressed = false;
		rightpressed = false;

		maxAmmo = 15;
		ammo = maxAmmo;
		reloadTime = 1336;
	}

	public boolean hasCollision()
	{
		for (int i = 0; i < Game.getInstance().getMaps().getCurrentMap().getCollisionMap().length; ++i)
		{
			for (int j = 0; j < Game.getInstance().getMaps().getCurrentMap().getCollisionMap()[i].length; ++j)
			{
				if (Game.getInstance().getMaps().getCurrentMap().getCollisionMap()[i][j] != 0 && getBounds().intersects(new Rectangle2D.Float(j * 64, i * 64, 64, 64)))
				{
					return true;
				}
			}
		}

		return false;
	}
	public void paint(Graphics2D g2d)
	{
		AffineTransform transform = g2d.getTransform();

		Point mousePostion = MouseInfo.getPointerInfo().getLocation();
		double angle = Math.atan2(mousePostion.y - getCenter().y , mousePostion.x - getCenter().x);

		g2d.rotate(angle, getCenter().x, getCenter().y);
		g2d.drawImage(image, location.x, location.y, Game.getInstance());
		g2d.setTransform(transform);
	}

	public void move()
	{
		if (uppressed)
		{
			if (location.y - speed > 0)
			{
				location.y -= speed;
			}

			if (hasCollision())
			{
				location.y += speed;
			}
		}

		if (downpressed)
		{
			if (location.y + speed < Game.getInstance().getHeight() - image.getHeight(Game.getInstance()))
			{
				location.y += speed;
			}

			if (hasCollision())
			{
				location.y -= speed;
			}
		}

		if (leftpressed)
		{
			if (location.x - speed > 0)
			{
				location.x -= speed;
			}

			if (hasCollision())
			{
				location.x += speed;
			}
		}

		if (rightpressed)
		{
			if (location.x + speed < Game.getInstance().getWidth() - image.getWidth(Game.getInstance()))
			{
				location.x += speed;
			}

			if (hasCollision())
			{
				location.x -= speed;
			}
		}
	}

	public void mouseReleased(MouseEvent e)
	{
		if (e.getButton() == MouseEvent.BUTTON1)
		{
			if (ammo > 0)
			{
				Point mousePostion = MouseInfo.getPointerInfo().getLocation();
				double angle = Math.atan2(mousePostion.y - getCenter().y, mousePostion.x - getCenter().x);
				Game.getInstance().getBullets().add(new Bullet(getCenter(), 10, 500, angle));

				if (--ammo == 0)
				{
					reloadStart = System.currentTimeMillis();
				}
			}

			else if (System.currentTimeMillis() - reloadStart > reloadTime)
			{
				ammo = maxAmmo;
				reloadStart = 0;
			}
		}
	}

	public void keyReleased(KeyEvent e)
	{
		if (e.getKeyCode() == KeyEvent.VK_W)
		{
			uppressed = false;
		}

		if (e.getKeyCode() == KeyEvent.VK_S)
		{
			downpressed = false;
		}

		if (e.getKeyCode() == KeyEvent.VK_A)
		{
			leftpressed = false;
		}

		if (e.getKeyCode() == KeyEvent.VK_D)
		{
			rightpressed = false;
		}
	}

	public void keyPressed(KeyEvent e)
	{
		if (e.getKeyCode() == KeyEvent.VK_W)
		{
			uppressed = true;
		}

		if (e.getKeyCode() == KeyEvent.VK_S)
		{
			downpressed = true;
		}

		if (e.getKeyCode() == KeyEvent.VK_A)
		{
			leftpressed = true;
		}

		if (e.getKeyCode() == KeyEvent.VK_D)
		{
			rightpressed = true;
		}
	}

	public Point getLocation()
	{
		return location;
	}

	public Dimension getDimensions()
	{
		return new Dimension(image.getWidth(Game.getInstance()), image.getHeight(Game.getInstance()));
	}

	public Rectangle getBounds()
	{
		return new Rectangle(location.x, location.y, getDimensions().width, getDimensions().height);
	}

	public Point getCenter()
	{
		return new Point(location.x + getDimensions().width / 2, location.y + getDimensions().height / 2);
	}
}