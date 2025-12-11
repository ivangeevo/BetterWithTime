package com.bwt.items;

import com.bwt.blocks.BwtBlocks;
import com.bwt.entities.WaterWheelEntity;
import com.bwt.entities.WindmillEntity;
import com.bwt.tags.BwtPaintingVariantTags;
import com.bwt.utils.RegistrationUtils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ConsumableComponents;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.component.type.FoodComponents;
import net.minecraft.entity.decoration.painting.PaintingVariant;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.item.consume.ApplyEffectsConsumeEffect;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class BwtItems implements ModInitializer {
    public static final Item cementBucketItem = RegistrationUtils.registerItem("cement_bucket", CementBucketItem::new);
	public static final Item armorPlateItem = RegistrationUtils.registerItem("armor_plate");
	public static final Item beltItem = RegistrationUtils.registerItem("belt");
	public static final Item breedingHarnessItem = RegistrationUtils.registerItem("breeding_harness");
	public static final Item broadheadItem = RegistrationUtils.registerItem("broadhead");
	public static final Item broadheadArrowItem = RegistrationUtils.registerItem("broadhead_arrow", BroadheadArrowItem::new);
//	public static final Item candleItem = RegistrationUtils.registerItem("candle", CandleItem::new);
	public static final Item canvasItem = RegistrationUtils.registerItem("canvas", CanvasItem::new);
	public static final Item coalDustItem = RegistrationUtils.registerItem("coal_dust");
	public static final Item compositeBowItem = RegistrationUtils.registerItem("composite_bow", CompositeBowItem::new, new Item.Settings().maxDamage(576));
	public static final Item concentratedHellfireItem = RegistrationUtils.registerItem("concentrated_hellfire");
    public static final Item cookedWolfChopItem = RegistrationUtils.registerItem("cooked_wolf_chop", new Item.Settings().food(FoodComponents.COOKED_PORKCHOP));
	public static final Item donutItem = RegistrationUtils.registerItem(
            "donut",
            new Item.Settings().food(
                    new FoodComponent.Builder()
                            .nutrition(1)
                            .saturationModifier(0.5f)
                            .alwaysEdible()
                            .build(),
                    ConsumableComponents.food().consumeSeconds(0.8F).build()
            )
    );
	public static final DyeItem dungItem = RegistrationUtils.registerItem("dung", DungItem::new);
	public static final Item dynamiteItem = RegistrationUtils.registerItem("dynamite", DynamiteItem::new, new Item.Settings().useCooldown(1f));
//	public static final Item enderSpectaclesItem = RegistrationUtils.registerItem("ender_spectacles", EnderSpectaclesItem::new);
	public static final Item fabricItem = RegistrationUtils.registerItem("fabric");
	public static final Item filamentItem = RegistrationUtils.registerItem("filament");
	public static final Item flourItem = RegistrationUtils.registerItem("flour");
	public static final Item foulFoodItem = RegistrationUtils.registerItem(
            "foul_food",
            new Item.Settings().food(
                    new FoodComponent.Builder()
                            .nutrition(1)
                            .build(),
                    ConsumableComponents.food()
                            .consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(StatusEffects.POISON, 20 * 30, 0), 0.8f))
                            .build()
            )
    );
    public static final Item friedEggItem = RegistrationUtils.registerItem(
            "fried_egg",
            new Item.Settings().food(
                    new FoodComponent.Builder()
                            .nutrition(3)
                            .saturationModifier(0.25f)
                            .build()
            )
    );
//	public static final Item fuseItem = RegistrationUtils.registerItem("fuse", FuseItem::new);
	public static final Item gearItem = RegistrationUtils.registerItem("gear");
	public static final Item glueItem = RegistrationUtils.registerItem("glue");
	public static final Item groundNetherrackItem = RegistrationUtils.registerItem("ground_netherrack");
	public static final Item haftItem = RegistrationUtils.registerItem("haft");
	public static final Item hellfireDustItem = RegistrationUtils.registerItem("hellfire_dust");
	public static final Item hempFiberItem = RegistrationUtils.registerItem("hemp_fiber");
	public static final Item hempItem = RegistrationUtils.registerItem("hemp");
	public static final Item hempSeedsItem = RegistrationUtils.registerUniqueBlockItem(BwtBlocks.hempCropBlock, "hemp_seeds");
	public static final Item kibbleItem = RegistrationUtils.registerItem("kibble");
