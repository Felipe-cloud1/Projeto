import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 * Painel (aba) de consulta do status das salas para um dia/horario
 * escolhidos. Exibe TODAS as salas cadastradas (livres, ocupadas e
 * indisponiveis), conforme a regra de visibilidade do sistema, com
 * o motivo detalhado para as que nao estao livres.
 *
 * @author Sistema de Reservas
 */
public class PainelStatusSalas extends JPanel
{
    private SistemaReservas sistema;

    private JComboBox<DiaSemana> comboDia;
    private JComboBox<Horario> comboHorario;
    private DefaultTableModel modeloTabela;
    private JTable tabela;

    public PainelStatusSalas(SistemaReservas sistema)
    {
        this.sistema = sistema;
        montarInterface();
        consultar();
    }

    private void montarInterface()
    {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel filtro = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filtro.setBorder(BorderFactory.createTitledBorder("Consultar disponibilidade"));

        filtro.add(new JLabel("Dia:"));
        comboDia = new JComboBox<>(DiaSemana.values());
        filtro.add(comboDia);

        filtro.add(new JLabel("Horario:"));
        comboHorario = new JComboBox<>(Horario.values());
        filtro.add(comboHorario);

        JButton botaoConsultar = new JButton("Consultar");
        botaoConsultar.addActionListener(e -> consultar());
        filtro.add(botaoConsultar);

        add(filtro, BorderLayout.NORTH);

        modeloTabela = new DefaultTableModel(
            new Object[]{"Sala", "Status", "Detalhe"}, 0)
        {
            @Override
            public boolean isCellEditable(int linha, int coluna)
            {
                return false;
            }
        };
        tabela = new JTable(modeloTabela);
        tabela.getColumnModel().getColumn(1).setCellRenderer(new RendererStatus());
        add(new JScrollPane(tabela), BorderLayout.CENTER);
    }

    private void consultar()
    {
        DiaSemana dia = (DiaSemana) comboDia.getSelectedItem();
        Horario horario = (Horario) comboHorario.getSelectedItem();

        modeloTabela.setRowCount(0);
        for (StatusSala status : sistema.consultarStatusSalas(dia, horario))
        {
            modeloTabela.addRow(new Object[]{
                status.getSala().getCodigo(), status.getStatus(), status.getDetalhe()
            });
        }
    }

    /**
     * Atualiza os combos de dia/horario (nao mudam, mas mantido por
     * padronizacao com os demais paineis) e reconsulta o status.
     */
    public void atualizarCombos()
    {
        consultar();
    }

    /**
     * Renderer simples que colore a celula de status: verde para
     * Livre, laranja para Ocupada, vermelho para Indisponivel.
     */
    private static class RendererStatus extends DefaultTableCellRenderer
    {
        @Override
        public Component getTableCellRendererComponent(JTable tabela, Object valor,
            boolean selecionado, boolean foco, int linha, int coluna)
        {
            Component c = super.getTableCellRendererComponent(
                tabela, valor, selecionado, foco, linha, coluna);

            String status = String.valueOf(valor);
            if (status.equals("Livre"))
            {
                c.setBackground(new Color(200, 255, 200));
            }
            else if (status.equals("Ocupada"))
            {
                c.setBackground(new Color(255, 230, 180));
            }
            else
            {
                c.setBackground(new Color(255, 200, 200));
            }
            return c;
        }
    }
}
