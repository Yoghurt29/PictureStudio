package com.yo.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Panel;
import java.awt.Rectangle;
import java.awt.Shape;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Toolkit;
import javax.swing.JMenuBar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yo.controller.IndexController;
import com.yo.controller.OpenCVUtil;
import com.yo.pojo.Drawer;
import com.yo.service.FileService;

import javax.swing.JMenu;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.awt.event.ActionEvent;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Button;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;

import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import java.awt.SystemColor;
import javax.swing.border.EtchedBorder;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.border.BevelBorder;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.FontUIResource;
import javax.swing.event.ChangeEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JToggleButton;
import javax.swing.JRadioButton;
import java.awt.event.MouseMotionAdapter;
@Component
public class MainActivity extends JFrame {
	protected static final String IMAGEOUTPUTATTRIBUTES_EDGECOLOR = "edgeColor";
	protected static final String IMAGEOUTPUTATTRIBUTES_ISGETEDGE = null;
	static public String IMAGEOUTPUTATTRIBUTES_ISBORDER="isBorder";
	static public String IMAGEOUTPUTATTRIBUTES_ISSHOWINFO="isShowInfo";
	static public String IMAGEOUTPUTATTRIBUTES_ISCUT="isCut";
	static public String IMAGEOUTPUTATTRIBUTES_CUTLOCATION="cutLocation";
	@Autowired
	private IndexController indexController;
	private PicturePanel previewPicturePanel=null;
	//寬
	private JTextField textField;
	//高
	private JTextField textField_1;
	private	JCheckBox widthCheckbox;
	private	JCheckBox heightCheckbox;
	public String activeImagePath;
	public Image activeImage;
	//存放輸出設置,包括裁剪位置,opvc參數等
	public HashMap<String, Object> imageOutputAttributes =new HashMap<String,Object>();
	private JTextArea textAreaSetScreenValueInfo;
	private JTextField textField_2;
	private JTextField textField_3;
	private JToggleButton isCropButton;
	private JCheckBox isBorderCheckBox;
	private JCheckBox isShowPictureInfoCheckBox;
	private JPanel aboutPanel;
	private JButton outputButton;

	@Override
	protected void frameInit() {
		super.frameInit();
		//this.setUndecorated(false); 
        /*Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); 
        Rectangle bounds = new Rectangle(screenSize); 
        this.setBounds(bounds); 
        this.setExtendedState(JFrame.MAXIMIZED_BOTH); */
	}
	/**
	 * 輸出圖片到投影儀
	 * @param imageUrl
	 * @param setValue	校準參數
	 * @param outputWidth
	 * @param outputHeight
	 */
	public void drawImageToPreviewPicturePanelWithSet(String imageUrl,final double setValueWidth,final double setValueHeight,final double outputWidth,final double outputHeight,final boolean isBorder,final boolean isShowInfo){
		previewPicturePanel.setDrawer(new Drawer() {
			@Override
			public void draw(Graphics g,Image image,PicturePanel panel) {
				Dimension outputSize = FileService.getOutputSize(image, setValueWidth,setValueHeight, outputWidth, outputHeight);
				
				Graphics2D g2d = (Graphics2D) g;  
				// 设置画笔颜色，填充或描边  
				final Paint p = new GradientPaint(0.0f, 0.0f,Color.BLACK,  
						50, 50, Color.BLACK, true);  
				// 设置画笔颜色为白色  繪製背景色
				g2d.setPaint(p);  
				g2d.setColor(Color.BLACK);  
				g2d.fillRect(0, 0, previewPicturePanel.getWidth(),previewPicturePanel.getHeight());				
				
				g.drawImage(image, 0, 0, (int)Math.round(outputSize.getWidth())-1, (int)Math.round(outputSize.getHeight())-1, 0, 0, image.getWidth(panel),image.getHeight(panel),panel);

				if(isBorder){
					//邊框
					g2d.setColor(new Color(41,141,208));  
					Shape shape = null;  
					shape = new RoundRectangle2D.Double(0, 0, (int)Math.round(outputSize.getWidth())-1, (int)Math.round(outputSize.getHeight())-1, 6.5D, 6.5D);  
					g2d.draw(shape); 
				}
				if(isShowInfo){
					double imgMod=image.getWidth(null)/image.getHeight(null);
					double actuallyMod=outputWidth/outputHeight;
					g2d.drawString(outputWidth*2+"釐米,"+outputHeight*2+"釐米    ", (int)Math.round(outputSize.getWidth())-1, (int)Math.round(outputSize.getHeight())-1);
				}
				//Dimension adaptContainerSize = FileService.getAdaptContainerSize(image, 800, 450);
		        System.out.println("\tdebug: Jpanel尺寸: \t"+panel.getSize().getWidth()+","+panel.getSize().getHeight());
		        System.out.println("\tdebug: Jpanel位置: \t"+panel.getLocation().getX()+","+panel.getLocation().getY());
		        System.out.println("\tdebug: Jpanel圖片尺寸: \t"+image.getWidth(panel)+","+image.getHeight(panel));
		        System.out.println("\tdebug: Jpanel縮放尺寸: \t"+outputSize.getWidth()+","+outputSize.getHeight());
			}


			@Override
			public void draw(Graphics g, java.awt.Component target) {
				
			}
		});
		previewPicturePanel.setImagePath(imageUrl);
		previewPicturePanel.repaint();
		System.out.println("\tdebug: previewPicturePanel:\t"+imageUrl+","+previewPicturePanel.isVisible());
	}
	