//	public static final Item netherBrickItem = RegistrationUtils.registerItem("nether_brick", NetherBrickItem::new);
	public static final Item nethercoalItem = RegistrationUtils.registerItem("nethercoal");
//	public static final Item nitreItem = RegistrationUtils.registerItem("nitre", NitreItem::new);
	public static final Item paddingItem = RegistrationUtils.registerItem("padding");
	public static final Item poachedEggItem = RegistrationUtils.registerItem(
            "poached_egg",
            new Item.Settings().food(
                    new FoodComponent.Builder()
                            .nutrition(3)
                            .saturationModifier(0.25f)
                            .build()
            )
    );
	public static final Item potashItem = RegistrationUtils.registerItem("potash");
    public static final Item rawEggItem = RegistrationUtils.registerItem(
            "raw_egg",
            new Item.Settings().food(
                    new FoodComponent.Builder()
                            .nutrition(2)
                            .saturationModifier(0.25f)
                            .build()
            )
    );
    public static final Item redstoneEyeItem = RegistrationUtils.registerItem("redstone_eye");
    public static final Item netheriteMattockItem = RegistrationUtils.registerItem(
            "netherite_mattock",
            settings -> new MattockItem(ToolMaterial.NETHERITE, 1, -3.0f, settings),
            new Item.Settings().fireproof()
    );
    public static final Item netheriteBattleAxeItem = RegistrationUtils.registerItem(
            "netherite_battle_axe",
            settings -> new BattleAxeItem(ToolMaterial.NETHERITE, 3, -2.4f, settings),
            new Item.Settings().fireproof()
    );
	public static final Item ropeItem = RegistrationUtils.registerItem("rope", RopeItem::new);
	public static final Item rottedArrowItem = RegistrationUtils.registerItem("rotted_arrow", RottedArrowItem::new);
	public static final Item sailItem = RegistrationUtils.registerItem("sail", new Item.Settings().maxCount(1));
	public static final Item sawDustItem = RegistrationUtils.registerItem("saw_dust");
	public static final Item scouredLeatherItem = RegistrationUtils.registerItem("scoured_leather");
	public static final Item screwItem = RegistrationUtils.registerItem("screw");
    public static final Item soapItem = RegistrationUtils.registerItem("soap");
    public static final Item soulDustItem = RegistrationUtils.registerItem("soul_dust");
	public static final Item soulUrnItem = RegistrationUtils.registerItem("soul_urn", SoulUrnItem::new);
	public static final Item strapItem = RegistrationUtils.registerItem("strap");
	public static final Item tallowItem = RegistrationUtils.registerItem("tallow");
	public static final Item tannedLeatherItem = RegistrationUtils.registerItem("tanned_leather");
