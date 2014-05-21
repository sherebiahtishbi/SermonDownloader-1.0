package com.tishbi.ui;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import com.tishbi.sitecontent.Sermon;
import com.tishbi.util.Utilities;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class frmPlayer extends JFrame 
{
	private static final long serialVersionUID = 1L;
	private Player player = null;
	private FileInputStream inputStream;
	private Sermon currentSermon;

	public frmPlayer(Sermon sermon)
	{
		this.currentSermon = sermon; 
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{341, -235};
		gridBagLayout.rowHeights = new int[]{14, 180, 23, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		JLabel lblTitle = new JLabel("");
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 11));
		GridBagConstraints gbc_lblTitle = new GridBagConstraints();
		gbc_lblTitle.anchor = GridBagConstraints.NORTH;
		gbc_lblTitle.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblTitle.insets = new Insets(0, 0, 5, 0);
		gbc_lblTitle.gridx = 0;
		gbc_lblTitle.gridy = 0;
		getContentPane().add(lblTitle, gbc_lblTitle);
		lblTitle.setText(sermon.Title);
		
		JLabel lblSpeaker = new JLabel("");
		GridBagConstraints gbc_lblSpeaker = new GridBagConstraints();
		gbc_lblSpeaker.fill = GridBagConstraints.BOTH;
		gbc_lblSpeaker.insets = new Insets(0, 0, 5, 0);
		gbc_lblSpeaker.gridx = 0;
		gbc_lblSpeaker.gridy = 1;
		getContentPane().add(lblSpeaker, gbc_lblSpeaker);

		JButton btnPlay = new JButton("Play");
		GridBagConstraints gbc_btnPlay = new GridBagConstraints();
		gbc_btnPlay.anchor = GridBagConstraints.WEST;
		gbc_btnPlay.gridx = 0;
		gbc_btnPlay.gridy = 2;
		getContentPane().add(btnPlay, gbc_btnPlay);
		
		JButton btnClose = new JButton("Close");
		GridBagConstraints gbc_btnClose = new GridBagConstraints();
		gbc_btnClose.anchor = GridBagConstraints.EAST;
		gbc_btnClose.gridx = 1;
		gbc_btnClose.gridy = 2;
		getContentPane().add(btnClose, gbc_btnClose);
		
		btnClose.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				setVisible(false);
			}
		});

		String sermonFileName = Utilities.getSermonFilename(sermon.Title, sermon.Speaker);
		if (sermon.Speaker!=null)
			lblSpeaker.setText(sermon.Speaker);
		
		setTitle(sermon.Title);
		
		try 
		{
			inputStream = new FileInputStream(new File(sermonFileName));
			player = new Player(inputStream);
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (JavaLayerException e) 
		{
			e.printStackTrace();
		}
		
		btnPlay.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				try 
				{
					if (player != null)
					player.play();
				} 
				catch (JavaLayerException e) 
				{
					e.printStackTrace();
				}
			}
		});

	}
	
	public void Play()
	{
		try 
		{
			if (player != null)
			player.play();
		} 
		catch (JavaLayerException e) 
		{
			e.printStackTrace();
		}
	}
	
	public Sermon CurrentSermon()
	{
		return this.currentSermon;
	}
	
}
