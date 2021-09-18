import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Scoreboard extends Actor
{
    private static final int MAX_KILLS = 10;
    
    private int score;
    private String text;
    private int counter;
    
    public Scoreboard(String name, int starter)
    {
        // creates a canvas, sets font color to white, sets font family to Berlin Sans
        // and sets the image to the input fields ie. the name of the string and the 
        // starting value
        GreenfootImage img = new GreenfootImage(200,40);

        Color c = new Color(255,255,255);
        img.setColor(c);

        img.setFont( new Font( "Berlin Sans FB Demi",true,false,25) );
        img.drawString(name + ": " + starter,5,35);
        setImage(img);
        text = name;
        score = starter;
    }

    // changes the number of kills and changes to color green if reaches 10
    public void changeKills(int number) 
    {
        score = score + number;
        GreenfootImage img = getImage();
        img.clear();
        if(score < MAX_KILLS) {
            img.drawString(text + ": " + score,5,35);  
        } else {
            img.setColor(new Color(0,255,0));
            img.drawString(text + ": " + score,5,35); ;
        }
    }   

    // changes the number of stars left and changes to color red if reaches 0
    public void changeStars(int number) 
    {
        score = score + number;
        GreenfootImage img = getImage();
        img.clear();
        if(score > 0) {
            img.drawString(text + ": " + score,5,35);  
        } else {
            img.setColor(new Color(255,0,0));
            img.drawString(text + ": " + score,5,35);
        }
    }

    // changes the number of lives and changes to you died if reaches 0
    public void changeLives(int number) 
    {
        score = score + number;
        GreenfootImage img = getImage();
        img.clear();
        if(score > 0) {
            img.drawString(text + ": " + score,5,35);  
        } else {
            img.drawString("You Died!",5,35);
        }
    }
    
    // access the current value of lives, kills or stars
    public int getNum() {
        return score;
    }
}
