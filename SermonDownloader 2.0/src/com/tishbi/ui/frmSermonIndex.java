package com.tishbi.ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagLayout;
import java.awt.Toolkit;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JLabel;

import java.awt.GridBagConstraints;

import javax.swing.JButton;

import java.awt.Insets;

import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JMenu;

import com.sun.glass.events.KeyEvent;
import com.tishbi.sitecontent.AllEnums.UpdateStatus;
import com.tishbi.sitecontent.Downloader;
import com.tishbi.sitecontent.Sermon;
import com.tishbi.sitecontent.SermonIndex;
import com.tishbi.sitecontent.SermonIndexCache;
import com.tishbi.sitecontent.Speaker;
import com.tishbi.sitecontent.AllEnums.DownloadStatus;
import com.tishbi.sitecontent.UpdatesChecker;
import com.tishbi.util.Globals;
import com.tishbi.util.Logger;
import com.tishbi.util.Playlist;
import com.tishbi.util.SermonPlayer;
import com.tishbi.util.Utilities;
import com.tishbi.util.XmlUtility;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JSplitPane;
import javax.swing.border.TitledBorder;

import java.awt.Font;

import javax.swing.JCheckBox;

import java.awt.GridLayout;

import javax.swing.SwingConstants;
import javax.swing.JToolBar;


public class frmSermonIndex extends JFrame 
{
	private JPanel contentPane;
	private JList lstSpeakers;
	private JScrollPane scrollPaneSpeakers;
	private JScrollPane scrollPaneSermons;
	private JTextField txtSearch;
	private JCheckBox chkSelectAll;
	private JTable tblSermons;
	private JLabel lblSpeaker;
	private JLabel lblPlaying;
	private JButton btnDownload;
	private JButton btnUpdates;
	private JToolBar tblPlayer;
	private JButton btnFirst;
	private JButton btnPrevious;
	private JButton btnPlay;
	private JButton btnPause;
	private JButton btnStop;
	private JButton btnNext;
	private JButton btnLast;
	
	private ArrayList<Speaker> speakers = new ArrayList<Speaker>();
	private ArrayList<Speaker> filteredList = new ArrayList<Speaker>();
	private ArrayList<Sermon> sermons = new ArrayList<Sermon>();
	private ArrayList<Sermon> selectedSermons = new ArrayList<Sermon>();

	private DefaultListModel<Speaker> speakerModel = new DefaultListModel<Speaker>();
	private DefaultListModel<Speaker> speakerFilterModel = new DefaultListModel<Speaker>();
	
	private ActivityBox txtLogs;
	private SermonPlayer player;
	private Speaker selectedSpeaker;
	private Playlist playlist = null;
	private UpdatesChecker updateChecker;
	
	private int screenWidth, screenHeight;
	private boolean playerStopped = false;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
					frmSermonIndex frame = new frmSermonIndex();
					frame.setVisible(true);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public frmSermonIndex() 
	{
		setIconImage(Toolkit.getDefaultToolkit().getImage(frmSermonIndex.class.getResource("/com/tishbi/res/images/appicon.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 742);
		setTitle("Sermon Downloader 1.0 (Sermonindex.net)");
		screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
		screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
		setLocation((screenWidth-getWidth())/2,100);
		
		//menu components
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(new Color(128, 128, 0));
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		mnFile.setMnemonic(KeyEvent.VK_F);
		menuBar.add(mnFile);
		
		JMenuItem mntmFileExit = new JMenuItem("Exit");
		mntmFileExit.addActionListener(new ActionListener() {

		    @Override
		    public void actionPerformed(ActionEvent arg0) {
		    	int dec = Utilities.Confirm("Are you sure you want to exit the SermonIndex Downloader?", "Exit");
				if (dec == JOptionPane.YES_OPTION)
					System.exit(0);
		    }
		    
		});
		mntmFileExit.setMnemonic(KeyEvent.VK_X);
		mnFile.add(mntmFileExit);	
		
		
		JMenu mnData = new JMenu("Data");
		mnData.setMnemonic(KeyEvent.VK_D);
		menuBar.add(mnData);
		
		JMenuItem mntmSyncupWithWebsite = new JMenuItem("Check for Updates");
		mntmSyncupWithWebsite.setIcon(new ImageIcon(SermonIndexCache.getSyncupImage()));
		mntmSyncupWithWebsite.setToolTipText("<html>This will syncup the local Cached data of selected speaker<br> with website, if no speaker is selected <br>than list of speakers will be rebuilt. <br><br><b>This operation may take several minutes.</b></html>");
		mnData.add(mntmSyncupWithWebsite);
		
		JMenu mnuHelp = new JMenu("Help");
		mnuHelp.setMnemonic(KeyEvent.VK_H);
		menuBar.add(mnuHelp);
		
		JMenuItem mntmAbout = new JMenuItem("About SermonIndex.net");
		mntmAbout.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				dlgAbout about = new dlgAbout();
				about.setLocation((screenWidth-744)/2, (screenHeight-getHeight())/2);
				about.setVisible(true);		
			}
		});
		mntmAbout.setMnemonic(KeyEvent.VK_A);
		mnuHelp.add(mntmAbout);		

		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 1.0, 0.0};
		gbl_contentPane.columnWidths = new int[]{312, 333, 135};
		gbl_contentPane.rowHeights = new int[]{20, 24, 379, 20, 127, 14};
		gbl_contentPane.columnWeights = new double[]{1.0, 0.0, 1.0};
		contentPane.setLayout(gbl_contentPane);

