package view.text_manage;




import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import view.TextPanel;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.im.InputMethodRequests;
import java.io.*;

/**
 * Created by Administrator on 2016/7/21.
 */
public class MyShowPane extends RSyntaxTextArea implements KeyListener {
    private String path;
    private TextPanel textPanel;
    private String content;
    private boolean modify = false;

    public MyShowPane(String path,TextPanel textPanel) throws IOException {
        File file = new File(path);
        this.path = path;
        this.textPanel = textPanel;
        BufferedReader reader = new BufferedReader(new FileReader(file));
        //判断是什么语言对应的文件
        if(path.contains(".")){
            int pox = path.lastIndexOf('.');
            if(pox!=-1){
                String str = path.substring(pox+1);
                initHighlight(str);
            }
        }
        String str = "";
        String temp = reader.readLine();
        while (temp != null) {
            str += (temp + '\n');
            temp = reader.readLine();
        }
        System.out.println("read finish");
        this.setFont(new Font("Consolas", Font.BOLD, 12));


        this.setText(str);
        this.addKeyListener(this);
    }


    private void initHighlight(String style){
        if(style.equalsIgnoreCase("java")){
            this.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        }else if(style.equalsIgnoreCase("xml")){
            this.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_XML);
        }else if(style.equalsIgnoreCase("html")){
            this.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_HTML);
        }
    }

    public void close(){
        if(this.hasModified()){
            int option = JOptionPane.showConfirmDialog(null,
                    "文件已修改，是否保存？", "保存文件？", JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE, null);
            switch (option) {
                case JOptionPane.YES_NO_OPTION:
                    this.saveFile();
                case JOptionPane.NO_OPTION:
            }
        }
        textPanel.subSaveCount(this);
    }

    public void saveFile(){
        File file = new File(path);
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file);
            fileWriter.write(this.getText());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.modify = false;
    }

    public boolean hasModified(){
        return this.modify;
    }

    @Override
    public InputMethodRequests getInputMethodRequests() {
        return null;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        String get = this.getText();
        if(!get.equals(content)){
            content = get;
            textPanel.addSaveCount(this);
            this.modify = true;
        }
    }
}
