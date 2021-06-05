import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class MenuScene extends Scene {
	
	public KeyL keyListener; //keyboard listener
	public MouseL mouseListener; //mouse listener
	public BufferedImage title, play, playPressed, exit, exitPressed; //png images - menu tabs
	public Rect playRect, exitRect, titleRect; //places for the buttons
	
	public BufferedImage playCurrentImage, exitCurrentImage; //hovered buttons
	
	
	
	public MenuScene(KeyL keyListener, MouseL mouseListener) {
		this.keyListener = keyListener;
		this.mouseListener = mouseListener;
		
		try {
			BufferedImage spritesheet = ImageIO.read(new File("assets/menuSprite.png"));
			title = spritesheet.getSubimage(0,  242, 960, 240); //subimage - cutted interesting element of png file from the assets folder
			play = spritesheet.getSubimage(0,  121, 261, 121); // cutted play image
			playPressed = spritesheet.getSubimage(264,  121, 261, 121);
			exit = spritesheet.getSubimage(0,  0, 233, 93);
			exitPressed = spritesheet.getSubimage(264,  0,  233, 93);
			
		}catch(Exception e) {
			e.printStackTrace();
		} // catching possibly bugs
		
		playCurrentImage = play; //allows us to change image to pressed 
		exitCurrentImage = exit; //same as above
		
		titleRect = new Rect(240, 100, 300, 100); //title static coordinates in window
		playRect = new Rect(310, 280, 150, 70); //play button static coordinates in window
		exitRect = new Rect(318, 355, 130, 55); //exit button static coordinates in window

	}
	
	@Override
	public void update(double dt) {
		if (mouseListener.getX() >= playRect.x && mouseListener.getX() <= playRect.x + playRect.width && mouseListener.getY() >= playRect.y && mouseListener.getY() <= playRect.y + playRect.height) {
			playCurrentImage = playPressed; // hovering play button
			if (mouseListener.isPressed()) {
				Window.getWindow().changeState(1); //changing newState to 1 so the game is starting
			}
		} else {
			playCurrentImage = play; //unhovering play button
		}
		if (mouseListener.getX() >= exitRect.x && mouseListener.getX() <= exitRect.x + exitRect.width && mouseListener.getY() >= exitRect.y && mouseListener.getY() <= exitRect.y + exitRect.height) {
			exitCurrentImage = exitPressed; //hovering the exit button
			if(mouseListener.isPressed()) {
				Window.getWindow().close();
			} //after clicking exit window is closing
		} else {
			exitCurrentImage = exit; //unhovering the exit button
		}//looking for the exit button and making it hover
	}
	
	@Override
	public void draw(Graphics g) {

		g.setColor(new Color(164, 55, 189)); //setting background color for menu
		g.fillRect(0,  0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT); //setting size of background for the window size
		
		g.drawImage(title,  (int)titleRect.x, (int)titleRect.y, (int)titleRect.width, (int)titleRect.height, null); // setting size of title
		g.drawImage(playCurrentImage, (int)playRect.x, (int)playRect.y, (int)playRect.width, (int)playRect.height, null); // setting size of play button
		g.drawImage(exitCurrentImage, (int)exitRect.x, (int)exitRect.y, (int)exitRect.width, (int)exitRect.height, null); // setting size of exit button
		
	}

}