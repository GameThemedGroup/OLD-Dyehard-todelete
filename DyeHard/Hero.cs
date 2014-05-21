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
        private int sizeOfWeaponRack = 3;
        private Weapon[] weaponRack;

        public Hero()
            : base(new Vector2(GameWorld.rightEdge / 3, GameWorld.topEdge / 2), 5f, 5f)
        {

            base.setLabel("Dye");
            boundaries = new List<Obstacle>();
            weaponRack = new Weapon[sizeOfWeaponRack];
            weaponRack[0] = new Weapon(this);
            weaponRack[1] = new OverHeatWeapon(this);
            weaponRack[2] = new LimitedBulletWeapon(this);
            weapon = weaponRack[0];


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

            // update the hero's weapon

            //for (int x = 0; x < sizeOfWeaponRack; x++)
          // {
              // weaponRack[x].update();
           // }
                //charles update all weapon
                weapon.update();


            if (KeyboardDevice.isKeyTapped(Microsoft.Xna.Framework.Input.Keys.D1))
            {
                changeWeapon(0);
            }
            if (KeyboardDevice.isKeyTapped(Microsoft.Xna.Framework.Input.Keys.D2))
            {
                changeWeapon(1);
            }
            if (KeyboardDevice.isKeyTapped(Microsoft.Xna.Framework.Input.Keys.D3))
            {
                changeWeapon(2);
            }
        }

        public void gotDyed()
        {
            if (weapon.GetType() == typeof(LimitedBulletWeapon))
            {
                weapon.recharge();
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
           // weapon.setEnemies(enemies);

            for (int x = 0; x < sizeOfWeaponRack; x++)
            {
                weaponRack[x].setEnemies(enemies);
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
     
        public void lowerGravity()
        {
            gravityFactor = 0.2f;
        }

        public void normalizeGravity()
        {
            gravityFactor = 1f;
        }

        public void changeWeapon(int weaponNum)
        {
            if (weaponNum >= 0 && weaponNum < sizeOfWeaponRack)
            {
                weapon = weaponRack[weaponNum];
            }


        }


    }
}
