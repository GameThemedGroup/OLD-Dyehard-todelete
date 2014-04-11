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
        private Color color;
        private XNACS1Rectangle gate;
        private XNACS1Rectangle wall;
        private XNACS1Rectangle preview;
        private Hero hero;

        public Gate(int offset, Hero hero, float leftEdge, Color color)
        {
            this.hero = hero;
            this.color = color;

            // set up pipe
            float position = (width * 0.5f) + leftEdge;

            float drawHeight =  Game.topEdge() / Stargate.PIPE_COUNT;
            float drawOffset = drawHeight * (offset + 0.5f);
            
            this.gate = new XNACS1Rectangle(new Vector2(position, drawOffset), width, drawHeight);
            this.gate.Color = new Color(color, 100);

            this.wall = new XNACS1Rectangle(new Vector2(leftEdge + 1, gate.CenterY), 2, gate.Height);
            this.wall.Color = new Color(Color.White, 10);

            this.preview = new XNACS1Rectangle(new Vector2(Game.rightEdge(), drawOffset), 4f, 0f);
            this.preview.Color = this.gate.Color;
            this.preview.Visible = false;
        }

        ~Gate()
        {
            gate.RemoveFromAutoDrawSet();
            wall.RemoveFromAutoDrawSet();
            preview.RemoveFromAutoDrawSet();
        }

        public void move()
        {
            gate.CenterX -= Environment.Speed;
            wall.CenterX -= Environment.Speed;
            preview.Visible = gate.LowerLeft.X > (preview.LowerLeft.X + preview.Width) && (Game.rightEdge() + (Space.width * 0.75f)) > gate.LowerLeft.X;
            if (preview.Visible)
            {
                preview.Height = (gate.Height * (1 - ((gate.LowerLeft.X - (preview.LowerLeft.X + preview.Width)) / (Space.width * 0.75f))));
            }
        }

        public void draw()
        {
            preview.TopOfAutoDrawSet();
            gate.TopOfAutoDrawSet();
            wall.TopOfAutoDrawSet();
        }

        public void interact()
        {
            wall.Visible = hero.getColor() != color;
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
                hero.setColor(color);
            }
        }

        private bool contains(XNACS1Rectangle other)
        {
            float topEdge = gate.MaxBound.Y;
            float bottomEdge = gate.MinBound.Y;

            if (other.CenterY < topEdge && other.CenterY >= bottomEdge)
            {
                float leftEdge = gate.LowerLeft.X;
                float rightEdge = leftEdge + gate.Width;
                return other.CenterX < rightEdge && other.CenterX > leftEdge;
            }

            return false;
        }

        public bool isOffScreen()
        {
            return gate.CenterX + gate.Width / 2 <= 0;
        }

        public float rightEdge()
        {
            return gate.CenterX + gate.Width / 2;
        }
    }
}
