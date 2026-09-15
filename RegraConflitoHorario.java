/**
 * Regra de negocio: impede que duas reservas ocupem a mesma sala,
 * no mesmo dia da semana e no mesmo bloco de horario. Caso ja exista
 * uma reserva no espaco/tempo pretendido, a regra so permite a nova
 * reserva se o professor solicitante tiver senioridade maior que o
 * professor que ja ocupa o horario (prioridade por senioridade).
 * Quando isso ocorre, o SistemaReservas eh responsavel por substituir
 * a reserva antiga pela nova.
 *
 * @author Sistema de Reservas
 */
public class RegraConflitoHorario implements RegraDeReserva
{
    @Override
    public ResultadoValidacao validar(Sala sala, Professor professor, DiaSemana dia,
                                       Horario horario, SistemaReservas sistema)
    {
        Reserva existente = sistema.buscarReservaEm(sala, dia, horario);

        if (existente == null)
        {
            return ResultadoValidacao.ok();
        }

        if (existente.getProfessor().equals(professor))
        {
            return ResultadoValidacao.falha("Voce ja possui esta sala reservada neste horario");
        }

        boolean temPrioridade = professor.getNivel()
            .temPrioridadeSobre(existente.getProfessor().getNivel());

        if (temPrioridade)
        {
            return ResultadoValidacao.ok();
        }

        return ResultadoValidacao.falha(
            "Ocupada por outro professor: " + existente.getProfessor().getNome() +
            " (" + existente.getProfessor().getNivel() + ")");
    }
}
