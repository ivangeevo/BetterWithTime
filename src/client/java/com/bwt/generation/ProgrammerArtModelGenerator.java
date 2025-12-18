package com.bwt.generation;

import com.bwt.blocks.*;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.data.client.*;
import net.minecraft.util.Identifier;

public class ProgrammerArtModelGenerator extends ModelGenerator {
    public ProgrammerArtModelGenerator(FabricDataOutput generator) {
        super(generator);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        generateGrothedNetherrack(blockStateModelGenerator);
        blockStateModelGenerator.registerSingleton(BwtBlocks.concentratedHellfireBlock, TexturedModel.CUBE_ALL);
        generatePaneBlock(blockStateModelGenerator, BwtBlocks.grateBlock);
        generatePaneBlock(blockStateModelGenerator, BwtBlocks.slatsBlock);
        generatePaneBlock(blockStateModelGenerator, BwtBlocks.wickerPaneBlock);
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier
                .create(BwtBlocks.lensBlock,
                        BlockStateVariant.create().put(
                                VariantSettings.MODEL,
                                TexturedModel.makeFactory(TextureMap::sideFrontBack, Models.TEMPLATE_COMMAND_BLOCK)
                                        .upload(BwtBlocks.lensBlock, blockStateModelGenerator.modelCollector)
                        )
                ).coordinate(BlockStateModelGenerator.createNorthDefaultRotationStates())
        );
        blockStateModelGenerator.blockStateCollector.accept(
                VariantsBlockStateSupplier.create(
                        BwtBlocks.sawBlock,
                        BlockStateVariant.create().put(
                                VariantSettings.MODEL,
                                ModelIds.getBlockModelId(BwtBlocks.sawBlock)
                        )
                ).coordinate(createUpDefaultRotationStates())
        );
        blockStateModelGenerator.blockStateCollector.accept(
                VariantsBlockStateSupplier.create(BwtBlocks.soapBlock, BlockStateVariant.create().put(VariantSettings.MODEL, TexturedModel.makeFactory(block -> TextureMap.sideFrontTop(block).put(TextureKey.TOP, TextureMap.getSubId(block, "_side")), Models.ORIENTABLE).upload(BwtBlocks.soapBlock, blockStateModelGenerator.modelCollector)))
                        .coordinate(BlockStateModelGenerator.createNorthDefaultRotationStates())
        );
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier
                .create(BwtBlocks.vineTrapBlock)
                .coordinate(
                        BlockStateVariantMap.create(VineTrapBlock.HALF)
                                .register(BlockHalf.BOTTOM, BlockStateVariant.create().put(VariantSettings.MODEL, ModelIds.getBlockModelId(BwtBlocks.vineTrapBlock)))
                                .register(BlockHalf.TOP, BlockStateVariant.create().put(VariantSettings.MODEL, ModelIds.getBlockModelId(BwtBlocks.vineTrapBlock)).put(VariantSettings.X, VariantSettings.Rotation.R180))
                )
        );
        TexturedModel.ORIENTABLE_WITH_BOTTOM.upload(BwtBlocks.bellowsBlock, blockStateModelGenerator.modelCollector);
        Models.SLAB.upload(BwtBlocks.wickerSlabBlock, TexturedModel.CUBE_ALL.get(BwtBlocks.wickerBlock).getTextures(), blockStateModelGenerator.modelCollector);
        Models.SLAB_TOP.upload(BwtBlocks.wickerSlabBlock, TexturedModel.CUBE_ALL.get(BwtBlocks.wickerBlock).getTextures(), blockStateModelGenerator.modelCollector);
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(BwtBlocks.kilnBlock, BlockStateVariant.create().put(VariantSettings.MODEL, ModelIds.getBlockModelId(Blocks.BRICKS))));
    }

    public void generateGrothedNetherrack(BlockStateModelGenerator blockStateModelGenerator) {
        TexturedModel netherrackTexturedModel = TexturedModel.CUBE_ALL.get(Blocks.NETHERRACK);
        Identifier netherrackTexture = netherrackTexturedModel.getTextures().getTexture(TextureKey.ALL);
        TextureMap grothedNetherrackTextureMap = new TextureMap()
                .put(TextureKey.SIDE, TextureMap.getSubId(BwtBlocks.grothedNetherrackBlock, "_side"))
                .put(TextureKey.TOP, TextureMap.getSubId(BwtBlocks.grothedNetherrackBlock, "_top"))
                .put(TextureKey.BOTTOM, netherrackTexture);
        blockStateModelGenerator.blockStateCollector.accept(
                VariantsBlockStateSupplier.create(
                        BwtBlocks.grothedNetherrackBlock,
                        BlockStateVariant.create().put(
                                VariantSettings.MODEL,
                                Models.CUBE_BOTTOM_TOP.upload(
                                        BwtBlocks.grothedNetherrackBlock,
                                        grothedNetherrackTextureMap,
                                        blockStateModelGenerator.modelCollector
                                )
                        )
                )
        );
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
    }
}
