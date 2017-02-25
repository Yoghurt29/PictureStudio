package com.yo.main;
import javax.swing.JFrame;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Main {

	public Main() {
		super();
	}
	public static void main(String[] args) {
		ApplicationContext ctx=new ClassPathXmlApplicationContext("applicationContext.xml");
		JFrame mainActivity = (JFrame) ctx.getBean("mainActivity");
        mainActivity.setVisible(true);
	}
}