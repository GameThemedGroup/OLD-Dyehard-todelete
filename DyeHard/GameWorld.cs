using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using XNACS1Lib;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Input;

namespace Dyehard
{
    class GameWorld
    {
        public static readonly float panelSize = 4f;
        public static readonly float rightEdge = XNACS1Base.World.WorldMax.X;
        public static readonly float leftEdge = XNACS1Base.World.WorldMin.X;
        public static readonly float topEdge = XNACS1Base.World.WorldMax.Y - panelSize;
        public static readonly float bottomEdge = XNACS1Base.World.WorldMin.Y;

        private const float StartSpeed = 0.2f;

        public static float Speed;
        public static Vector2 Gravity = new Vector2(0, -0.02f);

        private float SpeedReference;
        private bool stop;
        private Timer timer;

        private Queue<Environment> onscreen;
        private Queue<Environment> upcoming;
        private Hero hero;
        private EnemyManager eManager;
        private Player player;
        private InfoPanel infoPanel;
   
        public GameWorld()
        {
            hero = new Hero();
            player = new Player(hero);
            infoPanel = new InfoPanel(hero);
            eManager = new EnemyManager(hero);
            stop = false;
            timer = new Timer(15);

            SpeedReference = StartSpeed;
            Speed = SpeedReference;

            
            onscreen = new Queue<Environment>();
            upcoming = new Queue<Environment>();

            // set the enemy manager for the weapon
            hero.setEnemies(eManager.getEnemies());

            // first element on screen
            onscreen.Enqueue(new Space(hero, eManager.getEnemies(), leftEdge));

            // fill the rest of the exisiting screen
            while (onscreen.Last().rightEdge() <= rightEdge)
            {
                onscreen.Enqueue(nextElement(onscreen));
            }

            // prep upcoming elements
            upcoming.Enqueue(nextElement(onscreen));

        }

        public void update()
        {
            checkControl();
            accelerateGame();

            player.update();

            foreach (Environment e in onscreen)
            {
                e.move();
            }

            foreach (Environment e in upcoming)
            {
                e.move();
            }

            updateSequence();

            foreach (Environment e in onscreen)
            {
                e.interact();
            }

            eManager.update();
            hero.update();

            infoPanel.update();
        }

        public void draw()
        {
            foreach (Environment e in onscreen)
            {
                e.draw();
            }

            foreach (Environment e in upcoming)
            {
                e.draw();
            }
            eManager.draw();

            hero.draw();
            infoPanel.draw();
        }

        private void checkControl()
        {
            // pause game speed
            if (KeyboardDevice.isKeyTapped(Keys.W))
            {
                stop = !stop;
                if (stop)
                {
                    Console.WriteLine("Entering debug mode - press 'W' to resume game");
                    Speed = 0f;
                }
                else
                {
                    Console.WriteLine("Exiting debug mode");
                    Speed = SpeedReference;
                }
            }

            if (KeyboardDevice.isKeyTapped(Keys.P))
            {
                eManager.killAll();
            }
        }

        private void accelerateGame()
        {
            if (!stop)
            {
                timer.update();
                Speed = SpeedReference;
            }

            if (timer.isDone())
            {
                timer.reset();
                SpeedReference += 0.01f;
            }
        }

        private void updateSequence()
        {
            if (onscreen.First().isOffScreen())
            {
                // remove off screen element
                onscreen.Dequeue();
            }

            if (onscreen.Last().rightEdge() <= rightEdge)
            {
                // move item from upcoming to end of onscreen
                upcoming.Enqueue(nextElement(upcoming));
                onscreen.Enqueue(upcoming.Dequeue());
            }
        }

        private Environment nextElement(Queue<Environment> seq)
        {
            if (seq.Last().GetType() == typeof(Stargate))
            {
                return new Space(hero, eManager.getEnemies(), seq.Last().rightEdge());
            }
            else
            {
                return new Stargate(hero, eManager.getEnemies(), seq.Last().rightEdge());
            }
        }

        public bool gameOver()
        {
            return !hero.isAlive();
        }
    }
}
