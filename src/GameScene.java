import java.awt.*;
import java.awt.event.KeyEvent;

import java.awt.geom.Rectangle2D;

public class GameScene extends Scene{
	
	Rect background, foreground;
	Snake snake;
	KeyL keyListener;
	SnakeAI snakeAI;
	Obstacle obstacle;
	public Food food;
	public Frog frog;
	
	public GameScene(KeyL keyListener) {
		background = new Rect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT); //setting size of game background
		foreground = new Rect(24, 48, Constants.TILE_WIDTH * 31, Constants.TILE_WIDTH * 22);// creating 31 columns and 22 rows with the height of 24
		obstacle = new Obstacle(foreground,24,24,10,1);
		obstacle.spawn();
		snake = new Snake(obstacle,8, 48, 192, 24, 24, foreground); //size - 5, startX - 48, startY - 48 + 24, bodyW - 24, bodyH - 24
		this.keyListener = keyListener;
		food = new Food(foreground, snake,obstacle, 24, 24, Color.GREEN); //creating a bonus for snake
		frog = new Frog(foreground,snake,food,obstacle,24,24,Color.red);

		food.spawn();
		frog.spawn();
		snakeAI = new SnakeAI(obstacle,snake,food,frog,8,48,408,24,24,foreground);
	}
	
	@Override
	public void update(double dt) {
		
		if(keyListener.isKeyPressed(KeyEvent.VK_UP)) {
			snake.changeDirection(Direction.UP);
		} else if (keyListener.isKeyPressed(KeyEvent.VK_DOWN)) {
			snake.changeDirection(Direction.DOWN);
		} else if (keyListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
			snake.changeDirection(Direction.RIGHT);
		} else if (keyListener.isKeyPressed(KeyEvent.VK_LEFT)) {
			snake.changeDirection(Direction.LEFT);
		} //checking user is pressing any key to move the snake in another way

		if (!food.isSpawned) food.spawn(); //calling spawning food everytime we eat that
		if (!frog.isSpawned) frog.spawn();

		food.update(dt); //food updates after every delta time
		frog.update1(dt);
		snakeAI.update(dt);
		snake.update(dt); //snakes update after every delta time



	}
	
	@Override
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.BLACK); //color of our background
		g2.fill(new Rectangle2D.Double(background.x, background.y, background.width, background.height)); //size of out game background
		g2.setColor(Color.GRAY); //foreground color
		g2.fill(new Rectangle2D.Double(foreground.x, foreground.y, foreground.width, foreground.height)); //foreground size
		obstacle.draw(g2);
		snake.draw(g2);
		food.draw(g2);
		snakeAI.draw(g2);
		frog.draw(g2);
	}

}
