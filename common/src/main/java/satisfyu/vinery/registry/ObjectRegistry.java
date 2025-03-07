package satisfyu.vinery.registry;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import de.cristelknight.doapi.Util;
import de.cristelknight.doapi.common.block.ChairBlock;
import dev.architectury.core.item.ArchitecturySpawnEggItem;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.material.PushReaction;
import org.jetbrains.annotations.NotNull;
import satisfyu.vinery.Vinery;
import satisfyu.vinery.VineryIdentifier;
import satisfyu.vinery.block.FlowerPotBlock;
import satisfyu.vinery.block.*;
import satisfyu.vinery.block.grape.GrapeBush;
import satisfyu.vinery.block.grape.GrapeVineBlock;
import satisfyu.vinery.block.grape.SavannaGrapeBush;
import satisfyu.vinery.block.grape.TaigaGrapeBush;
import satisfyu.vinery.block.stem.LatticeStemBlock;
import satisfyu.vinery.block.stem.PaleStemBlock;
import satisfyu.vinery.block.storage.*;
import satisfyu.vinery.item.*;
import satisfyu.vinery.util.GeneralUtil;
import satisfyu.vinery.util.VineryFoodComponent;
import satisfyu.vinery.world.VineryConfiguredFeatures;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class ObjectRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Vinery.MODID, Registries.ITEM);
    public static final Registrar<Item> ITEM_REGISTRAR = ITEMS.getRegistrar();
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Vinery.MODID, Registries.BLOCK);
    public static final Registrar<Block> BLOCK_REGISTRAR = BLOCKS.getRegistrar();

    //Grapes
    public static final RegistrySupplier<Block> RED_GRAPE_BUSH = registerB("red_grape_bush", () -> new GrapeBush(getBushSettings(), GrapeTypes.RED));
    public static final RegistrySupplier<Item> RED_GRAPE_SEEDS = registerI("red_grape_seeds", () -> new GrapeBushSeedItem(RED_GRAPE_BUSH.get(), getSettings(), GrapeTypes.RED));
    public static final RegistrySupplier<Item> RED_GRAPE = registerI("red_grape", () -> new GrapeItem(getSettings().food(Foods.SWEET_BERRIES), GrapeTypes.RED, RED_GRAPE_SEEDS.get()));
    public static final RegistrySupplier<Block> WHITE_GRAPE_BUSH = registerB("white_grape_bush", () -> new GrapeBush(getBushSettings(), GrapeTypes.WHITE));
    public static final RegistrySupplier<Item> WHITE_GRAPE_SEEDS = registerI("white_grape_seeds", () -> new GrapeBushSeedItem(WHITE_GRAPE_BUSH.get(), getSettings(), GrapeTypes.WHITE));
    public static final RegistrySupplier<Item> WHITE_GRAPE = registerI("white_grape", () -> new GrapeItem(getSettings().food(Foods.SWEET_BERRIES), GrapeTypes.WHITE, WHITE_GRAPE_SEEDS.get()));
    public static final RegistrySupplier<Block> SAVANNA_RED_GRAPE_BUSH = registerB("savanna_grape_bush_red", () -> new SavannaGrapeBush(getBushSettings(), GrapeTypes.SAVANNA_RED));
    public static final RegistrySupplier<Item> SAVANNA_RED_GRAPE_SEEDS = registerI("savanna_grape_seeds_red", () -> new GrapeBushSeedItem(SAVANNA_RED_GRAPE_BUSH.get(), getSettings(), GrapeTypes.SAVANNA_RED));
    public static final RegistrySupplier<Item> SAVANNA_RED_GRAPE = registerI("savanna_grapes_red", () -> new GrapeItem(getSettings().food(Foods.SWEET_BERRIES), GrapeTypes.SAVANNA_RED, ObjectRegistry.SAVANNA_RED_GRAPE_SEEDS.get()));
    public static final RegistrySupplier<Block> SAVANNA_WHITE_GRAPE_BUSH = registerB("savanna_grape_bush_white", () -> new SavannaGrapeBush(getBushSettings(), GrapeTypes.SAVANNA_WHITE));
    public static final RegistrySupplier<Item> SAVANNA_WHITE_GRAPE_SEEDS = registerI("savanna_grape_seeds_white", () -> new GrapeBushSeedItem(SAVANNA_WHITE_GRAPE_BUSH.get(), getSettings(), GrapeTypes.SAVANNA_WHITE));
    public static final RegistrySupplier<Item> SAVANNA_WHITE_GRAPE = registerI("savanna_grapes_white", () -> new GrapeItem(getSettings().food(Foods.SWEET_BERRIES), GrapeTypes.SAVANNA_WHITE, ObjectRegistry.SAVANNA_WHITE_GRAPE_SEEDS.get()));
    public static final RegistrySupplier<Block> TAIGA_RED_GRAPE_BUSH = registerB("taiga_grape_bush_red", () -> new TaigaGrapeBush(getBushSettings(), GrapeTypes.TAIGA_RED));
    public static final RegistrySupplier<Item> TAIGA_RED_GRAPE_SEEDS = registerI("taiga_grape_seeds_red", () -> new GrapeBushSeedItem(TAIGA_RED_GRAPE_BUSH.get(), getSettings(), GrapeTypes.TAIGA_RED));
    public static final RegistrySupplier<Item> TAIGA_RED_GRAPE = registerI("taiga_grapes_red", () -> new GrapeItem(getSettings().food(Foods.SWEET_BERRIES), GrapeTypes.TAIGA_RED, ObjectRegistry.TAIGA_RED_GRAPE_SEEDS.get()));
    public static final RegistrySupplier<Block> TAIGA_WHITE_GRAPE_BUSH = registerB("taiga_grape_bush_white", () -> new TaigaGrapeBush(getBushSettings(), GrapeTypes.TAIGA_WHITE));
    public static final RegistrySupplier<Item> TAIGA_WHITE_GRAPE_SEEDS = registerI("taiga_grape_seeds_white", () -> new GrapeBushSeedItem(TAIGA_WHITE_GRAPE_BUSH.get(), getSettings(), GrapeTypes.TAIGA_WHITE));
    public static final RegistrySupplier<Item> TAIGA_WHITE_GRAPE = registerI("taiga_grapes_white", () -> new GrapeItem(getSettings().food(Foods.SWEET_BERRIES), GrapeTypes.TAIGA_WHITE,ObjectRegistry.TAIGA_WHITE_GRAPE_SEEDS.get()));
    public static final RegistrySupplier<Block> JUNGLE_RED_GRAPE_BUSH = registerB("jungle_grape_bush_red", () -> new GrapeVineBlock(getBushSettings(), GrapeTypes.JUNGLE_RED));
    public static final RegistrySupplier<Item> JUNGLE_RED_GRAPE_SEEDS = registerI("jungle_grape_seeds_red", () -> new GrapeBushSeedItem(JUNGLE_RED_GRAPE_BUSH.get(), getSettings(), GrapeTypes.JUNGLE_RED));
    public static final RegistrySupplier<Item> JUNGLE_RED_GRAPE = registerI("jungle_grapes_red", () -> new GrapeItem(getSettings().food(Foods.BAKED_POTATO), GrapeTypes.JUNGLE_RED, ObjectRegistry.JUNGLE_RED_GRAPE_SEEDS.get()));
    public static final RegistrySupplier<Block> JUNGLE_WHITE_GRAPE_BUSH = registerB("jungle_grape_bush_white", () -> new GrapeVineBlock(getBushSettings(), GrapeTypes.JUNGLE_WHITE));
    public static final RegistrySupplier<Item> JUNGLE_WHITE_GRAPE_SEEDS = registerI("jungle_grape_seeds_white", () -> new GrapeBushSeedItem(JUNGLE_WHITE_GRAPE_BUSH.get(), getSettings(), GrapeTypes.JUNGLE_WHITE));
    public static final RegistrySupplier<Item> JUNGLE_WHITE_GRAPE = registerI("jungle_grapes_white", () -> new GrapeItem(getSettings().food(Foods.BAKED_POTATO), GrapeTypes.JUNGLE_WHITE, ObjectRegistry.JUNGLE_WHITE_GRAPE_SEEDS.get()));    public static final RegistrySupplier<Block> CHERRY_SAPLING = registerB("cherry_sapling", () -> new SaplingBlock(new AbstractTreeGrower() {
        @Override
        protected @NotNull ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource random, boolean bees) {
            if (random.nextBoolean()) return VineryConfiguredFeatures.CHERRY_KEY;
            return VineryConfiguredFeatures.CHERRY_VARIANT_KEY;
        }

    }, BlockBehaviour.Properties.of().noCollission().randomTicks().instabreak().sound(SoundType.GRASS)));
    public static final RegistrySupplier<Item>  CHERRY_SAPLING_ITEM = registerI("cherry_sapling", () -> new BlockItem(CHERRY_SAPLING.get(), getSettings()));
    public static final RegistrySupplier<Block> APPLE_TREE_SAPLING = registerB("apple_tree_sapling", () -> new SaplingBlock(new AbstractTreeGrower() {
        @Override
        protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource random, boolean bees) {
            if (random.nextBoolean()) {
                if (bees) return VineryConfiguredFeatures.APPLE_BEE_KEY;
                return VineryConfiguredFeatures.APPLE_KEY;
            } else {
                if (bees) return VineryConfiguredFeatures.APPLE_VARIANT_WITH_BEE_KEY;
                return VineryConfiguredFeatures.APPLE_VARIANT_KEY;
            }
        }
    }, BlockBehaviour.Properties.of().noCollission().randomTicks().instabreak().sound(SoundType.GRASS)));
    public static final RegistrySupplier<Item>  APPLE_TREE_SAPLING_ITEM = registerI("apple_tree_sapling", () -> new BlockItem(APPLE_TREE_SAPLING.get(), getSettings()));
    public static final RegistrySupplier<Item> CHERRY = registerI("cherry", () -> new CherryItem(getSettings().food(Foods.COOKIE)));
    public static final RegistrySupplier<Item> ROTTEN_CHERRY = registerI("rotten_cherry", () -> new RottenCherryItem(getSettings().food(Foods.POISONOUS_POTATO)));
    public static final RegistrySupplier<Block> GRAPEVINE_LEAVES = registerB("grapevine_leaves", () -> new LeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)));
    public static final RegistrySupplier<Item>  GRAPEVINE_LEAVES_ITEM = registerI("grapevine_leaves", () -> new BlockItem(GRAPEVINE_LEAVES.get(), getSettings()));
    public static final RegistrySupplier<Block> CHERRY_LEAVES = registerB("cherry_leaves", () -> new CherryLeaves(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)));
    public static final RegistrySupplier<Item>  CHERRY_LEAVES_ITEM = registerI("cherry_leaves", () -> new BlockItem(CHERRY_LEAVES.get(), getSettings()));
    public static final RegistrySupplier<Block> APPLE_LEAVES = registerB("apple_leaves", () -> new AppleLeaves(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)));
    public static final RegistrySupplier<Item>  APPLE_LEAVES_ITEM = registerI("apple_leaves", () -> new BlockItem(APPLE_LEAVES.get(), getSettings()));
    public static final RegistrySupplier<Block> WHITE_GRAPE_CRATE = registerB("white_grape_crate", () -> new Block(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Item>  WHITE_GRAPE_CRATE_ITEM = registerI("white_grape_crate", () -> new BlockItem(WHITE_GRAPE_CRATE.get(), getSettings()));
    public static final RegistrySupplier<Block> RED_GRAPE_CRATE = registerB("red_grape_crate", () -> new Block(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Item>  RED_GRAPE_CRATE_ITEM = registerI("red_grape_crate", () -> new BlockItem(RED_GRAPE_CRATE.get(), getSettings()));
    public static final RegistrySupplier<Block> CHERRY_CRATE = registerB("cherry_crate", () -> new Block(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Item>  CHERRY_CRATE_ITEM = registerI("cherry_crate", () -> new BlockItem(CHERRY_CRATE.get(), getSettings()));
    public static final RegistrySupplier<Block> APPLE_CRATE = registerB("apple_crate", () -> new Block(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Item>  APPLE_CRATE_ITEM = registerI("apple_crate", () -> new BlockItem(APPLE_CRATE.get(), getSettings()));
    public static final RegistrySupplier<Block> GRAPEVINE_LATTICE = registerB("grapevine_lattice", () -> new LatticeStemBlock(getGrapevineSettings()));
    public static final RegistrySupplier<Item>  GRAPEVINE_LATTICE_ITEM = registerI("grapevine_lattice", () -> new BlockItem(GRAPEVINE_LATTICE.get(), getSettings()));
    public static final RegistrySupplier<Block> GRAPEVINE_POT = registerB("grapevine_pot", () -> new GrapevinePotBlock(BlockBehaviour.Properties.copy(Blocks.BARREL)));
    public static final RegistrySupplier<Item>  GRAPEVINE_POT_ITEM = registerI("grapevine_pot", () -> new BlockItem(GRAPEVINE_POT.get(), getSettings()));
    public static final RegistrySupplier<Block> FERMENTATION_BARREL = registerB("fermentation_barrel", () -> new FermentationBarrelBlock(BlockBehaviour.Properties.copy(Blocks.BARREL).noOcclusion()));
    public static final RegistrySupplier<Item>  FERMENTATION_BARREL_ITEM = registerI("fermentation_barrel", () -> new BlockItem(FERMENTATION_BARREL.get(), getSettings()));
    public static final RegistrySupplier<Block> APPLE_PRESS = registerB("wine_press", () -> new ApplePressBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Item>  WINE_PRESS_ITEM = registerI("wine_press", () -> new BlockItem(APPLE_PRESS.get(), getSettings()));
    public static final RegistrySupplier<Block> CHAIR = registerB("chair", () -> new ChairBlock(BlockBehaviour.Properties.of().strength(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Item>  CHAIR_ITEM = registerI("chair", () -> new BlockItem(CHAIR.get(), getSettings()));
    public static final RegistrySupplier<Block> TABLE = registerB("table", () -> new TableBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistrySupplier<Item>  TABLE_ITEM = registerI("table", () -> new BlockItem(TABLE.get(), getSettings()));
    public static final RegistrySupplier<Block> WINE_RACK_3 = registerB("wine_rack_3", () -> new WineRackStorageBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD), VinerySoundEvents.WINE_RACK_3_OPEN.get(), VinerySoundEvents.WINE_RACK_3_CLOSE.get()));
    public static final RegistrySupplier<Item>  WINE_RACK_3_ITEM = registerI("wine_rack_3", () -> new BlockItem(WINE_RACK_3.get(), getSettings()));
    public static final RegistrySupplier<Block> WINE_RACK_5 = registerB("wine_rack_5", () -> new WineRackStorageBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD), VinerySoundEvents.WINE_RACK_5_OPEN.get(), VinerySoundEvents.WINE_RACK_5_CLOSE.get()));
    public static final RegistrySupplier<Item>  WINE_RACK_5_ITEM = registerI("wine_rack_5", () -> new BlockItem(WINE_RACK_5.get(), getSettings()));
    public static final RegistrySupplier<Block> BARREL = registerB("barrel", () -> new BarrelBlock(BlockBehaviour.Properties.copy(Blocks.BARREL)));
    public static final RegistrySupplier<Item>  BARREL_ITEM = registerI("barrel", () -> new BlockItem(BARREL.get(), getSettings()));
    public static final RegistrySupplier<Block> APPLE_LOG = registerB("apple_log", GeneralUtil::logBlock);
    public static final RegistrySupplier<Item>  APPLE_LOG_ITEM = registerI("apple_log", () -> new BlockItem(APPLE_LOG.get(), getSettings()));
    public static final RegistrySupplier<Block> APPLE_WOOD = registerB("apple_wood", GeneralUtil::logBlock);
    public static final RegistrySupplier<Item>  APPLE_ITEM = registerI("apple_wood", () -> new BlockItem(APPLE_WOOD.get(), getSettings()));
    public static final RegistrySupplier<Block> STRIPPED_CHERRY_LOG = registerB("stripped_cherry_log", GeneralUtil::logBlock);
    public static final RegistrySupplier<Item>  STRIPPED_CHERRY_LOG_ITEM = registerI("stripped_cherry_log", () -> new BlockItem(STRIPPED_CHERRY_LOG.get(), getSettings()));
    public static final RegistrySupplier<Block> CHERRY_LOG = registerB("cherry_log", GeneralUtil::logBlock);
    public static final RegistrySupplier<Item>  CHERRY_LOG_ITEM = registerI("cherry_log", () -> new BlockItem(CHERRY_LOG.get(), getSettings()));
    public static final RegistrySupplier<Block> STRIPPED_CHERRY_WOOD = registerB("stripped_cherry_wood", GeneralUtil::logBlock);
    public static final RegistrySupplier<Item>  STRIPPED_CHERRY_WOOD_ITEM = registerI("stripped_cherry_wood", () -> new BlockItem(STRIPPED_CHERRY_WOOD.get(), getSettings()));
    public static final RegistrySupplier<Block> CHERRY_WOOD = registerB("cherry_wood", GeneralUtil::logBlock);
    public static final RegistrySupplier<Item>  CHERRY_WOOD_ITEM = registerI("cherry_wood", () -> new BlockItem(CHERRY_WOOD.get(), getSettings()));
    public static final RegistrySupplier<Block> CHERRY_BEAM = registerB("cherry_beam", GeneralUtil::logBlock);
    public static final RegistrySupplier<Item>  CHERRY_BEAM_ITEM = registerI("cherry_beam", () -> new BlockItem(CHERRY_BEAM.get(), getSettings()));
    public static final RegistrySupplier<Block> CHERRY_PLANKS = registerB("cherry_planks", () -> new Block(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Item> CHERRY_PLANK_ITEM = registerI("cherry_planks", () -> new BlockItem(CHERRY_PLANKS.get(), getSettings()));
    public static final RegistrySupplier<Block> CHERRY_FLOORBOARD = registerB("cherry_floorboard", () -> new Block(BlockBehaviour.Properties.copy(CHERRY_PLANKS.get())));
    public static final RegistrySupplier<Item>  CHERRY_FLOORBOARD_ITEM = registerI("cherry_floorboard", () -> new BlockItem(CHERRY_FLOORBOARD.get(), getSettings()));
    public static final RegistrySupplier<Block> CHERRY_STAIRS = registerB("cherry_stairs", () -> new StairBlock(CHERRY_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(CHERRY_PLANKS.get())));
    public static final RegistrySupplier<Item>  CHERRY_STAIRS_ITEM = registerI("cherry_stairs", () -> new BlockItem(CHERRY_STAIRS.get(), getSettings()));
    public static final RegistrySupplier<Block> CHERRY_SLAB = registerB("cherry_slab", () -> new SlabBlock(getSlabSettings()));
    public static final RegistrySupplier<Item>  CHERRY_SLAB_ITEM = registerI("cherry_slab", () -> new BlockItem(CHERRY_SLAB.get(), getSettings()));
    public static final RegistrySupplier<Block> CHERRY_FENCE = registerB("cherry_fence", () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE)));
    public static final RegistrySupplier<Item>  CHERRY_FENCE_ITEM = registerI("cherry_fence", () -> new BlockItem(CHERRY_FENCE.get(), getSettings()));
    public static final RegistrySupplier<Block> CHERRY_FENCE_GATE = registerB("cherry_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE), WoodType.CHERRY));
    public static final RegistrySupplier<Item>  CHERRY_FENCE_GATE_ITEM = registerI("cherry_fence_gate", () -> new BlockItem(CHERRY_FENCE_GATE.get(), getSettings()));
    public static final RegistrySupplier<Block> CHERRY_BUTTON = registerB("cherry_button", () -> woodenButton(BlockSetType.CHERRY, FeatureFlags.VANILLA));
    public static final RegistrySupplier<Item>  CHERRY_BUTTON_ITEM = registerI("cherry_button", () -> new BlockItem(CHERRY_BUTTON.get(), getSettings()));
    public static final RegistrySupplier<Block> CHERRY_PRESSURE_PLATE = registerB("cherry_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE), BlockSetType.CHERRY));
    public static final RegistrySupplier<Item>  CHERRY_PRESSURE_PLATE_ITEM = registerI("cherry_pressure_plate", () -> new BlockItem(CHERRY_PRESSURE_PLATE.get(), getSettings()));
    public static final RegistrySupplier<Block> CHERRY_DOOR = registerB("cherry_door", () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_DOOR), BlockSetType.CHERRY));
    public static final RegistrySupplier<Item>  CHERRY_DOOR_ITEM = registerI("cherry_door", () -> new BlockItem(CHERRY_DOOR.get(), getSettings()));
    public static final RegistrySupplier<Block> CHERRY_TRAPDOOR = registerB("cherry_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_TRAPDOOR), BlockSetType.CHERRY));
    public static final RegistrySupplier<Item>  CHERRY_TRAPDOOR_ITEM = registerI("cherry_trapdoor", () -> new BlockItem(CHERRY_TRAPDOOR.get(), getSettings()));
    public static final RegistrySupplier<Block> WINDOW = registerB("window", () -> new WindowBlock(BlockBehaviour.Properties.copy(Blocks.GLASS_PANE)));
    public static final RegistrySupplier<Item>  WINDOW_ITEM = registerI("window", () -> new BlockItem(WINDOW.get(), getSettings()));
    public static final RegistrySupplier<Block> LOAM = registerB("loam", () -> new Block(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.MUD)));
    public static final RegistrySupplier<Item>  LOAM_ITEM = registerI("loam", () -> new BlockItem(LOAM.get(), getSettings()));
    public static final RegistrySupplier<Block> LOAM_STAIRS = registerB("loam_stairs", () -> new StairBlock(LOAM.get().defaultBlockState(), BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.MUD)));
    public static final RegistrySupplier<Item>  LOAM_STAIRS_ITEM = registerI("loam_stairs", () -> new BlockItem(LOAM_STAIRS.get(), getSettings()));
    public static final RegistrySupplier<Block> LOAM_SLAB = registerB("loam_slab", () -> new SlabBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.MUD)));
    public static final RegistrySupplier<Item>  LOAM_SLAB_ITEM = registerI("loam_slab", () -> new BlockItem(LOAM_SLAB.get(), getSettings()));
    public static final RegistrySupplier<Block> COARSE_DIRT_SLAB = registerB("coarse_dirt_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.COARSE_DIRT)));
    public static final RegistrySupplier<Item>  COARSE_DIRT_SLAB_ITEM = registerI("coarse_dirt_slab", () -> new BlockItem(COARSE_DIRT_SLAB.get(), getSettings()));
    public static final RegistrySupplier<Block> DIRT_SLAB = registerB("dirt_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.DIRT)));
    public static final RegistrySupplier<Item>  DIRT_SLAB_ITEM = registerI("dirt_slab", () -> new BlockItem(DIRT_SLAB.get(), getSettings()));
    public static final RegistrySupplier<Block> GRASS_SLAB = registerB("grass_slab", () -> new SpreadableGrassSlab(BlockBehaviour.Properties.copy(Blocks.GRASS_BLOCK)));
    public static final RegistrySupplier<Item>  GRASS_SLAB_ITEM = registerI("grass_slab", () -> new BlockItem(GRASS_SLAB.get(), getSettings()));
    public static final RegistrySupplier<Item>  APPLE_JUICE = registerI("apple_juice", () -> new Item(getSettings()));
    public static final RegistrySupplier<Item> RED_GRAPEJUICE_WINE_BOTTLE = registerI("red_grapejuice_wine_bottle", () -> new Item(getSettings()));
    public static final RegistrySupplier<Item> WHITE_GRAPEJUICE_WINE_BOTTLE = registerI("white_grapejuice_wine_bottle", () -> new Item(getSettings()));
    public static final RegistrySupplier<Item> SAVANNA_RED_GRAPEJUICE_BOTTLE = registerI("savanna_red_grapejuice_bottle",  () -> new Item(getSettings()));
    public static final RegistrySupplier<Item> SAVANNA_WHITE_GRAPEJUICE_BOTTLE = registerI("savanna_white_grapejuice_bottle",  () -> new Item(getSettings()));
    public static final RegistrySupplier<Item> TAIGA_RED_GRAPEJUICE_BOTTLE = registerI("taiga_red_grapejuice_bottle",  () -> new Item(getSettings()));
    public static final RegistrySupplier<Item> TAIGA_WHITE_GRAPEJUICE_BOTTLE = registerI("taiga_white_grapejuice_bottle", () -> new Item(getSettings()));
    public static final RegistrySupplier<Item> JUNGLE_RED_GRAPEJUICE_BOTTLE = registerI("jungle_red_grapejuice_bottle",  () -> new Item(getSettings()));
    public static final RegistrySupplier<Item> JUNGLE_WHITE_GRAPEJUICE_BOTTLE = registerI("jungle_white_grapejuice_bottle",  () -> new Item(getSettings()));
    public static final RegistrySupplier<Block> STAL_WINE = registerB("stal_wine", () -> new WineBottleBlock(getWineSettings(), 3));
    public static final RegistrySupplier<Item>  STAL_WINE_ITEM = registerI("stal_wine", () -> new DrinkBlockSmallItem(STAL_WINE.get(), getWineItemSettings(VineryEffects.IMPROVED_REGENERATION.get())));
    public static final RegistrySupplier<Block> KELP_CIDER = registerB("kelp_cider", () -> new WineBottleBlock(getWineSettings(), 3));
    public static final RegistrySupplier<Item>  KELP_CIDER_ITEM = registerI("kelp_cider", () -> new DrinkBlockSmallItem(KELP_CIDER.get(), getWineItemSettings(VineryEffects.IMPROVED_WATER_BREATHING.get())));
    public static final RegistrySupplier<Block> STRAD_WINE = registerB("strad_wine", () -> new WineBottleBlock(getWineSettings(), 2));
    public static final RegistrySupplier<Item>  STRAD_WINE_ITEM = registerI("strad_wine", () -> new DrinkBlockBigItem(STRAD_WINE.get(), getWineItemSettings(VineryEffects.IMPROVED_ABSORBTION.get())));
    public static final RegistrySupplier<Block> MAGNETIC_WINE = registerB("magnetic_wine", () -> new WineBottleBlock(getWineSettings(), 1));
    public static final RegistrySupplier<Item>  MAGNETIC_WINE_ITEM = registerI("magnetic_wine", () -> new DrinkBlockBigItem(MAGNETIC_WINE.get(), getWineItemSettings(VineryEffects.MAGNET.get())));
    public static final RegistrySupplier<Block> CHORUS_WINE = registerB("chorus_wine", () -> new WineBottleBlock(getWineSettings(), 1));
    public static final RegistrySupplier<Item>  CHORUS_WINE_ITEM = registerI("chorus_wine", () -> new DrinkBlockBigItem(CHORUS_WINE.get(), getWineItemSettings(VineryEffects.TELEPORT.get(), 1)));
    public static final RegistrySupplier<Block> AEGIS_WINE = registerB("aegis_wine", () -> new WineBottleBlock(getWineSettings(), 2));
    public static final RegistrySupplier<Item>  AEGIS_WINE_ITEM = registerI("aegis_wine", () -> new DrinkBlockBigItem(AEGIS_WINE.get(), getWineItemSettings(MobEffects.NIGHT_VISION)));
    public static final RegistrySupplier<Block> SOLARIS_WINE = registerB("solaris_wine", () -> new WineBottleBlock(getWineSettings(), 3));
    public static final RegistrySupplier<Item>  SOLARIS_WINE_ITEM = registerI("solaris_wine", () -> new DrinkBlockSmallItem(SOLARIS_WINE.get(), getWineItemSettings(VineryEffects.IMPROVED_STRENGTH.get())));
    public static final RegistrySupplier<Block> NOIR_WINE = registerB("noir_wine", () -> new WineBottleBlock(getWineSettings(), 3));
    public static final RegistrySupplier<Item>  NOIR_WINE_ITEM = registerI("noir_wine", () -> new DrinkBlockSmallItem(NOIR_WINE.get(), getWineItemSettings(MobEffects.HEAL, 5)));
    public static final RegistrySupplier<Block> BOLVAR_WINE = registerB("bolvar_wine", () -> new WineBottleBlock(getWineSettings(), 3));
    public static final RegistrySupplier<Item>  BOLVAR_WINE_ITEM = registerI("bolvar_wine", () -> new DrinkBlockSmallItem(BOLVAR_WINE.get(), getWineItemSettings(VineryEffects.IMPROVED_HASTE.get())));
    public static final RegistrySupplier<Block> CHERRY_WINE = registerB("cherry_wine", () -> new WineBottleBlock(getWineSettings(), 3));
    public static final RegistrySupplier<Item>  CHERRY_WINE_ITEM = registerI("cherry_wine", () -> new DrinkBlockSmallItem(CHERRY_WINE.get(), getWineItemSettings(MobEffects.REGENERATION)));
    public static final RegistrySupplier<Block> CLARK_WINE = registerB("clark_wine", () -> new WineBottleBlock(getWineSettings(), 3));
    public static final RegistrySupplier<Item>  CLARK_WINE_ITEM = registerI("clark_wine", () -> new DrinkBlockSmallItem(CLARK_WINE.get(), getWineItemSettings(VineryEffects.IMPROVED_JUMP_BOOST.get())));
    public static final RegistrySupplier<Block> JELLIE_WINE = registerB("jellie_wine", () -> new WineBottleBlock(getWineSettings(), 1));
    public static final RegistrySupplier<Item>  JELLIE_WINE_ITEM = registerI("jellie_wine", () -> new DrinkBlockBigItem(JELLIE_WINE.get(), getWineItemSettings(VineryEffects.JELLIE.get())));
    public static final RegistrySupplier<Block> APPLE_CIDER = registerB("apple_cider", () -> new WineBottleBlock(getWineSettings(), 2));
    public static final RegistrySupplier<Item>  APPLE_CIDER_ITEM = registerI("apple_cider", () -> new DrinkBlockBigItem(APPLE_CIDER.get(), getWineItemSettings(VineryEffects.IMPROVED_REGENERATION.get(), 15)));
    public static final RegistrySupplier<Block> APPLE_WINE = registerB("apple_wine", () -> new WineBottleBlock(getWineSettings(), 3));
    public static final RegistrySupplier<Item>  APPLE_WINE_ITEM = registerI("apple_wine", () -> new DrinkBlockBigItem(APPLE_WINE.get(), getWineItemSettings(VineryEffects.IMPROVED_INSTANT_HEALTH.get(), 10)));
    public static final RegistrySupplier<Block> RED_WINE = registerB("red_wine", () -> new WineBottleBlock(getWineSettings(), 3));
    public static final RegistrySupplier<Item>  RED_WINE_ITEM = registerI("red_wine", () -> new DrinkBlockSmallItem(RED_WINE.get(), getWineItemSettings(VineryEffects.IMPROVED_FIRE_RESISTANCE.get())));
    public static final RegistrySupplier<Block> PRAETORIAN_WINE = registerB("praetorian_wine", () -> new WineBottleBlock(getWineSettings(), 2));
    public static final RegistrySupplier<Item>  PRAETORIAN_WINE_ITEM = registerI("praetorian_wine", () -> new DrinkBlockBigItem(PRAETORIAN_WINE.get(), getWineItemSettings(MobEffects.GLOWING)));
    public static final RegistrySupplier<Block> CHENET_WINE = registerB("chenet_wine", () -> new WineBottleBlock(getWineSettings(), 2));
    public static final RegistrySupplier<Item>  CHENET_WINE_ITEM = registerI("chenet_wine", () -> new DrinkBlockBigItem(CHENET_WINE.get(), getWineItemSettings(VineryEffects.IMPROVED_SPEED.get())));
    public static final RegistrySupplier<Block> KING_DANIS_WINE = registerB("king_danis_wine", () -> new WineBottleBlock(getWineSettings(), 1));
    public static final RegistrySupplier<Item>  KING_DANIS_WINE_ITEM = registerI("king_danis_wine", () -> new DrinkBlockBigItem(KING_DANIS_WINE.get(), getWineItemSettings(VineryEffects.IMPROVED_INSTANT_HEALTH.get(), 45)));
    public static final RegistrySupplier<Block> MELLOHI_WINE = registerB("mellohi_wine", () -> new WineBottleBlock(getWineSettings(), 2));
    public static final RegistrySupplier<Item>  MELLOHI_WINE_ITEM = registerI("mellohi_wine", () -> new DrinkBlockBigItem(MELLOHI_WINE.get(), getWineItemSettings(VineryEffects.IMPROVED_FIRE_RESISTANCE.get())));
    public static final RegistrySupplier<Block> JO_SPECIAL_MIXTURE = registerB("jo_special_mixture", () -> new WineBottleBlock(getWineSettings(), 1));
    public static final RegistrySupplier<Item>  JO_SPECIAL_MIXTURE_ITEM = registerI("jo_special_mixture", () -> new DrinkBlockBigItem(JO_SPECIAL_MIXTURE.get(), getWineItemSettings(VineryEffects.TRIPPY.get())));
    public static final RegistrySupplier<Block> CRISTEL_WINE = registerB("cristel_wine", () -> new WineBottleBlock(getWineSettings(), 1));
    public static final RegistrySupplier<Item>  CRISTEL_WINE_ITEM = registerI("cristel_wine", () -> new DrinkBlockBigItem(CRISTEL_WINE.get(), getWineItemSettings(VineryEffects.EXPERIENCE_EFFECT.get())));
    public static final RegistrySupplier<Block> GLOWING_WINE = registerB("glowing_wine", () -> new WineBottleBlock(getWineSettings(), 3));
    public static final RegistrySupplier<Item>  GLOWING_WINE_ITEM = registerI("glowing_wine", () -> new DrinkBlockBigItem(GLOWING_WINE.get(), getWineItemSettings(MobEffects.GLOWING)));
    public static final RegistrySupplier<Block> CREEPERS_CRUSH = registerB("creepers_crush", () -> new WineBottleBlock(getWineSettings(), 2));
    public static final RegistrySupplier<Item>  CREEPERS_CRUSH_ITEM = registerI("creepers_crush", () -> new DrinkBlockBigItem(CREEPERS_CRUSH.get(), getWineItemSettings(VineryEffects.CREEPER_EFFECT.get(), 1)));
    public static final RegistrySupplier<Block> BOTTLE_MOJANG_NOIR = registerB("bottle_mojang_noir", () -> new WineBottleBlock(getWineSettings(), 3));
    public static final RegistrySupplier<Item>  BOTTLE_MOJANG_NOIR_ITEM = registerI("bottle_mojang_noir", () -> new DrinkBlockSmallItem(BOTTLE_MOJANG_NOIR.get(), getWineItemSettings(MobEffects.DAMAGE_BOOST)));
    public static final RegistrySupplier<Block> VILLAGERS_FRIGHT = registerB("villagers_fright", () -> new WineBottleBlock(getWineSettings(), 3));
    public static final RegistrySupplier<Item>  VILLAGERS_FRIGHT_ITEM = registerI("villagers_fright", () -> new DrinkBlockSmallItem(VILLAGERS_FRIGHT.get(), getWineItemSettings(MobEffects.BAD_OMEN, 1200)));
    public static final RegistrySupplier<Block> MEAD = registerB("mead", () -> new WineBottleBlock(getWineSettings(), 2));
    public static final RegistrySupplier<Item>  MEAD_ITEM = registerI("mead", () -> new DrinkBlockBigItem(MEAD.get(), getWineItemSettings(MobEffects.SATURATION)));
    public static final RegistrySupplier<Block> EISWEIN = registerB("eiswein", () -> new WineBottleBlock(getWineSettings(), 2));
    public static final RegistrySupplier<Item>  EISWEIN_ITEM = registerI("eiswein", () -> new DrinkBlockBigItem(EISWEIN.get(), getWineItemSettings(MobEffects.HEAL, 5)));
    public static final RegistrySupplier<Item>  WINE_BOTTLE = registerI("wine_bottle", () -> new CherryItem(getSettings()));
    public static final RegistrySupplier<Item> APPLE_MASH = registerI("apple_mash", () -> new CherryItem(getSettings().food(Foods.APPLE)));
    public static final RegistrySupplier<Block> GRAPEVINE_STEM = registerB("grapevine_stem", () -> new PaleStemBlock(getGrapevineSettings()));
    public static final RegistrySupplier<Item>  GRAPEVINE_STEM_ITEM = registerI("grapevine_stem", () -> new BlockItem(GRAPEVINE_STEM.get(), getSettings()));
    public static final RegistrySupplier<Block> STORAGE_POT = registerB("storage_pot", () -> new StoragePotBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD), SoundEvents.DYE_USE, SoundEvents.DYE_USE));
    public static final RegistrySupplier<Item>  STORAGE_POT_ITEM = registerI("storage_pot", () -> new BlockItem(STORAGE_POT.get(), getSettings()));
    public static final RegistrySupplier<Block> FLOWER_BOX = registerB("flower_box", () -> new FlowerBoxBlock(BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));
    public static final RegistrySupplier<Item>  FLOWER_BOX_ITEM = registerI("flower_box", () -> new BlockItem(FLOWER_BOX.get(), getSettings()));
    public static final RegistrySupplier<Block> FLOWER_POT = registerB("flower_pot", () -> new FlowerPotBlock(BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));
    public static final RegistrySupplier<Item>  FLOWER_POT_ITEM = registerI("flower_pot", () -> new BlockItem(FLOWER_POT.get(), getSettings()));
    public static final RegistrySupplier<Block> WINE_BOX = registerB("wine_box", () -> new WineBox(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).noOcclusion()));
    public static final RegistrySupplier<Item>  WINE_BOX_ITEM = registerI("wine_box", () -> new BlockItem(WINE_BOX.get(), getSettings()));
    public static final RegistrySupplier<Block> BIG_TABLE = registerB("big_table", () -> new BigTableBlock(BlockBehaviour.Properties.of().strength(2.0F, 2.0F)));
    public static final RegistrySupplier<Item>  BIG_TABLE_ITEM = registerI("big_table", () -> new BlockItem(BIG_TABLE.get(), getSettings()));
    public static final RegistrySupplier<Block> SHELF = registerB("shelf", () -> new ShelfBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Item>  SHELF_ITEM = registerI("shelf", () -> new BlockItem(SHELF.get(), getSettings()));
    public static final RegistrySupplier<Block> BASKET = registerB("basket", () -> new BasketBlock(BlockBehaviour.Properties.of().instabreak().noOcclusion(), 1));
    public static final RegistrySupplier<Item>  BASKET_ITEM = registerI("basket", () -> new BlockItem(BASKET.get(), getSettings()));
    public static final RegistrySupplier<Block> STACKABLE_LOG = registerB("stackable_log", () -> new StackableLogBlock(getLogBlockSettings().noOcclusion().lightLevel(state -> state.getValue(StackableLogBlock.FIRED) ? 13 : 0)));
    public static final RegistrySupplier<Item>  STACKABLE_LOG_ITEM = registerI("stackable_log", () -> new BlockItem(STACKABLE_LOG.get(), getSettings()));
    public static final RegistrySupplier<Item> STRAW_HAT = registerI("straw_hat", () -> new StrawHatItem(getSettings().rarity(Rarity.RARE)));
    public static final RegistrySupplier<Item> WINEMAKER_APRON = registerI("winemaker_apron", () -> new WinemakerDefaultArmorItem(VineryMaterials.WINEMAKER_ARMOR, ArmorItem.Type.CHESTPLATE, getSettings().rarity(Rarity.EPIC)));
    public static final RegistrySupplier<Item> WINEMAKER_LEGGINGS = registerI("winemaker_leggings", () -> new WinemakerDefaultArmorItem(VineryMaterials.WINEMAKER_ARMOR, ArmorItem.Type.LEGGINGS, getSettings().rarity(Rarity.RARE)));
    public static final RegistrySupplier<Item> WINEMAKER_BOOTS = registerI("winemaker_boots", () -> new WinemakerDefaultArmorItem(VineryMaterials.WINEMAKER_ARMOR, ArmorItem.Type.BOOTS, getSettings().rarity(Rarity.RARE)));
    public static final RegistrySupplier<Block> CALENDAR = registerB("calendar", () -> new CalendarBlock(BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));
    public static final RegistrySupplier<Item>  CALENDAR_ITEM = registerI("calendar", () -> new Calendar(CALENDAR.get(), getSettings()));
    public static final RegistrySupplier<Item> MULE_SPAWN_EGG = registerI("mule_spawn_egg", () -> new ArchitecturySpawnEggItem(VineryEntites.MULE, -1, -1, getSettings()));
    public static final RegistrySupplier<Item> WANDERING_WINEMAKER_SPAWN_EGG = registerI("wandering_winemaker_spawn_egg", () -> new ArchitecturySpawnEggItem(VineryEntites.WANDERING_WINEMAKER, -1, -1, getSettings()));
    public static final RegistrySupplier<Block> POTTED_APPLE_TREE_SAPLING = registerB("potted_apple_tree_sapling", () -> new net.minecraft.world.level.block.FlowerPotBlock(ObjectRegistry.APPLE_TREE_SAPLING.get(), BlockBehaviour.Properties.copy(Blocks.POTTED_POPPY)));
    public static final RegistrySupplier<Block> POTTED_CHERRY_TREE_SAPLING = registerB("potted_cherry_tree_sapling", () -> new net.minecraft.world.level.block.FlowerPotBlock(ObjectRegistry.CHERRY_SAPLING.get(), BlockBehaviour.Properties.copy(Blocks.POTTED_POPPY)));
    public static final RegistrySupplier<Block> CHERRY_WINE_RACK_BIG = registerWithItem("wine_rack_1", () -> new NineBottleStorageBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> CHERRY_WINE_RACK_SMALL = registerWithItem("wine_rack_2", () -> new FourBottleStorageBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> CHERRY_WINE_RACK_MID = registerWithItem("cherry_wine_rack_mid", () -> new BigBottleStorageBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> OAK_WINE_RACK_BIG = registerWithItem("oak_wine_rack_big", () -> new NineBottleStorageBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> OAK_WINE_RACK_SMALL = registerWithItem("oak_wine_rack_small", () -> new FourBottleStorageBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> OAK_WINE_RACK_MID = registerWithItem("oak_wine_rack_mid", () -> new BigBottleStorageBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> BIRCH_WINE_RACK_BIG = registerWithItem("birch_wine_rack_big", () -> new NineBottleStorageBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> BIRCH_WINE_RACK_SMALL = registerWithItem("birch_wine_rack_small", () -> new FourBottleStorageBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> BIRCH_WINE_RACK_MID = registerWithItem("birch_wine_rack_mid", () -> new BigBottleStorageBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> SPRUCE_WINE_RACK_BIG = registerWithItem("spruce_wine_rack_big", () -> new NineBottleStorageBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> SPRUCE_WINE_RACK_SMALL = registerWithItem("spruce_wine_rack_small", () -> new FourBottleStorageBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> SPRUCE_WINE_RACK_MID = registerWithItem("spruce_wine_rack_mid", () -> new BigBottleStorageBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> DARK_OAK_WINE_RACK_BIG = registerWithItem("dark_oak_wine_rack_big", () -> new NineBottleStorageBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> DARK_OAK_WINE_RACK_SMALL = registerWithItem("dark_oak_wine_rack_small", () -> new FourBottleStorageBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> DARK_OAK_WINE_RACK_MID = registerWithItem("dark_oak_wine_rack_mid", () -> new BigBottleStorageBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> JUNGLE_WINE_RACK_BIG = registerWithItem("jungle_wine_rack_big", () -> new NineBottleStorageBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> JUNGLE_WINE_RACK_SMALL = registerWithItem("jungle_wine_rack_small", () -> new FourBottleStorageBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> JUNGLE_WINE_RACK_MID = registerWithItem("jungle_wine_rack_mid", () -> new BigBottleStorageBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> ACACIA_WINE_RACK_BIG = registerWithItem("acacia_wine_rack_big", () -> new NineBottleStorageBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> ACACIA_WINE_RACK_SMALL = registerWithItem("acacia_wine_rack_small", () -> new FourBottleStorageBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> ACACIA_WINE_RACK_MID = registerWithItem("acacia_wine_rack_mid", () -> new BigBottleStorageBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> MANGROVE_WINE_RACK_BIG = registerWithItem("mangrove_wine_rack_big", () -> new NineBottleStorageBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> MANGROVE_WINE_RACK_SMALL = registerWithItem("mangrove_wine_rack_small", () -> new FourBottleStorageBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> MANGROVE_WINE_RACK_MID = registerWithItem("mangrove_wine_rack_mid", () -> new BigBottleStorageBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> BAMBOO_WINE_RACK_BIG = registerWithItem("bamboo_wine_rack_big", () -> new NineBottleStorageBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> BAMBOO_WINE_RACK_SMALL = registerWithItem("bamboo_wine_rack_small", () -> new FourBottleStorageBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> BAMBOO_WINE_RACK_MID = registerWithItem("bamboo_wine_rack_mid", () -> new BigBottleStorageBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> MCCHERRY_WINE_RACK_BIG = registerWithItem("mccherry_wine_rack_big", () -> new NineBottleStorageBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> MCCHERRY_WINE_RACK_SMALL = registerWithItem("mccherry_wine_rack_small", () -> new FourBottleStorageBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> MCCHERRY_WINE_RACK_MID = registerWithItem("mccherry_wine_rack_mid", () -> new BigBottleStorageBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> WOOD_FIRED_OVEN = registerWithItem("wood_fired_oven", () -> new StoveBlock(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistrySupplier<Block> STOVE = registerWithItem("stove", () -> new StoveBlock(BlockBehaviour.Properties.copy(Blocks.STONE).lightLevel(block -> 12)));
    public static final RegistrySupplier<Block> KITCHEN_SINK = registerWithItem("kitchen_sink", () -> new KitchenSinkBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()));
    public static final RegistrySupplier<Item>  VINERY_STANDARD = registerI("vinery_standard", () -> new VineryStandardItem(new Item.Properties().stacksTo(16).rarity(Rarity.UNCOMMON)));



    static <T extends Item> RegistrySupplier<T> registerI(String path, Supplier<T> item) {
        final ResourceLocation id = new VineryIdentifier(path);
        return ITEM_REGISTRAR.register(id, item);
    }

    static <T extends Block> RegistrySupplier<T> registerB(String path, Supplier<T> block) {
        final ResourceLocation id = new VineryIdentifier(path);
        return BLOCK_REGISTRAR.register(id, block);
    }


    public static void init() {
        VineryBoatsAndSigns.init();
        ITEMS.register();
        BLOCKS.register();
    }

    public static final List<Supplier<Block>> STANDARD_BLOCKS = Lists.newArrayList();
    public static final List<Supplier<Block>> STANDARD_WALL_BLOCKS = Lists.newArrayList();
    public static final List<Supplier<Block>> STANDARD_FLOOR_BLOCKS = Lists.newArrayList();

    public static BlockBehaviour.Properties properties(float strength) {
        return properties(strength, strength);
    }

    public static BlockBehaviour.Properties properties(float breakSpeed, float explosionResist) {
        return BlockBehaviour.Properties.of().strength(breakSpeed, explosionResist);
    }


    private static Item.Properties getSettings(Consumer<Item.Properties> consumer) {
        Item.Properties settings = new Item.Properties();
        consumer.accept(settings);
        return settings;
    }


    static Item.Properties getSettings() {
        return getSettings(settings -> {
        });
    }


    private static Item.Properties getWineItemSettings(MobEffect effect) {
        return getSettings().food(wineFoodComponent(effect, 45 * 20));
    }

    private static Item.Properties getWineItemSettings(MobEffect effect, int duration) {
        return getSettings().food(wineFoodComponent(effect, duration));
    }


    private static FoodProperties wineFoodComponent(MobEffect effect, int duration) {
        List<Pair<MobEffectInstance, Float>> effects = Lists.newArrayList();
        if (effect != null) effects.add(Pair.of(new MobEffectInstance(effect, duration), 1.0f));
        return new VineryFoodComponent(effects);
    }

    private static BlockBehaviour.Properties getBushSettings() {
        return BlockBehaviour.Properties.copy(Blocks.SWEET_BERRY_BUSH);
    }


    private static BlockBehaviour.Properties getGrassSettings() {
        return BlockBehaviour.Properties.copy(Blocks.GRASS_BLOCK).noOcclusion();
    }

    private static BlockBehaviour.Properties getGrapevineSettings() {
        return BlockBehaviour.Properties.of().strength(2.0F).randomTicks().sound(SoundType.WOOD).noOcclusion();
    }

    private static BlockBehaviour.Properties getLogBlockSettings() {
        return BlockBehaviour.Properties.of().strength(2.0F).sound(SoundType.WOOD);
    }

    private static BlockBehaviour.Properties getSlabSettings() {
        return getLogBlockSettings().explosionResistance(3.0F);
    }

    public static <T extends Block> RegistrySupplier<T> registerWithoutItem(String path, Supplier<T> block) {
        return Util.registerWithoutItem(BLOCKS, BLOCK_REGISTRAR, new VineryIdentifier(path), block);
    }

    public static <T extends Block> RegistrySupplier<T> registerWithItem(String path, Supplier<T> block) {
        return Util.registerWithItem(BLOCKS, BLOCK_REGISTRAR, ITEMS, ITEM_REGISTRAR, new VineryIdentifier(path), block);
    }

    private static BlockBehaviour.Properties getWineSettings() {
        return BlockBehaviour.Properties.copy(Blocks.GLASS).noOcclusion().instabreak();
    }

    private static ButtonBlock woodenButton(BlockSetType blockSetType, FeatureFlag... featureFlags) {
        BlockBehaviour.Properties properties = BlockBehaviour.Properties.of().noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY);
        if (featureFlags.length > 0) {
            properties = properties.requiredFeatures(featureFlags);
        }

        return new ButtonBlock(properties, blockSetType, 30, true);
    }
}
