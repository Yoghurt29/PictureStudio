package com.yo.controller;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.File;

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
	public void palyPicture(String pictureUrl){
		playActivity.playPicture(pictureUrl);
	}
	//C:\Windows\system32\displayswitch.exe \extend
	public void showPlayActivity(){
        //TODO 遍曆觀察設備列表,調用playController的paly方法
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();//获得图形环境
        GraphicsDevice[] graphicsDevices = ge.getScreenDevices();//获得图形设备，即显示器，以数组形式作为返回值   
        System.out.println("\tdebug: "+graphicsDevices.length+"個顯示器被發現!投影到介面1");
        //playActivity.playPicture("D:\\Users\\Trulon_Chu\\Desktop\\XnView\\test_1920_1080.jpg");
        if(graphicsDevices.length>1){
        	graphicsDevices[1].setFullScreenWindow(playActivity);
        }
        playActivity.playPicture("D:\\Users\\Trulon_Chu\\Desktop\\XnView\\test.jpg");
	}
}
