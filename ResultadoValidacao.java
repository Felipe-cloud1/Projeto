/**
 * Representa o resultado de uma validacao de regra de negocio:
 * se passou (valido) e, caso nao tenha passado, o motivo.
 * Tambem eh reaproveitada para expressar o status de uma sala
 * (livre/ocupada/indisponivel) na consulta de disponibilidade.
 *
 * @author Sistema de Reservas
 */
public class ResultadoValidacao
{
    private boolean valido;
    private String motivo;

    public ResultadoValidacao(boolean valido, String motivo)
    {
        this.valido = valido;
        this.motivo = motivo;
    }

    /**
     * @return um resultado de validacao positivo (sem restricoes)
     */
    public static ResultadoValidacao ok()
    {
        return new ResultadoValidacao(true, "Livre");
    }

    /**
     * @return um resultado de validacao negativo, com o motivo informado
     */
    public static ResultadoValidacao falha(String motivo)
    {
        return new ResultadoValidacao(false, motivo);
    }

    public boolean isValido()
    {
        return valido;
    }

    public String getMotivo()
    {
        return motivo;
    }

    @Override
    public String toString()
    {
        return motivo;
    }
}
