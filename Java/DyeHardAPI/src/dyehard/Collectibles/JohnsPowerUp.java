package dyehard.Collectibles;

import dyehard.Player.Hero;

public class JohnsPowerUp extends PowerUp {

    public JohnsPowerUp() {
        applicationOrder = 1337;
        label.setText("JOHN");
    }

    @Override
    public void apply(Hero hero) {
        System.out.println("John");
    }

    @Override
    public void unapply(Hero hero) {
        System.out.println("Done");
    }

    @Override
    public PowerUp clone() {
        return new JohnsPowerUp();
    }

    @Override
    public String toString() {
        return "JOHN: " + super.toString();
    }
}