//	public static final Item tannedLeatherBootsItem = RegistrationUtils.registerItem("tanned_leather_boots", TannedLeatherBootsItem::new);
//	public static final Item tannedLeatherCapItem = RegistrationUtils.registerItem("tanned_leather_cap", TannedLeatherCapItem::new);
//	public static final Item tannedLeatherPantsItem = RegistrationUtils.registerItem("tanned_leather_pants", TannedLeatherPantsItem::new);
//	public static final Item tannedLeatherTunicItem = RegistrationUtils.registerItem("tanned_leather_tunic", TannedLeatherTunicItem::new);
    public static final Item waterWheelItem = RegistrationUtils.registerItem(
            "water_wheel",
            settings -> new HorizontalMechPowerSourceItem(WaterWheelEntity::new, settings),
            new Item.Settings().maxCount(1)
    );
    public static final Item windmillItem = RegistrationUtils.registerItem(
            "windmill",
            settings -> new HorizontalMechPowerSourceItem(WindmillEntity::new, settings),
            new Item.Settings().maxCount(1)
    );
	public static final Item wolfChopItem = RegistrationUtils.registerItem("wolf_chop", new Item.Settings().food(FoodComponents.PORKCHOP));
	public static final Item woodBladeItem = RegistrationUtils.registerItem("wood_blade");

    @Override
    public void onInitialize() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(content -> {
            content.addAfter(Items.NETHERITE_PICKAXE, BwtItems.netheriteMattockItem);
            content.addAfter(Items.NETHERITE_AXE, BwtItems.netheriteBattleAxeItem);
//            content.addAfter(Items.WATER_BUCKET, cementBucketItem);
//            content.add(breedingHarnessItem);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(content -> {
            content.addAfter(Items.NETHERITE_AXE, BwtItems.netheriteBattleAxeItem);

            content.addAfter(Items.BOW, compositeBowItem);
            content.addAfter(Items.ARROW, broadheadArrowItem, rottedArrowItem);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE).register(content -> {
            content.add(windmillItem);
            content.add(waterWheelItem);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(content -> {
            content.addAfter(Items.COOKED_PORKCHOP, wolfChopItem);
            content.addAfter(wolfChopItem, cookedWolfChopItem);
            content.addAfter(Items.BREAD, donutItem);
            content.add(kibbleItem);
            content.addAfter(Items.DRIED_KELP, rawEggItem, poachedEggItem, friedEggItem);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(content -> {
            content.addAfter(Items.WHEAT_SEEDS, hempSeedsItem);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(content -> {
            content.addAfter(Items.WHEAT, hempItem);
            content.add(hempFiberItem);
            content.add(dungItem);
            content.add(ropeItem);
            content.add(gearItem);
            content.add(flourItem);
            content.add(scouredLeatherItem);
            content.add(tannedLeatherItem);
            content.add(filamentItem);
            content.add(fabricItem);
            content.add(sailItem);
            content.add(groundNetherrackItem);
            content.add(sawDustItem);
            content.add(soulDustItem);
            content.add(hellfireDustItem);
            content.add(concentratedHellfireItem);
            content.add(potashItem);
            content.add(coalDustItem);
            content.add(broadheadItem);
            content.add(nethercoalItem);
            content.add(redstoneEyeItem);
            content.add(haftItem);
            content.add(armorPlateItem);
            content.add(dynamiteItem);
            content.add(glueItem);
            content.add(paddingItem);
            content.add(screwItem);
            content.add(strapItem);
            content.add(beltItem);
            content.add(soulUrnItem);
            content.add(soapItem);
            content.add(tallowItem);
            content.add(woodBladeItem);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(content -> {
            content.addAfter(Items.GLOW_ITEM_FRAME, canvasItem);
            content.getContext().lookup()
                    .getOptional(RegistryKeys.PAINTING_VARIANT)
                    .ifPresent(
                            registryWrapper -> addCanvases(
                                    content,
                                    registryWrapper,
                                    registryEntry -> registryEntry.isIn(BwtPaintingVariantTags.CANVAS_PLACEABLE)
                            )
                    );
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.OPERATOR).register(content -> {
            content.getContext().lookup()
                    .getOptional(RegistryKeys.PAINTING_VARIANT)
                    .ifPresent(
                            registryWrapper -> addCanvases(
                                    content,
                                    registryWrapper,
                                    registryEntry -> !registryEntry.isIn(BwtPaintingVariantTags.CANVAS_PLACEABLE)
                            )
                    );
        });
    }

    public void replaceItem(FabricItemGroupEntries content, ItemConvertible itemToReplace, ItemConvertible newItem) {
        Item anchorItem = itemToReplace.asItem();
        for (List<ItemStack> addTo : List.of(content.getDisplayStacks(), content.getSearchTabStacks())) {
            for (int i = 0; i < addTo.size(); i++) {
                if (addTo.get(i).isOf(anchorItem)) {
                    addTo.set(i, new ItemStack(newItem));
                    break;
                }
            }
        }

        content.add(newItem);
    }

    private static void addCanvases(
            FabricItemGroupEntries entries,
            RegistryWrapper.Impl<PaintingVariant> registryWrapper,
            Predicate<RegistryEntry<PaintingVariant>> filter
    ) {
        registryWrapper.streamEntries()
                .filter(filter)
                .sorted(Comparator.comparing(
                        RegistryEntry::value,
                        Comparator.comparingInt(PaintingVariant::getArea).thenComparing(PaintingVariant::width)
                ))
                .forEach(
                        canvasVariantEntry -> {
                            ItemStack itemStack = new ItemStack(canvasItem);
                            itemStack.set(DataComponentTypes.PAINTING_VARIANT, canvasVariantEntry);
                            entries.addAfter(canvasItem, itemStack);
                        }
                );
    }
}
