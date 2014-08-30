package dyehard;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import Engine.Rectangle;
import dyehard.DHR.ImageID;
import dyehard.World.GameWorld;

public class DyehardDistanceMeter {
    protected int maxValue;
    protected int currentValue;
    protected Rectangle progress;

    List<Rectangle> markers;
    Rectangle dyeMarker;
    BufferedImage filledMarker;

    // TODO remove magic numbers
    public DyehardDistanceMeter(int maxValue) {
        this.maxValue = maxValue;
        currentValue = 0;

        progress = DHR.getScaledRectangle(ImageID.UI_PATH);

        progress.center.setX(GameWorld.RIGHT_EDGE / 2);
        progress.center.setY(fromTop(progress, 1.4f));

        Rectangle baseMarker = DHR.getScaledRectangle(ImageID.UI_PATH_MARKER);
        markers = new ArrayList<Rectangle>();

        // first gate at 500 and every 900 afterwards
        for (int i = 500; i < maxValue; i += 900) {
            Rectangle marker = new Rectangle(baseMarker);
            marker.center.setX(toWorldUnits(i));
            marker.center.setY(fromTop(marker, 0.9f));
            markers.add(marker);
        }

        filledMarker = DHR.getTexture(ImageID.UI_PATH_MARKER_FULL);

        dyeMarker = DHR.getScaledRectangle(ImageID.UI_DYE_PATH_MARKER);
        dyeMarker.center.setY(fromTop(dyeMarker, 0.9f));

        setValue(currentValue);
    }

    protected float fromTop(Rectangle image, float padding) {
        return GameWorld.TOP_EDGE - image.size.getY() / 2f - padding;
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