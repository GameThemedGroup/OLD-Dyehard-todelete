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
        // game objects
        private Hero hero;
        private DistanceTracker distanceTracker;
        private Background background;

        // control objects
        private PauseScreen pauseScreen;

        public Game()
        {
        }

        protected override void InitializeWorld()
        {
            World.SetWorldCoordinate(new Vector2(0f, 0f), 100f);

            hero = new Hero();
            background = new Background(hero);
            distanceTracker = new DistanceTracker(hero);
            pauseScreen = new PauseScreen();
        }

        
        protected override void UpdateWorld()
        {
            checkControl();

            if (pauseScreen.isActive())
            {
                // do nothing
            }
            else if (hero.isAlive())
            {
                background.update();
                hero.update();
                distanceTracker.update();
            }
            else
            {
                // reset the world
                InitializeWorld();
            }
        }

        private void checkControl()
        {
            KeyboardDevice.update();

            if (KeyboardDevice.isKeyDown(Keys.Escape))
            {
                // allow user exit game
                Exit();
            }

            if (KeyboardDevice.isKeyTapped(Keys.Space))
            {
                // allow user to pause game
                pauseScreen.toggle();
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

        // get a random and unique subset of the available colors
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
    }
}
