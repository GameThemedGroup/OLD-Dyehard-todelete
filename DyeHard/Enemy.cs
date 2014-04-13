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
        protected Hero theHero;
        protected float moveSpeed = 0.5f;
        protected float timer;
        protected bool alreadyCollied;
        protected int movementType;
        protected Color thisEnemyColor;
        protected bool coloHaveChanged;
        public bool isRemoved;

        public Enemy(Vector2 center, int width, int height, Hero currentHero)
            : base(center, 5, 5)
        {
            this.getPosition().Texture = "Robot3";
            theHero = currentHero;
            timer = 5.0f;
            alreadyCollied = true;
            movementType = 1;
            coloHaveChanged = false;
            isRemoved = false;
            this.currentPosition.Color = Game.randomColor();
        }
        /*
        public void onUse(Player player)
        {
            this.remove();
        }
        */


        public void chaseHero()
        {
            float playerCenterX = theHero.getPosition().CenterX;
            float playerCenterY = theHero.getPosition().CenterY;

            this.getPosition().CenterX = this.getPosition().CenterX + 
                (playerCenterX - this.getPosition().CenterX) / 100;
            this.getPosition().CenterY = this.getPosition().CenterY + 
                (playerCenterY - this.getPosition().CenterY) / 100;
        }
         

        public void moveLeft()
        {
            this.getPosition().CenterX = this.getPosition().CenterX - Environment.Speed;

        }
      
        public void update()
        {
            if (movementType == 1)
            {
                this.moveLeft();
            }
            if (movementType == 2)
            {
                this.interactWithHero();
            }

            if (this.getPosition().Collided(theHero.getPosition()))
            {
                theHero.kill();
            }

            if (this.getPosition().CenterX <= 0)
            {
                this.movementType = 2;
                if (coloHaveChanged == false)
                {
                    thisEnemyColor = getRandomColor();
                    this.getPosition().TextureTintColor = thisEnemyColor;
                }
            }
        }
        
        public void interactWithHero()
        {
                //this.CenterX = player.CenterX - 20;
                //this.CenterY = player.CenterY;
                chaseHero();

        }
        
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
            this.getPosition().TextureTintColor = inputColor;
            coloHaveChanged = true;
        }
        

        public void remove()
        {
            getPosition().RemoveFromAutoDrawSet();
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
