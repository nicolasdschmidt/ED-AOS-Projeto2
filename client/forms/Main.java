package forms;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JSplitPane;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.CardLayout;
import javax.swing.BoxLayout;
import javax.swing.SpringLayout;
import java.awt.GridLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import src.Fila;
import src.Resultado;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;

public class Main extends JFrame {

	private JPanel contentPane;
	private JTextField txtRa;
	private JTextField txtCod;
	private JTextField txtNota;
	private JTextField txtFrequencia;
	private static Fila<Resultado> alunos;
	private JTable tblAlunos;
	private static DefaultTableModel model;
	private static Status status = new Status();
	private static Add_status add_status;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
					alunos = new Fila<>();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel formulario = new JPanel();
		contentPane.add(formulario, BorderLayout.NORTH);
		formulario.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_2 = new JPanel();
		formulario.add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblNewLabel = new JLabel("RA");
		panel_2.add(lblNewLabel);
		
		txtRa = new JTextField();
		panel_2.add(txtRa);
		txtRa.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Código da disciplina");
		panel_2.add(lblNewLabel_1);
		
		txtCod = new JTextField();
		panel_2.add(txtCod);
		txtCod.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Nota");
		panel_2.add(lblNewLabel_2);
		
		txtNota = new JTextField();
		panel_2.add(txtNota);
		txtNota.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Frequência");
		panel_2.add(lblNewLabel_3);
		
		txtFrequencia = new JTextField();
		panel_2.add(txtFrequencia);
		txtFrequencia.setColumns(10);
		
		JButton btnAdicionar = new JButton("Adionar");
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				try
				{				
					Resultado aluno = new Resultado(Integer.valueOf(txtRa.getText()), 
													Integer.valueOf(txtCod.getText()), 
													Float.valueOf(txtNota.getText()), 
													Float.valueOf(txtFrequencia.getText()));
					
					alunos.addItem(aluno);
					
					model.addRow(new Object[]{txtRa.getText(), txtCod.getText(), txtNota.getText(), txtFrequencia.getText()});
				}
				catch(NumberFormatException ex)
				{
					status.setStatus("Erro: Resultado em formato inválido. Todos devem ser número.");
					status.setVisible(true);
				}
				catch(Exception ex)
				{
					status.setStatus(ex.getMessage());
					status.setVisible(true);
				}
										
				txtRa.setText("");
				txtCod.setText("");
				txtNota.setText("");
				txtFrequencia.setText("");
			}
		});
		panel_2.add(btnAdicionar);
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				 add_status = new Add_status(alunos);
				 add_status.setVisible(true);
			}
		});
		panel_2.add(btnSalvar);
		
		JPanel list = new JPanel();
		contentPane.add(list, BorderLayout.CENTER);
		list.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		list.add(panel);
		
		model = new DefaultTableModel();
		tblAlunos = new JTable(model);
		tblAlunos.setFillsViewportHeight(true);
		tblAlunos.setEnabled(false);
		panel.add(tblAlunos);
		
		panel.add(tblAlunos.getTableHeader(), BorderLayout.NORTH);
		panel.add(tblAlunos, BorderLayout.CENTER);
		
		model.addColumn("RA");
		model.addColumn("Codigo da disciplina");
		model.addColumn("Nota");
		model.addColumn("Frequência");
	}
	

}
