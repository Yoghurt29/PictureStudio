package com.yo.service;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Component;

import com.yo.controller.IndexController;

@Component
public class FileService {
	//靜態臨時文件夾,默認打開文件夾,自動記錄的上次打開的文件夾
	public FileService() {
		// TODO Auto-generated constructor stub
	}
	//轉換任意文件為jpg,放在work文件夾下,保持原始尺寸,返回路徑
	public String nconvertToJpg(String absolutePath,String outFileName) {
		//String cmdarray="d:\\Users\\Trulon_chu\\gitRepository\\PictureStudiou\\XnView\\nconvert.exe -o "+outFileName+" -out jpeg -truecolors "+absolutePath;
		String cmdarray=IndexController.nconvertFloder+"\\nconvert.exe -o "+IndexController.workFloder+"\\"+outFileName+" -out jpeg -truecolors "+absolutePath;
		System.out.println("\tdebug: exec:"+cmdarray);
		absolutePath=new File(IndexController.workFloder,outFileName).getAbsolutePath();
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
		return absolutePath;
	}
	
}
