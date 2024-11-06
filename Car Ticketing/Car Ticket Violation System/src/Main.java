import java.awt.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;

public class Main extends MyFrame implements ActionListener, MouseListener, KeyListener, WindowListener {
    private Database db = new Database("Tickets.txt");
    private JLabel lblSearch;
    private JTextField txtSearch;
    private JTable tbl_Tickets;
    private DefaultTableModel model_tickets;
    private Vector columns, rowData;
    private TableRowSorter<DefaultTableModel> tbl_sort;

    private JButton btnAdd, btnClear, btnUpdate, btnDelete, btnClose;

    private JLabel lblTicketNo, lblViolatorName, lblViolationReason, lblPlateNumber, lblDate, lblFines, lblStatus;
    private JTextField txtTicketNo, txtViolatorName, txtPlateNumber, txtDate, txtFines;
    private JComboBox<String> cboViolationReason, cboStatus;
    private Font f = new Font("Arial", Font.BOLD, 16);

    private JPanel panelTicketInfo, panelButtons, panelSearch, panelTable;

    public Main() {
        initializedComponents();
        panelTicketInfo();
        panelTicketButtons();
        panelTicketSearch();
        panelTicketTable();

        // Setting bounds for panels
        add(panelTicketInfo).setBounds(60, 60, 300, 325);
        add(panelButtons).setBounds(60, 400, 600, 30);
        add(panelSearch).setBounds(365, 350, 300, 30);
        add(panelTable).setBounds(370, 60, 550, 290);

        add(setBackgroundImage("IMAGES/bgImage/bgImage.jpg"));
        setMyFrame("Car Violation Ticket System", 1100, 600, true, DISPOSE_ON_CLOSE, false);
        setLocationRelativeTo(null);
        txtTicketNo.setText(getRowCount());

        // Adding action listeners
        btnAdd.addActionListener(this);
        btnClear.addActionListener(this);
        btnUpdate.addActionListener(this);
        btnDelete.addActionListener(this);
        btnClose.addActionListener(this);

        tbl_Tickets.addMouseListener(this);
        txtSearch.addKeyListener(this);
        txtViolatorName.addKeyListener(this);
        txtPlateNumber.addKeyListener(this);
        txtFines.addKeyListener(this);

        addWindowListener(this);

        // Initialize database and reset components
        db = new Database("Tickets.txt");
        db.displayRecords(model_tickets);
        resetComponents();

        // Document listener for plate number
        txtPlateNumber.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                validatePlateNumber();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                validatePlateNumber();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                validatePlateNumber();
            }

