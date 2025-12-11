package com.bwt.utils;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

import java.util.function.Function;

public class RegistrationUtils {
    public static RegistryKey<Block> keyOfBlock(String id) {
        return RegistryKey.of(RegistryKeys.BLOCK, Id.of(id));
    }

    public static RegistryKey<Item> keyOfItem(String id) {
        return RegistryKey.of(RegistryKeys.ITEM, Id.of(id));
    }

    public static <T extends Block> T registerBlock(RegistryKey<Block> key, Function<AbstractBlock.Settings, T> factory, AbstractBlock.Settings settings) {
        T block = factory.apply(settings.registryKey(key));
        return Registry.register(Registries.BLOCK, key, block);
    }

    public static <T extends Block> T registerBlock(String id, Function<AbstractBlock.Settings, T> factory, AbstractBlock.Settings settings) {
        return registerBlock(keyOfBlock(id), factory, settings);
    }

    public static Block registerBlock(RegistryKey<Block> key, AbstractBlock.Settings settings) {
        return registerBlock(key, Block::new, settings);
    }

    public static Block registerBlock(String id, AbstractBlock.Settings settings) {
        return registerBlock(keyOfBlock(id), settings);
    }

    public static <T extends Item> T registerItem(RegistryKey<Item> key, Function<Item.Settings, T> factory, Item.Settings settings) {
        T block = factory.apply(settings.registryKey(key));
        return Registry.register(Registries.ITEM, key, block);
    }

    public static <T extends Item> T registerItem(String id, Function<Item.Settings, T> factory, Item.Settings settings) {
        return registerItem(keyOfItem(id), factory, settings);
    }

    public static Item registerItem(RegistryKey<Item> key, Item.Settings settings) {
        return registerItem(key, Item::new, settings);
    }

    public static Item registerItem(String id, Item.Settings settings) {
        return registerItem(keyOfItem(id), settings);
    }

    public static Item registerItem(String id) {
        return registerItem(id, new Item.Settings());
    }

    public static <T extends Item> T registerItem(String id, Function<Item.Settings, T> factory) {
        return registerItem(keyOfItem(id), factory, new Item.Settings());
    }

    public static <T extends Block> BlockItem registerBlockItem(T block, String id) {
        RegistryKey<Item> keyOfItem = RegistryKey.of(RegistryKeys.ITEM, Id.of(id));
        BlockItem blockItem = new BlockItem(block, new Item.Settings().registryKey(keyOfItem).useBlockPrefixedTranslationKey());
        return Registry.register(Registries.ITEM, keyOfItem, blockItem);
    }

    public static <T extends Block> BlockItem registerUniqueBlockItem(T block, String id, Item.Settings settings) {
        RegistryKey<Item> keyOfItem = RegistryKey.of(RegistryKeys.ITEM, Id.of(id));
        BlockItem blockItem = new BlockItem(block, settings.registryKey(keyOfItem).useItemPrefixedTranslationKey());
        return Registry.register(Registries.ITEM, keyOfItem, blockItem);
    }

    public static <T extends Block> BlockItem registerUniqueBlockItem(T block, String id) {
        return registerUniqueBlockItem(block, id, new Item.Settings());
    }

    public static <T extends Block> T registerBlockAndItem(String id, Function<AbstractBlock.Settings, T> factory, AbstractBlock.Settings settings) {
        T block = registerBlock(id, factory, settings);
        registerBlockItem(block, id);
        return block;
    }

    public static Block registerBlockAndItem(String id, AbstractBlock.Settings settings) {
        Block block = registerBlock(id, settings);
        registerBlockItem(block, id);
        return block;
    }
}
