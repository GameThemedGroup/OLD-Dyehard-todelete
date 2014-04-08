using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using XNACS1Lib;
using Microsoft.Xna.Framework;


namespace Dyehard
{
    public class EnemyManager
    {
        Random rnd ;
        


        private List<Enemy> enemies;
        float enemyTimer;

        public EnemyManager()
        {
            enemyTimer = 10;
            enemies = new List<Enemy>();
            enemies.Add(new Enemy(new Vector2(100, 20), 5, 5));
            rnd = new Random();
        }

        public void update()
        {
            if (enemies != null && enemies.Count > 0)
            {
                foreach (Enemy currentEnemy in enemies)
                {
                    currentEnemy.update();
                }
            }


            int enemyAppearPosition = rnd.Next(5, 50);
            enemyTimer = enemyTimer - 0.05f;
            if (enemyTimer <= 0)
            {
                enemies.Add(new Enemy(new Vector2(100, enemyAppearPosition), 5, 5));
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

       /* public void interact(BulletManager bManager)
        {
             List<Bullet> temp = bManager.getBulletList();

             if (enemys.Count > 0 && enemys != null && temp.Count > 0 && temp != null)
            {
                foreach (Enemy currentEnemy in enemys)
                {
                    for (int x = temp.Count(); x > 0; x--)
                    {
                        if (temp[x - 1].Collided(currentEnemy))
                        {
                            currentEnemy.changeColor(temp[x - 1].thisBulletColor);
                            temp[x - 1].remove();
                            temp.RemoveAt(x - 1);

                        }
                    }
                }
            }

        }
        */

       /* public void interact(Player player){
    
            if (enemys != null && enemys.Count > 0)
            {
                foreach (Enemy currentEnemy in enemys)
                {
                    currentEnemy.interact(player);
   
                    if (currentEnemy != null && player.Collided(currentEnemy) && currentEnemy.alreadyCollied == true)
                    {
                        Game.PlayACue("PikaVoice");
                        player.setDeadPublic();
                        break;
                    }
                }
            }
        }
        * */
        /*
        public void interact(Path currentPath)
        {
            if (enemys != null && enemys.Count > 0)
            {
                foreach (Enemy currentEnemy in enemys)
                {
                    currentEnemy.interact(currentPath);
                }
            }
        }
        */
    }
}
