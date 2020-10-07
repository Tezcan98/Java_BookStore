import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.eclipse.jface.text.templates.GlobalTemplateVariables.User;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;

public class OdemeTakip extends JFrame {

	private JPanel contentPane;
	private JTextField kim;
	private JTable table;
	DefaultTableModel model;
	private JTextField faturano;
	private JLabel lblMteriNo;
	private JLabel lblFaturaNumaras;
	private JButton btnNewButton;
	public static int fatno;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OdemeTakip frame = new OdemeTakip();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void faturaGoruntule(int M_id) {
		 PreparedStatement st;
	        ResultSet rs;
	  	  for(int j=1;j<20;j++) {
			  model.setValueAt("",j,0);
			  model.setValueAt("",j,1);  
	        }
	  	  int i=1;
	        String query = "select * from fatura,siparis where siparis=sip and musteri=?";
	        DbHelper dbhelper =new DbHelper();
	        try{
	           Connection conn=dbhelper.getConnection();
	           st = conn.prepareStatement(query);
	           st.setInt(1, M_id);
	           rs = st.executeQuery();
	           //rs.next(); 
	           while (rs.next ()) {
		            
	        	   model.setValueAt(rs.getInt(1),i,0);
	        	   model.setValueAt(rs.getInt(2),i,1);
	        	   model.setValueAt(rs.getString(3),i,2);
	            }
	       }
	       catch(SQLException e){
	           JOptionPane.showMessageDialog(null, e);
	       }
		
		
	}
	/**
	 * Create the frame.
	 */
	public OdemeTakip() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 839, 501);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton_1 = new JButton("Fatura Görüntüle");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				faturaGoruntule(Integer.parseInt(kim.getText()));
			}
		});
		btnNewButton_1.setBounds(241, 13, 129, 55);
		contentPane.add(btnNewButton_1);
		
		kim = new JTextField();
		kim.setBounds(107, 13, 88, 55);
		contentPane.add(kim);
		kim.setColumns(10);
		
		 model = new DefaultTableModel(); 
    
	     
		model.addColumn("Col1"); 
		model.addColumn("Col2"); 
		model.addColumn("Col3");  
		// Append a row  
	
	
		model.addRow(new Object[]{"Fatura No","Siparis No","Tarih"}); 
		for(int i =1;i<20;i++)
			model.addRow(new Object[]{"","",""}); 
		
		table = new JTable(model);
		table.setEnabled(false);
		table.setBounds(45, 95, 325, 306);
		contentPane.add(table);
		table.setRowHeight(30); 
		
		faturano = new JTextField();
		faturano.setColumns(10);
		faturano.setBounds(411, 166, 88, 55);
		contentPane.add(faturano);
		
		lblMteriNo = new JLabel("Müşteri No");
		lblMteriNo.setBounds(12, 29, 88, 23);
		contentPane.add(lblMteriNo);
		if(Main.user.equals("musteri"))
		{
			kim.setText(""+Main.main_m_id);
			kim.setVisible(false);
			lblMteriNo.setVisible(false);
		}
		lblFaturaNumaras = new JLabel("Fatura Numarası");
		lblFaturaNumaras.setBounds(403, 130, 114, 23);
		contentPane.add(lblFaturaNumaras);
		
		btnNewButton = new JButton("İleri");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			 	fatno=Integer.parseInt(faturano.getText());
			 	Odemeler frame=new Odemeler();
				frame.setLocationRelativeTo(null); 
				frame.setVisible(true);
			 	
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnNewButton.setBounds(548, 163, 88, 55);
		contentPane.add(btnNewButton);
	}
}
