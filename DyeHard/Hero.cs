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
        private const float rightBoundaryLimit = 0.85f; // percentage of screen

        private Weapon weapon;
        private List<Obstacle> boundaries;
        private float gravityFactor;
        private List<Weapon> weaponRack;

        public Hero()
            : base(new Vector2(GameWorld.rightEdge / 3, GameWorld.topEdge / 2), 5f, 5f)
        {
            base.setLabel("Dye");
            boundaries = new List<Obstacle>();

            weaponRack = new List<Weapon>();
            createWeapons();

            // set initial weapon to first
            weapon = weaponRack.First();

            gravityFactor = 1f;
            setBoundaries();
        }

        public override void update()
        {

            // restrict the hero's movement to the boundary
            foreach (Obstacle b in boundaries)
            {
                b.checkCollisions();
            }

            // update base character object (collisions, etc.)
            base.update();


            // update hero's weapons
            selectWeapon();

            if (KeyboardDevice.isKeyTapped(Microsoft.Xna.Framework.Input.Keys.F))
            {
                weapon.fire();
            }

            foreach (Weapon w in weaponRack)
            {
                w.update();
            }
        }


        public void gotDyed()
        {
            if (weapon.GetType() == typeof(LimitedAmmoWeapon))
            {
                ((LimitedAmmoWeapon)weapon).recharge();
            }
        }

        public override void draw()
        {
            weapon.draw();
            base.draw();
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
            position.Velocity = (position.Velocity + direction + (GameWorld.Gravity * gravityFactor)) * drag;

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
            foreach (Weapon w in weaponRack)
            {
                w.setEnemies(enemies);
            }
        }

        private void setBoundaries()
        {
            // determine the maximum horizontal boundary for the hero
            List<Enemy> emptyList = new List<Enemy>();
            float width = (1 - rightBoundaryLimit) * GameWorld.rightEdge;
            float boundaryX = GameWorld.rightEdge - (width / 2);
            float boundaryY = GameWorld.topEdge / 2;
            Obstacle boundary = new Obstacle(this, emptyList, new Vector2(boundaryX, boundaryY), width, GameWorld.topEdge);
            boundaries.Add(boundary);

            // determine minumum vertical and horizontal, and maximum vertical boundaries for hero
            float screenCenterX = (GameWorld.rightEdge - GameWorld.leftEdge) / 2;
            float screenCenterY = (GameWorld.topEdge - GameWorld.bottomEdge) / 2;
            boundary = new Obstacle(this, emptyList, new Vector2(screenCenterX, GameWorld.bottomEdge - screenCenterY), screenCenterX * 2, screenCenterY * 2);
            boundaries.Add(boundary);
            boundary = new Obstacle(this, emptyList, new Vector2(screenCenterX, GameWorld.topEdge + screenCenterY), screenCenterX * 2, screenCenterY * 2);
            boundaries.Add(boundary);
            boundary = new Obstacle(this, emptyList, new Vector2(GameWorld.leftEdge - screenCenterX, screenCenterY), screenCenterX * 2, screenCenterY * 2);
            boundaries.Add(boundary);
        }
     
        // functions for powerup interaction
        public void lowerGravity()
        {
            gravityFactor = 0.2f;
        }

        public void normalizeGravity()
        {
            gravityFactor = 1f;
        }


        private void selectWeapon()
        {
            if (KeyboardDevice.isKeyTapped(Microsoft.Xna.Framework.Input.Keys.D1))
            {
                weapon = weaponRack[0];
            }
            if (KeyboardDevice.isKeyTapped(Microsoft.Xna.Framework.Input.Keys.D2))
            {
                weapon = weaponRack[1];
            }
            if (KeyboardDevice.isKeyTapped(Microsoft.Xna.Framework.Input.Keys.D3))
            {
                weapon = weaponRack[2];
            }
            if (KeyboardDevice.isKeyTapped(Microsoft.Xna.Framework.Input.Keys.D4))
            {
                weapon = weaponRack[3];
            }
        }

        private void createWeapons()
        {
            weaponRack.Add(new Weapon(this));
            weaponRack.Add(new OverHeatWeapon(this));
            weaponRack.Add(new LimitedAmmoWeapon(this));
            weaponRack.Add(new SpreadFireWeapon(this));
        }

    }
}
