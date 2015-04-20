/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bounce;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


/**
 *
 * @author Saumyajit
 */
public class Bounce extends JFrame  
{
    JRootPane pnl = getRootPane();
    static int xpos=0;
    static int ypos=0;
    static Ball ball=new Ball();
    static Button button=new Button();
    static ResetBtn resetBtn=new ResetBtn();
    static JSlider power=new JSlider();
    static JSlider angle=new JSlider();
    static PlayAgain playAgain=new PlayAgain();
    static boolean clicked=false;
    static double radian=Math.toRadians(45);
    static double v=100;
    static double g=10;
    static double target=0;
    static boolean reset=false;
    static boolean again=false;
    static double range=(v*v)/(2*g);
    static boolean running=false;
    static double x=0;
    static double y=0;
    
    
    Bounce()
    {
        super("Cannon");
        setSize(1000,1000);
        
        setResizable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(ball);
        add(button);
        power.setToolTipText("Power");
        angle.setMinimum(1);
        angle.setValue(45);
        angle.setMaximum(89);
        add(power);
        add(angle);
        angle.setToolTipText("Angle");
        setVisible(true);
        add(resetBtn);
        add(playAgain);
        
        power.setEnabled(false);
        angle.setEnabled(false);
        pnl.requestFocus();
        
        
        Action action=new AbstractAction("Fire") {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                    if(xpos==0)
                        clicked=true;
            }
        };
        
        Action left=new AbstractAction("Left") {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                    if(power.getValue()>1)
                    {
                        power.setValue(power.getValue()-1);
                        ball.repaint();
                    }
            }
        };
        
        
        Action right=new AbstractAction("Right") {
            @Override
            public void actionPerformed(ActionEvent e) {
                    
                    if(power.getValue()<99)
                    {
                        power.setValue(power.getValue()+1);
                        ball.repaint();
                    }
            }
        };
        
        Action up=new AbstractAction("Up") {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                System.out.println("up pressed");
                if(angle.getValue()<89)
                {
                    angle.setValue(angle.getValue()+1);
                    ball.repaint();
                }
                    
                
            }
        };
        
        Action down=new AbstractAction("Down") {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                if(angle.getValue()>10)
                {
                    angle.setValue(angle.getValue()-1);
                    ball.repaint();
                }
                    
                    
                
            }
        };
        
        KeyStroke keyStroke = KeyStroke.getKeyStroke((char)KeyEvent.VK_SPACE);

        KeyStroke upArrow=KeyStroke.getKeyStroke(KeyEvent.VK_UP,0);
        KeyStroke downArrow=KeyStroke.getKeyStroke(KeyEvent.VK_DOWN,0);
        
        KeyStroke leftArrow=KeyStroke.getKeyStroke(KeyEvent.VK_LEFT,0);
        KeyStroke rightArrow=KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT,0);
        
        // Register Action in component's ActionMap.
        pnl.getActionMap().put("Fire", action);
        pnl.getActionMap().put("Up",up);
        pnl.getActionMap().put("Down", down);
        pnl.getActionMap().put("Left",left);
        pnl.getActionMap().put("Right",right);
        
        // Now register KeyStroke used to fire the action.  I am registering this with the
        // InputMap used when the component's parent window has focus.
        pnl.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, "Fire");
        pnl.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(upArrow, "Up");
        pnl.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(downArrow, "Down");
        pnl.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(leftArrow, "Left");
        pnl.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(rightArrow, "Right");
        
    }
    public static class ResetBtn extends JButton implements ActionListener
    {
        
        ResetBtn()
        {
            super("Reset");
            addActionListener(this);
        }
        
        

        @Override
        public void actionPerformed(ActionEvent e)
        {
            xpos=0;
            ypos=0;
            running=false;
            this.getRootPane().requestFocus();
            power.setValue(50);
            ball.repaint();
            angle.setValue(45);
        }
    }
    
    public static class PlayAgain extends JButton implements ActionListener
    {
        
        PlayAgain()
        {
            super("Play Again");
            addActionListener(this);
        }
        
        

        @Override
        public void actionPerformed(ActionEvent e)
        {
            target=Math.random();
            while(target*1000<200 || target*1000 >range)
                target=Math.random();
            target=1000*target;
            clicked=false;
            running=false;
            power.setValue(50);
            xpos=0;
            ypos=0;
            again=!again;
            this.getRootPane().requestFocus();
            ball.repaint();
            angle.setValue(45);
        }
    }
    
    
    
    
    
    
    
    public static class Ball extends JPanel implements ActionListener
    {
        
        public void paintComponent(Graphics g)
        {
            
            g.clearRect(0, 0,getWidth(), getHeight());
            g.setColor(Color.black);
            double theta=angle.getValue();
            theta=Math.toRadians(theta);
            g.drawLine(0, 690, (int)target+20, 690);
            g.fillOval(xpos,650-ypos,40,40);
            g.setColor(Color.red);
            
            double x=0;
            
            double y=0;
            v=power.getValue();
            radian=Math.toRadians(angle.getValue());
            double tan=Math.tan(radian);
            double cos=Math.cos(radian);
            int ctr=0;
            while(true)
            {
                ctr++;
                double temp=x*tan-(x*x*10)/(2*v*v*cos*cos);
                
                g.drawString(".", (int)x, 690-(int)y);
                y=(int)((temp));
                x++;
                if(x>4 && y<=0)
                {
                    break;  
                }
            }
            g.fillOval((int)target,670,20,20);   
            String instructions ="Press Left/Right Arrow to increase/decrease power.  Press Up/Down Arrow to increase/decrease angle.  Press Space to fire.";
            Font myFont = new Font("Serif", Font.BOLD, 16);
            g.setFont(myFont);
            g.drawString(instructions,20, 50);
            
        }
        

        @Override
        public void actionPerformed(ActionEvent e) {
           throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    
    
    }
    
    public static class Button extends JButton implements ActionListener
    {
        
        Button()
        {
            super("Fire");
            addActionListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            this.getRootPane().requestFocus();
            clicked=true;
        }
        
        
        
    }
    
    public static void play()throws InterruptedException
    {
        target=Math.random();
        
        while(target*1000<200 || target*1000 >range)
            target=Math.random();
        System.out.println("Target = "+target);
        target=1000*target;    
        
        
        Bounce b=new Bounce();
        int ctr=0;
        
        while(true)
        {
            if(!clicked)
            {
                v=power.getValue();
                radian=Math.toRadians(angle.getValue());
                
            }
            if(clicked==true)
            {
                running=true;
                Thread.sleep(4);
                xpos++;
                double tan=Math.tan(radian);
                double cos=Math.cos(radian);
                double temp=xpos*tan-(g*xpos*xpos)/(2*v*v*cos*cos);
                ypos=(int)((temp));
                ball.repaint();
                ctr++;
                System.out.println(ypos);
                if(xpos>1 && ypos<=0)
                {
                    
                    clicked=false;
                    System.out.println("Final = "+xpos);
                    if(xpos>=target-30 && xpos<=target+30)
                    {
                        JOptionPane.showMessageDialog(null, "Target Reached!!");
                        clicked=false;
                    }
                }
                
                
            }
            System.out.print(' ');
       }
    }
    
    
    public static void main(String[] args) throws InterruptedException
    {
        play();
        
    }
    
    
    
    
    
    
    
    
    
    
}
            