package minechem.block;

import minechem.gui.CreativeTabMinechem;
import minechem.reference.Textures;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockUraniumOre extends Block
{
    public BlockUraniumOre()
    {
        super(Material.IRON);
        this.setCreativeTab(CreativeTabMinechem.CREATIVE_TAB_ITEMS);
        this.setUnlocalizedName("oreUranium");
        this.setHardness(4F);
    }
}
