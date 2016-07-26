package view;

import view.tools.CreateProjectAdapter;
import view.tools.FileChooseAdapter;
import view.tools.ToolButton;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.tree.TreePath;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

/**
 * Created by Administrator on 2016/7/20.
 */
public class ToolPanel extends JToolBar {
    private ToolButton jbtNew = new ToolButton("new");
    private ToolButton jbtLoad = new ToolButton("load");
    private ToolButton jbtBuild = new ToolButton("build");
    private ToolButton jbtAdd = new ToolButton("add");
    private ToolButton jbtCreate = new ToolButton("create script");

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
        jbtBuild.setBorderPainted(false);
        jbtAdd.setBorderPainted(false);
        jbtCreate.setBorderPainted(false);

        jbtNew.addMouseListener(new CreateProjectAdapter("."+ File.separator+"ONE_PIECE",jFrame));

        jbtLoad.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                TreePath treePath = packetPanel.getSelectionPath();
                String path = getPackegePath(treePath);
                if(path == null){
                    JOptionPane.showMessageDialog(null,"please choose the app you want to test");
                    return;
                }
                consolePane.loadApp(path+"model.xml");
                packetPanel.refreshTree();
            }
        });
        jbtBuild.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                TreePath treePath = packetPanel.getSelectionPath();
                String path = getPackegePath(treePath);
                if(path == null){
                    JOptionPane.showMessageDialog(null,"please choose the app you want to test");
                    return;
                }
                consolePane.runApp(path+"sketch");
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
        jbtCreate.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent e) {
                TreePath treePath = packetPanel.getSelectionPath();
                String path = getPackegePath(treePath);
                if(path == null){
                    JOptionPane.showMessageDialog(null,"please choose the app you want to test");
                    return;
                }
                consolePane.createScript(path);
            }
        });
        this.add(jbtNew);
        this.add(jbtLoad);
        this.add(jbtBuild);
        this.add(jbtAdd);
        this.add(jbtCreate);
        this.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        this.jFrame = jFrame;

    }


    private String getPackegePath(TreePath path){
        if(path == null){
            return null;
        }
        Object[] obj = path.getPath();
        String filePath = "." + File.separator;
        for(int x = 0 ; x < 2 ; x ++){
            filePath += obj[x].toString() + File.separator;
        }
        return filePath;
    }


}
