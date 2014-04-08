using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using XNACS1Lib;
using Microsoft.Xna.Framework;

namespace Dyehard
{
    class Pipe
    {
        public static float width = Game.rightEdge() * 1.25f;
        private XNACS1Rectangle pipe;
        private XNACS1Rectangle pipeWall;
        private XNACS1Rectangle pipePreview;
        private Hero hero;

        public Pipe(int offset, Hero hero, float leftEdge, Color color)
        {
            this.hero = hero;

            // set up pipe
            float position = (width * 0.5f) + leftEdge;

            float drawHeight =  Game.topEdge() / Rainbow.PIPE_COUNT;
            float drawOffset = drawHeight * (offset + 0.5f);
            
            this.pipe = new XNACS1Rectangle(new Vector2(position, drawOffset), width, drawHeight);
            this.pipe.Color = color;

            this.pipeWall = new XNACS1Rectangle(new Vector2(leftEdge, pipe.CenterY), 1f, pipe.Height);
            this.pipeWall.Color = Color.DarkSlateGray;

            this.pipePreview = new XNACS1Rectangle(new Vector2(Game.rightEdge(), drawOffset), 4f, 0f);
            this.pipePreview.Color = this.pipe.Color;
            this.pipePreview.Visible = false;
        }

        public void move()
        {
            pipe.CenterX -= Background.Speed;
            pipeWall.CenterX -= Background.Speed;
            pipePreview.Visible = pipe.LowerLeft.X > pipePreview.LowerLeft.X && (Game.rightEdge() + Canvas.width) > pipe.LowerLeft.X;
            if (pipePreview.Visible)
            {
                pipePreview.Height = (pipe.Height * (1- ((pipe.LowerLeft.X - pipePreview.LowerLeft.X) / Canvas.width)));
            }
        }

        public void interact()
        {
            pipeWall.Visible = hero.getColor() != pipe.Color;
            if (pipeWall.Visible)
            {
                if (pipeWall.Collided(hero.getBox()))
                {
                    Console.WriteLine("hero died - collided with wall!");
                    hero.kill();
                }
            }

            if (contains(hero.getBox()))
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
