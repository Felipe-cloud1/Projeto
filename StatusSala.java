/**
 * Representa o status de uma sala em uma consulta de disponibilidade
 * (dia + horario especificos): se esta livre, ocupada ou indisponivel,
 * junto com o detalhe/motivo. Usada para preencher a tabela de status
 * na interface grafica, garantindo que nenhuma sala seja ocultada.
 *
 * @author Sistema de Reservas
 */
public class StatusSala
{
    private Sala sala;
    private String status;   // "Livre", "Ocupada" ou "Indisponivel"
    private String detalhe;

    public StatusSala(Sala sala, String status, String detalhe)
    {
        this.sala = sala;
        this.status = status;
        this.detalhe = detalhe;
    }

    public Sala getSala()
    {
        return sala;
    }

    public String getStatus()
    {
        return status;
    }

    public String getDetalhe()
    {
        return detalhe;
    }
}
