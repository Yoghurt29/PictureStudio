package com.yo.controller;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yo.view.MainActivity;
import com.yo.view.PlayActivity;

@Component
public class PlayController {
	@Autowired
	private PlayActivity playActivity;
	//TODO 播放隊列,提供上一張,下一張功能,在mainActivity可見
	public PlayController() {
		// TODO Auto-generated constructor stub
	}
	public void palyImg(File img){
		
	}
}
