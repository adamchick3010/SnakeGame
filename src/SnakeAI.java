import java.awt.geom.Rectangle2D;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Klasa prezentująca węża sterowanego przez AI
 */
public class SnakeAI {
    public Rect[] body = new Rect[300];
    public double bodyWidth, bodyHeight;
    public int size;
    public int tail = 0; //starting value
    public int head = 0; //starting value
    public Frog frog;
    public Snake snake;
    public Food food;
    public Obstacle obstacle;

    //snake movement
    public Direction direction = Direction.RIGHT;

    public double ogWaitBetweenUpdates = 0.1f;
    public double waitTimeLeft = ogWaitBetweenUpdates;

    public Rect background;

    /**
     *Tworzy obiekt klasy SnakeAI
     * @param obstacle - przeszkoda na planszy
     * @param snake - wąż sterowany przez gracza
     * @param food - jedzenie
     * @param frog - żaba
     * @param size - rozmiar węża AI
     * @param startX - pozycja startowa x
     * @param startY - pozycja startowa y
     * @param bodyWidth - szerokość elementu
     * @param bodyHeight - długość elementu
     * @param background - okno gry
     */
    public SnakeAI(Obstacle obstacle,Snake snake, Food food,Frog frog, int size, double startX, double startY, double bodyWidth, double bodyHeight, Rect background) { //constructor
        this.obstacle = obstacle;
        this.snake = snake;
        this.food = food;
        this.frog = frog;
        this.size = size; //integer consists size of snake
        this.bodyWidth = bodyWidth; //width of body - must be same as one piece of field width
        this.bodyHeight = bodyHeight; //length of body
        this.background = background; //for detecting boundaries

        for (int i = 0; i <= size; i++) {
            Rect bodyPiece = new Rect(startX + i * bodyWidth, startY, bodyWidth, bodyHeight);
            body[i] = bodyPiece;
            head++;
        } // this loop allows creating place for new rectangle
        head--;
    }

    /**
     * Aktualizacja pozucji i kolizji
     * @param dt
     */
    public void update(double dt) {
        if (waitTimeLeft > 0) {
            waitTimeLeft -= dt;
            return;
        }// setting time for update meanwhile the deltatime - wait time left is time between hitted key for example and next time with interaction of game


        if (intersectingWithRect(snake.body[head])||intersectingWithSnake(snake)) {
            Window.getWindow().changeState(0);
        } // if our snake has a crush we are changing state for 0 so we re going back to the menu


        waitTimeLeft = 0.2f;
        snake_movement();
        waitTimeLeft = ogWaitBetweenUpdates;
        double newX = 0;
        double newY = 0;


        if (direction == Direction.RIGHT) {
            newX = body[head].x + bodyWidth;
            newY = body[head].y;
        } //when we go right - our new x position is going on the right place
        else if (direction == Direction.LEFT){
            newX = body[head].x - bodyWidth;
            newY = body[head].y; //same for the left side
        } else if(direction == Direction.UP) {
            newX = body[head].x;
            newY = body[head].y - bodyHeight;
        } else if (direction == Direction.DOWN) {
            newX = body[head].x;
            newY = body[head].y + bodyHeight;
        }
        body[(head + 1) % body.length] = body[tail]; //we're taking the tail's position and moving it to the head's position
        body[tail] = null; // tail = null because we re moving it to the front of our snake
        head = (head + 1) % body.length; //we're making new index for the tail because it is one place moved
        tail = (tail + 1) % body.length; //we're making new index for the tail because it is one place moved

        body[head].x = newX;
        body[head].y = newY;
        if (intersectingWithRect(this.food.rect)) {
            grow();
            this.food.rect.x = -100;
            this.food.rect.y = -100;
            food.isSpawned = false;
        }
        if (intersectingWithRect(this.frog.rect)) {
            grow();
            this.frog.rect.x = -100;
            this.frog.rect.y = -100;
            frog.isSpawned = false;
        }

    }

    /**
     * Zwiększanie węża po zjedzeniu elementu gry
     */
    public void grow() {
        double newX = 0;
        double newY = 0;

        if (direction == Direction.RIGHT) {
            newX = body[tail].x + bodyWidth;
            newY = body[tail].y;
        } //when we go right - our new x position is going on the right place
        else if (direction == Direction.LEFT){
            newX = body[tail].x - bodyWidth;
            newY = body[tail].y; //same for the left side
        } else if(direction == Direction.UP) {
            newX = body[tail].x;
            newY = body[tail].y - bodyHeight;
        } else if (direction == Direction.DOWN) {
            newX = body[tail].x;
            newY = body[tail].y + bodyHeight;
        }

        Rect newBodyPiece = new Rect(newX, newY, bodyWidth, bodyHeight);

        tail = (tail - 1) % body.length;
        body[tail] = newBodyPiece;

    } // snake growing after eating food

    /**
     * Wykrywanie kolizji z innym elementem
     * @param rect
     * @return
     */
    public boolean intersectingWithRect(Rect rect) {
        for (int i = tail; i != head; i = (i + 1) % body.length) {
            if (intersecting(rect, body[i])) return true;
        }
        return false;
    }//detecting crashes with food

    /**
     * Wykrywanie kolizji z wężem sterowanym przez gracza
     * @param snake
     * @return
     */
    public boolean intersectingWithSnake(Snake snake){
        for(int i = snake.tail; i != snake.head; i = (i + 1) % snake.body.length){
            if(intersectingWithRect(snake.body[i])) return true;
        }
        return false;
    }

