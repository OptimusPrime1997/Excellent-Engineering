package view;

import jsyntaxpane.syntaxkits.JavaSyntaxKit;
import view.text_manage.MyShowPane;
import view.tools.TabbedPanel;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

/**
 * Created by Administrator on 2016/7/20.
 */
public class TextPanel extends JPanel {
    private JTabbedPane tb = new JTabbedPane();
    private int panel_count = 0;
    public TextPanel(JFrame jFrame){
        this.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        this.setLayout(new BorderLayout());
    }

    public void openFile(String path){
        System.out.println(path);
        if(panel_count == 0){
            this.add(tb);

        }
        try {

            MyShowPane myShowPane = new MyShowPane(path);
            //myShowPane.setEditorKit(new JavaSyntaxKit());
            JScrollPane jScrollPane = new JScrollPane(myShowPane);
            String name[] = path.split("/");
            tb.addTab(name[name.length - 1],jScrollPane);

            tb.setTabComponentAt(tb.indexOfComponent(jScrollPane),new TabbedPanel(tb,path));
            panel_count ++ ;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
