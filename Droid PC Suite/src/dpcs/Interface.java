package dpcs;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JScrollPane;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.commons.codec.digest.DigestUtils;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.core.ZipFile;
import java.awt.Color;

@SuppressWarnings({ "serial", "unused" })
public class Interface extends JFrame {
	JLabel FlasherDone, GeneralDone, WiperDone, BackupAndRestoreDone, ADBConnectionLabel, RootStatusLabel, AppStatus;
	boolean adbconnected = false, rooted = false;
	private JPanel contentPane;
	JTextArea CalculatedCrypto;

	volatile boolean flag = true;
	Runnable r = new Runnable() {
		public void run() {
			while (flag) {
				try {
					adbconnected = false;
					Process p1 = Runtime.getRuntime().exec("adb devices");
					p1.waitFor();
					Process p2 = Runtime.getRuntime().exec("adb shell touch /sdcard/.checkadbconnection");
					p2.waitFor();
					Process p3 = Runtime.getRuntime().exec("adb pull /sdcard/.checkadbconnection");
					p3.waitFor();
					Process p4 = Runtime.getRuntime().exec("adb shell rm /sdcard/.checkadbconnection");
					p4.waitFor();
					File file = new File(".checkadbconnection");
					if (file.exists() && !file.isDirectory()) {
						file.delete();
						adbconnected = true;
						ADBConnectionLabel.setText("Device is connected");
					} else {
						adbconnected = false;
						ADBConnectionLabel.setText("");
						ADBConnectionLabel.setText("Connect your device");
					}
				} catch (Exception e1) {
					System.err.println(e1);
				}

				try {
					File file = new File("su");
					Process p1 = Runtime.getRuntime().exec("adb pull /system/xbin/su");
					p1.waitFor();
					if (file.exists() && !file.isDirectory()) {
						file.delete();
						rooted = true;
						RootStatusLabel.setText("Device is rooted");
					} else {
						if (adbconnected == true) {
							rooted = false;
							RootStatusLabel.setText("Device is not rooted");
						} else {
							rooted = false;
							RootStatusLabel.setText("");
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
				}
			}
		}
	};

	public static void main(String[] args) {

		/*
		 * UNIMPIMENTED UI FEATURE; TO BE TARGETED IN FUTURE UPDATES.
		 * 
		 * / try {
		 * UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		 * } catch (Throwable e) { e.printStackTrace(); } /
		 */

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Interface frame = new Interface();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Interface() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Interface.class.getResource("/graphics/Icon.png")));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) { // Cache remover
				File file1 = new File("explorer.properties");
				if (file1.exists() && !file1.isDirectory()) {
					file1.delete();
				}
				File file2 = new File("log.txt");
				if (file2.exists() && !file2.isDirectory()) {
					file2.delete();
				}
				File file3 = new File("lastLog.txt");
				if (file3.exists() && !file3.isDirectory()) {
					file3.delete();
				}
				File file4 = new File(".logcat.txt");
				if (file4.exists() && !file4.isDirectory()) {
					file3.delete();
				}
				File file5 = new File(".userapps.txt");
				if (file5.exists() && !file5.isDirectory()) {
					file5.delete();
				}
				File file6 = new File(".privapps.txt");
				if (file6.exists() && !file6.isDirectory()) {
					file6.delete();
				}
				File file7 = new File(".systemapps.txt");
				if (file7.exists() && !file7.isDirectory()) {
					file7.delete();
				}
			}
		});

		setTitle("Droid PC Suite");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1088, 715);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		JMenuItem mntmExit = new JMenuItem("Exit");

		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});

		mnFile.add(mntmExit);
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);

		JMenuItem mntmCommonWorkarounds = new JMenuItem("Common workarounds");
		mntmCommonWorkarounds.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Workarounds obj = new Workarounds();
				obj.setVisible(true);
			}
		});
		mnHelp.add(mntmCommonWorkarounds);

		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				About obj = new About();
				obj.setVisible(true);
			}
		});

		JMenuItem mntmCheckForUpdates = new JMenuItem("Check for updates");
		mntmCheckForUpdates.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null,
						"Until auto updater is implemented, please visit XDA thread for news");
				// UNIMPIMENTED UPDATE FEATURE; DO NOT MODIFY, UNLESS YOU KNOW
				// WHAT YOU ARE DOING!
				try {
					System.out.println("Checking for updates...");
					// URL link = new URL
					// ("https://www.dropbox.com/s/fg53qb9j3sesbi1/AUTP.zip?dl=1");
					// //200 OK URL
					URL link = new URL("https://google.com/nobodycares/"); // JUST
																			// A
																			// FILLER
																			// AS
																			// IT
																			// IS
																			// UNIMPLIMENTED,
																			// SO...;
																			// BTW,
																			// 404
																			// CODE:
																			// INVALID
																			// URL.
					HttpURLConnection huc = (HttpURLConnection) link.openConnection();
					huc.setRequestMethod("GET");
					huc.connect();
					int code = huc.getResponseCode();
					if (code == 200) {
						if (JOptionPane.showConfirmDialog(null, "Update Available! Do you want to download update?",
								"Update", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
							String path = System.getProperty("user.dir"); // Cache
																			// remover
							File file = new File(path);
							File[] files = file.listFiles();
							for (File f : files) {
								if (f.isFile() && f.exists()) {
									f.delete();
								}
							}
							String fileName = "DPCS.jar";
							InputStream in = new BufferedInputStream(link.openStream());
							ByteArrayOutputStream out = new ByteArrayOutputStream();
							byte[] buf = new byte[1024];
							int n = 0;
							while (-1 != (n = in.read(buf))) {
								out.write(buf, 0, n);
							}
							out.close();
							in.close();
							byte[] response = out.toByteArray();
							FileOutputStream fos = new FileOutputStream(fileName);
							fos.write(response);
							fos.close();
							// EXTRACTOR
							String source = "DPCS.jar";
							try {
								ZipFile zipFile = new ZipFile(source);
								zipFile.extractAll(path);
							} catch (ZipException e1) {
								e1.printStackTrace();
							}
							// RESTART THE APP
							if (JOptionPane.showConfirmDialog(null,
									"Download complete, Do you want to restart Driod PC Suite?", "Update",
									JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
								Runtime runtime = Runtime.getRuntime();
								Process p4 = runtime.exec("java -jar DPCS.jar");
								System.exit(0);
							}
						}
					} else if (code == 404) {
						System.out.println("Update not available");
					} else {
						System.out.println("Error occured while checking for updates. Error Code: " + code);
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		mnHelp.add(mntmCheckForUpdates);
		mnHelp.add(mntmAbout);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnForceConnect = new JButton("Force connect");
		btnForceConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null,
						"Go to developer options and turn off android debugging and turn it on again");
				JOptionPane.showMessageDialog(null,
						"Now tap on Revoke USB debugging authorizations and confirm it by tapping OK on android device");
				JOptionPane.showMessageDialog(null, "Now disconnect your android device and reconnect it via USB");
				JOptionPane.showMessageDialog(null, "Reboot your device. After it completely boots up click OK");
				try {
					adbconnected = false; // Force kill adb server 3 times!
					Process p1 = Runtime.getRuntime().exec("adb kill-server");
					p1.waitFor();
					Process p2 = Runtime.getRuntime().exec("adb kill-server");
					p2.waitFor();
					Process p3 = Runtime.getRuntime().exec("adb kill-server");
					p3.waitFor();
					Process p4 = Runtime.getRuntime().exec("adb devices");
					p4.waitFor();
					JOptionPane.showMessageDialog(null, "Check if your device asks to Allow USB debugging");
					JOptionPane.showMessageDialog(null,
							"If yes check always allow from this computer checkbox and tap OK on your android device");
					Process p5 = Runtime.getRuntime().exec("adb shell touch /sdcard/.checkadbconnection");
					p5.waitFor();
					Process p6 = Runtime.getRuntime().exec("adb pull /sdcard/.checkadbconnection");
					p6.waitFor();
					Process p7 = Runtime.getRuntime().exec("adb shell rm /sdcard/.checkadbconnection");
					p7.waitFor();
					File file = new File(".checkadbconnection");
					if (file.exists() && !file.isDirectory()) {
						file.delete();
						adbconnected = true;
						ADBConnectionLabel.setText("Device is connected");
						JOptionPane.showMessageDialog(null, "Success!");
					} else {
						adbconnected = false;
						ADBConnectionLabel.setText("");
						ADBConnectionLabel.setText("Connect your device");
						JOptionPane.showMessageDialog(null,
								"Please try again or perhaps try installing your android device adb drivers on PC");
					}
				} catch (Exception e1) {
					System.err.println(e1);
				}

				try {
					File file = new File("su");
					Process p1 = Runtime.getRuntime().exec("adb pull /system/xbin/su");
					p1.waitFor();
					if (file.exists() && !file.isDirectory()) {
						file.delete();
						rooted = true;
						RootStatusLabel.setText("Device is rooted");
					} else {
						if (adbconnected == true) {
							rooted = false;
							RootStatusLabel.setText("Device is not rooted");
						} else {
							rooted = false;
							RootStatusLabel.setText("");
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnForceConnect.setBounds(921, 194, 136, 25);
		contentPane.add(btnForceConnect);

		AppStatus = new JLabel("");
		AppStatus.setBounds(12, 230, 1062, 17);
		contentPane.add(AppStatus);

		RootStatusLabel = new JLabel("");
		RootStatusLabel.setForeground(Color.RED);
		RootStatusLabel.setBounds(921, 12, 153, 17);
		contentPane.add(RootStatusLabel);

		ADBConnectionLabel = new JLabel("");
		ADBConnectionLabel.setForeground(Color.GREEN);
		ADBConnectionLabel.setBounds(921, 0, 152, 17);
		contentPane.add(ADBConnectionLabel);

		JLabel label_2 = new JLabel("");
		label_2.setIcon(new ImageIcon(Interface.class.getResource("/graphics/Interface_logo.png")));
		label_2.setBounds(50, 0, 1038, 256);
		contentPane.add(label_2);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 255, 1075, 447);
		contentPane.add(tabbedPane);

		JPanel panel_7 = new JPanel();
		tabbedPane.addTab("General", null, panel_7, null);
		panel_7.setLayout(null);

		final JButton btnInstallUserApp = new JButton("Install Apps as User");
		btnInstallUserApp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GeneralDone.setIcon(new ImageIcon(Interface.class.getResource("/graphics/WhiteBG.jpg")));
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("APK Files", "apk");
				chooser.setFileFilter(filter);
				int returnVal = chooser.showOpenDialog(getParent());
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = chooser.getSelectedFile();
					String filename = chooser.getSelectedFile().getName();
					try {
						AppStatus.setText("Installing...");
						String[] commands = new String[3];
						commands[0] = "adb";
						commands[1] = "install";
						commands[2] = file.getAbsolutePath();
						AppStatus.setText("Installing App...");
						Process p1 = Runtime.getRuntime().exec(commands, null);
						p1.waitFor();
						AppStatus.setText(filename + " has been successfully installed on your android device!");
						GeneralDone.setIcon(new ImageIcon(Interface.class.getResource("/graphics/Done.png")));
						btnInstallUserApp.setSelected(false);
					} catch (Exception e1) {
						System.err.println(e1);
					}
				}
			}
		});

		final JButton btnInstallAsPrivApp = new JButton("Install Apps to priv-app *");
		btnInstallAsPrivApp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GeneralDone.setIcon(new ImageIcon(Interface.class.getResource("/graphics/WhiteBG.jpg")));
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("APK Files", "apk");
				chooser.setFileFilter(filter);
				int returnVal = chooser.showOpenDialog(getParent());
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = chooser.getSelectedFile();
					try {
						AppStatus.setText("Installing...");
						Process p1 = Runtime.getRuntime().exec("adb remount");
						p1.waitFor();
						String[] pushcommand = new String[4];
						pushcommand[0] = "adb";
						pushcommand[1] = "push";
						pushcommand[2] = file.getAbsolutePath();
						pushcommand[3] = "/system/priv-app/";
						AppStatus.setText("Installing App...");
						Process p2 = Runtime.getRuntime().exec(pushcommand, null);
						p2.waitFor();
						AppStatus.setText("Rebooting your android device");
						Process p3 = Runtime.getRuntime().exec("adb reboot");
						p3.waitFor();
						AppStatus.setText("");
						GeneralDone.setIcon(new ImageIcon(Interface.class.getResource("/graphics/Done.png")));
						btnInstallAsPrivApp.setSelected(false);
					} catch (Exception e1) {
						System.err.println(e1);
					}
				}
			}
		});

		JButton btnUninstallApps = new JButton("Uninstall Apps");
		btnUninstallApps.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GeneralDone.setIcon(new ImageIcon(Interface.class.getResource("/graphics/WhiteBG.jpg")));
				Uninstaller obj = new Uninstaller();
				obj.setVisible(true);
			}
		});

		final JButton btnInstallAsSystemApp = new JButton("Install Apps to System *");
		btnInstallAsSystemApp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GeneralDone.setIcon(new ImageIcon(Interface.class.getResource("/graphics/WhiteBG.jpg")));
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("APK Files", "apk");
				chooser.setFileFilter(filter);
				int returnVal = chooser.showOpenDialog(getParent());
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = chooser.getSelectedFile();
					try {
						AppStatus.setText("Installing...");
						Process p1 = Runtime.getRuntime().exec("adb remount");
						p1.waitFor();
						String[] pushcommand = new String[4];
						pushcommand[0] = "adb";
						pushcommand[1] = "push";
						pushcommand[2] = file.getAbsolutePath();
						pushcommand[3] = "/system/app/";
						Process p2 = Runtime.getRuntime().exec(pushcommand, null);
						p2.waitFor();
						AppStatus.setText("Rebooting your android device");
						Process p3 = Runtime.getRuntime().exec("adb reboot");
						p3.waitFor();
						AppStatus.setText("");
						GeneralDone.setIcon(new ImageIcon(Interface.class.getResource("/graphics/Done.png")));
						btnInstallAsSystemApp.setSelected(false);
					} catch (Exception e1) {
						System.err.println(e1);
					}
				}
			}
		});

		JLabel lblNoteInstallationTo = new JLabel(
				"Note: Installation to priv-app is only for android 4.4.x and higher");
		lblNoteInstallationTo.setBounds(12, 338, 490, 15);
		panel_7.add(lblNoteInstallationTo);
		btnInstallAsSystemApp.setBounds(548, 27, 220, 75);
		panel_7.add(btnInstallAsSystemApp);

		GeneralDone = new JLabel("");
		GeneralDone.setIcon(new ImageIcon(Interface.class.getResource("/graphics/WhiteBG.jpg")));
		GeneralDone.setBounds(766, 27, 300, 200);
		panel_7.add(GeneralDone);
		btnUninstallApps.setBounds(282, 157, 220, 75);
		panel_7.add(btnUninstallApps);

		JButton btnFileManager = new JButton("File Manager");
		btnFileManager.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GeneralDone.setIcon(new ImageIcon(Interface.class.getResource("/graphics/WhiteBG.jpg")));
				try {
					Process p1 = Runtime.getRuntime().exec("java -jar .filemanager.jar");
					p1.waitFor();
				} catch (Exception e1) {
					System.err.println(e1);
				}
			}
		});
		btnFileManager.setBounds(25, 157, 220, 75);
		panel_7.add(btnFileManager);

		JLabel lblNeedsRoot = new JLabel(
				"* Needs root access, also may not work with some devices regardless of root access");
		lblNeedsRoot.setBounds(12, 365, 689, 15);
		panel_7.add(lblNeedsRoot);
		btnInstallAsPrivApp.setBounds(282, 27, 220, 75);
		panel_7.add(btnInstallAsPrivApp);
		btnInstallUserApp.setBounds(25, 27, 220, 75);
		panel_7.add(btnInstallUserApp);

		JLabel label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon(Interface.class.getResource("/graphics/WhiteBG.jpg")));
		label_1.setBounds(0, 0, 1072, 420);
		panel_7.add(label_1);

		JPanel panel = new JPanel();
		tabbedPane.addTab("Flasher", null, panel, null);
		panel.setLayout(null);

		final JButton btnFlashSystem = new JButton("System");
		btnFlashSystem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FlasherDone.setIcon(new ImageIcon(Interface.class.getResource("/graphics/WhiteBG.jpg")));
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Img Files", "img");
				chooser.setFileFilter(filter);
				int returnVal = chooser.showOpenDialog(getParent());
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = chooser.getSelectedFile();
					String filename = chooser.getSelectedFile().getName();
					try {
						AppStatus.setText("Flashing...");
						Process p1 = Runtime.getRuntime().exec("fastboot erase system");
						p1.waitFor();
						String[] commands = new String[4];
						commands[0] = "fastboot";
						commands[1] = "flash";
						commands[2] = "system";
						commands[3] = file.getAbsolutePath();
						Process p2 = Runtime.getRuntime().exec(commands, null);
						p2.waitFor();
						String[] notification = new String[2];
						AppStatus.setText(filename + "has been successfully flashed on your android device");
						FlasherDone.setIcon(new ImageIcon(Interface.class.getResource("/graphics/Done.png")));
						btnFlashSystem.setSelected(false);
					} catch (Exception e1) {
						System.err.println(e1);
					}
				}
			}
		});

		final JButton btnFlashData = new JButton("Data");
		btnFlashData.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				FlasherDone.setIcon(new ImageIcon(Interface.class.getResource("/graphics/WhiteBG.jpg")));
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Img Files", "img");
				chooser.setFileFilter(filter);
				int returnVal = chooser.showOpenDialog(getParent());
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = chooser.getSelectedFile();
					String filename = chooser.getSelectedFile().getName();
					try {
						AppStatus.setText("Flashing...");
						Process p1 = Runtime.getRuntime().exec("fastboot erase data");
						p1.waitFor();
						String[] commands = new String[4];
						commands[0] = "fastboot";
						commands[1] = "flash";
						commands[2] = "data";
						commands[3] = file.getAbsolutePath();
						Process p2 = Runtime.getRuntime().exec(commands, null);
						p2.waitFor();
						AppStatus.setText(filename + "has been successfully flashed on your android device");
						FlasherDone.setIcon(new ImageIcon(Interface.class.getResource("/graphics/Done.png")));
						btnFlashData.setSelected(false);
					} catch (Exception e1) {
						System.err.println(e1);
					}
				}
			}
		});

		final JButton btnFlashViaRecovery = new JButton("Flash via Recovery");
		btnFlashViaRecovery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FlasherDone.setIcon(new ImageIcon(Interface.class.getResource("/graphics/WhiteBG.jpg")));
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("zip Files", "zip");
				chooser.setFileFilter(filter);
				int returnVal = chooser.showOpenDialog(getParent());
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = chooser.getSelectedFile();
					String filename = chooser.getSelectedFile().getName();
					try {
						JOptionPane.showMessageDialog(null,
								"Select Update via ADB from recovery menu using physical keys on your device");
						String[] commands = new String[3];
						commands[0] = "adb";
						commands[1] = "sideload";
						commands[2] = file.getAbsolutePath();
						AppStatus.setText("Flashing...");
						Process p1 = Runtime.getRuntime().exec(commands, null);
						p1.waitFor();
						AppStatus.setText("Sideloaded...");
						FlasherDone.setIcon(new ImageIcon(Interface.class.getResource("/graphics/Done.png")));
						btnFlashViaRecovery.setSelected(false);
					} catch (Exception e1) {
						System.err.println(e1);
					}
				}
			}
		});

		JLabel lblAndroidIsNot = new JLabel("android is not booted ex. fastboot, bootloader, booting etc.");
		lblAndroidIsNot.setBounds(568, 334, 475, 19);
		panel.add(lblAndroidIsNot);

		JLabel lblDontWorry = new JLabel("Note: Don't worry if the app says to connect your device while");
		lblDontWorry.setBounds(525, 317, 518, 19);
		panel.add(lblDontWorry);

		FlasherDone = new JLabel("");
		FlasherDone.setIcon(new ImageIcon(Interface.class.getResource("/graphics/WhiteBG.jpg")));
		FlasherDone.setBounds(760, 29, 300, 200);
		panel.add(FlasherDone);
		btnFlashViaRecovery.setBounds(275, 154, 200, 75);
		panel.add(btnFlashViaRecovery);
		btnFlashData.setBounds(525, 29, 200, 75);
		panel.add(btnFlashData);
		btnFlashSystem.setBounds(275, 261, 200, 75);
		panel.add(btnFlashSystem);

		JLabel lblDeviceMust = new JLabel("* Device must be in fastboot mode");
		lblDeviceMust.setBounds(574, 356, 469, 19);
		panel.add(lblDeviceMust);

		final JButton btnFlashCache = new JButton("Cache");
		btnFlashCache.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FlasherDone.setIcon(new ImageIcon(Interface.class.getResource("/graphics/WhiteBG.jpg")));
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Img Files", "img");
				chooser.setFileFilter(filter);
				int returnVal = chooser.showOpenDialog(getParent());
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = chooser.getSelectedFile();
					String filename = chooser.getSelectedFile().getName();
					try {
						AppStatus.setText("Flashing...");
						Process p1 = Runtime.getRuntime().exec("fastboot erase cache");
						p1.waitFor();
						String[] commands = new String[4];
						commands[0] = "fastboot";
						commands[1] = "flash";
						commands[2] = "cache";
						commands[3] = file.getAbsolutePath();
						Process p2 = Runtime.getRuntime().exec(commands, null);
						p2.waitFor();
						AppStatus.setText(filename + "has been successfully flashed on your android device");
						FlasherDone.setIcon(new ImageIcon(Interface.class.getResource("/graphics/Done.png")));
						btnFlashCache.setSelected(false);
					} catch (Exception e1) {
						System.err.println(e1);
					}
				}
			}
		});

		btnFlashCache.setBounds(275, 29, 200, 75);
		panel.add(btnFlashCache);

		final JButton btnBootImage = new JButton("Boot");
		btnBootImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FlasherDone.setIcon(new ImageIcon(Interface.class.getResource("/graphics/WhiteBG.jpg")));
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Img Files", "img");
				chooser.setFileFilter(filter);
				int returnVal = chooser.showOpenDialog(getParent());
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = chooser.getSelectedFile();
					String filename = chooser.getSelectedFile().getName();
					try {
						AppStatus.setText("Flashing...");
						Process p1 = Runtime.getRuntime().exec("fastboot erase boot");
						p1.waitFor();
						String[] commands = new String[4];
						commands[0] = "fastboot";
						commands[1] = "flash";
						commands[2] = "boot";
						commands[3] = file.getAbsolutePath();
						Process p2 = Runtime.getRuntime().exec(commands, null);
						p2.waitFor();
						AppStatus.setText(filename + "has been successfully flashed on your android device");
						FlasherDone.setIcon(new ImageIcon(Interface.class.getResource("/graphics/Done.png")));
						btnBootImage.setSelected(false);
					} catch (Exception e1) {
						System.err.println(e1);
					}
				}
			}
		});

		btnBootImage.setBounds(30, 29, 200, 75);
		panel.add(btnBootImage);

		final JButton btnFlashDatazip = new JButton("Data.zip");
		btnFlashDatazip.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FlasherDone.setIcon(new ImageIcon(Interface.class.getResource("/graphics/WhiteBG.jpg")));
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("zip Files", "zip");
				chooser.setFileFilter(filter);
				int returnVal = chooser.showOpenDialog(getParent());
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = chooser.getSelectedFile();
					String filename = chooser.getSelectedFile().getName();
					try {
						AppStatus.setText("Flashing...");
						String[] commands = new String[3];
						commands[0] = "fastboot";
						commands[1] = "flash";
						commands[2] = file.getAbsolutePath();
						Process p1 = Runtime.getRuntime().exec(commands, null);
						p1.waitFor();
						AppStatus.setText(filename + "has been successfully flashed on your android device");
						FlasherDone.setIcon(new ImageIcon(Interface.class.getResource("/graphics/Done.png")));
						btnFlashDatazip.setSelected(false);
					} catch (Exception e1) {
						System.err.println(e1);
					}
				}
			}
		});

		btnFlashDatazip.setBounds(30, 146, 200, 75);
		panel.add(btnFlashDatazip);

		final JButton btnFlashRecovery = new JButton("Recovery");
		btnFlashRecovery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FlasherDone.setIcon(new ImageIcon(Interface.class.getResource("/graphics/WhiteBG.jpg")));
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Img Files", "img");
				chooser.setFileFilter(filter);
				int returnVal = chooser.showOpenDialog(getParent());
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = chooser.getSelectedFile();
					String filename = chooser.getSelectedFile().getName();
					try {
						AppStatus.setText("Flashing...");
						Process p1 = Runtime.getRuntime().exec("fastboot erase recovery");
						p1.waitFor();
						String[] commands = new String[4];
						commands[0] = "fastboot";
						commands[1] = "flash";
						commands[2] = "recovery";
						commands[3] = file.getAbsolutePath();
						Process p2 = Runtime.getRuntime().exec(commands, null);
						p2.waitFor();
						AppStatus.setText(filename + "has been successfully flashed on your android device");
						FlasherDone.setIcon(new ImageIcon(Interface.class.getResource("/graphics/Done.png")));
						btnFlashRecovery.setSelected(false);
					} catch (Exception e1) {
						System.err.println(e1);
					}
				}
			}
		});

		btnFlashRecovery.setBounds(525, 154, 200, 75);
		panel.add(btnFlashRecovery);

		JLabel lblYouMust = new JLabel("* You must have a bootloader that supports fastboot commands");
		lblYouMust.setBounds(22, 356, 533, 19);
		panel.add(lblYouMust);

		final JButton btnFlashSplash = new JButton("Splash");
		btnFlashSplash.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FlasherDone.setIcon(new ImageIcon(Interface.class.getResource("/graphics/Done.png")));
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Img Files", "img");
				chooser.setFileFilter(filter);
				int returnVal = chooser.showOpenDialog(getParent());
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = chooser.getSelectedFile();
					String filename = chooser.getSelectedFile().getName();
					try {
						AppStatus.setText("Flashing...");
						Process p1 = Runtime.getRuntime().exec("fastboot erase splash");
						p1.waitFor();
						String[] commands = new String[4];
						commands[0] = "fastboot";
						commands[1] = "flash";
						commands[2] = "splash";
						commands[3] = file.getAbsolutePath();
						Process p2 = Runtime.getRuntime().exec(commands, null);
						p2.waitFor();
						AppStatus.setText(filename + "has been successfully flashed on your android device");
						FlasherDone.setIcon(new ImageIcon(Interface.class.getResource("/graphics/Done.png")));
						btnFlashSplash.setSelected(false);
					} catch (Exception e1) {
						System.err.println(e1);
					}
				}
			}
		});

		btnFlashSplash.setBounds(30, 261, 200, 75);
		panel.add(btnFlashSplash);

		JLabel label_4 = new JLabel("");
		label_4.setIcon(new ImageIcon(Interface.class.getResource("/graphics/WhiteBG.jpg")));
		label_4.setBounds(0, 0, 1083, 420);
		panel.add(label_4);

		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Wiper", null, panel_1, null);
		panel_1.setLayout(null);

		JLabel label_11 = new JLabel("android is not booted ex. fastboot, bootloader, booting etc.");
		label_11.setBounds(313, 323, 475, 19);
		panel_1.add(label_11);

		JLabel label_3 = new JLabel("Note: Don't worry if the app says to connect your device while");
		label_3.setBounds(270, 306, 518, 19);
		panel_1.add(label_3);

		WiperDone = new JLabel("");
		WiperDone.setIcon(new ImageIcon(Interface.class.getResource("/graphics/WhiteBG.jpg")));
		WiperDone.setBounds(758, 26, 300, 200);
		panel_1.add(WiperDone);

		JLabel lblYouDeviceBootloader = new JLabel("You device bootloader must support fastboot commands");
		lblYouDeviceBootloader.setBounds(523, 357, 470, 19);
		panel_1.add(lblYouDeviceBootloader);

		JLabel label_14 = new JLabel("Device must be in fastboot mode");
		label_14.setBounds(270, 357, 252, 19);
		panel_1.add(label_14);

		JLabel label_13 = new JLabel("** Device must be rooted");
		label_13.setBounds(6, 357, 252, 19);
		panel_1.add(label_13);

		JButton btnWipeRecovery = new JButton("Recovery");
		btnWipeRecovery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WiperDone.setIcon(new ImageIcon(Interface.class.getResource("/graphics/WhiteBG.jpg")));
				try {
					AppStatus.setText("Wiping...");
					Process p1 = Runtime.getRuntime().exec("fastboot erase cache");
					p1.waitFor();
					AppStatus.setText("Recovery has been wiped");
					WiperDone.setIcon(new ImageIcon(Interface.class.getResource("/graphics/Done.png")));
				} catch (Exception e1) {
					System.err.println(e1);
				}
			}
		});

		btnWipeRecovery.setBounds(270, 137, 200, 75);
		panel_1.add(btnWipeRecovery);

		JButton btnWipeBoot = new JButton("Boot");
		btnWipeBoot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WiperDone.setIcon(new ImageIcon(Interface.class.getResource("/graphics/WhiteBG.jpg")));
				try {
					AppStatus.setText("Wiping...");
					Process p1 = Runtime.getRuntime().exec("fastboot erase boot");
					p1.waitFor();
					AppStatus.setText("Boot has been wiped");
					WiperDone.setIcon(new ImageIcon(Interface.class.getResource("/graphics/Done.png")));
				} catch (Exception e1) {
					System.err.println(e1);
				}
			}
		});

		btnWipeBoot.setBounds(25, 26, 200, 75);
		panel_1.add(btnWipeBoot);

		JButton btnWipeSystem = new JButton("System");
		btnWipeSystem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				WiperDone.setIcon(new ImageIcon(Interface.class.getResource("/graphics/WhiteBG.jpg")));
				try {
					AppStatus.setText("Wiping...");
					Process p1 = Runtime.getRuntime().exec("fastboot erase system");
					p1.waitFor();
					AppStatus.setText("System has been wiped");
					WiperDone.setIcon(new ImageIcon(Interface.class.getResource("/graphics/Done.png")));
				} catch (Exception e1) {
					System.err.println(e1);
				}
			}
		});

		btnWipeSystem.setBounds(25, 241, 200, 75);
		panel_1.add(btnWipeSystem);

		JButton btnWipeSplash = new JButton("Splash");
		btnWipeSplash.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				WiperDone.setIcon(new ImageIcon(Interface.class.getResource("/graphics/WhiteBG.jpg")));
				try {
					AppStatus.setText("Wiping...");
					Process p1 = Runtime.getRuntime().exec("fastboot erase splash");
					p1.waitFor();
					AppStatus.setText("Splash has been wiped");
					WiperDone.setIcon(new ImageIcon(Interface.class.getResource("/graphics/Done.png")));
				} catch (Exception e1) {
					System.err.println(e1);
				}
			}
		});

		btnWipeSplash.setBounds(523, 137, 200, 75);
		panel_1.add(btnWipeSplash);

		JButton btnWipeData = new JButton("Data");
		btnWipeData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				WiperDone.setIcon(new ImageIcon(Interface.class.getResource("/graphics/WhiteBG.jpg")));
				try {
					AppStatus.setText("Wiping...");
					Process p1 = Runtime.getRuntime().exec("fastboot erase data");
					p1.waitFor();
					AppStatus.setText("Data has been wiped");
					WiperDone.setIcon(new ImageIcon(Interface.class.getResource("/graphics/Done.png")));
				} catch (Exception e1) {
					System.err.println(e1);
				}
			}
		});

		btnWipeData.setBounds(25, 137, 200, 75);
		panel_1.add(btnWipeData);

		JButton btnFlashDalvikCache = new JButton("Dalvik Cache **");
		btnFlashDalvikCache.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WiperDone.setIcon(new ImageIcon(Interface.class.getResource("/graphics/WhiteBG.jpg")));
				try {
					AppStatus.setText("Wiping...");
					Process p1 = Runtime.getRuntime().exec("adb shell su -c rm * /data/dalvik-cache");
					p1.waitFor();
					Process p2 = Runtime.getRuntime().exec("adb shell su -c rm * /cache/dalvik-cache");
					p2.waitFor();
					AppStatus.setText("Dalvik Cache has been wiped! Now rebooting device...");
					Process p3 = Runtime.getRuntime().exec("adb reboot");
					p3.waitFor();
					AppStatus.setText("Done");
					WiperDone.setIcon(new ImageIcon(Interface.class.getResource("/graphics/Done.png")));
				} catch (Exception e1) {
					System.err.println(e1);
				}
			}
		});

		btnFlashDalvikCache.setBounds(518, 26, 200, 75);
		panel_1.add(btnFlashDalvikCache);

		JButton btnWipeCache = new JButton("Cache");
		btnWipeCache.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				WiperDone.setIcon(new ImageIcon(Interface.class.getResource("/graphics/WhiteBG.jpg")));
				try {
					AppStatus.setText("Wiping...");
					Process p1 = Runtime.getRuntime().exec("fastboot erase cache");
					p1.waitFor();
					AppStatus.setText("Cache has been wiped! Now rebooting device...");
					Process p2 = Runtime.getRuntime().exec("adb reboot");
					p2.waitFor();
					AppStatus.setText("Done");
					WiperDone.setIcon(new ImageIcon(Interface.class.getResource("/graphics/Done.png")));
				} catch (Exception e1) {
					System.err.println(e1);
				}
			}
		});

		btnWipeCache.setBounds(270, 26, 200, 75);
		panel_1.add(btnWipeCache);

		JLabel label_5 = new JLabel("");
		label_5.setIcon(new ImageIcon(Interface.class.getResource("/graphics/WhiteBG.jpg")));
		label_5.setBounds(0, 0, 1072, 420);
		panel_1.add(label_5);

		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("Rebooter", null, panel_2, null);
		panel_2.setLayout(null);

		JLabel label_12 = new JLabel("Note: Don't worry if the app says to connect your device while");
		label_12.setBounds(467, 317, 518, 19);
		panel_2.add(label_12);

		JLabel label_16 = new JLabel("android is not booted ex. fastboot, bootloader, booting etc.");
		label_16.setBounds(510, 334, 475, 19);
		panel_2.add(label_16);

		JLabel lblRebootFrom = new JLabel("Reboot from :");
		lblRebootFrom.setBounds(28, 182, 200, 15);
		panel_2.add(lblRebootFrom);

		JLabel lblRebootTo = new JLabel("Reboot to :");
		lblRebootTo.setBounds(28, 12, 200, 15);
		panel_2.add(lblRebootTo);

		JLabel lblNotFor = new JLabel("# Not for Samsung devices");
		lblNotFor.setBounds(491, 359, 238, 19);
		panel_2.add(lblNotFor);

		JLabel lblDeviceMust_1 = new JLabel("Device must be in fastboot mode (Except for Reboot System)");
		lblDeviceMust_1.setBounds(10, 332, 479, 19);
		panel_2.add(lblDeviceMust_1);

		JLabel lblYouMust_1 = new JLabel("* You must have a bootloader that supports fastboot commands");
		lblYouMust_1.setBounds(10, 359, 470, 19);
		panel_2.add(lblYouMust_1);

		JButton btnRebootFromFastboot = new JButton("Fastboot *");
		btnRebootFromFastboot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					AppStatus.setText("Rebooting...");
					Process p1 = Runtime.getRuntime().exec("fastboot reboot");
					p1.waitFor();
					AppStatus.setText("Done");
				} catch (Exception e1) {
					System.err.println(e1);
				}
			}
		});

		btnRebootFromFastboot.setBounds(28, 230, 200, 75);
		panel_2.add(btnRebootFromFastboot);

		JButton btnRebootToBootloaderFromFastboot = new JButton("Fastboot to Bootloader *");
		btnRebootToBootloaderFromFastboot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					AppStatus.setText("Rebooting...");
					Process p1 = Runtime.getRuntime().exec("fasboot reboot-bootloader");
					p1.waitFor();
					AppStatus.setText("Done");
				} catch (Exception e1) {
					System.err.println(e1);
				}
			}
		});

		btnRebootToBootloaderFromFastboot.setBounds(279, 230, 240, 75);
		panel_2.add(btnRebootToBootloaderFromFastboot);

		JButton btnRebootToRecovery = new JButton("Recovery");
		btnRebootToRecovery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					AppStatus.setText("Rebooting...");
					Process p1 = Runtime.getRuntime().exec("adb reboot recovery");
					p1.waitFor();
					AppStatus.setText("Done");
				} catch (Exception e1) {
					System.err.println(e1);
				}
			}
		});

		btnRebootToRecovery.setBounds(785, 55, 200, 75);
		panel_2.add(btnRebootToRecovery);

		JButton btnRebootToFastboot = new JButton("Fastboot");
		btnRebootToFastboot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					AppStatus.setText("Rebooting...");
					Process p1 = Runtime.getRuntime().exec("adb reboot fastboot");
					p1.waitFor();
					AppStatus.setText("Done");
				} catch (Exception e1) {
					System.err.println(e1);
				}
			}
		});

		btnRebootToFastboot.setBounds(529, 55, 200, 75);
		panel_2.add(btnRebootToFastboot);

		JButton btnRebootToBootloader = new JButton("Bootloader #");
		btnRebootToBootloader.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					AppStatus.setText("Rebooting...");
					Process p1 = Runtime.getRuntime().exec("adb reboot bootloader");
					p1.waitFor();
					AppStatus.setText("Done");
				} catch (Exception e1) {
					System.err.println(e1);
				}
			}
		});

		btnRebootToBootloader.setBounds(279, 55, 200, 75);
		panel_2.add(btnRebootToBootloader);

		JButton btnRebootSystem = new JButton("System");
		btnRebootSystem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					AppStatus.setText("Rebooting...");
					Process p1 = Runtime.getRuntime().exec("adb reboot");
					p1.waitFor();
					AppStatus.setText("Done");
				} catch (Exception e1) {
					System.err.println(e1);
				}
			}
		});

		btnRebootSystem.setBounds(28, 55, 200, 75);
		panel_2.add(btnRebootSystem);

		JLabel label_6 = new JLabel("");
		label_6.setIcon(new ImageIcon(Interface.class.getResource("/graphics/WhiteBG.jpg")));
		label_6.setBounds(0, 0, 1072, 420);
		panel_2.add(label_6);

		JPanel panel_3 = new JPanel();
		tabbedPane.addTab("Bootloader", null, panel_3, null);
		panel_3.setLayout(null);

		JLabel label_17 = new JLabel("Note: Don't worry if the app says to connect your device while");
		label_17.setBounds(23, 320, 518, 19);
		panel_3.add(label_17);

		JLabel label_18 = new JLabel("android is not booted ex. fastboot, bootloader, booting etc.");
		label_18.setBounds(66, 337, 475, 19);
		panel_3.add(label_18);

		JLabel lblOnlyForNexus = new JLabel(
				"Works only with specific devices ex. Nexus, Android One, few MTK devices etc.");
		lblOnlyForNexus.setBounds(25, 358, 660, 19);
		panel_3.add(lblOnlyForNexus);

		JButton btnUnlockBootloader = new JButton("Unlock Bootloader");
		btnUnlockBootloader.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					AppStatus.setText(
							"Unlocking bootloader will factory reset your device and may void your device warranty!");
					JOptionPane.showMessageDialog(null,
							"You will need to re-enable USB debugging later as your device will get factory reset");
					Process p1 = Runtime.getRuntime().exec("adb reboot bootloader");
					p1.waitFor();
					Process p2 = Runtime.getRuntime().exec("fastboot oem unlock");
					p2.waitFor();
				} catch (Exception e1) {
					System.err.println(e1);
				}
			}
		});

		btnUnlockBootloader.setBounds(425, 194, 230, 75);
		panel_3.add(btnUnlockBootloader);

		JButton btnLockBootloader = new JButton("Lock Bootloader");
		btnLockBootloader.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Process p1 = Runtime.getRuntime().exec("adb reboot bootloader");
					p1.waitFor();
					Process p2 = Runtime.getRuntime().exec("fastboot oem lock");
					p2.waitFor();
				} catch (Exception e1) {
					System.err.println(e1);
				}
			}
		});

		btnLockBootloader.setBounds(425, 57, 230, 75);
		panel_3.add(btnLockBootloader);

		JLabel label_7 = new JLabel("");
		label_7.setIcon(new ImageIcon(Interface.class.getResource("/graphics/WhiteBG.jpg")));
		label_7.setBounds(0, 0, 1072, 420);
		panel_3.add(label_7);

		JButton button = new JButton("Reboot to Recovery");
		button.setBounds(150, 153, 200, 75);
		panel_3.add(button);

		JPanel panel_4 = new JPanel();
		tabbedPane.addTab("Logger", null, panel_4, null);
		panel_4.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 72, 1072, 311);
		panel_4.add(scrollPane);

		final JTextArea LogViewer = new JTextArea();
		LogViewer.setEditable(false);
		scrollPane.setViewportView(LogViewer);

		JButton btnSaveAsTextFile = new JButton("Save as a text file");
		btnSaveAsTextFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame parentFrame = new JFrame();
				JFileChooser fileChooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
				fileChooser.setFileFilter(filter);
				fileChooser.setDialogTitle("Save as a text file");
				int userSelection = fileChooser.showSaveDialog(parentFrame);
				if (userSelection == JFileChooser.APPROVE_OPTION) {
					File fileToSave = fileChooser.getSelectedFile();
					FileWriter write = null;
					try {
						write = new FileWriter(fileToSave.getAbsolutePath() + ".txt");
						LogViewer.write(write);
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						if (write != null)
							try {
								write.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
					}
				}
			}
		});

		btnSaveAsTextFile.setBounds(420, 13, 200, 47);
		panel_4.add(btnSaveAsTextFile);

		JButton btnClearLogact = new JButton("Clear");
		btnClearLogact.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				LogViewer.setText("");
				AppStatus.setText("Logcat cleared");
			}
		});
		btnClearLogact.setBounds(845, 12, 200, 48);
		panel_4.add(btnClearLogact);

		JButton btnViewLogcat = new JButton("View Logcat");
		btnViewLogcat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					AppStatus.setText("Getting logcat...");
					Process p1 = Runtime.getRuntime().exec("adb logcat -d > /sdcard/.logcat.txt");
					p1.waitFor();
					Process p2 = Runtime.getRuntime().exec("adb pull /sdcard/.logcat.txt");
					p2.waitFor();
					Process p3 = Runtime.getRuntime().exec("adb logcat -c");
					p3.waitFor();
					Process p4 = Runtime.getRuntime().exec("adb shell rm /sdcard/.logcat.txt");
					p4.waitFor();
					Reader reader = null;
					try {
						reader = new FileReader(new File(".logcat.txt"));
						LogViewer.read(reader, "");
						AppStatus.setText("");
					} catch (IOException e) {
						e.printStackTrace();
					}
					File file = new File(".logcat.txt");
					if (file.exists() && !file.isDirectory()) {
						file.delete();
					}
				} catch (Exception e) {
					System.err.println(e);
				}
			}
		});

		btnViewLogcat.setBounds(37, 13, 200, 47);
		panel_4.add(btnViewLogcat);

		JLabel label_8 = new JLabel("");
		label_8.setIcon(new ImageIcon(Interface.class.getResource("/graphics/WhiteBG.jpg")));
		label_8.setBounds(0, 0, 1072, 420);
		panel_4.add(label_8);

		JPanel panel_5 = new JPanel();
		tabbedPane.addTab("Backup and Restore", null, panel_5, null);
		panel_5.setLayout(null);

		JLabel lblNoteThisIs = new JLabel("Note: This is android native backup and restore utility");
		lblNoteThisIs.setBounds(270, 346, 420, 15);
		panel_5.add(lblNoteThisIs);

		BackupAndRestoreDone = new JLabel("");
		BackupAndRestoreDone.setIcon(new ImageIcon(Interface.class.getResource("/graphics/WhiteBG.jpg")));
		BackupAndRestoreDone.setBounds(758, 70, 300, 200);
		panel_5.add(BackupAndRestoreDone);

		JLabel lblRestoreOperations = new JLabel("Restore Operations");
		lblRestoreOperations.setBounds(510, 12, 142, 36);
		panel_5.add(lblRestoreOperations);

		final JButton btnRestoreFromCustomLocationBackup = new JButton("From Custom Location");
		btnRestoreFromCustomLocationBackup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BackupAndRestoreDone.setIcon(new ImageIcon(Interface.class.getResource("/graphics/WhiteBG.jpg")));
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Android Backup Files", "ab");
				chooser.setFileFilter(filter);
				int returnVal = chooser.showOpenDialog(getParent());
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = chooser.getSelectedFile();
					String filename = chooser.getSelectedFile().getName();
					try {
						AppStatus.setText("Restoring may take upto several minutes, please be patient...");
						JOptionPane.showMessageDialog(null,
								"Unlock your device and confirm the restore operation when asked");
						String[] commands = new String[3];
						commands[0] = "adb";
						commands[1] = "restore";
						commands[2] = file.getAbsolutePath();
						AppStatus.setText("Restoring...");
						Process p1 = Runtime.getRuntime().exec(commands, null);
						p1.waitFor();
						AppStatus.setText("Restore completed successfully!");
						BackupAndRestoreDone.setIcon(new ImageIcon(Interface.class.getResource("/graphics/Done.png")));
						btnRestoreFromCustomLocationBackup.setSelected(false);
					} catch (Exception e1) {
						System.err.println(e1);
					}
				}
			}
		});

		btnRestoreFromCustomLocationBackup.setBounds(510, 184, 200, 75);
		panel_5.add(btnRestoreFromCustomLocationBackup);

		JLabel lblBackup = new JLabel("Backup Operations");
		lblBackup.setBounds(25, 12, 142, 36);
		panel_5.add(lblBackup);

		final JButton btnBackupInternelStorage = new JButton("Internel Storage");
		btnBackupInternelStorage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BackupAndRestoreDone.setIcon(new ImageIcon(Interface.class.getResource("/graphics/WhiteBG.jpg")));
				try {
					String[] notification = new String[3];
					AppStatus.setText("Backup can take upto several minutes, please be patient...");
					JOptionPane.showMessageDialog(null,
							"Unlock your device and confirm the backup operation when asked");
					String[] commands = new String[3];
					commands[0] = "adb";
					commands[1] = "backup";
					commands[2] = "-shared";
					AppStatus.setText("Performing backup...");
					Process p1 = Runtime.getRuntime().exec(commands, null);
					p1.waitFor();
					String[] notification2 = new String[2];
					AppStatus.setText("Backup completed successfully!");
					BackupAndRestoreDone.setIcon(new ImageIcon(Interface.class.getResource("/graphics/Done.png")));
					btnBackupInternelStorage.setSelected(false);
				} catch (Exception e1) {
					System.err.println(e1);
				}
			}
		});

		btnBackupInternelStorage.setBounds(25, 301, 200, 75);
		panel_5.add(btnBackupInternelStorage);

		final JButton btnBackupSystem = new JButton("System");
		btnBackupSystem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BackupAndRestoreDone.setIcon(new ImageIcon(Interface.class.getResource("/graphics/WhiteBG.jpg")));
				try {
					AppStatus.setText("Backup can take upto several minutes, please be patient...");
					JOptionPane.showMessageDialog(null,
							"Unlock your device and confirm the backup operation when asked");
					String[] commands = new String[3];
					commands[0] = "adb";
					commands[1] = "backup";
					commands[2] = "-system";
					AppStatus.setText("Performing backup...");
					Process p1 = Runtime.getRuntime().exec(commands, null);
					p1.waitFor();
					AppStatus.setText("Backup completed successfully!");
					BackupAndRestoreDone.setIcon(new ImageIcon(Interface.class.getResource("/graphics/Done.png")));
					btnBackupSystem.setSelected(false);
				} catch (Exception e1) {
					System.err.println(e1);
				}
			}
		});

		btnBackupSystem.setBounds(25, 186, 200, 75);
		panel_5.add(btnBackupSystem);

		final JButton btnBackupSingleApp = new JButton("Single App");
		btnBackupSingleApp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BackupAndRestoreDone.setIcon(new ImageIcon(Interface.class.getResource("/graphics/WhiteBG.jpg")));
				try {
					String message = JOptionPane.showInputDialog(null, "Please specify a package name to backup");
					String[] notification = new String[2];
					AppStatus.setText("Backup can take upto several minutes, please be patient...");
					JOptionPane.showMessageDialog(null,
							"Unlock your device and confirm the backup operation when asked");
					String[] commands = new String[3];
					commands[0] = "adb";
					commands[1] = "backup";
					commands[2] = message;
					AppStatus.setText("Performing backup...");
					Process p1 = Runtime.getRuntime().exec(commands, null);
					p1.waitFor();
					String[] notification2 = new String[2];
					AppStatus.setText("Backup completed successfully!");
					BackupAndRestoreDone.setIcon(new ImageIcon(Interface.class.getResource("/graphics/Done.png")));
					btnBackupSingleApp.setSelected(false);
				} catch (Exception e1) {
					System.err.println(e1);
				}
			}
		});

		btnBackupSingleApp.setBounds(270, 185, 200, 75);
		panel_5.add(btnBackupSingleApp);

		final JButton btnBackupAppAndAppData = new JButton("App and App Data");
		btnBackupAppAndAppData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BackupAndRestoreDone.setIcon(new ImageIcon(Interface.class.getResource("/graphics/WhiteBG.jpg")));
				try {
					String[] notification = new String[3];
					AppStatus.setText("Backup can take upto several minutes, please be patient...");
					JOptionPane.showMessageDialog(null,
							"Unlock your device and confirm the backup operation when asked");
					String[] commands = new String[3];
					commands[0] = "adb";
					commands[1] = "backup";
					commands[2] = "-all";
					AppStatus.setText("Performing backup...");
					Process p1 = Runtime.getRuntime().exec(commands, null);
					p1.waitFor();
					String[] notification2 = new String[2];
					AppStatus.setText("Backup completed successfully!");
					BackupAndRestoreDone.setIcon(new ImageIcon(Interface.class.getResource("/graphics/Done.png")));
					btnBackupAppAndAppData.setSelected(false);
				} catch (Exception e1) {
					System.err.println(e1);
				}
			}
		});

		btnBackupAppAndAppData.setBounds(270, 70, 200, 75);
		panel_5.add(btnBackupAppAndAppData);

		final JButton btnBackupWholeDevice = new JButton("Whole Device");
		btnBackupWholeDevice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BackupAndRestoreDone.setIcon(new ImageIcon(Interface.class.getResource("/graphics/WhiteBG.jpg")));
				try {
					AppStatus.setText("Backup can take upto several minutes, please be patient...");
					JOptionPane.showMessageDialog(null,
							"Unlock your device and confirm the backup operation when asked");
					String[] commands = new String[6];
					commands[0] = "adb";
					commands[1] = "backup";
					commands[2] = "-all";
					commands[3] = "-apk";
					commands[4] = "-shared";
					commands[5] = "-system";
					AppStatus.setText("Performing backup...");
					Process p1 = Runtime.getRuntime().exec(commands, null);
					p1.waitFor();
					AppStatus.setText("Backup completed successfully");
					BackupAndRestoreDone.setIcon(new ImageIcon(Interface.class.getResource("/graphics/Done.png")));
					btnBackupWholeDevice.setSelected(false);
				} catch (Exception e1) {
					System.err.println(e1);
				}
			}
		});

		btnBackupWholeDevice.setBounds(25, 70, 200, 75);
		panel_5.add(btnBackupWholeDevice);

		final JButton btnRestorePreviousBackup = new JButton("Previous Backup");
		btnRestorePreviousBackup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BackupAndRestoreDone.setIcon(new ImageIcon(Interface.class.getResource("/graphics/WhiteBG.jpg")));
				try {
					AppStatus.setText("Restoring can take upto several minutes, please be patient...");
					JOptionPane.showMessageDialog(null,
							"Unlock your device and confirm the restore operation when asked");
					String[] commands = new String[3];
					commands[0] = "adb";
					commands[1] = "restore";
					commands[2] = "backup.ab";
					AppStatus.setText("Restoring...");
					Process p1 = Runtime.getRuntime().exec(commands, null);
					p1.waitFor();
					AppStatus.setText("Restore completed successfully!");
					BackupAndRestoreDone.setIcon(new ImageIcon(Interface.class.getResource("/graphics/Done.png")));
					btnRestorePreviousBackup.setSelected(false);
				} catch (Exception e1) {
					System.err.println(e1);
				}
			}
		});

		btnRestorePreviousBackup.setBounds(510, 70, 200, 75);
		panel_5.add(btnRestorePreviousBackup);

		JLabel label_9 = new JLabel("");
		label_9.setIcon(new ImageIcon(Interface.class.getResource("/graphics/WhiteBG.jpg")));
		label_9.setBounds(0, 0, 1072, 420);
		panel_5.add(label_9);

		JPanel panel_9 = new JPanel();
		tabbedPane.addTab("Bypass Security", null, panel_9, null);
		panel_9.setLayout(null);

		JLabel lblRootOperationsexperimental = new JLabel("Root Operations [EXPERIMENTAL] :");
		lblRootOperationsexperimental.setBounds(12, 12, 388, 15);
		panel_9.add(lblRootOperationsexperimental);

		JLabel lblMethodbetter = new JLabel("Method #1 (Recommended)");
		lblMethodbetter.setBounds(12, 42, 163, 15);
		panel_9.add(lblMethodbetter);

		JButton btnPattern = new JButton("Pattern #");
		btnPattern.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					AppStatus.setText("Trying to break into security...");
					Process p1 = Runtime.getRuntime().exec("adb shell su -c rm /data/system/gesture.key");
					p1.waitFor();
					AppStatus.setText(
							"Done, now try to unlock the device with a random pattern then change security manually from settings");
				} catch (Exception e1) {
				}
			}
		});

		btnPattern.setBounds(200, 75, 240, 75);
		panel_9.add(btnPattern);

		JButton btnPasswordPin = new JButton("Password/ PIN #");
		btnPasswordPin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					AppStatus.setText("Trying to break into security...");
					Process p1 = Runtime.getRuntime().exec("adb shell su -c rm /data/system/password.key");
					p1.waitFor();
					AppStatus.setText("Done, check your device...");
				} catch (Exception e1) {
					System.err.println(e1);
				}
			}
		});

		btnPasswordPin.setBounds(630, 75, 240, 75);
		panel_9.add(btnPasswordPin);

		JLabel lblMayNot = new JLabel("# Works on Android 4.4.x and lower");
		lblMayNot.setBounds(630, 250, 269, 15);
		panel_9.add(lblMayNot);

		JLabel lblNonRoot = new JLabel("Non - Root/ Root Operations [EXPERIMENTAL] :");
		lblNonRoot.setBounds(12, 191, 388, 15);
		panel_9.add(lblNonRoot);

		JButton btnJellyBeanPatternPasswordPin = new JButton("Pattern/ Password/ PIN *");
		btnJellyBeanPatternPasswordPin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					AppStatus.setText("Trying to break into security...");
					Process p1 = Runtime.getRuntime().exec(
							"adb shell am start -n com.android.settings/com.android.settings.ChooseLockGeneric --ez confirm_credentials false --ei lockscreen.password_type 0 --activity-clear-task");
					p1.waitFor();
					AppStatus.setText("Rebooting...");
					Process p2 = Runtime.getRuntime().exec("adb reboot");
					p2.waitFor();
					AppStatus.setText("Done, check your device...");
				} catch (Exception e1) {
					System.err.println(e1);
				}
			}
		});

		btnJellyBeanPatternPasswordPin.setBounds(200, 250, 240, 75);
		panel_9.add(btnJellyBeanPatternPasswordPin);

		JLabel lblWorksWell = new JLabel("* Works well on Jelly Bean Devices but");
		lblWorksWell.setBounds(630, 273, 285, 15);
		panel_9.add(lblWorksWell);

		JLabel lblNewLabel = new JLabel("may work for older android versions");
		lblNewLabel.setBounds(640, 293, 285, 15);
		panel_9.add(lblNewLabel);

		JLabel label_20 = new JLabel("");
		label_20.setIcon(new ImageIcon(Interface.class.getResource("/graphics/WhiteBG.jpg")));
		label_20.setBounds(0, 0, 1072, 420);
		panel_9.add(label_20);

		JPanel panel_6 = new JPanel();
		tabbedPane.addTab("Cryptography", null, panel_6, null);
		panel_6.setLayout(null);

		JButton btnSHA512 = new JButton("SHA-512");
		btnSHA512.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				int returnVal = chooser.showOpenDialog(getParent());
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = new File("");
					AppStatus.setText("Calculating...");
					CalculatedCrypto.setText(DigestUtils.sha512Hex(file.getAbsolutePath()));
					AppStatus.setText("");
				}
			}
		});

		JButton btnClearCalculatedCrypto = new JButton("Clear");
		btnClearCalculatedCrypto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CalculatedCrypto.setText("");
			}
		});
		btnClearCalculatedCrypto.setBounds(625, 168, 200, 75);
		panel_6.add(btnClearCalculatedCrypto);

		JLabel lblLabelCalculatedSum = new JLabel("Calculated Sum :");
		lblLabelCalculatedSum.setBounds(12, 317, 264, 17);
		panel_6.add(lblLabelCalculatedSum);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPane_1.setBounds(12, 346, 1046, 28);
		panel_6.add(scrollPane_1);

		CalculatedCrypto = new JTextArea();
		scrollPane_1.setViewportView(CalculatedCrypto);
		CalculatedCrypto.setEditable(false);
		btnSHA512.setBounds(625, 26, 200, 75);
		panel_6.add(btnSHA512);

		JButton btnSHA384 = new JButton("SHA-384");
		btnSHA384.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				int returnVal = chooser.showOpenDialog(getParent());
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = new File("");
					AppStatus.setText("Calculating...");
					CalculatedCrypto.setText(DigestUtils.sha384Hex(file.getAbsolutePath()));
					AppStatus.setText("");
				}
			}
		});

		btnSHA384.setBounds(325, 168, 200, 75);
		panel_6.add(btnSHA384);

		JButton btnSHA256 = new JButton("SHA-256");
		btnSHA256.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				int returnVal = chooser.showOpenDialog(getParent());
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = new File("");
					AppStatus.setText("Calculating...");
					CalculatedCrypto.setText(DigestUtils.sha256Hex(file.getAbsolutePath()));
					AppStatus.setText("");
				}
			}
		});

		btnSHA256.setBounds(325, 26, 200, 75);
		panel_6.add(btnSHA256);

		JButton btnSHA1 = new JButton("SHA-1");
		btnSHA1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				int returnVal = chooser.showOpenDialog(getParent());
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = new File("");
					AppStatus.setText("Calculating...");
					CalculatedCrypto.setText(DigestUtils.sha1Hex(file.getAbsolutePath()));
					AppStatus.setText("");
				}
			}
		});

		btnSHA1.setBounds(27, 168, 200, 75);
		panel_6.add(btnSHA1);

		JButton btnMD5 = new JButton("MD5");
		btnMD5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				int returnVal = chooser.showOpenDialog(getParent());
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = new File("");
					AppStatus.setText("Calculating...");
					CalculatedCrypto.setText(DigestUtils.md5Hex(file.getAbsolutePath()));
					AppStatus.setText("");
				}
			}
		});

		btnMD5.setBounds(27, 26, 200, 75);
		panel_6.add(btnMD5);

		JLabel label_10 = new JLabel("");
		label_10.setIcon(new ImageIcon(Interface.class.getResource("/graphics/WhiteBG.jpg")));
		label_10.setBounds(0, 0, 1072, 420);
		panel_6.add(label_10);

		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(Interface.class.getResource("/graphics/WhiteBG.jpg")));
		label.setBounds(0, 0, 1088, 276);
		contentPane.add(label);

		Thread t = new Thread(r);
		t.start(); // Background thread
	}
}