	public void drawImageToPreviewPicturePanelAdaptSize(String imageUrl){
		previewPicturePanel.setDrawer(new Drawer() {
			@Override
			public void draw(Graphics g,Image image,PicturePanel panel) {
				Dimension outputSize =FileService.getAdaptContainerSize(image, 800, 450);
				//Dimension outputSize = FileService.getOutputSize(image, setValueWidth,setValueHeight, outputWidth, outputHeight);
				
				Graphics2D g2d = (Graphics2D) g;  
				// 设置画笔颜色，填充或描边  
				final Paint p = new GradientPaint(0.0f, 0.0f,Color.BLACK,  
						50, 50, Color.BLACK, true);  
				// 设置画笔颜色为白色  繪製背景色
				g2d.setPaint(p);  
				g2d.setColor(Color.BLACK);  
				g2d.fillRect(0, 0, previewPicturePanel.getWidth(),previewPicturePanel.getHeight());				
				
				g.drawImage(image, 0, 0, (int)Math.round(outputSize.getWidth())-1, (int)Math.round(outputSize.getHeight())-1, 0, 0, image.getWidth(panel),image.getHeight(panel),panel);

				//Dimension adaptContainerSize = FileService.getAdaptContainerSize(image, 800, 450);
		        System.out.println("\tdebug: Jpanel尺寸: \t"+panel.getSize().getWidth()+","+panel.getSize().getHeight());
		        System.out.println("\tdebug: Jpanel位置: \t"+panel.getLocation().getX()+","+panel.getLocation().getY());
		        System.out.println("\tdebug: Jpanel圖片尺寸: \t"+image.getWidth(panel)+","+image.getHeight(panel));
		        System.out.println("\tdebug: Jpanel縮放尺寸: \t"+outputSize.getWidth()+","+outputSize.getHeight());
			}


			@Override
			public void draw(Graphics g, java.awt.Component target) {
				
			}
		});
		previewPicturePanel.setImagePath(imageUrl);
		previewPicturePanel.repaint();
	}
	public String loadRawImage(File file){
		System.out.println("文件:"+file.getAbsolutePath()); 
    	//輸出原始文件
    	//indexController.getPlayController().palyPicture(rawUrl);
    	//轉換為jpg
    	//String url=indexController.getFileService().nconvertToJpg(file.getAbsolutePath(),file.getName().split("\\.")[0]+".jpg");
    	//轉換為png
    	String url=indexController.getFileService().nconvertToPng(file.getAbsolutePath(),file.getName().split("\\.")[0]+".png");
    	
    	indexController.getMainActivity().activeImagePath=url;
        try {  
            // 该方法会将图像加载到内存，从而拿到图像的详细信息。  
        	indexController.getMainActivity().activeImage = ImageIO.read(new FileInputStream(url));  
        } catch (FileNotFoundException ex) {  
            ex.printStackTrace();  
        } catch (IOException ex) {  
            ex.printStackTrace();  
        }
        
    	//輸出到投影
    	//indexController.getPlayController().palyPicture(url);
    	//輸出到主頁面
    	//indexController.getMainActivity().drawImageToPreviewPicturePanel(url);
    	/**
    	 * new Dimension(400, 200)為校準參數 為800*450對應的實際尺寸 調試使用 400cm*225cm
    	 */
    	indexController.showPlayActivity();
    	//調整後輸出到主頁面
    	
    	indexController.getMainActivity().drawImageToPreviewPicturePanelAdaptSize(url);
    	//調整後輸出到投影
    	//indexController.getPlayActivity().playPictureWithSet(url,new Dimension(400, 225),180,200);
		return url;
	}
	public MainActivity() throws HeadlessException {
		final JLabel leftCut=new JLabel();
		final JLabel rightCut=new JLabel();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle(Messages.getString("mainActivityTitle")); //$NON-NLS-1$
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainActivity.class.getResource("/ico/icon.png")));
		//設置
		setSize(1280-128-16, 720-32);
		setResizable(false); 
		
		JPanel panel = new JPanel();
		//panel.setBounds(0, 0, Math.round(getContentPane().getAlignmentX())-1, Math.round(getContentPane().getAlignmentY())-1);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setForeground(Color.DARK_GRAY);
		tabbedPane.setBounds(10, 10, 1032, 600);
		panel.add(tabbedPane);
		
				JPanel workPanel=new JPanel();
				JPanel preparedPanel=new JPanel();
				preparedPanel.addMouseListener(new MouseAdapter() {
					/**
					 * XXX 校準頁面進入,輸出800*450圖片
					 */
					@Override
					public void mouseEntered(MouseEvent arg0) {
						//JOptionPane.showMessageDialog(null, "校準頁面進入,輸出800*450圖片", "注意", JOptionPane.WARNING_MESSAGE);
						indexController.getPlayActivity().playPicture(IndexController.workFloder+"\\set.bmp");
					}
				});
				JPanel aboutPanel=new JPanel();
				this.aboutPanel=aboutPanel;
				//JButton button = new JButton(Messages.getString("MainActivity.button.text")); //$NON-NLS-1$
				tabbedPane.addTab("輸出", new ImageIcon(MainActivity.class.getResource("/ico/play64.png")), workPanel, "調整圖像並輸出");
				workPanel.setLayout(null);

				PicturePanel panel_1 = new PicturePanel();
				panel_1.addMouseListener(new MouseAdapter() {
					/**
					 * FIXME 完成鼠標按下開始繪製矩形,彈起時取得座標 按鈕按下裁剪則按座標展示圖片
					 */
					int[] location=new int[4];
					@Override
					public void mousePressed(MouseEvent arg0) {
						location[0]=arg0.getX();
						location[1]=arg0.getY();
						
						
						leftCut.setIcon(new ImageIcon(MainActivity.class.getResource("/ico/leftCut.png")));
						leftCut.setBounds(0, 0, 100, 100);
						leftCut.setLocation(location[0]-40, location[1]-40);
						previewPicturePanel.add(leftCut);
						
						System.out.println("鼠標按下"+arg0.getX()+","+arg0.getY());
					}
					@Override
					public void mouseReleased(MouseEvent arg0) {
						location[2]=arg0.getX();
						location[3]=arg0.getY();
						
						rightCut.setIcon(new ImageIcon(MainActivity.class.getResource("/ico/rightCut.png")));
						rightCut.setBounds(0, 0, 100, 100);
						rightCut.setLocation(location[2]-60, location[3]-60);
						previewPicturePanel.add(rightCut);
						
						System.out.println("鼠標彈起"+arg0.getX()+","+arg0.getY());
						//getImageOutputAttributes().put(MainActivity.IMAGEOUTPUTATTRIBUTES_ISCUT, true);
						getImageOutputAttributes().put(MainActivity.IMAGEOUTPUTATTRIBUTES_CUTLOCATION, location);
						if(null==getImageOutputAttributes().get(MainActivity.IMAGEOUTPUTATTRIBUTES_ISCUT)||!(boolean) getImageOutputAttributes().get(MainActivity.IMAGEOUTPUTATTRIBUTES_ISCUT)){
							Toolkit.getDefaultToolkit().beep();
							JOptionPane.showMessageDialog(null, "若需裁剪,請先點擊手動裁剪按鈕!", "提示", JOptionPane.WARNING_MESSAGE);
							leftCut.setIcon(null);
							rightCut.setIcon(null);
						}else{
							outputButton.doClick();
						}
					}
				});
				panel_1.addMouseMotionListener(new MouseMotionAdapter() {
					@Override
					public void mouseMoved(MouseEvent arg0) {
						double x=arg0.getLocationOnScreen().getX();
						double y=arg0.getLocationOnScreen().getY();
						System.out.println("鼠標位置:"+x+","+y);
					}
				});
				panel_1.setBackground(Color.GRAY);
				panel_1.setBounds(0, 31, 800, 450);
				panel_1.setLayout(null);
				workPanel.add(panel_1);
				this.previewPicturePanel=panel_1;
				

				
				JMenuBar menuBar = new JMenuBar();
				menuBar.setBounds(0, 0, 1037, 32);
				workPanel.add(menuBar);
				
				JMenuItem mntmNewMenuItem = new JMenuItem(Messages.getString("MainActivity.mntmNewMenuItem.text"));
				mntmNewMenuItem.setFont(new Font("微軟正黑體", Font.BOLD, 14));
				menuBar.add(mntmNewMenuItem);
				mntmNewMenuItem.setIcon(new ImageIcon(MainActivity.class.getResource("/ico/Documents16.png")));
				mntmNewMenuItem.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						JFileChooser jFileChooser=new JFileChooser();
						jFileChooser.showDialog(new JLabel(), "选择");  
				        File file=jFileChooser.getSelectedFile();  
				        if(file.isDirectory()){
				        	System.out.println("文件夹:"+file.getAbsolutePath()); 
				        	System.out.println("選擇的是文件夾,請選擇圖片文件!"); 
				        	//String rawUrl=file.getAbsolutePath();
				        	//indexController.getPlayController().palyPicture(rawUrl);
				        	//String url=indexController.getFileService().getModifiedPicturePath(file.getAbsolutePath());
				        	//indexController.getPlayController().palyPicture(url);
				        }else if(file.isFile()){  
				        	indexController.getMainActivity().loadRawImage(file);
				        	indexController.getMainActivity().getIsCropButton().setSelected(false);
				        	
				        	//System.out.println("文件:"+file.getAbsolutePath()); 
				        	//輸出原始文件
				        	//indexController.getPlayController().palyPicture(rawUrl);
				        	//轉換為jpg
				        	//String url=indexController.getFileService().nconvertToJpg(file.getAbsolutePath(),file.getName().split("\\.")[0]+".jpg");
				        	//轉換為png
				        	/*String url=indexController.getFileService().nconvertToPng(file.getAbsolutePath(),file.getName().split("\\.")[0]+".png");
				        	
				        	indexController.getMainActivity().activeImagePath=url;
				            try {  
				                // 该方法会将图像加载到内存，从而拿到图像的详细信息。  
				            	indexController.getMainActivity().activeImage = ImageIO.read(new FileInputStream(url));  
				            } catch (FileNotFoundException ex) {  
				                ex.printStackTrace();  
				            } catch (IOException ex) {  
				                ex.printStackTrace();  
				            }
				            */
				        	//輸出到投影
				        	//indexController.getPlayController().palyPicture(url);
				        	//輸出到主頁面
				        	//indexController.getMainActivity().drawImageToPreviewPicturePanel(url);
				        	/**
				        	 * new Dimension(400, 200)為校準參數 為800*450對應的實際尺寸 調試使用 400cm*225cm
				        	 */
				        	//indexController.showPlayActivity();
				        	//調整後輸出到主頁面
				        	
				        	//indexController.getMainActivity().drawImageToPreviewPicturePanelAdaptSize(url);
				        	//調整後輸出到投影
				        	//indexController.getPlayActivity().playPictureWithSet(url,new Dimension(400, 225),180,200);
				        }  
					}
				});
				
				JPanel panel_3 = new JPanel();
				panel_3.setBounds(795, 31, 242, 450);
				workPanel.add(panel_3);
				panel_3.setLayout(null);
				
				JPanel panel_2 = new JPanel();
				panel_2.setBounds(37, 10, 195, 150);
				panel_3.add(panel_2);
				panel_2.setBorder(new TitledBorder(new LineBorder(new Color(67, 78, 84), 2, true), "\u8F38\u51FA\u5C3A\u5BF8\u8ABF\u6574", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
				panel_2.setLayout(null);
				
				widthCheckbox = new JCheckBox(Messages.getString("MainActivity.checkBox.text")); //$NON-NLS-1$
				widthCheckbox.setForeground(new Color(0, 0, 0));
				widthCheckbox.addChangeListener(new ChangeListener() {
					//
					public void stateChanged(ChangeEvent arg0) {
						if(widthCheckbox.isSelected()){
							textField.setEditable(true);
							if(heightCheckbox.isSelected()){
								Toolkit.getDefaultToolkit().beep();
								//JOptionPane.showMessageDialog(null, "若手動指定寬度的同時又指定高度,可能會導致圖片被拉伸,您可以僅指定寬或高中的一項,讓系統計算另一項,以保持原始比例!", "注意", JOptionPane.WARNING_MESSAGE);
								widthCheckbox.setToolTipText("若手動指定寬度的同時又指定高度,可能會導致圖片被拉伸,您可以僅指定寬或高中的一項,讓系統計算另一項,以保持原始比例!");
								//widthCheckbox.setSelected(true);
							}
						}else{
							textField.setEditable(false);
						}
					}
				});
				widthCheckbox.setFont(new Font("新細明體", Font.BOLD, 16));
				widthCheckbox.setBounds(6, 29, 89, 23);
				panel_2.add(widthCheckbox);
				
				textField = new JTextField();
				textField.setEditable(false);
				textField.addFocusListener(new FocusAdapter() {
					@Override
					public void focusLost(FocusEvent arg0) {
						/**
						 * xxx
						 * 按比例自動計算高
						 */
						//
						boolean isWidthSelected = indexController.getMainActivity().getWidthCheckbox().isSelected();
						boolean isHeightSelected = indexController.getMainActivity().getHeightCheckbox().isSelected();
						//用戶輸入的尺寸 單位釐米
						double inputWidthSize= Double.parseDouble(indexController.getMainActivity().getTextField().getText());
						double inputHeightSize= Double.parseDouble("".equals(indexController.getMainActivity().getTextField_1().getText())?"0.0":indexController.getMainActivity().getTextField_1().getText());
						int imageWidth = indexController.getMainActivity().activeImage.getWidth(null);
						int imageHeight = indexController.getMainActivity().activeImage.getHeight(null);
						if(!isHeightSelected){
							//計算保持比例不拉伸的高
							String keepLikeHeight=new DecimalFormat(".00").format(1.0*inputWidthSize*imageHeight/imageWidth);
							indexController.getMainActivity().getTextField_1().setText(keepLikeHeight);
							indexController.getMainActivity().getTextField_1().setToolTipText("保持寬高比自動計算高為 "+keepLikeHeight+" ,若指定為其他值會導致圖片變形!");
							System.out.println(imageWidth+","+imageHeight+","+inputWidthSize+","+1.0*inputWidthSize*imageHeight/imageWidth+","+1.0*imageHeight/imageWidth);
						}
					}
				});
				textField.setText("");
				textField.setBounds(101, 31, 53, 21);
				panel_2.add(textField);
				textField.setColumns(10);
				
				JLabel label = new JLabel(Messages.getString("MainActivity.label.text")); //$NON-NLS-1$
				label.setFont(new Font("新細明體", Font.PLAIN, 14));
				label.setBounds(164, 34, 31, 15);
				panel_2.add(label);
				
				heightCheckbox = new JCheckBox(Messages.getString("MainActivity.chckbxNewCheckBox_1.text")); //$NON-NLS-1$
				heightCheckbox.addChangeListener(new ChangeListener() {
					public void stateChanged(ChangeEvent arg0) {
							if(heightCheckbox.isSelected()){
								textField_1.setEditable(true);
								if(widthCheckbox.isSelected()){
									Toolkit.getDefaultToolkit().beep();
									widthCheckbox.setToolTipText("若手動指定寬度的同時又指定高度,可能會導致圖片被拉伸,您可以僅指定寬或高中的一項,讓系統計算另一項,以保持原始比例!");
									//JOptionPane.showMessageDialog(null, "若手動指定寬度的同時又指定高度,可能會導致圖片被拉伸,您可以僅指定寬或高中的一項,讓系統計算另一項,以保持原始比例!", "注意", JOptionPane.WARNING_MESSAGE);
									//heightCheckbox.setSelected(true);
								}
							}else{
								textField_1.setEditable(false);
							}
					}
				});
				heightCheckbox.setFont(new Font("新細明體", Font.BOLD, 16));
				heightCheckbox.setBounds(6, 70, 89, 23);
				panel_2.add(heightCheckbox);
				
				textField_1 = new JTextField();
				textField_1.addFocusListener(new FocusAdapter() {
					@Override
					public void focusLost(FocusEvent arg0) {
						/**
						 * xxx
						 * 按比例自動計算寬
						 */
						//
						boolean isWidthSelected = indexController.getMainActivity().getWidthCheckbox().isSelected();
						boolean isHeightSelected = indexController.getMainActivity().getHeightCheckbox().isSelected();
						//用戶輸入的尺寸 單位釐米
						double inputWidthSize= Double.parseDouble("".equals(indexController.getMainActivity().getTextField().getText())?"0.0":indexController.getMainActivity().getTextField().getText());
						double inputHeightSize= Double.parseDouble("".equals(indexController.getMainActivity().getTextField_1().getText())?"0.0":indexController.getMainActivity().getTextField_1().getText());
						int imageWidth = indexController.getMainActivity().activeImage.getWidth(null);
						int imageHeight = indexController.getMainActivity().activeImage.getHeight(null);
						if(!isWidthSelected){
							//計算保持比例不拉伸的寬
							String keepLikeWidth=new DecimalFormat(".00").format(1.0*inputHeightSize*(1.0*imageWidth/imageHeight));
							indexController.getMainActivity().getTextField().setText(keepLikeWidth);
							indexController.getMainActivity().getTextField().setToolTipText("保持寬高比自動計算寬為 "+keepLikeWidth+" ,若指定為其他值會導致圖片變形!");
						}
					}
				});
				textField_1.setEditable(false);
				textField_1.setText("");
				textField_1.setColumns(10);
				textField_1.setBounds(101, 72, 53, 21);
				panel_2.add(textField_1);
				
				JLabel lblCm = new JLabel(Messages.getString("MainActivity.lblCm.text")); //$NON-NLS-1$
				lblCm.setFont(new Font("新細明體", Font.PLAIN, 14));
				lblCm.setBounds(164, 75, 31, 15);
				panel_2.add(lblCm);
				
				JToggleButton toggleButton = new JToggleButton(Messages.getString("MainActivity.toggleButton.text")); //$NON-NLS-1$
				toggleButton.setBounds(75, 106, 105, 34);
				panel_2.add(toggleButton);
				toggleButton.setFont(new Font("微軟正黑體", Font.BOLD, 16));
				this.isCropButton=toggleButton;
				toggleButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						// XXX 手動裁剪 調用投影按鈕,并開啟選框
						boolean isSelected=((JToggleButton)arg0.getSource()).isSelected();
						if(isSelected){
							imageOutputAttributes.put(MainActivity.IMAGEOUTPUTATTRIBUTES_ISCUT,true);
							leftCut.setIcon(new ImageIcon(MainActivity.class.getResource("/ico/leftCut.png")));
							rightCut.setIcon(new ImageIcon(MainActivity.class.getResource("/ico/rightCut.png")));
						}else{
							leftCut.setIcon(null);
							rightCut.setIcon(null);
							imageOutputAttributes.put(MainActivity.IMAGEOUTPUTATTRIBUTES_ISCUT,false);
						}
						outputButton.doClick();
						
					}
					/*public void actionPerformed(ActionEvent arg0) {
						
						 //FIXME 自動裁剪 採用修改文件的方式

						boolean isSelected=((JToggleButton)arg0.getSource()).isSelected();
						if(!isSelected){
							JOptionPane.showMessageDialog(null,"若需取消裁剪,請從新打開文件", "注意", JOptionPane.WARNING_MESSAGE);
							return;
						}
						if(null==activeImagePath||"".equals(activeImagePath)){
							JOptionPane.showMessageDialog(null,"請先打開圖片", "提示", JOptionPane.WARNING_MESSAGE);
						}
						activeImagePath=FileService.nconvertAutoCrop(activeImagePath, new File(activeImagePath).getName());
						indexController.getMainActivity().loadRawImage(new File(activeImagePath));
						JOptionPane.showMessageDialog(null,"裁剪完畢", "注意", JOptionPane.WARNING_MESSAGE);
						//裁剪后從新選擇并打開文件,新加一個load方法替換選擇文件后調用的方法,裁剪后調用load方法
					}*/
				});
				
				JPanel panel_4 = new JPanel();
				panel_4.setBorder(new TitledBorder(new LineBorder(new Color(67, 78, 84), 2, true), "\u7DDA\u6846\u8207\u80CC\u666F\u8ABF\u6574", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
				panel_4.setBounds(37, 170, 195, 176);
				panel_3.add(panel_4);
				panel_4.setLayout(null);
				
				JCheckBox checkBox = new JCheckBox(Messages.getString("MainActivity.checkBox.text_1")); //$NON-NLS-1$
				this.isBorderCheckBox=checkBox;
				checkBox.setFont(new Font("新細明體", Font.BOLD, 16));
				checkBox.setBounds(10, 109, 171, 23);
				panel_4.add(checkBox);
				
				JCheckBox checkBox_1 = new JCheckBox(Messages.getString("MainActivity.checkBox_1.text")); //$NON-NLS-1$
				this.isShowPictureInfoCheckBox=checkBox_1;
				checkBox_1.setFont(new Font("新細明體", Font.BOLD, 16));
				checkBox_1.setBounds(10, 134, 171, 23);
				panel_4.add(checkBox_1);
				
				JToggleButton toggleButton_1 = new JToggleButton(Messages.getString("MainActivity.toggleButton_1.text_1")); //$NON-NLS-1$
				toggleButton_1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						boolean isSelected=((JToggleButton)arg0.getSource()).isSelected();
						if(isSelected){
							imageOutputAttributes.put(IMAGEOUTPUTATTRIBUTES_ISGETEDGE, true);
						}else{
							imageOutputAttributes.put(IMAGEOUTPUTATTRIBUTES_ISGETEDGE, false);
						}
					}
				});
				toggleButton_1.setFont(new Font("微軟正黑體", Font.BOLD, 16));
				toggleButton_1.setBounds(10, 20, 105, 34);
				panel_4.add(toggleButton_1);
				
				JToggleButton toggleButton_2 = new JToggleButton(Messages.getString("MainActivity.toggleButton_2.text")); //$NON-NLS-1$
				toggleButton_2.setFont(new Font("微軟正黑體", Font.BOLD, 16));
				toggleButton_2.setBounds(10, 64, 105, 34);
				panel_4.add(toggleButton_2);
				
				JButton button_1 = new JButton("");
				button_1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Color color=new Color(200,200,200);
						color=JColorChooser.showDialog(null,"请选择輪廓的颜色" ,color);  
							if (color==null ) color=Color.lightGray; 
							System.out.println(color.getBlue()+","+color.getGreen()+","+color.getBlue());
							imageOutputAttributes.put(IMAGEOUTPUTATTRIBUTES_EDGECOLOR, color);
					}
				});
				button_1.setIcon(new ImageIcon(MainActivity.class.getResource("/ico/paint32.png")));
				button_1.setForeground(Color.BLACK);
				button_1.setFont(new Font("新細明體", Font.BOLD, 16));
				button_1.setBackground(new Color(210, 105, 30));
				button_1.setBounds(125, 20, 56, 34);
				panel_4.add(button_1);
								
				JButton button = new JButton(Messages.getString("MainActivity.button.text_1")); //$NON-NLS-1$
				outputButton=button;
				button.setBounds(47, 369, 171, 57);
				panel_3.add(button);
				button.setFont(new Font("新細明體", Font.BOLD, 16));
				button.setIcon(new ImageIcon(MainActivity.class.getResource("/ico/play64.png")));
				button.setForeground(new Color(0, 0, 0));
				button.setBackground(new Color(210, 105, 30));
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						playPicture();
					}
				});
				
				tabbedPane.addTab("校準", new ImageIcon(MainActivity.class.getResource("/ico/player64.png")), preparedPanel, null);
				preparedPanel.setLayout(null);
				
				JButton btnNewButton = new JButton(Messages.getString("MainActivity.btnNewButton.text")); //$NON-NLS-1$
				btnNewButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						/**
						 * xxx 獲取校準參數放入IndexController
						 */
						//釐米
						double widthCm=Double.parseDouble(textField_2.getText());
						double heightCm=Double.parseDouble(textField_3.getText());
						IndexController.screenSetWidth=widthCm;
						IndexController.screenSetHeight=heightCm;
						IndexController.applicationConfig.setProperty("screenSetWidth", String.valueOf(widthCm));
						IndexController.applicationConfig.setProperty("screenSetHeight", String.valueOf(heightCm));
						try {
							IndexController.applicationConfig.store(new FileWriter(IndexController.configFile), (new Date()).toString());
						} catch (IOException e) {
							JOptionPane.showMessageDialog(null, "配置文件保存到文件失敗!", "錯誤", JOptionPane.WARNING_MESSAGE);
							System.out.println("配置文件保存到文件失敗!");
							e.printStackTrace();
						}
					}
				});
				btnNewButton.setFont(new Font("新細明體", Font.PLAIN, 16));
				btnNewButton.setBounds(521, 152, 215, 86);
				preparedPanel.add(btnNewButton);
				
				textField_2 = new JTextField();
				textField_2.setFont(new Font("新細明體", Font.PLAIN, 16));
				textField_2.setText("");
				textField_2.setBounds(283, 152, 96, 34);
				preparedPanel.add(textField_2);
				textField_2.setColumns(10);
				
				textField_3 = new JTextField();
				textField_3.setFont(new Font("新細明體", Font.PLAIN, 16));
				textField_3.setText("");
				textField_3.setBounds(283, 204, 96, 34);
				preparedPanel.add(textField_3);
				textField_3.setColumns(10);
				
				JLabel lblNewLabel = new JLabel(Messages.getString("MainActivity.lblNewLabel.text")); //$NON-NLS-1$
				lblNewLabel.setFont(new Font("新細明體", Font.PLAIN, 16));
				lblNewLabel.setBounds(45, 64, 955, 15);
				preparedPanel.add(lblNewLabel);
				
				JLabel label_1 = new JLabel(Messages.getString("MainActivity.label_1.text")); //$NON-NLS-1$
				label_1.setFont(new Font("新細明體", Font.PLAIN, 16));
				label_1.setBounds(178, 162, 80, 15);
				preparedPanel.add(label_1);
				
				JLabel lblNewLabel_1 = new JLabel(Messages.getString("MainActivity.lblNewLabel_1.text")); //$NON-NLS-1$
				lblNewLabel_1.setFont(new Font("新細明體", Font.PLAIN, 16));
				lblNewLabel_1.setBounds(178, 220, 80, 15);
				preparedPanel.add(lblNewLabel_1);
				
				JTextArea textArea = new JTextArea();
				textArea.setBackground(SystemColor.activeCaption);
				textArea.setEditable(false);
				textArea.setFont(new Font("微軟正黑體", Font.PLAIN, 15));
				textAreaSetScreenValueInfo=textArea;
				textArea.setText(Messages.getString("MainActivity.textArea.text_1")); //$NON-NLS-1$
				textArea.setBounds(175, 262, 561, 226);
				preparedPanel.add(textArea);
				tabbedPane.addTab("關於", new ImageIcon(MainActivity.class.getResource("/ico/add64.png")), aboutPanel, null);
				aboutPanel.setLayout(null);
				
				JLabel lblNewLabel_2 = new JLabel("");
				lblNewLabel_2.setIcon(new ImageIcon(MainActivity.class.getResource("/ico/help.png")));
				lblNewLabel_2.setBounds(0, 10, 1017, 512);
				aboutPanel.add(lblNewLabel_2);
		
	}
	public void playPicture(){
		String tureActiveImagePath=activeImagePath;
		//是否處理圖片(尺寸不變)
		if(imageOutputAttributes.get(IMAGEOUTPUTATTRIBUTES_ISGETEDGE) != null && (boolean)imageOutputAttributes.get(IMAGEOUTPUTATTRIBUTES_ISGETEDGE)){
			tureActiveImagePath=OpenCVUtil.chageBackColorAndEdgeColorAndGetEdge(activeImagePath,(Color)imageOutputAttributes.get(IMAGEOUTPUTATTRIBUTES_EDGECOLOR), null);
		}else{
			tureActiveImagePath=activeImagePath;
		}
		
		//投影儀尺寸
		int playActivityHeight=indexController.getPlayActivity().getHeight();
		int playActivityWidth=indexController.getPlayActivity().getWidth();
		//最大輸出到地面尺寸 單位釐米 根據校準參數計算得到
		double maxInputWidthSize = (playActivityWidth/800.0)*IndexController.screenSetWidth;
		double maxInputHeightSize = (playActivityHeight/450.0)*IndexController.screenSetHeight;
		//
		boolean isWidthSelected = indexController.getMainActivity().getWidthCheckbox().isSelected();
		boolean isHeightSelected = indexController.getMainActivity().getHeightCheckbox().isSelected();

		String info="";
		info+="投影儀器尺寸為:"+playActivityWidth+","+playActivityHeight;
		info+="\r\n最大輸出到地面尺寸為:"+Math.round((maxInputWidthSize*10))/10.0+"cm,"+Math.round((maxInputHeightSize*10))/10.0+"cm ";
		info+="\r\n校準參數為:"+IndexController.screenSetWidth+"cm,"+IndexController.screenSetHeight+"cm";
		info+="\r\n(800*450對應的實際尺寸)";
		double inputWidthSize=0;
		double inputHeightSize=0;
		try {
			//用戶輸入的尺寸 單位釐米
			inputWidthSize= Double.parseDouble(indexController.getMainActivity().getTextField().getText());
			inputHeightSize= Double.parseDouble(indexController.getMainActivity().getTextField_1().getText());
		} catch (Exception e) {
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(null, indexController.getMainActivity().getTextField().getText()+","+indexController.getMainActivity().getTextField_1().getText()+"  不是一組合法的寬高值,請檢查輸入尺寸,數字或小數,單位釐米!","請檢查輸入", JOptionPane.WARNING_MESSAGE);
			return;
		}
		if(inputWidthSize>maxInputWidthSize||inputHeightSize>maxInputHeightSize){
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(null, info, "輸入尺寸過大!", JOptionPane.WARNING_MESSAGE);
			return;
		}
		boolean isBorder=indexController.getMainActivity().getIsBorderCheckBox().isSelected();
    	boolean isShowInfo=indexController.getMainActivity().getIsShowPictureInfoCheckBox().isSelected();
		if(null!=imageOutputAttributes.get(MainActivity.IMAGEOUTPUTATTRIBUTES_ISCUT) && (boolean) imageOutputAttributes.get(MainActivity.IMAGEOUTPUTATTRIBUTES_ISCUT)){
			Image activeImage = FileService.getImage(tureActiveImagePath);
			//計算輸出尺寸
			Dimension outputSize = FileService.getOutputSize(activeImage, IndexController.screenSetWidth,IndexController.screenSetHeight, inputWidthSize/2, inputHeightSize/2);
			//計算截取位置 百分比坐標*圖片實際尺寸,傳遞給playpicture展示
			int[] locationMouse=(int[]) imageOutputAttributes.get(MainActivity.IMAGEOUTPUTATTRIBUTES_CUTLOCATION);
			//int[] resultLocation=new int[4];
			locationMouse[0]=(int) (Math.round(activeImage.getWidth(null)*(locationMouse[0]/outputSize.getWidth()))-1);
			locationMouse[2]=(int) (Math.round(activeImage.getWidth(null)*(locationMouse[2]/outputSize.getWidth()))-1);
			locationMouse[1]=(int) (Math.round(activeImage.getHeight(null)*(locationMouse[1]/outputSize.getHeight()))-1);
			locationMouse[3]=(int) (Math.round(activeImage.getHeight(null)*(locationMouse[3]/outputSize.getHeight()))-1);
			indexController.getMainActivity().drawImageToPreviewPicturePanelWithSet(tureActiveImagePath,IndexController.screenSetWidth,IndexController.screenSetHeight,inputWidthSize/2,inputHeightSize/2,isBorder,isShowInfo);
			imageOutputAttributes.put(IMAGEOUTPUTATTRIBUTES_ISBORDER, isBorder);
			imageOutputAttributes.put(IMAGEOUTPUTATTRIBUTES_ISSHOWINFO, isShowInfo);
			indexController.getPlayActivity().playPictureWithSet(tureActiveImagePath,IndexController.screenSetWidth,IndexController.screenSetHeight,inputWidthSize,inputHeightSize,imageOutputAttributes);
			
		}else{
			//調整後輸出到主頁面(不能自動刷新?)
			indexController.getMainActivity().drawImageToPreviewPicturePanelWithSet(tureActiveImagePath,IndexController.screenSetWidth,IndexController.screenSetHeight,inputWidthSize/2,inputHeightSize/2,isBorder,isShowInfo);
			//調整後輸出到投影(疊加)
			indexController.getPlayActivity().playPictureWithSet(tureActiveImagePath,IndexController.screenSetWidth,IndexController.screenSetHeight,inputWidthSize,inputHeightSize,isBorder,isShowInfo);
		}		
	}
	public MainActivity(GraphicsConfiguration gc) {
		super(gc);
	}

	public MainActivity(String title) throws HeadlessException {
		super(title);
	}

	public MainActivity(String title, GraphicsConfiguration gc) {
		super(title, gc);
	}
	@Override
	public void dispose() {
		// TODO 退出進程
		super.dispose();
		System.out.println("dispose");
		System.exit(0);
	}
	
	public JTextField getTextField() {
		return textField;
	}
	public void setTextField(JTextField textField) {
		this.textField = textField;
	}
	public JTextField getTextField_1() {
		return textField_1;
	}
	public void setTextField_1(JTextField textField_1) {
		this.textField_1 = textField_1;
	}
	public JCheckBox getWidthCheckbox() {
		return widthCheckbox;
	}
	public void setWidthCheckbox(JCheckBox widthCheckbox) {
		this.widthCheckbox = widthCheckbox;
	}
	public JCheckBox getHeightCheckbox() {
		return heightCheckbox;
	}
	public void setHeightCheckbox(JCheckBox heightCheckbox) {
		this.heightCheckbox = heightCheckbox;
	}
	public PicturePanel getPreviewPicturePanel() {
		return previewPicturePanel;
	}
	public void setPreviewPicturePanel(PicturePanel previewPicturePanel) {
		this.previewPicturePanel = previewPicturePanel;
	}
	public JTextArea getTextAreaSetScreenValueInfo() {
		return textAreaSetScreenValueInfo;
	}
	public void setTextAreaSetScreenValueInfo(JTextArea textAreaSetScreenValueInfo) {
		this.textAreaSetScreenValueInfo = textAreaSetScreenValueInfo;
	}
	public JToggleButton getIsCropButton() {
		return isCropButton;
	}
	public JCheckBox getIsBorderCheckBox() {
		return isBorderCheckBox;
	}
	public JCheckBox getIsShowPictureInfoCheckBox() {
		return isShowPictureInfoCheckBox;
	}
	public JPanel getAboutPanel() {
		return aboutPanel;
	}
	public HashMap<String, Object> getImageOutputAttributes() {
		return imageOutputAttributes;
	}
}
