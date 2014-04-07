﻿using System;
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

        public Canvas(Hero hero, float leftEdge) : base()
        {
            this.hero = hero;

            this.painting = new Queue<XNACS1Circle>();

            float width = Game.rightEdge() * 1.8f;
            float height = Game.topEdge();

            float position = (width * 0.5f) + leftEdge;

            this.box = new XNACS1Rectangle(new Vector2(position, height/2), width, height);
            this.box.Color = Color.WhiteSmoke;
        }

        public override void move()
        {
            box.CenterX -= Game.Speed;
            foreach (XNACS1Circle c in painting)
            {
                c.CenterX -= Game.Speed;
            }
        }

        public override void interact()
        {
            XNACS1Rectangle heroBox = hero.getBox();
            if (contains(heroBox))
            {
                XNACS1Circle paint = new XNACS1Circle(heroBox.Center, 1.5f);
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

        public override bool isOffScreen()
        {
            return box.CenterX + box.Width / 2 <= 0;
        }

        public override float rightEdge()
        {
            return box.CenterX + box.Width / 2;
        }
    }
}
