package BD.Dbos;

public class UsuarioTeste implements Cloneable
{
    private String id, login, senha;
 
    public void setId (String id) throws Exception
    {
        if (id==null || id.equals(""))
            throw new Exception ("ID não fornecido.");

        this.id = id;
    }   

    public void setLogin (String login) throws Exception
    {
        if (login==null || login.equals(""))
            throw new Exception ("Login não fornecido.");

        this.login = login;
    }

    public void setSenha (String senha) throws Exception
    {
        if (senha == null || senha.equals(""))
            throw new Exception ("Senha não fornecido.");

        this.senha = senha;
    }

    public String getId    ()
    {
        return this.id;
    }

    public String getLogin ()
    {
        return this.login;
    }

    public String getSenha ()
    {
        return this.senha;
    }

    public UsuarioTeste (String id, String login, String senha) throws Exception
    {
        this.setId 	   (id);
        this.setLogin  (login);
        this.setSenha  (senha);
    }
    
    public String toString ()
    {
        String ret = "";

        ret+="Id.....: " + this.id     + "\n";
        ret+="Login..: " + this.login  + "\n";
        ret+="Senha..: " + this.senha;

        return ret;
    }

    public boolean equals (Object obj)
    {
        if (this==obj)
            return true;

        if (obj==null)
            return false;

        if (!(obj instanceof UsuarioTeste))
            return false;

        UsuarioTeste aluno = (UsuarioTeste) obj;

        if (this.id!=aluno.id)
            return false;

        if (!this.login.equals(aluno.login))
            return false;

        if (!this.senha.equals(aluno.senha))
            return false;

        return true;
    }

    public int hashCode ()
    {
        int ret=666;

        ret = 7*ret + this.id.hashCode();
        ret = 7*ret + this.login.hashCode();
        ret = 7*ret + this.senha.hashCode();

        return ret;
    }


    public UsuarioTeste (UsuarioTeste modelo) throws Exception
    {
        this.id 	= modelo.id; // nao clono, pq nao eh objeto
        this.login  = modelo.login;   // nao clono, pq nao eh clonavel
        this.senha  = modelo.senha;  // nao clono, pq nao eh clonavel
    }

    public Object clone ()
    {
        UsuarioTeste ret=null;

        try
        {
            ret = new UsuarioTeste (this);
        }
        catch (Exception erro)
        {} // nao trato, pq this nunca é null e construtor de
           // copia da excecao qdo seu parametro for null

        return ret;
    }
}