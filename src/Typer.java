import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.PrintWriter;

/**
 * Created by Crystall on 06/08/2018
 */
public class Typer extends JFrame implements KeyListener, ActionListener {

    private JTextField txt_key;
    private JButton btn_confirm;
    private JButton btn_reset;
    private File file;

    public Typer() {
        /**
         * Main Frame
         */
        setTitle("Typer - V1.0");
        setType(Window.Type.POPUP);
        setPreferredSize(new Dimension(450, 108));
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(Toolkit.getDefaultToolkit()
                .getImage(Typer.class.getResource("/com/sun/javafx/scene/control/skin/modena/dialog-warning@2x.png")));
        getContentPane().setLayout(null);

        /**
         * Input Field
         */
        txt_key = new JTextField();
        txt_key.setBounds(10, 12, 366, 20);
        txt_key.setColumns(10);
        getContentPane().add(txt_key);


        /**
         * Submit button
         */
        btn_confirm = new JButton();
        btn_confirm.setBounds(198, 39, 178, 23);
        btn_confirm.setText("Save to file");
        btn_confirm.setVisible(true);
        btn_confirm.setHorizontalAlignment(SwingConstants.CENTER);
        getContentPane().add(btn_confirm);

        /**
         * Reset button
         */
        btn_reset = new JButton();
        btn_reset.setBounds(10, 39, 178, 23);
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
            file = new File(Typer.class.getProtectionDomain().getCodeSource().getLocation().getPath());
            if (!file.exists()) {
                file.createNewFile();
            }
            PrintWriter writer = new PrintWriter(file, "UTF-8");
            writer.println(txt_key.getText());
            writer.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void clearText(JTextField field) {
        field.setText("");
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
            if ((txt_key.getText().length() + 1) % 5 == 0) {
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
            clearText(txt_key);
        } else if (e.getSource() == btn_reset) {
            clearText(txt_key);
        }
    }
}
