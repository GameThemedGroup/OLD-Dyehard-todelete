using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using XNACS1Lib;
using Microsoft.Xna.Framework;
namespace Dyehard
{
    class BrainRobot :Enemy
    {
       public BrainRobot( Vector2 center, int width, int height, Hero currentHero)
            : base(center, width, height, currentHero)
        {
            this.getPosition().Texture = "BrainRobot";
        }
    }
}
