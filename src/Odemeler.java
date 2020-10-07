import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Odemeler extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	DefaultTableModel model2; 
	private JLabel toplamuc;
	private JLabel kalanuc;
	private int ucret;
	private int odenen;
	private int kalan;
	JTable table2;
	JSpinner spinner;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Odemeler frame = new Odemeler();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	 

	private int ucretq() {
		 PreparedStatement st;
	      ResultSet rs;
	  	  int i=1;
	  	  
	        String query = "select miktar,birimfiyat from fatura f,siparis s,kitapsiparisi k,kitaplar kt where s.siparis=k.sip and f.sip=k.sip and kt.isbn = k.isbn and musteri=?";
	        DbHelper dbhelper =new DbHelper();
	        try{
	           Connection conn=dbhelper.getConnection();
	           st = conn.prepareStatement(query);
	           st.setInt(1, Main.main_m_id);
	           rs = st.executeQuery();
	           //rs.next(); 
	           while (rs.next ()) { 
	        	   ucret=Integer.parseInt(rs.getString(1))*Integer.parseInt(rs.getString(2));
	        	   return ucret;
	        	    
	            }
	       }
	       catch(SQLException e){
	           JOptionPane.showMessageDialog(null, e);
	       }
	        return 0;
	}
	

	private void odemeyap() {
	
		Connection connection=null;
		DbHelper dbHelper=new DbHelper(); 
		PreparedStatement statement =null;
	    ResultSet rs;
		int tak=0;
		 String query = "select max(tak) from odemeler where fat=?";
	        DbHelper dbhelper =new DbHelper();
	        try{
	           Connection conn=dbhelper.getConnection();
	           statement = conn.prepareStatement(query);
	           statement.setInt(1, OdemeTakip.fatno);
	           rs = statement.executeQuery();
	           //rs.next(); 
	           while (rs.next ()) {
		            
	        	   tak=rs.getInt(1); 
	            }
	       }
	       catch(SQLException e){
	           JOptionPane.showMessageDialog(null, e);
	       }
		
		
		
		 String SQL = "Insert INTO odemeler (fat,tak,odememiktari,kart) values (?,?,?,?) ;";
		try {

		    connection=dbHelper.getConnection();
			statement = connection.prepareStatement(SQL);
	 
			statement.setInt(1, OdemeTakip.fatno);
			
			if(!textField.getText().equals(""))
				statement.setInt(2, tak+1);
			else
				statement.setInt(2, 0);
			
			statement.setInt(3,(Integer)spinner.getValue());
			statement.setString(4,textField.getText());
			statement.executeUpdate(); 

			JOptionPane.showMessageDialog(null,"Odemeniz Gerçekleştirildi");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	private void odemelist() {
		 PreparedStatement st;
	      ResultSet rs;
	  	  int i=1;
	  	  
	        String query = "select * from odemeler o,fatura f,siparis s where s.siparis=f.sip and f.fat=o.fat and s.musteri=? and f.fat=?";
	        DbHelper dbhelper =new DbHelper();
	        try{
	           Connection conn=dbhelper.getConnection();
	           st = conn.prepareStatement(query);
	           st.setInt(1, Main.main_m_id);
	           st.setInt(2, OdemeTakip.fatno);
	           rs = st.executeQuery();
	           //rs.next(); 
	           while (rs.next ()) {
	        	   model2.setValueAt(rs.getInt(1),i,0);
	        	   model2.setValueAt(rs.getInt(2),i,1);
	        	   model2.setValueAt(rs.getString(3),i,2);
	        	    
	            }
	       }
	       catch(SQLException e){
	           JOptionPane.showMessageDialog(null, e);
	       }
		
		
	}
	private int odenenq() {
			PreparedStatement st;
			ResultSet rs; 
	  	  
	        String query = "select sum(odememiktari) from odemeler o,fatura f,siparis s where s.siparis=f.sip and f.fat=o.fat and s.musteri=? and f.fat=?";
	        DbHelper dbhelper =new DbHelper();
	        try{
	           Connection conn=dbhelper.getConnection();
	           st = conn.prepareStatement(query);
	           st.setInt(1, Main.main_m_id);
	           st.setInt(2, OdemeTakip.fatno);
	           rs = st.executeQuery();
	           //rs.next(); 
	           while (rs.next ()) {
 
	        	   return rs.getInt(1); 
	            }
	       }
	       catch(SQLException e){
	           JOptionPane.showMessageDialog(null, e);
	       }
		return 0;
		
		
		
	}
	 
	/**
	 * Create the frame.
	 */
	public Odemeler() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 964, 606);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	
		 
		model2 = new DefaultTableModel();   
		model2.addColumn("Col1"); 
		model2.addColumn("Col2"); 
		model2.addColumn("Col3");   
		model2.addColumn("Col4");  
		
		model2.addRow(new Object[]{"Fatura No","Taksit","Ödeme Miktarı","Kart Numarası"}); 
		for(int i =1;i<20;i++)
			model2.addRow(new Object[]{"","",""}); 
		
		table2 = new JTable(model2);
		table2.setEnabled(false);
		table2.setBounds(25, 24, 391, 248);
		contentPane.add(table2);
		table2.setRowHeight(30);
		
		JPanel panel = new JPanel();
		panel.setBounds(35, 294, 426, 252);
		contentPane.add(panel);
		panel.setLayout(null);
		
		if(Main.user.equals("musteri"))
			panel.setVisible(true);
		JLabel lblTaksitlendirmeIinKart = new JLabel("*Taksitlendirme için Kart Numarasını Giriniz.");
		lblTaksitlendirmeIinKart.setBounds(129, 183, 272, 16);
		panel.add(lblTaksitlendirmeIinKart);
		lblTaksitlendirmeIinKart.setForeground(Color.RED);
		
		spinner = new JSpinner();
		spinner.setBounds(129, 134, 113, 22);
		panel.add(spinner);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {

				if(textField.getText().equals("")) {
					spinner.setValue(kalan);
					spinner.setEnabled(false);
				}
				else {
					spinner.setEnabled(true);
				}
			}
		});
		textField.setBounds(127, 89, 284, 32);
		panel.add(textField);
		textField.setColumns(10);
		
		JLabel lblKartNumaras = new JLabel("Kart Numarası");
		lblKartNumaras.setBounds(12, 94, 103, 22);
		panel.add(lblKartNumaras);
		
		JLabel lbldenecekTutar = new JLabel("Ödenecek Tutar*");
		lbldenecekTutar.setBounds(12, 134, 103, 22);
		panel.add(lbldenecekTutar);
		
		JButton btnNewButton_1 = new JButton("Ödeme Yap");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				odemeyap();
			}

		});
		btnNewButton_1.setBounds(129, 207, 126, 32);
		panel.add(btnNewButton_1);
		
		toplamuc = new JLabel("Toplam Ücret =   ");
		toplamuc.setForeground(Color.DARK_GRAY);
		toplamuc.setBounds(12, 25, 206, 42);
		panel.add(toplamuc);
		
		 kalanuc = new JLabel("Kalan Ücret =   ");
		kalanuc.setForeground(Color.DARK_GRAY);
		kalanuc.setBounds(215, 25, 167, 42);
		
		ucret=ucretq();
		toplamuc.setText(toplamuc.getText()+ucret);  
		panel.add(kalanuc);
		odenen = odenenq();
		kalanuc.setText("Kalan Ucret =" + (ucret-odenen));
		kalan=(ucret-odenen);
		SpinnerModel smodel = new SpinnerNumberModel(0,0,kalan,1);
		spinner.setModel(smodel);
		spinner.setEnabled(false);
		odemelist();
	}
}
