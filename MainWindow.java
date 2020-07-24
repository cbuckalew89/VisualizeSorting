package main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.JFrame;

public class MainWindow implements ActionListener {
    
    private static final int WIDTH = 1650;
    private static final int HEIGHT = 830;
    private static final Color BAR_COLOR = Color.WHITE;
    private static final int MARGIN = 50;

    private static int barWidth = 15;
    private static int interval = 10;    
    
    private JFrame window;
    private ArrayDrawer panel;
    private MyMenu menu;

    public MainWindow()
    {
    	window = new JFrame();
    	window.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
    	window.setTitle("Sorting Visualiser");
    	window.setLocationRelativeTo(null);
    	window.setPreferredSize(new Dimension(WIDTH, HEIGHT));
    	window.setResizable(false);
    	
    	menu = new MyMenu(this);
    	menu.setBounds((int) (0.33*WIDTH), (int) (0.2*HEIGHT), (int) (0.33*WIDTH), (int) (0.6*HEIGHT));
    	menu.setVisible(false);

    	interval = menu.getInterval();
    	barWidth = menu.getBarWidth();
    	
    	panel = new ArrayDrawer(WIDTH, HEIGHT, MARGIN, barWidth, BAR_COLOR);
    	panel.setLayout(null);
    	panel.add(menu, BorderLayout.CENTER);
    	
    	window.add(panel, BorderLayout.CENTER);
    	window.pack();
    	window.setVisible(true);
    }
    
    public ArrayDrawer getPanel()
    {
    	return panel;
    }
    
    public static int getInterval()
    {
    	return interval;
    }
    
    public void showMenu()
    { 	
       	menu.setVisible(true);
    	window.pack();
    	panel.repaint();
    }
    
    public void hideMenu()
    {
    	menu.setVisible(false);
    	window.pack();
    	panel.repaint();
    }
    
    @Override
	public void actionPerformed(ActionEvent e) 
	{	
		if(e.getSource() == menu.getbSort()) {

			new Thread() {
				public void run() {
					int numberOfValues = panel.getValues().length;
					String selectedAlgotiyhm = menu.getSelectedAlgorithm();

					try {
						panel.setBarWidth(menu.getBarWidth());
						hideMenu();
						ArrayHandler.shuffle(panel.getValues(), panel);
						interval = menu.getInterval();
						Thread.sleep(500);

						switch (selectedAlgotiyhm){

							case "Selection Sort":
								ArrayHandler.selectionSort(panel.getValues(), panel);
								break;

							case "Bubble Sort":
								ArrayHandler.bubbleSort(panel.getValues(), panel);
								break;

							case "Insertion Sort":
								ArrayHandler.insertionSort(panel.getValues(), panel);
								break;

							case "Merge Sort":
								ArrayHandler.mergeSort(panel.getValues(), 0, numberOfValues - 1, panel);
								ArrayHandler.finish(numberOfValues, panel);
								break;

							case "Quick Sort":
								ArrayHandler.quickSort(panel.getValues(), 0, numberOfValues - 1, panel);
								ArrayHandler.finish(numberOfValues, panel);
								break;

							case "Heap Sort":
								ArrayHandler.heapSort(panel.getValues(), panel);
								break;

							case "Radix Sort":
								ArrayHandler.radixSort(panel.getValues(), numberOfValues, panel);
								break;

							case "Shell Sort":
								ArrayHandler.shellSort(panel.getValues(), panel);
								break;
						}
						showMenu();

					} catch(InterruptedException e1) {
						System.out.println(e1.getMessage());
					}
				}

			}.start();
	}
	}
		
    public static void main(String[] args) throws InterruptedException, LineUnavailableException
    {
    	new MainWindow().showMenu();
    }
}
