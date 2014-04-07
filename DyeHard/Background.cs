﻿using System;
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
            while (onscreen.Last().rightEdge() <= Game.rightEdge())
            {
                onscreen.Enqueue(nextElement(onscreen));
            }

            upcoming.Enqueue(nextElement(onscreen));
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
                upcoming.Enqueue(nextElement(upcoming));
                onscreen.Enqueue(upcoming.Dequeue());
            }
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

        private BackgroundElement nextElement(Queue<BackgroundElement> seq)
        {
            if (seq.Last().GetType().Name == "Rainbow")
            {
                Console.WriteLine("Adding canvas to background");
                return new Canvas(hero, seq.Last().rightEdge());
            }
            else
            {
                Console.WriteLine("Adding rainbow to background");
                return new Rainbow(hero, seq.Last().rightEdge());
            }
        }

    }
}
