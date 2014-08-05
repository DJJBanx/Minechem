package minechem.tileentity.decomposer;

import java.util.ArrayList;

import minechem.ModMinechem;
import minechem.block.BlockSimpleContainer;
import minechem.network.server.CommonProxy;
import minechem.utils.Reference;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class DecomposerBlock extends BlockSimpleContainer
{
	@SideOnly(Side.CLIENT)//Make sure this is only shown/used on client and not server
    private IIcon front;

    public DecomposerBlock()
    {
        super(Material.iron);
        setBlockName("blockChemicalDecomposer");
        setCreativeTab(ModMinechem.CREATIVE_TAB);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int par6, float par7, float par8, float par9)
    {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (tileEntity == null || entityPlayer.isSneaking())
            return false;
        entityPlayer.openGui(ModMinechem.INSTANCE, 0, world, x, y, z);
        return true;
    }

	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		return new DecomposerTileEntity();
	}

	//TODO: Find replacement
    @Override
    public void addStacksDroppedOnBlockBreak(TileEntity tileEntity, ArrayList<ItemStack> itemStacks)
    {
        DecomposerTileEntity decomposer = (DecomposerTileEntity) tileEntity;
        for (int slot = 0; slot < decomposer.getSizeInventory(); slot++)
        {
            ItemStack itemstack = decomposer.getStackInSlot(slot);
            if (itemstack != null)
            {
                itemStacks.add(itemstack);
            }
        }
        return;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ir)
    {
        blockIcon = ir.registerIcon(Reference.DECOMPOSER_TEX);
        front = ir.registerIcon(Reference.DECOMPOSER_FRONT_TEX);
        ModMinechem.INSTANCE.textureHook(ir);
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public int getRenderType()
    {
        return CommonProxy.RENDER_ID;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }
}
