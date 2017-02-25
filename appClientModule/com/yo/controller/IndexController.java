package com.yo.controller;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yo.view.MainActivity;
import com.yo.view.PlayActivity;
	@Component
	public class IndexController {
	@Autowired
	private MainActivity mainActivity;
	@Autowired
	private PlayController playController;
	@Autowired
	private PlayActivity playActivity;
	public static ConcurrentHashMap<String,Object> applicationContext = new ConcurrentHashMap();
	static{
		Properties props =new Properties();
		try {
			props.load(IndexController.class.getClassLoader().getResourceAsStream("appConfig.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Set<Object> keySet = props.keySet();
		for (Object key : keySet) {
			applicationContext.put((String) key, props.getProperty((String) key));
		}
	}
	public IndexController() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * 控制端界面
	 */
	public void hideMainActivity(){
		mainActivity.setVisible(false);
	}
	/**
	 * 投影端界面
	 */
	public void showPlayActivity(){
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();//获得图形环境
        GraphicsDevice[] graphicsDevices = ge.getScreenDevices();//获得图形设备，即显示器，以数组形式作为返回值   
        System.out.println("\tdebug: "+graphicsDevices.length+"個顯示器被發現!投影到介面1");
        if(graphicsDevices.length>1){
        	graphicsDevices[1].setFullScreenWindow(playActivity);
        }
	}
	
	
	public MainActivity getMainActivity() {
		return mainActivity;
	}
	public void setMainActivity(MainActivity mainActivity) {
		this.mainActivity = mainActivity;
	}
	public PlayActivity getPlayActivity() {
		return playActivity;
	}
	public void setPlayActivity(PlayActivity playActivity) {
		this.playActivity = playActivity;
	}
	
}