		setContentPane(contentPane);
		
		SetupPlayerToolBar();
		
		lblPlaying = new JLabel();
		lblPlaying.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblPlaying.setForeground(new Color(140, 140, 90));
		GridBagConstraints gbc_lblPlaying = new GridBagConstraints();
		gbc_lblPlaying.gridwidth = 2;
		gbc_lblPlaying.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblPlaying.insets = new Insets(0, 5, 5, 10);
		gbc_lblPlaying.gridx = 0;
		gbc_lblPlaying.gridy = 1;
		contentPane.add(lblPlaying, gbc_lblPlaying);
		
		lblSpeaker = new JLabel("");
		lblSpeaker.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblSpeaker.setForeground(new Color(140, 140, 90));
		GridBagConstraints gbc_lblSpeaker = new GridBagConstraints();
		gbc_lblSpeaker.anchor = GridBagConstraints.EAST;
		gbc_lblSpeaker.insets = new Insets(0, 0, 5, 10);
		gbc_lblSpeaker.gridx = 2;
		gbc_lblSpeaker.gridy = 1;
		contentPane.add(lblSpeaker, gbc_lblSpeaker);
		
		JSplitPane splitPane = new JSplitPane();
		GridBagConstraints gbc_splitPane = new GridBagConstraints();
		gbc_splitPane.anchor = GridBagConstraints.NORTHEAST;
		gbc_splitPane.gridwidth = 3;
		gbc_splitPane.insets = new Insets(0, 0, 5, 0);
		gbc_splitPane.fill = GridBagConstraints.BOTH;
		gbc_splitPane.gridx = 0;
		gbc_splitPane.gridy = 2;
		contentPane.add(splitPane, gbc_splitPane);
		
		lstSpeakers = new JList(speakerModel);
		lstSpeakers.setCellRenderer(new SpeakerListCellRenderer());
		tblSermons = new JTable();
		
		scrollPaneSpeakers = new JScrollPane();
		scrollPaneSpeakers.setViewportBorder(new TitledBorder(null, "Speakers", TitledBorder.LEFT, TitledBorder.TOP, null, null));
		scrollPaneSpeakers.setMinimumSize(new Dimension(200,50));
		scrollPaneSpeakers.setViewportView(lstSpeakers);
		splitPane.setLeftComponent(scrollPaneSpeakers);
		
		scrollPaneSermons = new JScrollPane();
		scrollPaneSermons.setViewportBorder(new TitledBorder(null, "Sermons", TitledBorder.LEFT, TitledBorder.TOP, null, null));
		scrollPaneSermons.setMinimumSize(new Dimension(100,50));
		scrollPaneSermons.setViewportView(tblSermons);
		splitPane.setRightComponent(scrollPaneSermons);
		
		JPanel pnlFilter = new JPanel();
		pnlFilter.setLayout(null);
		GridBagConstraints gbc_pnlFilter = new GridBagConstraints();
		gbc_pnlFilter.insets = new Insets(0, 0, 5, 5);
		gbc_pnlFilter.fill = GridBagConstraints.BOTH;
		gbc_pnlFilter.gridx = 0;
		gbc_pnlFilter.gridy = 3;
		contentPane.add(pnlFilter, gbc_pnlFilter);
		
