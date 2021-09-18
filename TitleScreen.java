import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class TitleScreen here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TitleScreen extends World
{
    private GreenfootSound bg;
    
    /**
     * Constructor for objects of class TitleScreen.
     * 
     */
    public TitleScreen()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(700, 500, 1); 
        
        // store the background music inside bg
        bg = new GreenfootSound("menu_music.mp3");
        // set background music volume to 100
        bg.setVolume(100);
        // play it in a loop
        bg.playLoop();
    }

    public void act() {
        // if pressed space, switch to graveyard world and lower volume to 75
        if(Greenfoot.isKeyDown("space")) {
            Greenfoot.setWorld(new Graveyard());
            bg.setVolume(75);
        } 
        // if pressed i, switch to instructions world and lower volume to 75
        if(Greenfoot.isKeyDown("i")) {
            Greenfoot.setWorld(new Instructions());
            bg.setVolume(75);
        } 
    }
}
