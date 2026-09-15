/**
 * Representa os dias da semana em que ha aulas na instituicao.
 *
 * @author Sistema de Reservas
 */
public enum DiaSemana
{
    SEGUNDA("Segunda-feira"),
    TERCA("Terca-feira"),
    QUARTA("Quarta-feira"),
    QUINTA("Quinta-feira"),
    SEXTA("Sexta-feira"),
    SABADO("Sabado");

    private final String descricao;

    DiaSemana(String descricao)
    {
        this.descricao = descricao;
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
