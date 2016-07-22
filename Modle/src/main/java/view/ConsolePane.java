package view;

import javax.swing.*;
import javax.swing.border.EtchedBorder;

/**
 * Created by Administrator on 2016/7/21.
 */
public class ConsolePane extends JPanel {

    public ConsolePane(JFrame jframe){
        this.setSize(jframe.getWidth(),jframe.getHeight()/20*5);
        this.setLocation(0,jframe.getHeight()/20*15);
        this.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
    }
}
