using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using XNACS1Lib;
using Microsoft.Xna.Framework;

namespace Dyehard
{
    class Gate
    {
        public static float width = Game.rightEdge() * 1.25f;
        private XNACS1Rectangle pipe;
        private XNACS1Rectangle wall;
        private XNACS1Rectangle preview;
        private Hero hero;

        public Gate(int offset, Hero hero, float leftEdge, Color color)
        {
            this.hero = hero;

            // set up pipe
            float position = (width * 0.5f) + leftEdge;

            float drawHeight =  Game.topEdge() / Stargate.PIPE_COUNT;
            float drawOffset = drawHeight * (offset + 0.5f);
            
            this.pipe = new XNACS1Rectangle(new Vector2(position, drawOffset), width, drawHeight);
            this.pipe.Color = color;

            this.wall = new XNACS1Rectangle(new Vector2(leftEdge, pipe.CenterY), 3.5f, pipe.Height);
            this.wall.Color = new Color(Color.Gray, 100);

            this.preview = new XNACS1Rectangle(new Vector2(Game.rightEdge(), drawOffset), 4f, 0f);
            this.preview.Color = this.pipe.Color;
            this.preview.Visible = false;
        }

        public void move()
        {
            pipe.CenterX -= Background.Speed;
            wall.CenterX -= Background.Speed;
            preview.Visible = pipe.LowerLeft.X > preview.LowerLeft.X && (Game.rightEdge() + Space.width) > pipe.LowerLeft.X;
            if (preview.Visible)
            {
                preview.Height = (pipe.Height * (1- ((pipe.LowerLeft.X - preview.LowerLeft.X) / Space.width)));
            }

            preview.TopOfAutoDrawSet();
            pipe.TopOfAutoDrawSet();
            wall.TopOfAutoDrawSet();
        }

        public void interact()
        {
            wall.Visible = hero.getColor() != pipe.Color;
            if (wall.Visible)
            {
                if (wall.Collided(hero.getBox()))
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
