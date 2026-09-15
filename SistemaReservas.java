import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe controladora do sistema. Mantem as colecoes de salas,
 * professores, reservas e indisponibilidades, e aplica a lista de
 * regras de negocio (RegraDeReserva) sempre que uma reserva eh
 * solicitada.
 *
 * @author Sistema de Reservas
 */
public class SistemaReservas
{
    private List<Sala> salas;
    private List<Professor> professores;
    private List<Reserva> reservas;
    private List<Indisponibilidade> indisponibilidades;
    private List<RegraDeReserva> regras;

    public SistemaReservas()
    {
        salas = new ArrayList<>();
        professores = new ArrayList<>();
        reservas = new ArrayList<>();
        indisponibilidades = new ArrayList<>();

        regras = new ArrayList<>();
        regras.add(new RegraRestricaoBlocoV());
        regras.add(new RegraIndisponibilidade());
        regras.add(new RegraConflitoHorario());
    }

    // ---------- Cadastro de Salas ----------

    /**
     * Cadastra uma nova sala no sistema.
     * @throws IllegalArgumentException se o codigo for invalido ou ja existir
     */
    public void cadastrarSala(String codigo, String descricao)
    {
        Sala nova = new Sala(codigo, descricao); // valida formato internamente
        for (Sala s : salas)
        {
            if (s.getCodigo().equals(nova.getCodigo()))
            {
                throw new IllegalArgumentException("Ja existe uma sala com o codigo " + nova.getCodigo());
            }
        }
        salas.add(nova);
    }

    public List<Sala> getSalas()
    {
        return new ArrayList<>(salas);
    }

    // ---------- Cadastro de Professores ----------

    public void cadastrarProfessor(String nome, String matricula, String departamento, NivelSenioridade nivel)
    {
        for (Professor p : professores)
        {
            if (p.getMatricula().equals(matricula))
            {
                throw new IllegalArgumentException("Ja existe um professor com a matricula " + matricula);
            }
        }
        professores.add(new Professor(nome, matricula, departamento, nivel));
    }

    public List<Professor> getProfessores()
    {
        return new ArrayList<>(professores);
    }

    // ---------- Indisponibilidades ----------

    public void cadastrarIndisponibilidadeSala(String codigoSala, String motivo,
                                                LocalDate inicio, LocalDate fim)
    {
        indisponibilidades.add(Indisponibilidade.paraSala(codigoSala, motivo, inicio, fim));
    }

    public void cadastrarIndisponibilidadeBloco(String bloco, String motivo,
                                                 LocalDate inicio, LocalDate fim)
    {
        indisponibilidades.add(Indisponibilidade.paraBloco(bloco, motivo, inicio, fim));
    }

    public List<Indisponibilidade> getIndisponibilidades()
    {
        return new ArrayList<>(indisponibilidades);
    }

    /**
     * Busca uma indisponibilidade ativa (na data informada) que afete
     * a sala informada, se houver.
     */
    public Indisponibilidade buscarIndisponibilidadeAtiva(Sala sala, LocalDate data)
    {
        for (Indisponibilidade i : indisponibilidades)
        {
            if (i.afetaSala(sala, data))
            {
                return i;
            }
        }
        return null;
    }

    // ---------- Reservas ----------

    /**
     * Busca a reserva existente para a combinacao exata de sala,
     * dia e horario, se houver.
     */
    public Reserva buscarReservaEm(Sala sala, DiaSemana dia, Horario horario)
    {
        for (Reserva r : reservas)
        {
            if (r.ocupaMesmoEspacoTempo(sala, dia, horario))
            {
                return r;
            }
        }
        return null;
    }

    /**
     * Tenta efetuar uma reserva, aplicando em sequencia todas as
     * regras de negocio cadastradas. Caso alguma regra reprove, a
     * reserva nao eh efetuada e o motivo eh retornado. Caso a reserva
     * seja aprovada por prioridade de senioridade sobre uma reserva
     * ja existente, a reserva antiga eh substituida pela nova.
     *
     * @return o resultado da tentativa (valido + motivo, ou invalido + motivo da falha)
     */
    public ResultadoValidacao tentarReservar(Sala sala, Professor professor, DiaSemana dia, Horario horario)
    {
        for (RegraDeReserva regra : regras)
        {
            ResultadoValidacao resultado = regra.validar(sala, professor, dia, horario, this);
            if (!resultado.isValido())
            {
                return resultado;
            }
        }

        // Todas as regras passaram: se havia uma reserva anterior
        // (que so passou por causa da prioridade de senioridade),
        // ela eh substituida.
        Reserva anterior = buscarReservaEm(sala, dia, horario);
        if (anterior != null)
        {
            reservas.remove(anterior);
        }

        reservas.add(new Reserva(sala, professor, dia, horario));
        return ResultadoValidacao.ok();
    }

    public List<Reserva> getReservas()
    {
        return new ArrayList<>(reservas);
    }

    /**
     * Para cada sala cadastrada, calcula o status (livre, ocupada ou
     * indisponivel) considerando o dia/horario informados, sem ocultar
     * nenhuma sala da listagem (conforme regra de visibilidade).
     *
     * @return lista de StatusSala, uma para cada sala cadastrada
     */
    public List<StatusSala> consultarStatusSalas(DiaSemana dia, Horario horario)
    {
        List<StatusSala> resultado = new ArrayList<>();
        for (Sala sala : salas)
        {
            Indisponibilidade indisponibilidade = buscarIndisponibilidadeAtiva(sala, LocalDate.now());
            if (indisponibilidade != null)
            {
                resultado.add(new StatusSala(sala, "Indisponivel",
                    "Indisponivel futuramente: " + indisponibilidade.getMotivo()));
                continue;
            }

            Reserva reserva = buscarReservaEm(sala, dia, horario);
            if (reserva != null)
            {
                resultado.add(new StatusSala(sala, "Ocupada",
                    "Ocupada por outro professor: " + reserva.getProfessor().getNome() +
                    " (" + reserva.getProfessor().getNivel() + ")"));
                continue;
            }

            resultado.add(new StatusSala(sala, "Livre", "Livre"));
        }
        return resultado;
    }
}