		txtSearch = new JTextField();
		txtSearch.setBounds(0, 0, 184, 26);
		
		pnlFilter.add(txtSearch);
		txtSearch.setColumns(10);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.setToolTipText("Search speaker ");
		btnSearch.setBounds(194, 0, 89, 26);
		btnSearch.setIcon(SermonIndexCache.searchButtonImage());
		pnlFilter.add(btnSearch);
		
		
		chkSelectAll = new JCheckBox("Select All");
		chkSelectAll.setVerticalAlignment(SwingConstants.TOP);
		
		JPanel pnlSermonbuttons = new JPanel();
		GridBagConstraints gbc_pnlSermonbuttons = new GridBagConstraints();
		gbc_pnlSermonbuttons.fill = GridBagConstraints.BOTH;
		gbc_pnlSermonbuttons.anchor = GridBagConstraints.NORTHEAST;
		gbc_pnlSermonbuttons.insets = new Insets(0, 0, 5, 0);
		gbc_pnlSermonbuttons.gridx = 2;
		gbc_pnlSermonbuttons.gridy = 3;
		pnlSermonbuttons.setLayout(new GridLayout(0, 3, 1, 1));
		
		btnDownload = new JButton("Start");
		btnDownload.setToolTipText("Start downloading selected sermons");
		btnDownload.setIcon(SermonIndexCache.downloadButtonImage());
		
		JButton btnFolders = new JButton("Open Sermon Folder");
		btnFolders.setToolTipText("Open the folder location for selected speaker's sermons");
		btnFolders.setIcon(SermonIndexCache.openFolderButtonImage());
		pnlSermonbuttons.add(chkSelectAll);
		pnlSermonbuttons.add(btnFolders);
		pnlSermonbuttons.add(btnDownload);
		
		contentPane.add(pnlSermonbuttons, gbc_pnlSermonbuttons);
		
		txtLogs = new ActivityBox();
		txtLogs.setFont(new Font("Monospaced", Font.PLAIN, 11));
		txtLogs.setEditable(false);
		
		JScrollPane scrollPaneLogs = new JScrollPane();
		GridBagConstraints gbc_scrollPaneLogs = new GridBagConstraints();
		gbc_scrollPaneLogs.anchor = GridBagConstraints.NORTH;
		gbc_scrollPaneLogs.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneLogs.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPaneLogs.gridwidth = 3;
		gbc_scrollPaneLogs.gridx = 0;
		gbc_scrollPaneLogs.gridy = 4;
		scrollPaneLogs.setViewportView(txtLogs);
		contentPane.add(scrollPaneLogs, gbc_scrollPaneLogs);
		
		JLabel lblSermonIndex = new JLabel("sermonindex.net");
		lblSermonIndex.setVerticalAlignment(SwingConstants.BOTTOM);
		GridBagConstraints gbc_lblSermonIndex = new GridBagConstraints();
		gbc_lblSermonIndex.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblSermonIndex.anchor = GridBagConstraints.SOUTHWEST;
		gbc_lblSermonIndex.insets = new Insets(0, 0, 0, 5);
		gbc_lblSermonIndex.gridx = 0;
		gbc_lblSermonIndex.gridy = 5;
		contentPane.add(lblSermonIndex, gbc_lblSermonIndex);
		
		JLabel lblOS = new JLabel("OS Information");
		lblOS.setVerticalAlignment(SwingConstants.BOTTOM);
		GridBagConstraints gbc_lblOs = new GridBagConstraints();
		gbc_lblOs.insets = new Insets(0, 0, 0, 10);
		gbc_lblOs.gridwidth = 2;
		gbc_lblOs.anchor = GridBagConstraints.EAST;
		gbc_lblOs.gridx = 1;
		gbc_lblOs.gridy = 5;
		contentPane.add(lblOS, gbc_lblOs);
		lblOS.setText("Operating System : " + Globals.getOS());
		
