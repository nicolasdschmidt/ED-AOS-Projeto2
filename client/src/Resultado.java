import java.util.Objects;

/**
 * Classe Resultado, representa uma lista desordenada, permitindo repetição.
 *
 */
public class Resultado
{
	private String ra;
	private String codDisciplina;
	private float nota;
	private float frequencia;

	/**
	 * Gera o construtor da classe
	 * @param ra						Ra do aluno
	 * @param codDisciplina				Disciplina em questão
	 * @param nota						Nota do aluno na desciplina
	 * @param frequencia				Frequência do aluna na disciplina
	 * @throws Exception				Se um dos parâmetros estiver em um formato invalido
	 */
	public Resultado(String ra, String codDisciplina, float nota, float frequencia) throws Exception
	{
		setRa(ra);
		setCodDisciplina(codDisciplina);
		setNota(nota);
		setFrequencia(frequencia);
	}

	/**
	 * Retorna o RA
	 * @return		RA do aluno
	 */
	public String getRa()
	{
		return ra;
	}

	/**
	 * Define um novo RA para o aluno
	 * @param ra				Ra do aluno
	 * @throws Exception		Se o Ra for nulo
	 */
	public void setRa(String ra) throws Exception
	{
		if(ra.isEmpty())
		{
			throw new Exception("Ra vazio");
		}

		this.ra = ra;
	}

	/**
	 * Retorna o Código da disciplina
	 * @return			Código da disciplina
	 */
	public String getCodDisciplina()
	{
		return codDisciplina;
	}

	/**
	 * Define um novo Código para a disciplina
	 * @param codDisciplina			Novo código
	 * @throws Exception			Se o código passado for nulo
	 */
	public void setCodDisciplina(String codDisciplina) throws Exception
	{
		if(codDisciplina.isEmpty())
		{
			throw new Exception("Codigo da disciplina vazio");
		}
		this.codDisciplina = codDisciplina;
	}

	/**
	 * Retorna a nota do aluna
	 * @return					Nota do aluno
	 */
	public float getNota()
	{
		return nota;
	}

	/**
	 * Define uma nova nota para o aluno
	 * @param nota				Nova nota para o aluno
	 * @throws Exception		Se a nota for menor que 0 ou maior que 10
	 */
	public void setNota(float nota) throws Exception
	{
		if((nota < 0 )|| (nota > 10))
		{
			throw new Exception("Nota inválida");
		}
		this.nota = nota;
	}

	/**
	 * Retorna a frequencia do aluno na disciplina
	 * @return				Frequencia da disciplina
	 */
	public float getFrequencia()
	{
		return frequencia;
	}

	/**
	 * Define uma nova frequência para o aluno
	 * @param frequencia	Nova frequência para o aluno
	 * @throws Exception	Se a frequencia for menor que 0 ou maior que 1
	 */
	public void setFrequencia(float frequencia) throws Exception
	{
		if((frequencia < 0 )|| (frequencia > 1))
		{
			throw new Exception("Frequência inválida");
		}

		this.frequencia = frequencia;
	}

	/**
	 * Rotorna os dados da classe em formato de String
	 * @return os dados da classe em formato de String
	 */
	@Override
	public String toString() {
		return "Resultado{" +
				"ra='" + ra + '\'' +
				", codDisciplina='" + codDisciplina + '\'' +
				", nota=" + nota +
				", frequencia=" + frequencia +
				'}';
	}

	/**
	 * Verifica se um obj é igual a este objeto
	 * @param obj		Objeto a ser comparado com este objeto
	 */
	@Override
	public boolean equals(Object o) {

		if (this == o)
			return true;

		if (o == null || getClass() != o.getClass())
			return false;

		Resultado resultado = (Resultado) o;

		return Float.compare(resultado.nota, nota) == 0
				&& Float.compare(resultado.frequencia, frequencia) == 0
				&& ra.equals(resultado.ra)
				&& codDisciplina.equals(resultado.codDisciplina);
	}

	/**
	 * Calcula o hashcode da classe
	 */
	@Override
	public int hashCode() {
		return Objects.hash(ra, codDisciplina, nota, frequencia);
	}

	/**
	 * Retorna um clone desse objeto
	 */
	@Override
	protected Object clone()
	{
		Resultado ret = null;

		try
		{
			ret  = new Resultado(this);
		}
		catch(Exception erro)
		{}

		return ret;
	}

	/**
	 * Construtor de cópia
	 * @param modelo fila a ser copiada
	 * @throws Exception caso modelo seja nulo
	 */
	public Resultado(Resultado modelo) throws Exception
	{
		if(modelo == null)
			throw new Exception("modelo ausente");

		this.ra = modelo.ra;
		this.codDisciplina = modelo.codDisciplina;
		this.nota = modelo.nota;
		this.frequencia = modelo.frequencia;
	}
}
