package dyehard.Player;

import Engine.BaseCode;
import Engine.Vector2;
import dyehard.DyehardRectangle;

public class HeroEffect {
    private final Hero hero;

    private final DyehardRectangle repelLeft;
    private final DyehardRectangle repelRight;
    private final DyehardRectangle invin;

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
    }

    public void update() {
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
    }

}
