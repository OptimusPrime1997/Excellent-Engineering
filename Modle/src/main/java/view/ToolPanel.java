package view;

import view.tools.CreateProjectAdapter;
import view.tools.ToolButton;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Administrator on 2016/7/20.
 */
public class ToolPanel extends JToolBar {
    private ToolButton jbtNew = new ToolButton("new");
    private ToolButton jbtOpen = new ToolButton("load");
    private ToolButton jbtPrint = new ToolButton("run");

    private JFrame jFrame;
    private ConsolePane consolePane;
    private TextPanel textPanel;
    private PacketPanel packetPanel;
    public ToolPanel(JFrame jFrame, final ConsolePane consolePane, TextPanel textPanel, final PacketPanel packetPanel){
        this.consolePane = consolePane;
        this.textPanel = textPanel;
        this.packetPanel = packetPanel;
        this.setBounds(0,0,jFrame.getWidth(),jFrame.getHeight()/20);
        jbtNew.setBorderPainted(false);
        jbtOpen.setBorderPainted(false);
        jbtPrint.setBorderPainted(false);
        jbtNew.addMouseListener(new CreateProjectAdapter("C://ONE_PIECE",jFrame));
        jbtOpen.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                TreePath treePath = packetPanel.getSelectionPath();
                consolePane.loadApp(packetPanel.getFilePath(treePath)+"/model.xml");
            }
        });
        jbtPrint.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                TreePath treePath = packetPanel.getSelectionPath();
                consolePane.runApp(packetPanel.getFilePath(treePath)+"/sketch");
            }
        });
        this.add(jbtNew);
        this.add(jbtOpen);
        this.add(jbtPrint);
        this.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        this.jFrame = jFrame;

    }




}
