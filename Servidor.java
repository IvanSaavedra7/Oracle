import java.util.*;

public class Servidor
{
	public static String PORTA_PADRAO = "3000";

    public static void main (String[] args)
    {
        if (args.length>1)
        {
            System.err.println ("NIAU: java Servidor [PORTA]\n");
            return;
        }

        String porta=Servidor.PORTA_PADRAO;

        if (args.length==1)
            porta = args[0];

        ArrayList<Parceiro> usuarios =
        new ArrayList<Parceiro> ();

        AceitadoraDeConexao aceitadoraDeConexao=null;
        try
        {
            aceitadoraDeConexao =
            new AceitadoraDeConexao (porta, usuarios);
            aceitadoraDeConexao.start();
        }
        catch (Exception erro)
        {
            System.err.println ("Escolha uma porta apropriada e liberada para uso!\n");
            return;
        }


        System.out.println ("O servidor esta ativo! Para desativa-lo,");
        System.out.println ("use o comando \"desativar\"\n");
        System.out.println ("ou se quiser ver os clientes conectados,");
        System.out.println ("use o comando \"listar usuarios\"\n");

        for(;;)
        {
        
            String comando=null;
            try
            {
                comando = Teclado.getUmString();
            }
            catch (Exception erro)
            {}


            if (comando.toLowerCase().equals("listar usuarios"))
            {
                for (Parceiro usuario:usuarios)
                {
                    try
                    {
                        System.out.println("cliente: "+usuario.getConexao().getInetAddress().getHostAddress());
                    }
                    catch (Exception erro)
                    {}
                }
            }



            if (comando.toLowerCase().equals("desativar"))
            {
                synchronized (usuarios)
                {
					ComunicadoDeDesligamento comunicadoDeDesligamento =
                    new ComunicadoDeDesligamento ();

                    for (Parceiro usuario:usuarios)
                    {
                        try
                        {
                            usuario.receba (comunicadoDeDesligamento);
                            usuario.adeus  ();
                        }
                        catch (Exception erro)
                        {}
                    }
                }

                System.out.println ("O servidor foi desativado!\n");
                System.exit(0);
            }

        }
    }
}
