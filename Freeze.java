import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Freeze here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Freeze extends Actor
{
    private static final int SPEED = 2;
    
    /**
     * Act - do whatever the Freeze wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    
    public Freeze() {
        getImage().scale(110,60);
    }
    
    public void act() 
    {
        move();
    }    
    
    // move down and remove if touching ground
    public void move() {
        setLocation(getX(), getY()+SPEED);
        if(isTouching(Ground.class)) {
            getWorld().removeObject(this);
        }
    }
}