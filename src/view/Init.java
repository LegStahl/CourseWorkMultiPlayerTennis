package view;


import java.awt.Color;
import java.awt.FlowLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Network;



public class Init extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JButton buttonBack, buttonLogIn, buttonSignUp, buttonExit, buttonLogInReg, buttonSignUpReg;
	
	private JPanel window;
	
	private JPanel forms;
	
	private static final int WIDTH = 385; 
	
	private static final int HEIGHT = 300;
	
	private MyListener listener;
	
	private JTextField password, login;
	
	private Network user;
	
	public Init(String title, Network user) {
		
		
		
		super(title);
		this.user = user;
		user.send("irts");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		window = new JPanel();
		
		buttonLogIn = new JButton("LogIn");
		
		buttonSignUp = new JButton("SignUp");
		
		buttonExit = new JButton("Exit");
		
		listener = new MyListener();
		
		buttonExit.addActionListener(listener);
		
		buttonLogIn.addActionListener(listener);
		
		buttonSignUp.addActionListener(listener);
		
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		
		window.setLayout(new FlowLayout());
		
		JLabel label = new JLabel("Welcome!");
		
		label.setHorizontalAlignment(JLabel.CENTER);
		
		this.add(label);
		
		window.add(new JLabel("If you've already used this app"));
		
		window.add(buttonLogIn);
		
		window.add(new JLabel("If this is your first visit"));
		
		window.add(buttonSignUp);
		
		this.add("CENTER" ,window);
		
		this.add(buttonExit);
		
		this.addWindowListener(new WindowAdapter() {
			  public void windowClosing(WindowEvent e) {
			       user.close(); 
			  }
		});
		
		setResizable(false);
		
		this.setLocation(450, 170);
		
		this.setSize(WIDTH, HEIGHT);
		
		this.setVisible(true);
		
		
	}
	private class MyListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == buttonLogIn) {
				
				System.out.println("LogIn");
				
				System.out.println("Pressed");
				
				Init.this.getContentPane().removeAll();
				
				JPanel passwordStr = new JPanel();
				
				passwordStr.setLayout(new FlowLayout());
				
				JPanel loginStr = new JPanel();
				
				loginStr.setLayout(new FlowLayout());
				
				JPanel buttonsStr = new JPanel();
				
				buttonsStr.setLayout(new FlowLayout());
				
				Init.this.repaint();
				
				forms = new JPanel();
				
				forms.setLayout(new BoxLayout(forms, BoxLayout.Y_AXIS));
				
				JLabel form = new JLabel("Please, enter your login and password");
				
				forms.add(form);
			
				JLabel pas = new JLabel("Your password");
				
				JLabel log = new JLabel("Your login");
				
				login = new JTextField(12);
				
				password = new JTextField(12);
				
				loginStr.add(log);
				
				loginStr.add(login);
				
				forms.add(loginStr);
				
				passwordStr.add(pas);
				
				passwordStr.add(password);
				
				forms.add(passwordStr);
				
				buttonBack = new JButton("Back");
				
				buttonBack.addActionListener(listener);
				
				buttonBack.setBackground(new Color(245, 222, 179));
				
				buttonLogInReg = new JButton("Enter");
				
				buttonLogInReg.addActionListener(listener);
				
				buttonLogInReg.setBackground(new Color(245, 222, 179));
				
				buttonsStr.add(buttonBack);
				
				buttonsStr.add(buttonLogInReg);
				
				forms.add(buttonsStr);
				
				forms.setBackground(new Color(211, 211, 211));
				
				Init.this.add(forms);
				
				Init.this.validate();
				
				Init.this.repaint();
			}
			if(e.getSource() == buttonSignUp) {
				System.out.println("SignUp");
				
				System.out.println("Pressed");
				
				Init.this.getContentPane().removeAll();
				
				JPanel passwordStr = new JPanel();
				
				passwordStr.setLayout(new FlowLayout());
				
				JPanel loginStr = new JPanel();
				
				loginStr.setLayout(new FlowLayout());
				
				JPanel buttonsStr = new JPanel();
				
				buttonsStr.setLayout(new FlowLayout());
				
				Init.this.repaint();
				
				forms = new JPanel();
				
				forms.setLayout(new BoxLayout(forms, BoxLayout.Y_AXIS));
				
				JLabel form = new JLabel("Please, enter your new login and new password");
				
				forms.add(form);
			
				JLabel pas = new JLabel("Your password");
				
				JLabel log = new JLabel("Your login");
				
				login = new JTextField(12);
				
				password = new JTextField(12);
				
				loginStr.add(log);
				
				loginStr.add(login);
				
				forms.add(loginStr);
				
				passwordStr.add(pas);
				
				passwordStr.add(password);
				
				forms.add(passwordStr);
				
				buttonBack = new JButton("Back");
				
				buttonBack.addActionListener(listener);
				
				buttonBack.setBackground(new Color(245, 222, 179));
				
				buttonSignUpReg = new JButton("Enter");
				
				buttonSignUpReg.addActionListener(listener);
				
				buttonSignUpReg.setBackground(new Color(245, 222, 179));
				
				buttonsStr.add(buttonBack);
				
				buttonsStr.add(buttonSignUpReg);
				
				forms.add(buttonsStr);
				
				forms.setBackground(new Color(211, 211, 211));
				
				Init.this.add(forms);
				
				
				Init.this.validate();
				
				Init.this.repaint();
			}
			if(e.getSource() == buttonBack) {
				System.out.println("Pressed");
				
				Init.this.getContentPane().removeAll();
				
				Init.this.add(new JLabel("Welcome!"));
				
				Init.this.add(window);
				
				Init.this.add(buttonExit);
				
				Init.this.validate();
				
				Init.this.repaint();
				
			}if(e.getSource() == buttonExit) {
				user.close();
				System.exit(0);
			}
			if(e.getSource() == buttonLogInReg) {
				System.out.println(login.getText() + " " + password.getText());
				user.send("@verify: " + login.getText() + " " + "password: " + password.getText() + " " );
				try {
					Thread.sleep(1000);
					if(user.getStatus()) {
						//заходим в игру удаляем окно
						JOptionPane.showMessageDialog(Init.this, "Yea, it's success!");
						@SuppressWarnings("unused")
						Menu menu = new Menu(user);
						Init.this.dispose();
					}
					else {
						JOptionPane.showMessageDialog(Init.this, "Ooops, something wrong here!");
					}
					}catch (InterruptedException e1) {
						
						e1.printStackTrace();
					}
			}
			if(e.getSource() == buttonSignUpReg) {
				System.out.println(login.getText() + " " + password.getText());
				user.send("@newUser: "+ login.getText() + " " + "password: " + password.getText() + " ");
				try {
					Thread.sleep(1000);
					if(user.getStatus()) {
						//заходим в игру удаляем окно
						JOptionPane.showMessageDialog(Init.this, "Yea, it's success!");
						@SuppressWarnings("unused")
						Menu menu = new Menu(user);
						Init.this.dispose();
						
					}
					else {
						JOptionPane.showMessageDialog(Init.this, "Ooops, something wrong here!");
					}
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}	
	}
}

			
		
	

	
	

