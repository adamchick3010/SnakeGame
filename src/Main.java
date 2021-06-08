

public class Main {
	public static void main(String[] args) {

		Window window = Window.getWindow(); //Tworzymy okno gry
		
		Thread thread = new Thread(window);
		thread.start();
	}
}
