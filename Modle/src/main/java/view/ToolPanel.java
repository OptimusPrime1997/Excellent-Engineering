package view;

import view.tools.ToolButton;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;

/**
 * Created by Administrator on 2016/7/20.
 */
public class ToolPanel extends JToolBar {
    private ToolButton jbtNew = new ToolButton("new");
    private ToolButton jbtOpen = new ToolButton("open");
    private ToolButton jbtPrint = new ToolButton("print");
    public ToolPanel(JFrame jFrame){
        this.setBounds(0,0,jFrame.getWidth(),jFrame.getHeight()/20);
        jbtNew.setBorderPainted(false);
        jbtOpen.setBorderPainted(false);
        jbtPrint.setBorderPainted(false);
        this.add(jbtNew);
        this.add(jbtOpen);
        this.add(jbtPrint);
        this.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
    }
}
