package com.bwt.items;

import com.bwt.tags.BwtBlockTags;
import net.minecraft.block.Block;
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
import net.minecraft.util.ActionResult;

import java.util.List;

public class MattockItem extends Item {
    public MattockItem(ToolMaterial material, float attackDamage, float attackSpeed, Item.Settings settings) {
        super(mattockSettings(settings, material, attackDamage, attackSpeed));
    }

    public static Item.Settings mattockSettings(Settings settings, ToolMaterial material, float attackDamage, float attackSpeed) {
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
                                        ToolComponent.Rule.ofAlwaysDropping(registryEntryLookup.getOrThrow(BwtBlockTags.MATTOCK_MINEABLE), material.speed())
                                ),
                                1.0F,
                                1,
                                true
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
                .component(DataComponentTypes.WEAPON, new WeaponComponent(2, 0));
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        // Only shovels have a right click action, so we inherit from that
        return Items.NETHERITE_SHOVEL.useOnBlock(context);
    }
}
