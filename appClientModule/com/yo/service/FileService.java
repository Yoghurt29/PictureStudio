package com.yo.service;

import java.awt.Dimension;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;

import com.yo.controller.IndexController;

@Component
public class FileService {
	//靜態臨時文件夾,默認打開文件夾,自動記錄的上次打開的文件夾
	public FileService() {
		// TODO Auto-generated constructor stub
	}
	//轉換任意文件為jpg,放在文件夾下,保持原始尺寸,返回路徑
	/**
	 * 將給定路徑文件轉換為jpg 輸出(覆蓋)到work路徑下 並返回輸出文件的絕對路徑
	 * @param absolutePath	輸入文件路徑
	 * @param outFileName	輸出文件名
	 * @return	輸出文件的絕對路徑
	 */
	public String nconvertToJpg(String absolutePath,String outFileName) {
		outFileName=UUID.randomUUID()+outFileName;
		//String cmdarray="d:\\Users\\Trulon_chu\\gitRepository\\PictureStudiou\\XnView\\nconvert.exe -o "+outFileName+" -out jpeg -truecolors "+absolutePath;
		String cmdarray=IndexController.nconvertFloder+"\\nconvert.exe -o "+IndexController.workFloder+"\\"+outFileName+" -out jpeg -truecolors "+absolutePath;
		if(absolutePath.endsWith(".ai")){
			cmdarray=IndexController.gsFloder+"\\gswin32c.exe -sDEVICE=jpeg -sOutputFile="+IndexController.workFloder+"\\"+outFileName+" -dBATCH -dNOPAUSE "+absolutePath;
		}
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
	//自動裁剪,去掉多餘像素
	public static String nconvertAutoCrop(String absolutePath,String outFileName) {
		outFileName=UUID.randomUUID()+outFileName;
		String outFilePath=new File(IndexController.workFloder,outFileName).getAbsolutePath();
		File oldFile=new File(outFilePath);
		if(oldFile.exists()){
			boolean delete = oldFile.delete();
			System.out.println("\tdebug: 文件移除 "+delete+","+outFilePath);
		}
		//String cmdarray="d:\\Users\\Trulon_chu\\gitRepository\\PictureStudiou\\XnView\\nconvert.exe -o "+outFileName+" -out jpeg -truecolors "+absolutePath;
		String cmdarray=IndexController.nconvertFloder+"\\nconvert.exe -autocrop 0 255 255 255 -o "+IndexController.workFloder+"\\"+outFileName+" -out png -truecolors "+absolutePath;
		if(absolutePath.endsWith(".ai")){
			cmdarray=IndexController.gsFloder+"\\gswin32c.exe -sDEVICE=png -sOutputFile="+IndexController.workFloder+"\\"+outFileName+" -dBATCH -dNOPAUSE "+absolutePath;
		}
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
		return outFilePath;		
	}
	//轉換任意文件為png,放在work文件夾下,保持原始尺寸,返回路徑
	/**
	 * 將給定路徑文件轉換為png 輸出(覆蓋)到work路徑下 並返回輸出文件的絕對路徑
	 * @param absolutePath	輸入文件路徑
	 * @param outFileName	輸出文件名
	 * @return	輸出文件的絕對路徑
	 */
	public String nconvertToPng(String absolutePath,String outFileName) {
		outFileName=UUID.randomUUID()+outFileName;
		String outFilePath=new File(IndexController.workFloder,outFileName).getAbsolutePath();
		File oldFile=new File(outFilePath);
		if(oldFile.exists()){
			oldFile.delete();
			System.out.println("\tdebug: 文件已移除 "+outFilePath);
		}
		//String cmdarray="d:\\Users\\Trulon_chu\\gitRepository\\PictureStudiou\\XnView\\nconvert.exe -o "+outFileName+" -out jpeg -truecolors "+absolutePath;
		String cmdarray=IndexController.nconvertFloder+"\\nconvert.exe -o "+IndexController.workFloder+"\\"+outFileName+" -out png -truecolors "+absolutePath;
		if(absolutePath.endsWith(".ai")){
			return this.nconvertToJpg(absolutePath, outFileName);
			//cmdarray=IndexController.gsFloder+"\\gswin32c.exe -sDEVICE=png -sOutputFile="+IndexController.workFloder+"\\"+outFileName+" -dBATCH -dNOPAUSE "+absolutePath;
		}
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
		return outFilePath;
	}
	/**
	 * 根據實際投影儀,校準時設定的參數,獲取輸出尺寸
	 * 1.向地面輸出 800*450 像素的畫面,用戶測得實際尺寸如 4m * 2.25m
	 * 2.用戶指定輸出  如寬2m 高1m 則計算出 寬2m,高1m 所對應的像素
	 * @param img
	 * @param setValue	校準參數   存儲的是800*450 投影到地面的實際尺寸 單位 釐米
	 * @param outputWidth	目標輸出尺寸 單位米
	 * @return
	 */
	static public Dimension getOutputSize(Image img,double setValueWidth,double setValueHeight,double outputWidth,double outputHeight){
		Dimension size=new Dimension();
		double pixPerCmOfWidth=800.0/setValueWidth;
		double pixPerCmOfHeight=450.0/setValueHeight;
		size.setSize(outputWidth*pixPerCmOfWidth, outputHeight*pixPerCmOfHeight);
		return size;
	}
	/**
	 * 根據畫布[像素尺寸]大小,得到保持比例,最大可填充的尺寸參數
	 * @param img
	 * @param width 畫布寬
	 * @param height 畫布高
	 * @return
	 */
	static public Dimension getAdaptContainerSize(Image img,int width,int height){
		double imgWidth=1.0*img.getWidth(null);
		double imgHeight=1.0*img.getHeight(null);
		double n1=imgWidth/imgHeight;
		double n2=1.0*width/height;
		Dimension size=new Dimension();
		if(n1<n2){
			size.setSize(1.0*imgWidth*(height/imgHeight), height);
		}else{
			size.setSize(1.0*width,imgHeight*(width/imgWidth));
		}
		return size;
	}
	static public Image getImage(String imgPath){
        Image  image= null;
		try {
			image = ImageIO.read(new FileInputStream(imgPath));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return image;
	}
}
