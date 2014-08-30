package dyehard;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import Engine.BaseCode;
import Engine.Rectangle;
import Engine.Vector2;
import dyehard.World.GameWorld;

public class DHR {
    static class ImageData {
        public String texturePath;
        public Vector2 targetedPixelSize;
        public Vector2 actualPixelSize;

        public BufferedImage texture = null;
    }

    public enum Image {
        UI_HUD, UI_PATH, UI_PATH_MARKER, UI_DYE_PATH_MARKER, UI_PATH_MARKER_FULL, UI_HEART
    }

    static Map<Image, ImageData> map = new HashMap<Image, ImageData>();

    static {
        // For line in csv file
        // line.split(",")
        // Image image = Enum.fromString(line[0])
        // data.texturePath = line[1] // texturePath
        // data.actualPixelSize = new Vector2(line[2], line[3])
        // data.targetedPixelSize = new Vector2(line[4], line[5])
        // data.texture = BaseCode.resources.loadImage(data.texturePath);
        // map.put(image, data);

        // TODO Grab these values from a file
        Vector2 targetSize = new Vector2(1920, 1080);

        addData(Image.UI_HUD, "Textures/UI/Dyehard_UI_HudFrame.png",
                new Vector2(1920, 134), targetSize);
        addData(Image.UI_PATH, "Textures/UI/Dyehard_UI_Progress_Path.png",
                new Vector2(1104, 54), targetSize);
        addData(Image.UI_PATH_MARKER,
                "Textures/UI/Dyehard_UI_Progress_marker_empty.png",
                new Vector2(76, 76), targetSize);
        addData(Image.UI_PATH_MARKER_FULL,
                "Textures/UI/Dyehard_UI_Progress_marker_full.png", new Vector2(
                        76, 76), targetSize);
        addData(Image.UI_DYE_PATH_MARKER,
                "Textures/UI/Dyehard_UI_Progress_Dye.png", new Vector2(76, 76),
                targetSize);

        addData(Image.UI_HEART, "Textures/UI/dyehard_ui_health_full.png",
                new Vector2(40, 40), targetSize);
    }

    static void addData(Image image, String path, Vector2 actualSize,
            Vector2 targetSize) {
        ImageData data = new ImageData();

        data.texturePath = path;
        data.actualPixelSize = actualSize;
        data.targetedPixelSize = targetSize;
        map.put(image, data);
    }

    public static BufferedImage getTexture(Image image) {
        ImageData data = map.get(image);
        if (data == null) {
            return null;
        }

        if (data.texture == null) {
            data.texture = BaseCode.resources.loadImage(data.texturePath);
        }

        return data.texture;
    }

    public static Rectangle getScaledRectangle(Image image) {
        ImageData data = map.get(image);
        if (data == null) {
            return null;
        }

        if (data.texture == null) {
            data.texture = BaseCode.resources.loadImage(data.texturePath);
        }

        Rectangle rect = new Rectangle();
        rect.texture = data.texture;
        rect.size = scaleToGameWorld(data.targetedPixelSize,
                data.actualPixelSize);
        return rect;
    }

    public static Rectangle getScaledRectangle(Vector2 screenSize,
            Vector2 rectSize, String texturePath) {
        Rectangle rect = new Rectangle();
        rect.texture = BaseCode.resources.loadImage(texturePath);
        rect.size = scaleToGameWorld(screenSize, rectSize);
        return rect;
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
