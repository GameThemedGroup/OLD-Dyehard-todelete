import java.awt.Color;
import java.util.Random;

import Engine.LibraryCode;

//This is "Game.cs"
public class DyeHard extends LibraryCode {
	
	//DyeHard Dye Colors
	public static int colorCount = 6;
	public static Color Green = new Color(38, 153, 70);
	public static Color Red = new Color(193, 24, 30);
    public static Color Yellow = new Color(228, 225, 21);
    public static Color Teal = new Color(90, 184, 186);
    public static Color Pink = new Color(215, 59, 148);
    public static Color Blue = new Color(50, 75, 150);
    
    private enum State
    {
    	BEGIN,
    	PAUSED,
    	PLAYING,
    	GAMEOVER
    };
    
    //screen objects
//    private Background background;
//    private Screen startScreen;
//    private Window pauseScreen;
//    private Window gameOverScreen;

    //game objects
    private GameWorld world;
    
    //Game state
    private State state;
    
    public void initializeWorld() {
    	super.initializeWorld();
    	
    	//Starting state should be begin
    	//Using playing to test controls
    	state = State.PLAYING;
    	//I pass keyboard into GameWorld when creating it because
    	//I need access to BaseCode for keyboard inputs.
    	world = new GameWorld(keyboard);
    	System.out.println("Width = " + getInitWidth() + " Length = " + getInitHeight());
    }
    
    public void updateWorld() {
    	switch (state) {
    		case PLAYING:
    			world.update();
    			world.draw();
    			break;
      default:
        break;
    	}
    }
    
    public static Color randomColor() {
    	Random rand = new Random();
    	return colorPicker(rand.nextInt(6));
    }
    
    private static Color colorPicker(int choice) {
    	switch (choice) {
	    	case 0: return Green;
	        case 1: return Red;
	        case 2: return Yellow;
	        case 3: return Teal;
	        case 4: return Pink;
	        case 5: return Blue;
    	}
    	return Color.black;
    }
	
}
