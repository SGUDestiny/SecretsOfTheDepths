package destiny.secretsofthevoid.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

import java.util.Collections;
import java.util.List;

public class BiomeCrateBlock extends Block {
    public BiomeCrateBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void spawnAfterBreak(BlockState pState, ServerLevel pLevel, BlockPos pPos, ItemStack pStack, boolean pDropExperience) {
        super.spawnAfterBreak(pState, pLevel, pPos, pStack, pDropExperience);
    }

/*    @Override
    public List<ItemStack> getDrops(BlockState pState, LootParams.Builder pParams) {
        ResourceLocation resourcelocation = this.getLootTable();
        if (resourcelocation == BuiltInLootTables.EMPTY) {
            return Collections.emptyList();
        } else {
            LootParams lootparams = pParams.withParameter(LootContextParams.BLOCK_STATE, pState).create(LootContextParamSets.BLOCK);
            ServerLevel serverlevel = lootparams.getLevel();
            LootTable loottable = serverlevel.getServer().getLootData().getLootTable(resourcelocation);
            return loottable.getRandomItems(lootparams);
        }
    }*/
}
