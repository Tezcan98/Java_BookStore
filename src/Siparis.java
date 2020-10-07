import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.TableModel;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Siparis extends JFrame {

	private JPanel contentPane;
	private JTable Urunler;
	private DefaultTableModel model;
	private DefaultTableModel sipmodel;
	private JTextField isbntext;
	private JTable siparisler;
	private DefaultTableModel sepetModel;
	private JTable Sepet;
	private JComboBox miktar; 
	private JTextField numara;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					Siparis frame = new Siparis();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public void urunListele(){  
        PreparedStatement st;
        ResultSet rs;
        String query = "select * from kitaplar;";
        DbHelper dbhelper =new DbHelper();
        int i=1;
        try{
           Connection conn=dbhelper.getConnection();
           st = conn.prepareStatement(query);
           rs = st.executeQuery();
           //rs.next(); 
           while (rs.next ()) {
        	   String isbn;
        	   String isim;
	           Double birimfiyat;
	           isbn=rs.getString(1);
	           isim=rs.getString(2);
	           birimfiyat = rs.getDouble(3);
        	//  model.addRow(new Object[]{isbn,isim,birimfiyat}); 
	 	       model.setValueAt(isbn,i,0);
	 	       model.setValueAt(isim,i,1); 
	 	       model.setValueAt(birimfiyat,i,2);
	 	       i++;
            }
       }
       catch(SQLException e){
           JOptionPane.showMessageDialog(null, e);
       }
	}
	
	
	
	public void sepetListele(){  
        PreparedStatement st;
        ResultSet rs;
        String query = "select * from kitapsiparisi,siparis where siparis=sip and musteri=?;";
        DbHelper dbhelper =new DbHelper();
        int i=1;
        for(int j=1;j<20;j++) {
	 	       sepetModel.setValueAt("",j,0);
	 	       sepetModel.setValueAt("",j,1); 
	 	       sepetModel.setValueAt("",j,2);
	 	       sipmodel.setValueAt("", j,0);
	 	       sipmodel.setValueAt("", j,1);
        }
        try{
           Connection conn=dbhelper.getConnection();
           st = conn.prepareStatement(query);
           st.setInt(1, Main.main_m_id);
           rs = st.executeQuery();
          
           //rs.next(); 
           while (rs.next ()) {
        	   String isbn;
        	   int sip;
	           int miktar;
	           sip=rs.getInt(1);
	           isbn=rs.getString(2);
	           miktar= rs.getInt(3);
        	//  model.addRow(new Object[]{isbn,isim,birimfiyat}); 
		 	   sipmodel.setValueAt(sip, i,0);
		 	   sipmodel.setValueAt(Main.main_m_id, i,1);
	 	       sepetModel.setValueAt(sip,i,0);
	 	       sepetModel.setValueAt(isbn,i,1); 
	 	       sepetModel.setValueAt(miktar,i,2);
	 	       i++;
            }
       }
       catch(SQLException e){
           JOptionPane.showMessageDialog(null, e);
       }
	}
	
	private void sepetekle(int ISBN,int count) {
		
		
		int sipariskodu=0; 
		Connection connection=null;
		DbHelper dbHelper=new DbHelper();
		PreparedStatement statement =null;
		String SQL = "insert into siparis(musteri) values (?);";
		try (	Connection conn = dbHelper.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(SQL,
                Statement.RETURN_GENERATED_KEYS))
		{
            pstmt.setInt(1, Main.main_m_id);
 
            int affectedRows = pstmt.executeUpdate();
            // check the affected rows 
            if (affectedRows > 0) {
                // get the ID back
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        sipariskodu= rs.getInt(1);  
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            } 
    		SQL = "Insert INTO kitapsiparisi (sip,isbn,miktar) values (?,?,?);";
    		statement = conn.prepareStatement(SQL);
    		statement.setInt(3,count);
    		statement.setInt(2,ISBN);
    		statement.setInt(1,sipariskodu);
    		statement.executeUpdate(); 
    		sepetListele(); 
		
		
		}
	      catch(SQLException e){
	           JOptionPane.showMessageDialog(null, e);
	      }
		
		
		 
		
	}
	private void sepetcikar(int siparis) {
		Connection connection=null;
	    DbHelper dbHelper=new DbHelper();
	 
        try{
        		connection = dbHelper.getConnection();
        		String SQL = "DELETE FROM kitapsiparisi WHERE sip = ? ";
                PreparedStatement pstmt = connection.prepareStatement(SQL);	 
        		pstmt.setInt(1, siparis); 
        		pstmt.executeUpdate();
        		 
        		for(int i =1;i<20;i++) { 
        			sepetModel.setValueAt("",i,0);
    	 	       sepetModel.setValueAt("",i,1); 
    	 	       sepetModel.setValueAt("",i,2);
        		}
        		sepetListele();
        		
    		
        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } 

        
        try{
    		connection = dbHelper.getConnection();
    		String SQL = "DELETE FROM b_siparis WHERE b_siparis = ?";
            PreparedStatement pstmt = connection.prepareStatement(SQL);	 
    		pstmt.setInt(1, siparis);
    		pstmt.executeUpdate();
    		sepetListele();
    				
		
        }catch (SQLException ex) {
        	JOptionPane.showMessageDialog(null, ex);
        } 
   
	}
	
	
	

	/**
	 * Create the frame.
	 * @param string 
	 */
	public Siparis() {
 
		setTitle("Sipariş Ekranı");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1344, 557);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		model = new DefaultTableModel(); 
		Urunler = new JTable(model);
	        Urunler.setPreferredScrollableViewportSize(Urunler.getPreferredSize());
	        Urunler.setBounds(37, 44, 428, 316);
//	        JScrollPane scrollPane = new JScrollPane(table);
	       // getContentPane().add(scrollPane); 
	       
	     contentPane.add(Urunler);
		// table.setAutoscrolls(true);
		// Create a couple of columns
	     
		model.addColumn("Col1"); 
		model.addColumn("Col2"); 
		model.addColumn("Col3");  
		// Append a row  
    	 
		Urunler.setRowHeight(30); 

		model.addRow(new Object[]{"ISBN","Kitap İsmi","Birim Fiyat"}); 
		for(int i =1;i<20;i++)
			model.addRow(new Object[]{"","",""}); 
		
		isbntext = new JTextField();
		isbntext.setFont(new Font("Tahoma", Font.PLAIN, 17));
		isbntext.setBounds(504, 73, 143, 40);
		contentPane.add(isbntext);
		isbntext.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("ISBN");
		lblNewLabel.setBounds(550, 44, 56, 16);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton(">>>Sepete Ekle>>>");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 int count=Integer.parseInt((String) miktar.getSelectedItem());
				 int isbn=Integer.parseInt(isbntext.getText());
				 sepetekle(isbn, count);
				
			}
		});
		btnNewButton.setBounds(489, 200, 176, 40);
		contentPane.add(btnNewButton);
		

	
		JLabel lblrnler = new JLabel("Ürünler");
		lblrnler.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblrnler.setBounds(37, 6, 81, 25);
		contentPane.add(lblrnler);
		
		JButton button = new JButton("<<<Sepetten Çıkar<<<");
		button.setVisible(false);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int siparis=Integer.parseInt(numara.getText());
				sepetcikar(siparis);
			}
		});
		button.setBounds(489, 320, 176, 40);
		contentPane.add(button);
		
		JLabel lblMiktar = new JLabel("Miktar");
		lblMiktar.setBounds(550, 118, 56, 16);
		contentPane.add(lblMiktar);
		
		miktar = new JComboBox();
		miktar.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "20", "25", "30", "50", "100"}));
		miktar.setMaximumRowCount(20);
		miktar.setBounds(524, 154, 115, 22);
		contentPane.add(miktar);
		
		sepetModel= new DefaultTableModel(); 
			
		sepetModel.addColumn("Col1"); 
		sepetModel.addColumn("Col2"); 
		sepetModel.addColumn("Col3");	
		sepetModel.addRow(new Object[]{"Siparis Kodu","ISBN","Miktar"}); 
		for(int i =1;i<20;i++) 
			sepetModel.addRow(new Object[]{"","",""}); 
		Sepet = new JTable(sepetModel);
		Sepet.setRowHeight(30);
		Sepet.setPreferredScrollableViewportSize(new Dimension(0, 0));
		Sepet.setEnabled(false);
		Sepet.setBounds(686, 44, 400, 327); 
		contentPane.add(Sepet);
		JLabel lblSipari = new JLabel("Sepet ( KitapSiparis)");
		lblSipari.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblSipari.setBounds(686, 13, 164, 25);
		contentPane.add(lblSipari);
		
		JLabel lblSipariKodu = new JLabel("Sipariş Numarası");
		lblSipariKodu.setVisible(false);
		lblSipariKodu.setBounds(521, 253, 126, 16);
		contentPane.add(lblSipariKodu);
		
		numara = new JTextField();
		numara.setVisible(false);
		numara.setFont(new Font("Tahoma", Font.PLAIN, 17));
		numara.setColumns(10);
		numara.setBounds(504, 270, 143, 40);
		contentPane.add(numara);
		
		sipmodel= new DefaultTableModel(); 
		
		sipmodel.addColumn("Col1"); 
		sipmodel.addColumn("Col2"); 
		sipmodel.addRow(new Object[]{"Sipariş No","Müşteri Id"}); 
		for(int i =1;i<20;i++) 
			sipmodel.addRow(new Object[]{"",""}); 
		table = new JTable(sipmodel);
		table.setRowHeight(30);
		table.setEnabled(false);
		table.setBounds(1141, 44, 134, 327);
		contentPane.add(table);
		
		JLabel lblSiparisler = new JLabel("Siparisler");
		lblSiparisler.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblSiparisler.setBounds(1141, 13, 185, 24);
		contentPane.add(lblSiparisler);
	 
		urunListele();
		sepetListele();
	}
}
