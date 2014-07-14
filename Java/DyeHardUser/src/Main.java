import Engine.GameWindow;

@SuppressWarnings("serial")
public class Main extends GameWindow
{
  public Main()
  {
    setRunner(new UserCode());
  }

  public static void main(String[] args)
  {
    (new Main()).startProgram();
  }
}