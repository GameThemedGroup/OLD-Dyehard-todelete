package dyehard.Player;

import java.util.HashMap;
import java.util.Map;

import Engine.BaseCode;
import Engine.Vector2;
import dyehard.DyehardRectangle;
import dyehard.Player.Hero.Direction;

public class HeroEffect {
    public class Offset {
        public Offset(float rotation, float x, float y) {
            this.rotation = rotation;
            transform = new Vector2(x, y);
        }

        public float rotation;
        public Vector2 transform;
    }

    private final Hero hero;

    private final DyehardRectangle repelLeft;
    private final DyehardRectangle repelRight;
    private final DyehardRectangle invin;
    private final DyehardRectangle boost;

    protected Map<Direction, Offset> offsets;

    public HeroEffect(Hero hero) {
        this.hero = hero;

        repelLeft = new DyehardRectangle();
        repelLeft.rotate = 180f;
        repelLeft.center = hero.center.clone().add(new Vector2(-6f, 0f));
        repelLeft.size = new Vector2(11.25f, 25f);
        repelLeft.texture = BaseCode.resources
                .loadImage("Textures/Hero/fx_repel_animSheet.png");
        repelLeft.setUsingSpriteSheet(true);
        repelLeft.setSpriteSheet(repelLeft.texture, 225, 500, 7, 2);
        repelLeft.visible = false;

        repelRight = new DyehardRectangle();
        repelRight.center = hero.center.clone().add(new Vector2(6f, 0f));
        repelRight.size = new Vector2(11.25f, 25f);
        repelRight.texture = BaseCode.resources
                .loadImage("Textures/Hero/fx_repel_animSheet.png");
        repelRight.setUsingSpriteSheet(true);
        repelRight.setSpriteSheet(repelRight.texture, 225, 500, 7, 2);
        repelRight.visible = false;

        invin = new DyehardRectangle();
        invin.center = hero.center.clone();
        invin.size = new Vector2(10f, 15f);
        invin.texture = BaseCode.resources
                .loadImage("Textures/Hero/fx_invincibility_AnimSheet.png");
        invin.setUsingSpriteSheet(true);
        invin.setSpriteSheet(invin.texture, 200, 300, 10, 2);
        invin.visible = false;

        // Constant offset for all directions
        offsets = new HashMap<Direction, Offset>();
        offsets.put(Direction.UP, new Offset(90f, -hero.size.getX() * 0.3f,
                -hero.size.getY() * 0.2f));
        offsets.put(Direction.DOWN, new Offset(-90f, -hero.size.getX() * 0.3f,
                hero.size.getY() * 0.2f));
        offsets.put(Direction.LEFT, new Offset(180f, hero.size.getX() / 1.8f,
                hero.size.getY() * 0.1f));
        // offsets.put(Direction.RIGHT, new Offset(30, -1, 1));
        offsets.put(Direction.NEUTRAL, new Offset(0f, -hero.size.getX() / 1.6f,
                hero.size.getY() * 0.1f));

        boost = new DyehardRectangle();
        Offset offset = offsets.get(hero.directionState);
        boost.rotate = offset.rotation;
        boost.center = hero.center.clone().add(offset.transform);
        boost.size = new Vector2(2f, 2f);
        boost.texture = BaseCode.resources
                .loadImage("Textures/Hero/Dye_boost_AnimSheet.png");
        boost.setUsingSpriteSheet(true);
        boost.setSpriteSheet(boost.texture, 40, 40, 8, 2);
        boost.visible = true;
    }

    public void update() {
        Offset offset = offsets.get(hero.directionState);
        boost.rotate = offset.rotation;
        boost.center = hero.center.clone().add(offset.transform);

        if (hero.isInvin) {
            if (!invin.visible) {
                invin.visible = true;
            }
            invin.center = hero.center.clone();
        } else {
            if (invin.visible) {
                invin.visible = false;
            }
        }

        if (hero.isRepel) {
            if (!repelRight.visible) {
                repelLeft.visible = true;
                repelRight.visible = true;
            }
            repelLeft.center = hero.center.clone().add(new Vector2(-6f, 0f));
            repelRight.center = hero.center.clone().add(new Vector2(6f, 0f));
        } else {
            if (repelRight.visible) {
                repelLeft.visible = false;
                repelRight.visible = false;
            }
        }
    }

    public void drawOnTop() {
        invin.removeFromAutoDrawSet();
        invin.addToAutoDrawSet();
        repelLeft.removeFromAutoDrawSet();
        repelLeft.addToAutoDrawSet();
        repelRight.removeFromAutoDrawSet();
        repelRight.addToAutoDrawSet();
        boost.removeFromAutoDrawSet();
        boost.addToAutoDrawSet();
    }

}
