package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.PrintStream;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import routingDaemon.RoutingDaemon;
import fileParser.FileParser;

public class MainGUI {

    private JFrame frmRip;
    private JTextField txtInputFile;
    private ImageIcon icon = new ImageIcon("rip_tombstone.png");
    private JButton btnStartRouter;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainGUI window = new MainGUI();
                    window.frmRip.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public MainGUI() {
        initialize();
    }

    /**
     * Initialise the contents of the frame.
     */
    private void initialize() {
        frmRip = new JFrame();
        frmRip.setResizable(false);
        frmRip.setIconImage(icon.getImage());
        frmRip.setTitle("RIP");
        frmRip.setBounds(100, 100, 459, 450);
        frmRip.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmRip.getContentPane().setLayout(null);

        JLabel lblConfigurationFile = new JLabel("Config File:");
        lblConfigurationFile.setBounds(10, 11, 123, 18);
        frmRip.getContentPane().add(lblConfigurationFile);

        txtInputFile = new JTextField();
        txtInputFile.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    startRoutingDaemon();
                }
            }
        });
        txtInputFile.setBounds(97, 11, 125, 20);
        frmRip.getContentPane().add(txtInputFile);
        txtInputFile.setColumns(10);

        btnStartRouter = new JButton("Start");
        btnStartRouter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startRoutingDaemon();
            }
        });
        btnStartRouter.setBounds(299, 392, 146, 23);
        frmRip.getContentPane().add(btnStartRouter);

        JTextArea txtOutput = new JTextArea();
        JScrollPane scroll = new JScrollPane(txtOutput);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        txtOutput.setEditable(false);
        PrintStream out = new PrintStream(new TextAreaOutputStream(txtOutput));
        System.setOut(out);
        System.setErr(out);
        scroll.setBounds(10, 60, 435, 327);
        frmRip.getContentPane().add(scroll);

        JLabel lblNewLabel = new JLabel("Output");
        lblNewLabel.setBounds(12, 41, 102, 18);
        frmRip.getContentPane().add(lblNewLabel);
    }

    private void startRoutingDaemon() {
        FileParser fp = new FileParser(txtInputFile.getText());
        System.out.print(fp);
        RoutingDaemon daemon = new RoutingDaemon(fp.getRouterId(),
                fp.getInputPorts(), fp.getOutputPorts());
        btnStartRouter.setEnabled(false);
        txtInputFile.setEnabled(false);
    }
}
