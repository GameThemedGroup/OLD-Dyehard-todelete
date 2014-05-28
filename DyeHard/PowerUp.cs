using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using XNACS1Lib;
using Microsoft.Xna.Framework;

namespace Dyehard
{
    abstract class PowerUp : GameObject
    {
        private const float width = 5f;
        protected Hero hero;
        protected XNACS1Rectangle box;

        public PowerUp(Hero hero, float minX, float maxX)
        {
            this.hero = hero;

            float padding = hero.getPosition().Width * 2;
            float randomX = XNACS1Base.RandomFloat(minX + padding, maxX - padding);
            float randomY = XNACS1Base.RandomFloat(GameWorld.bottomEdge + padding, GameWorld.topEdge - padding);
            box = new XNACS1Rectangle(new Vector2(randomX, randomY), width, width * 0.39f);
        }

        public override void remove()
        {
            box.RemoveFromAutoDrawSet();
        }

        public void move()
        {
            box.CenterX -= GameWorld.Speed;
        }

        public override void update()
        {
            if (box.Collided(hero.getPosition()) && box.Visible)
            {
                hero.collect(this);
            }
        }

        public override void draw()
        {
            box.TopOfAutoDrawSet();
        }

        public virtual void activate()
        {
            box.Visible = false;
        }

        public static PowerUp randomPowerUp(Hero hero, float minX, float maxX)
        {
            switch (XNACS1Base.RandomInt(4))
            {
                case 0:
                    return new SpeedUp(hero, minX, maxX);
                case 1:
                    return new Ghost(hero, minX, maxX);
                case 2:
                    return new Invincibility(hero, minX, maxX);
                case 3:  // default
                default:
                    return new LowGrav(hero, minX, maxX);
            }
        }
    }

    class SpeedUp : PowerUp
    {
        public static PowerUpMeter meter = new PowerUpMeter(0, Game.Green);

        public SpeedUp(Hero hero, float minX, float maxX)
            : base(hero, minX, maxX)
        {
            box.Texture = "PowerUp_Green";
        }

        public override void activate()
        {
            meter.reset(5);
            base.activate();
        }
    }

    class Ghost : PowerUp
    {
        public static PowerUpMeter meter = new PowerUpMeter(1, Game.Blue);

        public Ghost(Hero hero, float minX, float maxX)
            : base(hero, minX, maxX)
        {
            box.Texture = "PowerUp_Blue";
        }

        public override void activate()
        {
            meter.reset(5);
            base.activate();
        }
    }

    class Invincibility : PowerUp
    {
        public static PowerUpMeter meter = new PowerUpMeter(2, Game.Pink);

        public Invincibility(Hero hero, float minX, float maxX)
            : base(hero, minX, maxX)
        {
            box.Texture = "PowerUp_Pink";
        }

        public override void activate()
        {
            meter.reset(5);
            base.activate();
        }
    }

    class LowGrav : PowerUp
    {
        public static PowerUpMeter meter = new PowerUpMeter(3, Game.Red);

        public LowGrav(Hero hero, float minX, float maxX)
            : base(hero, minX, maxX)
        {
            box.Texture = "PowerUp_Red";
        }

        public override void activate()
        {
            meter.reset(5);
            base.activate();
        }
    }

    class PowerUpMeter
    {
        private XNACS1Rectangle box;
        private XNACS1Rectangle meter;
        private Timer timer;
        private float initialTime;
        private float initialMeterHeight;

        public PowerUpMeter(int sequenceNumber, Color color)
        {
            initialTime = 0f;
            timer = new Timer(0);
            float padding = 0.75f;
            float height = GameWorld.panelSize;
            float width = height;

            float offset = GameWorld.leftEdge + (sequenceNumber + 1) * (padding) + sequenceNumber * width + width / 2;

            box = new XNACS1Rectangle(new Vector2(offset, GameWorld.topEdge + (GameWorld.panelSize / 2)), width, height);
            box.Texture = "PowerUp_Box1";

            initialMeterHeight = height / 1.8f;
            meter = new XNACS1Rectangle(box.Center, width / 1.8f, initialMeterHeight);
            meter.Color = color;
        }

        ~PowerUpMeter()
        {
            box.Visible = false;
            box.RemoveFromAutoDrawSet();
        }

        public void update()
        {
            if (timer.isDone())
            {
                meter.Visible = false;
            }
            else
            {
                timer.update();
                meter.Visible = true;
                meter.Height = initialMeterHeight * Math.Max(0, timer.currentTime() / initialTime);
                meter.CenterY = box.CenterY - ((initialMeterHeight - meter.Height) / 2);
            }
        }

        public void reset(float time)
        {
            initialTime = time;
            timer = new Timer(time);
        }

        public void draw()
        {
            box.TopOfAutoDrawSet();
            meter.TopOfAutoDrawSet();
        }
    }
}
