using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using XNACS1Lib;
using Microsoft.Xna.Framework;

namespace Dyehard
{
    class Platform
    {
        public static float height = 1.2f;
        private Hero hero;
        private XNACS1Rectangle box;

        public Platform(int offset, Hero hero, float leftEdge)
        {
            this.hero = hero;

            // set up platform
            float position = (Stargate.width * 0.5f) + leftEdge;
;
            float drawOffset = ((offset * 1f) / Stargate.PIPE_COUNT) * Game.topEdge();
            
            this.box = new XNACS1Rectangle(new Vector2(position, drawOffset), Stargate.width, height);
            this.box.Color = Color.SlateGray;
        }

        ~Platform()
        {
            box.RemoveFromAutoDrawSet();
        }

        public void move()
        {
            box.CenterX -= Environment.Speed;
        }

        public void draw()
        {
            box.TopOfAutoDrawSet();
        }

        public void interact()
        {
            // if hero's velocity will lead the hero to collide with the box...
            // then we need to limit the hero's velocity to the edge of the box.
            XNACS1Rectangle heroBox = hero.getBox();
            if (willCollide(heroBox)) {

                    // by default, push hero up or down
                    if (box.CenterY < heroBox.CenterY)
                    {
                        heroBox.CenterY += (box.MaxBound.Y - heroBox.MinBound.Y);
                        heroBox.VelocityY = 0;
                    }
                    else
                    {
                        heroBox.CenterY += (box.MinBound.Y - heroBox.MaxBound.Y);
                        heroBox.VelocityY = 0;
                    } 
             
            }
        }

        public bool willCollide(XNACS1Rectangle other)
        {
            XNACS1Rectangle nextPosition = new XNACS1Rectangle(other.Center + other.Velocity, other.Width, other.Height);
            nextPosition.Visible = false;
            nextPosition.RemoveFromAutoDrawSet();
            return box.Collided(nextPosition);
        }
    }
}
