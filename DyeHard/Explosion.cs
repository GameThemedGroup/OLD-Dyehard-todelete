using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using XNACS1Lib;
using Microsoft.Xna.Framework;
namespace Dyehard
{
    class Explosion :XNACS1Circle
    {

        public XNACS1Circle position;
        public float radious = 5;
        public Explosion()
        {
            position = new XNACS1Circle();
        }

        public void explod(float x, float y, Color c)
        {
            position = new XNACS1Circle(new Vector2(x, y), radious);
            position.Color = c;
            position.Label = "Boom";
        }

        public void draw()
        {
            position.TopOfAutoDrawSet();
        }

    }
}
