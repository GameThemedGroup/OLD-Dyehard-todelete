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
        private const float enemyFrequency = 12f;
        private Hero hero;
        private List<Enemy> enemies;
        private Timer newEnemyTimer;

        public EnemyManager(Hero hero)
        {
            this.hero = hero;
            enemies = new List<Enemy>();
            newEnemyTimer = new Timer(enemyFrequency);
        }

        public void remove()
        {
            foreach (Enemy e in enemies) {
                e.remove();
            }
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
            if (GameWorld.Speed != 0)
            {
                newEnemyTimer.update();
            }
            
            //generate new enemy
            if (newEnemyTimer.isDone())
            {
                float randomY = XNACS1Base.RandomFloat(GameWorld.topEdge - 5, GameWorld.bottomEdge + 5);
                Vector2 position = new Vector2(GameWorld.rightEdge + 5, randomY);
                
                switch (XNACS1Base.RandomInt(0,3))
                {
                    case 1:
                        enemies.Add(new BrainRobot(position, 3.25f, 6, hero));
                        break;
                    case 2:
                        enemies.Add(new WhiteRobot(position, 4.14f, 6, hero));
                        break;
                    default:
                        enemies.Add(new BlackRobot(position, 3.6f, 6, hero));
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
