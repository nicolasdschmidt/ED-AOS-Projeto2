const express = require('express')
const bodyparser = require('body-parser')
const sql = require('mssql')
const app = express()
const port = 3000

app.use(bodyparser.json())
app.use(bodyparser.urlencoded({ extended: true }))

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
		// receber parâmetro
		let ra = req.body.ra
		let cod = req.body.cod
		let nota = req.body.nota
		let freq = req.body.freq

		// verificar restrições simples dos parâmetros
		if (
			ra == undefined ||
			ra <= 0 ||
			ra > 32767 ||
			cod == undefined ||
			cod <= 0 ||
			nota == undefined ||
			nota < 0 ||
			nota > 10 ||
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
		res.status(code).send(String(err).replace('Error: ', ''))
	}
})

/**
 * Função que guia todo o processo de verificação dos dados, remoção da matrícula, e geração do resultado
 */
function processarDados(ra, cod, nota, freq, res) {
	gerarResultado(ra, cod, nota, freq, res, function () {
		verificarMatricula(ra, cod, res, function () {
			removerMatricula(ra, cod, res)
		})
	})
}

/**
 * Verifica se um aluno está matriculado em uma disciplina
 */
function verificarMatricula(ra, cod, response, callback) {
	pool.request()
		.input('ra', sql.SmallInt, ra)
		.input('cod', sql.Int, cod)
		.query(
			'select * from mali2.Matriculas where ra = @ra and cod = @cod',
			(err, res) => {
				if (err) {
					response.sendStatus(500)
				} else {
					if (res.rowsAffected > 0) {
						callback()
					} else {
						response
							.status(200)
							.send(
								`Aluno (${ra}) não está matriculado na Disciplina (${cod})`
							)
					}
				}
			}
		)
}

/**
 * Gera e insere no BD o resultado de um aluno em uma matrícula
 */
function gerarResultado(ra, cod, nota, freq, response, callback) {
	pool.request()
		.input('ra', sql.SmallInt, ra)
		.input('cod', sql.Int, cod)
		.input('nota', sql.Int, nota)
		.input('freq', sql.Int, freq)
		.query(
			'insert into mali2.Resultados values(@ra, @cod, @nota, @freq)',
			(err, res) => {
				if (err) {
					if (err.message.includes('mali2.Alunos')) {
						response.status(400).send(`Aluno (${ra}) não existe`)
					} else if (err.message.includes('mali2.Disciplinas')) {
						response
							.status(400)
							.send(`Disciplina (${cod}) não existe`)
					} else if (err.message.includes('mali2.Resultados')) {
						response.status(500).send(`Resultado já existe`)
					} else {
						response.sendStatus(500)
					}
				} else {
					callback()
				}
			}
		)
}

/**
 * Exclui a matrícula de um aluno do BD
 */
function removerMatricula(ra, cod, response) {
	pool.request()
		.input('ra', sql.SmallInt, ra)
		.input('cod', sql.Int, cod)
		.query(
			'delete from mali2.Matriculas where RA = @ra and Cod = @cod',
			(err, res) => {
				if (err) {
					response
						.status(500)
						.send(
							'Erro ao remover matrícula, mas os resultados FORAM gerados'
						)
				} else {
					response.sendStatus(200)
				}
			}
		)
}

app.listen(port, () => console.log(`Listening at http://localhost:${port}`))
