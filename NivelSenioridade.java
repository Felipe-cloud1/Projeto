/**
 * Representa o nivel de senioridade de um professor.
 * A ordem de declaracao (JUNIOR, PLENO, SENIOR) eh usada diretamente
 * para calcular prioridade em disputas de reserva: quanto maior o
 * ordinal(), maior a prioridade.
 *
 * @author Sistema de Reservas
 */
public enum NivelSenioridade
{
    JUNIOR("Junior"),
    PLENO("Pleno"),
    SENIOR("Senior");

    private final String descricao;

    NivelSenioridade(String descricao)
    {
        this.descricao = descricao;
    }

    /**
     * Compara a prioridade deste nivel com outro.
     * @param outro o nivel a comparar
     * @return true se este nivel tem prioridade maior que o outro
     */
    public boolean temPrioridadeSobre(NivelSenioridade outro)
    {
        return this.ordinal() > outro.ordinal();
    }

    public String getDescricao()
    {
        return descricao;
    }

    @Override
    public String toString()
    {
        return descricao;
    }
}
