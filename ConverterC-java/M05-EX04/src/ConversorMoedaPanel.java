import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ConversorMoedaPanel extends JPanel {
    private JLabel rotuloValorReais = new JLabel("Valor em Reais (BRL):");
    private JTextField campoValorReais = new JTextField();
    private JLabel rotuloConverterPara = new JLabel("Converter para:");
    private JComboBox<String> comboMoedas = new JComboBox<>();
    private JButton botaoConverter = new JButton("Converter");

    private String[] moedas = {"USD - Dólar Americano", "EUR - Euro", "GBP - Libra Esterlina"};
    private double[] taxas = {5.51, 5.90, 6.97};

    public ConversorMoedaPanel() {
        configurarLayout();
        configurarComponentes();
        adicionarComponentes();
        configurarEventos();
    }

    private void configurarLayout() {
        setLayout(new GridLayout(5, 2, 5, 5));
    }

    private void configurarComponentes() {
        comboMoedas.setModel(new DefaultComboBoxModel<>(moedas));
    }

    private void adicionarComponentes() {
        add(rotuloValorReais);
        add(campoValorReais);

        add(rotuloConverterPara);
        add(comboMoedas);

        add(botaoConverter);
    }

    private void configurarEventos() {
        botaoConverter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                converterMoeda();
            }
        });
    }

    protected void converterMoeda() {
        String valorReaisStr = campoValorReais.getText().trim().replace(',', '.');
        if (valorReaisStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, insira um valor em Reais (BRL).", "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        double valorReais;
        try {
            valorReais = parseValor(valorReaisStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Valor em Reais inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String moedaDestino = (String) comboMoedas.getSelectedItem();
        String codigoMoeda = moedaDestino.split(" ")[0];

        double taxaCambio = 0;
        for (int i = 0; i < moedas.length; i++) {
            if (moedas[i].equals(moedaDestino)) {
                taxaCambio = taxas[i];
                break;
            }
        }

        double valorConvertido = valorReais / taxaCambio;

        DecimalFormat df = new DecimalFormat("#,##0.00");
        JOptionPane.showMessageDialog(this, String.format("R$ %.2f = %s %s", valorReais, df.format(valorConvertido), codigoMoeda),
                "Resultado da Conversão", JOptionPane.INFORMATION_MESSAGE);
    }

    private double parseValor(String valorStr) throws NumberFormatException {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        symbols.setGroupingSeparator(',');

        DecimalFormat decimalFormat = new DecimalFormat("#,##0.##", symbols);
        decimalFormat.setParseBigDecimal(true);

        try {
            return decimalFormat.parse(valorStr).doubleValue();
        } catch (Exception e) {
            throw new NumberFormatException("Formato numérico inválido.");
        }
    }
}
