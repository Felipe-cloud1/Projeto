/**
 * Representa uma reserva confirmada de uma sala, para um professor,
 * em um dia da semana e bloco de horario especificos.
 *
 * @author Sistema de Reservas
 */
public class Reserva
{
    private Sala sala;
    private Professor professor;
    private DiaSemana dia;
    private Horario horario;

    public Reserva(Sala sala, Professor professor, DiaSemana dia, Horario horario)
    {
        this.sala = sala;
        this.professor = professor;
        this.dia = dia;
        this.horario = horario;
    }

    public Sala getSala()
    {
        return sala;
    }

    public Professor getProfessor()
    {
        return professor;
    }

    public DiaSemana getDia()
    {
        return dia;
    }

    public Horario getHorario()
    {
        return horario;
    }

    /**
     * Verifica se esta reserva ocupa a mesma combinacao de sala,
     * dia e horario que outra (ou seja, se ha choque de agenda).
     */
    public boolean ocupaMesmoEspacoTempo(Sala outraSala, DiaSemana outroDia, Horario outroHorario)
    {
        return sala.equals(outraSala) && dia == outroDia && horario == outroHorario;
    }

    @Override
    public String toString()
    {
        return sala.getCodigo() + " | " + dia + " | " + horario +
               " | " + professor.getNome() + " (" + professor.getNivel() + ")";
    }
}
