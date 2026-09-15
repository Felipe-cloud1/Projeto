/**
 * Regra de negocio: salas do Bloco V (codigo iniciado em "CV") sao
 * de uso exclusivo do departamento CALEM (linguas estrangeiras).
 * Professores de outros departamentos nao podem reserva-las.
 *
 * @author Sistema de Reservas
 */
public class RegraRestricaoBlocoV implements RegraDeReserva
{
    @Override
    public ResultadoValidacao validar(Sala sala, Professor professor, DiaSemana dia,
                                       Horario horario, SistemaReservas sistema)
    {
        if (sala.isBlocoV() && !professor.pertenceAoCalem())
        {
            return ResultadoValidacao.falha(
                "Sala restrita ao departamento CALEM (Bloco V)");
        }
        return ResultadoValidacao.ok();
    }
}
