using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using XNACS1Lib;
using Microsoft.Xna.Framework;

namespace Dyehard
{
    class Space : BackgroundElement
    {
        public static float width = Game.rightEdge() * 3f;
        public static int powerupCount = 6;
        private XNACS1Rectangle space;
        private Hero hero;
        private List<PowerUp> powerups;

        public Space(Hero hero, float leftEdge) : base()
        {
            this.hero = hero;

            this.powerups = new List<PowerUp>();

            float height = Game.topEdge();
            float position = (width * 0.5f) + leftEdge;

            this.space = new XNACS1Rectangle(new Vector2(position, height/2), width, height);
            this.space.Color = new Color(25,25,25);

            // add powerups to canvas
            List<Color> colors = Game.randomColorSet(powerupCount);
            float region = (rightEdge() - leftEdge) / powerupCount;
            for (int i = 0; i < powerupCount; i++)
            {
                float regionLeft = leftEdge + (i * region);
                float regionRight = regionLeft + region;
                powerups.Add(new PowerUp(hero, regionLeft, regionRight, colors[i]));
            }
            powerups.Add(new ZeroGravityPowerUp(hero, leftEdge, rightEdge(), Game.randomColor()));
        }

        public override void move()
        {
            space.CenterX -= Background.Speed;
            space.TopOfAutoDrawSet();

            foreach (PowerUp p in powerups)
            {
                p.move();
            }
        }

        public override void interact()
        {
            XNACS1Rectangle heroBox = hero.getBox();

            foreach (PowerUp p in powerups)
            {
                p.interact();
            }
        }

        private bool contains(XNACS1Rectangle other)
        {
            float leftEdge = space.LowerLeft.X;
            float rightEdge = leftEdge + space.Width;

            float otherLeftEdge = other.MinBound.X;
            float otherRightEdge = otherLeftEdge + other.Width;

            return otherLeftEdge > leftEdge && otherRightEdge < rightEdge;
        }

        public override bool isOffScreen()
        {
            return space.CenterX + space.Width / 2 <= 0;
        }

        public override float rightEdge()
        {
            return space.CenterX + space.Width / 2;
        }

        private float leftEdge()
        {
            return space.CenterX + space.Width / 2;
        }
    }
}
