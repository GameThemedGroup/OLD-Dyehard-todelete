using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using XNACS1Lib;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Input;

namespace Dyehard
{
    class GameWorld
    {

        // Dyehard Dye Colors
        public static int colorCount = 6;
        public static Color Green = new Color(38, 153, 70);
        public static Color Red = new Color(193, 24, 30);
        public static Color Yellow = new Color(228, 225, 21);
        public static Color Teal = new Color(90, 184, 186);
        public static Color Pink = new Color(215, 59, 148);
        public static Color Blue = new Color(50, 75, 150);


        private const float START_SPEED = 0.2f;

        public static float Speed;
        public static Vector2 Gravity = new Vector2(0, -0.02f);

        private float SpeedReference;
        private bool stop;
        private Timer timer;

        private Queue<Environment> onscreen;
        private Queue<Environment> upcoming;
        private Hero hero;
        private EnemyManager eManager;
        private Player player;
        private InfoPanel infoPanel;
   
        public GameWorld()
        {
            hero = new Hero();
            player = new Player(hero);
            infoPanel = new InfoPanel(hero);
            eManager = new EnemyManager(hero);
            stop = false;
            timer = new Timer(15);

            SpeedReference = START_SPEED;
            Speed = SpeedReference;

            
            onscreen = new Queue<Environment>();
            upcoming = new Queue<Environment>();

            // set the enemy manager for the weapon
            hero.setEnemies(eManager.getEnemies());

            // first element on screen
            onscreen.Enqueue(new Space(hero, eManager.getEnemies(), Game.leftEdge()));

            // fill the rest of the exisiting screen
            while (onscreen.Last().rightEdge() <= Game.rightEdge())
            {
                onscreen.Enqueue(nextElement(onscreen));
            }

            // prep upcoming elements
            upcoming.Enqueue(nextElement(onscreen));

        }

        public void update()
        {
            checkControl();
            accelerateGame();

            player.update();

            foreach (Environment e in onscreen)
            {
                e.move();
            }

            foreach (Environment e in upcoming)
            {
                e.move();
            }

            updateSequence();

            foreach (Environment e in onscreen)
            {
                e.interact();
            }

            eManager.update();
            hero.update();

            infoPanel.update();
        }

        public void draw()
        {
            foreach (Environment e in onscreen)
            {
                e.draw();
            }

            foreach (Environment e in upcoming)
            {
                e.draw();
            }
            eManager.draw();

            hero.draw();
            infoPanel.draw();
        }

        private void checkControl()
        {
            // pause game speed
            if (KeyboardDevice.isKeyTapped(Keys.W))
            {
                stop = !stop;
                if (stop)
                {
                    Console.WriteLine("Entering debug mode - press 'W' to resume game");
                    Speed = 0f;
                }
                else
                {
                    Console.WriteLine("Exiting debug mode");
                    Speed = SpeedReference;
                }
            }

            if (KeyboardDevice.isKeyTapped(Keys.P))
            {
                eManager.killAll();
            }
        }

        private void accelerateGame()
        {
            if (!stop)
            {
                timer.update();
                Speed = SpeedReference;
            }

            if (timer.isDone())
            {
                timer.reset();
                SpeedReference += 0.01f;
            }
        }

        private void updateSequence()
        {
            if (onscreen.First().isOffScreen())
            {
                // remove off screen element
                onscreen.Dequeue();
            }

            if (onscreen.Last().rightEdge() <= Game.rightEdge())
            {
                // move item from upcoming to end of onscreen
                upcoming.Enqueue(nextElement(upcoming));
                onscreen.Enqueue(upcoming.Dequeue());
            }
        }

        private Environment nextElement(Queue<Environment> seq)
        {
            if (seq.Last().GetType() == typeof(Stargate))
            {
                return new Space(hero, eManager.getEnemies(), seq.Last().rightEdge());
            }
            else
            {
                return new Stargate(hero, eManager.getEnemies(), seq.Last().rightEdge());
            }
        }

        public bool gameOver()
        {
            return !hero.isAlive();
        }



        public static List<Color> randomColorSet(int count)
        {
            // get a random and unique subset of the available colors

            List<int> range = Enumerable.Range(0, colorCount).ToList();
            List<int> sample = new List<int>();

            // set up the indexes in the sample list
            for (int i = 0; i < count; i++)
            {
                int choice = XNACS1Base.RandomInt(range.Count);
                sample.Add(range.ElementAt(choice));
                range.RemoveAt(choice);
            }

            // get the colors from the indexes in the sample list
            List<Color> colors = new List<Color>();
            foreach (int i in sample)
            {
                colors.Add(colorPicker(i));
            }

            return colors;
        }

        public static Color randomColor()
        {
            // get a single random color
            return colorPicker(XNACS1Base.RandomInt(6));
        }

        private static Color colorPicker(int choice)
        {
            switch (choice)
            {
                case 0: return Green;
                case 1: return Red;
                case 2: return Yellow;
                case 3: return Teal;
                case 4: return Pink;
                case 5: return Blue;
            }
            return Color.Black;
        }
    }
}
