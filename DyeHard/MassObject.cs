using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using XNACS1Lib;
using Microsoft.Xna.Framework;

namespace Dyehard
{
    class MassObject : XNACS1Rectangle
    {
        private Vector2 gravity;
        private float drag;
        private float horizonalSpeedLimit;
        
        public MassObject(Vector2 position, float width, float height)
            : base(position, width, height)
        {
            this.gravity = new Vector2(0, -.05f);
            this.drag = 0.95f;
            this.horizonalSpeedLimit = 1f;

            // set object into motion;
            this.ShouldTravel = true;
        }

        public void push(Vector2 direction)
        {
            // scale direction
            direction = direction / 8f;

            if (MinBound.Y <= Game.bottomEdge() || MaxBound.Y >= Game.topEdge())
            {
                VelocityY = 0f;
            }
            Velocity = (Velocity + direction + gravity) * drag;

            if (VelocityX < 0)
            {
                VelocityX = Math.Max(VelocityX, -horizonalSpeedLimit);
            }
            else
            {
                VelocityX = Math.Min(VelocityX, horizonalSpeedLimit);
            }
        }
    }
}
