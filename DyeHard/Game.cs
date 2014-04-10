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
        private enum State
        {
            START_SCREEN,
            PAUSED,
            RUNNING
        };

        // screen objects
        private StartScreen startScreen;
        private PauseScreen pauseScreen;

        // game objects
        private Hero hero;
        private DistanceTracker distanceTracker;
        private Background background;

        // game state
        private State state;

        public Game()
        {
        }

        protected override void InitializeWorld()
        {
            this.state = State.START_SCREEN;
            World.SetWorldCoordinate(new Vector2(0f, 0f), 100f);

            hero = new Hero();
            background = new Background(hero);
            distanceTracker = new DistanceTracker(hero);
            pauseScreen = new PauseScreen();
            startScreen = new StartScreen();
        }


        protected override void UpdateWorld()
        {
            checkControl();

            switch (state)
            {
                case State.START_SCREEN:
                    startScreen.update();
                    break;

                case State.PAUSED:
                    pauseScreen.update();
                    break;

                case State.RUNNING:
                    hero.update();
                    background.update();
                    hero.redraw();
                    distanceTracker.update();
                    break;
            }
        }

        private void checkControl()
        {
            KeyboardDevice.update();

            if (KeyboardDevice.isKeyDown(Keys.Escape))
            {
                Exit(); // allow user exit game
            }

            switch (state)
            {
                case State.START_SCREEN:
                    //
                    if (KeyboardDevice.isKeyTapped(Keys.A))
                    {
                        state = State.RUNNING;
                    }
                    break;

                case State.PAUSED:
                    if (KeyboardDevice.isKeyTapped(Keys.Space))
                    {
                        state = State.RUNNING;
                    }
                    break;
                case State.RUNNING:
                    if (KeyboardDevice.isKeyTapped(Keys.Space))
                    {
                        state = State.PAUSED;
                    }
                    else if (!hero.isAlive())
                    {
                        InitializeWorld();
                    }
                    break;
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
            List<int> range = Enumerable.Range(0,6).ToList();
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
                colors.Add(colorPicker(i));
            }

            return colors;
        }

        public static Color randomColor()
        {
            return colorPicker(RandomInt(7));
        }

        private static Color colorPicker(int choice)
        {
            switch (choice)
            {
                case 0: return new Color(50, 75, 150);
                case 1: return Color.Green;
                case 2: return Color.Red;
                case 3: return Color.Yellow;
                case 4: return new Color(90, 184, 186);
                case 5: return new Color(215, 59, 148);
            }
            return Color.Black;
        }
    }
}
