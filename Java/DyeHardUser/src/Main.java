import Engine.GameWindow;

@SuppressWarnings("serial")
public class Main extends GameWindow {
    public static void main(String[] args) {
        (new Main()).startProgram();
    }

    public Main() {
        setRunner(new UserCode());
    }
}