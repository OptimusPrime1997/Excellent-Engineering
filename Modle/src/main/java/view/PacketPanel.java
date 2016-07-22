package view;

import view.resources_manage.FileList;
import view.resources_manage.FileTree;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Administrator on 2016/7/20.
 */
public class PacketPanel extends JPanel {
    private JScrollPane treeView;
    private FileTree tree;
    private TextPanel textPanel;
    private ConsolePane consolePane;

    public PacketPanel(JFrame jframe , TextPanel textPanel ,ConsolePane consolePane){
        this.setSize(jframe.getWidth()/4,jframe.getHeight()/20*14);
        this.setLocation(0,jframe.getHeight()/20);
        this.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        this.setLayout(new BorderLayout());
        this.textPanel = textPanel;
        FileList list = new FileList();
        tree = new FileTree(list);
        tree.addMouseListener(new TreeListener());
        tree.setRootVisible(false);
        //tree.setDoubleBuffered(true);
        //list.setDoubleBuffered(true);
        treeView = new JScrollPane(tree);
        treeView.setSize(jframe.getWidth()/4,jframe.getHeight()/5*4);
        treeView.setLocation(0,0);
        this.add(BorderLayout.CENTER,treeView);
        System.out.println(treeView.getSize());
        System.out.println(this.getSize());
        this.consolePane = consolePane;
        //this.repaint();
    }

    public String getFilePath(TreePath path){
        String filePath = "C://";
        Object[] p =path.getPath();
        if(!p[p.length-1].toString().contains(".")){
            return null;
        }
        for(int x = 0 ; x < p.length - 1 ; x++){
            filePath += p[x].toString() + '/';
        }
        filePath += p[p.length - 1].toString();
        return filePath;
    }

    class TreeListener implements MouseListener{
        TreePath path;
        @Override
        public void mouseClicked(MouseEvent e) {
            path = tree.getSelectionPath();
            if(e.getClickCount() != 2){
                return;
            }
            if(path == null){
                return;
            }
            textPanel.openFile(getFilePath(path));
        }

        @Override
        public void mousePressed(MouseEvent e) {


        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if(e.isPopupTrigger()) {
                JPopupMenu popMenu = new JPopupMenu();
                JMenuItem menuLeafNode1 = new JMenuItem("         run        ");
                menuLeafNode1.addMouseListener(new TreeRunListener(path));
                menuLeafNode1.setFont(new Font("Consolas",Font.BOLD,12));
                popMenu.add(menuLeafNode1);
                popMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
    class TreeRunListener implements MouseListener{
        private TreePath treePath;
        public TreeRunListener(TreePath path){
            this.treePath = path;
        }
        @Override
        public void mouseClicked(MouseEvent e) {


        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {
            String path = getFilePath(treePath);
            System.out.println(path);
            if(path.contains(".xml")){
                consolePane.runXML(path);
            }else if(!path.contains(".")){
                consolePane.runApp(path);
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
}