package com.yo.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import org.springframework.stereotype.Component;
public class PicturePanel extends JPanel{

	/**
	 * 禁用!
	 */
	private static final long serialVersionUID = 1L;
	
	public PicturePanel() {
		// TODO Auto-generated constructor stub
	}
	private Image image;  
    public void setImagePath(String imgPath) {  
        // 该方法不推荐使用，该方法是懒加载，图像并不加载到内存，当拿图像的宽和高时会返回-1；  
        // image = Toolkit.getDefaultToolkit().getImage(imgPath);  
        try {  
            // 该方法会将图像加载到内存，从而拿到图像的详细信息。  
            image = ImageIO.read(new FileInputStream(imgPath));  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }
    }  
   /**
    *	繪製圖像帶大小的Jpanel,在其上填充圖像 
    */
    @Override  
    public void paintComponent(Graphics g) {  
    	if (null == image) {  
    		return;  
    	}  
        this.setPreferredSize(new Dimension(image.getWidth(this),image.getHeight(this)));
        this.setLocation(0, 0);  
        g.drawImage(image, 0, 0, image.getWidth(this), image.getHeight(this),this); 
        System.out.println("\tdebug: Jpanel尺寸: "+this.getSize().getWidth()+","+this.getSize().getHeight());
        System.out.println("\tdebug: Jpanel位置: "+this.getLocation().getX()+","+this.getLocation().getY());
        System.out.println("\tdebug: Jpanel圖像尺寸: "+image.getWidth(this)+","+image.getHeight(this));
    }

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}  
    
}
