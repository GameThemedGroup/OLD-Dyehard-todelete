import java.awt.event.KeyEvent;

import Engine.LibraryCode;
import Engine.Rectangle;
import Engine.Vector2;
import Engine.KeyboardInput;

public class RectangleTest extends LibraryCode {
	
	Rectangle rec;
	boolean diditwork;
	
	public void initializeWorld() {
		super.initializeWorld();
		rec = new Rectangle();
		rec.size.set(10f, 10f);
		rec.center = new Vector2(50f, 50f);
		//rec.setImage("Heart_Empty.png");
		diditwork = false;
	}
	
	public void updateWorld() {
		//System.out.println("Hello World!");
		if(keyboard.isButtonDown(KeyEvent.VK_A)) {
			rec.center.setX(rec.center.getX() - 2f);
		}
		
		if(keyboard.isButtonDown(KeyEvent.VK_D)) {
			rec.center.setX(rec.center.getX() + 2f);
		}
		
		if(keyboard.isButtonDown(KeyEvent.VK_W)) {
			rec.center.setY(rec.center.getY() + 2f);
		}
		
		if(keyboard.isButtonDown(KeyEvent.VK_S)) {
			rec.center.setY(rec.center.getY() - 2f);
		}
		
		if(keyboard.isButtonTapped(KeyEvent.VK_K)){
			System.out.println("John is the greatest");
			diditwork = true;
		}
		
		if(!diditwork) {
			System.out.println("It didn't work");
		}
		
		//System.out.println(diditwork);
	}
	
}