import java.util.ArrayList;

/** A room. */
public class Room {
    /** A description of this room. */
    private String description;

    /** This rooms's name. */
    private String name;

    /** This room's exits. */
    private ArrayList<String> exits = new ArrayList<String>();

    /** This room's neighbors. */
    private ArrayList<Room> neighbors = new ArrayList<Room>();

    /** This room's treasure. */
    private Treasure treasure;

    /** This room's weapon. */
    private Weapon weapon;

    /** This room's monster. */
    private Monster monster;

    /**
     * @param name
     *            This room's name.
     * @param description
     *            A description of this room.
     */
    public Room(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /** Returns a description of this room. */
    public String getDescription() {
        return description;
    }

    /** Returns this room's name. */
    public String getName() {
        return name;
    }

    /** Returns this room's neighbor. */
    public Room getNeighbor(String direction) {
        int num = exits.indexOf(direction);
        if (num == -1) {
            return null;
        }
        return neighbors.get(num);
    }

    /** Returns this room's exits. */
    public String listExits() {
        return exits.toString();
    }

    /** Sets this room's monster. */
    public void setMonster(Monster type){
        monster = type;
    }

    /** Sets this room's treasure. */
    public void setTreasure(Treasure type){
        treasure = type;
    }

    /** Sets this room's weapon. */
    public void setWeapon(Weapon type){
        weapon = type;
    }

    /** Returns this room's monster. */
    public Monster getMonster() {
        return monster;
    }

    /** Returns this room's treasure. */
    public Treasure getTreasure() {
        return treasure;
    }

    /** Returns this room's weapon. */
    public Weapon getWeapon() {
        return weapon;
    }

    /** Adds the direction and name of this room's neighbor. */
    public void addNeighbor(String direction, Room name) {
        exits.add(direction);
        neighbors.add(name);
    }

}