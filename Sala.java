import java.util.regex.Pattern;

/**
 * Representa uma sala da instituicao. O codigo da sala segue o
 * formato obrigatorio "SBNN":
 *   S = Sede, sempre a letra 'C' (Centro)
 *   B = Bloco, letra de 'A' a 'V'
 *   NN = numero da sala, dois digitos de '00' a '09'
 *
 * @author Sistema de Reservas
 */
public class Sala
{
    // C fixo + bloco de A a V + numero de 00 a 09
    // (primeiro digito sempre '0', segundo digito de 0 a 9)
    private static final Pattern PADRAO_CODIGO =
        Pattern.compile("^C[A-V]0[0-9]$");

    private String codigo;
    private String descricao;

    /**
     * Cria uma nova sala, validando o formato do codigo.
     * @param codigo codigo no formato CBNN (ex: "CA07")
     * @param descricao descricao livre da sala (ex: "Laboratorio de Informatica")
     * @throws IllegalArgumentException se o codigo nao seguir o padrao SBNN
     *         ou se o numero da sala nao estiver entre 00 e 09
     */
    public Sala(String codigo, String descricao)
    {
        if (!isCodigoValido(codigo))
        {
            throw new IllegalArgumentException(
                "Codigo de sala invalido: '" + codigo + "'. " +
                "Formato esperado: C + bloco (A-V) + numero (00-09). Ex: CA07");
        }
        this.codigo = codigo.toUpperCase();
        this.descricao = descricao;
    }

    /**
     * Valida se um codigo de sala segue o padrao institucional SBNN.
     * @param codigo o codigo a validar
     * @return true se o codigo eh valido
     */
    public static boolean isCodigoValido(String codigo)
    {
        if (codigo == null) return false;
        return PADRAO_CODIGO.matcher(codigo.toUpperCase()).matches();
    }

    public String getCodigo()
    {
        return codigo;
    }

    public String getDescricao()
    {
        return descricao;
    }

    /**
     * @return a letra do bloco geografico da sala (ex: "V" em "CV03")
     */
    public String getBloco()
    {
        return codigo.substring(1, 2);
    }

    /**
     * @return true se a sala pertence ao Bloco V (restrito ao CALEM)
     */
    public boolean isBlocoV()
    {
        return getBloco().equals("V");
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (!(obj instanceof Sala)) return false;
        return codigo.equals(((Sala) obj).codigo);
    }

    @Override
    public int hashCode()
    {
        return codigo.hashCode();
    }

    @Override
    public String toString()
    {
        return codigo + " - " + descricao;
    }
}
