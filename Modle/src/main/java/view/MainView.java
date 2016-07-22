package view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Administrator on 2016/7/20.
 */
public class MainView extends JFrame{
    private ToolPanel toolPanel;
    private PacketPanel packetPanel;
    private TextPanel textPanel;
    private ConsolePane consolePane;

    public MainView(){
        this.setTitle("ONE PIECE");
        this.setLayout(new GridBagLayout());
        Dimension screenSize =Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screenSize.width/6*5,screenSize.height/6*5);
        this.setLocation(screenSize.width/2-this.getWidth()/2,screenSize.height/2-this.getHeight()/2);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        toolPanel = new ToolPanel(this);
        textPanel = new TextPanel(this);
        packetPanel = new PacketPanel(this, textPanel);
        consolePane = new ConsolePane(this);


        this.getContentPane().add(toolPanel,this.initToolPanelPosition());
        this.getContentPane().add(packetPanel,this.initPacketPanelPosition());
        this.getContentPane().add(textPanel,this.initTextPanelPosition());
        this.getContentPane().add(consolePane,this.initConsolePane());

        this.setVisible(true);

    }

    private GridBagConstraints initToolPanelPosition(){
        GridBagConstraints toolPos = new GridBagConstraints();
        toolPos.fill = toolPos.BOTH;
        toolPos.gridwidth = 2;
        toolPos.gridheight = 1;
        toolPos.gridx = 0;
        toolPos.gridy = 0;
        toolPos.ipadx = this.getWidth();
        toolPos.ipady = this.getHeight()/30;

        return toolPos;
    }

    private GridBagConstraints initPacketPanelPosition(){
        GridBagConstraints packetPos = new GridBagConstraints();
        packetPos.fill = packetPos.BOTH;
        packetPos.gridwidth = 1;
        packetPos.gridheight = 1;
        packetPos.gridx = 0;
        packetPos.gridy = 1;
        //packetPos.weighty = this.getHeight() / 30 * 18;
       // packetPos.weightx = this.getWidth() / 4;
        packetPos.ipady = this.getHeight() / 30 * 21;
        packetPos.ipadx = this.getWidth() / 4;
        return packetPos;
    }

    private GridBagConstraints initTextPanelPosition(){
        GridBagConstraints textPos = new GridBagConstraints();
        textPos.fill = textPos.BOTH;
        textPos.gridwidth = 1;
        textPos.gridheight = 1;
        textPos.gridx = 1;
        textPos.gridy = 1;
        textPos.weighty = this.getHeight() / 30 * 21;
        textPos.weightx = this.getWidth() / 4 * 3;
        return textPos;
    }

    private GridBagConstraints initConsolePane(){
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill= constraints.BOTH;
        constraints.gridwidth = 2;
        constraints.gridheight = 1;
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.ipady = this.getHeight() / 30 * 8;
        constraints.ipadx = this.getWidth();
        return constraints;
    }
    public static void main(String[] args){
        MainView mainView = new MainView();
    }
}
