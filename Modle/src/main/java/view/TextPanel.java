package view;

import view.text_manage.MyShowPane;
import view.tools.TabbedPanel;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/20.
 */
public class TextPanel extends JPanel {
    private JTabbedPane tb = new JTabbedPane();
    private int panel_count = 0;
    private ArrayList<MyShowPane> list = new ArrayList<MyShowPane>();
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

            MyShowPane myShowPane = new MyShowPane(path,this);
            //myShowPane.setEditorKit(new JavaSyntaxKit());
            JScrollPane jScrollPane = new JScrollPane(myShowPane);
            String name[] = path.split("/");
            for(int x = 0 ;  x < tb.getTabCount() ; x++){
                if(tb.getTitleAt(x).equals(name[name.length - 1])){
                    tb.setSelectedIndex(x);
                    return;
                }
            }

            tb.addTab(name[name.length - 1],jScrollPane);

            tb.setTabComponentAt(tb.indexOfComponent(jScrollPane),new TabbedPanel(tb,path));
            tb.setSelectedIndex(tb.indexOfComponent(jScrollPane));
            panel_count ++ ;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addSaveCount(MyShowPane myShowPane){
        if(!list.contains(myShowPane)){
            list.add(myShowPane);
        }
    }

    public void subSaveCount(MyShowPane myShowPane){
        list.remove(myShowPane);
    }

    public boolean hasUnSavedFile(){
        if(list.size() > 0){
            return true;
        }else{
            return false;
        }
    }
    public void saveFile(){
        JScrollPane scrollPane = (JScrollPane) tb.getComponentAt(tb.getSelectedIndex());
        MyShowPane myShowPane = (MyShowPane) scrollPane.getViewport().getView();
        if(myShowPane.hasModified()){
            list.remove(myShowPane);
        }

    }

    public void saveAllFile(){
        for(MyShowPane myShowPane : list){
            myShowPane.saveFile();
        }
    }
}
