package com.lulan.shincolle.client.gui;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.lulan.shincolle.client.inventory.ContainerShipInventory;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.network.CreatePacketC2S;
import com.lulan.shincolle.reference.AttrID;
import com.lulan.shincolle.reference.GUIs;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.tileentity.TileEntitySmallShipyard;
import com.lulan.shincolle.utility.GuiHelper;
import com.lulan.shincolle.utility.LogHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

/**ICON_SHIPTYPE(157,18) 
 * NameIcon: LargeShip(0,0)(40x42) SmallShip(0,43)(30x30) 
 *           驅逐(41,0)(28x28) 輕巡(41,29) 重巡(41,58) 雷巡(41,87) 補給(12,74)
 *           戰艦(70,0) 航母(70,29) 輕母(70,58) 姬(70,87) 潛水(99,0) 浮游(99,29)
 *           
 * Color note:gold:16766720 gray:4210752 dark-gray:3158064 white:16777215 green:65280
 *            yellow:16776960 orange:16753920 red:16711680 cyan:65535
 *            magenta:16711935 pink:16751103
 */
public class GuiShipInventory extends GuiContainer {

	private BasicEntityShip entity;
	private InventoryPlayer player;
	private float xMouse, yMouse;
	private int xClick, yClick;
	private static final ResourceLocation TEXTURE_BG = new ResourceLocation(Reference.TEXTURES_GUI+"GuiShipInventory.png");
	private static final ResourceLocation TEXTURE_ICON = new ResourceLocation(Reference.TEXTURES_GUI+"GuiNameIcon.png");
	//draw string
	private String titlename, shiplevel, lvMark, hpMark, strATK, strDEF, strSPD, strMOV, strHIT, Kills, Exp, AmmoLight, AmmoHeavy, Grudge, Owner;
	private int hpCurrent, hpMax, color;
	//draw button, page
	private int showPage;
	private int pageIndictor;
	private boolean SwitchLight;
	private boolean SwitchHeavy;
	
	//ship type icon array
	private static final short[][] ICON_SHIPTYPE = {
		{41,0}, {41,29}, {41,58}, {41,87}, {70,58}, {70,29}, {70,0}, {12,74}, {99,0},
		{70,87}, {70,87}, {99,29}};
	//ship name icon array
	private static final short[][] ICON_SHIPNAME = {
		{128,0}, {139,0}, {150,0}, {161,0}, {172,0}, {183,0}, {194,0}, {205,0},
		{216,0}, {227,0}, {238,0}, {128,60}, {139,60}, {150,60}, {161,60}, {172,60},
		{183,60}, {194,60}, {205,60}, {216,60}, {227,60}, {238,60}, {128,120}, {139,120},
		{150,120}, {161,120}, {172,120}, {183,120}, {194,120}, {205,120}, {216,120}, 
		{227,120}, {238,120}};
	
	public GuiShipInventory(InventoryPlayer invPlayer, BasicEntityShip entity1) {
		super(new ContainerShipInventory(invPlayer, entity1));
		this.entity = entity1;
		this.player = invPlayer;
		this.xSize = 250;
		this.ySize = 214;
		this.showPage = 1;	//show page 1
	}
	
	//GUI前景: 文字 
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		//取得gui顯示名稱
		titlename = entity.getCustomNameTag();	//get type name from nbt
		
		//畫出字串 parm: string, x, y, color, (是否dropShadow)
		//draw entity name (title) 
		this.fontRendererObj.drawString(titlename, 8, 6, 0);
			
