package com.yo.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.FlowLayout;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
//TODO 考慮隱藏圖表來禁止點擊後介面隱藏,以及禁止鼠標移上來,修改鼠標位置
@Component
public class PlayActivity extends JFrame {	
    public void playPicture(String imgPath) {  
        //picturePanel.setImagePath(imgPath);  
        //實際展示尺寸,根據品目尺寸計算
       /*Toolkit kit = Toolkit.getDefaultToolkit(); 
        Dimension screenSize = kit.getScreenSize();  
        int screenHeight = screenSize.height;  
        int screenWidth = screenSize.width;*/ 
        Image image = null;
		try {
			image = ImageIO.read(new FileInputStream(imgPath));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	this.getGraphics().drawImage(image, 0, 0,image.getWidth(this),image.getWidth(this),Color.blue,this);
    	this.getGraphics().drawString("原始比例", 100, 100);
    	this.getGraphics().drawString("hello", 200, 100);
    	this.getGraphics().drawString("java", 300, 100);
    }  
	@Override
	protected void processWindowEvent(WindowEvent e) {
		if(e.getID()==WindowEvent.WINDOW_DEACTIVATED){
			System.out.println("\tdebug: PlayActivity 長期活動,阻止DEACTIVED");
		}else{
			System.out.println(e.toString());
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
