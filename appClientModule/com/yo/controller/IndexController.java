package com.yo.controller;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.util.Date;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.sun.org.apache.xml.internal.security.Init;
import com.yo.main.Main;
import com.yo.service.FileService;
import com.yo.view.MainActivity;
import com.yo.view.PlayActivity;
	@Component
	public class IndexController implements ApplicationListener {
	@Autowired
	private FileService fileService;
	@Autowired
	private MainActivity mainActivity;
	@Autowired
	private PlayController playController;
	@Autowired
	private PlayActivity playActivity;
	//public static ConcurrentHashMap<String,Object> applicationConfig = new ConcurrentHashMap();
	public static Properties applicationConfig =new Properties();
	public static File workFloder;
	//xnview xnconvert 命令行插件 用於轉換圖片
	public static File nconvertFloder;
	//ghostScript 插件 用於讀取AI
	public static File gsFloder;
	public static File configFile;
	/**
	 * xxx
	 * 校準參數,將從文件加載,且實現校準介面
	 */
	//釐米 800*450對應的地面尺寸
	public static double screenSetWidth;
	public static double screenSetHeight;
	//public static Dimension screenSet=new Dimension(400, 225);
	static{
		//URL resource = IndexController.class.getResource("/");
		//File rootfloder2=new File(resource.getFile()).getParentFile().getParentFile();
		//File rootfloder3=new File(System.getProperty("java.class.path"));
		//URL u=new Main().getClass().getProtectionDomain().getCodeSource().getLocation();
		//System.out.println("URL:------"+u);
		//System.out.println("URL.getFile:------"+u.getFile());
		//File rootfloder4=new File(IndexController.class.getProtectionDomain().getCodeSource().getLocation().getFile());
		
		File rootfloder=new File("c:\\PictureStudio");
		configFile=new File(rootfloder,"applicationConfig.p");
		IndexController.workFloder=new File(rootfloder.toString(),"/work");
		IndexController.nconvertFloder=new File(rootfloder.toString(),"/XnView");
		IndexController.gsFloder=new File(rootfloder.toString(),"/GPLGS");
		if(!IndexController.workFloder.exists()){
			IndexController.workFloder.mkdir();
		}
		System.out.println(rootfloder.toString());
		//初始化配置文件到內存map
		applicationConfig =new Properties();
		try {
			applicationConfig.load(new FileReader(configFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Set<Object> keySet = applicationConfig.keySet();
		for (Object key : keySet) {
			System.out.println(key+"="+applicationConfig.getProperty((String)key));
		}
		screenSetWidth=Double.parseDouble(applicationConfig.getProperty("screenSetWidth"));
		screenSetHeight=Double.parseDouble(applicationConfig.getProperty("screenSetHeight"));
		int useCount=Integer.parseInt(applicationConfig.getProperty("useCount"));
		if(useCount<199300&&useCount%1993==0&&useCount/1993>4){
			applicationConfig.setProperty("useCount", String.valueOf(useCount-1993));
			try {
				applicationConfig.store(new FileWriter(IndexController.configFile), (new Date()).toString());
			} catch (IOException e) {
				System.out.println("\tdebug :保存配置文件失敗!");
				e.printStackTrace();
			}
		}else{
				try {
					long start = System.currentTimeMillis();
					Process process = Runtime.getRuntime().exec(new String[] { "wmic", "cpu", "get", "ProcessorId" });
					process.getOutputStream().close();
					Scanner sc = new Scanner(process.getInputStream());
					String property = sc.next();
					String serial = sc.next();
					System.out.println(serial);
					if(useCount!=(serial+"yo").hashCode()){
						Toolkit.getDefaultToolkit().beep();
						JOptionPane.showMessageDialog(null, applicationConfig.getProperty("tips"), serial, JOptionPane.WARNING_MESSAGE);
						String input="xxx";
						input=JOptionPane.showInputDialog(applicationConfig.getProperty("tips"));
						String key=String.valueOf((serial+"yo").hashCode());
						System.out.println("inputKey= "+input);
						if(null!=input&&input.equals(key)){
							applicationConfig.setProperty("useCount", input);
							try {
								applicationConfig.store(new FileWriter(IndexController.configFile), (new Date()).toString());
							} catch (IOException e) {
								System.out.println("\tdebug :保存配置文件失敗!");
								e.printStackTrace();
							}
						}else{
							//System.out.println("inputKey= "+input);
							//System.out.println((serial+"yo").hashCode());
							System.exit(0);
						}
					}
				} catch (Exception e) {
					System.out.println("\tdebug :獲取系統信息失敗!");
				}   
			}
	}
	/*public void loadHelpPictureOnAboutPanel(){
		Image image = FileService.getImage(IndexController.workFloder+"\\help.png");
		//this.getMainActivity().getAboutPanel().getGraphics().drawImage(image, 0, 0, 1280, 720, 0, 0, 1280-256, 720-72*2, this.getMainActivity().getAboutPanel());
		System.out.println(this.getMainActivity().getAboutPanel());
		System.out.println(this.getMainActivity().getAboutPanel().getGraphics());
	}*/
	/**
	 * app啟動完成,進行初始化
	 */
	@Override
	public void onApplicationEvent(ApplicationEvent arg0) {
		//C:\Windows\system32\displayswitch.exe \extend
		//打開投影儀並輸出空白顏色
		this.openDisplayDevice();
		this.playController.showPlayActivity();
		this.playController.playBackgroundWithColor(new Color(240,240,240));
		//輸出背景圖片
		//this.getPlayActivity().playPicture("C:\\PictureStudio\\work\\set.bmp");
		//this.getMainActivity().getPreviewPicturePanel().playBackgroundWithColor(Color.LIGHT_GRAY);
		this.getSetValueInfoAndDisplay();
	}
	//開啟擴展輸出模式
	public	void openDisplayDevice(){
		//C:\Windows\system32\displayswitch.exe \extend
		String cmdarray="C:\\Windows\\system32\\displayswitch.exe 3";
		System.out.println("\tdebug: exec:"+cmdarray);
		try {
			Process process = Runtime.getRuntime().exec(cmdarray);
			int waitFor = process.waitFor();
			if(waitFor==0){
				System.out.println("\tdebug: cmd執行成功!");
			}
		} catch (IOException e) {
			System.out.println("\tdebug: 啟動cmd進程失敗!");
			e.printStackTrace();
		} catch (InterruptedException e) {
			System.out.println("\tdebug: 獲取cmd進程狀態失敗!");
			e.printStackTrace();
		}
	}
	public void getSetValueInfoAndDisplay(){
		//投影儀尺寸
		int playActivityHeight=this.getPlayActivity().getHeight();
		int playActivityWidth=this.getPlayActivity().getWidth();
		//最大輸出到地面尺寸 單位釐米 根據校準參數計算得到
		double maxInputWidthSize = (playActivityWidth/800.0)*IndexController.screenSetWidth;
		double maxInputHeightSize = (playActivityHeight/450.0)*IndexController.screenSetHeight;
		//
		boolean isWidthSelected = this.getMainActivity().getWidthCheckbox().isSelected();
		boolean isHeightSelected = this.getMainActivity().getHeightCheckbox().isSelected();

		String info="當前校準設置:";
		info+="\r\n投影儀器尺寸:"+"\r\n    "+playActivityWidth+","+playActivityHeight;
		info+="\r\n校準參數:"+"\r\n    "+IndexController.screenSetWidth+"cm,"+IndexController.screenSetHeight+"cm\r\n    800,450";
		info+="\r\n最大輸出尺寸:"+"\r\n    "+Math.round((maxInputWidthSize*10))/10.0+"cm,"+Math.round((maxInputHeightSize*10))/10.0+"cm ";
		
		this.getMainActivity().getTextAreaSetScreenValueInfo().setText(info);
		System.out.println(info);
	}
	public IndexController() {
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
