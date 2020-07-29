import java.lang.reflect.Method;

/**
 * Classe Fila.
 *
 * @param <X> tipo a ser armazenado
 */
public class Fila <X> 
{
	private ListaSimplesDesordenada<X> lista = new ListaSimplesDesordenada<X>();

	public Fila() {}

	/**
	 * Insere no fim da fila.
	 * @param x					Elemento a ser inserido
	 * @throws Exception		Se nenhum elemento foi passado
	 */
	public void addItem(X x) throws Exception
	{
		if (x==null)
            throw new Exception ("Falta o que guardar");
		
		if (x instanceof Cloneable)
			lista.insiraNoFim(meuCloneDeX(x));
        else
            lista.insiraNoFim(x);
	}
	
	/**
	 * Get das informações do primeiro elemento
	 * @return					O primeiro elemento da fila
	 * @throws Exception		Se a fila estiver vazia
	 */
	public X recuperarItem() throws Exception 
	{
		return lista.getDoInicio();
	}
	
	/**
	 * Remove do início da fila.
	 * @throws 				Caso a fila esteja vazia
	 */
	public void removerItem() throws Exception
	{
		lista.removaDoInicio();
	}
	
	/**
	 * Retorna um clone do objeto.
	 * @param x objeto a ser clonado
	 * @return clone de x
	 */
    private X meuCloneDeX(X x)
    {
        X ret  = null;

        try
        {
			Class<?> classe = x.getClass();
			Class<?>[] tipoDosParms = null;
			Method metodo = classe.getMethod("clone", tipoDosParms);
			Object[] parms = null;
			ret = (X)metodo.invoke(x, parms);
        }
        catch(Exception erro)
        {}

        return ret;
    }
    
    /**
	 * Get da quantidade de Nós na lista
	 * @return a quantidade de Nós na lista
	 */
    public int getQtd()
    {
    	return lista.getQtd();
    }
    
	/**
	 * Rotorna os dados da classe em formato de String
	 * @return os dados da classe em formato de String
	 */
    public String toString()
    {
    	int qtd = this.getQtd();
        String ret = qtd + " elementos";
        
        if (qtd != 0)
        {
			try 
        	{
				ret += ", sendo o primeiro  " + lista.getDoInicio();
			} 
        	catch (Exception e) 
        	{}
        }
        
        return ret;
    }
    
	/**
	 * Verifica se um obj é igual a este objeto
	 * @param obj		Objeto a ser comparado com este objeto
	 */
    public boolean equals (Object obj)
    {
        if(this==obj)
            return true;

        if(obj==null)
            return false;

        if(this.getClass()!=obj.getClass())
            return false;

        Fila<X> fil = (Fila<X>) obj;

        if(this.getQtd() != fil.getQtd())
            return false;
        
        if(!this.lista.equals(fil.lista))
        	return false;

        return true;
    }
    
	/**
	 * Calcula o hashcode da classe
	 */
    public int hashCode ()
    {
        int ret=666;

        ret = ret*7 + this.lista.hashCode();

        if (ret<0)
            ret=-ret;

        return ret;
    }
    
	/**
	 * Construtor de cópia
	 * @param modelo fila a ser copiada
	 * @throws Exception caso modelo seja nulo
	 */
    public Fila (Fila<X> modelo) throws Exception
    {
        if(modelo == null)
            throw new Exception("modelo ausente");
        
        this.lista = (ListaSimplesDesordenada<X>) modelo.lista.clone();
    }

	/**
	 * Retorna um clone desse objeto
	 */
    public Object clone()
    {
        Fila<X> ret = null;

        try
        {
            ret  = new Fila(this);
        }
        catch(Exception erro)
        {}

        return ret;
    }
}
