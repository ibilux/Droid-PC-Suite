package dpcs;

import java.io.File;

import javax.swing.SwingUtilities;

public class Main {
	@SuppressWarnings("static-access")
	public static void main(String args[]) throws Exception {
		Splash image = new Splash();
		image.setVisible(true);
		Thread thread = Thread.currentThread();
		thread.sleep(5000);
		image.dispose();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Interface().setVisible(true);
				try {
					Process p1 = Runtime.getRuntime().exec("adb kill-server"); // Old
																				// ADB
																				// instance
																				// killer
					p1.waitFor();
					File file1 = new File(".checkadbconnection"); // Cache
																	// remover
					if (file1.exists() && !file1.isDirectory()) {
						file1.delete();
						File file2 = new File("su");
						if (file2.exists() && !file2.isDirectory()) {
							file2.delete();
						}
					}
				} catch (Exception e) {
					System.err.println(e);
				}
			}
		});
	}
}
