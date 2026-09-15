/**
 * Contrato para uma regra de negocio aplicada no momento de uma
 * tentativa de reserva. Cada regra institucional (restricao de
 * bloco, conflito de horario, indisponibilidade por manutencao...)
 * eh implementada como uma classe separada, permitindo que o
 * SistemaReservas aplique todas elas de forma uniforme e que novas
 * regras sejam adicionadas sem alterar as classes existentes.
 *
 * @author Sistema de Reservas
 */
public interface RegraDeReserva
{
    /**
     * Valida se a reserva pretendida respeita esta regra.
     *
     * @param sala a sala pretendida
     * @param professor o professor que deseja reservar
     * @param dia o dia da semana pretendido
     * @param horario o bloco de horario pretendido
     * @param sistema referencia ao sistema, para consultar reservas
     *                e indisponibilidades ja existentes
     * @return um ResultadoValidacao indicando se a reserva pode
     *         prosseguir e, caso nao possa, o motivo
     */
    ResultadoValidacao validar(Sala sala, Professor professor, DiaSemana dia,
                                Horario horario, SistemaReservas sistema);
}
