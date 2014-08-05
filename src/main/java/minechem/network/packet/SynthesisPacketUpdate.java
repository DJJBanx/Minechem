package minechem.network.packet;

import minechem.network.PacketDispatcher;
import minechem.tileentity.prefab.MinechemTileEntity;
import minechem.tileentity.synthesis.SynthesisTileEntity;
import net.minecraft.entity.player.EntityPlayer;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.relauncher.Side;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.util.ForgeDirection;

import java.io.IOException;

public class SynthesisPacketUpdate extends PacketDispatcher.AbstractPacket<SynthesisPacketUpdate>
{
	/** Client only reference to the tile entity that is the chemical synthesis machine. */
	private SynthesisTileEntity tileEntity;

	/** X coordinates of tile entity source. */
	private int tilePosX;

	/** Y coordinates of tile entity source. */
	private int tilePosY;

	/** Z coordinates of tile entity source. */
	private int tilePosZ;

	/** Energy stored */
	private double lastItemStoredEnergy;

	/** Maximum amount of energy. */
	private double lastItemStoredEnergyMaximum;


	public SynthesisPacketUpdate(SynthesisTileEntity tileSynthesis){
		// World position information.
		tilePosX = tileSynthesis.xCoord;
		tilePosY = tileSynthesis.yCoord;
		tilePosZ = tileSynthesis.zCoord;

		// Energy.
		lastItemStoredEnergy = tileSynthesis.getEnergy(ForgeDirection.UNKNOWN);
		lastItemStoredEnergyMaximum = tileSynthesis.getEnergyCapacity(ForgeDirection.UNKNOWN);

		// Tile entity specific.
		this.tileEntity = tileSynthesis;
	}

	public SynthesisPacketUpdate(){
		// Required for reflection.
	}

	@Override
	public void sendPacket (PacketBuffer out)throws IOException {
		// ------
		// SERVER
		// ------

		// World coordinate information.
		out.writeInt(tilePosX);
		out.writeInt(tilePosY);
		out.writeInt(tilePosZ);

		// Energy.
		out.writeLong((long) lastItemStoredEnergy);
		out.writeLong((long) lastItemStoredEnergyMaximum);
	}

	@Override
	public void receivePacket (PacketBuffer in)throws IOException {
		// ------
		// CLIENT
		// ------

		// World coordinate information.
		this.tilePosX = in.readInt();
		this.tilePosY = in.readInt();
		this.tilePosZ = in.readInt();

		// Energy.
		this.lastItemStoredEnergy = in.readLong();
		this.lastItemStoredEnergyMaximum = in.readLong();
	}

	@Override
	public void processPacket (EntityPlayer player){
		// ------
		// CLIENT PACKET
		// ------

		tileEntity = (SynthesisTileEntity) player.worldObj.getTileEntity(tilePosX, tilePosY, tilePosZ);
		if (tileEntity == null)
		{
			return;
		}

		// Energy.
		this.tileEntity.setEnergy(ForgeDirection.UNKNOWN, lastItemStoredEnergy);
		this.tileEntity.setEnergyCapacity((long) lastItemStoredEnergyMaximum);
	}
}
