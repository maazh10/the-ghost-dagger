import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Plane here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Plane extends Actor
{
    private static final int SPEED = 2;
    
    private int location;
    private boolean dropped = false;
    
    /**
     * Act - do whatever the Plane wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    
    public Plane(int x) {
        dropped = false;
        getImage().scale(240,110);
        
        // set the variable location to a multiple of 2 ranging from 0 to the width of the screen
        location = Greenfoot.getRandomNumber(x/2)*2;
    }
    
    public void act() 
    {
        moveAndDrop();
    }    
    
    // move to the left and drop a bomb at the random location intialized in the constructor
    public void moveAndDrop() {
        setLocation(getX()-SPEED,getY());
        if(getX() <= location && !dropped) {
            getWorld().addObject(new Bomb(), getX(), getY());
            dropped = true;
        }
        if(isAtEdge()) {
            getWorld().removeObject(this);
        }
    }
}
