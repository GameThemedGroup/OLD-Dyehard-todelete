using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using XNACS1Lib;
using Microsoft.Xna.Framework;

namespace Dyehard
{
    abstract class Environment
    {
        abstract public void interact();
        abstract public void move();
        abstract public void draw();
        abstract public bool isOffScreen();
        abstract public float rightEdge();
    }
}
