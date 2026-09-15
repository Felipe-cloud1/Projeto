import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 * Painel (aba) de cadastro e listagem de salas. Contem um formulario
 * simples (codigo + descricao) e uma tabela com todas as salas
 * cadastradas.
 *
 * @author Sistema de Reservas
 */
public class PainelSalas extends JPanel
{
    private SistemaReservas sistema;
    private JanelaPrincipal janelaPrincipal;

    private JTextField campoCodigo;
    private JTextField campoDescricao;
    private DefaultTableModel modeloTabela;
    private JTable tabela;

    public PainelSalas(SistemaReservas sistema, JanelaPrincipal janelaPrincipal)
    {
        this.sistema = sistema;
        this.janelaPrincipal = janelaPrincipal;
        montarInterface();
        atualizarListagem();
    }

    private void montarInterface()
    {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel formulario = new JPanel(new FlowLayout(FlowLayout.LEFT));
        formulario.setBorder(BorderFactory.createTitledBorder("Cadastrar nova sala"));

        formulario.add(new JLabel("Codigo (ex: CA07):"));
        campoCodigo = new JTextField(8);
        formulario.add(campoCodigo);

        formulario.add(new JLabel("Descricao:"));
        campoDescricao = new JTextField(20);
        formulario.add(campoDescricao);

        JButton botaoCadastrar = new JButton("Cadastrar Sala");
        botaoCadastrar.addActionListener(e -> cadastrarSala());
        formulario.add(botaoCadastrar);

        add(formulario, BorderLayout.NORTH);

        modeloTabela = new DefaultTableModel(new Object[]{"Codigo", "Bloco", "Descricao"}, 0)
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

    private void cadastrarSala()
    {
        String codigo = campoCodigo.getText().trim();
        String descricao = campoDescricao.getText().trim();

        if (codigo.isEmpty() || descricao.isEmpty())
        {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos.",
                "Campos obrigatorios", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try
        {
            sistema.cadastrarSala(codigo, descricao);
            campoCodigo.setText("");
            campoDescricao.setText("");
            atualizarListagem();
            janelaPrincipal.atualizarTudo();
            JOptionPane.showMessageDialog(this, "Sala cadastrada com sucesso!");
        }
        catch (IllegalArgumentException ex)
        {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                "Erro ao cadastrar sala", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Atualiza a tabela de salas com os dados atuais do sistema.
     */
    public void atualizarListagem()
    {
        modeloTabela.setRowCount(0);
        for (Sala sala : sistema.getSalas())
        {
            modeloTabela.addRow(new Object[]{
                sala.getCodigo(), sala.getBloco(), sala.getDescricao()
            });
        }
    }
}
