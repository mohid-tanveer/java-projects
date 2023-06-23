/** A Weapon. */
public class Weapon {

    /** A description of this weapon. */
    private String description;

    /** This weapon's name. */
    private String name;

    /** The strength value of this weapon. */
    private int damage;

    /**
     * @param name
     *            This weapon's name.
     * @param value
     *            The damage of this weapon.
     * @param description
     *            A description of this weapon.
     */
    public Weapon(String name, int value, String description) {
        this.name = name;
        this.damage = value;
        this.description = description;
    }

    /** Returns a description of this weapon. */
    public String getDescription() {
        return description;
    }

    /** Returns this weapon's name. */
    public String getName() {
        return name;
    }

    /** Returns the strength value of this weapon. */
    public int getDamage() {
        return damage;
    }

}
