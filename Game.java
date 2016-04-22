import java.util.*;
import java.lang.*;
import java.text.*;
import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game extends JPanel implements ActionListener, KeyListener{
    //Frame Constants
    public static int frameWidth = 600;
    public static int frameHeight = 600;
    private static String[] choice = {"Left", "Right"};
    //Paddle Constants
    public static int padWidth = 8;
    public static int padLength = 80;
    public static int halfpadLength = padLength / 2;
    public static int halfPadWidth = padWidth / 2;
    //Ball Variables
    public int ballRadius = 20;
    public int[] ballPosition = {frameWidth / 2, frameHeight / 2};
    public int[] ballVelocity = {0, 0};
    //Vertical Paddles
    public int[] paddle1Position = {halfPadWidth - 1, frameHeight / 2};
    public int[] paddle2Position = {frameWidth - halfPadWidth + 1, frameHeight / 2};
    public int[] paddle1Velocity = {0, 0};
    public int[] paddle2Velocity = {0, 0};
    //Horizontal Paddles
    public int[] paddle3Position = {frameWidth / 2, halfPadWidth - 1};
    public int[] paddle4Position = {frameWidth / 2, frameHeight - halfPadWidth + 1};
    public int[] paddle3Velocity = {0, 0};
    public int[] paddle4Velocity = {0, 0};
    //Scores
    public int score1 = 0;
    public int score2 = 0;
    public int score3 = 0;
    public int score4 = 0;
    private int randomChoice;
    Game() {
        score1 = 0;
        score2 = 0;
        score3 = 0;
        score4 = 0;
        Random random = new Random();
        randomChoice = random.nextInt(choice.length);
        spawn_ball(choice[randomChoice]);
        addKeyListener(this);
        javax.swing.Timer timer = new javax.swing.Timer(100, this);
        timer.start();
    }
    
    public void spawn_ball(String randomChoice){
        ballPosition[0] = frameWidth / 2;
        ballPosition[1] = frameHeight / 2;
        Random random = new Random();
        if(randomChoice.equals("Left")){
            ballVelocity[0] = -1 * random.nextInt(2) - 2;
            ballVelocity[1] = -1 * random.nextInt(2) - 1;
        }
        else if(randomChoice.equals("Right")){
            ballVelocity[0] = random.nextInt(2) - 2;
            ballVelocity[1] = -1 * random.nextInt(2) - 1;
        }
    }
    
    public void actionPerformed(ActionEvent e){
        //TODO : Perform Checks on ball and Paddles
        //TODO : if ball goes out of bounds, call spawn_ball() such that initial velocity of the ball is towards the winner of the last point; function and update scores
        //TODO : Update Position of ball
        //TODO : Update Position of Paddles; keep paddles inside the board
        //TODO : Call repaint() function
    }
    
    public void paintComponent(Graphics graphics){
        setBackground(Color.BLACK);
        
        graphics.setColor(Color.BLUE);
        
        //Drawing Lines on board
        graphics.drawLine(frameWidth / 2, 0, frameWidth / 2, frameHeight);
        graphics.drawLine(padWidth, 0, padWidth, frameHeight);
        graphics.drawLine(frameWidth - padWidth, 0, frameWidth - padWidth, frameHeight);
        graphics.drawLine(0, padWidth, frameWidth, padWidth);
        graphics.drawLine(0, frameHeight - padWidth, frameWidth, frameHeight - padWidth);
        
        //Drawing Balls
        graphics.fillOval(ballPosition[0] - ballRadius, ballPosition[1] - ballRadius, 2 * ballRadius, 2 * ballRadius);
        
        //Drawing Paddles
        
        //Vertical Paddles
        graphics.fillRect(paddle1Position[0] - padWidth / 2, paddle1Position[1] - padLength / 2, padWidth, padLength);
        graphics.fillRect(paddle2Position[0] - padWidth / 2, paddle2Position[1] - padLength / 2, padWidth, padLength);
        
        //Horizontal Paddles
        graphics.fillRect(paddle3Position[0] - padLength / 2, paddle3Position[1] - padWidth / 2, padLength, padWidth);
        graphics.fillRect(paddle4Position[0] - padLength / 2, paddle4Position[1] - padWidth / 2, padLength, padWidth);
        
        graphics.setColor(Color.WHITE);
        
        //Writing Scores on board
        graphics.drawString(Integer.toString(score1), 150, 150);
        graphics.drawString(Integer.toString(score2), 450, 150);
        graphics.drawString(Integer.toString(score1), 150, 450);
        graphics.drawString(Integer.toString(score2), 450, 450);
    }
    
    public void keyTyped(KeyEvent e) {} //DON'T DO ANYTHING HERE
    
    public void keyPressed(KeyEvent e){
        //TODO : Update on key press events
    }
    
    public void keyReleased(KeyEvent e){
        //TODO : Update on key released events
    }
    
    public static void main(String[] args){
        
        JFrame frame = new JFrame("Ping The Pong");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        
        Game game = new Game();
        frame.add(game, BorderLayout.CENTER);
        
        frame.setSize(frameWidth, 624);
        frame.setVisible(true);
        
    }
}