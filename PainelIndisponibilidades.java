import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 * Painel (aba) de cadastro de indisponibilidades programadas
 * (manutencao/reforma), que podem se aplicar a uma sala especifica
 * ou a um bloco geografico inteiro.
 *
 * @author Sistema de Reservas
 */
public class PainelIndisponibilidades extends JPanel
{
    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final String[] LETRAS_BLOCO = {
        "A","B","C","D","E","F","G","H","I","J","K","L","M",
        "N","O","P","Q","R","S","T","U","V"
    };

    private SistemaReservas sistema;
    private JanelaPrincipal janelaPrincipal;

    private JRadioButton radioSala;
    private JRadioButton radioBloco;
    private JComboBox<Sala> comboSala;
    private JComboBox<String> comboBloco;
    private JTextField campoMotivo;
    private JTextField campoDataInicio;
    private JTextField campoDataFim;
    private DefaultTableModel modeloTabela;
    private JTable tabela;

    public PainelIndisponibilidades(SistemaReservas sistema, JanelaPrincipal janelaPrincipal)
    {
        this.sistema = sistema;
        this.janelaPrincipal = janelaPrincipal;
        montarInterface();
        atualizarCombosEListagem();
    }

    private void montarInterface()
    {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel formulario = new JPanel(new GridLayout(0, 1, 5, 5));
        formulario.setBorder(BorderFactory.createTitledBorder("Cadastrar indisponibilidade"));

        JPanel linhaTipo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        radioSala = new JRadioButton("Sala especifica", true);
        radioBloco = new JRadioButton("Bloco inteiro");
        ButtonGroup grupo = new ButtonGroup();
        grupo.add(radioSala);
        grupo.add(radioBloco);

        comboSala = new JComboBox<>();
        comboBloco = new JComboBox<>(LETRAS_BLOCO);
        comboBloco.setEnabled(false);

        radioSala.addActionListener(e -> {
            comboSala.setEnabled(true);
            comboBloco.setEnabled(false);
        });
        radioBloco.addActionListener(e -> {
            comboSala.setEnabled(false);
            comboBloco.setEnabled(true);
        });

        linhaTipo.add(radioSala);
        linhaTipo.add(comboSala);
        linhaTipo.add(radioBloco);
        linhaTipo.add(new JLabel("Bloco:"));
        linhaTipo.add(comboBloco);
        formulario.add(linhaTipo);

        JPanel linhaMotivo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        linhaMotivo.add(new JLabel("Motivo:"));
        campoMotivo = new JTextField(30);
        linhaMotivo.add(campoMotivo);
        formulario.add(linhaMotivo);

        JPanel linhaDatas = new JPanel(new FlowLayout(FlowLayout.LEFT));
        linhaDatas.add(new JLabel("Data inicio (dd/mm/aaaa):"));
        campoDataInicio = new JTextField(10);
        linhaDatas.add(campoDataInicio);
        linhaDatas.add(new JLabel("Data fim (dd/mm/aaaa):"));
        campoDataFim = new JTextField(10);
        linhaDatas.add(campoDataFim);
        JButton botaoCadastrar = new JButton("Cadastrar Indisponibilidade");
        botaoCadastrar.addActionListener(e -> cadastrarIndisponibilidade());
        linhaDatas.add(botaoCadastrar);
        formulario.add(linhaDatas);

        add(formulario, BorderLayout.NORTH);

        modeloTabela = new DefaultTableModel(
            new Object[]{"Alvo", "Data Inicio", "Data Fim", "Motivo"}, 0)
        {
            @Override
            public boolean isCellEditable(int linha, int coluna)
            {
                return false;
            }
        };
        tabela = new JTable(modeloTabela);
        add(new JScrollPane(tabela), BorderLayout.CENTER);
    }

    private void cadastrarIndisponibilidade()
    {
        String motivo = campoMotivo.getText().trim();
        LocalDate inicio;
        LocalDate fim;

        if (motivo.isEmpty())
        {
            JOptionPane.showMessageDialog(this, "Informe o motivo da indisponibilidade.",
                "Campo obrigatorio", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try
        {
            inicio = LocalDate.parse(campoDataInicio.getText().trim(), FORMATO_DATA);
            fim = LocalDate.parse(campoDataFim.getText().trim(), FORMATO_DATA);
        }
        catch (DateTimeParseException ex)
        {
            JOptionPane.showMessageDialog(this,
                "Datas invalidas. Use o formato dd/mm/aaaa.",
                "Erro de formato", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (fim.isBefore(inicio))
        {
            JOptionPane.showMessageDialog(this,
                "A data fim nao pode ser anterior a data inicio.",
                "Erro de datas", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try
        {
            if (radioSala.isSelected())
            {
                Sala sala = (Sala) comboSala.getSelectedItem();
                if (sala == null)
                {
                    JOptionPane.showMessageDialog(this, "Cadastre ao menos uma sala primeiro.",
                        "Dados insuficientes", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                sistema.cadastrarIndisponibilidadeSala(sala.getCodigo(), motivo, inicio, fim);
            }
            else
            {
                String bloco = (String) comboBloco.getSelectedItem();
                sistema.cadastrarIndisponibilidadeBloco(bloco, motivo, inicio, fim);
            }

            campoMotivo.setText("");
            campoDataInicio.setText("");
            campoDataFim.setText("");
            atualizarCombosEListagem();
            janelaPrincipal.atualizarTudo();
            JOptionPane.showMessageDialog(this, "Indisponibilidade cadastrada com sucesso!");
        }
        catch (IllegalArgumentException ex)
        {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                "Erro ao cadastrar indisponibilidade", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Atualiza o combo de salas e a tabela de indisponibilidades.
     */
    public void atualizarCombosEListagem()
    {
        Object selecionada = comboSala.getSelectedItem();
        comboSala.removeAllItems();
        for (Sala s : sistema.getSalas())
        {
            comboSala.addItem(s);
        }
        if (selecionada != null) comboSala.setSelectedItem(selecionada);

        modeloTabela.setRowCount(0);
        for (Indisponibilidade i : sistema.getIndisponibilidades())
        {
            modeloTabela.addRow(new Object[]{
                i.getAlvo(), i.getDataInicio(), i.getDataFim(), i.getMotivo()
            });
        }
    }
}
