using System;
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
        private Vector2 gravity = new Vector2(0, -0.02f);
        private Weapon weapon;
        private List<PowerUp> powerups;
        private Obstacle boundary;
        private const float boundaryLimit = 0.85f; // percentage of screen

        public Hero()
            : base(new Vector2(Game.rightEdge() / 3, Game.topEdge() / 2), 5f, 5f)
        {
            base.setLabel("Dye");
            this.powerups = new List<PowerUp>();
            this.weapon = new Weapon(this);
            
            // set the maximum boundary for the hero
            float width = (1 - boundaryLimit) * Game.rightEdge();
            float boundaryX = Game.rightEdge() - (width / 2);
            float boundaryY = Game.topEdge() / 2;
            // use an empty enemy list as we don't want to obstruct enemies
            this.boundary = new Obstacle(this, new List<Enemy>(), new Vector2(boundaryX, boundaryY), width, Game.topEdge());
        }

        public override void update()
        {
            // update base character object
            base.update();

            // clamp position and velocity to game boundaries
            if (position.LowerLeft.Y <= Game.bottomEdge())
            {
                position.CenterY = Game.bottomEdge() + (position.Height / 2);
                if (position.VelocityY < 0)
                {
                    position.VelocityY = 0f;
                }
            }
            if ((position.LowerLeft.Y + position.Height) >= Game.topEdge())
            {
                position.CenterY = Game.topEdge() - (position.Height / 2);
                if (position.VelocityY > 0) {
                    position.VelocityY = 0f;
                }
            }
            if (position.LowerLeft.X <= Game.leftEdge())
            {
                position.CenterX = Game.leftEdge() + (position.Width / 2);
                if (position.VelocityX < 0)
                {
                    position.VelocityX = 0f;
                }
            }
            if ((position.LowerLeft.X + position.Width) >= Game.rightEdge())
            {
                position.CenterX = Game.rightEdge() - (position.Width / 2);
                if (position.VelocityX > 0) {
                    position.VelocityX = 0f;
                }
            }

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
            position.Velocity = (position.Velocity + direction + gravity) * drag;

            if (position.VelocityX < 0)
            {
                position.VelocityX = Math.Max(position.VelocityX, -1 * horizontalSpeedLimit);
            }
            else
            {
                position.VelocityX = Math.Min(position.VelocityX, horizontalSpeedLimit);
            }

            // restrict the hero's movement to the boundary
            boundary.interact();
        }

        public void setEnemies(List<Enemy> enemies)
        {
            weapon.setEnemies(enemies);
        }
    }
}
