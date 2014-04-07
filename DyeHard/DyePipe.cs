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
        XNACS1Rectangle pipe;
        XNACS1Rectangle pipePreview;
        Hero hero;

        public DyePipe(int offset, Hero hero, float leftEdge, Color color)
        {
            this.hero = hero;

            // set up pipe
            float drawWidth = Game.rightEdge();
            float position = (drawWidth * 0.5f) + leftEdge;

            float drawHeight =  Game.topEdge() / Rainbow.PIPE_COUNT;
            float drawOffset = drawHeight * (offset + 0.5f);
            
            this.pipe = new XNACS1Rectangle(new Vector2(position, drawOffset), drawWidth, drawHeight);
            this.pipe.Color = color;

            this.pipePreview = new XNACS1Rectangle(new Vector2(Game.rightEdge(), drawOffset), 4f, drawHeight * .9f);
            this.pipePreview.Color = this.pipe.Color;
            this.pipePreview.Visible = false;
        }

        public void move()
        {
            pipe.CenterX -= Background.Speed;
            pipePreview.Visible = pipe.LowerLeft.X > pipePreview.LowerLeft.X && Game.rightEdge() * 2 > pipe.LowerLeft.X ;
        }

        public void interact()
        {
            XNACS1Rectangle heroBox = hero.getBox();
            if (contains(heroBox))
            {
                hero.setColor(pipe.Color);
            }
        }

        private bool contains(XNACS1Rectangle other)
        {
            float topEdge = pipe.MaxBound.Y;
            float bottomEdge = pipe.MinBound.Y;

            if (other.CenterY < topEdge && other.CenterY >= bottomEdge)
            {
                float leftEdge = pipe.LowerLeft.X;
                float rightEdge = leftEdge + pipe.Width;
                return other.CenterX < rightEdge && other.CenterX > leftEdge;
            }

            return false;
        }

        public bool isOffScreen()
        {
            return pipe.CenterX + pipe.Width / 2 <= 0;
        }

        public float rightEdge()
        {
            return pipe.CenterX + pipe.Width / 2;
        }
    }
}
