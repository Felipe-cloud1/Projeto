import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;

/**
 * Janela principal da aplicacao. Organiza as funcionalidades do
 * sistema em abas (JTabbedPane): Salas, Professores, Reservas,
 * Indisponibilidades e Status das Salas.
 *
 * @author Sistema de Reservas
 */
public class JanelaPrincipal extends JFrame
{
    private SistemaReservas sistema;
    private PainelSalas painelSalas;
    private PainelProfessores painelProfessores;
    private PainelReservas painelReservas;
    private PainelIndisponibilidades painelIndisponibilidades;
    private PainelStatusSalas painelStatusSalas;

    public JanelaPrincipal(SistemaReservas sistema)
    {
        super("Sistema de Gerenciamento e Reserva de Salas Universitarias");
        this.sistema = sistema;
        montarInterface();
    }

    private void montarInterface()
    {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        JTabbedPane abas = new JTabbedPane();

        painelSalas = new PainelSalas(sistema, this);
        painelProfessores = new PainelProfessores(sistema, this);
        painelReservas = new PainelReservas(sistema, this);
        painelIndisponibilidades = new PainelIndisponibilidades(sistema, this);
        painelStatusSalas = new PainelStatusSalas(sistema);

        abas.addTab("Salas", painelSalas);
        abas.addTab("Professores", painelProfessores);
        abas.addTab("Reservas", painelReservas);
        abas.addTab("Indisponibilidades", painelIndisponibilidades);
        abas.addTab("Status das Salas", painelStatusSalas);

        add(abas);
    }

    /**
     * Atualiza os combos/listas de todos os paineis que dependem de
     * salas, professores, reservas ou indisponibilidades. Chamado
     * sempre que um cadastro eh alterado em qualquer aba.
     */
    public void atualizarTudo()
    {
        painelSalas.atualizarListagem();
        painelProfessores.atualizarListagem();
        painelReservas.atualizarCombos();
        painelIndisponibilidades.atualizarCombosEListagem();
        painelStatusSalas.atualizarCombos();
    }
}
