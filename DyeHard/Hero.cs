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
        private MassObject box;
        private XNACS1Rectangle boxBorder;
        private Weapon weapon;
        private bool alive;

        public Hero()
        {
            this.alive = true;
            this.box = new MassObject(new Vector2(Game.rightEdge() / 3, Game.topEdge() / 2), 5f, 5f);
            this.box.Label = "hero";
            this.box.Color = Color.Green;

            this.boxBorder = new XNACS1Rectangle(this.box.Center, this.box.Width + .4f, this.box.Height + .4f);
            this.boxBorder.Color = Color.Black;

            this.weapon = new Weapon(this);
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
            Vector2 jetpack = new Vector2(XNACS1Base.GamePad.ThumbSticks.Right.X, XNACS1Base.GamePad.ThumbSticks.Right.Y);
            if (jetpack.Y > 0) {
                jetpack.Y *= 2.0f;
            }

            box.push(jetpack);
            XNACS1Base.World.ClampAtWorldBound(box);

            boxBorder.Center = box.Center;

            weapon.update();

            if (KeyboardDevice.isKeyTapped(Microsoft.Xna.Framework.Input.Keys.F))
            {
                weapon.fire();
            }

            boxBorder.TopOfAutoDrawSet();
            box.TopOfAutoDrawSet();
        }
    }
}
