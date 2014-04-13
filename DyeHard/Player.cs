using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using XNACS1Lib;
using Microsoft.Xna.Framework;

// Player takes input from the current game player to manipulate the Hero object.
// Player represents essentially the joystick commands.

namespace Dyehard
{
    class Player
    {
        private Hero hero;

        public Player(Hero hero)
        {
            this.hero = hero;
        }

        public void update()
        {

            Vector2 direction = new Vector2(XNACS1Base.GamePad.ThumbSticks.Right.X, XNACS1Base.GamePad.ThumbSticks.Right.Y);

            // add jetpack factor
            if (direction.Y > 0)
            {
                direction.Y *= 1.5f;
            }

            // update hero per input
            ((CharacterBlock)hero.getBox()).push(direction);
        }
    } 
}
