/**
 * Representa os blocos fixos de 50 minutos da grade institucional de
 * horarios. Cada bloco ja carrega seu periodo (Manha/Tarde/Noite) e o
 * intervalo de hora de inicio/fim, evitando que essa informacao fique
 * espalhada ou digitada livremente pelo usuario.
 *
 * @author Sistema de Reservas
 */
public enum Horario
{
    M1("Manha", "07h30", "08h20"),
    M2("Manha", "08h20", "09h10"),
    M3("Manha", "09h10", "10h00"),
    M4("Manha", "10h20", "11h10"),
    M5("Manha", "11h10", "12h00"),
    M6("Manha", "12h00", "12h50"),

    T1("Tarde", "13h00", "13h50"),
    T2("Tarde", "13h50", "14h40"),
    T3("Tarde", "14h40", "15h30"),
    T4("Tarde", "15h50", "16h40"),
    T5("Tarde", "16h40", "17h30"),
    T6("Tarde", "17h50", "18h40"),

    N1("Noite", "18h40", "19h30"),
    N2("Noite", "19h30", "20h20"),
    N3("Noite", "20h20", "21h10"),
    N4("Noite", "21h20", "22h10"),
    N5("Noite", "22h10", "23h00");

    private final String periodo;
    private final String horaInicio;
    private final String horaFim;

    Horario(String periodo, String horaInicio, String horaFim)
    {
        this.periodo = periodo;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
    }

    public String getPeriodo()
    {
        return periodo;
    }

    public String getHoraInicio()
    {
        return horaInicio;
    }

    public String getHoraFim()
    {
        return horaFim;
    }

    /**
     * @return representacao completa, ex: "M1 (07h30-08h20)"
     */
    public String getDescricaoCompleta()
    {
        return name() + " (" + horaInicio + "-" + horaFim + ")";
    }

    @Override
    public String toString()
    {
        return getDescricaoCompleta();
    }
}