    /**
     * Kolizja pomiędzy 2 elementami
     * @param r1
     * @param r2
     * @return
     */
    public boolean intersecting(Rect r1, Rect r2) {
        return (r1.x >= r2.x && r1.x + r1.width <= r2.x + r2.width &&
                r1.y >= r2.y && r1.y + r1.height <= r2.y + r2.height);
    } //detecting crashes

    /**
     * Zmiana kierunku ruchu
     * @param newDirection
     */
    public void changeDirection(Direction newDirection) {
        if (newDirection == Direction.RIGHT && direction != Direction.LEFT)
            direction = newDirection;
        else if (newDirection == Direction.LEFT && direction != Direction.RIGHT)
            direction = newDirection;
        else if (newDirection == Direction.UP && direction != Direction.DOWN)
            direction = newDirection;
        else if (newDirection == Direction.DOWN && direction != Direction.UP)
            direction = newDirection;
    } //thanks to these else ifs we can avoid snake going into himself (he cant stop etc)

    /**
     * Ruch węża AI
     * generowanie kolejnych kierunków ruchu węża sterowanego przez AI
     * głowym założeniem AI jest unikanie granic okna gry oraz przeszkód znajdującyhc się na planszy
     * dodatkowo snake posuzkuje obiektów klasy Food i rusza sie w ich kierunku
     */
    public void snake_movement(){
        //Searhing for food
        if(food.rect.x == body[head].x){
            if(direction ==Direction.RIGHT)changeDirection(Direction.UP);
            else changeDirection(Direction.DOWN);
        }
        else if(food.rect.y == body[head].y){
            if(direction ==Direction.UP)changeDirection(Direction.LEFT);
            else changeDirection(Direction.RIGHT);
        }

//        if(frog.rect.x == body[head].x){
//            if(direction == Direction.RIGHT && direction!= Direction.UP)changeDirection(Direction.UP);
//            else if (direction!=Direction.DOWN)changeDirection(Direction.DOWN);
//        }
//        else if(frog.rect.y == body[head].y){
//            if(direction ==Direction.UP && direction != Direction.LEFT)changeDirection(Direction.LEFT);
//            else if(direction !=Direction.RIGHT) changeDirection(Direction.RIGHT);
//        }


        //Avoiding obstacle
        if(obstacle.intersectingWithRect(new Rect(body[head].x+bodyWidth,body[head].y,body[head].width,body[head].height))){
            changeDirection(Direction.DOWN);
        }
        else if(obstacle.intersectingWithRect(new Rect(body[head].x-bodyWidth,body[head].y,body[head].width,body[head].height))){
            changeDirection(Direction.UP);
        }
        else if(obstacle.intersectingWithRect(new Rect(body[head].x,body[head].y+bodyWidth,body[head].width,body[head].height))){
            changeDirection(Direction.RIGHT);
        }
        else if(obstacle.intersectingWithRect(new Rect(body[head].x,body[head].y-bodyWidth,body[head].width,body[head].height))){
            changeDirection(Direction.LEFT);
        }

        //Avoiding borders
        if(body[head].x   <= background.x && direction==Direction.LEFT){changeDirection(Direction.DOWN);}
        else if(body[head].x + bodyWidth >= background.x+background.width&& direction == Direction.RIGHT){changeDirection(Direction.UP);}
        else if((body[head].y ) <= background.y && direction ==Direction.UP){changeDirection(Direction.LEFT);}
        else if(body[head].y + bodyWidth >= background.y + background.height && direction ==Direction.DOWN){changeDirection(Direction.RIGHT);}

        //Avoiding corners
        if(body[head].x  <= background.x && body[head].y  <= background.y){
            if (direction == Direction.UP) changeDirection(Direction.RIGHT);
            if (direction == Direction.LEFT) changeDirection(Direction.DOWN);
        }
        else if(body[head].x   <= background.x && body[head].y + bodyWidth >= background.y + background.height){
            if (direction == Direction.DOWN) changeDirection(Direction.RIGHT);
            if (direction == Direction.LEFT) changeDirection(Direction.UP);
        }
        else if(body[head].x + bodyWidth >= background.x+background.width && body[head].y + bodyWidth >= background.y + background.height){
            if (direction == Direction.DOWN) changeDirection(Direction.LEFT);
            if (direction == Direction.RIGHT) changeDirection(Direction.UP);
        }
        else if(body[head].x + bodyWidth >= background.x+background.width && body[head].y  <= background.y) {
            if (direction == Direction.UP) changeDirection(Direction.LEFT);
            if (direction == Direction.RIGHT) changeDirection(Direction.DOWN);
        }
    }

    /**
     * Rysowanie wężaAI
     * @param g2
     */
    public void draw(Graphics2D g2) {
        for (int i = tail; i != head; i = (i+1) % body.length) {
            Rect piece = body[i]; //incrementing our snake
            double subWidth = (piece.width - 6.0) / 2.0; //3px gap between pieces of snake - width
            double subHeight = (piece.height - 6.0) / 2.0; //3px gap between pieces of snake - height

            g2.setColor(Color.YELLOW);
            //one piece of snake's body is made from 4 smaller rectangles
            g2.fill(new Rectangle2D.Double(piece.x + 2.0, piece.y + 2.0, subWidth, subHeight)); //first small rectangle
            g2.fill(new Rectangle2D.Double(piece.x + 4.0 + subWidth, piece.y + 2.0, subWidth, subHeight)); //second small rectangle
            g2.fill(new Rectangle2D.Double(piece.x + 2.0, piece.y + 4.0 + subHeight, subWidth, subHeight)); //third small rectangle
            g2.fill(new Rectangle2D.Double(piece.x + 4.0 + subWidth, piece.y + 4.0 + subHeight, subWidth, subHeight)); //fourth small rectangle
        } //this loop checks after every one piece of time should we grow or not
    }
}
