import java.awt.*;

import javax.swing.JFrame; //importowanie biblioteki swing

public class Window extends JFrame implements Runnable{

	public static Window window = null;
	public boolean isRunning; 
	
	public int currentState;
	public Scene currentScene;
	
	public KeyL keyListener = new KeyL();
	public MouseL mouseListener = new MouseL();
	
	public Window(int width, int height, String title) {
		setSize(width, height);
		setTitle(title);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addKeyListener(keyListener);
		addMouseListener(mouseListener);
		addMouseMotionListener(mouseListener);
		
		isRunning = true; //setting is running true by default
		
		changeState(0);
	} //setting basics
	
	public static Window getWindow() {
		if (Window.window == null) {
			Window.window = new Window(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, Constants.SCREEN_TITLE); //a method creating window whith the size
		}
		return Window.window;
	}
	
	public void close() {
		isRunning = false; // ability to close application when exit button is clicked
		
	}
	
	public void changeState(int newState) {
		currentState = newState;
		switch(currentState) {
		case 0:
			currentScene = new MenuScene(keyListener, mouseListener); // when newState equals 0 we are in the Menu Scene
			break;
		case 1:
			currentScene = new GameScene(keyListener); // when newState equals 1 we are in the game mode
			break;
		default:
			System.out.println("Unknown scene.");
			currentScene = null;
			break;
		} //this switch allow us choice the scenes between the menu and game scene
	}
	
	public void update(double dt) {
		Image dbImage = createImage(getWidth(), getHeight());
		Graphics dbg = dbImage.getGraphics();
		this.draw(dbg);
		getGraphics().drawImage(dbImage, 0, 0, this); //setting coordinates to 0 0 (XY), and setting this image observer
		
		currentScene.update(dt);
	} //double buffer allow us drawing the graphics into an off-screen and then copying them to the screen all the once
	
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.BLACK);
		g2.fillRect(0, 0, getWidth(), getHeight());
		
		currentScene.draw(g);
	}
	//Dziêki wykorzystaniu adnotacji @Override kompilator otrzymuje od nas informacjê, ¿e dana metoda bêdzie przys³aniaæ inn¹ metodê.
	@Override
	public void run() {
		double lastFrameTime = 0.0; //inicjujemy czas od 0
		try {
			while (isRunning) {
				double time = Time.getTime();
				double deltaTime = time - lastFrameTime; //delta time is time between frames (FPS)
				lastFrameTime = time;
				
				update(deltaTime);
			}//game loop
		} catch(Exception e) {
			e.printStackTrace();
		} //catching possible bugs
		this.dispose();
	}
}
