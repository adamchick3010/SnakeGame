import java.awt.*;


public class Food {
	public Rect background;
	public Snake snake;
	public int width, height;
	public Color color;
	public Rect rect;
	public Obstacle obstacle;
	
	public int xPadding;
	
	public boolean isSpawned = false;

	public Food(Rect background, Snake snake,Obstacle obstacle ,int width, int height, Color color) {
		this.background = background;
		this.snake = snake;
		this.obstacle = obstacle;
		this.width = width;
		this.height = height;
		this.color = color;
		this.rect = new Rect(0, 0, width, height);
		
		xPadding = (int)((Constants.TILE_WIDTH - this.width) / 2.0); //setting padding on our food
	}
	
	public void spawn() {
		do {
			double randX = (int)(Math.random() * (int)(background.width / Constants.TILE_WIDTH)) * Constants.TILE_WIDTH + background.x; //generating random number which exists in the x scale background
			double randY = (int)(Math.random() * (int)(background.height / Constants.TILE_WIDTH)) * Constants.TILE_WIDTH + background.y; //generating random number which wxists in the y scale background
			this.rect.x = randX;
			this.rect.y = randY;
		} while(snake.intersectingWithRect(this.rect) || obstacle.intersectingWithRect(new Rect(rect.x+width, rect.y, width,height))); //trying to avoid spawning on the place where our snake is
		this.isSpawned = true;
	}

	public void update(double dt) {
		if (snake.intersectingWithRect(this.rect)) {
            snake.grow();
            Window.getWindow().score += 1;
            this.rect.x = -100;
            this.rect.y = -100;
            isSpawned = false;
        }
	}
	
    public void draw(Graphics2D g2) {
        g2.setColor(color);
//        g2.fillRect(x + xPadding,  y + xPadding,  width,  height);
        g2.fillRect((int)this.rect.x + xPadding, (int)this.rect.y + xPadding, width, height);
    }
}
