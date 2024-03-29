package bd.daos;
import java.sql.SQLException;

import bd.BDSQLServer;
import bd.core.*;
import bd.dbos.UsuarioTeste;

public class UsuariosTestes {

	//VERIFICA O USU�RIO
	public static boolean procurarUsuarioTeste(String idLoginPassado, String senhaPassada) throws Exception
	{
		MeuResultSet resultado = null;		
		try {
			
			String sql;
			
			sql = "ProcuraUsuario_sp " + 
			      "@login = "          + idLoginPassado +
			      "@senha = "          + senhaPassada;
			
			BDSQLServer.COMANDO.prepareStatement (sql);
			resultado = (MeuResultSet) BDSQLServer.COMANDO.executeQuery ();
		    
			return resultado.first();
		}
		catch (SQLException erro)
        {
            throw new Exception("Usu�rio n�o encontrado!");
        }

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
                throw new Exception ("N�o existe tal login");
            
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
