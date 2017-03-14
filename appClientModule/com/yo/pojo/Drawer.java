package com.yo.pojo;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;

import com.yo.view.PicturePanel;

public interface Drawer {
	//void draw(Graphics g,int arg1, int arg2, int arg3,int arg4, int arg5, int arg6,int arg7,int arg8, ImageObserver io);
	void draw(Graphics g, Image image, PicturePanel picturePanel);
	public void draw(Graphics g,Component target);
}
