using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using XNACS1Lib;
using Microsoft.Xna.Framework;


namespace Dyehard
{
     class Enemy : Character
    {
        protected Hero hero;
        protected int movementType;
        public bool isRemoved;
        

        public Enemy(Vector2 center, float width, float height, Hero hero)
            : base(center, width, height)
        {
            this.hero = hero;
            movementType = 1;
            isRemoved = false;
            setColor(Game.randomColor());
        }

        public void chaseHero()
        {
            if (Environment.Speed != 0)
            {
                Vector2 direction = Vector2.Normalize(hero.getPosition().Center - position.Center);
                position.Velocity = (direction * 0.15f);
            }
            else
            {
                position.Velocity = new Vector2();
            }
        }
         

        public void floatLeft()
        {
            position.CenterX -= Environment.Speed;
        }
      
        public override void update()
        {
            if (movementType == 1)
            {
                floatLeft();
            }
            if (movementType == 2)
            {
                chaseHero();
            }

            if (this.getPosition().Collided(hero.getPosition()))
            {
                hero.kill();
            }

            if (getPosition().CenterX <= 0)
            {
                movementType = 2;
            }

            base.update();
        }

         public void gotShot(Color color){
             this.setColor(color);
         }
    }
}
