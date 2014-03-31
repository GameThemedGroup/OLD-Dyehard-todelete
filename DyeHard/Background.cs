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
        bool rainbowTurn;
        Queue<BackgroundElement> sequence;
        Hero hero;
   
        public Background(Hero hero)
        {
            this.hero = hero;
            this.sequence = new Queue<BackgroundElement>();
            this.sequence.Enqueue(new Canvas(hero));
            this.sequence.First().centerOnScreen();
            rainbowTurn = true;
        }

        public void update()
        {
            if (Keyboard.GetState().IsKeyUp(Keys.Space))
            {
                foreach (BackgroundElement e in sequence)
                {
                    e.move();
                }
            }
            updateSequence();

            foreach (BackgroundElement e in sequence)
            {
                e.interact();
            }
            
        }

        private void updateSequence()
        {
            if (sequence.First().isOffScreen())
            {
                // remove off screen element
                sequence.Dequeue();
                Console.WriteLine("Dumping element from background");
            }

            if (sequence.Last().rightEdgeIsOnScreen())
            {
                // need to add new element to end of sequence
                // alternate between adding rainbow or canvas
                if (rainbowTurn)
                {
                    Console.WriteLine("Adding rainbow to background");
                    sequence.Enqueue(new Rainbow(hero));
                    rainbowTurn = false;
                }
                else
                {
                    Console.WriteLine("Adding canvas to background");
                    sequence.Enqueue(new Canvas(hero));
                    rainbowTurn = true;
                }
            }
        }

    }
}
