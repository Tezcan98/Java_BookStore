import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JTextPane;
import java.awt.SystemColor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class MusteriOlustur extends JFrame {

	private JPanel contentPane;
	private JTextField name;
	private JTextField email;
	private int insertid;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MusteriOlustur frame = new MusteriOlustur();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	} 
	
	/**
	 * Create the frame.
	 */
	public int CreateP(String isim,String email){
	      
	  String SQL = "insert into musteri(isim,email) values (?,?);";
	  DbHelper dbHelper=new DbHelper();
        int mid=0;
 
        try (	Connection conn = dbHelper.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(SQL,
                Statement.RETURN_GENERATED_KEYS)) {
 
            pstmt.setString(1, isim);
            pstmt.setString(2, email);
 
            int affectedRows = pstmt.executeUpdate();
            // check the affected rows 
            if (affectedRows > 0) {
                // get the ID back
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        mid = rs.getInt(1);
                        JOptionPane.showMessageDialog(null, isim+" kisi, "+mid+" Id ile eklendi.");
                        return mid;
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return mid;
	}
	public void deleteP(int id) {

		Connection connection=null;
	    DbHelper dbHelper=new DbHelper();
	    if(id != 0)
        try{
        		connection = dbHelper.getConnection();
        		String SQL = "DELETE FROM musteri WHERE mid = ?";
                PreparedStatement pstmt = connection.prepareStatement(SQL);	 
        		pstmt.setInt(1, id);
        		pstmt.executeUpdate();
        		 JOptionPane.showMessageDialog(null, "Son Eklenen Kayıt Silindi");
        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } 
	    name.setText("");
		email.setText("");
		
	}
	
	public MusteriOlustur() {
		  
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 460, 288);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		name = new JTextField();
		name.setColumns(10);
		name.setBounds(117, 13, 296, 35);
		contentPane.add(name);
		
		JButton btnMteriOlutur = new JButton("Müşteri Oluştur");
		btnMteriOlutur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String isim = name.getText(); 
				String mail = email.getText();
				insertid=CreateP(isim.trim(), mail.trim());
				
			}
		});
		btnMteriOlutur.setBounds(119, 168, 128, 25);
		contentPane.add(btnMteriOlutur);
		
		email = new JTextField();
		email.setColumns(10);
		email.setBounds(117, 86, 296, 35);
		contentPane.add(email);
		JFrame j=this;
		JButton btnIptal = new JButton("İptal");
		btnIptal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 name.setText("");
				email.setText("");
				j.setVisible(false);
				//deleteP(insertid);
			}
		});
		btnIptal.setBounds(259, 168, 97, 25);
		contentPane.add(btnIptal);
		
		JLabel lblNewLabel = new JLabel("Müşteri İsmi");
		lblNewLabel.setBounds(12, 22, 84, 26);
		contentPane.add(lblNewLabel);
		
		JLabel lblMteriEmail = new JLabel("Müşteri Email");
		lblMteriEmail.setBounds(12, 90, 84, 26);
		contentPane.add(lblMteriEmail);
	}
}
