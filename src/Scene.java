import java.awt.Graphics;

public abstract class Scene {
	public abstract void update(double dt);
	public abstract void draw(Graphics g);

} //abstract class allow us extending Scene classes like MenuScene or Gamescene - code is shorter
