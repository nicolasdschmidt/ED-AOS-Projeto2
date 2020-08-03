const express = require('express')
const bodyparser = require('body-parser')
const sql = require('mssql')
const app = express()
const port = 3000

app.use(bodyparser.json())
//app.use(bodyparser.urlencoded({ extended: true }))

// parâmetros de conexão ao banco de dados do SQL Server
const config = {
	server: 'regulus.cotuca.unicamp.br',
	authentication: {
		type: 'default',
		options: {
			userName: 'BD19191',
			password: 'senha123%',
		},
	},
	options: {
		database: 'BD19191',
		encrypt: false,
		enableArithAbort: true,
	},
}

// estabelece uma conexão global ao BD
const pool = new sql.ConnectionPool(config)
pool.connect((err) => {
	if (err != null) console.error(err)
})

// único método POST, recebe e processa os resultados de um aluno
app.post('/', (req, res) => {
	try {
		console.log('---\nBODY:', req.body)
		// receber parâmetro
		let ra = req.body.ra
		let cod = req.body.cod
		let nota = req.body.nota
		let freq = req.body.freq

		console.log(`ra: ${ra}; cod: ${cod}; nota: ${nota}; freq: ${freq}`)

		// verificar restrições simples dos parâmetros
		if (
			ra == undefined ||
			ra <= 0 ||
			ra > 32767 ||
			cod == undefined ||
			cod <= 0 ||
			nota == undefined ||
			nota < 0.0 ||
			nota > 10.0 ||
			freq == undefined ||
			freq < 0.0 ||
			freq > 1.0
		) {
			throw new Error('400 Bad Request: Parâmetros inválidos')
		}

		// chamar função que faz todo o processo
		processarDados(ra, cod, nota, freq, res)
	} catch (err) {
		/* caso ocorra algum erro, fazer parse da mensagem de erro
		 * para extrair o código de erro, e enviá-lo de volta pelo
		 * pedido HTTP
		 */
		let code = 500
		let c = Number(String(err).substring(7, 10))
		if (!isNaN(c)) code = c
		printarErro(String(err))
		res.status(code).send(String(err).replace('Error: ', ''))
	}
})

function printarErro(err) {
	console.error(`Retornado erro HTTP: ${err}`)
}

/**
 * Função que guia todo o processo de verificação dos dados, remoção da matrícula, e geração do resultado
 */
function processarDados(ra, cod, nota, freq, res) {
	let data = {
		ra: ra,
		cod: cod,
		nota: nota,
		freq: freq,
		res: res,
	}
	verificarAluno(data)
}

/**
 * Verifica se um aluno existe
 */
function verificarAluno(data) {
	let response = data.res
	let ra = data.ra
	pool.request()
		.input('ra', sql.SmallInt, ra)
		.query('select * from mali2.Alunos where ra = @ra', (err, res) => {
			if (err) {
				response.sendStatus(500)
			} else {
				if (res.rowsAffected[0] > 0) verificarDisciplina(data)
				else {
					printarErro(`Aluno (${ra}) não existe`)
					response.status(400).send(`Aluno (${ra}) não existe`)
				}
			}
		})
}

/**
 * Verifica se uma disciplina existe
 */
function verificarDisciplina(data) {
	let response = data.res
	let cod = data.cod
	pool.request()
		.input('cod', sql.Int, cod)
		.query(
			'select * from mali2.Disciplinas where cod = @cod',
			(err, res) => {
				if (err) {
					response.sendStatus(500)
				} else {
					if (res.rowsAffected[0] > 0) verificarMatricula(data)
					else {
						printarErro(`Disciplina (${cod}) não existe`)
						response
							.status(400)
							.send(`Disciplina (${cod}) não existe`)
					}
				}
			}
		)
}

/**
 * Verifica se um aluno está matriculado em uma disciplina
 */
function verificarMatricula(data) {
	let response = data.res
	let ra = data.ra
	let cod = data.cod
	pool.request()
		.input('ra', sql.SmallInt, ra)
		.input('cod', sql.Int, cod)
		.query(
			'select * from mali2.Matriculas where ra = @ra and cod = @cod',
			(err, res) => {
				if (err) {
					response.sendStatus(500)
				} else {
					if (res.rowsAffected[0] > 0) verificarResultado(data)
					else {
						printarErro(
							`Aluno (${ra}) não está matriculado na Disciplina (${cod})`
						)
						response
							.status(400)
							.send(
								`Aluno (${ra}) não está matriculado na Disciplina (${cod})`
							)
					}
				}
			}
		)
}

/**
 * Verifica se um resultado já existe
 */
function verificarResultado(data) {
	let response = data.res
	let ra = data.ra
	let cod = data.cod
	pool.request()
		.input('ra', sql.SmallInt, ra)
		.input('cod', sql.Int, cod)
		.query(
			'select * from mali2.Resultados where ra = @ra and cod = @cod',
			(err, res) => {
				if (err) {
					response.sendStatus(500)
				} else {
					if (res.rowsAffected[0] == 0) gerarResultado(data)
					else {
						printarErro(`Resultado já existe`)
						response.status(500).send(`Resultado já existe`)
					}
				}
			}
		)
}

/**
 * Gera e insere no BD o resultado de um aluno em uma matrícula
 */
function gerarResultado(data) {
	let response = data.res
	let ra = data.ra
	let cod = data.cod
	let nota = data.nota
	let freq = data.freq
	pool.request()
		.input('ra', sql.SmallInt, ra)
		.input('cod', sql.Int, cod)
		.input('nota', sql.Float, nota)
		.input('freq', sql.Float, freq)
		.query(
			'insert into mali2.Resultados values(@ra, @cod, @nota, @freq)',
			(err, res) => {
				if (err) {
					printarErro(
						'Erro ao gerar resultado, a matrícula não foi removida'
					)
					response.status(500).send('Erro ao gerar resultado')
				} else {
					removerMatricula(data)
				}
			}
		)
}

/**
 * Exclui a matrícula de um aluno do BD
 */
function removerMatricula(data) {
	let response = data.res
	let ra = data.ra
	let cod = data.cod
	pool.request()
		.input('ra', sql.SmallInt, ra)
		.input('cod', sql.Int, cod)
		.query(
			'delete from mali2.Matriculas where RA = @ra and Cod = @cod',
			(err, res) => {
				if (err) {
					printarErro(
						'Erro ao remover matrícula, mas os resultados FORAM gerados'
					)
					response
						.status(500)
						.send(
							'Erro ao remover matrícula, mas os resultados FORAM gerados'
						)
				} else {
					console.log('200 OK')
					response.sendStatus(200)
				}
			}
		)
}

app.listen(port, () => console.log(`Listening at http://localhost:${port}`))
