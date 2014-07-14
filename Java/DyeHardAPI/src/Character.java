import Engine.Rectangle;
import Engine.Vector2;

public class Character extends GameObject {
	
	//private boolean alive;
    protected Rectangle position;
    private Rectangle nextPosition;
    //private ArrayList<Rectangle> pendingCollisions;
    
    public Character(Vector2 position, float width, float height)
    {
    	this.position = new Rectangle();
    	this.position.center.set(position);
    	this.position.size.set(width, height);
    	this.position.color = DyeHard.randomColor();
    	
    	this.nextPosition = new Rectangle();
    	this.nextPosition.center.set(position);
    	this.nextPosition.size.set(width, height);
    	this.nextPosition.color = DyeHard.randomColor();
    	this.nextPosition.visible = false;

    	// set object into motion;
    	this.position.velocity = new Vector2(0, 0);
    	//this.alive = true;
    	//this.pendingCollisions = new ArrayList<Rectangle>();
    }
	
	public void remove() {
		position.removeFromAutoDrawSet();
        nextPosition.removeFromAutoDrawSet();
	}
	
	public void draw() {
		//.TopOfAutoDrawSet
		//There is a function in the library under the ResourceHandler
		//class that is called moveToFrontOfDrawSet that does this.
		//moveToFrontOfDrawSet has been commented out of the
		//Primitive class so this will have to do.
		position.removeFromAutoDrawSet();
		position.addToAutoDrawSet();
	}
	
	public void update() {
		position.center.add(position.velocity);
	}
}
