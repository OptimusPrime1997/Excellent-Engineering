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
    private JTable table;
    private JProgressBar progressBar = new JProgressBar();
    private String[] tableHeader = {"TestName","Result"};

    public ConsolePane(JFrame jframe){
        this.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

        table = new JTable(new Object[][]{},tableHeader);
        JScrollPane scrollPane = new JScrollPane(table);
        progressBar.setSize(jframe.getWidth() / 4 ,jframe.getHeight() / 20);
        progressBar.setValue(70);
        progressBar.setForeground(Color.green);
        scrollPane.setSize(jframe.getWidth(),jframe.getHeight() / 30 * 8);

        this.add(progressBar);
        this.add(scrollPane);
    }

    public void runXML(String path){
        System.out.println("build new task");
        RunningTask runningTask = new RunningTask();
        runningTask.addPath(path);
        Thread thread = new Thread(runningTask);
        thread.run();
    }

    public void runApp(String path){

    }

    public void initTable(){
        TableModel  model = new DefaultTableModel(new Object[][]{},tableHeader);
        table.setModel(model);
        progressBar.setValue(0);
    }
    public void addCell(String testName,boolean result){

    }

    class RunningTask implements Runnable{
        private ArrayList<String> xmlPath = new ArrayList<String>();

        public void addPath(String path){
            xmlPath.add(path);
        }

        @Override
        public void run() {
            System.out.println("run begin");
            ModelCreateByPaths modelCreateByPaths = new ModelCreateByPaths();
            File[] files = new File[xmlPath.size()];
            int x = 0;
            for(String path : xmlPath){
                files[x] = new File(path);
                x++;
            }
            ModelService modelService = modelCreateByPaths.getModel(PathStrategyEnum.ROOT_FRIST,files);
            modelService.printModel();
        }
    }
}
