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
            enemies.RemoveAll(enemy => !enemy.isAlive());

            foreach (Enemy currentEnemy in enemies)
            {
                currentEnemy.update();
            }

            if (Environment.Speed != 0)
            {
                newEnemyTimer.update();
            }
            
            if (newEnemyTimer.isDone())
            {
                Vector2 enemyAppearPosition = new Vector2(Game.rightEdge() + 10f, Game.topEdge() - XNACS1Base.RandomFloat(5,20));
                
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

            if (enemies != null && enemies.Count > 0)
            {
                for (int i = enemies.Count - 1; i >= 0; i--)
                {
                    if (enemies[i].isRemoved == true)
                    {
                        enemies.RemoveAt(i);
                    }
                }
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
