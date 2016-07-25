package view;

import view.resources_manage.FileList;
import view.resources_manage.FileTree;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.MouseAdapter;
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
        tree.setDoubleBuffered(true);
        list.setDoubleBuffered(true);
        treeView = new JScrollPane(tree);
        treeView.setSize(jframe.getWidth()/4,jframe.getHeight()/5*4);
        treeView.setLocation(0,0);
        this.add(BorderLayout.CENTER,treeView);
        //System.out.println(treeView.getSize());
        //System.out.println(this.getSize());
        this.consolePane = consolePane;
    }
    public void refreshTree(){
        tree.refreshTree();
    }
    public String getFilePath(TreePath path){
        String filePath = "C://";
        Object[] p =path.getPath();
        for(int x = 0 ; x < p.length - 1 ; x++){
            filePath += p[x].toString() + '/';
        }
        filePath += p[p.length - 1].toString();
        return filePath;
    }

    public boolean isFloder(TreePath path){
        Object[] p = path.getPath();
        if(p[p.length - 1].toString().contains(".")){
            return false;
        }
        return true;
    }
    public boolean isRootPacket(TreePath path){
        Object[] p = path.getPath();
        if(p.length == 2){
            return true;
        }
        return false;
    }

    public TreePath getSelectionPath(){
        return tree.getSelectionPath();
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
            if(!isFloder(path)){
                textPanel.openFile(getFilePath(path));
            }

        }

        @Override
        public void mousePressed(MouseEvent e) {


        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if(path == null){
                return;
            }
            if(e.isPopupTrigger() && isRootPacket(path)) {
                JPopupMenu popMenu = new JPopupMenu();
                JMenuItem menuLeafNode1 = new JMenuItem("output model");
                menuLeafNode1.addMouseListener(new TreeRunListener(path));
                menuLeafNode1.setFont(new Font("Consolas",Font.BOLD,12));
                JMenuItem menuItem = new JMenuItem("load model");
                menuItem.addMouseListener(new MouseAdapter(){
                    public void mouseReleased(MouseEvent e) {
                        String treePath = getFilePath(path);
                        consolePane.loadApp(treePath + "/model.xml");
                    }
                });
                popMenu.add(menuLeafNode1);
                popMenu.add(menuItem);
                JMenuItem menuItem2 = new JMenuItem("build path");

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
            consolePane.runApp(path + "/sketch");

            tree.refreshTree();
        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

}