            private void validatePlateNumber() {
                String plateNumber = txtPlateNumber.getText();
                // Perform validation if needed
                // Example: Check if plate number format is correct
            }
        });
    }

    public JPanel panelTicketTable() {
        panelTable = new JPanel();
        tbl_Tickets = new JTable();
        model_tickets = new DefaultTableModel();

        panelTable.setLayout(new BorderLayout());
        panelTable.add(new JScrollPane(tbl_Tickets), BorderLayout.CENTER);

        String cols[] = {"Ticket No", "Violator Name", "Violation Reason", "Plate Number", "Date", "Fines", "Status"};

        columns = new Vector<>();
        for (String val : cols) {
            columns.add(val);
        }
        model_tickets.setColumnIdentifiers(columns);
        tbl_Tickets.setModel(model_tickets);
        tbl_Tickets.setAutoResizeMode(tbl_Tickets.AUTO_RESIZE_OFF);

        return panelTable;
    }

    public void initializedComponents() {
        lblTicketNo = new JLabel("Ticket No: ");
        lblViolatorName = new JLabel("Violator Name: ");
        lblViolationReason = new JLabel("Violation Reason: ");
        lblPlateNumber = new JLabel("Plate Number: ");
        lblDate = new JLabel("Date: ");
        lblFines = new JLabel("Fines: ");
        lblStatus = new JLabel("Status: ");

        txtTicketNo = new JTextField(20);
        txtTicketNo.setEditable(false);

        txtViolatorName = new JTextField(20);
        txtPlateNumber = new JTextField(20);
        txtDate = new JTextField(LocalDate.now().toString());
        txtDate.setEditable(false);
        txtFines = new JTextField(20);

        cboStatus = new JComboBox<>();
        loadtoCombobox2();

        cboViolationReason = new JComboBox<>();
        loadtoComboBox();

        btnAdd = new JButton("Add");
        btnClear = new JButton("Clear");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnClose = new JButton("Close");
    }

    public void loadtoCombobox2(){
        cboStatus.addItem("Not Paid");
        cboStatus.addItem("Paid");
    }

    public void loadtoComboBox() {
        cboViolationReason.addItem("Speeding");
        cboViolationReason.addItem("Illegal Parking");
        cboViolationReason.addItem("Running a Red Light");
        cboViolationReason.addItem("No Seatbelt");
        cboViolationReason.addItem("Reckless Driving");
    }

    public String getRowCount() {
        return "TCK" + (1000 + model_tickets.getRowCount());
    }

    public void panelTicketInfo() {
        panelTicketInfo = new JPanel();
        panelTicketInfo.setBorder(BorderFactory.createTitledBorder("Ticket Information"));
        panelTicketInfo.setLayout(new GridLayout(7, 2));
        panelTicketInfo.setFont(f);
        panelTicketInfo.setOpaque(false);

        panelTicketInfo.add(lblTicketNo);
        panelTicketInfo.add(txtTicketNo);
        panelTicketInfo.add(lblViolatorName);
        panelTicketInfo.add(txtViolatorName);
        panelTicketInfo.add(lblViolationReason);
        panelTicketInfo.add(cboViolationReason);
        panelTicketInfo.add(lblPlateNumber);
        panelTicketInfo.add(txtPlateNumber);
        panelTicketInfo.add(lblDate);
        panelTicketInfo.add(txtDate);
        panelTicketInfo.add(lblFines);
        panelTicketInfo.add(txtFines);
        panelTicketInfo.add(lblStatus);
        panelTicketInfo.add(cboStatus);
    }

    public JPanel panelTicketSearch() {
        panelSearch = new JPanel();
        lblSearch = new JLabel("Search");
        txtSearch = new JTextField(10);
        panelSearch.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 1));
        panelSearch.add(lblSearch);
        panelSearch.add(txtSearch);
        panelSearch.setOpaque(false);
        return panelSearch;
    }

    public void getData() {
        rowData = new Vector<>();
        rowData.add(txtTicketNo.getText());
        rowData.add(txtViolatorName.getText());
        rowData.add(cboViolationReason.getSelectedItem());
        rowData.add(txtPlateNumber.getText());
        rowData.add(txtDate.getText());
        rowData.add(txtFines.getText());
        rowData.add(cboStatus.getSelectedItem());
    }

    public void resetComponents() {
        txtTicketNo.setText(getRowCount());

        btnAdd.setEnabled(true);
        btnClear.setEnabled(true);
        btnClose.setEnabled(true);

        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);

        txtViolatorName.setText("");
        txtPlateNumber.setText("");
        txtFines.setText("");

        cboViolationReason.setSelectedIndex(0);
    }

    public void tableClick() {
        txtTicketNo.setText(getRowCount());
        btnAdd.setEnabled(false);
        btnUpdate.setEnabled(true);
        btnDelete.setEnabled(true);
    }

    public void process() {
        String records = "";
        for (int r = 0; r < model_tickets.getRowCount(); r++) {
            for (int c = 0; c < model_tickets.getColumnCount(); c++) {
                records += model_tickets.getValueAt(r, c) + "#";
            }
            records += "\n";
        }
        db.storeToFile(records);
    }

    public void panelTicketButtons() {
        panelButtons = new JPanel();
        panelButtons.setLayout(new GridLayout(1, 5, 4, 2));
        panelButtons.add(btnAdd);
        panelButtons.add(btnClear);

        panelButtons.add(new JLabel(""));

        panelButtons.add(btnUpdate);
        panelButtons.add(btnDelete);
        panelButtons.add(btnClose);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btnAdd)) {
            getData();
            model_tickets.addRow(rowData);
            resetComponents();
        } else if (e.getSource().equals(btnClear)) {
            resetComponents();
        } else if (e.getSource().equals(btnUpdate)) {
            int selectedRowIndex = tbl_Tickets.getSelectedRow();
            if (selectedRowIndex != -1) {
                getData();
                tbl_Tickets.setValueAt(rowData.get(1), selectedRowIndex, 1); // Update Violator Name
                tbl_Tickets.setValueAt(rowData.get(3), selectedRowIndex, 3); // Update Plate Number
                tbl_Tickets.setValueAt(rowData.get(6), selectedRowIndex, 6); // Update Status
                resetComponents();
            }
        } else if (e.getSource().equals(btnDelete)) {
            int i = tbl_Tickets.getSelectedRow();
            model_tickets.removeRow(i);
            resetComponents();
        } else if (e.getSource().equals(btnClose)) {
            process();
            System.exit(0);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int i = tbl_Tickets.getSelectedRow();
        if (i != -1) {
            txtTicketNo.setText(tbl_Tickets.getValueAt(i, 0) + "");
            txtViolatorName.setText(tbl_Tickets.getValueAt(i, 1) + "");
            cboViolationReason.setSelectedItem(tbl_Tickets.getValueAt(i, 2) + "");
            txtPlateNumber.setText(tbl_Tickets.getValueAt(i, 3) + "");
            txtDate.setText(tbl_Tickets.getValueAt(i, 4) + "");
            txtFines.setText(tbl_Tickets.getValueAt(i, 5) + "");
            tableClick();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {
        filterRecord(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        filterRecord(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        filterRecord(e);
    }

    public void filterRecord(KeyEvent e) {
        tbl_sort = new TableRowSorter<>(model_tickets);
        tbl_Tickets.setRowSorter(tbl_sort);

        if (e.getSource().equals(txtSearch)) {
            tbl_sort.setRowFilter(RowFilter.regexFilter("(?i)" + txtSearch.getText(), 0, 1));
        }

        if (e.getSource().equals(txtPlateNumber)) {
            char c = e.getKeyChar();
            int code = e.getKeyCode();

            if (!(Character.isLetterOrDigit(c) || Character.isISOControl(c) || code == KeyEvent.VK_BACK_SPACE || code == KeyEvent.VK_CAPS_LOCK || code == KeyEvent.VK_SHIFT)) {
                e.consume();
                JOptionPane.showMessageDialog(this, "Please input letters and numbers only...", "Warning Message", JOptionPane.WARNING_MESSAGE);
            }
        }

        if (e.getSource().equals(txtViolatorName)) {
            if (Character.isDigit(e.getKeyChar())) {
                e.consume();
                JOptionPane.showMessageDialog(this, "Please input letters only...", "Warning Message", JOptionPane.WARNING_MESSAGE);
            }
        }

        if (e.getSource().equals(txtFines)) {
            if (!(Character.isDigit(e.getKeyChar()) || e.getKeyChar() == KeyEvent.VK_BACK_SPACE)) {
                e.consume();
                JOptionPane.showMessageDialog(this, "Please input numbers only...", "Warning Message", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {}

    @Override
    public void windowClosing(WindowEvent e) {
        process();
    }

    @Override
    public void windowClosed(WindowEvent e) {}

    @Override
    public void windowIconified(WindowEvent e) {}

    @Override
    public void windowDeiconified(WindowEvent e) {}

    @Override
    public void windowActivated(WindowEvent e) {}

    @Override
    public void windowDeactivated(WindowEvent e) {}
}
