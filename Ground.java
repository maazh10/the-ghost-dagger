import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Ground here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Ground extends Actor
{
    /**
     * Act - do whatever the Ground wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public Ground(int type) {
        // set the image to one of the 3 types of ground specified
        if(type == 1) {
            setImage("Tile (1).png");
        } else if(type == 2) {
            setImage("Tile (2).png");
        } else if(type == 3) {
            setImage("Tile (3).png");
        }
    }
    
    public void act() 
    {
        // Add your action code here.
    }    
}
