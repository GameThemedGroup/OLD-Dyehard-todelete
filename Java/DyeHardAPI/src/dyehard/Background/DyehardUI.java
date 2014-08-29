package dyehard.Background;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import Engine.BaseCode;
import Engine.Rectangle;
import Engine.Text;
import Engine.Vector2;
import dyehard.UpdateObject;
import dyehard.Player.Hero;
import dyehard.World.GameWorld;

public class DyehardUI extends UpdateObject {
    protected Hero hero;
    Rectangle frame;
    Rectangle progress;
    Text scoreText;
    List<Rectangle> hearts = new ArrayList<Rectangle>();

    public DyehardUI(Hero hero) {

        Vector2 screenSize = new Vector2(1920, 1080);
        this.hero = hero;
        frame = new Rectangle();
        String path = "Textures/UI/Dyehard_UI_HudFrame.png";
        frame.texture = BaseCode.resources.loadImage(path);

        // TODO remove magic numbers
        frame.size = scaleToGameWorld(screenSize, new Vector2(1920, 134));

        // TODO remove magic numbers
        frame.center = new Vector2(GameWorld.RIGHT_EDGE / 2, GameWorld.TOP_EDGE
                - frame.size.getY() / 2);

        progress = new Rectangle();
        path = "Textures/UI/Dyehard_UI_Progress_Path.png";
        progress.texture = BaseCode.resources.loadImage(path);

        // TODO remove magic numbers
        progress.size = scaleToGameWorld(screenSize, new Vector2(1104, 54));

        // TODO remove magic numbers
        progress.center = new Vector2(GameWorld.RIGHT_EDGE / 2,
                GameWorld.TOP_EDGE - progress.size.getY() / 2 - 1.5f);

        hearts = new ArrayList<Rectangle>();
        path = "Textures/UI/dyehard_ui_health_full.png";
        Rectangle baseHeart = new Rectangle();
        baseHeart.texture = BaseCode.resources.loadImage(path);

        // TODO remove magic numbers
        baseHeart.size = scaleToGameWorld(screenSize, new Vector2(40, 40));

        for (int i = 0; i < 4; ++i) {
            Rectangle heart = new Rectangle(baseHeart);
            float width = heart.size.getX();

            // TODO remove magic numbers
            heart.center = new Vector2(GameWorld.RIGHT_EDGE - i * 1.62f * width
                    - 4f, GameWorld.TOP_EDGE - width / 2 - 1.4f);
            hearts.add(heart);
        }

        scoreText = new Text("", 4f, GameWorld.TOP_EDGE - 3.25f);
        scoreText.setFrontColor(Color.white);
        scoreText.setBackColor(Color.black);
        scoreText.setFontSize(18);
        scoreText.setFontName("Arial");
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public void update() {
        scoreText.setText("123456");
    }

    /**
     * Converts an images pixel size to game world size Eg. An image with a
     * width of 1920 on a screen size of 1920 will have a width equal to 100% of
     * the game world.
     * 
     * @param size
     *            The size of the graphic in pixels
     * @param screen
     *            The expected size of the window in pixels
     * @return The size of the graphic in game world units
     */
    public static Vector2 scaleToGameWorld(Vector2 screen, Vector2 size) {
        float widthRatio = screen.getX() / GameWorld.RIGHT_EDGE;
        float heightRatio = screen.getY() / GameWorld.TOP_EDGE;

        Vector2 scaledSize = new Vector2();
        scaledSize.setX(size.getX() / widthRatio);
        scaledSize.setY(size.getY() / heightRatio);

        return scaledSize;
    }
}
