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
        Random rnd ;
        Hero theHero;


        private List<Enemy> enemies;
        float enemyTimer;

        public EnemyManager(Hero currentHero)
        {
            theHero = currentHero;
            enemyTimer = 10;
            enemies = new List<Enemy>();
            rnd = new Random();
        }

        public void update()
        {
            enemies.RemoveAll(enemy => !enemy.isAlive());

            foreach (Enemy currentEnemy in enemies)
            {
                currentEnemy.update();
            }

            Vector2 enemyAppearPosition = new Vector2(Game.rightEdge() + 10f, Game.topEdge() - 5f);
            enemyTimer = enemyTimer - 0.05f;
            if (enemyTimer <= 0)
            {
                int caseSwitch = rnd.Next(0,3);
                switch (caseSwitch)
                {
                    case 1:
                        enemies.Add(new BrainRobot(enemyAppearPosition, 5, 5, theHero));
                        break;
                    case 2:
                        enemies.Add(new WhiteRobot(enemyAppearPosition, 5, 5, theHero));
                        break;
                    default:
                        enemies.Add(new BlackRobot(enemyAppearPosition, 5, 5, theHero));
                        break;
                }
                enemyTimer = 10;
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
