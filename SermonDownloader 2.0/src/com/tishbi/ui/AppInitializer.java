package com.tishbi.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;
import javax.swing.UIManager;

import com.tishbi.sitecontent.AllEnums.UpdateStatus;
import com.tishbi.sitecontent.SermonIndexCache;
import com.tishbi.sitecontent.UpdatesChecker;
import com.tishbi.util.Utilities;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AppInitializer extends JDialog
{

	private final JPanel contentPanel = new JPanel();
	private int screenWidth, screenHeight;
	private UpdatesChecker updateChecker;

	/**
	 * Create the dialog.
	 */
	public AppInitializer(JFrame frm)
	{
		super(frm);
		setTitle("Initializing Sermon Downloader");
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 455, 179);
		screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
		screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
		setLocation((screenWidth-getWidth())/2,(screenHeight-getHeight())/2);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JTextArea txtrYouAreRunning = new JTextArea();
		txtrYouAreRunning.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtrYouAreRunning.setBackground(UIManager.getColor("CheckBox.background"));
		txtrYouAreRunning.setWrapStyleWord(true);
		txtrYouAreRunning.setLineWrap(true);
		txtrYouAreRunning.setText("You are running this Application for the first time. It needs to setup some basic data before you can use it. Please wait patiently as it sets up the basic data to make it perform faster in response as you use it.");
		txtrYouAreRunning.setBounds(70, 11, 364, 81);
		txtrYouAreRunning.setBorder(new EmptyBorder(0,0,0,0));
		contentPanel.add(txtrYouAreRunning);
		
		JLabel lblStatus = new JLabel("New label");
		lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblStatus.setBounds(10, 93, 424, 24);
		contentPanel.add(lblStatus);
		
		JLabel lblImg = new JLabel("");
		lblImg.setBounds(10, 11, 50, 59);
		lblImg.setIcon(new ImageIcon(SermonIndexCache.AppImage()));
		contentPanel.add(lblImg);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			
			JButton btnNewButton = new JButton("Ok\r\n");
			btnNewButton.addActionListener(new ActionListener() 
			{
				public void actionPerformed(ActionEvent arg0) 
				{
					dispose();
				}
			});
			buttonPane.add(btnNewButton);
		}
		setVisible(true);
		updateChecker = new UpdatesChecker();
		updateChecker.addPropertyChangeListener(new UpdateStatusListener(lblStatus));
		updateChecker.CheckforUpdates();
	}
}
