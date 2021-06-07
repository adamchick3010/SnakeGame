import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Frog {
    public Rect background;
    public Snake snake;
    public SnakeAI snakeAI;
    public int width, height;
    public Color color;
    public Rect rect;

    public int xPadding;

    public boolean isSpawned = false;
    public double ogWaitBetweenUpdates = 0.2f;
    public double waitTimeLeft = ogWaitBetweenUpdates;

    public Frog(Rect background, Snake snake, int width, int height, Color color) {
        this.background = background;
        this.snake = snake;
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
        } while(snake.intersectingWithRect(this.rect)); //trying to avoid spawning on the place where our snake is
        this.isSpawned = true;
    }

    public void update1(double dt) {
        if (waitTimeLeft > 0) {
            waitTimeLeft -= dt;
            return;
        }

        waitTimeLeft = ogWaitBetweenUpdates;
        if (snake.intersectingWithRect(this.rect)) {
            snake.grow();
            this.rect.x = -100;
            this.rect.y = -100;
            isSpawned = false;
        }
        move();
    }


    public void move(){
        //Frog movement, random moves avoiding snake
        ArrayList<Rect> options = new ArrayList<Rect>();
        if(rect.y + (2*height) < background.y+background.height && !snake.intersectingWithRect(new Rect(rect.x , rect.y+height, this.width,this.height))){
            options.add(new Rect(rect.x , rect.y+height, this.width,this.height));
        }
        if(rect.y - height > background.y && !snake.intersectingWithRect(new Rect(rect.x ,rect.y-height, this.width,this.height))){
            options.add(new Rect(rect.x ,rect.y-height, this.width,this.height));
        }
        if(rect.x + (2*width) < background.x+background.width && !snake.intersectingWithRect(new Rect(rect.x+width , rect.y, this.width,this.height))){
            options.add(new Rect(rect.x+width , rect.y, this.width,this.height));
        }
        if(rect.x - width > background.x && !snake.intersectingWithRect(new Rect(rect.x-width , rect.y, this.width,this.height))){
            options.add(new Rect(rect.x-width , rect.y, this.width,this.height));
        }
        if (!options.isEmpty()) {
            int rnd = new Random().nextInt(options.size());
            Rect newPos = options.get(rnd);
            try {
                this.rect = newPos;
            } catch (NullPointerException e) {}
        }

    }

    public void draw(Graphics2D g2) {
        g2.setColor(color);
//        g2.fillRect(x + xPadding,  y + xPadding,  width,  height);
        g2.fillRect((int)this.rect.x + xPadding, (int)this.rect.y + xPadding, width, height);
    }
}