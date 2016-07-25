package view;

import create_model.ModelCreateByPaths;
import service.ModelService;
import util.xmlwriter.PathStrategyEnum;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/21.
 */
public class ConsolePane extends JPanel {
    private JProgressBar progressBar = new JProgressBar();
    private JTextField textField = new JTextField("no task");
    private JFrame frame;
    private int max = 0;
    private int process = 0;

    public ConsolePane(JFrame jframe){
        this.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        this.setLayout(new GridLayout(1,2));
        this.frame = jframe;
        progressBar.setValue(70);
        progressBar.setForeground(Color.green);
        progressBar.setVisible(false);
        textField.setEditable(false);
        this.add(textField,getTextFieldConstriants());
        this.add(progressBar,getProgressBarConstriants());
    }

    private GridBagConstraints getProgressBarConstriants(){
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = gridBagConstraints.BOTH;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.weightx = frame.getWidth() / 3 * 1;
        gridBagConstraints.weighty = frame.getHeight() / 30;
        return gridBagConstraints;
    }

    private GridBagConstraints getTextFieldConstriants(){
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = gridBagConstraints.BOTH;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.weightx = frame.getWidth() / 3 * 2;
        gridBagConstraints.weighty = frame.getHeight() / 30;
        return gridBagConstraints;
    }

    public void runApp(String path){
        System.out.println(path);
        File file = new File(path);
        File[] fileList = file.listFiles();
        RunningTask runningTask = new RunningTask(path.replace("sketch",""));
        for(File f : fileList){
            runningTask.addPath(f);
        }
        Thread thread = new Thread(runningTask);
        thread.run();

    }

    public void loadApp(String path){

    }
    
    public void resetProcessBar(){
        this.progressBar.setValue(0);
        this.process = 0;
        this.max = 0;
    }

    public void setMax(int max){
        this.max = max;
        this.process = 0;
    }

    public void addProcessBar(){
        this.process ++ ;
        this.progressBar.setValue(this.process / this.max * 100);
    }

    class RunningTask implements Runnable{
        private ArrayList<File> xmlPath = new ArrayList<File>();
        private String appFloderPath;
        public RunningTask(String appFloderPath){
            System.out.println(appFloderPath);
            this.appFloderPath = appFloderPath;
        }

        public void addPath(File file){
            xmlPath.add(file);
        }

        @Override
        public void run() {
            System.out.println("run begin");
            resetProcessBar();
            ModelCreateByPaths modelCreateByPaths = new ModelCreateByPaths();
            File[] files = new File[xmlPath.size()];
            int x = 0;
            for(File f : xmlPath){
                files[x] = f;
                x++;
            }
            ModelService modelService = modelCreateByPaths.getModel(PathStrategyEnum.ROOT_FRIST,files,appFloderPath);
            textField.setText("save the model");
            modelService.printModel();
            int num = Integer.parseInt(JOptionPane.showInputDialog(null,"please input the count of the paths you want to create"));
            textField.setText("building the path");
            modelService.printXML(num);   //这里默认是20
            textField.setText("build finish");

        }
    }
}
