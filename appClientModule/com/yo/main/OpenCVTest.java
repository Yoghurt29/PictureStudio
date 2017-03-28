package com.yo.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

public class OpenCVTest {

	public OpenCVTest() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		//Files.copy(new FileInputStream(new File("D:\\Users\\Trulon_Chu\\Desktop\\JavaOpenCV246\\libs")),Paths.get("C:\\PictureStudio\\OpenCV"));  
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );  
	      Mat mat = Mat.eye( 3, 3, CvType.CV_8UC1 );  
	      System.out.println( "mat = " + mat.dump() );
	}

}
