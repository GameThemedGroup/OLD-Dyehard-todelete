package dyehard.World;

import Engine.BaseCode;
import Engine.Vector2;
import dyehard.GameObject;
import dyehard.GameWorld;
import dyehard.Obstacles.Obstacle;

public class PlatformSingle extends Obstacle {
    private GameObject arc;

    public PlatformSingle() {
    }

    public PlatformSingle(Vector2 center) {
        this.center = center;
        size = new Vector2(6.5f, 1.25f);
        velocity = new Vector2(-GameWorld.Speed, 0f);
        shouldTravel = true;
        texture = BaseCode.resources
                .loadImage("Textures/Background/Warp_Divider.png");

        arc = new GameObject();
        arc.center = center.clone();
        arc.velocity = velocity;
        arc.texture = BaseCode.resources
                .loadImage("Textures/Background/Warp_Divider_Arc.png");
        arc.size = size.clone();
        arc.setPanning(true);
        arc.setPanningSheet(arc.texture, 260, 50, 10, 2, false);
        // so the platform is on top of the arc
        removeFromAutoDrawSet();
        addToAutoDrawSet();
    }

    @Override
    public void update() {
        super.update();
        if (center.getX() < (GameWorld.LEFT_EDGE - 5f)) {
            arc.destroy();
        }
    }

}
