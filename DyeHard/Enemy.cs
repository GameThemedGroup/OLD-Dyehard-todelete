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
        //protected float moveSpeed = 0.5f;
        protected int movementType;
        public bool isRemoved;
        

        public Enemy(Vector2 center, int width, int height, Hero currentHero)
            : base(center, 5, 5)
        {
            this.getPosition().Texture = "Robot3";
            hero = currentHero;
            movementType = 1;
            isRemoved = false;
            setColor(Game.randomColor());
            
        }

        public void chaseHero()
        {
            float playerCenterX = hero.getPosition().CenterX;
            float playerCenterY = hero.getPosition().CenterY;

            XNACS1Rectangle heroPosition = hero.getPosition();

                position.Velocity = Environment.Speed * (heroPosition.Center - position.Center) / 100f;

 
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
