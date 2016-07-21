package view;

import view.resources_manage.FileList;
import view.resources_manage.FileTree;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
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
    public PacketPanel(JFrame jframe ,ResultPanel resultPanel){
        this.setSize(jframe.getWidth()/4,jframe.getHeight()/7*6);
        this.setLocation(0,jframe.getHeight()/7);
        this.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        this.setLayout(new BorderLayout());
        FileList list = new FileList();
        tree = new FileTree(list);
        tree.addMouseListener(new TreeListener());
        //tree.setDoubleBuffered(true);
        //list.setDoubleBuffered(true);
        treeView = new JScrollPane(tree);
        treeView.setSize(jframe.getWidth()/4,jframe.getHeight()/5*4);
        treeView.setLocation(0,0);
        this.add(BorderLayout.CENTER,treeView);
        System.out.println(treeView.getSize());
        System.out.println(this.getSize());
        //this.repaint();
    }

    class TreeListener implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {

                TreePath path = tree.getSelectionPath();
                System.out.println(path.toString());


        }

        @Override
        public void mousePressed(MouseEvent e) {


        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if(e.isPopupTrigger()) {
                System.out.println("func called");
                JPopupMenu popMenu = new JPopupMenu();
                JMenuItem MenuLeafNode1 = new JMenuItem("测试用");
                popMenu.add(MenuLeafNode1);
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
}
