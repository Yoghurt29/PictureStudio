package com.yo.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Paint;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.springframework.stereotype.Component;

import com.yo.pojo.Drawer;
import com.yo.service.FileService;
/**
 *	繪製圖像,使用時候,首先調用setDraw(new Draw()...) 再調用setImage(..) 即可在該panel上按指定draw方法展示圖片
 * @author Trulon_Chu
 *
 */
public class PicturePanel extends JPanel{

	private static final long serialVersionUID = 1L;
	
	public PicturePanel() {
		// TODO Auto-generated constructor stub
	}
	private Image image;
	private Drawer drawer;
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
    @Override  
    public void paintComponent(Graphics g) {  
    	if (null == image) {  
    		return;  
    	}  
        drawer.draw(g,image,this);
    }

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
	public Drawer getDrawer() {
		return drawer;
	}
	public void setDrawer(Drawer drawer) {
		this.drawer = drawer;
	}
	public PicturePanel(Image image, Drawer drawer) {
		super();
		this.image = image;
		this.drawer = drawer;
	}  
}
