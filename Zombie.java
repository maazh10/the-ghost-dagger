import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
/**
 * Write a description of class Zombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Zombie extends Actor
{
    private static final int SPEED = 2;
    private static final int ITER_COUNT = 5;
    private static final int DEBRIS_AMOUNT = 7;
    private static final int FREEZE_DURATION = 300;

    private GreenfootImage[] walk = new GreenfootImage[10];
    private GreenfootImage[] die = new GreenfootImage[10];
    private GreenfootImage[] attack = new GreenfootImage[8];
    private int iIndex = 0;
    private int imageCounter = 0;
    private boolean isMoving = true;
    private boolean remove = false;
    private int freezeCounter = 0;
    private int attackIndex = 1;
    private boolean isHit = false;

    /**
     * Act - do whatever the Zombie wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public Zombie() {
        // initialize
        iIndex = 0;
        imageCounter = 0;
        isMoving = true;
        remove = false;
        freezeCounter = 0;
        attackIndex = 1;
        isHit = false;

        // fill the animation arrays for waling, dying, and attacking with designated images
        for(int i = 1; i < walk.length; i++) {
            walk[i] = new GreenfootImage("Walk (" + i + ").png");
            walk[i].scale(100,115);
            walk[i].mirrorHorizontally();
        }
        for(int i = 1; i < die.length; i++) {
            die[i] = new GreenfootImage("Dead (" + i + ").png");
            die[i].scale(130,130);
            die[i].mirrorHorizontally();
        }
        for(int i = 1; i < attack.length; i++) {
            attack[i] = new GreenfootImage("Attack (" + i + ").png");
            attack[i].scale(100,115);
            attack[i].mirrorHorizontally();
        }
        
        // set the initial image
        setImage("Walk (1).png");
    }

    public void act() 
    {
        if(isMoving) {
            walk();
            attack();
        } else {
            release();
        }
        die();
        if(remove) {
            getWorld().removeObject(this);
        }
    }    
    
    // walk across the screen right to left while iterating through the walk array for animation every 5 iterations
    public void walk() {
        setLocation(getX()-SPEED, getY());
        imageCounter++;
        if(imageCounter % ITER_COUNT == 0) {
            iIndex++;
        }
        if(iIndex == walk.length) {
            iIndex = 1;
        }
        setImage(walk[iIndex]);
        if(isAtEdge()) {
            remove = true;
        }
    }
    
    // if found a Ninja
    // do the attack animation
    // play hurt sound
    // add a few debris behind ninja
    // reduce lives of ninja 
    // if it's his last life remove ninja from the world
    public void attack() {
        Ninja touchingNinja = (Ninja)getOneIntersectingObject(Ninja.class);
        if(touchingNinja != null) {
            imageCounter++;
            if(imageCounter % ITER_COUNT == 0) {
                attackIndex++;
            }
            if(attackIndex == attack.length-1) {
                attackIndex = 1;
                Greenfoot.playSound("ninja_hurt.wav");
                remove = true;
                ((Graveyard)getWorld()).loseLives();
                for(int i = 0; i < DEBRIS_AMOUNT; i++) {
                    getWorld().addObject( new Debris("ninja"), touchingNinja.getX(), touchingNinja.getY() );
                }
                if(((Graveyard)getWorld()).getLives() == 0) {
                    getWorld().removeObject(touchingNinja);
                }
            }
            setImage(attack[attackIndex]);
        }
    }

    // if encountered a star
    // play dead sound
    // do the dead animation
    // remove itself 
    // add to ninja's kills
    public void die() {
        Star touchingStar = (Star)getOneIntersectingObject(Star.class);
        if(touchingStar != null) {
            getWorld().removeObject(touchingStar);
            isHit = true;
            iIndex = 1;
            imageCounter = 0;
        }
        if(isHit) {
            imageCounter++;
            if(imageCounter % ITER_COUNT == 0) {
                iIndex++;
            }
            if(iIndex == die.length) {
                isHit = false;
                iIndex = 1;
                remove = true;
                ((Graveyard)getWorld()).addToKills();
                Greenfoot.playSound("zombie_death.wav");
            }
            setImage(die[iIndex]);
        }
    }

    // stop moving, i.e. freeze
    public void stop() {
        isMoving = false;
    }
    
    // if frozen, start moving after the given duration
    public void release() {
        freezeCounter++;
        if(freezeCounter == FREEZE_DURATION) {
            isMoving = true;
            freezeCounter = 0;
        }
    }

}