import java.net.*;
import java.io.*;

public class Cliente // TESTE - RAFAEL2
{
	public static final String HOST_PADRAO  = "localhost";
	public static final int    PORTA_PADRAO = 3000;

	public static void main (String[] args)
	{
        if (args.length>2)
        {
            System.err.println ("Uso esperado: java Cliente [HOST [PORTA]]\n");
            return;
        }

        Socket conexao=null;
        try
        {
            String host = Cliente.HOST_PADRAO;
            int    porta= Cliente.PORTA_PADRAO;

            if (args.length>0)
                host = args[0];

            if (args.length==2)
                porta = Integer.parseInt(args[1]);

            conexao = new Socket (host, porta);
        }
        catch (Exception erro)
        {
            System.err.println ("Indique o servidor e a porta corretos!\n");
            return;
        }

        ObjectOutputStream transmissor=null;
        try
        {
            transmissor =
            new ObjectOutputStream(
            conexao.getOutputStream());
        }
        catch (Exception erro)
        {
            System.err.println ("Indique o servidor e a porta corretos!\n");
            return;
        }

        ObjectInputStream receptor=null;
        try
        {
            receptor =
            new ObjectInputStream(
            conexao.getInputStream());
        }
        catch (Exception erro)
        {
            System.err.println ("Indique o servidor e a porta corretos!\n");
            return;
        }

        Parceiro servidor=null;
        try
        {
            servidor =
            new Parceiro (conexao, receptor, transmissor);
        }
        catch (Exception erro)
        {
            System.err.println ("Indique o servidor e a porta corretos!\n");
            return;
        }

		///////////////////// CODIGO QUE CUIDA DA PARTE DE CASO O SERVIDOR DELIGAR ////////////////////

        TratadoraDeComunicadoDeDesligamento tratadoraDeComunicadoDeDesligamento = null;
        try
        {
			tratadoraDeComunicadoDeDesligamento = new TratadoraDeComunicadoDeDesligamento (servidor);
		}
		catch (Exception erro)
		{} // sei que servidor foi instanciado
		
		tratadoraDeComunicadoDeDesligamento.start();
		
        ////////////////////////////////////////////////////////////////////////////////////////////////
        
        System.out.println ("DIGITE OS COMANDOS PARA INTERAGIR COM O ORACULO: ");
		System.out.println ("* OU DIGITE '[T]erminar' PARA ENCERRAR ");
		System.out.println ("----------------------------------------------- ");

        String codigo=""; // CODIGO DO CLIENTE
        do
        {
			

            try
            {
				codigo = (Teclado.getUmString().toUpperCase());
		    }
		    catch (Exception erro)
		    {
				System.err.println ("Opcao invalida!\n");
				continue;
			}


			try
			{

				servidor.receba(new PedidoDeCodigo(codigo));

			
			}
			catch (Exception erro)
			{
				System.err.println ("Erro de comunicacao com o servidor;");
				System.err.println ("Tente novamente!");
				System.err.println ("Caso o erro persista, termine o programa");
				System.err.println ("e volte a tentar mais tarde!\n");
			}
        }
        while (!codigo.equals("[T]erminar"));

		try
		{
			servidor.receba (new PedidoParaSair ());
		}
		catch (Exception erro)
		{}
		
		System.out.println ("Obrigado por usar este programa!");
		System.exit(0);
	}
}
