import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.BorderLayout;

public class AutocorrectFrame extends JFrame {

    final private JTextField textfield = new JTextField();
    final private JList<String> autocorrectList = new JList<String>(new DefaultListModel<String>());
    final private Autocorrector autocorrector;

    public AutocorrectFrame(Autocorrector autocorrector) {
        super("Autocorrector");
        this.autocorrector = autocorrector;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().add(textfield, BorderLayout.NORTH);
        this.getContentPane().add(new JScrollPane(autocorrectList), BorderLayout.CENTER);
        this.pack();

        textfield.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                //System.out.println("insert: " + textfield.getText());
                update();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                //System.out.println("remove: " + textfield.getText());
                update();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                update();
            }
        });
    }

    public JTextField getTextField() {
        return textfield;
    }

    private void update() {
        DefaultListModel<String> listmodel = (DefaultListModel<String>) autocorrectList.getModel();
        listmodel.clear();

        String text = textfield.getText();
        if (!text.equals("") && !text.endsWith(" ")) {
            String[] split = textfield.getText().split(" ");
            String last = split[split.length - 1];
            listmodel.addAll(autocorrector.getBestSuggestions(last));
        }

    }
}
