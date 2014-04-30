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

         protected enum EnemyState
         {
             BEGIN,
             CHASEHERO,
             PLAYING,
             DEAD
         };
        protected Hero hero;
        protected EnemyState enemyState;
        Timer enemyBhaviorTimer;
        int changeBehaviorTime = 5;

        public Enemy(Vector2 center, float width, float height, Hero hero)
            : base(center, width, height)
        {
            this.hero = hero;
            setColor(Game.randomColor());
            enemyState = EnemyState.BEGIN;
            enemyBhaviorTimer = new Timer(changeBehaviorTime);
        }


        public override void update()
        {
            enemyBhaviorTimer.update();
            if (enemyBhaviorTimer.isDone())
            {
                enemyState = EnemyState.CHASEHERO;
            }

            switch (enemyState)
            {
                case EnemyState.BEGIN:
                    moveLeft();
                    break;

                case EnemyState.CHASEHERO:
                    chaseHero();
                    break;
            }


            if (this.getPosition().Collided(hero.getPosition()))
            {
                hero.kill();
            }

            base.update();
        }


        public void chaseHero()
        {
            if (Environment.Speed != 0)
            {
                Vector2 direction = Vector2.Normalize(hero.getPosition().Center - position.Center);
                position.Velocity = (direction * 0.1f);
            }
            else
            {
                position.Velocity = new Vector2();
            }
        }


        public void moveLeft()
        {
            position.CenterX -= Environment.Speed;
        }

         public void gotShot(Color color){
             this.setColor(color);
         }
    }
}
