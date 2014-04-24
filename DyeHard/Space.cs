using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using XNACS1Lib;
using Microsoft.Xna.Framework;

namespace Dyehard
{
    class Space : EnvironmentElement
    {
        public static float width = Game.rightEdge() * 4f;
        public static int powerupCount = 6;
        private XNACS1Rectangle space;
        private Hero hero;
        private List<PowerUp> powerups;
        private Debris debris1;
        private Debris debris2;
        private Trail trail;

        public Space(Hero hero, List<Enemy> enemies, float leftEdge) : base()
        {
            this.hero = hero;

            this.powerups = new List<PowerUp>();
            this.trail = new Trail(hero);

            float height = Game.topEdge();
            float position = (width * 0.5f) + leftEdge;

            this.space = new XNACS1Rectangle(new Vector2(position, height/2), width, height);
            this.space.Visible = false;

            // add powerups to space region
            List<Color> colors = Game.randomColorSet(powerupCount);
            float rightEdge = space.CenterX + space.Width / 2;
            float region = (rightEdge - leftEdge) / powerupCount;
            for (int i = 0; i < powerupCount; i++)
            {
                float regionLeft = leftEdge + (i * region);
                float regionRight = regionLeft + region;
                powerups.Add(new PowerUp(hero, regionLeft, regionRight, colors[i]));
            }

            this.debris1 = new Debris(hero, enemies, leftEdge, rightEdge);
            this.debris2 = new Debris(hero, enemies, leftEdge, rightEdge);
        }

        ~Space()
        {
            space.RemoveFromAutoDrawSet();
        }

        public override void move()
        {
            space.CenterX -= Environment.Speed;

            trail.move();

            foreach (PowerUp p in powerups)
            {
                p.move();
            }

            debris1.move();
            debris2.move();
        }

        public override void draw()
        {
            space.TopOfAutoDrawSet();

            trail.draw();

            debris1.draw();
            debris2.draw();

            foreach (PowerUp p in powerups)
            {
                p.draw();
            }

        }

        public override void interact()
        {
            foreach (PowerUp p in powerups)
            {
                p.interact();
            }

            if (contains(hero.getPosition()))
            {
                trail.interact();
            }

            debris1.interact();
            debris2.interact();
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
