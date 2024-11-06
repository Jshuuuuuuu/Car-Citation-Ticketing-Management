import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Database {

    private File file=null;
    private FileWriter fWrite=null;
    private FileReader fRead=null;
    private Scanner scan=null;
    private Vector<String> row, column;
    public Database() {
    }

    public Database(String filename) {
        file=new File(filename);
    }

    public void setFilename(String filename) {
        new Database(filename);
    }

    public String getFilename() { return file.getName(); }

    public void errorMessage(String msg){
        JOptionPane.showMessageDialog(null,msg,"Error",JOptionPane.ERROR_MESSAGE);
    }

    public void storeToFile(String records){
        try {
            fWrite=new FileWriter(file);
            fWrite.write(records);
            fWrite.flush();
        } catch (Exception e) {
            errorMessage("Error 101: storeToFile\n"+e.getMessage());
        }
    }

    public void displayRecords(DefaultTableModel modelTickets) {
    }
}
