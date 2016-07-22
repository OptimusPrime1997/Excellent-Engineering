package view.text_manage;




import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;

import javax.swing.*;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Administrator on 2016/7/21.
 */
public class MyShowPane extends RSyntaxTextArea {
    public MyShowPane(String path) throws IOException {
        File file = new File(path);
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
}
