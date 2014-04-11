using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using XNACS1Lib;
using Microsoft.Xna.Framework;

namespace Dyehard
{
    class Hero
    {
        private CharacterBlock box;
        private Weapon weapon;
        private List<PowerUp> powerups;
        private bool alive;

        public Hero()
        {
            this.powerups = new List<PowerUp>();
            this.alive = true;
            this.box = new CharacterBlock(new Vector2(Game.rightEdge() / 3, Game.topEdge() / 2), 5f, 5f);
            this.box.Label = "hero";
            this.box.Color = Game.randomColor();

            this.weapon = new Weapon(this);
        }

        ~Hero()
        {
            box.RemoveFromAutoDrawSet();
        }

        public XNACS1Rectangle getBox()
        {
            return box;
        }

        public void setColor(Color color)
        {
            box.Color = color;
        }

        public Color getColor()
        {
            return box.Color;
        }

        public bool isAlive()
        {
            return alive;
        }

        public void kill()
        {
            alive = false;
        }

        public void update()
        {
            box.push(XNACS1Base.GamePad.ThumbSticks.Right);
            XNACS1Base.World.ClampAtWorldBound(box);
            weapon.update();

            foreach (PowerUp p in powerups)
            {
                p.update();
            }
            powerups.RemoveAll(p => p.expired());

        }

        public void redraw()
        {
            weapon.redraw();
            box.TopOfAutoDrawSet();
        }

        public void collect(PowerUp p)
        {
            Console.WriteLine("hero picked up powerup");
            powerups.Add(p);
        }
    }
}
