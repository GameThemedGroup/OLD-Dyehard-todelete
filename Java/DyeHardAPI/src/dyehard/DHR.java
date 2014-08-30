package dyehard;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import Engine.BaseCode;
import Engine.Rectangle;
import Engine.Vector2;
import dyehard.World.GameWorld;

public class DHR {
    public interface CsvParser {
        public void parseData(String data);
    }

    static class ImageData {
        public String texturePath;
        public Vector2 targetedPixelSize;
        public Vector2 actualPixelSize;

        public BufferedImage texture = null;
    }

    public enum ImageID {
        UI_HUD, UI_PATH, UI_PATH_MARKER, UI_DYE_PATH_MARKER, UI_PATH_MARKER_FULL, UI_HEART
    }

    static Map<ImageID, ImageData> map = new HashMap<ImageID, ImageData>();

    static {
        loadFromFile("Resources/Textures/ImageData.csv", new ImageDataParser());
    }

    public static void loadFromFile(String csvPath, CsvParser parser) {
        InputStream input = DHR.class.getClassLoader().getResourceAsStream(
                csvPath);
        BufferedReader br = new BufferedReader(new InputStreamReader(input));
        String line;

        try {

            br = new BufferedReader(new InputStreamReader(input));
            line = br.readLine(); // discard table headers
            while ((line = br.readLine()) != null) {
                parser.parseData(line);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static class ImageDataParser implements CsvParser {
        @Override
        public void parseData(String line) {
            String[] data = line.split(",");

            ImageID image = ImageID.valueOf(data[0]);
            int screenWidth = Integer.valueOf(data[1]);
            int screenHeight = Integer.valueOf(data[2]);
            int actualWidth = Integer.valueOf(data[3]);
            int actualHeight = Integer.valueOf(data[4]);
            String path = data[5];

            addData(image, path, new Vector2(actualWidth, actualHeight),
                    new Vector2(screenWidth, screenHeight));
        }
    }

    static void addData(ImageID image, String path, Vector2 actualSize,
            Vector2 targetSize) {
        ImageData data = new ImageData();

        data.texturePath = path;
        data.actualPixelSize = actualSize;
        data.targetedPixelSize = targetSize;
        map.put(image, data);
    }

    public static BufferedImage getTexture(ImageID image) {
        ImageData data = map.get(image);
        if (data == null) {
            return null;
        }

        if (data.texture == null) {
            data.texture = BaseCode.resources.loadImage(data.texturePath);
        }

        return data.texture;
    }

    public static Rectangle getScaledRectangle(ImageID image) {
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
