package dyehard.Background;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import Engine.Rectangle;
import Engine.Text;
import Engine.Vector2;
import dyehard.DHR;
import dyehard.DHR.ImageID;
import dyehard.DyehardDistanceMeter;
import dyehard.UpdateObject;
import dyehard.Player.Hero;
import dyehard.World.GameState;
import dyehard.World.GameWorld;

public class DyehardUI extends UpdateObject {
    protected Hero hero;
    Rectangle hud;
    DyehardDistanceMeter distanceMeter;
    Text scoreText;
    List<Rectangle> hearts = new ArrayList<Rectangle>();

    public DyehardUI(Hero hero) {
        this.hero = hero;

        hud = DHR.getScaledRectangle(ImageID.UI_HUD);
        hud.center.setX(GameWorld.RIGHT_EDGE / 2);
        hud.center.setY(fromTop(hud, 0f));

        Rectangle baseHeart = DHR.getScaledRectangle(ImageID.UI_HEART);
        hearts = new ArrayList<Rectangle>();
        for (int i = 0; i < 4; ++i) {
            Rectangle heart = new Rectangle(baseHeart);
            float width = heart.size.getX();

            // TODO magic numbers
            heart.center = new Vector2(GameWorld.RIGHT_EDGE - i * 1.62f * width
                    - 4f, GameWorld.TOP_EDGE - width / 2 - 1.4f);
            hearts.add(heart);
        }

        distanceMeter = new DyehardDistanceMeter(GameState.TargetDistance);

        // TODO magic numbers
        scoreText = new Text("", 4f, GameWorld.TOP_EDGE - 3.25f);
        scoreText.setFrontColor(Color.white);
        scoreText.setBackColor(Color.black);
        scoreText.setFontSize(18);
        scoreText.setFontName("Arial");
    }

    protected float fromTop(Rectangle image, float padding) {
        return GameWorld.TOP_EDGE - image.size.getY() / 2f - padding;
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public void update() {
        // TODO calculate number of hearts remaining
        scoreText.setText(Integer.toString(GameState.Score));
        distanceMeter.setValue(GameState.DistanceTravelled);
    }
}