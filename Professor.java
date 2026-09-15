/**
 * Representa um professor cadastrado no sistema, com seus dados
 * institucionais e nivel de senioridade (usado para prioridade em
 * disputas de reserva de sala).
 *
 * @author Sistema de Reservas
 */
public class Professor implements Comparable<Professor>
{
    private String nome;
    private String matricula;
    private String departamento;
    private NivelSenioridade nivel;

    /**
     * Cria um novo professor.
     * @param nome nome completo do professor
     * @param matricula matricula (identificador unico) do professor
     * @param departamento departamento ao qual o professor pertence
     * @param nivel nivel de senioridade (Junior, Pleno ou Senior)
     */
    public Professor(String nome, String matricula, String departamento, NivelSenioridade nivel)
    {
        this.nome = nome;
        this.matricula = matricula;
        this.departamento = departamento;
        this.nivel = nivel;
    }

    public String getNome()
    {
        return nome;
    }

    public String getMatricula()
    {
        return matricula;
    }

    public String getDepartamento()
    {
        return departamento;
    }

    public NivelSenioridade getNivel()
    {
        return nivel;
    }

    /**
     * @return true se o professor pertence ao departamento CALEM
     *         (linguas estrangeiras), unico autorizado a reservar
     *         salas do Bloco V.
     */
    public boolean pertenceAoCalem()
    {
        return departamento != null && departamento.equalsIgnoreCase("CALEM");
    }

    /**
     * Compara professores pela senioridade (usado para ordenacao em
     * listas de prioridade). Senior > Pleno > Junior.
     */
    @Override
    public int compareTo(Professor outro)
    {
        return outro.nivel.ordinal() - this.nivel.ordinal();
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (!(obj instanceof Professor)) return false;
        Professor outro = (Professor) obj;
        return matricula != null && matricula.equals(outro.matricula);
    }

    @Override
    public int hashCode()
    {
        return matricula != null ? matricula.hashCode() : 0;
    }

    @Override
    public String toString()
    {
        return nome + " (" + matricula + " - " + departamento + " - " + nivel + ")";
    }
}
