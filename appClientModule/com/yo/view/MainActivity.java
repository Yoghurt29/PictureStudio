package com.yo.view;

import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Toolkit;
import javax.swing.JMenuBar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yo.controller.IndexController;
import com.yo.service.FileService;

import javax.swing.JMenu;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.JMenuItem;
@Component
public class MainActivity extends JFrame {
	@Autowired
	private IndexController indexController;
	@Override
	protected void frameInit() {
		super.frameInit();
		this.setUndecorated(false); 
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); 
        Rectangle bounds = new Rectangle(screenSize); 
        this.setBounds(bounds); 
        this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
	}
	public MainActivity() throws HeadlessException {
		setTitle(Messages.getString("mainActivityTitle")); //$NON-NLS-1$
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainActivity.class.getResource("/ico/icon.png")));
		getContentPane().setLayout(null);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 434, 21);
		getContentPane().add(menuBar);
		
		JMenu menu = new JMenu(Messages.getString("MainActivity.menu.text")); //$NON-NLS-1$
		menuBar.add(menu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem(Messages.getString("MainActivity.mntmNewMenuItem.text")); //$NON-NLS-1$
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jFileChooser=new JFileChooser();
				jFileChooser.showDialog(new JLabel(), "选择");  
		        File file=jFileChooser.getSelectedFile();  
		        if(file.isDirectory()){
		        	System.out.println("文件夹:"+file.getAbsolutePath()); 
		        	//String rawUrl=file.getAbsolutePath();
		        	//indexController.getPlayController().palyPicture(rawUrl);
		        	//String url=indexController.getFileService().getModifiedPicturePath(file.getAbsolutePath());
		        	//indexController.getPlayController().palyPicture(url);
		        }else if(file.isFile()){  
		        	System.out.println("文件:"+file.getAbsolutePath());  
		        	//String rawUrl=file.getAbsolutePath();
		        	//indexController.getPlayController().palyPicture(rawUrl);
		        	String url=indexController.getFileService().nconvertToJpg(file.getAbsolutePath(),file.getName().split("\\.")[0]+".jpg");
		        	indexController.getPlayController().palyPicture(url);
		        }  
			}
		});
		menu.add(mntmNewMenuItem);
		
		JMenu menu_2 = new JMenu(Messages.getString("MainActivity.menu_2.text")); //$NON-NLS-1$
		menuBar.add(menu_2);
		
		JMenu menu_3 = new JMenu(Messages.getString("MainActivity.menu_3.text")); //$NON-NLS-1$
		menuBar.add(menu_3);
		
		JButton btnTestbtn = new JButton(Messages.getString("MainActivity.btnTestbtn.text")); //$NON-NLS-1$
		btnTestbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				indexController.showPlayActivity();
			}
		});
		btnTestbtn.setBounds(0, 20, 87, 23);
		getContentPane().add(btnTestbtn);
		// TODO Auto-generated constructor stub
	}

	public MainActivity(GraphicsConfiguration gc) {
		super(gc);
	}

	public MainActivity(String title) throws HeadlessException {
		super(title);
		// TODO Auto-generated constructor stub
	}

	public MainActivity(String title, GraphicsConfiguration gc) {
		super(title, gc);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		super.dispose();
		System.out.println("dispose");
	}
}
