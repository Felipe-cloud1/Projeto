import java.time.LocalDate;

/**
 * Regra de negocio: impede reservas em salas (ou blocos inteiros)
 * que estejam marcados como indisponiveis por manutencao/reforma
 * programada, considerando a data atual do sistema.
 *
 * @author Sistema de Reservas
 */
public class RegraIndisponibilidade implements RegraDeReserva
{
    @Override
    public ResultadoValidacao validar(Sala sala, Professor professor, DiaSemana dia,
                                       Horario horario, SistemaReservas sistema)
    {
        Indisponibilidade indisponibilidade = sistema.buscarIndisponibilidadeAtiva(sala, LocalDate.now());

        if (indisponibilidade != null)
        {
            return ResultadoValidacao.falha(
                "Indisponivel: " + indisponibilidade.getMotivo());
        }
        return ResultadoValidacao.ok();
    }
}
