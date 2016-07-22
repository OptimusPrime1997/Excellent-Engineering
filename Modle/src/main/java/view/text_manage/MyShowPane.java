package view.text_manage;




import javax.swing.*;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Administrator on 2016/7/21.
 */
public class MyShowPane extends JEditorPane {
    public MyShowPane(String path) throws IOException {
        File file = new File(path);
        BufferedReader reader = new BufferedReader(new FileReader(file));
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


}
