package com.lulan.shincolle.handler;

import java.io.File;

import com.lulan.shincolle.reference.Reference;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.config.Configuration;


public class ConfigHandler {
	
	public static Configuration config;	//宣告config檔實體
	
	//設定檔變數
	//GENERAL
	public static boolean debugMode = false;
	//SHIP SETTING
	public static float hpRatio = 1.0f;

	
	//讀取設定檔參數
	private static void loadConfiguration() {
		//是否開啟debug mode (spam debug/info message)
		debugMode = config.getBoolean("Debug_Mode", "general", false, "Enable debug message (SPAM WARNING)");
		
		//讀取 ship setting設定
		//hp ratio
		hpRatio = config.getFloat("Scale_HP", "ship setting", 1f, 0.1f, 10f, "Ship HP scale");	
		
		//若設定檔有更新過 則儲存
		if(config.hasChanged()) {
			config.save();
		}		
	}
	
	
	//設定檔處理 初始化動作
	public static void init(File configFile) {		
		//如果設定檔實體還未建立 則建立之
		if(config == null) {
			config = new Configuration(configFile);	//建立config檔實體
			loadConfiguration();
		}		
	}
	
	
	//若版本更新後 設定檔需要更新 則在此區塊增加更新方法
	@SubscribeEvent
	public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
		//若設定檔的mod id跟目前mod id不同時 則進行更新
		if(event.modID.equalsIgnoreCase(Reference.MOD_ID)) {
			loadConfiguration();
		}
	}

}
