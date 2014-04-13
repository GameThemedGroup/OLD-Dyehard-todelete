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
        private XNACS1Rectangle gate;
        private XNACS1Rectangle gateway;
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
            
            this.gate = new XNACS1Rectangle(new Vector2(position, drawOffset), Stargate.width, drawHeight - (Platform.height * 2));
            this.gate.Color = new Color(color, 100);

            this.gateway = new XNACS1Rectangle(new Vector2(leftEdge + 1.5f, gate.CenterY), 3f, drawHeight);
            this.gateway.Color = Color.Maroon;

            this.preview = new XNACS1Rectangle(new Vector2(Game.rightEdge(), drawOffset), 4f, 0f);
            this.preview.Color = this.gate.Color;
            this.preview.Visible = false;
        }

        ~Gate()
        {
            gate.RemoveFromAutoDrawSet();
            gateway.RemoveFromAutoDrawSet();
            preview.RemoveFromAutoDrawSet();
        }

        public void move()
        {
            gate.CenterX -= Environment.Speed;
            gateway.CenterX -= Environment.Speed;
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
            gateway.TopOfAutoDrawSet();
        }

        public void interact()
        {
            gateway.Visible = hero.getColor() != color;
            if (gateway.Visible)
            {
                if (gateway.Collided(hero.getBox()))
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
