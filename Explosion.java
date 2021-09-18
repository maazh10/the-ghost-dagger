import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)

/**
 * An explosion. It starts by expanding and then collapsing. 
 * The explosion will explode other obejcts that the explosion intersects.
 * 
 * @author Poul Henriksen
 * @version 1.0.1
 */
public class Explosion extends Actor
{
    private static final int SIZE_MULTIPLIER = 12;
    private static final int SPIN_AMOUNT = 10;
    
    private GreenfootImage[] images = new GreenfootImage[20];

    private int imageIndex = 0;
    private int factor = 1;

    public Explosion() 
    {
        // initialize
        factor = 1;
        imageIndex = 0;
        
        // fill the array images with 20 different sizes of the explosion image
        int size = 0;
        for(int i = 1; i < images.length; i++) {
            size = i*SIZE_MULTIPLIER;
            images[i] = new GreenfootImage("explosion.png");
            images[i].scale(size,size);
        }
        // set the image to the first image in the array 
        setImage(images[1]);
    }    
    
    public void act()
    { 
        explode();
    }
    
    // create an explosion animation by turning the object constantly and setting the image by iterating through the images array 
    // once it reaches the highest size of explosion (last index) it then shrinks back down
    public void explode() {
        turn(SPIN_AMOUNT);
        imageIndex = imageIndex + factor;
        if(imageIndex == images.length-1) {
            factor = -factor;
        }
        if(imageIndex < 1) {
            getWorld().removeObject(this);
        }
        setImage(images[imageIndex]);
    }
}
