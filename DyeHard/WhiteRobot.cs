using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using XNACS1Lib;
using Microsoft.Xna.Framework;
namespace Dyehard
{
    class WhiteRobot : Enemy
    {
        public WhiteRobot(Vector2 center, float width, float height, Hero currentHero)
            : base(center, width, height, currentHero)
        {
            this.setTexture("WhiteRobot");
        }
    }
}
