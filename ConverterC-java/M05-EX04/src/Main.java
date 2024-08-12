import javax.swing.JFrame;

public class Main extends JFrame {
    private ConversorMoedaPanel panel;

    public Main() {
        setTitle("Conversor de Moeda");
        setSize(1000, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new ConversorMoedaPanel();
        add(panel);

        setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }
}
