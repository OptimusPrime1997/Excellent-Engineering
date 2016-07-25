package view.tools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;


/**
 * Created by 王栋 on 2016/7/25 0025.
 */
public class CreateProjectAdapter implements MouseListener{

    private String rootDir;
    private Component parent;

    public CreateProjectAdapter(String rootDir, Component parent){
        this.rootDir = rootDir;
        this.parent = parent;
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        int width = 300;
        int height = 100;
        JButton button = new JButton("确认");
        final JTextField textField = new JTextField(80);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int)dimension.getWidth();
        int y = (int)dimension.getHeight();

        JDialog jDialog = new JDialog();
        jDialog.setBounds((x-width)/2,(y-height)/2,width,height);
        jDialog.getContentPane().setLayout(new GridLayout(1,3));
        jDialog.getContentPane().add(new JLabel("项目名"));
        jDialog.getContentPane().add(textField);
        jDialog.getContentPane().add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc2 = new JFileChooser();
                jfc2.setMultiSelectionEnabled(true);
                int nRetVal = jfc2.showDialog(parent,"选择");
                if (nRetVal == JFileChooser.APPROVE_OPTION){
                    //生成sketch文件夹
                    File root = new File(rootDir+ File.separatorChar+textField.getText());
                    System.out.println(root.getAbsolutePath());
                    File sktch = new File(root.getAbsolutePath()+File.separatorChar+"sketch");
                    if (!sktch.exists()||!sktch.isDirectory()){
                        sktch.mkdir();
                    }
                    //生成sketch文件夹

                    File[] files = jfc2.getSelectedFiles();

                    for(File file : files){
                        System.out.println(file.getAbsolutePath());
                        File copyFile = new File(sktch.getAbsolutePath()+File.separatorChar+file.getName());
                        copy(file,copyFile);

                    }

                }
            }
        });
    }


    private void copy(File file,File copyFile){
        try {
            BufferedOutputStream copyBOS = new BufferedOutputStream(new FileOutputStream(copyFile));
            BufferedInputStream fileBIS = new BufferedInputStream(new FileInputStream(file));
            byte[] buf = new byte[1024*512];
            int len = 0 ;
            while ((len = fileBIS.read(buf))!=-1){
                copyBOS.write(buf,0,len);
            }
            copyBOS.close();
            fileBIS.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
