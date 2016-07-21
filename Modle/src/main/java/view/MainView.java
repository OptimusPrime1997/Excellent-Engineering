package view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Administrator on 2016/7/20.
 */
public class MainView extends JFrame{
    private ToolPanel toolPanel;
    private PacketPanel packetPanel;
    private ResultPanel resultPanel;
    public MainView(){
        this.setTitle("ONE PIECE");
        this.setLayout(null);
        Dimension screenSize =Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screenSize.width/6*5,screenSize.height/6*5);
        this.setLocation(screenSize.width/2-this.getWidth()/2,screenSize.height/2-this.getHeight()/2);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        toolPanel = new ToolPanel(this);
        resultPanel = new ResultPanel(this);
        packetPanel = new PacketPanel(this,resultPanel);
        this.getContentPane().add(toolPanel);
        this.getContentPane().add(packetPanel);
        this.getContentPane().add(resultPanel);
        this.setVisible(true);
    }

    public static void main(String[] args){
        MainView mainView = new MainView();
    }
}
