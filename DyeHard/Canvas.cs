using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using XNACS1Lib;
using Microsoft.Xna.Framework;

namespace DyeHard
{
    class Canvas : BackgroundElement
    {
        XNACS1Rectangle box;
        Hero hero;
        Queue<XNACS1Circle> painting;

        public Canvas(Hero hero)
            : base()
        {
            this.hero = hero;

            this.painting = new Queue<XNACS1Circle>();

            float length = XNACS1Base.World.WorldMax.X;
            float width = XNACS1Base.World.WorldMax.Y;

            float offScreen = (length * 0.5f) + XNACS1Base.World.WorldMax.X;

            this.box = new XNACS1Rectangle(new Vector2(offScreen, width/2), length, width);
            this.box.Color = Color.AntiqueWhite;
        }

        public override void move()
        {
            box.CenterX += Game.Speed;
            foreach (XNACS1Circle c in painting)
            {
                c.CenterX += Game.Speed;
            }
        }

        public override void interact()
        {
            XNACS1Rectangle heroBox = hero.getBox();
            if (contains(heroBox))
            {
                XNACS1Circle paint = new XNACS1Circle(heroBox.Center, 0.2f);
                paint.Color = heroBox.Color;
                painting.Enqueue(paint);
            }
        }

        private bool contains(XNACS1Rectangle other)
        {
            float leftEdge = box.LowerLeft.X;
            float rightEdge = leftEdge + box.Width;

            float otherLeftEdge = other.MinBound.X;
            float otherRightEdge = otherLeftEdge + other.Width;

            return otherLeftEdge > leftEdge && otherRightEdge < rightEdge;
        }

        public override void centerOnScreen()
        {
            this.box.CenterX -= XNACS1Base.World.WorldMax.X;
        }

        public override bool isOffScreen()
        {
            return box.CenterX + box.Width / 2 <= 0;
        }

        public override bool rightEdgeIsOnScreen()
        {
            float rightEdge = XNACS1Base.World.WorldMax.X;
            return box.CenterX + box.Width / 2 <= rightEdge;
        }
    }
}
