using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using XNACS1Lib;
using Microsoft.Xna.Framework;


namespace Dyehard
{
    
    class Explosion
    {
        private float explosionRadius = 2.5f;
        private HaloEmitter p;
        protected XNACS1Circle position;
        Timer timer;
        private bool isDelete;
        private int explosionExistTime = 1;

        public Explosion(Hero h, Enemy e)
        {
            isDelete = false;
            position = new XNACS1Circle(e.getPosition().Center, explosionRadius);
            p = new HaloEmitter(e.getPosition().Center, 30, 0.7f, h.getColor(), 2);
            p.DrawHalo(20);
            timer = new Timer(explosionExistTime);
            position.Color = h.getColor();
            position.Label = "Boom";
            position.Visible = false;
        }

        public void update()
        {
            timer.update();
            position.TopOfAutoDrawSet();
            p.TopOfAutoDrawSet();

            if (timer.isDone())
            {
                isDelete = true;
            }
        }

        public bool deletedOrNot()
        {
            return isDelete;
        }

        public void interactEnemy(List<Enemy> enemies){

            foreach (Enemy e in enemies)
            {
                if (e.getPosition().Collided(position)){
                    e.gotShot(position.Color);
                }
            }

        }
    }
}
