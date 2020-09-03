package BD.Daos;
import java.sql.SQLException;

import BD.BDSQLServer;
import BD.Core.*;
import BD.Dbos.UsuarioTeste;

public class UsuariosTestes {

	//VERIFICA O USUÁRIO
	public static boolean achouUsuario(String idLoginPassado, String senhaPassada) throws Exception
	{
		MeuResultSet resultado = null;		
		try {
			
			String sql;
			
			sql = "ProcuraUsuario_sp " + 
			      "@login = " + idLoginPassado + ", "+
			      "@senha = " + senhaPassada;
			
			BDSQLServer.COMANDO.prepareStatement (sql);			
			
			resultado = (MeuResultSet) BDSQLServer.COMANDO.executeQuery ();
			
		    if(!resultado.first())
		    	throw new Exception ("Usuario nao encontrado!");
		}
		catch (SQLException erro)
        {
			
            throw new Exception(erro.getMessage());
        }
		
		return true;
	}
	
	// VERIFICA A SENHA
	public static UsuarioTeste getSenhaUsuario(String senhaPassada) throws Exception
	{
		UsuarioTeste usuario = null;
		
		try {
			
			String sql;
			sql = "ProcuraSenha2_sp @senha = " + senhaPassada;
			BDSQLServer.COMANDO.prepareStatement (sql);
			MeuResultSet resultado = (MeuResultSet)BDSQLServer.COMANDO.executeQuery ();
		    
            if (!resultado.first())
                throw new Exception ("Não existe tal login");
            
            usuario = new UsuarioTeste (resultado.getString ("ID"),
                      resultado.getString("LOGIN"),
                      resultado.getString ("SENHA"));
            
		}
		catch (SQLException erro)
        {
            throw new Exception(erro.getSQLState());
        }
		
		return usuario;
	}
	
	
	
 	
}
