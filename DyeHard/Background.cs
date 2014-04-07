using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using XNACS1Lib;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Input;

namespace DyeHard
{
    class Background
    {

        public static float Speed;
        private static float SpeedReference = 0.5f;
        private static float SpeedAccumulator = 0f;

        private bool rainbowTurn;
        private Queue<BackgroundElement> onscreen;
        private Queue<BackgroundElement> upcoming;
        private Hero hero;
   
        public Background(Hero hero)
        {
            Speed = SpeedReference;

            this.hero = hero;
            onscreen = new Queue<BackgroundElement>();
            upcoming = new Queue<BackgroundElement>();

            // initialize sequence with one canvas followed by
            // one rainbow (so it can forecast colors to player)
            onscreen.Enqueue(new Canvas(hero, Game.leftEdge()));
            rainbowTurn = true;
            while (onscreen.Last().rightEdge() <= Game.rightEdge())
            {
                addItem(onscreen);
            }

            if (rainbowTurn)
            {
                upcoming.Enqueue(new Rainbow(hero, onscreen.Last().rightEdge()));
            }
            else
            {
                upcoming.Enqueue(new Canvas(hero, onscreen.Last().rightEdge()));
            }
            rainbowTurn = !rainbowTurn;
        }

        public void update()
        {
            accelerateGame();

            foreach (BackgroundElement e in onscreen)
            {
                e.move();
            }
            foreach (BackgroundElement e in upcoming)
            {
                e.move();
            }

            updateSequence();

            foreach (BackgroundElement e in onscreen)
            {
                e.interact();
            }            
        }

        public void stop()
        {
            Speed = 0f;
        }

        public void resume()
        {
            Speed = SpeedReference;
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
                addItem(upcoming);
                onscreen.Enqueue(upcoming.Dequeue());
            }
        }

        private void addItem(Queue<BackgroundElement> seq)
        {
            // alternate between adding rainbow or canvas
            BackgroundElement e;
            if (rainbowTurn)
            {
                Console.WriteLine("Adding rainbow to background");
                e = new Rainbow(hero, seq.Last().rightEdge());
            }
            else
            {
                Console.WriteLine("Adding canvas to background");
                e = new Canvas(hero, seq.Last().rightEdge());
            }
            rainbowTurn = !rainbowTurn;
            seq.Enqueue(e);
        }

        private static void accelerateGame()
        {
            SpeedAccumulator += Speed;
            if (SpeedAccumulator > 500)
            {
                SpeedReference *= 1.1f;
                SpeedAccumulator = 0f;
                Console.WriteLine("Increasing game speed to " + SpeedReference);
            }
        }

    }
}
