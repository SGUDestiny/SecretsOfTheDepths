package destiny.secretsofthevoid.server.blocks;

import destiny.secretsofthevoid.server.SecretsOfTheVoid;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BiomeCrateBlock extends Block {
    public final ResourceLocation lootTableLocation;

    public BiomeCrateBlock(Properties pProperties, String lootTablePath) {
        super(pProperties);
        this.lootTableLocation = ResourceLocation.tryBuild(SecretsOfTheVoid.MODID, lootTablePath);
    }

    @Override
    public void playerDestroy(@NotNull Level level, @NotNull Player player, @NotNull BlockPos pos, @NotNull BlockState state,
                              @Nullable BlockEntity blockEntity, ItemStack toolStack) {
        if (toolStack.getItem() instanceof AxeItem) {
            openCrate(state, level, pos);
        } else {
            dropResources(state, level, pos, blockEntity, player, toolStack, false);
        }

        player.awardStat(Stats.BLOCK_MINED.get(this));
        player.causeFoodExhaustion(0.005F);
    }

    public void openCrate(BlockState state, Level level, BlockPos pos) {
        if (level instanceof ServerLevel) {
            getCrateDrops((ServerLevel)level, pos).forEach((lootStack) -> popResource(level, pos, lootStack));
            state.spawnAfterBreak((ServerLevel)level, pos, ItemStack.EMPTY, true);
        }
    }

    public List<ItemStack> getCrateDrops(ServerLevel serverLevel, BlockPos pos) {
        LootTable lootTable = serverLevel.getServer().getLootData().getLootTable(this.lootTableLocation);
        LootParams.Builder lootBuilder = new LootParams.Builder(serverLevel);

        Vec3 posVec = Vec3.atCenterOf(pos);
        LootParams lootParams = lootBuilder.withParameter(LootContextParams.ORIGIN, posVec).create(LootContextParamSets.CHEST);

        return lootTable.getRandomItems(lootParams);
    }
}
