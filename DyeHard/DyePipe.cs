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

        public DyePipe(int offset, Hero hero)
        {
            this.hero = hero;

            // set up pipe
            float drawLength = XNACS1Base.World.WorldMax.X;
            float offScreen = (drawLength * 0.5f) + XNACS1Base.World.WorldMax.X;

            float drawWidth =  XNACS1Base.World.WorldMax.Y / Rainbow.PIPE_COUNT;
            float drawOffset = drawWidth * (offset + 0.5f);
            
            this.box = new XNACS1Rectangle(new Vector2(offScreen, drawOffset), drawLength, drawWidth);
            this.box.Color = Game.randomColor();
        }

        public void move()
        {
            box.CenterX += Game.Speed;
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

        public bool rightEdgeIsOnScreen()
        {
            float rightEdge = XNACS1Base.World.WorldMax.X;
            return box.CenterX + box.Width / 2 <= rightEdge;
        }
    }
}
