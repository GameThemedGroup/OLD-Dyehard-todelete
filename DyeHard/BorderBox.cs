using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using XNACS1Lib;
using Microsoft.Xna.Framework;

namespace Dyehard
{
    class BorderBox
    {
        private List<XNACS1Rectangle> edges;

        public BorderBox(Vector2 center, float width, float height, float thickness, Color color)
        {
            float drawWidth = width;
            float drawHeight = height;
            XNACS1Rectangle top = new XNACS1Rectangle(new Vector2(center.X, center.Y + (height / 2) - (thickness / 2)), drawWidth, thickness);
            XNACS1Rectangle bottom = new XNACS1Rectangle(new Vector2(center.X, center.Y - (height / 2) + (thickness / 2)), drawWidth, thickness);
            XNACS1Rectangle right = new XNACS1Rectangle(new Vector2(center.X + (width / 2) - (thickness / 2), center.Y), thickness, drawHeight);
            XNACS1Rectangle left = new XNACS1Rectangle(new Vector2(center.X - (width / 2) + (thickness / 2), center.Y), thickness, drawHeight);

            this.edges = new List<XNACS1Rectangle>();

            this.edges.Add(top);
            this.edges.Add(bottom);
            this.edges.Add(left);
            this.edges.Add(right);

            foreach (XNACS1Rectangle edge in edges)
            {
                edge.Color = color;
            }
            
        }

        ~BorderBox()
        {
            foreach (XNACS1Rectangle edge in edges)
            {
                edge.RemoveFromAutoDrawSet();
            }
        }

        public void draw()
        {
            foreach (XNACS1Rectangle edge in edges)
            {
                edge.TopOfAutoDrawSet();
            }
        }
    }
}
