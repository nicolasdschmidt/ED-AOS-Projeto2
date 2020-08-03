package forms;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import src.Fila;
import src.Resultado;

import javax.swing.JTable;
import javax.swing.JLabel;

public class Add_status extends JFrame {

	private JPanel contentPane;
	private JTable tblAlunos;
	private static DefaultTableModel model;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//Add_status frame = new Add_status();
					//frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @param alunos 
	 */
	public Add_status(Fila<Resultado> alunos){
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 385, 307);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JLabel lblEnviandoDados = new JLabel("Enviando dados");
		contentPane.add(lblEnviandoDados, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		
		model = new DefaultTableModel();
		tblAlunos = new JTable(model);
		tblAlunos.setFillsViewportHeight(true);
		tblAlunos.setEnabled(false);
		panel.add(tblAlunos);
		
		panel.add(tblAlunos.getTableHeader(), BorderLayout.NORTH);
		panel.add(tblAlunos, BorderLayout.CENTER);
		
		model.addColumn("STATUS");
		model.addColumn("RA");
		model.addColumn("Codigo da disciplina");
		model.addColumn("Nota");
		model.addColumn("Frequência");
		
		while(alunos.getQtd() > 0)
		{
			try 
			{
				Resultado aluno = alunos.recuperarItem();
				model.addRow(new Object[]{"STATUS DA API", aluno.getRa(), aluno.getCod(), aluno.getNota(), aluno.getFreq()});
				alunos.removerItem();
			}
			catch(Exception ex)
			{}
		}
	}

}
