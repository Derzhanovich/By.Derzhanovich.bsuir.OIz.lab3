package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Frame extends JFrame  {
	
	private static final long serialVersionUID = 1L;
	
	private File file = new File("src/images/1.jpg");
	private JPanel optionsPanel = new JPanel();
	private JPanel startImgPanel = new JPanel();
	private JPanel resultImgPanel = new JPanel();
	private JLabel ImageLabel;
	private BufferedImage startImage = null;
	JFrame frame = new JFrame();
	Segmentation segmentImg;
	JTextArea text = new JTextArea("2");

	Frame() {
		setLayout(new BorderLayout());
		JLabel s = new JLabel ("Количество кластеров: ");
		JButton segment = new JButton(("Сегментация"));

		optionsPanel.add(s);
		optionsPanel.add(text);
		optionsPanel.add(segment);

		try {
			startImage = ImageIO.read(new FileImageInputStream(file));
		} catch (IOException e) {
			e.printStackTrace();
		}

		JLabel label = new JLabel(new ImageIcon(startImage));
		startImgPanel.add(label);
		
		add(startImgPanel, BorderLayout.WEST);
		add(optionsPanel, BorderLayout.NORTH);
		add(resultImgPanel, BorderLayout.CENTER);
		
		
		segment.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent arg0) {
					try {
						segmentImg = new Segmentation(startImage, Integer.parseInt(text.getText()));
					} catch (NumberFormatException e) {
						segmentImg = new Segmentation(startImage, 2);
					}
					
					ImageLabel = new JLabel(new ImageIcon(segmentImg.bind()));
					setMinimumSize(new Dimension(1030, 460));
					resultImgPanel.removeAll();
					resultImgPanel.add(ImageLabel);
					resultImgPanel.repaint();
					JOptionPane.showMessageDialog(frame, "Сегментация завершена");
					pack();
			}
		});

		pack();
	}
}
