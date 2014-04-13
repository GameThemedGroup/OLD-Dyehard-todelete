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
        private Color color;
        private XNACS1Rectangle path;
        private XNACS1Rectangle gate;
        private XNACS1Rectangle preview;
        private Hero hero;

        public Gate(int offset, Hero hero, float leftEdge, Color color)
        {
            this.hero = hero;
            this.color = color;

            // set up pipe
            float position = (Stargate.width * 0.5f) + leftEdge;

            float drawHeight =  Game.topEdge() / Stargate.PIPE_COUNT;
            float drawOffset = drawHeight * (offset + 0.5f);
            
            this.path = new XNACS1Rectangle(new Vector2(position, drawOffset), Stargate.width, drawHeight - (Platform.height * 2));
            this.path.Color = new Color(color, 100);

            this.gate = new XNACS1Rectangle(new Vector2(leftEdge + 1.5f, path.CenterY), 3f, path.Height);
            this.gate.Color = Color.Maroon;

            this.preview = new XNACS1Rectangle(new Vector2(Game.rightEdge(), drawOffset), 4f, 0f);
            this.preview.Color = this.path.Color;
            this.preview.Visible = false;
        }

        ~Gate()
        {
            path.RemoveFromAutoDrawSet();
            gate.RemoveFromAutoDrawSet();
            preview.RemoveFromAutoDrawSet();
        }

        public void move()
        {
            path.CenterX -= Environment.Speed;
            gate.CenterX -= Environment.Speed;
            preview.Visible = path.LowerLeft.X > (preview.LowerLeft.X + preview.Width) && (Game.rightEdge() + (Space.width * 0.7f)) > path.LowerLeft.X;
            if (preview.Visible)
            {
                preview.Height = ((path.Height + (Platform.height * 2)) * (1 - ((path.LowerLeft.X - (preview.LowerLeft.X + preview.Width)) / (Space.width * 0.7f))));
            }
        }

        public void draw()
        {
            preview.TopOfAutoDrawSet();
            path.TopOfAutoDrawSet();
            gate.TopOfAutoDrawSet();
        }

        public void interact()
        {
            gate.Visible = hero.getColor() != color;
            if (gate.Visible)
            {
                if (gate.Collided(hero.getPosition()))
                {
                    Console.WriteLine("hero died - collided with wall!");
                    hero.kill();
                }
            }

            if (contains(hero.getPosition()))
            {
                hero.setColor(color);
            }
        }

        private bool contains(XNACS1Rectangle other)
        {
            float topEdge = path.MaxBound.Y;
            float bottomEdge = path.MinBound.Y;

            if (other.CenterY < topEdge && other.CenterY >= bottomEdge)
            {
                float leftEdge = path.LowerLeft.X;
                float rightEdge = leftEdge + path.Width;
                return other.CenterX < rightEdge && other.CenterX > leftEdge;
            }

            return false;
        }

        public bool isOffScreen()
        {
            return path.CenterX + path.Width / 2 <= 0;
        }

        public float rightEdge()
        {
            return path.CenterX + path.Width / 2;
        }
    }
}
