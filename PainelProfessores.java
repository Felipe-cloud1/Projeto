import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 * Painel (aba) de cadastro e listagem de professores. Contem um
 * formulario (nome, matricula, departamento, nivel) e uma tabela
 * com todos os professores cadastrados.
 *
 * @author Sistema de Reservas
 */
public class PainelProfessores extends JPanel
{
    private SistemaReservas sistema;
    private JanelaPrincipal janelaPrincipal;

    private JTextField campoNome;
    private JTextField campoMatricula;
    private JTextField campoDepartamento;
    private JComboBox<NivelSenioridade> comboNivel;
    private DefaultTableModel modeloTabela;
    private JTable tabela;

    public PainelProfessores(SistemaReservas sistema, JanelaPrincipal janelaPrincipal)
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
        formulario.setBorder(BorderFactory.createTitledBorder("Cadastrar novo professor"));

        formulario.add(new JLabel("Nome:"));
        campoNome = new JTextField(15);
        formulario.add(campoNome);

        formulario.add(new JLabel("Matricula:"));
        campoMatricula = new JTextField(8);
        formulario.add(campoMatricula);

        formulario.add(new JLabel("Departamento:"));
        campoDepartamento = new JTextField(12);
        formulario.add(campoDepartamento);

        formulario.add(new JLabel("Nivel:"));
        comboNivel = new JComboBox<>(NivelSenioridade.values());
        formulario.add(comboNivel);

        JButton botaoCadastrar = new JButton("Cadastrar Professor");
        botaoCadastrar.addActionListener(e -> cadastrarProfessor());
        formulario.add(botaoCadastrar);

        add(formulario, BorderLayout.NORTH);

        modeloTabela = new DefaultTableModel(
            new Object[]{"Nome", "Matricula", "Departamento", "Nivel"}, 0)
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

    private void cadastrarProfessor()
    {
        String nome = campoNome.getText().trim();
        String matricula = campoMatricula.getText().trim();
        String departamento = campoDepartamento.getText().trim();
        NivelSenioridade nivel = (NivelSenioridade) comboNivel.getSelectedItem();

        if (nome.isEmpty() || matricula.isEmpty() || departamento.isEmpty())
        {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos.",
                "Campos obrigatorios", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try
        {
            sistema.cadastrarProfessor(nome, matricula, departamento, nivel);
            campoNome.setText("");
            campoMatricula.setText("");
            campoDepartamento.setText("");
            atualizarListagem();
            janelaPrincipal.atualizarTudo();
            JOptionPane.showMessageDialog(this, "Professor cadastrado com sucesso!");
        }
        catch (IllegalArgumentException ex)
        {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                "Erro ao cadastrar professor", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Atualiza a tabela de professores com os dados atuais do sistema.
     */
    public void atualizarListagem()
    {
        modeloTabela.setRowCount(0);
        for (Professor p : sistema.getProfessores())
        {
            modeloTabela.addRow(new Object[]{
                p.getNome(), p.getMatricula(), p.getDepartamento(), p.getNivel()
            });
        }
    }
}
