﻿using System;
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
        private List<Enemy> enemies;

        public Gate(int offset, Hero hero, List<Enemy> enemies, float leftEdge, Color color)
        {
            this.hero = hero;
            this.enemies = enemies;
            this.color = color;

            // set up pipe
            float position = (Stargate.width * 0.5f) + leftEdge;

            float drawHeight =  Game.topEdge() / Stargate.GATE_COUNT;
            float drawOffset = drawHeight * (offset + 0.5f);
            
            this.path = new XNACS1Rectangle(new Vector2(position, drawOffset), Stargate.width, drawHeight - (Platform.height * 2));
            this.path.Color = new Color(color, 100);

            this.gate = new XNACS1Rectangle(new Vector2(leftEdge + 0.25f, path.CenterY), 0.5f, path.Height);
            this.gate.Color = Color.Maroon;
            gate.Visible = false;

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

            if (hero.getColor() != color && gate.Collided(hero.getPosition()))
            {
                hero.kill();
            }

            foreach (Enemy e in enemies)
            {
                if (e.getColor() != color && gate.Collided(e.getPosition()))
                {
                    e.kill();
                }
            }

            if (contains(hero.getPosition()))
            {
                hero.setColor(color);
            }

            foreach (Enemy e in enemies)
            {
                if (contains(e.getPosition()))
                {
                    e.setColor(color);
                }
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
