using System;
using System.Linq;
using System.Collections.Generic;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Content;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework.Input;
using Microsoft.Xna.Framework.Storage;
using Microsoft.Xna.Framework.GamerServices;
using XNACS1Lib;

namespace Dyehard
{
    public class Game : XNACS1Base
    {
        private bool paused;
        private bool debugging;

        // game objects
        private Hero hero;
        private DistanceTracker heroDistance;
        private Background background;
        private PauseScreen pauseScreen;
        

        public Game()
        {
            paused = false;
            debugging = false;
        }

        protected override void InitializeWorld()
        {
            World.SetWorldCoordinate(new Vector2(0f, 0f), 100f);

            hero = new Hero();
            background = new Background(hero);
            heroDistance = new DistanceTracker(hero);
            pauseScreen = new PauseScreen();
        }

        
        protected override void UpdateWorld()
        {
            checkGameControl();
            if (paused)
            {
                pauseScreen.show();
            } 
            else if (hero.isAlive())
            {
                pauseScreen.hide();
                updateGameObjects();
            }
            else
            {
                InitializeWorld();
            }
        }

        private void checkGameControl()
        {
            KeyboardDevice.update();
            if (KeyboardDevice.isKeyDown(Keys.Escape))
            {
                Exit();
            }

            // pause game speed
            if (KeyboardDevice.isKeyTapped(Keys.W))
            {
                debugging = !debugging;
                if (debugging)
                {
                    Console.WriteLine("Entering debug mode - press 'W' to resume game");
                    background.stop();
                }
                else
                {
                    Console.WriteLine("Exiting debug mode");
                    background.resume();
                }
            }

            if (KeyboardDevice.isKeyTapped(Keys.Space))
            {
                paused = !paused;
            }
        }

        private void updateGameObjects()
        {
            // might need to move hero first (since background interacts with hero - hero doesnt interact with background)
            background.update();
            hero.update();
            heroDistance.update();
        }

        public static List<Color> randomColorSet(int count)
        {
            List<int> range = Enumerable.Range(0,7).ToList();
            List<int> sample = new List<int>();
            for (int i = 0; i < count; i++)
            {
                int choice = RandomInt(range.Count);
                sample.Add(range.ElementAt(choice));
                range.RemoveAt(choice);
            }

            List<Color> colors = new List<Color>();

            foreach (int i in sample)
            {
                colors.Add(ColorPicker(i));
            }

            return colors;
        }

        private static Color ColorPicker(int choice)
        {
            switch (choice)
            {
                case 0: return Color.Blue;
                case 1: return Color.Green;
                case 2: return Color.Red;
                case 3: return Color.Yellow;
                case 4: return Color.Purple;
                case 5: return Color.Orange;
                case 6: return Color.Pink;
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
