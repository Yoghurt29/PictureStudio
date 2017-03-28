package com.yo.controller;

import java.awt.Color;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class OpenCVUtil {
	static String PROCESS_CHAGEBACKCOLORANDEDGECOLORANDGETEDGE = "_chageBackColorAndEdgeColorAndGetEdge";
	static String PROCESS_GETEDGE="_getEdge";
	static String PROCESS_CHAGEBACKCOLORANDEDGECOLOR="_chageBackColorAndEdgeColor";
	static{
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);  
	}
	public OpenCVUtil() {
		// TODO Auto-generated constructor stub
	}
	public static void main(String[] args) {
		Mat mat = Imgcodecs.imread("D:/PictureStudio/orange.jpg");
		Mat mat2 = doCanny(mat);
		Imgcodecs.imwrite("D:/PictureStudio/ai_doCanny.bmp", mat2);
		Mat mat3 = colorReplace(mat2,null,null);
		Imgcodecs.imwrite("D:/PictureStudio/ai_replaseColor.bmp", mat3);
	}
	public static String chageBackColorAndEdgeColorAndGetEdge(String inputPath,Color edgeColor,Color backColor){
		Mat matIn = Imgcodecs.imread(inputPath);
		Mat mat1 = doCanny(matIn);
		Mat mat2 = colorReplace(mat1,edgeColor,backColor);
		Imgcodecs.imwrite(inputPath+OpenCVUtil.PROCESS_CHAGEBACKCOLORANDEDGECOLORANDGETEDGE, mat2);
		return inputPath+OpenCVUtil.PROCESS_CHAGEBACKCOLORANDEDGECOLORANDGETEDGE;
	}
	public static String getEdge(String inputPath){
		Mat matIn = Imgcodecs.imread(inputPath);
		Mat matOut = doCanny(matIn);
		Imgcodecs.imwrite(inputPath+OpenCVUtil.PROCESS_GETEDGE, matOut);
		return inputPath+OpenCVUtil.PROCESS_GETEDGE;
	}
	public static String chageBackColorAndEdgeColor(String inputPath,Color edgeColor,Color backColor){
		Mat matIn = Imgcodecs.imread(inputPath);
		Mat matOut = colorReplace(matIn,edgeColor,backColor);
		Imgcodecs.imwrite(inputPath+OpenCVUtil.PROCESS_CHAGEBACKCOLORANDEDGECOLOR, matOut);
		return inputPath+OpenCVUtil.PROCESS_CHAGEBACKCOLORANDEDGECOLOR;
	}
    private static Mat doCanny(Mat frame)
    {
            // init
            Mat grayImage = new Mat();
            Mat detectedEdges = new Mat();
           
            // convert to grayscale
            Imgproc.cvtColor(frame, grayImage, Imgproc.COLOR_BGR2GRAY);
           
            // reduce noise with a 3x3 kernel
            Imgproc.blur(grayImage, detectedEdges, new Size(3, 3));
           
            // canny detector, with ratio of lower:upper threshold of 3:1
            Imgproc.Canny(detectedEdges, detectedEdges, 50, 50 * 3);
           
            // using Canny's output as a mask, display the result
            Mat dest = new Mat();
            frame.copyTo(dest, detectedEdges);
           
            return dest;
    }
    /*public static Mat colorReplace(Mat imgSrc){
        for (int y = 0; y < imgSrc.height(); y++)
        {
            for (int x = 0; x < imgSrc.width(); x++)
            {
                //得到该行像素点的值
                    double[] data = imgSrc.get(y,x);
                    for (int i1 = 0; i1 < data.length; i1++) {
                        if(data[i1]>10)
                    	data[i1] = 255;//像素点都改为白色
                    }
                    imgSrc.put(y,x,data);
            }
        }
        return imgSrc;
    }*/
    public static Mat colorReplace(Mat imgSrc,Color edgeColor,Color backColor){
    	for (int y = 0; y < imgSrc.height(); y++)
    	{
    		for (int x = 0; x < imgSrc.width(); x++)
    		{
    			//得到该行像素点的值
    			double[] data = imgSrc.get(y,x);
    			if(data[0]==0&&data[1]==0&&data[2]==0){
    				if(null!=backColor){
    					data[0]=backColor.getRed();
    					data[1]=backColor.getGreen();
    					data[2]=backColor.getBlue();
    				}
    			}else{
    				if(null!=edgeColor){
    					data[0]=edgeColor.getRed();
    					data[1]=edgeColor.getGreen();
    					data[2]=edgeColor.getBlue();
    				}
    			}
    			imgSrc.put(y,x,data);
    		}
    	}
    	return imgSrc;
    }
    public void boldPoint(Mat imgSrc,List<double[]> points){
    	for (double[] ds : points) {
			
		}
    }
}
