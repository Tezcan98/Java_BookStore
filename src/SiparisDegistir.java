import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SiparisDegistir extends JFrame {

	private JPanel contentPane;
	private JTextField girilen;
	private JTextField isbn;
	JLabel sipno;
	private JTextField count;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SiparisDegistir frame = new SiparisDegistir();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace(); 
				}
			}
		});
	}
	private void Delete() {
		Connection connection=null;
		DbHelper dbHelper=new DbHelper();
		PreparedStatement statement =null;
		 ResultSet rs;
		boolean var=false;
		 String query = "select * from odemeler where fat=?";
	        DbHelper dbhelper =new DbHelper();
	        try{
	           Connection conn=dbhelper.getConnection();
	           statement = conn.prepareStatement(query);
	           statement.setInt(1, Integer.parseInt(girilen.getText()));
	           
	           rs = statement.executeQuery();
	           //rs.next(); 
	           while (rs.next ()) {
		            
	        	   var=true;
	            }
	       }
	       catch(SQLException e){
	           JOptionPane.showMessageDialog(null, e);
	       }
		
	    if(!var) {
		try {
			connection = dbHelper.getConnection();
			String sql = "Delete from kitapsiparisi WHERE sip= ?;";
			statement = connection.prepareStatement(sql);
			statement.setInt(1,Integer.parseInt(girilen.getText())); 
			statement.executeUpdate();
			isbn.setText("0");
			count.setText("0");
			sipno.setText("");
			
			JOptionPane.showMessageDialog(null,"Kitap Siparisi Silindi");
			}
	       catch(SQLException e){
	           JOptionPane.showMessageDialog(null, e);
	       }}
	    else
	    	 JOptionPane.showMessageDialog(null, "Müşterinin Ödemesi var");
		
	}

	private void Select() {
		// TODO Auto-generated method stub
		 PreparedStatement st;
	        ResultSet rs;
	        String query = "select * from kitapsiparisi where sip=?";
	        DbHelper dbhelper =new DbHelper();
	        try{
	           Connection conn=dbhelper.getConnection();
	           st = conn.prepareStatement(query);
	           st.setInt(1,Integer.parseInt(girilen.getText()));
	           rs = st.executeQuery();
	           //rs.next(); 
	           while (rs.next ()) {
		            
	        	    sipno.setText(rs.getString(1));
		            isbn.setText(rs.getString(2));
		            count.setText(rs.getString(3));
		            
	            }
	       }
	       catch(SQLException e){
	           JOptionPane.showMessageDialog(null, e);
	       }
	}
	/**
	 * Create the frame.
	 */
	private void Uptade() {
		Connection connection=null;
		DbHelper dbHelper=new DbHelper();
		PreparedStatement statement =null;
		try {
		connection = dbHelper.getConnection();
		String sql = "UPDATE kitapsiparisi SET isbn = ? , miktar = ? WHERE sip= ?;";
		statement = connection.prepareStatement(sql);
		statement.setInt(1,Integer.parseInt(isbn.getText()));
		statement.setInt(2,Integer.parseInt(count.getText()));
		statement.setInt(3,Integer.parseInt(girilen.getText())); 
		statement.executeUpdate();
		JOptionPane.showMessageDialog(null,"Siparis Bilgisi Güncellendi");
		}
       catch(SQLException e){
           JOptionPane.showMessageDialog(null, e);
       }
		
	}
	
	public SiparisDegistir() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 700, 451);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton button = new JButton("Onay");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 
				Uptade();
			}
		});
		button.setFont(new Font("Tahoma", Font.PLAIN, 20));
		button.setBounds(55, 300, 121, 47);
		contentPane.add(button);
		JFrame j=this;
		JButton button_1 = new JButton("İptal");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				j.hide();
			}
		});
		button_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		button_1.setBounds(219, 300, 121, 47);
		contentPane.add(button_1);
		
		JButton btnSiparisSil = new JButton("Siparis Sil");
		btnSiparisSil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Delete();
			}
		});
		btnSiparisSil.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnSiparisSil.setBounds(396, 300, 185, 47);
		contentPane.add(btnSiparisSil);
		
		girilen = new JTextField();
	 
		girilen.setText("0");
		girilen.setFont(new Font("Tahoma", Font.PLAIN, 20));
		girilen.setColumns(10);
		girilen.setBounds(171, 206, 185, 47);
		contentPane.add(girilen);
		
		JLabel lblSiparisNo = new JLabel("Siparis No");
		lblSiparisNo.setFont(new Font("Tahoma", Font.ITALIC, 20));
		lblSiparisNo.setBounds(206, 146, 112, 47);
		contentPane.add(lblSiparisNo);
		
		 sipno = new JLabel("sipno");
		sipno.setFont(new Font("Tahoma", Font.ITALIC, 20));
		sipno.setBounds(55, 52, 112, 47);
		contentPane.add(sipno);
		
		JLabel lblMiktar = new JLabel("isbn");
		lblMiktar.setFont(new Font("Tahoma", Font.ITALIC, 20));
		lblMiktar.setBounds(189, 13, 112, 47);
		contentPane.add(lblMiktar);
		
		isbn = new JTextField();
		isbn.setText("0");
		isbn.setFont(new Font("Tahoma", Font.PLAIN, 20));
		isbn.setColumns(10);
		isbn.setBounds(169, 52, 149, 47);
		contentPane.add(isbn);
		
		JLabel lblMiktar_1 = new JLabel("miktar");
		lblMiktar_1.setFont(new Font("Tahoma", Font.ITALIC, 20));
		lblMiktar_1.setBounds(382, 13, 112, 47);
		contentPane.add(lblMiktar_1);
		
		count = new JTextField();
		count.setText("0");
		count.setFont(new Font("Tahoma", Font.PLAIN, 20));
		count.setColumns(10);
		count.setBounds(381, 52, 149, 47);
		contentPane.add(count);
		
		JButton btnNewButton = new JButton("git");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Select();
			}
		});
		btnNewButton.setBounds(397, 206, 97, 47);
		contentPane.add(btnNewButton);
		
		JLabel lblSiparisNo_1 = new JLabel("Siparis No");
		lblSiparisNo_1.setFont(new Font("Tahoma", Font.ITALIC, 20));
		lblSiparisNo_1.setBounds(55, 13, 112, 47);
		contentPane.add(lblSiparisNo_1);
	}
}
