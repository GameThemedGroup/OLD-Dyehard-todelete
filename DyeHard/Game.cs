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
        private static bool FULLSCREEN = true;



        public static float panelSize = 4f;

        private enum State
        {
            BEGIN,
            PAUSED,
            PLAYING,
            GAMEOVER
        };

        // screen objects
        private Background background;
        private Screen startScreen;
        private Window pauseScreen;
        private Window gameOverScreen;

        // game objects
        private GameWorld world;

        // game state
        private State state;
        
        // constructor
        public Game() {
        }

        // Initialize the game world
        protected override void InitializeWorld()
        {
            SetAppWindowPixelDimension(FULLSCREEN, 1920, 1080);
            World.SetWorldCoordinate(new Vector2(0f, 0f), 100f);
            preloadTexturedObjects();
            loadControllerObjects();

            state = State.BEGIN;
            world = new GameWorld();
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
                    world.draw();
                    pauseScreen.draw();
                    break;

                case State.PLAYING:
                    world.update();
                    world.draw();

                    break;

                case State.GAMEOVER:
                    world.draw();
                    gameOverScreen.draw();
                    break;
            }
        }

        private void checkControl()
        {
            KeyboardDevice.update();

            if (KeyboardDevice.isKeyDown(Keys.Escape))
            {
                Exit();
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
                        pauseScreen.remove();
                    }
                    if (KeyboardDevice.isKeyTapped(Keys.Q))
                    {
                        state = State.BEGIN;
                        world = new GameWorld();
                        pauseScreen.remove();
                    }
                    break;

                case State.PLAYING:
                    if (KeyboardDevice.isKeyTapped(Keys.A))
                    {
                        state = State.PAUSED;
                    }
                    else if (world.gameOver())
                    {
                        state = State.GAMEOVER;
                    }
                    break;

                case State.GAMEOVER:
                    if (KeyboardDevice.isKeyTapped(Keys.A))
                    {
                        state = State.BEGIN;
                        world = new GameWorld();
                        gameOverScreen.remove();
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
            return World.WorldMax.Y - panelSize;
        }

        public static float bottomEdge()
        {
            return World.WorldMin.Y;
        }


        // game text
        private const string startText = "Press 'A' to begin.";
        private const string pauseText = "Paused.\nResume game:    'A'\nRestart game:     'Q'";
        private const string deathText = "YOU HAVE DIED...\n\n'A' to continue.";
        private const string controls = "\n\nControls:\n" +
                                        "Move:        arrow keys\n" +
                                        "Fire dye gun:          'F'\n" +
                                        "Pause game:          'A'\n" +
                                        "Quit game:          'ESC'\n" +
                                        "Stop the world:       'W'";


        // preload any game objects that have textures
        private static void preloadTexturedObjects()
        {
            // dont save references to any preloaded objects
            Hero preloadHero = new Hero();

            Enemy preloadEnemy = new BrainRobot(new Vector2(-100f, -100f), 0, 0, preloadHero);
            new WhiteRobot(new Vector2(-100f, -100f), 0, 0, preloadHero);
            new BlackRobot(new Vector2(-100f, -100f), 0, 0, preloadHero);

            new PowerUp(preloadHero, -200f, -100f, GameWorld.Blue);
            new PowerUp(preloadHero, -200f, -100f, GameWorld.Green);
            new PowerUp(preloadHero, -200f, -100f, GameWorld.Yellow);
            new PowerUp(preloadHero, -200f, -100f, GameWorld.Red);
            new PowerUp(preloadHero, -200f, -100f, GameWorld.Pink);
            new PowerUp(preloadHero, -200f, -100f, GameWorld.Teal);

            new PowerUpMeter(0);

            new Explosion(preloadHero, preloadEnemy);
        }

        private void loadControllerObjects()
        {
            background = new Background();
            startScreen = new Screen(startText + controls);
            pauseScreen = new Window(pauseText + controls);
            gameOverScreen = new Window(deathText);
        }

    }
}
