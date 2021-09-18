import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
/**
 * Write a description of class Bomb here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Bomb extends Actor
{
    private static final int SIZE = 70;
    private static final int GROUND_HEIGHT = 366;
    private static final int MAX_TIME = 50;
    private static final int EXPLOSION_RANGE = 150;
    private static final int DEBRIS_AMOUNT = 15;

    private boolean isMoving = true;
    private int timer = 0;

    /**
     * Act - do whatever the Bomb wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */

    public Bomb() { 
        // initialize 
        isMoving = true;
        timer = 0;

        getImage().scale(SIZE,SIZE);
    }

    public void act() 
    {
        if(isMoving) {
            move();
        }
        explode();
    }    
    
    // move down and rotate
    public void move() {
        setLocation(getX(), getY()+2);
        turn(5);
    }
    
    // stop moving when it hits the ground 
    // explode after timer goes off
    // play explosion sound
    // add explosion object
    // create a list of ninja and zombies in the rage of 200 pixels
    // kill all zombies found
    // if ninja found, reduce his life, if it's his last life kill him
    // play respective sounds
    // remove itslef after explosion
    public void explode() {
        if(getY() > GROUND_HEIGHT) {
            isMoving = false;
            timer++;
            if(timer == MAX_TIME) {
                Greenfoot.playSound("explosion.wav");
                getWorld().addObject(new Explosion(), getX(), getY());
                List<Ninja> ninja = getObjectsInRange(EXPLOSION_RANGE,Ninja.class);
                for(Ninja n : ninja) {
                    ((Graveyard)getWorld()).loseLives();
                    Greenfoot.playSound("ninja_hurt.wav");
                    for(int i = 0; i < DEBRIS_AMOUNT; i++) {
                        getWorld().addObject( new Debris("ninja"), n.getX(), n.getY() );
                    }
                    if(((Graveyard)getWorld()).getLives() == 0) {
                        Greenfoot.playSound("ninja_dead.wav");
                        getWorld().removeObject(n);
                    }
                }
                List<Zombie> zombies = getObjectsInRange(EXPLOSION_RANGE,Zombie.class);
                for(Zombie z : zombies) {
                    getWorld().removeObject(z);
                }
                getWorld().removeObject(this);
            }
        }
    }
}
