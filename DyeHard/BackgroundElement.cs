﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using XNACS1Lib;
using Microsoft.Xna.Framework;

namespace DyeHard
{
    abstract class BackgroundElement
    {
        abstract public void interact();
        abstract public void move();
        abstract public void centerOnScreen();
        abstract public bool isOffScreen();
        abstract public bool rightEdgeIsOnScreen();
    }
}
