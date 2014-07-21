package Dyehard.World;

import Engine.Rectangle;

public abstract class GameWorldRegion extends Rectangle {
    abstract public boolean isOffScreen();

    abstract public float rightEdge();
}