		drawAttributes();	
		
	}

	//GUI背景: 背景圖片
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1,int par2, int par3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);	//RGBA
		
		//draw background
        Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURE_BG);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        
        //draw page indicator
        switch(this.showPage) {
        case 1:	//page 1
        	this.pageIndictor = 18;
        	break;
        case 2:	//page 2
        	this.pageIndictor = 54;
        	break;
        case 3:	//page 3
        	this.pageIndictor = 90;
        	break;
        }
        drawTexturedModalRect(guiLeft+147, guiTop+this.pageIndictor, 250, 0, 6, 34);
        
        //draw ammo ON/OFF
        if(this.showPage == 2) {
        	this.SwitchLight = this.entity.getEntityFlag(AttrID.F_UseAmmoLight);
            this.SwitchHeavy = this.entity.getEntityFlag(AttrID.F_UseAmmoHeavy);
            
            if(this.SwitchLight) {
            	drawTexturedModalRect(guiLeft+87, guiTop+71, 0, 214, 11, 11);
            }
            else {
            	drawTexturedModalRect(guiLeft+87, guiTop+71, 11, 214, 11, 11);
            }
            
            if(this.SwitchHeavy) {
            	drawTexturedModalRect(guiLeft+87, guiTop+92, 0, 214, 11, 11);
            }
            else {
            	drawTexturedModalRect(guiLeft+87, guiTop+92, 11, 214, 11, 11);
            }
        }       
        
        //draw level, ship type icon
        Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURE_ICON);
        if(entity.getShipLevel() > 100) {
        	drawTexturedModalRect(guiLeft+157, guiTop+18, 0, 0, 40, 42);
        	drawTexturedModalRect(guiLeft+159, guiTop+22, ICON_SHIPTYPE[entity.getShipType()][0], ICON_SHIPTYPE[entity.getShipType()][1], 28, 28);
        }
        else {
        	drawTexturedModalRect(guiLeft+157, guiTop+18, 0, 43, 30, 30);
        	drawTexturedModalRect(guiLeft+157, guiTop+18, ICON_SHIPTYPE[entity.getShipType()][0], ICON_SHIPTYPE[entity.getShipType()][1], 28, 28);
        }
        
        //draw left bottom name
        drawTexturedModalRect(guiLeft+166, guiTop+63, ICON_SHIPNAME[entity.getShipID()][0], ICON_SHIPNAME[entity.getShipID()][1], 11, 59);
        
        //draw entity model
        drawEntityModel(guiLeft+210, guiTop+100, 25, (float)(guiLeft + 200 - xMouse), (float)(guiTop + 50 - yMouse), this.entity);
        
	}
	
	//get new mouseX,Y and redraw gui
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		xMouse = mouseX;
		yMouse = mouseY;
	}
	
	//draw entity model, copy from player inventory class
	public static void drawEntityModel(int x, int y, int scale, float yaw, float pitch, BasicEntityShip entity) {		
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, 50.0F);
		GL11.glScalef(-scale, scale, scale);
		GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
		float f2 = entity.renderYawOffset;
		float f3 = entity.rotationYaw;
		float f4 = entity.rotationPitch;
		float f5 = entity.prevRotationYawHead;
		float f6 = entity.rotationYawHead;
		GL11.glRotatef(135.0F, 0.0F, 1.0F, 0.0F);
		RenderHelper.enableStandardItemLighting();
		GL11.glRotatef(-135.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-((float) Math.atan(pitch / 40.0F)) * 20.0F, 1.0F, 0.0F, 0.0F);
		entity.renderYawOffset = (float) Math.atan(yaw / 40.0F) * 20.0F;
		entity.rotationYaw = (float) Math.atan(yaw / 40.0F) * 40.0F;
		entity.rotationPitch = -((float) Math.atan(pitch / 40.0F)) * 20.0F;
		entity.rotationYawHead = entity.rotationYaw;
		entity.prevRotationYawHead = entity.rotationYaw;		
		GL11.glTranslatef(0.0F, entity.yOffset, 0.0F);
		RenderManager.instance.playerViewY = 180.0F;
		RenderManager.instance.renderEntityWithPosYaw(entity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
		entity.renderYawOffset = f2;
		entity.rotationYaw = f3;
		entity.rotationPitch = f4;
		entity.prevRotationYawHead = f5;
		entity.rotationYawHead = f6;
		GL11.glPopMatrix();
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
	}

	//draw level,hp,atk,def...
	private void drawAttributes() {
		//draw hp, level
		shiplevel = String.valueOf(entity.getShipLevel());
		lvMark = I18n.format("gui.shincolle:level");
		hpMark = I18n.format("gui.shincolle:hp");
		hpCurrent = MathHelper.ceiling_float_int(entity.getHealth());
		hpMax = MathHelper.ceiling_float_int(entity.getMaxHealth());
		color = 0;
		
		//draw attribute name
		this.fontRendererObj.drawStringWithShadow(lvMark, 223-this.fontRendererObj.getStringWidth(lvMark), 6, 65535);
		this.fontRendererObj.drawStringWithShadow(hpMark, 144-this.fontRendererObj.getStringWidth(hpMark), 6, 65535);
		
		//draw level: 150->gold other->white
		if(entity.getShipLevel() < 150) {
			color = 16777215;  //white
		}
		else {
			color = 16766720;  //gold	
		}
		this.fontRendererObj.drawStringWithShadow(shiplevel, xSize-7-this.fontRendererObj.getStringWidth(shiplevel), 6, color);

		//draw hp / maxhp, if currHP < maxHP, use darker color
		color = pickColor(entity.getBonusPoint(AttrID.HP));
		this.fontRendererObj.drawStringWithShadow("/"+String.valueOf(hpMax), 148 + this.fontRendererObj.getStringWidth(String.valueOf(hpCurrent)), 6, color);
		if(hpCurrent < hpMax) {
			switch(entity.getBonusPoint(AttrID.HP)) {
			case 0:
				color = 16119285;	//gray
				break;
			case 1:
				color = 13421568;	//dark yellow
				break;
			case 2:
				color = 16747520;	//dark orange
				break;
			default:
				color = 13107200;	//dark red
				break;
			}
		}
		this.fontRendererObj.drawStringWithShadow(String.valueOf(hpCurrent), 147, 6, color);	
				
		//draw string in different page
		switch(this.showPage) {
		case 1: {	//page 0: attribute page		
			strATK = String.format("%.2f", this.entity.getFinalState(AttrID.ATK));
			strDEF = String.format("%.2f", this.entity.getFinalState(AttrID.DEF))+"%";
			strSPD = String.format("%.2f", this.entity.getFinalState(AttrID.SPD));
			strMOV = String.format("%.2f", this.entity.getFinalState(AttrID.MOV));
			strHIT = String.format("%.2f", this.entity.getFinalState(AttrID.HIT));
			
			//draw attribute name			
			this.fontRendererObj.drawString(I18n.format("gui.shincolle:firepower"), 87, 20, pickColor(5));
			this.fontRendererObj.drawString(I18n.format("gui.shincolle:armor"), 87, 41, pickColor(5));
			this.fontRendererObj.drawString(I18n.format("gui.shincolle:attackspeed"), 87, 62, pickColor(5));
			this.fontRendererObj.drawString(I18n.format("gui.shincolle:movespeed"), 87, 83, pickColor(5));
			this.fontRendererObj.drawString(I18n.format("gui.shincolle:range"), 87, 104, pickColor(5));
					
			//draw firepower
			color = pickColor(entity.getBonusPoint(AttrID.ATK));
			this.fontRendererObj.drawStringWithShadow(strATK, 145-this.fontRendererObj.getStringWidth(strATK), 30, color);
			
			//draw armor
			color = pickColor(entity.getBonusPoint(AttrID.DEF));
			this.fontRendererObj.drawStringWithShadow(strDEF, 145-this.fontRendererObj.getStringWidth(strDEF), 51, color);
			
			//draw attack speed
			color = pickColor(entity.getBonusPoint(AttrID.SPD));
			this.fontRendererObj.drawStringWithShadow(strSPD, 145-this.fontRendererObj.getStringWidth(strSPD), 72, color);
			
			//draw movement speed
			color = pickColor(entity.getBonusPoint(AttrID.MOV));
			this.fontRendererObj.drawStringWithShadow(strMOV, 145-this.fontRendererObj.getStringWidth(strMOV), 93, color);
					
			//draw range
			color = pickColor(entity.getBonusPoint(AttrID.HIT));
			this.fontRendererObj.drawStringWithShadow(strHIT, 145-this.fontRendererObj.getStringWidth(strHIT), 114, color);
			break;
			}
		case 2:	{	//page 2: exp, kills, L&H ammo, fuel
			//draw string
			this.fontRendererObj.drawString(I18n.format("gui.shincolle:kills"), 87, 20, pickColor(5));
			this.fontRendererObj.drawString(I18n.format("gui.shincolle:exp"), 87, 41, pickColor(5));
			this.fontRendererObj.drawString(I18n.format("gui.shincolle:ammolight"), 87, 62, pickColor(5));
			this.fontRendererObj.drawString(I18n.format("gui.shincolle:ammoheavy"), 87, 83, pickColor(5));
			this.fontRendererObj.drawString(I18n.format("gui.shincolle:grudge"), 87, 104, pickColor(5));
			//draw value
			entity.setExpNext();  //update exp value
			Exp = String.valueOf(this.entity.getExpCurrent())+"/"+String.valueOf(this.entity.getExpNext());
			Kills = String.valueOf(this.entity.getKills());
			AmmoLight = String.valueOf(this.entity.getNumAmmoLight());
			AmmoHeavy = String.valueOf(this.entity.getNumAmmoHeavy());
			Grudge = String.valueOf(this.entity.getNumGrudge());
				
			this.fontRendererObj.drawStringWithShadow(Kills, 145-this.fontRendererObj.getStringWidth(Kills), 30, pickColor(0));
			this.fontRendererObj.drawStringWithShadow(Exp, 145-this.fontRendererObj.getStringWidth(Exp), 51, pickColor(0));
			this.fontRendererObj.drawStringWithShadow(AmmoLight, 145-this.fontRendererObj.getStringWidth(AmmoLight), 72, pickColor(0));
			this.fontRendererObj.drawStringWithShadow(AmmoHeavy, 145-this.fontRendererObj.getStringWidth(AmmoHeavy), 93, pickColor(0));
			this.fontRendererObj.drawStringWithShadow(Grudge, 145-this.fontRendererObj.getStringWidth(Grudge), 114, pickColor(0));
						
			break;
			}
		case 3: {	//page 3: owner name
			//draw string
			this.fontRendererObj.drawString(I18n.format("gui.shincolle:owner"), 87, 20, pickColor(5));
			
			//draw value
			Owner = this.entity.getOwnerName();
			
			this.fontRendererObj.drawStringWithShadow(Owner, 145-this.fontRendererObj.getStringWidth(Owner), 30, pickColor(1));
			
			break;
			}			
		}//end page switch
	}

	//0:white 1:yellow 2:orange 3:red
	private int pickColor(int b) {
		switch(b) {
		case 0:
			return 16777215;	//white
		case 1:
			return 16776960;	//yellow
		case 2:
			return 16753920;	//orange
		case 3:
			return 16724787;	//dark gray, for string mark
		case 4:
			return 3158064;		//dark gray, for string mark
		case 5:
			return 0;
		default:
			return 16724787;	//red
		}
	}
	
	//handle mouse click, @parm posX, posY, mouseKey (0:left 1:right 2:middle 3:...etc)
	@Override
	protected void mouseClicked(int posX, int posY, int mouseKey) {
        super.mouseClicked(posX, posY, mouseKey);
        
        int ButtonValue;
        
        //get click position
        xClick = posX - this.guiLeft;
        yClick = posY - this.guiTop;
        
        //match all pages
        switch(GuiHelper.getButton(GUIs.SHIPINVENTORY, 0, xClick, yClick)) {
        case 0:
        	this.showPage = 1;
        	break;
        case 1:
        	this.showPage = 2;
        	break;
        case 2:
        	this.showPage = 3;
        	break;
        default:	//繼續找是否為page 2的按鍵
        	if(this.showPage == 2) {
    	        switch(GuiHelper.getButton(GUIs.SHIPINVENTORY, 1, xClick, yClick)) {
    	        case 0:
    	        	this.SwitchLight = this.entity.getEntityFlag(AttrID.F_UseAmmoLight);      		
            		if(this.SwitchLight) {	//若原本為true, 則設為false(0)
            			ButtonValue = 0;
            		}
            		else {
            			ButtonValue = 1;
            		}
            		LogHelper.info("DEBUG : GUI click: use ammo light: "+ButtonValue);
            		CreatePacketC2S.sendC2SGUIShipInvClick(this.entity, AttrID.B_ShipInv_AmmoLight, ButtonValue);
    	        	break;
    	        case 1:
    	        	this.SwitchHeavy = this.entity.getEntityFlag(AttrID.F_UseAmmoHeavy);	
            		if(this.SwitchHeavy) {	//若原本為true, 則設為false(0)
            			ButtonValue = 0;
            		}
            		else {
            			ButtonValue = 1;
            		}
            		LogHelper.info("DEBUG : GUI click: use ammo heavy: "+ButtonValue);
            		CreatePacketC2S.sendC2SGUIShipInvClick(this.entity, AttrID.B_ShipInv_AmmoHeavy, ButtonValue);
    	        	break;
    	        }//end page=2 switch
            }
        	break;
    	}//end all page switch

	}
	

}