package dpcs;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;

@SuppressWarnings({ "serial", "unused" })
public class Uninstaller extends JFrame {

	private JPanel contentPane;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Uninstaller frame = new Uninstaller();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Uninstaller() {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Uninstaller.class.getResource("/graphics/Icon.png")));
		setTitle("Uninstaller");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 500, 486);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnUninstallUserApps = new JButton("User Apps");
		btnUninstallUserApps.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UninstallUserApps obj = new UninstallUserApps();
				obj.setVisible(true);
			}
		});

		JLabel lblNoteUnstallationOf = new JLabel(
				"Note: Uninstallation of priv-app is only for android 4.4.x and higher");
		lblNoteUnstallationOf.setBounds(12, 406, 474, 15);
		contentPane.add(lblNoteUnstallationOf);

		btnUninstallUserApps.setBounds(140, 34, 200, 75);
		contentPane.add(btnUninstallUserApps);

		JButton btnUninstallSystemApps = new JButton("System Apps *");
		btnUninstallSystemApps.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UninstallSystemApps obj = new UninstallSystemApps();
				obj.setVisible(true);
			}
		});

		btnUninstallSystemApps.setBounds(140, 151, 200, 75);
		contentPane.add(btnUninstallSystemApps);

		JButton btnUninstallPrivapps = new JButton("Priv-apps *");
		btnUninstallPrivapps.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UninstallPrivApps obj = new UninstallPrivApps();
				obj.setVisible(true);
			}
		});

		btnUninstallPrivapps.setBounds(140, 261, 200, 75);
		contentPane.add(btnUninstallPrivapps);

		JLabel lblNeedsRoot = new JLabel("* Needs root and does not work on production android builds");
		lblNeedsRoot.setBounds(10, 425, 476, 17);
		contentPane.add(lblNeedsRoot);

		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(Uninstaller.class.getResource("/graphics/WhiteBG.jpg")));
		label.setBounds(0, 0, 500, 459);
		contentPane.add(label);
	}
}
