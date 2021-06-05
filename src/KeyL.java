import java.awt.event.KeyAdapter;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class KeyL extends KeyAdapter implements KeyListener {
	private boolean[] keyPressed = new boolean[128];
	
	@Override
	public void keyPressed(KeyEvent keyEvent) {
		keyPressed[keyEvent.getKeyCode()] = true;
	}//reading keycode of key pressed on out keyboard
	
	@Override
	public void keyReleased(KeyEvent keyEvent) {
		keyPressed[keyEvent.getKeyCode()] = false;
	}//reading realising the key pressed
	
	public boolean isKeyPressed(int keyCode) {
		return keyPressed[keyCode];
	}//checking the key is pressed
}