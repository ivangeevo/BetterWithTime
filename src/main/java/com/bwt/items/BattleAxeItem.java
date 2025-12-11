package com.bwt.items;

import com.bwt.tags.BwtBlockTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.ToolComponent;
import net.minecraft.component.type.WeaponComponent;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.ActionResult;

import java.util.List;

public class BattleAxeItem extends Item {
    public BattleAxeItem(ToolMaterial material, float attackDamage, float attackSpeed, Item.Settings settings) {
        super(battleAxeSettings(settings, material, attackDamage, attackSpeed));
    }

    public static Item.Settings battleAxeSettings(Settings settings, ToolMaterial material, float attackDamage, float attackSpeed) {
        RegistryEntryLookup<Block> registryEntryLookup = Registries.createEntryLookup(Registries.BLOCK);
        return settings
                .maxDamage(material.durability())
                .repairable(material.repairItems())
                .enchantable(material.enchantmentValue())
                .component(
                        DataComponentTypes.TOOL,
                        new ToolComponent(
                                List.of(
                                        ToolComponent.Rule.ofNeverDropping(registryEntryLookup.getOrThrow(material.incorrectBlocksForDrops())),
                                        ToolComponent.Rule.ofAlwaysDropping(registryEntryLookup.getOrThrow(BwtBlockTags.BATTLEAXE_MINEABLE), material.speed()),
                                        ToolComponent.Rule.ofAlwaysDropping(RegistryEntryList.of(Blocks.COBWEB.getRegistryEntry()), 15.0F),
                                        ToolComponent.Rule.of(registryEntryLookup.getOrThrow(BlockTags.SWORD_INSTANTLY_MINES), Float.MAX_VALUE),
                                        ToolComponent.Rule.of(registryEntryLookup.getOrThrow(BlockTags.SWORD_EFFICIENT), 1.5F)
                                ),
                                1.0F,
                                1,
                                false
                        )
                )
                .attributeModifiers(
                        AttributeModifiersComponent.builder()
                                .add(
                                        EntityAttributes.ATTACK_DAMAGE,
                                        new EntityAttributeModifier(
                                                Item.BASE_ATTACK_DAMAGE_MODIFIER_ID,
                                                attackDamage + material.attackDamageBonus(),
                                                EntityAttributeModifier.Operation.ADD_VALUE
                                        ),
                                        AttributeModifierSlot.MAINHAND
                                )
                                .add(
                                        EntityAttributes.ATTACK_SPEED,
                                        new EntityAttributeModifier(
                                                Item.BASE_ATTACK_SPEED_MODIFIER_ID,
                                                attackSpeed,
                                                EntityAttributeModifier.Operation.ADD_VALUE
                                        ),
                                        AttributeModifierSlot.MAINHAND
                                )
                                .build()
                )
                .component(DataComponentTypes.WEAPON, new WeaponComponent(2, WeaponComponent.AXE_DISABLE_BLOCKING_FOR_SECONDS));
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        return Items.NETHERITE_AXE.useOnBlock(context);
    }
}
