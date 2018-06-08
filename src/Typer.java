import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 * Created by Crystall on 06/08/2018
 */
public class Typer extends JFrame implements KeyListener, ActionListener {

    private JTextArea txt_key;
    private JLabel lbl_info;
    private JButton btn_confirm;
    private JButton btn_reset;
    private File file;

    public Typer() {
        /**
         * Main Frame
         */
        setTitle("Typer - V1.0");
        setType(Window.Type.POPUP);
        setPreferredSize(new Dimension(420, 320));
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.lightGray);
        setIconImage(Toolkit.getDefaultToolkit().getImage(Typer.class.getResource("/com/sun/javafx/scene/control/skin/modena/dialog-warning@2x.png")));
        getContentPane().setLayout(null);

        /**
         * Info Label
         */
        lbl_info = new JLabel("Developed by Marek Hasselder");
        lbl_info.setForeground(Color.BLUE);
        lbl_info.setBounds(10, 220, 226, 14);
        getContentPane().add(lbl_info);

        /**
         * Input Field
         */
        txt_key = new JTextArea();
        txt_key.setBounds(10, 12, 366, 200);
        txt_key.setColumns(10);
        txt_key.setFont(new Font("Consolas", Font.BOLD, 16));
        getContentPane().add(txt_key);


        /**
         * Submit button
         */
        btn_confirm = new JButton();
        btn_confirm.setBounds(198, 239, 178, 23);
        btn_confirm.setText("Save to file");
        btn_confirm.setVisible(true);
        btn_confirm.setHorizontalAlignment(SwingConstants.CENTER);
        getContentPane().add(btn_confirm);

        /**
         * Reset button
         */
        btn_reset = new JButton();
        btn_reset.setBounds(10, 239, 178, 23);
        btn_reset.setText("Reset");
        btn_reset.setVisible(true);
        btn_reset.setHorizontalAlignment(SwingConstants.CENTER);
        getContentPane().add(btn_reset);

        txt_key.addKeyListener(this);
        btn_confirm.addActionListener(this);
        btn_reset.addActionListener(this);

        pack();
        setLocationRelativeTo(null);
    }

    /**
     * Saves the content to a textfile
     */
    public void saveToFile() {
        try {
            file = new File("./keycodes.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            writer.append(txt_key.getText() + getSystemNewline());
            writer.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Returns the new line for the right system
     * @return
     */
    public static String getSystemNewline() {
        String eol = null;
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("mac")) {
            int v = Integer.parseInt(System.getProperty("os.version"));
            eol = (v <= 9 ? "\r" : "\n");
        }
        if (os.contains("nix"))
            eol = "\n";
        if (os.contains("win"))
            eol = "\r\n";

        return eol;
    }

    /**
     * Clears the text of the input field
     */
    public void clearText() {
        txt_key.setText("");
    }

    @Override
    public void keyTyped(KeyEvent e) {
        e.consume();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        char s = e.getKeyChar();
        if (e.getSource() == txt_key && (Character.isAlphabetic(s) || Character.isDigit(s))) {
            e.consume();
            txt_key.setText(txt_key.getText() + Character.toUpperCase(s));
            int length = txt_key.getText().length() + 1;
            if (length % 30 == 0) {
                txt_key.setText(txt_key.getText() + getSystemNewline());
            } else if (length % 6 == 0) {
                txt_key.setText(txt_key.getText() + "-");
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        e.consume();
    }

    public static void main(String... args) {
        new Typer();
    }

    @Override public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btn_confirm) {
            saveToFile();
            clearText();
        } else if (e.getSource() == btn_reset) {
            clearText();
        }
    }
}
