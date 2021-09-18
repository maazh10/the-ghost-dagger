import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Star here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Star extends Actor
{
    private static final int SPEED = 5;
    
    /**
     * Act - do whatever the Star wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public Star() {
        getImage().scale(30,30);
    }
    
    public void act() 
    {
        move();
    }    
    
    // move to the right and turn rapidly
    // remove itslef from the edge
    public void move() {
        setLocation(getX()+SPEED,getY());
        turn(SPEED);
        if(isAtEdge()) {
            getWorld().removeObject(this);
        }
    }
    
}