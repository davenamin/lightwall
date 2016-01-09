package edu.uri.cs;


import java.awt.Color;
import java.awt.Paint;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.PaintScale;
import org.jfree.chart.renderer.xy.XYBlockRenderer;
import org.jfree.data.xy.MatrixSeries;
import org.jfree.data.xy.MatrixSeriesCollection;
import org.jfree.data.xy.XYZDataset;

public class Simulator {

    private ChartPanel panel;
    private JFrame frame;
    private JFreeChart jfree;
    private MatrixSeries leds;
    
    public Simulator(){
    	
    	leds = new MatrixSeries("testing", 5, 5);
 
    	jfree = createChart(new MatrixSeriesCollection(leds));
    	
    	panel = new ChartPanel(jfree);
    	
    	
        frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(600, 800);
        frame.add(panel);
        frame.setVisible(true);
    }

    public void populateLEDs(){
    	for (int i = 0; i < 5; i++){
    		for (int j=0; j<5; j++){
    			leds.update(i, j, (Math.random() * 0xffffff));
    		}
    	}
    }
    
    
    /**
     * stolen from XYBlockChartDemo1
     */
    private static JFreeChart createChart(XYZDataset dataset) {
    	NumberAxis xAxis = new NumberAxis("X");
    	xAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
    	xAxis.setLowerMargin(0.0);
    	xAxis.setUpperMargin(0.0);
    	NumberAxis yAxis = new NumberAxis("Y");
    	yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
    	yAxis.setLowerMargin(0.0);
    	yAxis.setUpperMargin(0.0);
    	XYBlockRenderer renderer = new XYBlockRenderer();
    	PaintScale scale = new PaintScale(){

			@Override
			public double getLowerBound() {
				return 0x000000;
			}

			@Override
			public double getUpperBound() {
				return 0xffffff;
			}

			@Override
			public Paint getPaint(double value) {
				String colstr = Integer.toHexString((int)value);
				colstr = "0x"+colstr;
				System.out.println(colstr);
				System.out.println(Color.decode(colstr));
				return Color.decode(colstr);
			}};
    	renderer.setPaintScale(scale);
    	XYPlot plot = new XYPlot(dataset, xAxis, yAxis, renderer);
    	plot.setBackgroundPaint(Color.lightGray);
    	plot.setDomainGridlinesVisible(false);
    	plot.setRangeGridlinePaint(Color.white);
    	JFreeChart chart = new JFreeChart("Brick Wall Simulator", plot);
    	chart.removeLegend();
    	chart.setBackgroundPaint(Color.white);
    	return chart;
    }
    
    
    public static void main (String[] args){
    	Simulator sim = new Simulator();
    	sim.populateLEDs();
    }
}

