import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.ibm.icu.text.SimpleDateFormat;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Takip extends JFrame {
	private DefaultTableModel model_1;
	DefaultTableModel sepetModel;
	private JPanel contentPane;
	private JTable onay;
	private JTextField sip;
	JButton btnFaturaCikar;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Takip frame = new Takip();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
 
	 
	private void faturaCikar(int sipar) {

		  
		Connection connection=null;
		DbHelper dbHelper=new DbHelper(); 
		PreparedStatement statement =null;
		String SQL = "Insert INTO fatura (sip,tarih) values (?,?);";
		try {

		    connection=dbHelper.getConnection();
			statement = connection.prepareStatement(SQL);
			statement.setInt(1,sipar);
			statement.setString(2,java.time.LocalDate.now().toString());
			statement.executeUpdate(); 
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	 
	public void Listele(){  
        PreparedStatement st;
        ResultSet rs;
        String query = "select * from siparis ;";
        DbHelper dbhelper =new DbHelper();
        int i=1;
        for(int j=1;j<20;j++) {
        	model_1.setValueAt("",j,0);
        	model_1.setValueAt("",j,1);  
        }
        try{
           Connection conn=dbhelper.getConnection();
           st = conn.prepareStatement(query);
           rs = st.executeQuery();
          
           //rs.next(); 
           while (rs.next ()) {
        	   
        	   int b_siparis;
	           int musteri;
	           b_siparis=rs.getInt(1);
	           musteri=rs.getInt(2);
        	//  model.addRow(new Object[]{isbn,isim,birimfiyat}); 
	 	       model_1.setValueAt(b_siparis,i,0);
	 	      model_1.setValueAt(musteri,i,1);  
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
	public Takip() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 645, 549);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel siparisler = new JLabel("Siparisler");
		siparisler.setFont(new Font("Tahoma", Font.PLAIN, 17));
		siparisler.setBounds(12, 23, 157, 25);
		contentPane.add(siparisler);
		
		sip = new JTextField();
		sip.setFont(new Font("Tahoma", Font.PLAIN, 17));
		sip.setColumns(10);
		sip.setBounds(447, 227, 143, 40);
		contentPane.add(sip);
		JLabel lblSipariNo = new JLabel("Sipariş No");
		lblSipariNo.setBounds(477, 189, 109, 25);
		contentPane.add(lblSipariNo);
		
		model_1 = new DefaultTableModel();
		model_1.addColumn("Col1"); 
		model_1.addColumn("Col2");
		model_1.addRow(new Object[]{"Sipariş No","Müşteri Id"}); 
		for(int i=0;i<20;i++)
			model_1.addRow(new Object[]{"",""});
		
		onay = new JTable(model_1);
		onay.setRowHeight(30);
		onay.setEnabled(false);
		onay.setBounds(12, 61, 413, 380);
		contentPane.add(onay);
		
		
		sepetModel = new DefaultTableModel(); 
		
		sepetModel.addColumn("Col1"); 
		sepetModel.addColumn("Col2");  
		sepetModel.addRow(new Object[]{"Sipariş No","Müşteri Id"}); 
		for(int i =1;i<20;i++) 
			sepetModel.addRow(new Object[]{"","",""});
		
		 btnFaturaCikar = new JButton("FATURA CIKAR");
		btnFaturaCikar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int siparisno=Integer.parseInt(sip.getText());
				faturaCikar(siparisno);
				 JOptionPane.showMessageDialog(null, "Siparis Eklendi");
			}
		});
		btnFaturaCikar.setBounds(447, 297, 143, 47);
		contentPane.add(btnFaturaCikar);
		Listele();
	}
}
