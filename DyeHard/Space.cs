using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using XNACS1Lib;
using Microsoft.Xna.Framework;

namespace Dyehard
{
    class Space : Environment
    {
        public static float width = Game.rightEdge() * 3f;
        public static int powerupCount = 13;
        public static int debrisCount = 18;

        private XNACS1Rectangle space;
        private Hero hero;
        private List<PowerUp> powerups;
        private List<Debris> debris;
        private Trail trail;

        public Space(Hero hero, List<Enemy> enemies, float leftEdge) : base()
        {
            this.hero = hero;

            this.powerups = new List<PowerUp>();
            this.debris = new List<Debris>();
            this.trail = new Trail(hero);

            float height = Game.topEdge();
            float position = (width * 0.5f) + leftEdge;

            this.space = new XNACS1Rectangle(new Vector2(position, height/2), width, height);
            this.space.Visible = false;

            // add powerups to space region
            List<Color> colors = GameWorld.randomColorSet(GameWorld.colorCount);
            float rightEdge = space.CenterX + space.Width / 2;
            float region = (rightEdge - leftEdge) / powerupCount;
            for (int i = 0; i < powerupCount; i++)
            {
                float regionLeft = leftEdge + (i * region);
                float regionRight = regionLeft + region;
                this.powerups.Add(new PowerUp(hero, regionLeft, regionRight, colors[i % GameWorld.colorCount]));
            }

            // offset the region to pad the space before the next element
            // this makes the region slightly smaller than it actually should be otherwise
            int offset = 1;
            region = (rightEdge - leftEdge) / (debrisCount + offset);
            for (int i = 0; i < debrisCount; i++) {
                float regionLeft = leftEdge + (i * region);
                float regionRight = regionLeft + region;
                this.debris.Add(new Debris(hero, enemies, regionLeft, regionRight));
            }
        }

        ~Space()
        {
            space.RemoveFromAutoDrawSet();
        }

        public override void move()
        {
            space.CenterX -= GameWorld.Speed;

            trail.move();

            foreach (PowerUp p in powerups)
            {
                p.move();
            }

            foreach (Debris d in debris)
            {
                d.move();
            }
        }

        public override void draw()
        {
            space.TopOfAutoDrawSet();

            trail.draw();

            foreach (Debris d in debris)
            {
                d.draw();
            }

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

            foreach (Debris d in debris)
            {
                d.interact();
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
