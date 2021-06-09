import java.awt.event.KeyAdapter;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

/**
 * Obsługa klawiszy
 */
public class KeyL extends KeyAdapter implements KeyListener {
	private boolean[] keyPressed = new boolean[128];

	/**
	 * Wczytanie kodu przycisku
	 * @param keyEvent
	 */
	@Override
	public void keyPressed(KeyEvent keyEvent) {
		keyPressed[keyEvent.getKeyCode()] = true;
	}//reading keycode of key pressed on out keyboard

	/**
	 * Sprawdzenie czy przycisk został uwolniony
	 * @param keyEvent
	 */
	@Override
	public void keyReleased(KeyEvent keyEvent) {
		keyPressed[keyEvent.getKeyCode()] = false;
	}//reading realising the key pressed

	/**
	 * Sprawdzenie czy przycisk został wciśnięty
	 * @param keyCode
	 * @return
	 */
	public boolean isKeyPressed(int keyCode) {
		return keyPressed[keyCode];
	}//checking the key is pressed
}