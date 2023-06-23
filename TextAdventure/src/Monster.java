/** A monster. */
public class Monster {
    /** A description of this monster. */
    private String description;

    /** This monster's name. */
    private String name;

    /** The armor value of this monster. */
    private int armor;

    /**
     * @param name
     *            This monster's name.
     * @param value
     *            The armor value of this monster.
     * @param description
     *            A description of this monster.
     */
    public Monster(String name, int value, String description) {
        this.name = name;
        this.armor = value;
        this.description = description;
    }

    /** Returns a description of this monster. */
    public String getDescription() {
        return description;
    }

    /** Returns this monster's name. */
    public String getName() {
        return name;
    }

    /** Returns the armor value of this monster. */
    public int getArmor() {
        return armor;
    }

}
