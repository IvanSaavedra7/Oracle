package cliente;
import java.net.*;
import java.util.Scanner;
import java.io.*;

import bd.daos.UsuariosTestes;

public class Cliente
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
        
        System.out.println ("Por favor, digite o nome de usuÃ¡rio: ");
        String usuario  = ""; //NOME DO USUÃ�RIO
		System.out.println ("* OU DIGITE '[T]erminar' PARA ENCERRAR ");
		System.out.println ("----------------------------------------------- ");

        do
        {
			

            try {
                 usuario = (Teclado.getUmString());
                 
                 //UsuariosTestes.getUsuarioTeste(usuario, senhaPassada)

            }
            catch(Exception err){

                System.err.println("Por favor digite um usÃ¡rio existente!!\n");
            }
            
            System.out.println("Por favor, digite a senha de usuário:");
            String senha= "";
            try
            {
				senha = (Teclado.getUmString().toUpperCase());
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
