package com.yo.controller;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yo.service.FileService;
import com.yo.view.MainActivity;
import com.yo.view.PlayActivity;
/**
 * 1.playActivity未打開 空指針
 * @author Trulon_Chu
 *
 */
	@Component
	public class IndexController {
	@Autowired
	private FileService fileService;
	@Autowired
	private MainActivity mainActivity;
	@Autowired
	private PlayController playController;
	@Autowired
	private PlayActivity playActivity;
	public static ConcurrentHashMap<String,Object> applicationConfig = new ConcurrentHashMap();
	public static File workFloder;
	public static File nconvertFloder;
	static{
		URL resource = IndexController.class.getClassLoader().getResource("./");
		File rootfloder=new File(resource.getFile()).getParentFile().getParentFile();
		IndexController.workFloder=new File(rootfloder.toString(),"work");
		IndexController.nconvertFloder=new File(rootfloder.toString(),"XnView");
		if(!IndexController.workFloder.exists()){
			IndexController.workFloder.mkdir();
		}
		//URL resource1 = IndexController.class.getClassLoader().getResource("work");
		System.out.println(rootfloder.toString());
		//初始化配置文件到內存map
		Properties props =new Properties();
		try {
			props.load(IndexController.class.getClassLoader().getResourceAsStream("applicationConfig.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Set<Object> keySet = props.keySet();
		for (Object key : keySet) {
			applicationConfig.put((String) key, props.getProperty((String) key));
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
		this.playController.showPlayActivity();
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
	public FileService getFileService() {
		return fileService;
	}
	public void setFileService(FileService fileService) {
		this.fileService = fileService;
	}
	public PlayController getPlayController() {
		return playController;
	}
	public void setPlayController(PlayController playController) {
		this.playController = playController;
	}
	
}
