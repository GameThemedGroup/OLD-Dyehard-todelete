package dyehard.Collectibles;

import dyehard.Player.Hero;

public class JohnsBallinAssPowerUp extends PowerUp {

    public JohnsBallinAssPowerUp() {
        applicationOrder = 1337;
        label.setText("BALLINNNNN");
    }

    @Override
    public void apply(Hero hero) {
        System.out.println("John is the greatest");
    }

    @Override
    public void unapply(Hero hero) {
        System.out.println("John is still the greatest");
    }

    @Override
    public PowerUp clone() {
        return new JohnsBallinAssPowerUp();
    }

    @Override
    public String toString() {
        return super.toString() + " BALLINNNNN";
    }
}
