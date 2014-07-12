import Engine.GameWindow;

@SuppressWarnings("serial")
public class Main extends GameWindow
{
  public Main()
  {
    setRunner(new RectangleTest());
  }

  public static void main(String[] args)
  {
    (new Main()).startProgram();
    
  }
}