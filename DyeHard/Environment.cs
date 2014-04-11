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
        private const float START_SPEED = 0.6f;
        public static float Speed;
        private float SpeedReference;
        private bool stop;
        private Timer timer;

        private Queue<EnvironmentElement> onscreen;
        private Queue<EnvironmentElement> upcoming;
        private Hero hero;
   
        public Environment(Hero hero)
        {
            this.stop = false;
            this.timer = new Timer(10);

            SpeedReference = START_SPEED;
            Speed = SpeedReference;

            this.hero = hero;
            this.onscreen = new Queue<EnvironmentElement>();
            this.upcoming = new Queue<EnvironmentElement>();

            // first element on screen
            onscreen.Enqueue(new Space(hero, Game.leftEdge()));

            // fill the rest of the exisiting screen
            while (onscreen.Last().rightEdge() <= Game.rightEdge())
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
                timer = new Timer(10);
                SpeedReference *= 1.1f;
                Console.WriteLine("Increasing game speed to " + SpeedReference);
            }
        }

        private void updateSequence()
        {
            if (onscreen.First().isOffScreen())
            {
                // remove off screen element
                onscreen.Dequeue();
                Console.WriteLine("Dumping element from background");
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
                Console.WriteLine("Adding canvas to background");
                return new Space(hero, seq.Last().rightEdge());
            }
            else
            {
                Console.WriteLine("Adding rainbow to background");
                return new Stargate(hero, seq.Last().rightEdge());
            }
        }

    }
}