		Logger.Log("Initial UI setup is done.");

		
		mntmSyncupWithWebsite.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				updateChecker = new UpdatesChecker();
				updateChecker.CheckforUpdates();
			}
		});

	
		chkSelectAll.setEnabled(false);
		chkSelectAll.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if (sermons.size()>0)
				{
					if (chkSelectAll.isSelected())
					{
						for (Sermon _sermon : sermons)
						{
							if (_sermon != null)
								_sermon.isSermonSelected = true;
						}
						ShowSermons(sermons);
					}
					else
					{
						for (Sermon _sermon : sermons)
						{
							if (_sermon != null)
								_sermon.isSermonSelected = false;
						}
						ShowSermons(sermons);
					}
				}
			}
		});

		
		btnSearch.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				if (txtSearch.getText().trim().length() == 0 && speakerModel.size() == 0) return;
				filterSpeakers();
			}
		});

		
		final Date startTime = new Date();
		if (SermonIndexCache.isLocalCacheOfSpeakersAvailable())
		{
			BuildSpeakersFromLocalCache();
		}
		else
		{
			SwingWorker updWorker = new SwingWorker()
			{
				@Override
				protected Object doInBackground() throws Exception
				{
					updateChecker = new UpdatesChecker();
					updateChecker.addPropertyChangeListener(new UpdateStatusListener(lblPlaying));
					updateChecker.CheckforUpdates();
					return null;
				}

				@Override
				protected void done()
				{
					lblPlaying.setText("Updates successfully Applied. Loading all speaker...");
					lblPlaying.setIcon(null);
					BuildSpeakersFromLocalCache();
				}
			};
			updWorker.execute();
		}
		
		//Player toolbar 
		
		btnPlay.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				if (playlist != null) playlist.Stop();
				playlist = null;
				for (Sermon _sermon : sermons)
				{
					if (_sermon.isSermonSelected)
					{
						if (playlist == null)
							playlist = new Playlist(tblSermons);
						
						playlist.Add(_sermon);
					}
				}
				if (playlist != null)
				{
					playlist.addPropertyChangeListener(new PlaybackListener(lblPlaying));
					playlist.Play();
				}
				else
					Utilities.ShowMessage("No sermon or song selected to play.", "Empty playlist");
			}
		});
		
		btnPause.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				if (playlist != null) playlist.Stop();
			}
		});
		
		btnNext.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				if (playlist == null) return;
				playlist.Next();
			}
		});

		
		
		
		//Gather all sermons of selected speaker
		lstSpeakers.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				if (player != null) player.Stop();
				
				int index = lstSpeakers.locationToIndex(e.getPoint());
				if (index != -1 && SwingUtilities.isLeftMouseButton(e))
				{
					selectedSpeaker = (Speaker) lstSpeakers.getModel().getElementAt(index);
					lblSpeaker.setText(selectedSpeaker.Name);
					if (selectedSpeaker.SpeakerImage!=null)
						lblSpeaker.setIcon(new ImageIcon(selectedSpeaker.SpeakerImage.getScaledInstance(24, 24, java.awt.Image.SCALE_SMOOTH)));
					else
						lblSpeaker.setIcon(new ImageIcon(SermonIndexCache.speakerDefaultImage().getScaledInstance(24, 24, java.awt.Image.SCALE_SMOOTH)));
					
					sermons.clear();
					try 
					{
						if (SermonIndexCache.isLocalCacheOfSermonsForSpeakerAvailable(selectedSpeaker.Name))
						{
							sermons.add(new Sermon("","Loading sermons from local Cache..."));
							txtLogs.append("Loading sermons of -> "+ selectedSpeaker.Name +" from local Cache.\n");
						}
						else
						{
							scrollPaneSermons.setViewportBorder(new TitledBorder(null, "Sermons (0)", TitledBorder.LEFT, TitledBorder.TOP, null, null));
							txtLogs.append("No sermon data available for selected speaker, please check for updates.\n");
							Utilities.ShowMessage("No sermon data available for selected speaker, please check for updates.","No Sermons");
							return;
						}
					} catch (IOException e1) 
					{
						e1.printStackTrace();
					}
					ShowSermons(sermons);
					final Date d = new Date();
					SwingWorker<Date,Integer> collectSermons = new SwingWorker<Date,Integer>() 
					{
						@Override
						protected Date doInBackground() throws Exception 
						{
							sermons = SermonIndexCache.getSermonsOfSpeaker(selectedSpeaker);
							return new Date();
						}

						@Override
						protected void done() 
						{
							if (sermons != null && sermons.size()>0)
							{
								txtLogs.append(sermons.size()+" sermons of " + lblSpeaker.getText() + " found. Time Taken " + Utilities.TimeTaken(d)+"\n");
								ShowSermons(sermons);
							}
							else
								txtLogs.append("0 sermons found.\n");
							super.done();
						}
					};
					collectSermons.execute();
				}				
			}
		});
		
		
		
		//Folder button click 
		btnFolders.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				if (lblSpeaker.getText().trim().isEmpty())
					Utilities.ShowMessage("Select a speaker first to open his sermon folder.", "Open Folder");
				else
				{
					String speakerFolder = Globals.sermonPath() + Utilities.getFormatedSpeakerName(lblSpeaker.getText());
					try 
					{
						File folder = new File(speakerFolder);
						if (folder.exists())
							Desktop.getDesktop().open(new File(speakerFolder));
						else
							Utilities.ShowMessage("You need to download atleast one sermon before you can open the sermon folder of a speaker.", "Open Folder");
					} catch (IOException e1) 
					{
						e1.printStackTrace();
					}
				}
			}
		});
		
		btnDownload.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				downloadSelectedSermons();
			}
		});
		
		
		
		tblSermons.addMouseMotionListener(new MouseMotionAdapter() 
		{
			@Override
			public void mouseMoved(MouseEvent e) 
			{
				DownloadStatus ds = null;
				int col = tblSermons.columnAtPoint(e.getPoint());
				int row = tblSermons.rowAtPoint(e.getPoint());
				if (row != -1 && col != -1)
					ds = sermons.get(row).Status;
				if (col != -1)
				{
					if (col==4 &&  ds == DownloadStatus.ALREADY_DONE || ds == DownloadStatus.COMPLETED || ds == DownloadStatus.PLAYING)
						tblSermons.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					else
						tblSermons.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				}
			}
		});

		tblSermons.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				if (sermons.isEmpty()) return;
				Sermon _sermon = null;
				int col = tblSermons.columnAtPoint(e.getPoint());
				int row = tblSermons.rowAtPoint(e.getPoint());
				if (row != -1 && col != -1)
				{
					_sermon = sermons.get(row);
				}
				if (col == 4 && _sermon != null)
				{
					// check to see if sermon has been downloaded before playing in player
					if(_sermon.Status == DownloadStatus.NOT_STARTED || _sermon.Status == DownloadStatus.FAILED) {
						int dec = Utilities.Confirm("You must first download the sermon. Would you like to download it?","Download Sermon");
						if (dec == JOptionPane.YES_OPTION) {
							SermonTableModel model = (SermonTableModel)tblSermons.getModel();
							model.setValueAt(true, row, 3);
							downloadSelectedSermons();
						}
						return;
					}
						
					if (playlist != null)
					{
						Sermon currSermon = playlist.currentlyPlaying();
						playlist.Stop();
						if (currSermon == _sermon) return;
					}
					
					playlist = new Playlist(tblSermons);
					playlist.Add(_sermon);
					playlist.addPropertyChangeListener(new PlaybackListener(lblPlaying));
					playlist.Play();
				}
			}
		});
	}
	
	private void downloadSelectedSermons() {
		boolean sermonSelected = false;
		if (sermons.isEmpty())
		{
			Utilities.ShowMessage("There are no sermons to download", "Missing Information");
			return;
		}
		selectedSermons = ((SermonTableModel)tblSermons.getModel()).getSermons();
		for (Sermon _sermon : selectedSermons)
		{
			if (_sermon.isSermonSelected)
			{
				_sermon.Status = DownloadStatus.DOWNLOADING;
				_sermon.Speaker = lblSpeaker.getText();
				sermonSelected = true;
			}
		}
		if (!sermonSelected)
		{
			Utilities.ShowMessage("No sermon selected to download.", "Missing Information");
			return;
		}
		ShowSermons(selectedSermons);
		
		
        final Date d = new Date();
        try
        {
        	txtLogs.append("Started downloading at : " + d.toString()+"\n========================\n");
            SwingWorker<Date,Sermon> worker = new SwingWorker<Date,Sermon>()
            {
				@Override
				protected Date doInBackground() throws Exception 
				{
					for(final Sermon _sermon : selectedSermons)
					{
						if (!_sermon.isSermonSelected) continue;
						_sermon.Status = Downloader.Download(_sermon);
						publish(_sermon);
					}
					return new Date();
				}

				@Override
				protected void done() 
				{
					txtLogs.append("=======================================\n");
	                txtLogs.append("Completed downloading. Total time taken : " + Utilities.TimeTaken(d)+"\n");
	                btnDownload.setEnabled(true);
	            }

				@Override
				protected void process(List<Sermon> sermons) 
				{
					for(Sermon _sermon : sermons)
					{
						((SermonTableModel)tblSermons.getModel()).setDownloadStatus(_sermon);
						txtLogs.append(_sermon.Title + 
								"(" + _sermon.Length + 
								") -> "+ _sermon.getStatus() + "\n");
					}
					tblSermons.repaint();
				}
            };
            worker.execute();
        }
        catch(Exception ex)
        {
        	ex.printStackTrace();
        }
	}
	
	private void SetupPlayerToolBar()
	{
		tblPlayer = new JToolBar();
		tblPlayer.setFloatable(false);
		GridBagConstraints gbc_tblPlayer = new GridBagConstraints();
		gbc_tblPlayer.anchor = GridBagConstraints.NORTH;
		gbc_tblPlayer.gridwidth = 3;
		gbc_tblPlayer.insets = new Insets(0, 0, 5, 5);
		gbc_tblPlayer.gridx = 0;
		gbc_tblPlayer.gridy = 0;
		
		btnFirst = new JButton("");
		btnFirst.setToolTipText("Start from beginning");
		btnFirst.setIcon(SermonIndexCache.toolbarGotoFirstImage());
		
		btnPrevious = new JButton("");
		btnPrevious.setToolTipText("Previous sermon/song");
		btnPrevious.setIcon(SermonIndexCache.toolbarGotoPreviousImage());
		
		btnPlay = new JButton("");
		btnPlay.setToolTipText("Start playing all selected sermons/songs");
		btnPlay.setIcon(SermonIndexCache.toolbarPlayImage());
		
		btnStop = new JButton("");
		btnStop.setToolTipText("Stop playback");
		btnStop.setIcon(SermonIndexCache.toolbarStopImage());
		
		btnPause = new JButton("");
		btnPause.setToolTipText("Pause playback");
		btnPause.setIcon(SermonIndexCache.toolbarPauseImage());
		
		btnNext = new JButton("");
		btnNext.setToolTipText("Play next sermon/song");
		btnNext.setIcon(SermonIndexCache.toolbarGotoNextImage());
		
		btnLast = new JButton("");
		btnLast.setToolTipText("Go to last sermon/song");
		btnLast.setIcon(SermonIndexCache.toolbarGotoLastImage());

		tblPlayer.add(btnFirst);
		tblPlayer.add(btnPrevious);
		tblPlayer.add(btnPlay);
		tblPlayer.add(btnPause);
		tblPlayer.add(btnStop);
		tblPlayer.add(btnNext);
		tblPlayer.add(btnLast);
		
		contentPane.add(tblPlayer, gbc_tblPlayer);
	}
	
	private void ShowSermons(ArrayList<Sermon> _sermons)
	{
		try
		{
			scrollPaneSermons.setViewportBorder(new TitledBorder(null, "Sermons ("+ _sermons.size() +")", TitledBorder.LEFT, TitledBorder.TOP, null, null));
		    tblSermons.setModel(new SermonTableModel(_sermons));
		    tblSermons.setRowHeight(25);
		    tblSermons.setAutoCreateRowSorter(true);
		    tblSermons.removeColumn(tblSermons.getColumnModel().getColumn(5));
		    tblSermons.removeColumn(tblSermons.getColumnModel().getColumn(5));
		    tblSermons.removeColumn(tblSermons.getColumnModel().getColumn(5));
		    
		    tblSermons.getColumnModel().getColumn(0).setPreferredWidth(400);
		    tblSermons.getColumnModel().getColumn(2).setCellRenderer(new StatusCellRenderer());
		    tblSermons.getColumnModel().getColumn(4).setCellRenderer(new PlayButtonCellRenderer());
		    tblSermons.setShowVerticalLines(true);
		    this.sermons = _sermons;
		    chkSelectAll.setEnabled(true);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	
	//List filter
	private void filterSpeakers()
	{
		if (txtSearch.getText().isEmpty()) 
		{
			lstSpeakers.setModel(speakerModel);
			scrollPaneSpeakers.setViewportBorder(new TitledBorder(null, "Speakers (" + speakerModel.size() + ")" , TitledBorder.LEFT, TitledBorder.TOP, null, null));
		}
		else
		{
			SwingWorker filterWorker = new SwingWorker() 
			{

				@Override
				protected Object doInBackground() throws Exception 
				{
					if (speakerFilterModel.size() > 0) speakerFilterModel.clear();
					for(Speaker spk : speakers)
					{
						if (spk.Name.toUpperCase().contains(txtSearch.getText().trim().toUpperCase()))
						{
							speakerFilterModel.addElement(spk);
						}
					}
					return null;
				}

				@Override
				protected void done() 
				{
					lstSpeakers.setModel(speakerFilterModel);
					scrollPaneSpeakers.setViewportBorder(new TitledBorder(null, "Speakers (" + speakerFilterModel.size() + ")" , TitledBorder.LEFT, TitledBorder.TOP, null, null));
					scrollPaneSpeakers.repaint();
				}
			};
			filterWorker.execute();
		}
	}

	private void UpdateSpeakerList(final ArrayList<Speaker> _speakers, boolean reBuild)
	{
		if (reBuild) speakerModel.clear();
		
		for(Speaker _speaker : _speakers)
		{
			if (_speaker != null)
				speakerModel.addElement(_speaker);
		}
		scrollPaneSpeakers.setViewportBorder(new TitledBorder(null, "Speakers (" + speakerModel.size() + ")" , TitledBorder.LEFT, TitledBorder.TOP, null, null));
		lstSpeakers.setModel(speakerModel);
		if (speakerModel != null)
		{
			Logger.Log("Adding SearchListener.");
			//txtSearch.getDocument().addDocumentListener(new SearchListener(speakerModel));
		}
	}
	
	private void BuildSpeakersFromLocalCache()
	{
		txtLogs.append("Loading Speakers from Cache, please wait......\n");
		SwingWorker<Date,Speaker > listLoader = new SwingWorker<Date,Speaker>()
		{
			@Override
			protected Date doInBackground() throws Exception 
			{
				speakers = SermonIndexCache.getSpeakersFromCache();
				for(Speaker _speaker : speakers)
				{
					publish(_speaker);
				}
				
				return new Date();
			}

			@Override
			protected void done() 
			{
				txtLogs.append(speakers.size() + " Speakers found in local Cache.\n");
				UpdateSpeakerList(speakers,true);
				txtSearch.setEditable(true);
				lblPlaying.setText("");
			}
		};
		listLoader.execute();
	}
	
	private void BuildSpeakerListFromSite()
	{
		if (speakers.size()>0)
		{
			speakers.clear();
			speakerModel.clear();
			lstSpeakers.setModel(speakerModel);
		}
		
		final Date startTime = new Date();
		txtLogs.append("Loading Speakers from SermonIndex.net, please wait......\n");
		SwingWorker<Date,Speaker> worker = new SwingWorker<Date,Speaker>()
		{
			Speaker _speaker = null;
			String url;
			int speakerCount=0;
			
			@Override
			protected Date doInBackground() throws Exception 
			{
				for(int i=1; i<=1500;i++)
				{
					url = "http://www.sermonindex.net/modules/mydownloads/viewcat.php?cid="+ i;
					_speaker = SermonIndex.getSpeaker(url);
					if (_speaker != null)
						speakers.add(_speaker);
					publish(_speaker);
				}
				return new Date();
			}

			@Override
			protected void process(List<Speaker> _speakers) 
			{
				for (Speaker _speaker : _speakers)
				{
					if(_speaker != null)
						speakerModel.addElement(_speaker);
				}
				lstSpeakers.setModel(speakerModel);
			}

			@Override
			protected void done() 
			{
				if (!speakers.isEmpty())
				{
					txtLogs.append(speakers.size() + " speakers found, Total time taken : " + Utilities.TimeTaken(startTime)+"\n");
					txtSearch.setEditable(true);
					System.out.println("Serializing to XML");
					txtLogs.append("Re-writing local Cache.\n");
					XmlUtility.SpeakersToXml(speakers);
					txtLogs.append("Local Cache re-written successfully. Next time speakers list will be loaded much faster.\n");
				}
				else
				{
					txtLogs.append("No speakers found.\n");
				}
			}
		};
		worker.execute();
	}
}
