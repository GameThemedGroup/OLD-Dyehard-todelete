using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using XNACS1Lib;
using Microsoft.Xna.Framework;


namespace Dyehard
{
    public  class Enemy : XNACS1Rectangle 
    {

        private float moveSpeed = 0.5f;
        float timer;
        public bool alreadyCollied;
        public int movementType;
        public Color thisEnemyColor;
        private bool coloHaveChanged;
        public bool isRemoved;

        public Enemy(Vector2 center, int width, int height)
            : base(center, 5, 5)
        {
            Texture = "pika";
            timer = 5.0f;
            alreadyCollied = true;
            movementType = 1;
            coloHaveChanged = false;
            isRemoved = false;
        }
        /*
        public void onUse(Player player)
        {
            this.remove();
        }
        */

        /*
        public void chasePlayer(Player player)
        {
            float playerCenterX = player.CenterX;
            float playerCenterY = player.CenterY;

            this.CenterX = this.CenterX + (playerCenterX - this.CenterX) / 100;
            this.CenterY = this.CenterY + (playerCenterY - this.CenterY) / 100;
        }
         */

        public void moveLeft()
        {
            this.CenterX = this.CenterX - Background.Speed;

        }
      
        public void upDate()
        {
            if (movementType == 1)
            {
                this.moveLeft();
            }

            if (this.CenterX <= 0)
            {
                this.movementType = 2;
                if (coloHaveChanged == false)
                {
                    thisEnemyColor = getRandomColor();
                    this.TextureTintColor = thisEnemyColor;
                }
            }

           

        }
        /*
        public void interact(Player player)
        {
            if (movementType == 2)
            {
                //this.CenterX = player.CenterX - 20;
                //this.CenterY = player.CenterY;
                chasePlayer(player);
            }

        }
        */
        /*
        public void interact(Path currentPath)
        {

            XNACS1Rectangle temp;
            for (int x = 0; x < 4; x++)
            {
                temp = currentPath.getPaths()[x];
                temp.Color = currentPath.getPaths()[x].Color;
                if (this.Collided(temp) && temp.Color != this.thisEnemyColor)
                {
                    this.remove();
                    this.isRemoved = true;
                }
            }
        }
        */
        public void changeColor(Color inputColor){
            thisEnemyColor = inputColor;
            this.TextureTintColor = inputColor;
            coloHaveChanged = true;
        }
        

        public void remove()
        {
            RemoveFromAutoDrawSet();
            //boarder.RemoveFromAutoDrawSet();
        }

        

        public static Color getRandomColor()
        {

             Random r = new Random();

            switch (r.Next(8))
            {
                case 0: return Color.Red;
                case 1: return Color.Yellow;
                case 2: return Color.Green;
                case 3: return Color.Blue;
                case 4: return Color.Pink;
                case 5: return Color.Orange;
                case 6: return Color.Cyan;
                default: return Color.Purple;
            }
        }
    }
}
