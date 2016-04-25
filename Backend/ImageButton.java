import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class ImageButton extends JPanel implements ActionListener{

    
    JRadioButton button;
    JRadioButton button1;
    JRadioButton button2;
    JRadioButton button3;
    Color color;

    JButton b;
    ImageButton(){
        button = new JRadioButton("Red");
        button1 = new JRadioButton("Blue");
        button2 = new JRadioButton("Green");
        button3 = new JRadioButton("Pink");
        b = new JButton("Proceed");


        button.setBounds(50, 100, 150, 30);
        button1.setBounds(50, 150, 150, 30);
        button2.setBounds(50, 200, 150, 30);
        button3.setBounds(50, 250, 150, 30);
        b.setBounds(225, 300, 150, 30);
        b.addActionListener(this);
        ButtonGroup BG = new ButtonGroup();
        BG.add(button);
        BG.add(button1);
        BG.add(button2);
        BG.add(button3);
        add(b);
        add(button);
        add(button1);
        add(button2);
        add(button3);
        setSize(300,300);
        setLayout(null);
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e){
        if(button.isSelected()){
            JOptionPane.showMessageDialog(this, "Red");
            color = Color.RED;
        }
    	else if(button1.isSelected()){
            JOptionPane.showMessageDialog(this, "Blue");
            color = Color.BLUE;
        }
        else if(button2.isSelected()){
            JOptionPane.showMessageDialog(this, "Green");
            color = Color.GREEN;
        }
        else if(button3.isSelected()){
            JOptionPane.showMessageDialog(this, "Pink");
            color = Color.PINK;
        }
    }
    
    public Color getColor(){
        return color;
    }
    
    public void paintComponent(Graphics graphics){
        setBackground(Color.WHITE);
        graphics.setColor(Color.BLACK);
        graphics.drawString("Choose Colour", 250, 50);
        graphics.setColor(Color.RED);
        graphics.fillRoundRect(220, 100, 100, 30, 5, 5);
        graphics.setColor(Color.BLUE);
        graphics.fillRoundRect(220, 150, 100, 30, 5, 5);
        graphics.setColor(Color.GREEN);
        graphics.fillRoundRect(220, 200, 100, 30, 5, 5);
        graphics.setColor(Color.PINK);
        graphics.fillRoundRect(220, 250, 100, 30, 5, 5);
    }
    
    public static void main(String[] args){
        JFrame frame = new JFrame("Ping The Pong");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        
        ImageButton game = new ImageButton();
        frame.add(game, BorderLayout.CENTER);
        
        frame.setSize(600, 624);
        frame.setVisible(true);
        
    }
}
