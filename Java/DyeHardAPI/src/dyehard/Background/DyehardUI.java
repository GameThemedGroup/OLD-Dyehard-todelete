package dyehard.Background;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import Engine.BaseCode;
import Engine.Rectangle;
import Engine.Text;
import Engine.Vector2;
import dyehard.UpdateObject;
import dyehard.Player.Hero;
import dyehard.World.GameState;
import dyehard.World.GameWorld;

public class DyehardUI extends UpdateObject {
    protected Hero hero;
    Rectangle frame;
    DyehardProgressBar progress;
    Text scoreText;
    List<Rectangle> hearts = new ArrayList<Rectangle>();

    final Vector2 screenSize = new Vector2(1920, 1080);

    public DyehardUI(Hero hero) {

        this.hero = hero;
        frame = new Rectangle();
        String path = "Textures/UI/Dyehard_UI_HudFrame.png";
        frame.texture = BaseCode.resources.loadImage(path);

        // TODO remove magic numbers
        frame.size = scaleToGameWorld(screenSize, new Vector2(1920, 134));

        // TODO remove magic numbers
        frame.center = new Vector2(GameWorld.RIGHT_EDGE / 2, GameWorld.TOP_EDGE
                - frame.size.getY() / 2);

        progress = new DyehardProgressBar(GameState.TargetDistance);

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

    private class DyehardProgressBar {
        protected int maxValue;
        protected int currentValue;
        protected Rectangle progress;

        List<Rectangle> markers;
        Rectangle dyeMarker;
        BufferedImage filledMarker;

        public DyehardProgressBar(int maxValue) {
            this.maxValue = maxValue;
            currentValue = 0;

            // TODO remove magic numbers
            String progressTexturePath = "Textures/UI/Dyehard_UI_Progress_Path.png";
            progress = scaledRectangle(screenSize, new Vector2(1104, 54),
                    progressTexturePath);

            // TODO remove magic numbers
            progress.center = new Vector2(GameWorld.RIGHT_EDGE / 2,
                    GameWorld.TOP_EDGE - progress.size.getY() / 2 - 1.4f);

            String markerTexturePath = "Textures/UI/Dyehard_UI_Progress_marker_empty.png";
            Rectangle baseMarker = scaledRectangle(screenSize, new Vector2(76,
                    76), markerTexturePath);

            markers = new ArrayList<Rectangle>();

            // first gate at 500 and every 900 afterwards
            // TODO magic numbers
            for (int i = 500; i < maxValue; i += 900) {
                Rectangle marker = new Rectangle(baseMarker);
                marker.center.setY(GameWorld.TOP_EDGE - marker.size.getY() / 2
                        - 0.9f);
                marker.center.setX(toWorldUnits(i));
                markers.add(marker);
            }

            filledMarker = BaseCode.resources
                    .loadImage("Textures/UI/Dyehard_UI_Progress_marker_full.png");

            String dyeMarkerTexturePath = "Textures/UI/Dyehard_UI_Progress_Dye.png";
            dyeMarker = scaledRectangle(screenSize, new Vector2(76, 76),
                    dyeMarkerTexturePath);

            // TODO magic numbers
            dyeMarker.center.setY(GameWorld.TOP_EDGE - dyeMarker.size.getY()
                    / 2 - 0.9f);

            setValue(currentValue);
        }

        public void setValue(int value) {
            currentValue = value;
            dyeMarker.center.setX(toWorldUnits(value));

            for (Rectangle r : markers) {
                if (dyeMarker.center.getX() > r.center.getX()) {
                    r.texture = filledMarker;
                }
            }
        }

        protected float toWorldUnits(int value) {
            float scale = progress.size.getX() / maxValue;
            float startX = progress.center.getX() - progress.size.getX() / 2f;

            return startX + value * scale;
        }
    }

    public static Rectangle scaledRectangle(Vector2 screenSize,
            Vector2 rectSize, String texturePath) {
        Rectangle rect = new Rectangle();
        rect.texture = BaseCode.resources.loadImage(texturePath);
        rect.size = scaleToGameWorld(screenSize, rectSize);
        return rect;
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public void update() {
        scoreText.setText(Integer.toString(GameState.Score));
        progress.setValue(GameState.DistanceTravelled);
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
