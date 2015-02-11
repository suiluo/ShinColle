package com.lulan.shincolle.init;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class ModRecipes {
	//�t��d��
	//GameRegistry.addSmelting(���o��, ����, 0.1f�g��);
	//GameRegistry.addRecipe(new ShapedOreRecipe(���o��, " s ", "sss", " s ", 's', "stickWood" ���ư}�C));
    //GameRegistry.addRecipe(new ShapelessOreRecipe(���o��, ����A, ����B, ...));		
	public static void init() {
		//�t�����or���~
		ItemStack abyssiumBlock = new ItemStack(ModBlocks.BlockAbyssium);
		ItemStack abyssiumStack = new ItemStack(ModItems.Abyssium);
		ItemStack abyssiumStack9 = new ItemStack(ModItems.Abyssium,9);
		ItemStack ammo1 = new ItemStack(ModItems.Ammo,1); 
		ItemStack ammo8 = new ItemStack(ModItems.Ammo,8); 
		ItemStack ammo9 = new ItemStack(ModItems.Ammo,9); 
		ItemStack ammo16 = new ItemStack(ModItems.Ammo,16);
		ItemStack ammo32 = new ItemStack(ModItems.Ammo,32);
		ItemStack ammo64 = new ItemStack(ModItems.Ammo,64);
		ItemStack ammoContainer = new ItemStack(ModItems.AmmoContainer,1);
		ItemStack ammoHeavy1 = new ItemStack(ModItems.HeavyAmmo,1); 
		ItemStack ammoHeavy2 = new ItemStack(ModItems.HeavyAmmo,2);
		ItemStack ammoHeavy4 = new ItemStack(ModItems.HeavyAmmo,4);
		ItemStack ammoHeavy8 = new ItemStack(ModItems.HeavyAmmo,8);
		ItemStack ammoHeavy9 = new ItemStack(ModItems.HeavyAmmo,9);
		ItemStack ammoHeavyContainer = new ItemStack(ModItems.HeavyAmmoContainer,1);
		ItemStack blazepowderStack = new ItemStack(Items.blaze_powder);
		ItemStack bucketRepairStack = new ItemStack(ModItems.BucketRepair);	
		ItemStack grudeStack = new ItemStack(ModItems.Grudge);
		ItemStack grudeStack9 = new ItemStack(ModItems.Grudge,9);
		ItemStack grudeBlock = new ItemStack(ModBlocks.BlockGrudge);
		ItemStack gunpowderStack = new ItemStack(Items.gunpowder);	
		ItemStack polymetalStack = new ItemStack(ModItems.Polymetal);
		ItemStack polymetalStack9 = new ItemStack(ModItems.Polymetal,9);
		ItemStack polymetalBlock = new ItemStack(ModBlocks.BlockPolymetal);
		ItemStack smallshipyardStack = new ItemStack(ModBlocks.BlockSmallShipyard);
		
		//SHAPELESS RECIPE
		//abyssium material:
		GameRegistry.addRecipe(new ShapelessOreRecipe(abyssiumStack, "ingotIron", grudeStack));
		GameRegistry.addRecipe(new ShapelessOreRecipe(bucketRepairStack, Items.lava_bucket, grudeStack));		
		//1 block to 9 items
		GameRegistry.addRecipe(new ShapelessOreRecipe(ammo9, ammoContainer));
		GameRegistry.addRecipe(new ShapelessOreRecipe(ammoHeavy9, ammoHeavyContainer));
		GameRegistry.addRecipe(new ShapelessOreRecipe(abyssiumStack9, abyssiumBlock));
		GameRegistry.addRecipe(new ShapelessOreRecipe(grudeStack9, grudeBlock));
		GameRegistry.addRecipe(new ShapelessOreRecipe(polymetalStack9, polymetalBlock));

		//SHAPED RECIPE
		//ammo material: copper/tin=8 iron/bronze=16 abyssium/gold/silver=32 diamond=64
		GameRegistry.addRecipe(new ShapedOreRecipe(ammo8,"iii","igi","ipi",'i',"ingotCopper",'g',grudeStack,'p',gunpowderStack));
		GameRegistry.addRecipe(new ShapedOreRecipe(ammo8,"iii","igi","ipi",'i',"ingotTin",'g',grudeStack,'p',gunpowderStack));
		GameRegistry.addRecipe(new ShapedOreRecipe(ammo16,"iii","igi","ipi",'i',"ingotIron",'g',grudeStack,'p',gunpowderStack));
		GameRegistry.addRecipe(new ShapedOreRecipe(ammo16,"iii","igi","ipi",'i',"ingotBronze",'g',grudeStack,'p',gunpowderStack));
		GameRegistry.addRecipe(new ShapedOreRecipe(ammo32,"iii","igi","ipi",'i',abyssiumStack,'g',grudeStack,'p',gunpowderStack));
		GameRegistry.addRecipe(new ShapedOreRecipe(ammo32,"iii","igi","ipi",'i',"ingotGold",'g',grudeStack,'p',gunpowderStack));
		GameRegistry.addRecipe(new ShapedOreRecipe(ammo32,"iii","igi","ipi",'i',"ingotSilver",'g',grudeStack,'p',gunpowderStack));
		GameRegistry.addRecipe(new ShapedOreRecipe(ammo64,"iii","igi","ipi",'i',"gemDiamond",'g',grudeStack,'p',gunpowderStack));		
		//heavy ammo material: copper/tin=1 iron/bronze=2 gold/silver=4 diamond=8
		GameRegistry.addRecipe(new ShapedOreRecipe(ammoHeavy1,"iii","igi","ipi",'i',"ingotCopper",'g',grudeStack,'p',blazepowderStack));
		GameRegistry.addRecipe(new ShapedOreRecipe(ammoHeavy2,"iii","igi","ipi",'i',"ingotTin",'g',grudeStack,'p',blazepowderStack));
		GameRegistry.addRecipe(new ShapedOreRecipe(ammoHeavy2,"iii","igi","ipi",'i',"ingotIron",'g',grudeStack,'p',blazepowderStack));
		GameRegistry.addRecipe(new ShapedOreRecipe(ammoHeavy2,"iii","igi","ipi",'i',"ingotBronze",'g',grudeStack,'p',blazepowderStack));
		GameRegistry.addRecipe(new ShapedOreRecipe(ammoHeavy4,"iii","igi","ipi",'i',abyssiumStack,'g',grudeStack,'p',blazepowderStack));
		GameRegistry.addRecipe(new ShapedOreRecipe(ammoHeavy4,"iii","igi","ipi",'i',"ingotGold",'g',grudeStack,'p',blazepowderStack));
		GameRegistry.addRecipe(new ShapedOreRecipe(ammoHeavy4,"iii","igi","ipi",'i',"ingotSilver",'g',grudeStack,'p',blazepowderStack));
		GameRegistry.addRecipe(new ShapedOreRecipe(ammoHeavy8,"iii","igi","ipi",'i',"gemDiamond",'g',grudeStack,'p',blazepowderStack));		
		//9 items to 1 block
		GameRegistry.addRecipe(new ShapedOreRecipe(ammoContainer,"aaa","aaa","aaa",'a',ammo1));
		GameRegistry.addRecipe(new ShapedOreRecipe(ammoHeavyContainer,"aaa","aaa","aaa",'a',ammoHeavy1));
		GameRegistry.addRecipe(new ShapedOreRecipe(abyssiumBlock,"aaa","aaa","aaa",'a',abyssiumStack));
		GameRegistry.addRecipe(new ShapedOreRecipe(grudeBlock,"aaa","aaa","aaa",'a',grudeStack));
		GameRegistry.addRecipe(new ShapedOreRecipe(polymetalBlock,"aaa","aaa","aaa",'a',polymetalStack));
		//small shipyard
		GameRegistry.addRecipe(new ShapedOreRecipe(smallshipyardStack,"glg","lol","ooo",'g',grudeStack,'l',Items.lava_bucket,'o',Blocks.obsidian));
		
	}

}