package com.yo.main;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.UIManager;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Main {

	public Main() {
		super();
	}
	/*public static void setUIFont()
	{
		Font f = new Font("宋体",Font.PLAIN,18);
		String   names[]={ "Label", "CheckBox", "PopupMenu","MenuItem", "CheckBoxMenuItem",
				"JRadioButtonMenuItem","ComboBox", "Button", "Tree", "ScrollPane",
				"TabbedPane", "EditorPane", "TitledBorder", "Menu", "TextArea",
				"OptionPane", "MenuBar", "ToolBar", "ToggleButton", "ToolTip",
				"ProgressBar", "TableHeader", "Panel", "List", "ColorChooser",
				"PasswordField","TextField", "Table", "Label", "Viewport",
				"RadioButtonMenuItem","RadioButton", "DesktopPane", "InternalFrame"
		}; 
		for (String item : names) {
			 UIManager.put(item+ ".font",f); 
		}
	}*/
	public static void main(String[] args) {
		try
	    {
	    	UIManager.put("RootPane.setupButtonVisible", false);  
	        org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
	    }
	    catch(Exception e)
	    {
	        System.out.println("UI複寫錯誤!");
	    }

		ApplicationContext ctx=new ClassPathXmlApplicationContext("applicationContext.xml");
		JFrame mainActivity = (JFrame) ctx.getBean("mainActivity");
        mainActivity.setVisible(true);
	}
}