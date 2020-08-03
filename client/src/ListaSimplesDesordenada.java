package src;

import java.lang.reflect.*;

/**
 * Classe Lista, representa uma lista desordenada, permitindo repetição.
 *
 * @param <X> tipo a ser armazenado
 */
public class ListaSimplesDesordenada <X>
{
	/**
	 * Classe Nó, representa um elemento da lista.
	 */
	protected class No
    {
        private X  info;
        private No prox;
        
		/**
		 * Construtor de classe.
		 * @param i Os dados deste Nó.
		 * @param p Nó seguinte a este.
		 */
        public No (X i, No p)
        {
            this.info = i;
            this.prox = p;
        }

		/**
		 * Construtor de classe.
		 * @param i Os dados deste Nó.
		 */
        public No (X i)
        {
            this.info = i;
            this.prox = null;
        }

		/**
		 * Get dos dados armazenados neste Nó.
		 * @return os dados deste Nó
		 */
        public X getInfo ()
        {
            return this.info;
        }

		/**
		 * Get do Nó seguinte a este.
		 * @return o próximo Nó
		 */
        public No getProx ()
        {
            return this.prox;
        }

		/**
		 * Define os dados deste Nó.
		 * @param i os dados deste Nó
		 */
        public void setInfo (X i)
        {
            this.info = i;
        }

		/**
		 * Define o Nó seguinte a este
		 * @param p o próximo Nó
		 */
        public void setProx (No p)
        {
            this.prox = p;
        }
    }
	
	protected No primeiro, ultimo;

	/**
	 * Construtor de classe
	 */
    public ListaSimplesDesordenada ()
    {
		this.primeiro=this.ultimo=null;
	}

	/**
	 * Retorna um clone do objeto.
	 * @param x objeto a ser clonado
	 * @return clone de x
	 */
    protected X meuCloneDeX (X x)
    {
        X ret=null;

        try
        {
            Class<?> classe = x.getClass();
            Class<?>[] tiposDosParms = null; 
            Method metodo = classe.getMethod("clone",tiposDosParms);
            Object[] parms = null; 
            ret = (X)metodo.invoke(x,parms);
        }
        catch (Exception erro)
        {} 

        return ret;
    }

	/**
	 * Insere um Nó no fim da lista.
	 * @param i Nó a ser inserido
	 * @throws Exception caso i seja nulo
	 * @throws Exception caso i já exista na lista
	 */
    public void insiraNoFim (X i) throws Exception
    {
        if (i==null)
            throw new Exception ("Informacao ausente");

        X inserir=null;
        if (i instanceof Cloneable)
            inserir = meuCloneDeX (i);
        else
            inserir = i;
            
        if (this.ultimo==null) 
        {
            this.ultimo   = new No (inserir);
            this.primeiro = this.ultimo;
        }
        else
        {
            this.ultimo.setProx (new No (inserir));
            this.ultimo = this.ultimo.getProx();
        }
    }
    
	/**
	 * Remove o Nó do início da lista.
	 * @throws Exception caso a lista esteja vazia
	 */
    public void removaDoInicio () throws Exception
    {
        if (this.primeiro==null)
            throw new Exception ("Nada a remover");

        if (this.primeiro==this.ultimo) 
        {
            this.primeiro=null;
            this.ultimo  =null;
            return;
        }

        this.primeiro = this.primeiro.getProx();
    }

	/**
	 * Get das informações do primeiro Nó
	 * @return dados do primeiro Nó
	 * @throws Exception caso a lista esteja vazia
	 */
    public X getDoInicio () throws Exception
    {
        if (this.primeiro==null)
            throw new Exception ("Nada a obter");

        X ret = this.primeiro.getInfo();
        if (ret instanceof Cloneable)
            ret = meuCloneDeX (ret);
            
        return ret;
    }

    /**
     * retorna true se a lista estiver vazia
     * @return
     */
    public boolean isVazia()
    {
        return this.primeiro == null;
    }
    
    /**
	 * Get da quantidade de Nós na lista
	 * @return a quantidade de Nós na lista
	 */
    public int getQtd() {
		int ret = 0;

		No atual = this.primeiro;

		while (atual != null) {
			ret++;
			atual = atual.getProx();
		}

		return ret;
	}
    
	/**
	 * Rotorna os dados da classe em formato de String
	 */
    public String toString ()
    {
        String ret="[";

        No atual=this.primeiro;

        while (atual!=null)
        {
            ret=ret+atual.getInfo();

            if (atual!=this.ultimo)
                ret=ret+",";

            atual=atual.getProx();
        }

        return ret+"]";
    }

	/**
	 * Verifica se um obj é igual a este objeto
	 * @param obj		Objeto a ser comparado com este objeto
	 */
    public boolean equals (Object obj)
    {
        if (this==obj)
            return true;

        if (obj==null)
            return false;

        if (this.getClass()!=obj.getClass())
            return false;

        ListaSimplesDesordenada<X> lista =
       (ListaSimplesDesordenada<X>)obj;

        No atualThis =this .primeiro;
        No atualLista=lista.primeiro;

        while (atualThis!=null && atualLista!=null)
        {
            if (!atualThis.getInfo().equals(atualLista.getInfo()))
                return false;

            atualThis  = atualThis .getProx();
            atualLista = atualLista.getProx();
        }

        if (atualThis!=null)
            return false;

        if (atualLista!=null)
            return false;

        return true;
    }

	/**
	 * Calcula o hashcode da classe
	 */
    public int hashCode ()
    {
        final int PRIMO = 13;
        
        int ret=666; 

        for (No atual=this.primeiro;
             atual!=null;
             atual=atual.getProx())
             ret = 17*ret + atual.getInfo().hashCode();

        if (ret<0) ret = -ret;

        return ret;
    }

	/**
	 * Construtor de cópia
	 * @param modelo lista a ser copiada
	 * @throws Exception caso modelo seja nulo
	 */
    public ListaSimplesDesordenada (ListaSimplesDesordenada<X> modelo) throws Exception
    {
        if (modelo==null)
            throw new Exception ("Modelo ausente");

        if (modelo.primeiro==null)
            return;

        this.primeiro = new No (modelo.primeiro.getInfo());

        No atualDoThis   = this  .primeiro;
        No atualDoModelo = modelo.primeiro.getProx();

        while (atualDoModelo!=null)
        {
            atualDoThis.setProx (new No (atualDoModelo.getInfo()));
            atualDoThis   = atualDoThis  .getProx ();
            atualDoModelo = atualDoModelo.getProx ();
        }

        this.ultimo = atualDoThis;
    }

	/**
	 * Retorna um clone desse objeto
	 */
    public Object clone ()
    {
        ListaSimplesDesordenada<X> ret=null;

        try
        {
            ret = new ListaSimplesDesordenada (this);
        }
        catch (Exception erro)
        {}

        return ret;
    }
}