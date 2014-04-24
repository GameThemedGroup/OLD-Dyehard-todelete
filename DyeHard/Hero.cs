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
        private static float drag = 0.94f;
        private Vector2 gravity = new Vector2(0, -0.03f);
        private Weapon weapon;
        private List<PowerUp> powerups;

        public Hero()
            : base(new Vector2(Game.rightEdge() / 3, Game.topEdge() / 2), 5f, 5f)
        {
            base.setLabel("hero");
            this.powerups = new List<PowerUp>();
            this.weapon = new Weapon(this);
        }

        public override void update()
        {
            // update character
            base.update();
            XNACS1Base.World.ClampAtWorldBound(position);

            // update weapon
            weapon.update();

            // update powerups
            foreach (PowerUp p in powerups)
            {
                p.update();
            }
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
            direction = direction / 10f;

            // add 'jetpack' factor
            if (direction.Y > 0)
            {
                direction.Y *= 1.7f;
            }

            // update velocity
            position.Velocity = (position.Velocity + direction + gravity) * drag;
        }

        public void setEmenyManagerForTheWeaponInYourHand(EnemyManager eManager)
        {
            weapon.setEmenyManager(eManager);
        }
    }
}
