package com.yo.view;

import java.awt.Dimension;
import java.awt.Event;
import java.awt.FlowLayout;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import org.springframework.stereotype.Component;
@Component
public class PlayActivity extends JFrame {	
	@Override
	protected void processWindowEvent(WindowEvent e) {
		System.out.println(e.toString());
		if(e.getID()==WindowEvent.WINDOW_DEACTIVATED){
			System.out.println("PlayActivity 長期活動,禁止被置為DEACTIVED!");
		}else{
			super.processWindowEvent(e);
		}
	}
	@Override
	protected void frameInit() {
		super.frameInit();
		this.setLayout(new FlowLayout()); 
	        /** 
	         * true无边框 全屏显示 
	         * false有边框 全屏显示 
	         */ 
	        this.setUndecorated(true); 
	        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); 
	        Rectangle bounds = new Rectangle(screenSize); 
	        this.setBounds(bounds); 
	        this.setExtendedState(this.MAXIMIZED_BOTH); 
	        this.setAlwaysOnTop(true);
	        
	}
	public PlayActivity() throws HeadlessException {
		// TODO Auto-generated constructor stub
	}

	public PlayActivity(GraphicsConfiguration gc) {
		super(gc);
		// TODO Auto-generated constructor stub
	}

	public PlayActivity(String title) throws HeadlessException {
		super(title);
		// TODO Auto-generated constructor stub
	}

	public PlayActivity(String title, GraphicsConfiguration gc) {
		super(title, gc);
		// TODO Auto-generated constructor stub
	}

}
