#region Using Statements
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
        private static float SpeedReference = -0.05f;
        private static float SpeedAccumulator = 0f;

        // game objects
        Hero hero;
        Background background;
        

        public Game()
        {
        }

        protected override void InitializeWorld()
        {
            World.SetWorldCoordinate(new Vector2(0f, 0f), 16f);
            hero = new Hero("Hero!");
            background = new Background(hero);
            Speed = SpeedReference;
        }

        
        protected override void UpdateWorld()
        {
            checkGameControl();
            updateGameObjects();
        }

        private void updateGameObjects()
        {
            // accelerate game
            SpeedAccumulator -= Speed;
            if (SpeedAccumulator > 50)
            {
                SpeedReference -= 0.01f;
                SpeedAccumulator = 0f;
            }
 
            // update objects
            background.update();
            hero.update();
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
    }
}
