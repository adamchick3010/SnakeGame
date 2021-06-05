import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MouseL extends MouseAdapter implements MouseMotionListener {
	public boolean isPressed = false;
	public double x = 0.0, y = 0.0;
	
	@Override
	public void mousePressed(MouseEvent e) {
		isPressed = true;
	}//checking mouse button is pressed
	
	@Override
	public void mouseReleased(MouseEvent e) {
		isPressed = false;
	}//checking mouse button is released
	
	@Override
	public void mouseMoved(MouseEvent e) {
		this.x = e.getX();
		this.y = e.getY();
	}//reading coordinates of cursor position
	
	public double getX() { return this.x; }//returning x coordinate of cursor position
	public double getY() { return this.y; }//returning y coordinate of cursor position
	
	public boolean isPressed() { return this.isPressed; }
	
}
