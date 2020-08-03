package src;
import java.util.Objects;

/**
 * Classe Resultado, representa uma lista desordenada, permitindo repetição.
 *
 */
public class Resultado
{
	private int ra;
	private int cod;
	private float nota;
	private float freq;

	/**
	 * Gera o construtor da classe
	 * @param ra						Ra do aluno
	 * @param cod						Disciplina em questão
	 * @param nota						Nota do aluno na desciplina
	 * @param freq						Frequência do aluna na disciplina
	 * @throws Exception				Se um dos parâmetros estiver em um formato invalido
	 */
	public Resultado(int ra, int cod, float nota, float freq) throws Exception
	{
		setRa(ra);
		setCod(cod);
		setNota(nota);
		setFreq(freq);
	}

	/**
	 * Retorna o RA
	 * @return		RA do aluno
	 */
	public int getRa()
	{
		return ra;
	}

	/**
	 * Define um novo RA para o aluno
	 * @param ra				Ra do aluno
	 * @throws Exception		Se o Ra for nulo
	 */
	public void setRa(int ra) throws Exception
	{
		this.ra = ra;
	}

	/**
	 * Retorna o Código da disciplina
	 * @return			Código da disciplina
	 */
	public int getCod()
	{
		return cod;
	}

	/**
	 * Define um novo Código para a disciplina
	 * @param codDisciplina			Novo código
	 * @throws Exception			Se o código passado for nulo
	 */
	public void setCod(int codDisciplina) throws Exception
	{
		this.cod = codDisciplina;
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
	public float getFreq()
	{
		return freq;
	}

	/**
	 * Define uma nova frequência para o aluno
	 * @param frequencia	Nova frequência para o aluno
	 * @throws Exception	Se a frequencia for menor que 0 ou maior que 1
	 */
	public void setFreq(float frequencia) throws Exception
	{
		if((frequencia < 0 )|| (frequencia > 1))
		{
			throw new Exception("Frequência inválida");
		}

		this.freq = frequencia;
	}

	/**
	 * Rotorna os dados da classe em formato de String
	 * @return os dados da classe em formato de String
	 */
	@Override
	public String toString() {
		return "Resultado{" +
				"ra='" + ra + '\'' +
				", codDisciplina='" + cod + '\'' +
				", nota=" + nota +
				", frequencia=" + freq +
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
				&& Float.compare(resultado.freq, freq) == 0
				&& ra == resultado.ra
				&& cod == resultado.cod;
	}

	/**
	 * Calcula o hashcode da classe
	 */
	@Override
	public int hashCode() {
		return Objects.hash(ra, cod, nota, freq);
	}

	/**
	 * Retorna um clone desse objeto
	 */
	@Override
	public Object clone()
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
		this.cod = modelo.cod;
		this.nota = modelo.nota;
		this.freq = modelo.freq;
	}
}