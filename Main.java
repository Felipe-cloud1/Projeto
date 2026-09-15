import javax.swing.SwingUtilities;

/**
 * Ponto de entrada da aplicacao. No BlueJ, esta classe tambem pode
 * ser ignorada e a interface pode ser aberta diretamente criando um
 * objeto JanelaPrincipal (passando um SistemaReservas) e chamando
 * setVisible(true) pelo menu de contexto do objeto.
 *
 * @author Sistema de Reservas
 */
public class Main
{
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() -> {
            SistemaReservas sistema = new SistemaReservas();
            JanelaPrincipal janela = new JanelaPrincipal(sistema);
            janela.setVisible(true);
        });
    }
}
