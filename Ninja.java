import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Ninja here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Ninja extends Actor
{
    private static final int SPEED = 2;
    private static final int ITER_COUNT = 5;
    private static final int ATTACK_ITER_COUNT = 3;
    private static final int THROW_ITER_COUNT = 7;
    private static final int ATTACK_ANIM_FINISH = 20;
    private static final int THROW_ANIM_FINISH = 15;
    private static final int FREEZE_DURATION = 300;

    private GreenfootImage[] idle = new GreenfootImage[10];
    private GreenfootImage[] right = new GreenfootImage[10];
    private GreenfootImage[] left = new GreenfootImage[10];
    private GreenfootImage[] attack = new GreenfootImage[10];
    private GreenfootImage[] star = new GreenfootImage[10];

    private int iIndex = 0;
    private int imageCounter = 0;
    private int attackImageCounter = 0;
    private int attackImageIndex = 0;
    private int attackCount = 0;
    private boolean justAttacked = false;
    private int throwImageCounter = 0;
    private int throwImageIndex = 0;
    private int throwCount = 0;
    private boolean justThrew = false;
    private boolean freeze = false;
    private int fCount = 0;
    private boolean notEnoughStars = false;
    private boolean played = false;
    
    /**
     * Act - do whatever the Ninja wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */

    public Ninja() {
        // initialize
        iIndex = 0;
        imageCounter = 0;
        attackImageCounter = 0;
        attackCount = 0;
        justAttacked = false;
        throwImageCounter = 0;
        throwImageIndex = 0;
        throwCount = 0;
        justThrew = false;
        freeze = false;
        fCount = 0;
        notEnoughStars = false;
        
        // fill the arrays for standing idle, moving right, moving left, attacking and throwing with relevant images
        for(int i = 0; i < idle.length; i++) {
            idle[i] = new GreenfootImage("Idle__00" + i + ".png");
            idle[i].scale(70,120);
        }
        for(int i = 0; i < right.length; i++) {
            right[i] = new GreenfootImage("Run__00" + i + ".png");
            right[i].scale(100,120);
        }
        for(int i = 0; i < left.length; i++) {
            left[i] = new GreenfootImage("Run__00" + i + ".png");
            left[i].mirrorHorizontally();
            left[i].scale(100,120);
        }
        for(int i = 0; i < attack.length; i++) {
            attack[i] = new GreenfootImage("Attack__00" + i + ".png");
            attack[i].scale(150,140);
        }
        for(int i = 0; i < star.length; i++) {
            star[i] = new GreenfootImage("Throw__00" + i + ".png");
            star[i].scale(110,120);
        }
        // set initial image
        setImage("Idle__000.png");
        getImage().scale(70,120);
    }

    public void act() 
    {
        if(Greenfoot.isKeyDown("right")) {
            moveRight();
            attack();
        } else if(Greenfoot.isKeyDown("left")) {
            moveLeft();
        } else {
            stand();
            throwStar();
            attack();
        }
        freezePowerUp();
    }    
    
    // if right key pressed
    // move right while animating from the array right
    public void moveRight() {
        setLocation(getX()+SPEED, getY());
        imageCounter++;
        if(imageCounter % ITER_COUNT == 0) {
            iIndex++;
        }
        if(iIndex == right.length-1) {
            iIndex = 0;
        }
        setImage(right[iIndex]);
    }
    
    // left key pressed
    // move left while animating from the array left
    public void moveLeft() {
        setLocation(getX()-SPEED, getY());
        imageCounter++;
        if(imageCounter % ITER_COUNT == 0) {
            iIndex++;
        }
        if(iIndex == left.length-1) {
            iIndex = 0;
        }
        setImage(left[iIndex]);
    }
    
    // stand still while animating from the array idle
    public void stand() {
        imageCounter++;
        if(imageCounter % ITER_COUNT == 0) {
            iIndex++;
        }
        if(iIndex == idle.length-1) {
            iIndex = 0;
        }
        setImage(idle[iIndex]);
    }
    
    //if space pressed
    // attack with attack animations and sound and with cooldown not allowing to attack while holding space
    // after animating if found zombie, remove the zombie, increase kills, play sound
    public void attack() {
        if(Greenfoot.isKeyDown("space") && justAttacked == false) {  
            attackImageCounter++;
            if(attackImageCounter % ATTACK_ITER_COUNT == 0) {
                attackImageIndex++;
            }
            if(attackImageIndex == attack.length-1) {
                attackImageIndex = 0;
            }
            setImage(attack[attackImageIndex]);

            attackCount++;
            if(attackCount == ATTACK_ANIM_FINISH) {
                Zombie z = (Zombie)getOneIntersectingObject(Zombie.class);
                if(z != null) {
                    getWorld().removeObject(z);
                    Greenfoot.playSound("zombie_death.wav");
                    for(int i = 0; i < 25; i++) {
                        getWorld().addObject( new Debris("zombie"), getX(), getY() );
                    }
                    ((Graveyard)getWorld()).addToKills();
                }
                justAttacked = true;
                attackCount = 0;
                attackImageIndex = 0;
                Greenfoot.playSound("sword_swing.wav");
            }
        }
        if(!Greenfoot.isKeyDown("space")) {
            justAttacked = false;
        }
    }
    
    // throw a ninja star if z is pressed and we have enough stars
    // animate the throwing and decrease a star after each throw
    // don't throw if out of stars
    // play relevant sounds
    public void throwStar() {
        if(!notEnoughStars) {
            if(Greenfoot.isKeyDown("z") && !justThrew) {
                throwCount++;
                throwImageCounter++;
                if(throwImageCounter % THROW_ITER_COUNT == 0) {
                    throwImageIndex++;
                }
                if(throwImageIndex == star.length-1) {
                    throwImageIndex = 0;
                }
                setImage(star[iIndex]);
                if(throwCount == THROW_ANIM_FINISH) {
                    justThrew = true;
                    throwCount = 0;
                    Greenfoot.playSound("throw_star.wav");
                    getWorld().addObject(new Star(), getX(), getY());
                    ((Graveyard)getWorld()).decreaseStars();
                }
            }
            if(!Greenfoot.isKeyDown("z")) {
                throwImageIndex = 0;
                justThrew = false;
            }
            if(((Graveyard)getWorld()).getStars() == 0) {
                notEnoughStars = true;
            }
        }
        if(Greenfoot.isKeyDown("z") && notEnoughStars && !played) {
            Greenfoot.playSound("no_stars.mp3");
            played = true;
        } 
        if(!Greenfoot.isKeyDown("z")) {
            played = false;
        }
    }
    
    // if collected freeze powerup 
    // play sound
    // freeze zombies through the method in the world
    // stop freeze effect after given duration
    public void freezePowerUp() {
        if(isTouching(Freeze.class)) {
            Greenfoot.playSound("freeze.wav");
            ((Graveyard)getWorld()).freezeZombies();
            freeze = true;
            removeTouching(Freeze.class);
        }
        fCount++;
        if(fCount % FREEZE_DURATION == 0) {
            freeze = false;
        }
    }

    // return if zombies are currently frozen or not
    public boolean getFreeze() {
        return freeze;
    }
}