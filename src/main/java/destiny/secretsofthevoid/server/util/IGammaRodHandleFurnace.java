package destiny.secretsofthevoid.server.util;

import net.minecraft.core.BlockPos;

public interface IGammaRodHandleFurnace
{
	boolean isFissioningGammaRod();
	void setFissioningGammaRod(boolean val);

	BlockPos getFurnacePos();
	void setFurnacePos(BlockPos pos);
}
