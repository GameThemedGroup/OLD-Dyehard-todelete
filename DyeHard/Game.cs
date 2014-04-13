﻿using System;
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
            BEGIN,
            PAUSED,
            PLAYING,
            DEAD
        };

        // screen objects
        private Background background;
        private Window startScreen;
        private Window pauseScreen;
        private Window deathScreen;

        // game objects
        private Player player;
        private Hero hero;
        private DistanceTracker distanceTracker;
        private PowerUpTracker powerupTracker;
        private Environment environment;

        // game state
        private State state;

        protected override void InitializeWorld()
        {
            SetAppWindowPixelDimension(true, 1280, 720);

            World.SetWorldCoordinate(new Vector2(0f, 0f), 100f);
            background = new Background();
            pauseScreen = new Window("Paused.\n\n\n'A' to resume.\n\n'Q' to restart.");
            startScreen = new Window("DYEHARD\n\n'A' to begin.");
            deathScreen = new Window("YOU HAVE DIED...\n\n'A' to continue.");
            initializeObjects();
        }


        private void initializeObjects()
        {
            state = State.BEGIN;
            hero = new Hero();
            player = new Player(hero);
            environment = new Environment(hero);
            distanceTracker = new DistanceTracker(hero);
            powerupTracker = new PowerUpTracker(hero);
        }


        protected override void UpdateWorld()
        {

            checkControl();
            background.update();

            switch (state)
            {
                case State.BEGIN:
                    startScreen.draw();
                    break;

                case State.PAUSED:
                    environment.draw();
                    hero.draw();
                    distanceTracker.draw();
                    powerupTracker.draw();

                    pauseScreen.draw();
                    break;

                case State.PLAYING:
                    player.update();
                    environment.update();
                    hero.update();
                    distanceTracker.update();
                    powerupTracker.update();

                    environment.draw();
                    hero.draw();
                    distanceTracker.draw();
                    powerupTracker.draw();
                    break;

                case State.DEAD:
                    environment.draw();
                    distanceTracker.draw();
                    powerupTracker.draw();

                    deathScreen.draw();
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
                case State.BEGIN:
                    if (KeyboardDevice.isKeyTapped(Keys.A))
                    {
                        state = State.PLAYING;
                    }
                    break;

                case State.PAUSED:
                    if (KeyboardDevice.isKeyTapped(Keys.A))
                    {
                        state = State.PLAYING;
                    }
                    if (KeyboardDevice.isKeyTapped(Keys.Q))
                    {
                        initializeObjects();
                    }
                    break;

                case State.PLAYING:
                    if (KeyboardDevice.isKeyTapped(Keys.A))
                    {
                        state = State.PAUSED;
                    }
                    else if (!hero.isAlive())
                    {
                        state = State.DEAD;
                    }
                    break;

                case State.DEAD:
                    if (KeyboardDevice.isKeyTapped(Keys.A))
                    {
                        initializeObjects();
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
            return colorPicker(RandomInt(6));
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
