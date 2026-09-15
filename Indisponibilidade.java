import java.time.LocalDate;

/**
 * Representa um periodo de indisponibilidade programada (ex: reforma
 * ou manutencao) que pode se aplicar a uma sala especifica ou a um
 * bloco geografico inteiro.
 *
 * @author Sistema de Reservas
 */
public class Indisponibilidade
{
    private String salaEspecifica; // null se for para o bloco inteiro
    private String bloco;          // usado quando salaEspecifica == null
    private String motivo;
    private LocalDate dataInicio;
    private LocalDate dataFim;

    /**
     * Cria uma indisponibilidade para uma sala especifica.
     */
    public static Indisponibilidade paraSala(String codigoSala, String motivo,
                                              LocalDate dataInicio, LocalDate dataFim)
    {
        Indisponibilidade i = new Indisponibilidade();
        i.salaEspecifica = codigoSala.toUpperCase();
        i.bloco = null;
        i.motivo = motivo;
        i.dataInicio = dataInicio;
        i.dataFim = dataFim;
        return i;
    }

    /**
     * Cria uma indisponibilidade para um bloco geografico inteiro.
     */
    public static Indisponibilidade paraBloco(String bloco, String motivo,
                                               LocalDate dataInicio, LocalDate dataFim)
    {
        Indisponibilidade i = new Indisponibilidade();
        i.salaEspecifica = null;
        i.bloco = bloco.toUpperCase();
        i.motivo = motivo;
        i.dataInicio = dataInicio;
        i.dataFim = dataFim;
        return i;
    }

    /**
     * Verifica se esta indisponibilidade se aplica a sala informada,
     * na data informada.
     */
    public boolean afetaSala(Sala sala, LocalDate data)
    {
        boolean dentroDoPeriodo = !data.isBefore(dataInicio) && !data.isAfter(dataFim);
        if (!dentroDoPeriodo) return false;

        if (salaEspecifica != null)
        {
            return sala.getCodigo().equals(salaEspecifica);
        }
        return sala.getBloco().equals(bloco);
    }

    public String getMotivo()
    {
        return motivo;
    }

    public LocalDate getDataInicio()
    {
        return dataInicio;
    }

    public LocalDate getDataFim()
    {
        return dataFim;
    }

    public boolean isParaBlocoInteiro()
    {
        return salaEspecifica == null;
    }

    public String getAlvo()
    {
        return salaEspecifica != null ? "Sala " + salaEspecifica : "Bloco " + bloco + " (inteiro)";
    }

    @Override
    public String toString()
    {
        return getAlvo() + " | " + dataInicio + " a " + dataFim + " | Motivo: " + motivo;
    }
}
