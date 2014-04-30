using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Audio;
using Microsoft.Xna.Framework.Content;
using Microsoft.Xna.Framework.GamerServices;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework.Input;
using Microsoft.Xna.Framework.Media;
using Microsoft.Xna.Framework.Net;
using Microsoft.Xna.Framework.Storage;

using XNACS1Lib;

namespace Dyehard
{
    public class HaloEmitter : XNACS1ParticleEmitter
    {
        float m_Radius;
        float m_Speed;

        public HaloEmitter(Vector2 center, int initialParticleLife, float particleSize, Color color, float radius)
            : base(center, initialParticleLife, particleSize, "", color)
        {
            AutoEmitEnabled = false;
            m_Radius = radius;
            m_Speed = radius / initialParticleLife;
        }

        public void DrawHalo(int numParticles)
        {
            for (int i = 0; i < numParticles; i++)
            {
                Emit();
            }
        }

        protected override void NewParticle(XNACS1Particle newParticle)
        {
            float theta = XNACS1Base.RandomFloat(0, (float)(2 * Math.PI));
            newParticle.CenterX += m_Radius * (float)Math.Cos(theta);
            newParticle.CenterY += m_Radius * (float)Math.Sin(theta);
            Vector2 randomVelocity = new Vector2(XNACS1Base.RandomFloat(-1, 1), XNACS1Base.RandomFloat(-1, 1));
            randomVelocity *= m_Speed;
            newParticle.Velocity = randomVelocity;
            newParticle.ShouldTravel = true;
        }

        protected override void UpdateParticle(XNACS1Particle particle)
        {
            int temp = (255 * particle.Life) / InitialLife;
            if (temp > 255) temp = 255;
            else if (temp < 0) temp = 0;
            Color tempColor = particle.TextureTintColor;
            tempColor.A = (byte)temp;
            particle.TextureTintColor = tempColor;
        }
    }
}
