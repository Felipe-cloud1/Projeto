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
import javax.swing.table.DefaultTableModel;

/**
 * Painel (aba) de reservas. Permite selecionar sala, professor, dia
 * e horario atraves de combos, tentar efetuar a reserva (aplicando
 * todas as regras de negocio) e visualizar as reservas ja efetuadas.
 *
 * @author Sistema de Reservas
 */
public class PainelReservas extends JPanel
{
    private SistemaReservas sistema;
    private JanelaPrincipal janelaPrincipal;

    private JComboBox<Sala> comboSala;
    private JComboBox<Professor> comboProfessor;
    private JComboBox<DiaSemana> comboDia;
    private JComboBox<Horario> comboHorario;
    private DefaultTableModel modeloTabela;
    private JTable tabela;

    public PainelReservas(SistemaReservas sistema, JanelaPrincipal janelaPrincipal)
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
        formulario.setBorder(BorderFactory.createTitledBorder("Nova reserva"));

        formulario.add(new JLabel("Sala:"));
        comboSala = new JComboBox<>();
        formulario.add(comboSala);

        formulario.add(new JLabel("Professor:"));
        comboProfessor = new JComboBox<>();
        formulario.add(comboProfessor);

        formulario.add(new JLabel("Dia:"));
        comboDia = new JComboBox<>(DiaSemana.values());
        formulario.add(comboDia);

        formulario.add(new JLabel("Horario:"));
        comboHorario = new JComboBox<>(Horario.values());
        formulario.add(comboHorario);

        JButton botaoReservar = new JButton("Reservar");
        botaoReservar.addActionListener(e -> efetuarReserva());
        formulario.add(botaoReservar);

        add(formulario, BorderLayout.NORTH);

        modeloTabela = new DefaultTableModel(
            new Object[]{"Sala", "Dia", "Horario", "Professor", "Nivel"}, 0)
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

    private void efetuarReserva()
    {
        Sala sala = (Sala) comboSala.getSelectedItem();
        Professor professor = (Professor) comboProfessor.getSelectedItem();
        DiaSemana dia = (DiaSemana) comboDia.getSelectedItem();
        Horario horario = (Horario) comboHorario.getSelectedItem();

        if (sala == null || professor == null)
        {
            JOptionPane.showMessageDialog(this,
                "Cadastre ao menos uma sala e um professor antes de reservar.",
                "Dados insuficientes", JOptionPane.WARNING_MESSAGE);
            return;
        }

        ResultadoValidacao resultado = sistema.tentarReservar(sala, professor, dia, horario);

        if (resultado.isValido())
        {
            JOptionPane.showMessageDialog(this, "Reserva efetuada com sucesso!");
            atualizarListagem();
            janelaPrincipal.atualizarTudo();
        }
        else
        {
            JOptionPane.showMessageDialog(this, resultado.getMotivo(),
                "Reserva nao autorizada", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Atualiza os combos de sala e professor com os cadastros atuais.
     */
    public void atualizarCombos()
    {
        Object salaSelecionada = comboSala.getSelectedItem();
        comboSala.removeAllItems();
        for (Sala s : sistema.getSalas())
        {
            comboSala.addItem(s);
        }
        if (salaSelecionada != null) comboSala.setSelectedItem(salaSelecionada);

        Object professorSelecionado = comboProfessor.getSelectedItem();
        comboProfessor.removeAllItems();
        for (Professor p : sistema.getProfessores())
        {
            comboProfessor.addItem(p);
        }
        if (professorSelecionado != null) comboProfessor.setSelectedItem(professorSelecionado);
    }

    /**
     * Atualiza a tabela de reservas efetuadas.
     */
    public void atualizarListagem()
    {
        modeloTabela.setRowCount(0);
        for (Reserva r : sistema.getReservas())
        {
            modeloTabela.addRow(new Object[]{
                r.getSala().getCodigo(), r.getDia(), r.getHorario(),
                r.getProfessor().getNome(), r.getProfessor().getNivel()
            });
        }
    }
}
