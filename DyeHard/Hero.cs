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
        //private Character character;
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
            XNACS1Base.World.ClampAtWorldBound(currentPosition);

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
    }
}
