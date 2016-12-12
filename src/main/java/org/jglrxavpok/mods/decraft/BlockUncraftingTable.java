package org.jglrxavpok.mods.decraft;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jglrxavpok.mods.decraft.ModUncrafting;
import org.jglrxavpok.mods.decraft.stats.ModAchievements;


public class BlockUncraftingTable extends Block
{

	public BlockUncraftingTable()
    {
        super(Material.ROCK);
        setUnlocalizedName("uncrafting_table");
        setHardness(3.5F);
        setSoundType(SoundType.STONE);
        this.setCreativeTab(CreativeTabs.DECORATIONS);
    }

	
	@Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
//      if (!worldIn.isRemote)
//      {
//      }
		playerIn.openGui(ModUncrafting.instance, ModGuiHandler.GUI_TABLE, worldIn, pos.getX(), pos.getY(), pos.getZ());
		checkForPorteManteau(playerIn, worldIn, pos);
		return true;
    }

   
	private void checkForPorteManteau(EntityPlayer playerIn, World worldIn, BlockPos pos)
	{
		boolean furnace = false;
		boolean chest = false;
		boolean workbench = false;
		
		if (worldIn.getBlockState(pos.down()).getBlock() instanceof net.minecraft.block.BlockFence)
		{
			Block blockEast = worldIn.getBlockState(pos.east()).getBlock();
			Block blockWest = worldIn.getBlockState(pos.west()).getBlock();
			Block blockNorth = worldIn.getBlockState(pos.north()).getBlock();
			Block blockSouth = worldIn.getBlockState(pos.south()).getBlock();
			
			// check if one of the adjacent blocks is a furnace
			if (
				(blockNorth == Blocks.FURNACE || blockNorth == Blocks.LIT_FURNACE) ||
				(blockSouth == Blocks.FURNACE || blockSouth == Blocks.LIT_FURNACE) ||
				(blockEast == Blocks.FURNACE || blockEast == Blocks.LIT_FURNACE) ||
				(blockWest == Blocks.FURNACE || blockWest == Blocks.LIT_FURNACE)
			)
			{
				furnace = true;
			}
			
			// check if one of the adjacent blocks is a chest
			if (
				blockNorth == Blocks.CHEST || 
				blockSouth == Blocks.CHEST || 
				blockEast == Blocks.CHEST || 
				blockWest == Blocks.CHEST
			)
			{
				chest = true;
			}
			
			// check if one of the adjacent blocks is a crafting table
			if (
				blockNorth == Blocks.CRAFTING_TABLE || 
				blockSouth == Blocks.CRAFTING_TABLE || 
				blockEast == Blocks.CRAFTING_TABLE || 
				blockWest == Blocks.CRAFTING_TABLE
			)
			{
				workbench = true;
			}
			
			// if the block is adjacent to all three, trigger the achievement
			if ((furnace) && (chest) && (workbench)) 
			{
				playerIn.addStat(ModAchievements.porteManteauAchievement);
			}
		}
	}

}