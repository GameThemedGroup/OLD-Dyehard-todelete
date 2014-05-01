using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using XNACS1Lib;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Input;

namespace Dyehard
{
    class Environment
    {
        private const float START_SPEED = 0.1f;

        public static float Speed;
        private float SpeedReference;
        private bool stop;
        private Timer timer;

        private Queue<EnvironmentElement> onscreen;
        private Queue<EnvironmentElement> upcoming;
        private Hero hero;
        private EnemyManager eManager;
   
        public Environment(Hero hero)
        {
            this.eManager = new EnemyManager(hero);
            this.stop = false;
            this.timer = new Timer(15);

            SpeedReference = START_SPEED;
            Speed = SpeedReference;

            this.hero = hero;
            this.onscreen = new Queue<EnvironmentElement>();
            this.upcoming = new Queue<EnvironmentElement>();

            // set the enemy manager for the weapon
            hero.setEnemies(eManager.getEnemies());

            // first element on screen
            this.onscreen.Enqueue(new Space(hero, eManager.getEnemies(), Game.leftEdge()));

            // fill the rest of the exisiting screen
            while (onscreen.Last().rightEdge() <= Game.rightEdge())
            {
                onscreen.Enqueue(nextElement(onscreen));
            }

            // prep upcoming elements
            this.upcoming.Enqueue(nextElement(onscreen));

        }

        public void update()
        {
            checkControl();
            accelerateGame();

            foreach (EnvironmentElement e in onscreen)
            {
                e.move();
            }

            foreach (EnvironmentElement e in upcoming)
            {
                e.move();
            }

            updateSequence();

            foreach (EnvironmentElement e in onscreen)
            {
                e.interact();
            }

            eManager.update();
        }

        public void draw()
        {
            foreach (EnvironmentElement e in onscreen)
            {
                e.draw();
            }

            foreach (EnvironmentElement e in upcoming)
            {
                e.draw();
            }
            eManager.draw();
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

            if (onscreen.Last().rightEdge() <= Game.rightEdge())
            {
                // move item from upcoming to end of onscreen
                upcoming.Enqueue(nextElement(upcoming));
                onscreen.Enqueue(upcoming.Dequeue());
            }
        }

        private EnvironmentElement nextElement(Queue<EnvironmentElement> seq)
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

    }
}
