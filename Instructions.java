import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Instructions here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Instructions extends World
{
    /**
     * Constructor for objects of class Instructions.
     * 
     */
    public Instructions()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(700, 500, 1); 
    }
    
    public void act() {
       // if pressed space, switch to graveyard world
       if(Greenfoot.isKeyDown("space")) {
            Greenfoot.setWorld(new Graveyard());
       } 
    }
}
