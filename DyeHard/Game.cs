﻿#region Using Statements
using System;
using System.Collections.Generic;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Content;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework.Input;
using Microsoft.Xna.Framework.Storage;
using Microsoft.Xna.Framework.GamerServices;
#endregion

using XNACS1Lib;

namespace DyeHard
{
    public class Game : XNACS1Base
    {

        public static float Speed;
        private static float SpeedReference = 0.4f;
        private static float SpeedAccumulator = 0f;

        // game objects
        Hero hero;
        DistanceTracker heroDistance;
        Background background;
        

        public Game()
        {
            Speed = SpeedReference;
        }

        protected override void InitializeWorld()
        {
            World.SetWorldCoordinate(new Vector2(0f, 0f), 100f);

            hero = new Hero();
            background = new Background(hero);
            heroDistance = new DistanceTracker(hero);
        }

        
        protected override void UpdateWorld()
        {
            checkGameControl();
            updateGameObjects();
        }

        private void checkGameControl()
        {
            if (Keyboard.GetState().IsKeyDown(Keys.Escape))
            {
                Exit();
            }

            // pause game speed
            if (Keyboard.GetState().IsKeyDown(Keys.Space))
            {
                Speed = 0f;
            }
            else
            {
                Speed = SpeedReference;
            }
        }

        private void updateGameObjects()
        {
            accelerateGame();
            background.update();
            hero.update();
            heroDistance.update();
        }

        private static void accelerateGame()
        {
            SpeedAccumulator += Speed;
            if (SpeedAccumulator > 500)
            {
                SpeedReference *= 1.1f;
                SpeedAccumulator = 0f;
                Console.WriteLine("Increasing game speed to " + SpeedReference);
            }
        }

        public static Color randomColor()
        {
            switch (RandomInt(10))
            {
                case 0: return Color.Blue;
                case 1: return Color.Green;
                case 2: return Color.Red;
                case 3: return Color.Yellow;
                case 4: return Color.Purple;
                case 5: return Color.Orange;
                case 6: return Color.Navy;
                case 7: return Color.LimeGreen;
                case 8: return Color.Gray;
                case 9: return Color.Pink;
                default: return Color.Black;
            }
        }

        public static float rightEdge()
        {
            return World.WorldMax.X;
        }

        public static float leftEdge()
        {
            return World.WorldMin.X;
        }

        public static float topEdge()
        {
            return World.WorldMax.Y;
        }

        public static float bottomEdge()
        {
            return World.WorldMin.Y;
        }
    }
}
