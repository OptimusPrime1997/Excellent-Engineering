package view;

import javax.swing.*;
import javax.swing.border.EtchedBorder;

/**
 * Created by Administrator on 2016/7/20.
 */
public class ResultPanel extends JPanel {

    public ResultPanel(JFrame jFrame){

        this.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        this.setSize(jFrame.getWidth()/4*3,jFrame.getHeight()*7/6);
        this.setLocation(jFrame.getWidth()/4,jFrame.getHeight()/7);
    }
}
