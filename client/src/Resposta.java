package src;

public class Resposta {
	
	private int code;
	private String message;
	
	public Resposta (int code, String message) {
		setCode(code);
		setMessage(message);
	}
	
	public void setCode(int code) {
		this.code = code;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public int getCode() {
		return this.code;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public String toString() {
		return this.code + " " + this.message;
	}
	
	public boolean equals (Object obj)
    {
        if(this==obj)
            return true;

        if(obj==null)
            return false;

        if(this.getClass()!=obj.getClass())
            return false;

        Resposta res = (Resposta) obj;

        if (res.getCode() != this.code)
        	return false;
        
        if (res.getMessage() != this.message)
        	return false;

        return true;
    }
	
	public int hashCode ()
    {
        int ret=666;

        ret = ret*7 + this.code;
        ret = ret*7 + this.message.hashCode();

        if (ret<0)
            ret=-ret;

        return ret;
    }
	
	public Resposta (Resposta modelo) throws Exception
    {
        if(modelo == null)
            throw new Exception("Modelo ausente");
        
        this.code = modelo.getCode();
        this.message = modelo.getMessage();
    }
	
	public Object clone()
    {
		Resposta ret = null;

        try
        {
            ret = new Resposta(this);
        }
        catch(Exception erro) {}
        
        return ret;
    }
}
