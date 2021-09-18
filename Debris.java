import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Debris here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Debris extends Actor
{
    private static final int MAX_WIDTH = 10;
    private static final int MAX_HEIGHT = 10;
    private static final int FULL_ROTATION = 360;
    private static final int QUARTER_ROTATION = 90;
    private static final int SPEED_DIFF = 3;
    private static final int SPEED_MIN = 5;

    private int speed;

    /**
     * Act - do whatever the Debris wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public Debris(String type) {

        // sets width and height to random values each time 
        int width = Greenfoot.getRandomNumber(MAX_WIDTH)+1;
        int height = Greenfoot.getRandomNumber(MAX_HEIGHT)+1;

        // sets rotation to random from 0 to 360 if a zombie dies 
        // sets rotation to random from 90 to 180 if ninja lost a life
        int rotation = 0;
        if(type == "zombie") {
            rotation = Greenfoot.getRandomNumber(FULL_ROTATION);
        }
        if(type == "ninja") {
            rotation = Greenfoot.getRandomNumber(QUARTER_ROTATION)+QUARTER_ROTATION;
        }

        getImage().scale(width,height);
        turn(rotation);

        // sets speed to a different value each time (5-8)
        speed = Greenfoot.getRandomNumber(SPEED_DIFF)+SPEED_MIN;

    }

    public void act() 
    {
        move();
    }
    
    // moves according to the speed variable and removes at the edge
    public void move() {
        move(speed);
        if(isAtEdge()) {
            getWorld().removeObject(this);
        }
    }
}
