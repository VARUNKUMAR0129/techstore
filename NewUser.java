

import javax.swing.*;
import java.awt.Color;
import java.awt.event.*;
import java.awt.Font;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;


public class NewUser extends JFrame implements ActionListener
{
	JPanel p1,p2;
	JLabel l1,l2,l3,l4,l5,l6,l7;
	JTextField t1,t2,t3,t4,t5;
	JPasswordField pass;
	JComboBox<String> user;
	JCheckBox show;
	JButton b1,b2;
	Connection con;
   
	NewUser()
	{
		MyConnection my = new MyConnection();
	    con = my.Connect();
		setTitle("New Registration");
		p1 = new JPanel();
		p1.setLayout(null);
		p1.setBackground(Color.gray);
		p1.setBounds(0,0,510,50);
		p2 = new JPanel();
		p2.setLayout(null);
		p2.setBackground(Color.white);
		p2.setBounds(0,52,510,500);
		l1 = new JLabel("New User Registration");
		l1.setFont(new Font("arial",Font.BOLD,18));
		l1.setForeground(Color.white);
		l1.setBounds(170,15,220,30);
		l2 = new JLabel("User Type:");
		l2.setFont(new Font("arial",Font.BOLD,14));
		l2.setBounds(15,85,145,30);
		user = new JComboBox<>();
		user.addItem("---Select User---");
		user.addItem("Admin");
		user.addItem("User");
		user.addItem("Trainer");
		user.setBounds(130,85,200,30);
		l3 = new JLabel("Name:");
		l3.setFont(new Font("arial",Font.BOLD,14));
		l3.setBounds(15,125,145,30);
		t1 = new JTextField();
		t1.setBounds(130,125,200,30);
		l4 = new JLabel("Mobile No.:");
		l4.setFont(new Font("arial",Font.BOLD,14));
		l4.setBounds(15,165,145,30);
		t2 = new JTextField();
		t2.setBounds(130,165,200,30);
		l5 = new JLabel("Gmail:");
		l5.setFont(new Font("arial",Font.BOLD,14));
		l5.setBounds(15,205,145,30);
		t3 = new JTextField();
		t3.setBounds(130,205,200,30);
		l6 = new JLabel("Username:");
		l6.setFont(new Font("arial",Font.BOLD,14));
		l6.setBounds(15,245,145,30);
		t4 = new JTextField();
		t4.setBounds(130,245,200,30);
		l7 = new JLabel("Password:");
		l7.setFont(new Font("arial",Font.BOLD,14));
		l7.setBounds(15,285,145,30);
		pass = new JPasswordField();
		pass.setBounds(130,285,200,30);
		show = new JCheckBox("Show Password");
		show.setBounds(335,285,130,30);
		show.addActionListener(this);
		b1 = new JButton("Sign Up");
		b1.setBounds(130,320,80,30);
		b1.addActionListener(this);
		b2 = new JButton("Login");
		b2.setBounds(220,320,80,30);
		b2.addActionListener(this);
		add(p1);
		add(p2);
		p1.add(l1);
		p2.add(l2);
		p2.add(user);
		p2.add(l3);
		p2.add(t1);
		p2.add(l4);
		p2.add(t2);
		p2.add(l5);
		p2.add(t3);
		p2.add(l6);
		p2.add(t4);
		p2.add(l7);
		p2.add(pass);
		p2.add(show);
		p2.add(b1);
		p2.add(b2);
		setSize(510,600);
		setLayout(null);
		setVisible(true);
		setLocationRelativeTo(null);
	}
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==b1)
		{
			String User = user.getSelectedItem().toString();
			String name = t1.getText();
			String mobile = t2.getText();
			String g = t3.getText();
			String uname = t4.getText();
			String passw = String.valueOf(pass.getPassword());
			if(User.equals(""))
			{
				JOptionPane.showMessageDialog(null,"Select the user type");
			}
			if(name.equals(""))
			{
				JOptionPane.showMessageDialog(null,"Add a name");
			}
			if(mobile.equals(""))
			{
				JOptionPane.showMessageDialog(null,"Add a Mobile No.");
			}
			if(g.equals(""))
			{
				JOptionPane.showMessageDialog(null,"Add a Mail ID");
			}
			if(uname.equals(""))
			{
				JOptionPane.showMessageDialog(null,"Add a Username");
			}
			if(passw.equals(""))
			{
				JOptionPane.showMessageDialog(null,"Add a Password");
			}
			if(checkUsername(uname))
			{
				JOptionPane.showMessageDialog(null,"Username already exists!!");
			}
			else
			{
				PreparedStatement st;
				String query = "insert into users (USER,NAME,MOBILENO,GMAIL,USERNAME,PASSWORD) values(?,?,?,?,?,?)";
				try
				{
					st = con.prepareStatement(query);
					st.setString(1,User);
					st.setString(2,name);
					st.setString(3,mobile);
					st.setString(4,g);
					st.setString(5,uname);
					st.setString(6,passw);
					if(st.executeUpdate() > 0)
					{
						JOptionPane.showMessageDialog(null,"New User Added");
						this.dispose();
					}
				} catch(SQLException ex)
				{
					Logger.getLogger(NewUser.class.getName()).log(Level.SEVERE,null,ex);
				}
			}
	
		}
		else if(e.getSource()==b2)
		{
			this.dispose();
			new Login().setVisible(true);
		}
		else if(show.isSelected())
		{
			pass.setEchoChar((char)0);
		}
		else 
		{
			pass.setEchoChar('*');
		}
	}
	public boolean checkUsername(String t4)
	{
		PreparedStatement st;
		ResultSet rs;
		boolean checkUsername = false;
		String query1 = "SELECT * FROM USERS WHERE USERNAME=?";
		try
		{
			st = con.prepareStatement(query1);
			st.setString(1,t4);
			rs = st.executeQuery();
			if(rs.next())
			{
				checkUsername = true;
			}
		} catch(SQLException ex)
		{
			Logger.getLogger(NewUser.class.getName()).log(Level.SEVERE,null,ex);
		}
		return checkUsername;
	}
			
	public static void main(String[] args)
	{
		new NewUser();
	}
}

 