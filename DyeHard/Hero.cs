﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using XNACS1Lib;
using Microsoft.Xna.Framework;

namespace Dyehard
{
    class Hero : Character
    {
        private const float horizontalSpeedLimit = 0.8f;
        private static float drag = 0.96f;  // smaller number means more reduction
        private Weapon weapon;
        private List<PowerUp> powerups;
        private List<Obstacle> boundaries;
        private const float rightBoundaryLimit = 0.85f; // percentage of screen

        public Hero()
            : base(new Vector2(Game.rightEdge() / 3, Game.topEdge() / 2), 5f, 5f)
        {
            base.setLabel("Dye");
            this.powerups = new List<PowerUp>();
            this.boundaries = new List<Obstacle>();
            this.weapon = new Weapon(this);

            setUpBoundaries();
        }

        private void setUpBoundaries()
        {
            // determine the maximum horizontal boundary for the hero
            List<Enemy> emptyList = new List<Enemy>();
            float width = (1 - rightBoundaryLimit) * Game.rightEdge();
            float boundaryX = Game.rightEdge() - (width / 2);
            float boundaryY = Game.topEdge() / 2;
            Obstacle boundary = new Obstacle(this, emptyList, new Vector2(boundaryX, boundaryY), width, Game.topEdge());
            boundaries.Add(boundary);

            // determine minumum vertical and horizontal, and maximum vertical boundaries for hero
            float screenCenterX = (Game.rightEdge() - Game.leftEdge()) / 2;
            float screenCenterY = (Game.topEdge() - Game.bottomEdge()) / 2;
            boundary = new Obstacle(this, emptyList, new Vector2(screenCenterX, Game.bottomEdge() - screenCenterY), screenCenterX * 2, screenCenterY * 2);
            boundaries.Add(boundary);
            boundary = new Obstacle(this, emptyList, new Vector2(screenCenterX, Game.topEdge() + screenCenterY), screenCenterX * 2, screenCenterY * 2);
            boundaries.Add(boundary);
            boundary = new Obstacle(this, emptyList, new Vector2(Game.leftEdge() - screenCenterX, screenCenterY), screenCenterX * 2, screenCenterY * 2);
            boundaries.Add(boundary);
        }

        public override void update()
        {
            // restrict the hero's movement to the boundary
            foreach (Obstacle b in boundaries)
            {
                b.interact();
            }

            // update base character object (collisions, etc.)
            base.update();

            // update the hero's weapon
            weapon.update();
        }

        public override void draw()
        {
            weapon.draw();
            base.draw();
        }

        public void collect(PowerUp p)
        {
            powerups.Add(p);
        }

        public int getPowerUpCount()
        {
            return powerups.Count;
        }

        public void push(Vector2 direction)
        {
            // scale direction
            direction = direction / 12f;

            // add 'jetpack' factor
            if (direction.Y > 0)
            {
                direction.Y *= 1.7f;
            }

            // update velocity
            position.Velocity = (position.Velocity + direction + GameWorld.Gravity) * drag;

            if (position.VelocityX < 0)
            {
                position.VelocityX = Math.Max(position.VelocityX, -1 * horizontalSpeedLimit);
            }
            else
            {
                position.VelocityX = Math.Min(position.VelocityX, horizontalSpeedLimit);
            }
        }

        public void setEnemies(List<Enemy> enemies)
        {
            weapon.setEnemies(enemies);
        }
    }
}
