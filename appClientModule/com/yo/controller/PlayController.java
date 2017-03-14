package com.yo.controller;

import java.awt.Color;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.io.File;
import java.util.Date;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yo.view.MainActivity;
import com.yo.view.PlayActivity;

@Component
public class PlayController {
	@Autowired
	private PlayActivity playActivity;
	//TODO 播放隊列,提供上一張,下一張功能,在mainActivity可見
	//TODO 設備列表,觀察者模式,允許註冊設備
	public PlayController() {
		// TODO Auto-generated constructor stub
	}
	public void playBackgroundWithColor(Color c){
		//new Color(200, 0, 0, 200)
		playActivity.playBackgroundWithColor(c);
	}
	public void palyPicture(String pictureUrl){
		playActivity.playPicture(pictureUrl);
	}
	public void showPlayActivity(){
		long startTime=new Date().getTime();
		long endTime=new Date().getTime();
		while(endTime-startTime<5*1000){
			endTime=new Date().getTime();
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();//获得图形环境
			GraphicsDevice[] graphicsDevices = ge.getScreenDevices();//获得图形设备，即显示器，以数组形式作为返回值   
			System.out.println("\tdebug: "+graphicsDevices.length+"個顯示器被發現!投影到介面1");
			//playActivity.playPicture("D:\\Users\\Trulon_Chu\\Desktop\\XnView\\test_1920_1080.jpg");
			if(graphicsDevices.length>1){
				graphicsDevices[1].setFullScreenWindow(playActivity);
				return;
			}
		}
		Toolkit.getDefaultToolkit().beep();
		JOptionPane.showMessageDialog(null, "未找到投影設備", "請將電腦連接倒到投影儀!", JOptionPane.WARNING_MESSAGE);
        //playActivity.playPicture("D:\\Users\\Trulon_Chu\\Desktop\\XnView\\test.jpg");
	}
}
