import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
/**
 * Write a description of class MyWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Graveyard extends World
{
    private static final int NUM_OF_BLOCKS = 4;
    private static final int STARTING_KILLS = 0;
    private static final int STARTING_LIVES = 3;
    private static final int STARTING_STARS = 5;
    private static final int ZOMBIE_COOLDOWN = 25;
    private static final int PLANE_COOLDOWN = 500;
    private static final int FREQ_INCREASE = 5;
    private static final int MIN_LIVES = 0;
    private static final int MAX_KILLS = 10;
    private static final int END_GAME_ITER = 50;
    
    private Lives[] hearts = {new Lives(), new Lives(), new Lives()};

    private Ninja n;
    private Scoreboard kills;
    private Scoreboard lives;
    private Scoreboard stars;
    private int lifeIndex = 2;
    private int zombieCount = 0;
    private int frequency = 15;
    private boolean freqSwitch = false;
    private int planeCount = 0;
    private int deathCounter = 0;
    private int winCounter = 0;

    /**
     * Constructor for objects of class MyWorld.
     * 
     */
    public Graveyard()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels
        super(700, 500, 1); 
        // set overlapping order
        setPaintOrder(Win.class, Lose.class, Explosion.class, Bomb.class, Debris.class, Ninja.class, Scoreboard.class, Lives.class);

        // initialize
        lifeIndex = 2;
        zombieCount = 0;
        frequency = 15;
        freqSwitch = false;
        planeCount = 0;
        deathCounter = 0;
        winCounter = 0;

        // add ground type 1 and 3 on the far left and far right
        // add 4 ground type 2's in the middle
        addObject(new Ground(1), 63, 450);
        int c = 0;
        for(int i = 0; i < NUM_OF_BLOCKS; i++) {
            addObject(new Ground(2), 190+c, 450);
            c = c + 125;
        }
        addObject(new Ground(3), 640, 450);

        // add a ninja object and store in the variable n
        n = new Ninja();
        addObject(n, 50, 328);

        // store the kills scoreboard inside a variable
        // and the kills scoreboard starting at 3
        kills = new Scoreboard("Kills", STARTING_KILLS);
        addObject(kills, 124, 20);

        // store the stars scoreboard inside a variable
        // and the stars scoreboard starting at 5
        stars = new Scoreboard("Stars Left", STARTING_STARS);
        addObject(stars, 645, 20);

        // store the lives scoreboard inside a variable
        lives = new Scoreboard("Lives", STARTING_LIVES);
        // add 3 Lives object from the array hearts
        addObject(hearts[0], 310, 30); 
        addObject(hearts[1], 350, 30); 
        addObject(hearts[2], 390, 30); 

    }

    public void act() {
        addZombies();
        addPlanes();
        addFreezePowerUp();
        endGame();
    }

    // add zombies every 25 iterations at 15% rate only if they are not frozen
    // the rate increases as ninja gets more kills
    public void addZombies() {
        if(!n.getFreeze()) {
            zombieCount++;
            if(zombieCount % ZOMBIE_COOLDOWN == 0) {
                if(Greenfoot.getRandomNumber(100) < frequency) {
                    addObject(new Zombie(), getWidth(), 330);         
                }
            }
        }
        if(kills.getNum() == 3 && !freqSwitch) {
            frequency = frequency + FREQ_INCREASE;
            freqSwitch = true;
        } else if(kills.getNum() == 5 && freqSwitch) {
            frequency = frequency + FREQ_INCREASE;
            freqSwitch = false;
        } else if(kills.getNum() == 7 && !freqSwitch) {
            frequency = frequency + FREQ_INCREASE;
            freqSwitch = true;
        }
    }

    // add a plane every 500 iterations
    public void addPlanes() {
        planeCount++;
        if(planeCount == PLANE_COOLDOWN) {
            addObject(new Plane(getWidth()), getWidth(), 40);
            planeCount = 0;
        }
    } 

    // only add freeze power ups if there are zombies in the world
    // make a list of all zombie object in the world
    // if the list is not empty add a freeze power up 1/6% of the time
    public void addFreezePowerUp() {
        List<Zombie> zombies = getObjects(Zombie.class);
        if(!zombies.isEmpty()) {
            if(Greenfoot.getRandomNumber(600) < 1) {
                addObject(new Freeze(), Greenfoot.getRandomNumber(getWidth()), 0);
            }
        }
    }

    // make a list of zombies 
    // freeze them by calling the stop method on each zombie in the list
    public void freezeZombies() {
        List<Zombie> zombies = getObjects(Zombie.class);
        for(Zombie z : zombies) {
            z.stop();
        }
    }  

    // increase kills by 1
    public void addToKills() {
        kills.changeKills(1);
    }

    // decrease lives by 1
    public void loseLives() {
        lives.changeLives(-1);
        removeObject(hearts[lifeIndex]);
        lifeIndex--;
    }

    // decrease stars by 1
    public void decreaseStars() {
        stars.changeStars(-1);
    }

    // if lost all lives, play sound, add game over object, and stop the game after 50 iterations
    // if got 10 kills, play sound, add you win object, and stop the game after 50 iterations
    // ... BECAUSE WE"RE IN THE ENDGAME NOW.
    public void endGame() {
        if(lifeIndex < MIN_LIVES) {
            Greenfoot.playSound("lose.wav");
            addObject(new Lose(), getWidth()/2, getHeight()/2);
            deathCounter++;
            if(deathCounter >= END_GAME_ITER) {
                Greenfoot.stop();
            }
        }
        if(kills.getNum() >= MAX_KILLS) {
            Greenfoot.playSound("win.wav");
            addObject(new Win(), getWidth()/2, getHeight()/2);
            winCounter++;
            if(winCounter >= END_GAME_ITER) {
                Greenfoot.stop();
            }
        }
    }
    
    // get the number of lives remaining 
    public int getLives() {
        return lives.getNum();
    }
    
    // get the number of stars left
    public int getStars() {
        return stars.getNum();
    }
}