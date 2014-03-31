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

        public const float SPEED = -0.05f;

        // game objects
        Hero hero;
        Background background;
        

        public Game()
        {
        }

        protected override void InitializeWorld()
        {
            World.SetWorldCoordinate(new Vector2(0f, 0f), 16f);
            hero = new Hero("The Hero");
            background = new Background(hero);
        }

        
        protected override void UpdateWorld()
        {
            if (ButtonState.Pressed == GamePad.Buttons.Back || Keyboard.GetState().IsKeyDown(Keys.Escape))
            {
                Exit();
            }

            background.update();
            hero.update();
            if (GamePad.ButtonAClicked())
                Console.WriteLine("Controller connected!");
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
