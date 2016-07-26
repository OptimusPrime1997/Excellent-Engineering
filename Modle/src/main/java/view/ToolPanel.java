package view;

import view.tools.CreateProjectAdapter;
import view.tools.FileChooseAdapter;
import view.tools.ToolButton;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.tree.TreePath;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

/**
 * Created by Administrator on 2016/7/20.
 */
public class ToolPanel extends JToolBar {
    private ToolButton jbtNew = new ToolButton("new");
    private ToolButton jbtLoad = new ToolButton("load");
    private ToolButton jbtRun = new ToolButton("run");
    private ToolButton jbtAdd = new ToolButton("add");

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
        jbtLoad.setBorderPainted(false);
        jbtRun.setBorderPainted(false);
        jbtAdd.setBorderPainted(false);

        jbtNew.addMouseListener(new CreateProjectAdapter("."+ File.separator+"ONE_PIECE",jFrame));

        jbtLoad.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                TreePath treePath = packetPanel.getSelectionPath();
                consolePane.loadApp(packetPanel.getFilePath(treePath)+File.separator+"model.xml");
                packetPanel.refreshTree();
            }
        });
        jbtRun.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                TreePath treePath = packetPanel.getSelectionPath();
                consolePane.runApp(packetPanel.getFilePath(treePath)+File.separator+"sketch");
                packetPanel.refreshTree();
            }
        });
        jbtAdd.addMouseListener(new FileChooseAdapter("."+File.separator+"ONE_PIECE",jFrame){
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                super.mouseClicked(e);
                packetPanel.refreshTree();

            }
        });
        this.add(jbtNew);
        this.add(jbtLoad);
        this.add(jbtRun);
        this.add(jbtAdd);
        this.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        this.jFrame = jFrame;

    }




}
