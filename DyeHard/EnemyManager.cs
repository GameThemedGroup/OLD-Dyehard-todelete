using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using XNACS1Lib;
using Microsoft.Xna.Framework;


namespace Dyehard
{
     class EnemyManager
    {
        Hero theHero;

        float enemyAX = 100.0f;
        float enemyARandonTop = 50.0f;
        float enemyARandomBot = 0.00f;
        Vector2 enemyAppearPosition;

        private List<Enemy> enemies;
        Timer newEnemyTimer;

        public EnemyManager(Hero currentHero)
        {
            theHero = currentHero;
            enemies = new List<Enemy>();
            newEnemyTimer = new Timer(7);
        }

        public void update()
        {
            // remove any dead enemies
            enemies.RemoveAll(enemy => !enemy.isAlive());

            foreach (Enemy currentEnemy in enemies)
            {
                currentEnemy.update();
            }

            // prevent generating enemy
            if (Environment.Speed != 0)
            {
                newEnemyTimer.update();
            }
            
            //generate new enemy
            if (newEnemyTimer.isDone())
            {
                enemyAppearPosition = new Vector2(enemyAX,XNACS1Base.RandomFloat(enemyARandomBot, enemyARandonTop));
                
                switch (XNACS1Base.RandomInt(0,3))
                {
                    case 1:
                        enemies.Add(new BrainRobot(enemyAppearPosition, 3.25f, 6, theHero));
                        break;
                    case 2:
                        enemies.Add(new WhiteRobot(enemyAppearPosition, 4.14f, 6, theHero));
                        break;
                    default:
                        enemies.Add(new BlackRobot(enemyAppearPosition, 3.6f, 6, theHero));
                        break;
                }

                newEnemyTimer.reset();
            }
        }

        public void draw()
        {
            foreach (Enemy b in enemies)
            {
                b.getPosition().TopOfAutoDrawSet();
            }
        }

        public void killAll()
        {
            enemies.Clear();
        }

        public List<Enemy> getEnemies()
        {
            return enemies;
        }
   
    }
}
