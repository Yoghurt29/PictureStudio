package com.yo.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.geom.RoundRectangle2D;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.UIManager;

import org.springframework.stereotype.Component;

import com.yo.controller.IndexController;
import com.yo.pojo.Drawer;
import com.yo.service.FileService;
//TODO 考慮隱藏圖表來禁止點擊後介面隱藏,以及禁止鼠標移上來,修改鼠標位置
@Component
public class PlayActivity extends JFrame {	
	public Drawer drawer;
/**
 * 
 * @param url
 * @param url
 * @param w 校準寬
 * @param h	校準高
 * @param i	目標輸出寬 CM
 * @param j 目標輸出高CM
 * @param isBorder
 * @param isShowInfo
 * @param startXOfImg 截取圖片部分 的左上角X座標
 * @param startYOfImg
 * @param endXOfImg
 * @param endYOfImg
 */
	public void playPictureWithSet(String url, double w,double h, double i, double j,HashMap imageOutputAttributes) {
		Image image = FileService.getImage(url);
		Dimension outputSize = FileService.getOutputSize(image, w,h, i, j);
		this.getGraphics().clearRect(0, 0, this.getWidth(), this.getHeight());
		int[] location=(int[]) imageOutputAttributes.get(MainActivity.IMAGEOUTPUTATTRIBUTES_CUTLOCATION);
		
		//左上角
		//this.getGraphics().drawImage(image, 0, 0, (int)Math.round(outputSize.getWidth())-1, (int)Math.round(outputSize.getHeight())-1, /*startXOfImg*/location[0], /*startYOfImg*/location[1], /*endXOfImg*/location[2],/*endYOfImg*/location[3],this);
		
		//居中
		this.getGraphics().drawImage(image, (int)Math.round((this.getWidth()-outputSize.getWidth())/2-1), (int)Math.round((this.getHeight()-outputSize.getHeight())/2-1), (int)Math.round((this.getWidth()+outputSize.getWidth())/2-1), (int)Math.round((this.getHeight()+outputSize.getHeight())/2-1),/*startXOfImg*/location[0], /*startYOfImg*/location[1], /*endXOfImg*/location[2],/*endYOfImg*/location[3],this);
		
		Graphics2D g2d=(Graphics2D) this.getGraphics();
		
		if((boolean) imageOutputAttributes.get(MainActivity.IMAGEOUTPUTATTRIBUTES_ISBORDER)){
			//邊框
			g2d.setColor(new Color(255,0,0));  
			Shape shape = null;  
			shape = new RoundRectangle2D.Double(0, 0, (int)Math.round(outputSize.getWidth())-1, (int)Math.round(outputSize.getHeight())-1, 6.5D, 6.5D);  
			g2d.draw(shape); 
		}
		if((boolean) imageOutputAttributes.get(MainActivity.IMAGEOUTPUTATTRIBUTES_ISSHOWINFO)){
			/*Image imageInIo = null;
			try {
				imageInIo=ImageIO.read(new FileInputStream(url));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			double imgMod=imageInIo.getWidth(null)/imageInIo.getHeight(null);
			double actuallyMod=i/j;*/
			g2d.drawString("宽 "+i+" 厘米,高 "+j+" 厘米    ", (int)Math.round(outputSize.getWidth())-1, (int)Math.round(outputSize.getHeight())-1);
		}
		if(!IndexController.isRelase){
			for(int x=1;x<10;x++){
				for(int y=1;y<10;y++){
					g2d.setColor(new Color((int)Math.round(Math.random()*255),(int)Math.round(Math.random()*255),(int)Math.round(Math.random()*255))); 
					g2d.drawString("测试版本!",this.getWidth()-(this.getWidth()*x*10)/100,this.getHeight()-(this.getHeight()*y*10)/100);
				}
			}
		}
	}
	/**
	 * 
	 * @param url
	 * @param w 校準寬
	 * @param h	校準高
	 * @param i	目標輸出寬 CM
	 * @param j 目標輸出高CM
	 * @param isBorder
	 * @param isShowInfo
	 */
	public void playPictureWithSet(String url, double w,double h, double i, double j,boolean isBorder,boolean isShowInfo) {
		Image image = FileService.getImage(url);
		Dimension outputSize = FileService.getOutputSize(image, w,h, i, j);
		this.getGraphics().clearRect(0, 0, this.getWidth(), this.getHeight());
		//左上角
		//this.getGraphics().drawImage(image, 0, 0, (int)Math.round(outputSize.getWidth())-1, (int)Math.round(outputSize.getHeight())-1, 0, 0, image.getWidth(this),image.getHeight(this),this);
		//居中
		this.getGraphics().drawImage(image, (int)Math.round((this.getWidth()-outputSize.getWidth())/2-1), (int)Math.round((this.getHeight()-outputSize.getHeight())/2-1), (int)Math.round((this.getWidth()+outputSize.getWidth())/2-1), (int)Math.round((this.getHeight()+outputSize.getHeight())/2-1), 0, 0, image.getWidth(this),image.getHeight(this),this);
		Graphics2D g2d=(Graphics2D) this.getGraphics();
		
		if(isBorder){
			//邊框
			g2d.setColor(new Color(255,0,0));  
			Shape shape = null;  
			//shape = new RoundRectangle2D.Double(0, 0, (int)Math.round(outputSize.getWidth())-1, (int)Math.round(outputSize.getHeight())-1, 6.5D, 6.5D);  
			shape = new RoundRectangle2D.Double((int)Math.round((this.getWidth()-outputSize.getWidth())/2-1), (int)Math.round((this.getHeight()-outputSize.getHeight())/2-1),Math.round(outputSize.getWidth()),Math.round(outputSize.getHeight()), 6.5D, 6.5D);  
			g2d.draw(shape); 
		}
		if(isShowInfo){
			/*Image imageInIo = null;
			try {
				imageInIo=ImageIO.read(new FileInputStream(url));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			double imgMod=imageInIo.getWidth(null)/imageInIo.getHeight(null);
			double actuallyMod=i/j;*/
			g2d.drawString("寬"+i+"釐米,"+j+"釐米    ",Math.round(outputSize.getWidth()),Math.round(outputSize.getHeight()));
		}
		if(!IndexController.isRelase){
			for(int x=1;x<10;x++){
				for(int y=1;y<10;y++){
					g2d.setColor(new Color((int)Math.round(Math.random()*255),(int)Math.round(Math.random()*255),(int)Math.round(Math.random()*255))); 
					g2d.drawString("测试版本!",this.getWidth()-(this.getWidth()*x*10)/100,this.getHeight()-(this.getHeight()*y*10)/100);
				}
			}
		}
	}
	public void playBackgroundWithColor(final Color color){
		//new Color(R, G, B, 200)
		this.drawer=new Drawer() {
			
			@Override
			public void draw(Graphics g, Image image, PicturePanel picturePanel) {
				
			}
			
			@Override
			public void draw(Graphics g,java.awt.Component c) {
				JFrame target=(JFrame) c;
				int width=target.getWidth();
				int heigth=target.getHeight();
				final int R = 66;  
				final int G = 194;  
				final int B = 110;
				Graphics2D g2d = (Graphics2D) g;  
				// 设置画笔颜色，填充或描边  
				final Paint p = new GradientPaint(0.0f, 0.0f, new Color(R, G, B, 100),  
						50, 50, new Color(R, G, B, 200), true);  
				// 设置画笔颜色为白色  繪製背景色
				g2d.setPaint(p);  
				g2d.setColor(color);  
				g2d.fillRect(0, 0, width, heigth);  
				
				// 设置画笔颜色为蓝色   繪製邊框
				/*g2d.setColor(new Color(41,141,208));  
				Shape shape = null;  
				shape = new RoundRectangle2D.Double(0, 0, width, heigth, 6.5D, 6.5D);  
				g2d.draw(shape);*/ 
			}

		};
		
	}
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
    	this.getGraphics().drawImage(image, 0, 0,image.getWidth(this),image.getHeight(this),Color.black,this);
    	this.getGraphics().drawString("原始比例 "+image.getWidth(this)+","+image.getHeight(this), image.getWidth(this), image.getHeight(this));
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
		this.setLayout(null); 
	        /** 
	         * true无边框 全屏显示 
	         * false有边框 全屏显示 
	         */ 
	        this.setUndecorated(true);
			//this.getRootPane().setbu
			//UIManager.put(this.getRootPane().set;, "false");
	        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); 
	        Rectangle bounds = new Rectangle(screenSize); 
	        this.setBounds(bounds); 
	        //this.setExtendedState(this.MAXIMIZED_BOTH); 
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
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		//super.paint(g);
		if(null!=drawer)
		drawer.draw(g,this);
	}
	@Override
	public void update(Graphics g) {
		drawer.draw(g,this);
	}
	@Override
	public void paintComponents(Graphics g) {
		drawer.draw(g,this);
	}
	public Drawer getDrawer() {
		return drawer;
	}
	public void setDrawer(Drawer drawer) {
		this.drawer = drawer;
	}
}
