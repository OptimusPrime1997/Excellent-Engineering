package view.tools;

import view.MainView;

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
        System.out.println("点击了");
        int width = 300;
        int height = 100;
        JButton button = new JButton("确认");
        final JTextField textField = new JTextField(20);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int)dimension.getWidth();
        int y = (int)dimension.getHeight();

        final JDialog jDialog = new JDialog();
        jDialog.setTitle("填写项目名称");
        jDialog.setBounds((x-width)/2,(y-height)/2,width,height);
        jDialog.getContentPane().setLayout(new FlowLayout());
        jDialog.getContentPane().add(new JLabel("项目名"));
        jDialog.getContentPane().add(textField);
        jDialog.getContentPane().add(button);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String projectName = textField.getText();
                jDialog.dispose();
                JFileChooser jfc2 = new JFileChooser();
                jfc2.setMultiSelectionEnabled(true);
                int nRetVal = jfc2.showDialog(parent,"选择");
                if (nRetVal == JFileChooser.APPROVE_OPTION){
                    //生成sketch文件夹
                    File root = new File(rootDir+ File.separatorChar+projectName);
                    System.out.println(root.getAbsolutePath());
                    File sktch = new File(root.getAbsolutePath()+File.separatorChar+"sketch");
                    if (!sktch.exists()||!sktch.isDirectory()){
                        System.out.println("生成对应的文件夹");
                        sktch.mkdirs();
                    }
                    //生成sketch文件夹

                    File[] files = jfc2.getSelectedFiles();

                    for(File file : files){
                        System.out.println(file.getAbsolutePath());
                        File copyFile = new File(sktch.getAbsolutePath()+File.separatorChar+file.getName());
                        copy(file,copyFile);

                    }

                    ((MainView)parent).refreshTree();

                }
            }
        });

        jDialog.setModal(true);
        jDialog.setVisible(true);

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
