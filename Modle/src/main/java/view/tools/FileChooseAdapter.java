package view.tools;

import impl.area.*;
import org.w3c.dom.events.MouseEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.Component;
import java.awt.event.MouseListener;
import java.io.*;

/**
 * Created by 王栋 on 2016/7/25 0025.
 */
public class FileChooseAdapter implements MouseListener {

    private String rootDir;
    private Component parent;
    public FileChooseAdapter(String rootDir, Component parent){
        this.rootDir = rootDir;
        this.parent = parent;
    }

    @Override
    public void mouseClicked(java.awt.event.MouseEvent e) {
        JFileChooser jfc1 = new JFileChooser();
        jfc1.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        jfc1.setCurrentDirectory(new File(rootDir));
        jfc1.setDialogTitle("选择项目");
        int nRetVal1 = jfc1.showDialog(parent,"选择");

        if(nRetVal1 == JFileChooser.APPROVE_OPTION){
            JFileChooser jfc2 = new JFileChooser();
            jfc2.setMultiSelectionEnabled(true);
            int nRetVal = jfc2.showDialog(parent,"选择");
            if (nRetVal == JFileChooser.APPROVE_OPTION){
                //生成sketch文件夹
                File root = jfc1.getSelectedFile();
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
    public void mousePressed(java.awt.event.MouseEvent e) {

    }

    @Override
    public void mouseReleased(java.awt.event.MouseEvent e) {

    }

    @Override
    public void mouseEntered(java.awt.event.MouseEvent e) {

    }

    @Override
    public void mouseExited(java.awt.event.MouseEvent e) {

    }
}
