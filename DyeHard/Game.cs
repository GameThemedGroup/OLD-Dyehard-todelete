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
        private static bool FULLSCREEN = false;
        
        // Dyehard Dye Colors
        public static int colorCount = 6;
        public static Color Green = new Color(38, 153, 70);
        public static Color Red = new Color(193, 24, 30);
        public static Color Yellow = new Color(228, 225, 21);
        public static Color Teal = new Color(90, 184, 186);
        public static Color Pink = new Color(215, 59, 148);
        public static Color Blue = new Color(50, 75, 150);

        private enum State
        {
            BEGIN,
            PAUSED,
            PLAYING,
            DEAD
        };

        // screen objects
        private Background background;
        private Screen startScreen;
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

        private const string controls = "\n\nControls:\n" +
                                        "Move:        arrow keys\n" +
                                        "Fire dye gun:          'F'\n" +
                                        "Pause game:          'A'\n" +
                                        "Quit game:          'ESC'\n" + 
                                        "Stop the world:       'W'";
        
        // constructor
        public Game() {
        }

        // Initialize the game world
        protected override void InitializeWorld()
        {
            SetAppWindowPixelDimension(FULLSCREEN, 1920, 1080);
            World.SetWorldCoordinate(new Vector2(0f, 0f), 100f);

            preloadTexturedObjects();
            
            background = new Background();
            startScreen = new Screen("Press 'A' to begin." + controls);
            pauseScreen = new Window("Paused.\nResume game:    'A'\nRestart game:     'Q'" + controls);
            deathScreen = new Window("YOU HAVE DIED...\n\n'A' to continue.");
            initializeObjects();
        }

        // preload any game objects that have textures
        private static void preloadTexturedObjects()
        {
            // dont save references to any preloaded objects
            Hero preload = new Hero();

            new BrainRobot(new Vector2(-100f, -100f), 0, 0, preload);
            new WhiteRobot(new Vector2(-100f, -100f), 0, 0, preload);
            new BlackRobot(new Vector2(-100f, -100f), 0, 0, preload);

            new PowerUp(preload, -200f, -100f, Blue);
            new PowerUp(preload, -200f, -100f, Green);
            new PowerUp(preload, -200f, -100f, Yellow);
            new PowerUp(preload, -200f, -100f, Red);
            new PowerUp(preload, -200f, -100f, Pink);
            new PowerUp(preload, -200f, -100f, Teal);
        }


        // initialize the objects for a new game
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
                        startScreen.remove();
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

        public static List<Color> randomColorSet(int count)
        {
            // get a random and unique subset of the available colors

            List<int> range = Enumerable.Range(0,colorCount).ToList();
            List<int> sample = new List<int>();

            // set up the indexes in the sample list
            for (int i = 0; i < count; i++)
            {
                int choice = RandomInt(range.Count);
                sample.Add(range.ElementAt(choice));
                range.RemoveAt(choice);
            }

            // get the colors from the indexes in the sample list
            List<Color> colors = new List<Color>();
            foreach (int i in sample)
            {
                colors.Add(colorPicker(i));
            }

            return colors;
        }

        public static Color randomColor()
        {
            // get a single random color
            return colorPicker(RandomInt(6));
        }

        private static Color colorPicker(int choice)
        {
            switch (choice)
            {
                case 0: return Green;
                case 1: return Red;
                case 2: return Yellow;
                case 3: return Teal;
                case 4: return Pink;
                case 5: return Blue;
            }
            return Color.Black;
        }
    }
}
