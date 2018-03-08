package sorc.Code;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JSeparator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class FirstTimeGUI  extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frmScheduleRetrival;
	private JTextField txtNumbers;
	private JPasswordField pwdHUBPass;
	private JTextField txtQ1;
	private JTextField txtQ2;
	private JTextField txtQ3;
	private JTextField txtA1;
	private JTextField txtA2;
	private JTextField txtA3;
	private JPasswordField pwdMaster;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					GUI window = new GUI();
//					window.frmScheduleRetrival.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the application.
	 */
	public FirstTimeGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmScheduleRetrival = new JFrame();
		frmScheduleRetrival.setTitle("Schedule Retrival");
		frmScheduleRetrival.setBounds(100, 100, 428, 390);
		frmScheduleRetrival.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmScheduleRetrival.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 412, 352);
		frmScheduleRetrival.getContentPane().add(panel);
		panel.setLayout(null);
		
		txtNumbers = new JTextField();
		txtNumbers.setBounds(179, 11, 136, 20);
		panel.add(txtNumbers);
		txtNumbers.setColumns(10);
		
		pwdHUBPass = new JPasswordField();
		pwdHUBPass.setBounds(179, 44, 136, 20);
		panel.add(pwdHUBPass);
		
		JLabel lblPartnerNumbers = new JLabel("Partner Numbers (US*******)");
		lblPartnerNumbers.setBounds(10, 14, 159, 14);
		panel.add(lblPartnerNumbers);
		
		JLabel lblHubPassword = new JLabel("HUB Password");
		lblHubPassword.setBounds(48, 47, 74, 14);
		panel.add(lblHubPassword);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 75, 385, 13);
		panel.add(separator);
		
		JLabel lblSeq = new JLabel("Security Question #2");
		lblSeq.setBounds(10, 148, 102, 14);
		panel.add(lblSeq);
		
		JLabel label = new JLabel("Security Question #1");
		label.setBounds(10, 86, 102, 14);
		panel.add(label);
		
		JLabel lblSecurityQuestion = new JLabel("Security Question #3 (Optional)");
		lblSecurityQuestion.setBounds(10, 201, 159, 14);
		panel.add(lblSecurityQuestion);
		
		txtQ1 = new JTextField();
		txtQ1.setEditable(false);
		txtQ1.setColumns(10);
		txtQ1.setBounds(179, 83, 216, 20);
		panel.add(txtQ1);
		
		txtQ2 = new JTextField();
		txtQ2.setEditable(false);
		txtQ2.setColumns(10);
		txtQ2.setBounds(179, 145, 216, 20);
		panel.add(txtQ2);
		
		txtQ3 = new JTextField();
		txtQ3.setEditable(false);
		txtQ3.setColumns(10);
		txtQ3.setBounds(179, 197, 216, 20);
		panel.add(txtQ3);
		
		txtA1 = new JTextField();
		txtA1.setColumns(10);
		txtA1.setBounds(179, 114, 136, 20);
		panel.add(txtA1);
		
		txtA2 = new JTextField();
		txtA2.setColumns(10);
		txtA2.setBounds(179, 170, 136, 20);
		panel.add(txtA2);
		
		JLabel lblQuestionAnswer = new JLabel("Question #1 Answer");
		lblQuestionAnswer.setBounds(10, 117, 102, 14);
		panel.add(lblQuestionAnswer);
		
		JLabel lblQuestionAnswer_1 = new JLabel("Question #2 Answer");
		lblQuestionAnswer_1.setBounds(10, 173, 102, 14);
		panel.add(lblQuestionAnswer_1);
		
		JLabel lblQuestionAnswer_2 = new JLabel("Question #3 Answer (Optional)");
		lblQuestionAnswer_2.setBounds(10, 227, 159, 14);
		panel.add(lblQuestionAnswer_2);
		
		txtA3 = new JTextField();
		txtA3.setColumns(10);
		txtA3.setBounds(179, 224, 136, 20);
		panel.add(txtA3);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 254, 385, 13);
		panel.add(separator_1);
		
		JLabel lblMasterPassword = new JLabel("Master Password");
		lblMasterPassword.setToolTipText("This will be used When you run the Program next time");
		lblMasterPassword.setBounds(46, 275, 89, 14);
		panel.add(lblMasterPassword);
		
		pwdMaster = new JPasswordField();
		pwdMaster.setBounds(179, 269, 136, 20);
		panel.add(pwdMaster);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(151, 312, 89, 23);
		panel.add(btnSubmit);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
