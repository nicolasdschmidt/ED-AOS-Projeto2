const express = require('express')
const bodyparser = require('body-parser')
const sql = require('mssql')
const app = express()
const port = 3000

app.use(bodyparser.json())
app.use(bodyparser.urlencoded({ extended: true }))

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

app.get('/', (req, res) => res.sendStatus(200))

app.post('/', (req, res) => {
	try {
		// receber parâmetros do post
		let ra = req.body.ra
		let cod = req.body.cod
		let nota = req.body.nota
		let freq = req.body.freq

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

		// atualmente impossível fazer verificações de aluno/disciplina/matricula:
		// mssql é assíncrono, não é possível esperar pelo resultado, nem lançar
		// erros dentro de um bloco async

		// inserir dados na tabela Resultados
		;(async function () {
			try {
				let pool = await sql.connect(config)
				let result = await pool
					.request()
					.input('ra', sql.SmallInt, ra)
					.input('cod', sql.Int, cod)
					.input('nota', sql.Int, nota)
					.input('freq', sql.Int, freq)
					.query(
						'insert into mali2.Resultados values(@ra, @cod, @nota, @freq)'
					)
			} catch (err) {
				throw new Error(
					'500 Internal Server Error: Erro do SQL Server ao criar resultados (matrícula não removida)'
				)
			}
		})()

		// remover matrícula
		;(async function () {
			try {
				let pool = await sql.connect(config)
				let result = await pool
					.request()
					.input('ra', sql.SmallInt, ra)
					.input('cod', sql.Int, cod)
					.query(
						'delete from mali2.Matriculas where RA = @ra and Cod = @cod'
					)
			} catch (err) {
				throw new Error(
					'500 Internal Server Error: Erro do SQL Server ao remover matrícula (resultados FORAM criados)'
				)
			}
		})()

		res.sendStatus(200)
	} catch (err) {
		let code = 500
		let c = Number(String(err).substring(7, 10))
		if (!isNaN(c)) code = c
		res.status(code).send(String(err).replace('Error: ', ''))
	}
})

app.listen(port, () => console.log(`Listening at http://localhost:${port}`))
