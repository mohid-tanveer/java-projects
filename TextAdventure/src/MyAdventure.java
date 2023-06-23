import java.util.Scanner;

/** My text adventure game */
public class MyAdventure {
    public static void main(String[] args) {
        new MyAdventure().run();
    }

    /** The player's current intelligence. */
    private int bestIntelligence;

    /** The room where the player currently is. */
    private Room currentRoom;

    /** Total score of player. */
    private int score;

    public MyAdventure() {
        // Create rooms
        Room entrance = new Room("the gate",
                "A long road awaits you, in the distance you can see what appears to be a library");
        Room mg = new Room("the middle ground", "A place where students are studying, maybe join them and take your canvas quiz for life class");
        Room lair = new Room("the lair", "You need food to do well on your exam in briggs");
        Room briggs = new Room("briggs",
                "Are you ready to take your exam? Did you bring your backpack from your dorm? proceed with caution");
        Room dorm = new Room("dorm", "There's a comfortable bed, and your missing backpack");
        Room gym = new Room("bclc gym", "There are lots of weights to work out with. Have you eaten yet, you don't want to pass out. Strengthen the body, strengthen the mind");
        // Create connections
        entrance.addNeighbor("north", mg);
        mg.addNeighbor("south", entrance);
        mg.addNeighbor("west", lair);
        mg.addNeighbor("north", briggs);
        mg.addNeighbor("east", dorm);
        briggs.addNeighbor("west", lair);
        briggs.addNeighbor("south", mg);
        lair.addNeighbor("east", mg);
        lair.addNeighbor("south", gym);
        gym.addNeighbor("north", lair);
        gym.addNeighbor("east", mg);
        dorm.addNeighbor("west", mg);
        // Create and install assessments
        mg.setMonster(new Monster("quiz", 2, "A fairly easy online 'quiz' for your life class"));
        gym.setMonster(new Monster("workout", 3, "You should squeeze in a 'workout' before your exam"));
        briggs.setMonster(new Monster("exam", 5, "Your final 'exam' for your comp sci class"));
        // Create and install treasures
        mg.setTreasure(new Treasure("quizgrade", 10,
                "You should check your 'quizgrade' after you finish."));
        briggs.setTreasure(new Treasure("examgrade", 80,
                "You should check your 'examgrade' after you finish."));
        dorm.setTreasure(new Treasure("clock", 10, "Maybe you should check the 'clock' to see if it's time to sleep after your exam."));
        // Create and install items that raise intelligence
        lair.setWeapon(new Weapon("food", 4, "You should take some 'food' to eat"));
        dorm.setWeapon(new Weapon("backpack", 6, "Your 'backpack' with the things you need for your exam"));
        // The player starts in the entrance, armed with a sword
        currentRoom = entrance;
        bestIntelligence = 3;
    }

    /**
     * Takes the specified assessment and prints the result. If the assessment is
     * present, this either passes it (if the player's intelligence is good enough) or
     * results in the player crying (and the end of the game).
     * Or attempts to take the specified weapon.
     */
    public void take(String name) {
        Monster monster = currentRoom.getMonster();
        Weapon weapon = currentRoom.getWeapon();
        if (monster != null && monster.getName().equals(name)) {
            if (bestIntelligence > monster.getArmor()) {
                System.out.println("You did great!");
                currentRoom.setMonster(null);
            } else {
                System.out.println("You failed miserably.");
                System.out.println("The " + monster.getName() + " made you cry!");
                System.out.println();
                System.out.println("GAME OVER");
                System.exit(0);
            }
        } else if (weapon != null && weapon.getName().equals(name)) {
            currentRoom.setWeapon(null);
            if (weapon.getDamage() > bestIntelligence) {
                bestIntelligence = weapon.getDamage();
                System.out.println("You'll be a more effective student now!");
            }
        } else {
            System.out.println("There is no " + name + " here.");
        }
    }

    /** Moves in the specified direction, if possible. */
    public void go(String direction) {
        Room destination = currentRoom.getNeighbor(direction);
        if (destination == null) {
            System.out.println("You can't go that way from here.");
        } else {
            currentRoom = destination;
        }
    }

    /** Handles a command read from standard input. */
    public void handleCommand(String line) {
        String[] words = line.split(" ");
        if (currentRoom.getMonster() != null
                && !(words[0].equals("take") || words[0].equals("look"))) {
            System.out.println("You can't do that until you take the assessment.");
            listCommands();
        } else if (words[0].equals("take")) {
            take(words[1]);
        } else if (words[0].equals("go")) {
            go(words[1]);
        } else if (words[0].equals("look")) {
            look();
        } else if (words[0].equals("check")) {
            check(words[1]);
        } else {
            System.out.println("I don't understand that.");
            listCommands();
        }
    }

    /** Prints examples of possible commands as a hint to the player. */
    public void listCommands() {
        System.out.println("Examples of commands:");
        System.out.println("  take quiz");
        System.out.println("  go north");
        System.out.println("  look");
        System.out.println("  check quizgrade");
    }

    /** Prints a description of the current room and its contents. */
    public void look() {
        System.out.println(currentRoom.getDescription() + ".");
        if (currentRoom.getMonster() != null) {
            System.out.println(currentRoom.getMonster().getDescription());
        }
        if (currentRoom.getWeapon() != null) {
            System.out.println(currentRoom.getWeapon().getDescription());
        }
        if (currentRoom.getTreasure() != null) {
            System.out.println(currentRoom.getTreasure().getDescription());
        }
        System.out.println("Exits: " + currentRoom.listExits());
    }

    /** Runs the game. */
    public void run() {
        listCommands();
        System.out.println();
        while (true) {
            System.out.println("You are in the " + currentRoom.getName() + ".");
            System.out.print("> ");
            try (Scanner scan = new Scanner(System.in)) {
                String str = scan.nextLine();
                handleCommand(str);
            }
            System.out.println();
        }
    }

    /** Attempts to pick up the specified treasure. */
    public void check(String name) {
        Treasure treasure = currentRoom.getTreasure();
        if (treasure != null && treasure.getName().equals(name)) {
            currentRoom.setTreasure(null);
            score += treasure.getValue();
            System.out.println("Your score is now " + score + " out of 100.");
            if (score == 100) {
                System.out.println();
                System.out.println("You had a long day time to go to sleep...");
                System.out.println("YOU WIN!");
                System.exit(0);
            }
        } else {
            System.out.println("There is no " + name + " here.");
        }
    }
}
