import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Font; 
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JLabel;

public class Main extends JFrame {

	private JPanel contentPane;
	private JTextField mid;
	private JTextField isim;
	private JButton siparis;
	private JLabel hosgeldin;
	JButton bilgiler;
	JButton olustur;
	public static int main_m_id ;
	JRadioButton R_must,R_yetk;
	public static String user; 
	private JButton takip;
	private JButton sipdegist;
	private JButton btndemeTakip;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public boolean GirisYap(boolean musteri,int mid,String name) {
		    PreparedStatement st;
	        ResultSet rs;
	        String nerden;
	        String query;
	        user="my_admin";
	        if(musteri) 
	        	 query = "select * from musteri where mid=? and isim=?";
	        else
	        	 query = "select * from yetkili where yid=? and isim=?";
	        DbHelper dbhelper =new DbHelper();
	      
	        try{ 
	           Connection conn=dbhelper.getConnection();
	           st = conn.prepareStatement(query);  
	           st.setInt(1, mid);
	           st.setString(2, name);
	           rs = st.executeQuery();
	           //rs.next(); 
	           while (rs.next ()) {
	        	   
	        	   if(!musteri) {
	        	   		takip.setVisible(true);
	        	   		sipdegist.setVisible(true);
	        		   if(rs.getBoolean(2))	{
	        			   user="my_admin";
	        			   olustur.setVisible(true);
	        			 
	        		   }
	        			   else
	        			   user="personel";
	        	   }else {
	        		   user="musteri";

						siparis.setVisible(true);
	        	   }
	        	   		bilgiler.setVisible(true);
		            	return true;
	            }
	       }
	       catch(SQLException e){
	           JOptionPane.showMessageDialog(null, e);
	       }
	        return false;
		
	}
	/**
	 * Create the frame.
	 */
	public Main() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1046, 696);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane); 
		contentPane.setLayout(null);
		
		olustur = new JButton("Müşteri Oluştur");
		olustur.setVisible(false);
		olustur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) { 
				MusteriOlustur frame = new MusteriOlustur();
				frame.setLocationRelativeTo(null); 
				frame.setVisible(true); 
			}
		});
		olustur.setFont(new Font("Tahoma", Font.ITALIC, 20));
		olustur.setBounds(801, 92, 215, 66);
		contentPane.add(olustur);
		
		bilgiler = new JButton("Müşteri Bilgileri");
		bilgiler.setVisible(false);
		bilgiler.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MusteriDuzenle frame = new MusteriDuzenle();
				frame.setLocationRelativeTo(null); 
				frame.setVisible(true);
				
			}
		});
		bilgiler.setFont(new Font("Tahoma", Font.ITALIC, 20));
		bilgiler.setBounds(801, 170, 215, 66);
		contentPane.add(bilgiler);
		
		JPanel panel = new JPanel();
		panel.setVisible(false);
		panel.setBounds(12, 13, 449, 238);
		contentPane.add(panel);
		panel.setLayout(null);
		
		mid = new JTextField();
		mid.setColumns(10);
		mid.setBounds(128, 13, 296, 35);
		panel.add(mid);
		
		isim = new JTextField();
		isim.setColumns(10);
		isim.setBounds(128, 89, 296, 35);
		panel.add(isim);
		
		R_must = new JRadioButton("Müşteri");
		R_must.setBounds(128, 142, 127, 25);
		panel.add(R_must);
		
		R_yetk = new JRadioButton("Yetkili");
		R_yetk.setBounds(274, 142, 127, 25);
		panel.add(R_yetk);
		
		JButton button = new JButton("Giriş Yap");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean musteri;
				main_m_id =Integer.parseInt(mid.getText()); 
				String m_name=isim.getText().trim();
				if(R_must.isSelected()) 
					musteri=true; 
				else 
					musteri=false;
				
				if(GirisYap(musteri,main_m_id, m_name)) {
					hosgeldin.setText("Hosgeldiniz "+m_name);
					hosgeldin.setVisible(true);
					panel.setVisible(false);
					btndemeTakip.setVisible(true);
				}
				else
					JOptionPane.showMessageDialog(null, "İd ya da isim yanlış !");
				
			}
		});
		button.setBounds(128, 176, 108, 53);
		panel.add(button);
		
		ButtonGroup G = new ButtonGroup();
		
		G.add(R_must);
		G.add(R_yetk);
		
		JLabel lblNewLabel = new JLabel("ID");
		lblNewLabel.setBounds(30, 22, 86, 16);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("İsim");
		lblNewLabel_1.setBounds(30, 96, 86, 21);
		panel.add(lblNewLabel_1);
		
		hosgeldin = new JLabel("New label");
		hosgeldin.setForeground(Color.PINK);
		hosgeldin.setHorizontalAlignment(SwingConstants.CENTER);
		hosgeldin.setVisible(false);
		hosgeldin.setBackground(Color.YELLOW);
		hosgeldin.setFont(new Font("Tahoma", Font.ITALIC, 21));
		hosgeldin.setBounds(240, 13, 582, 50);
		contentPane.add(hosgeldin);
		
		siparis = new JButton("Sipariş Oluştur");
		siparis.setVisible(false);
		siparis.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Siparis frame = new Siparis();
				frame.setLocationRelativeTo(null); 
				frame.setVisible(true);
			} 
			
		});
		siparis.setBounds(12, 286, 198, 84);
		contentPane.add(siparis);
		
		takip = new JButton("Sipariş Takip");
		takip.setVisible(false);
		takip.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Takip frame=new Takip();
				frame.setLocationRelativeTo(null); 
				frame.setVisible(true);
			}
		});
		takip.setBounds(12, 404, 198, 84);
		contentPane.add(takip);
		
		sipdegist = new JButton("Sipariş Değiştir/İptal");
		sipdegist.setVisible(false);
		sipdegist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SiparisDegistir frame=new SiparisDegistir();
				frame.setLocationRelativeTo(null); 
				frame.setVisible(true);
			}
		});
		sipdegist.setBounds(12, 516, 198, 72);
		contentPane.add(sipdegist);
		
		btndemeTakip = new JButton("Ödeme Takip");
		btndemeTakip.setVisible(false);
		btndemeTakip.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OdemeTakip frame=new OdemeTakip();
				frame.setLocationRelativeTo(null); 
				frame.setVisible(true);
				
			}
		});
		btndemeTakip.setBounds(831, 519, 185, 66);
		contentPane.add(btndemeTakip);
		
		JButton btnNewButton = new JButton("Giriş Yapmak için Tıklayınız ");
		btnNewButton.setBounds(23, 13, 205, 84);
		contentPane.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					panel.setVisible(true);
					btnNewButton.setVisible(false);
				}
		});
		btnNewButton.setFont(new Font("Tw Cen MT", Font.ITALIC, 13));
		btnNewButton.setBackground(Color.CYAN);
	}
}
