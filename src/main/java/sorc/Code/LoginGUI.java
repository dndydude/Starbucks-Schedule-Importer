package sorc.Code;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPasswordField;

public class LoginGUI extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPasswordField pwdMasterPass;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					LoginGUI frame = new LoginGUI();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public LoginGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 238, 147);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblMasterPassword = new JLabel("Master Password");
		lblMasterPassword.setBounds(69, 11, 82, 14);
		contentPane.add(lblMasterPassword);
		
		JButton btnGetSchedule = new JButton("Get Schedule");
		btnGetSchedule.setBounds(53, 68, 118, 23);
		contentPane.add(btnGetSchedule);
		
		pwdMasterPass = new JPasswordField();
		pwdMasterPass.setBounds(53, 37, 118, 20);
		contentPane.add(pwdMasterPass);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
