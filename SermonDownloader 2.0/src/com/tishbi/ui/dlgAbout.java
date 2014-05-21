package com.tishbi.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTextPane;
import javax.swing.JScrollPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.UIManager;

public class dlgAbout extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private int screenWidth, screenHeight;

	/**
	 * Create the dialog.
	 */
	public dlgAbout() 
	{
		setTitle("About SermonIndex.net");
		setResizable(false);
		screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
		screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

		setBounds(600, 300, 744, 485);
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblAbout = new JLabel("Sermon Downloader 1.0");
		lblAbout.setHorizontalAlignment(SwingConstants.LEFT);
		lblAbout.setVerticalAlignment(SwingConstants.TOP);
		lblAbout.setBounds(10, 11, 213, 14);
		contentPanel.add(lblAbout);
		
		JTextPane txtAbout = new JTextPane();
		txtAbout.setBackground(UIManager.getColor("Button.light"));
		txtAbout.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtAbout.setContentType("text/html");
		txtAbout.setEditable(false);
		DefaultCaret caret = (DefaultCaret)txtAbout.getCaret();
		caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
		txtAbout.setText("<html><b>About Us - Sermon Index</b>\r\n<br><br>\r\nSermonindex is not an attempt by man to build something for God. It began as God gave the burden and only will continue as He is guiding the work being fully trusted. It began by a work of God's Spirit and for His glory: Not by might nor by power, but by my Spirit, says the LORD Almighty.<a href=#footnotes><sup>1</sup></a> The work and ministry of SermonIndex can be encapsulated in this one word: Revival. Concepts such as Holiness, Purity, Christ-Likeness, Self-Denial and Discipleship are hardly the goal of much modern preaching. Thus the main thrust of the speakers and articles on the website encourage us towards a reviving of these missing elements of Christianity. This biblical balanced Christianity is something needed desperately today. Unless the LORD builds the house, the builders labor in vain.<a href=#footnotes><sup>2</sup></a> The desire for a Spiritual Awakening (revival in a larger-sense<a href=#footnotes><sup>3</sup></a>) is close to the heart of this ministry. That we would see God working in such a way that sin would be forsaken, restitution be made, Christ honored and the morality of society even changed. \r\r\n<br /><br />\r\r\n<i>'The mission of SermonIndex is the preservation<a href=#footnotes><sup>4</sup></a> and propagation<a href=#footnotes><sup>5</sup></a> of classical Biblical preaching and the promotion<a href=#footnotes><sup>6</sup></a> of genuine Biblical revival to this generation.'</i>\r\r\n<br /><br />\r\r\nOne of the original members<a href=#footnotes><sup>7</sup></a> who registerd on SermonIndex made this comment which rung true to our journey in finding these resources: <i>I was now beholding a precious gem, what my heart had been longing for. The strong preaching that seems to have gone by the wayside has once again been resurrected.</i> Today there is a cry for the authentic and genuine in the midst of today's lukewarm nominal christianity.<a href=#footnotes><sup>8</sup></a> This following Scripture was given by God during the founding of the ministry of SermonIndex: A horrible and shocking thing has happened in the land: The prophets prophesy lies, the priests rule by their own authority, and my people love it this way. But what will you do in the end?<a href=#footnotes><sup>9</sup></a><br /><br /><br />\r\r\n\r\r\n<b>Q. When did SermonIndex start?</b><br>\r\r\n<i>Answer. The ministry of sermonindex started officially in December 2002. The conception of the site began a year before that when the founder read a book Why Revival Tarries.<a href=#footnotes><sup>10</sup></a> This book deeply impacted and gave a sense of clarity to many questions relating to the desire for true revival in Church. The first media to be added to the site were the Leonard Ravenhill Videos which you can now download as a <a href='http://www.sermonindex.net/podcast.php'>podcast</a>. These video tapes were given by the Ravenhill family which you can <a href='http://www.ravenhill.org/tapes2.htm' target='_blank'>order still</a>.</i><br><br>\r\r\n\r\r\n<b>Q. What is the reach of this ministry?</b><br>\r\r\n<i>Answer. SermonIndex has been able to reach into 215 countries in the world in the last 9 years with million's of sermon downloads. All these resources have been made freely available to God's people. Read some <a href=http://www.sermonindex.net/commendations.php>commendations of SermonIndex.net</a> from various ministry leaders and pastors.</i><br><br>\r\r\n\r\r\n<b>Q. Who runs SermonIndex?</b><br>\r\r\n<i>Answer. The body of Christ. Since its inception volunteer support has made it possible to archive thousands of audio, video and text sermons and other resources on the website. The true ownership of the website belongs to God and His people who have made it a inter-denominational expression of the body of Christ world-wide. The ministry founder is currently running the website <a href='http://www.sermonindex.net/donate.php'>ministry by faith</a>.</i><br><br>\r\r\n\r\r\n<b>Q. Please define to us the SermonIndex motto statement?</b><br>\r\r\n<i>Answer. This phrase 'Promoting Genuine Biblical Revival' was adopted early on as the SermonIndex website grew in its vision and purpose. This desire for genuine revival was birthed after hearing the preaching ministry of Leonard Ravenhill. A message given titled <a href='http://www.sermonindex.net/modules/mydownloads/singlefile.php?lid=1&commentView=itemComments'>Christ Magnified In Our Bodies</a>, was the first message added to SermonIndex and clarifies this desire.</i></i><br><br>\r\r\n\r\r\n<b>Q. Does SermonIndex have a certain denominational emphasis?</b><br>\r\r\n<i>Answer. No. SermonIndex desires to be a expression of the apostolic church found in Scriptures (Book Of Acts). It is a inter-denominational ministry with 100's of different denominations and ministries involved. Revival, Holiness, Authenticity, and a genuine experience of Biblical Christianity is core the values of this ministry. We believe that the Church of Jesus Christ is larger than any one denomination or group, and that Christ has His love slaves in many different sections of the visible church in many different parts of the world. A general statement of faith can be read below.</i><br><br>\r\r\n\r\r\n<b>Q. Is there a statement of faith for the website?</b><br>\r\r\n<i>Answer. Being undenominational, we seek to minister to all who love our Lord Jesus Christ in sincerity (Eph. 6:24), and to love all who love Him (John 15:12; 1 John 4:20-21). By this shall all men know that ye are My disciples, if ye have love one to another (John 13:35). We hold to the Bible, both Old and New Testaments, as the inspired word of God.  We believe in the New Birth, and that Salvation is a free gift of God through Jesus Christ to all who will repent, and believe the Gospel (Mark 1:15; John 3:16). We believe in the virgin birth of Jesus, the only begotten Son of God (Isa. 7:14; Matt. 1:18-25; Luke 1:26-35; Luke 2:1-7; John 1:14; John 3:16; Acts 13:16-41; 1 John 4:1-15). We believe in the deity of God the Father, the deity of Jesus, the Son of God, and the deity of the Holy Spirit. We believe that, while we were yet sinners, Jesus Christ shed His precious Blood on the cross, and died, that we might have life, and have it more abundantly (Rom. 5:8; John 10:10).  We believe that Jesus rose again from the dead (1 Thess. 4:14; Luke 24:1-8; Acts 10:34-41; Luke 24:33-46). We believe that Jesus, some days after His resurrection from the dead, ascended to God in heaven (Acts1:1-10; Eph. 1:15-23).  We believe in the personal return of Jesus with power and great glory (Luke 21:27), and that His return is an event for saved people to look forward to with great expectation and with constant watchfulness and prayer (Luke 21:34-36; 1 John 3:2-3).  We believe that this same Jesus, which was taken up from the apostles into heaven, shall so come in like manner as they saw Him go into heaven (Acts 1:1-11). We believe it is the privilege and duty of every Christian--to be filled with the Holy Spirit, and to live a holy life, and to be always ready for the return of Jesus, and that, in the meantime, each Christian should Occupy (keep busy in His will, and in His service) till the Lord's return (John 7:37-39; Acts 1:8; Eph. 5:18; Matt. 24:44; Luke 19:13; Titus 2:11-15; 1 Peter 1:13-19).</i><br><br>\r\r\n\r\r\n<b>Q. What are some of the sermons that are highly recommended?</b><br>\r\r\n<i>Answer. Subscribe freely to the official iTunes SermonIndex Classics Podcast featuring various speakers.. <a href='http://www.sermonindex.net/podcast.php'>SermonIndex Classics Podcast</a></i><br><br>\r\r\n\r\r\n<b>Q. Can I put a link to SermonIndex.net?</b><br>\r\r\n<i>Answer. Yes. This is encouraged and it helps us spread these messages to many more people. We also have a <a href='http://www.sermonindex.net/banners.php'>selection of image banners</a> you can use to put on your website for free.</i><br><br>\r\r\n\r\r\n<br><br>\r\r\n\r\r\n<br>\r\r\n<a name=footnotes>Footnotes:</a><br>\r\r\n<sup>1</sup> Zechariah 4:6<br>\r\r\n<sup>2</sup> Psalm 127:1<br>\r\r\n<sup>3</sup> There are exact times in Church history where there was a moving of God's Holy Spirit where large numbers of unbelievers repent and believe in Christ. Such movements begin in prayer and usually accomplish more in a few years then decades without such.<br>\r\r\n<sup>4</sup> With preservation we mean the gathering of classic Christianity to be able to keep it safe and accessible to generations to come freely.<br>\r\r\n<sup>5</sup> With propagation our desire is to see these sermon messages to reach ears that have never heard such a message and view of true Christianity. <br>\r\r\n<sup>6</sup> With promotion we mean the spreading of these messages further into areas where they have been and to equip saints to freely share these sermons with as many as possible.<br>\r\r\n<sup>7</sup> Mike Balog<br>\r\r\n<sup>8</sup> We bring a clear distinction between larger christendom and the true body of Christ.<br>\r\r\n<sup>9</sup> Jeremiah 5:30-31<br>\r\r\n<sup>10</sup> A famous book written by the revivalist Leonard Ravenhill.<br>\r\r\n\r\r\n\r\r\n<br><br>");
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 36, 722, 376);
		contentPanel.add(scrollPane);
		
		scrollPane.setViewportView(txtAbout);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() 
				{
					public void actionPerformed(ActionEvent arg0) 
					{
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}
}
