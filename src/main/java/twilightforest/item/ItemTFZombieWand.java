package twilightforest.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import twilightforest.TwilightForestMod;
import twilightforest.entity.EntityTFLoyalZombie;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;



public class ItemTFZombieWand extends ItemTF {

	protected ItemTFZombieWand() {
		super();
        this.maxStackSize = 1;
        this.setMaxDamage(9);
		this.setCreativeTab(TFItems.creativeTab);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack par1ItemStack, World worldObj, EntityPlayer player, EnumHand hand) {
		
		if (par1ItemStack.getItemDamage() < this.getMaxDamage()) {
			player.setActiveHand(hand);
			
			if (!worldObj.isRemote) {
				// what block is the player pointing at?
				RayTraceResult mop = getPlayerPointVec(worldObj, player, 20.0F);
				
				if (mop != null) {
					// make a zombie there
					EntityTFLoyalZombie zombie = new EntityTFLoyalZombie(worldObj);
					zombie.setPositionAndRotation(mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord, 1.0F, 1.0F);  /// NPE here needs to be fixed
					zombie.setTamed(true);
					try {
						zombie.func_152115_b(player.getUniqueID().toString());
						//zombie.setOwner(player.getName());
					} catch (NoSuchMethodError ex) {
						// ignore?
						FMLLog.warning("[TwilightForest] Could not determine player name for loyal zombie, ignoring error.");
					}
					zombie.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 1200, 1));
					worldObj.spawnEntityInWorld(zombie);

					par1ItemStack.damageItem(1, player);
				}
			}
		}
		else {
			player.resetActiveHand();
		}
		
		return ActionResult.newResult(EnumActionResult.SUCCESS, par1ItemStack);
	}
	
	/**
	 * What block is the player pointing the wand at?
	 * 
	 * This very similar to player.rayTrace, but that method is not available on the server.
	 * 
	 * @return
	 */
	private RayTraceResult getPlayerPointVec(World worldObj, EntityPlayer player, float range) {
        Vec3d position = new Vec3d(player.posX, player.posY + player.getEyeHeight(), player.posZ);
        Vec3d look = player.getLook(1.0F);
        Vec3d dest = position.addVector(look.xCoord * range, look.yCoord * range, look.zCoord * range);
        return worldObj.rayTraceBlocks(position, dest);
	}


    /**
     * How long it takes to use or consume an item
     */
    @Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 30;
    }
    
    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    @Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return EnumAction.BOW;
    }
    
    /**
     * Return an item rarity from EnumRarity
     */    
    @Override
	public EnumRarity getRarity(ItemStack par1ItemStack) {
    	return EnumRarity.RARE;
	}
    
    /**
     * Display charges left in tooltip
     */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		par3List.add((par1ItemStack.getMaxDamage() -  par1ItemStack.getItemDamage()) + " charges left");
	}
}