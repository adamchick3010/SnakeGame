import java.awt.geom.Rectangle2D;
import java.awt.*;

public class Obstacle {
    public Rect background;
    public int width, height,size,type;
    public Color color = Color.BLUE;
    public Rect[] body = new Rect[20];
    public double obWidth,obHeight;

    public Obstacle(Rect background, int width, int height,int size, int type){ //type 1 - poziom, type 2-pion
        this.background = background;
        this.width = width;
        this.height = height;
        this.size = size;
        this.type = type;
    }
    public void spawn() {

        if(type==1) {
            double randX,randY;
            do {
                randX = (int) (Math.random() * (int) (background.width / Constants.TILE_WIDTH)) * Constants.TILE_WIDTH + background.x; //generating random number which exists in the x scale background
                randY = (int) (Math.random() * (int) (background.height / Constants.TILE_WIDTH)) * Constants.TILE_WIDTH + background.y; //generating random number which wxists in the y scale background
            }while(randX + size *width > background.width);
            for (int i = 0; i <= size; i++) {
                Rect bodyPiece = new Rect(randX + i * width, randY, width, height);
                body[i] = bodyPiece;
            }
            this.obWidth = randX + size * width;
            this.obHeight = randY +height;
        }
        if(type==2) {
            double randX,randY;
            do {
                randX = (int) (Math.random() * (int) (background.width / Constants.TILE_WIDTH)) * Constants.TILE_WIDTH + background.x; //generating random number which exists in the x scale background
                randY = (int) (Math.random() * (int) (background.height / Constants.TILE_WIDTH)) * Constants.TILE_WIDTH + background.y; //generating random number which wxists in the y scale background
            }while(randY + size *width > background.height);
            for (int i = 0; i <= size; i++) {
                Rect bodyPiece = new Rect(randX, randY + i * width, width, height);
                body[i] = bodyPiece;
            }
            this.obWidth = randX + width;
            this.obHeight = randY + size * height;
        }
    }
    public boolean intersectingWithRect(Rect rect) {
        for (int i = 0; i <=size; i ++) {
            Rect piece = body[i];
            if (intersecting(rect, piece)) return true;
        }
        return false;
    }

    public boolean intersecting(Rect r1, Rect r2) {
        return (r1.x >= r2.x && r1.x + r1.width <= r2.x + r2.width &&
                r1.y >= r2.y && r1.y + r1.height <= r2.y + r2.height);
    } //detecting crashes

    public void draw(Graphics2D g2) {
        g2.setColor(color);
        for (int i = 0; i <=size; i ++) {
            Rect piece = body[i];
            g2.fill(new Rectangle2D.Double(piece.x , piece.y , width, height));
        }
    }
}
