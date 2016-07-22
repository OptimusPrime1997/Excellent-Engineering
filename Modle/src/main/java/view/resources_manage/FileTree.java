package view.resources_manage;

/**
 * Created by Administrator on 2016/7/21.
 */

import java.awt.Component;
import java.io.File;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

/**
 * @author root
 *
 */
public class FileTree extends JTree {
    static final long serialVersionUID = 0;

    private FileList theList;

    public FileTree(FileList list) {
        theList = list;
        setModel(new FileSystemModel(new FolderNode()));
        this.setCellRenderer(new FolderRenderer());
        this.setSelectionRow(0);

    }
    @Override
    public void fireValueChanged(TreeSelectionEvent tse) {
        TreePath tp = tse.getNewLeadSelectionPath();
        Object o = tp.getLastPathComponent();
        theList.fireTreeSelectionChanged((FolderNode) o);
    }

    @Override
    public void fireTreeCollapsed(TreePath path) {
        super.fireTreeCollapsed(path);
        TreePath curpath = getSelectionPath();
        if (path.isDescendant(curpath)) {
            setSelectionPath(path);
        }
    }

    @Override
    public void fireTreeWillExpand(TreePath path) {
        System.out.println("Path will expand is " + path);
    }

    @Override
    public void fireTreeWillCollapse(TreePath path) {
        System.out.println("Path will collapse is " + path);
    }
}

//----------------------------------------------------------------------------------------------------------------------
class FileSystemModel implements TreeModel {
    I_fileSystem theRoot;
    char fileType = I_fileSystem.DIRECTORY;

    public FileSystemModel(I_fileSystem fs) {
        theRoot = fs;
    }

    public Object getRoot() {
        return theRoot;
    }

    public Object getChild(Object parent, int index) {
        return ((I_fileSystem) parent).getChild(fileType, index);
    }

    public int getChildCount(Object parent) {
        return ((I_fileSystem) parent).getChildCount(fileType);
    }

    public boolean isLeaf(Object node) {
        return ((I_fileSystem) node).isLeaf(fileType);
    }

    public int getIndexOfChild(Object parent, Object child) {
        return ((I_fileSystem) parent).getIndexOfChild(fileType, child);
    }

    public void valueForPathChanged(TreePath path, Object newValue) {
    }

    public void addTreeModelListener(TreeModelListener l) {
    }

    public void removeTreeModelListener(TreeModelListener l) {
    }
}

//----------------------------------------------------------------------------------------------------------------------
interface I_fileSystem {
    final public static char DIRECTORY = 'D';
    final public static char FILE = 'F';
    final public static char ALL = 'A';

    public Icon getIcon();
    public I_fileSystem getChild(char fileType, int index);
    public int getChildCount(char fileType);
    public boolean isLeaf(char fileType);
    public int getIndexOfChild(char fileType, Object child);
}

//----------------------------------------------------------------------------------------------------------------------
class FileNode implements  I_fileSystem{
    private FileSystemView fsView;
    private File theFile;
    public FileNode(FileSystemView fsView , File file){
        this.fsView = fsView;
        this.theFile = file;
    }
    @Override
    public Icon getIcon() {
        return fsView.getSystemIcon(theFile);
    }

    @Override
    public I_fileSystem getChild(char fileType, int index) {
        System.out.println("run file");
        return null;
    }

    @Override
    public int getChildCount(char fileType) {
        return 0;
    }

    @Override
    public boolean isLeaf(char fileType) {
        return true;
    }

    @Override
    public int getIndexOfChild(char fileType, Object child) {
        return 0;
    }
}

//----------------------------------------------------------------------------------------------------------------------
class FolderNode implements I_fileSystem {
    // private static FolderNode theRoot;
    private static FileSystemView fsView;
    private static boolean showHiden = true;;
    private File theFile;
    private Vector<File> all = new Vector<File>();
    private Vector<File> folder = new Vector<File>();

    /**
     * set that whether apply hiden file.
     * @param ifshow
     */
    public void setShowHiden(boolean ifshow) {
        showHiden = ifshow;
    }

    public Icon getIcon() {
        return fsView.getSystemIcon(theFile);
    }

    public String toString() {
        // return fsView.
        return fsView.getSystemDisplayName(theFile);
    }

    /**
     * create a root node. by default, it should be the DeskTop in window file system.
     */
    public FolderNode() {
        fsView = FileSystemView.getFileSystemView();

        theFile = new File("C:/ONE_PIECE");
        if(!theFile.exists()){
            theFile.mkdir();
            System.err.println("build the workspace");
        }
        prepareChildren();
    }

    private void prepareChildren() {
        File[] files = fsView.getFiles(theFile, showHiden);

        for (int i = 0; i < files.length; i++) {
            all.add(files[i]);
            if (!files[i].toString().toLowerCase().endsWith(".lnk")) {
                folder.add(files[i]);
            }
        }
    }

    private FolderNode(File file) {
        theFile = file;
        prepareChildren();
    }

    public I_fileSystem getChild(char fileType, int index) {
        if (I_fileSystem.DIRECTORY == fileType) {
            return new FolderNode(folder.get(index));
        } else if (I_fileSystem.ALL == fileType) {
            return new FolderNode(all.get(index));
        } else if (I_fileSystem.FILE == fileType) {
            return new FileNode(fsView,folder.get(index));
        } else {
            return null;
        }
    }

    public int getChildCount(char fileType) {
        if (I_fileSystem.DIRECTORY == fileType) {
            return folder.size();
        } else if (I_fileSystem.ALL == fileType) {
            return all.size();
        } else if (I_fileSystem.FILE == fileType) {
            return -1;
        } else {
            return -1;
        }
    }

    public boolean isLeaf(char fileType) {
        if (I_fileSystem.DIRECTORY == fileType) {
            return folder.size() == 0;
        } else if (I_fileSystem.ALL == fileType) {
            return all.size() == 0;
        } else if (I_fileSystem.FILE == fileType) {
            return true;
        } else {
            return true;
        }
    }

    public int getIndexOfChild(char fileType, Object child) {
        if (child instanceof FolderNode) {
            if (I_fileSystem.DIRECTORY == fileType) {
                return folder.indexOf(((FolderNode) child).theFile);
            } else if (I_fileSystem.ALL == fileType) {
                return all.indexOf(((FolderNode) child).theFile);
            } else if (I_fileSystem.FILE == fileType) {
                return -1;
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }
}

//----------------------------------------------------------------------------------------------------------------------
class FolderRenderer extends DefaultTreeCellRenderer {
    private static final long serialVersionUID = 1L;

    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row,    boolean hasFocus) {
        I_fileSystem node = (I_fileSystem) value;
        Icon icon = node.getIcon();

        setLeafIcon(icon);
        setOpenIcon(icon);
        setClosedIcon(icon);

        return super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
    }
}
