package servidor;
public class PedidoDeCodigo extends Comunicado
{
    private String codigo;

    public PedidoDeCodigo (String codigo)
    {
        this.codigo = codigo;
    }

    public String getCodigo ()
    {
        return this.codigo;
    }

    public String toString ()
    {
        return (""+this.codigo);
    }
}
