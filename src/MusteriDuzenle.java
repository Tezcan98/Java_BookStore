import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextPane;
import java.awt.SystemColor;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JToolBar;
import javax.swing.JLayeredPane;
import javax.swing.JTable;

public class MusteriDuzenle extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField girilen;
	private JTextField email;
	private JTable table;
	private int offset;
	private JLabel mid,isim;
	DefaultTableModel model;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MusteriDuzenle frame = new MusteriDuzenle();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}



	private void DeleteM(int m_id) {
		Connection connection=null;
		DbHelper dbHelper=new DbHelper();
		PreparedStatement statement =null;
		try {
			connection = dbHelper.getConnection();
			String sql = "Delete from musteri WHERE mid= ?;";
			statement = connection.prepareStatement(sql);
			statement.setInt(1,m_id); 
			statement.executeUpdate();
			JOptionPane.showMessageDialog(null,"Müsteri Silindi");
			}
	       catch(SQLException e){
	           JOptionPane.showMessageDialog(null, e);
	       }
		
	}

	private void UptadeM(int m_id, String mail) {
		Connection connection=null;
		DbHelper dbHelper=new DbHelper();
		PreparedStatement statement =null;
		try {
		connection = dbHelper.getConnection();
		String sql = "UPDATE musteri SET email = ? WHERE mid= ?;";
		statement = connection.prepareStatement(sql);
		statement.setInt(2,m_id);
		statement.setString(1,mail); 
		statement.executeUpdate();
		JOptionPane.showMessageDialog(null,"E-mail Bilgisi Güncellendi");
		}
       catch(SQLException e){
           JOptionPane.showMessageDialog(null, e);
       }
		
	}
	public void Selecter(int m_id){  
        PreparedStatement st;
        ResultSet rs;
        String query = "select * from musteri where mid='"+m_id +"'";
        DbHelper dbhelper =new DbHelper();
        try{
           Connection conn=dbhelper.getConnection();
           st = conn.prepareStatement(query);
           rs = st.executeQuery();
           //rs.next(); 
           while (rs.next ()) {
	            
        	    mid.setText(rs.getString(1));
	            isim.setText(rs.getString(2));
	            email.setText(rs.getString(3));
	            
            }
       }
       catch(SQLException e){
           JOptionPane.showMessageDialog(null, e);
       }
	
	}
	public void Select_limit(int offset){  
		PreparedStatement st;
		ResultSet rs;
		int count=0;
		offset *=5;
		String query = "select count(*) from musteri";
		DbHelper dbhelper =new DbHelper();
		try{
		   Connection conn=dbhelper.getConnection();
		   st = conn.prepareStatement(query); 
		   rs = st.executeQuery();
		   //rs.next(); 
		       while (rs.next ()) {  
		    	    count=rs.getInt(1);
		        }
		   }
		   catch(SQLException e){
		       JOptionPane.showMessageDialog(null, e);
		   }
  
	    query = "select * from musteri order by isim,mid limit 5 offset ?";
	    dbhelper =new DbHelper();
	    offset=offset%count;
	    int i=0;
	    try{
	       Connection conn=dbhelper.getConnection();
	       st = conn.prepareStatement(query); 
	       st.setInt(1, offset);
	       rs = st.executeQuery();
	       //rs.next();  
	       while (rs.next ()) {  
	    	    String m_id =rs.getString(1);
	            String m_isim=rs.getString(2);
	            String m_email =rs.getString(3);  

	 	       	model.setValueAt(m_id,i,0);
	        	model.setValueAt(m_isim,i,1); 
	        	model.setValueAt(m_email,i,2); 
	        	 
	        	i++;
	        }
       }
       catch(SQLException e){
           JOptionPane.showMessageDialog(null, e);
       }
   }
	/**
	 * Create the frame.
	 */
	public MusteriDuzenle() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1500, 403);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		mid = new JLabel("Müşteri İd");
		mid.setVisible(false);
		mid.setFont(new Font("Tahoma", Font.ITALIC, 20));
		mid.setBounds(33, 38, 185, 47);
		contentPane.add(mid);
		
		isim = new JLabel("Müşteri İsmi");
		isim.setVisible(false);
		isim.setFont(new Font("Tahoma", Font.ITALIC, 20));
		isim.setBounds(230, 38, 208, 47);
		contentPane.add(isim);

		
		model = new DefaultTableModel(); 
  

		// Create a couple of columns 
		model.addColumn("Col1"); 
		model.addColumn("Col2"); 
		model.addColumn("Col3"); 
		offset = 0;
		model.addRow(new Object[]{"","",""});
		model.addRow(new Object[]{""," ",""});
		model.addRow(new Object[]{"","",""});
		model.addRow(new Object[]{"","",""});
		model.addRow(new Object[]{"","",""});
 
		if(!Main.user.equals("musteri"))
			Select_limit(offset);
 
		girilen = new JTextField();
		 
		girilen.setText("0");
		girilen.addKeyListener(new KeyAdapter() {
		 
			@Override
			public void keyReleased(KeyEvent arg0) {
				int id=0;
				try
				{
					id=Integer.parseInt(girilen.getText());
					Selecter(id);
					mid.setVisible(true);
					isim.setVisible(true);
					email.setVisible(true); 
				}
				catch(Exception ex) {
 
				}
				
			}
		});
		girilen.setFont(new Font("Tahoma", Font.PLAIN, 20));
		girilen.setColumns(10);
		girilen.setBounds(253, 158, 185, 47);
		contentPane.add(girilen);
		
		JLabel lblArananMteriIdyi = new JLabel("Aranan Müşteri İD'yi Giriniz");
		lblArananMteriIdyi.setFont(new Font("Tahoma", Font.ITALIC, 20));
		lblArananMteriIdyi.setBounds(215, 98, 265, 47);
		contentPane.add(lblArananMteriIdyi);
		
		JButton btnNewButton = new JButton("Onay");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int m_id = Integer.parseInt(mid.getText());
				String mail = email.getText();
				UptadeM(m_id,mail);
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnNewButton.setBounds(137, 252, 121, 47);
		contentPane.add(btnNewButton);
		JFrame f = this;
		JButton btnIptal = new JButton("İptal");
		btnIptal.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) { 
				f.hide();
			}
		});
		btnIptal.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnIptal.setBounds(301, 252, 121, 47);
		contentPane.add(btnIptal); 
		
		JButton btnMteriyiSil = new JButton("Müşteriyi Sil");
		btnMteriyiSil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int m_id = Integer.parseInt(mid.getText());
				DeleteM(m_id);
			}  
		});
		btnMteriyiSil.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnMteriyiSil.setBounds(478, 252, 185, 47);
		contentPane.add(btnMteriyiSil);
		
		email = new JTextField();
		email.setVisible(false);
		email.setBounds(550, 41, 173, 47);
		contentPane.add(email);
		email.setColumns(10); 
		
		JPanel kisiler = new JPanel();
		kisiler.setBounds(810, 27, 595, 316);
		contentPane.add(kisiler);
		kisiler.setLayout(null);
		
		table = new JTable(model);
		table.setBounds(108, 30, 400, 200);
		kisiler.add(table);
		// Append a row  
    	
		table.setEnabled(false);
		table.setRowHeight(30);
		
		JButton btnGeri = new JButton("Geri");
		btnGeri.setBounds(36, 243, 97, 25);
		kisiler.add(btnGeri);
		
		JButton btnIleri = new JButton("İleri");
		btnIleri.setBounds(486, 243, 97, 25);
		kisiler.add(btnIleri);
		btnIleri.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				offset++;
				Select_limit(offset);
			}
		}); 
		btnGeri.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(offset>0) {
					offset--;
					Select_limit(offset);
				}
			}
		});
		
		if(Main.user.equals("musteri")) {
			kisiler.setVisible(false);
			setBounds(100, 100, 800, 403);
			btnMteriyiSil.setVisible(false); 
			girilen.setText(""+Main.main_m_id);;
			girilen.setEnabled(false);
			int id=Integer.parseInt(girilen.getText());
			Selecter(id);
			mid.setVisible(true);
			isim.setVisible(true);
			email.setVisible(true); 
		}
	}
}
