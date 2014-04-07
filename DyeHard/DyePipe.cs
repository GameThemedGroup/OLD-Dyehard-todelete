using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using XNACS1Lib;
using Microsoft.Xna.Framework;

namespace DyeHard
{
    class DyePipe
    {
        XNACS1Rectangle box;
        Hero hero;

        public DyePipe(int offset, Hero hero, float leftEdge)
        {
            this.hero = hero;

            // set up pipe
            float drawLength = Game.rightEdge();
            float position = (drawLength * 0.5f) + leftEdge;

            float drawWidth =  Game.topEdge() / Rainbow.PIPE_COUNT;
            float drawOffset = drawWidth * (offset + 0.5f);
            
            this.box = new XNACS1Rectangle(new Vector2(position, drawOffset), drawLength, drawWidth);
            this.box.Color = Game.randomColor();
        }

        public void move()
        {
            box.CenterX -= Game.Speed;
        }

        public void interact()
        {
            XNACS1Rectangle heroBox = hero.getBox();
            if (contains(heroBox))
            {
                heroBox.Color = box.Color;
            }
        }

        private bool contains(XNACS1Rectangle other)
        {
            float topEdge = box.MaxBound.Y;
            float bottomEdge = box.MinBound.Y;

            if (other.CenterY < topEdge && other.CenterY > bottomEdge)
            {
                float leftEdge = box.LowerLeft.X;
                float rightEdge = leftEdge + box.Width;
                return other.CenterX < rightEdge && other.CenterX > leftEdge;
            }

            return false;
        }

        public bool isOffScreen()
        {
            return box.CenterX + box.Width / 2 <= 0;
        }

        public float rightEdge()
        {
            return box.CenterX + box.Width / 2;
        }
    }
}
