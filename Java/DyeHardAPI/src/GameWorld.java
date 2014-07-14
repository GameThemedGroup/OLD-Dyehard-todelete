import Engine.KeyboardInput;
import Engine.Vector2;

public class GameWorld extends GameObject {
    // private final float StartSpeed = 0.2f;
    public static float Speed;
    public static Vector2 Gravity = new Vector2(0, -0.02f);
    // private float SpeedReference;
    // private boolean stop;
    // private Timer accelerationTimer;
    //
    // private Queue<GameWorldRegion> onscreen;
    // private Queue<GameWorldRegion> upcoming;
    private Hero hero;
    // private EnemyManager eManager;
    private Player player;

    // private InfoPanel infoPanel;
    // private Laser laser;
    public GameWorld(KeyboardInput keyboard) {
        hero = new Hero();
        player = new Player(hero, keyboard);
    }

    @Override
    public void draw() {
        hero.draw();
    }

    @Override
    public void remove() {
        hero.remove();
    }

    @Override
    public void update() {
        player.update();
        hero.update();
    }
}
