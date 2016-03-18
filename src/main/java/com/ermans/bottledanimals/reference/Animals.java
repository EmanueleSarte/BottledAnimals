package com.ermans.bottledanimals.reference;

import com.ermans.bottledanimals.init.ModItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import java.util.HashSet;

public enum Animals {
    PIG("Pig", "Pig",
            new ItemStack[]{new ItemStack(Items.carrot), new ItemStack(Items.potato)},
            new ItemStack[]{new ItemStack(Items.porkchop, 2)}),

    SHEEP("Sheep", "Sheep",
            new ItemStack[]{new ItemStack(Items.wheat)},
            new ItemStack[]{new ItemStack(ItemBlock.getItemFromBlock(Blocks.wool), 1)}),

    COW("Cow", "Cow",
            new ItemStack[]{new ItemStack(Items.wheat)},
            new ItemStack[]{new ItemStack(Items.beef, 2), new ItemStack(Items.leather, 1)}),

    CHICKEN("Chicken", "Chicken",
            new ItemStack[]{new ItemStack(Items.wheat_seeds)},
            new ItemStack[]{new ItemStack(Items.chicken, 1), new ItemStack(Items.feather, 1)}),

    SQUID("Squid", "Squid",
            new ItemStack[]{new ItemStack(ModItems.itemSquidFood)},
            new ItemStack[]{new ItemStack(Items.dye, 1, 0)}),

    WOLF("Wolf", "Wolf",
            new ItemStack[]{new ItemStack(Items.porkchop), new ItemStack(Items.cooked_porkchop), new ItemStack(Items.beef), new ItemStack(Items.cooked_beef)},
            new ItemStack[0]),

    MUSHROOM_COW("MushroomCow", "Mushroom Cow",
            new ItemStack[]{new ItemStack(Items.wheat)},
            new ItemStack[]{new ItemStack(Items.beef, 2), new ItemStack(Items.leather, 1)}),

    OCELOT("Ozelot", "Ocelot",
            new ItemStack[]{new ItemStack(Items.fish), new ItemStack(Items.fish, 1, 1), new ItemStack(Items.fish, 1, 2),},
            new ItemStack[0]),

    HORSE("EntityHorse", "Horse",
            new ItemStack[]{new ItemStack(Items.golden_apple), new ItemStack(Items.golden_carrot)},
            new ItemStack[]{new ItemStack(Items.leather, 1)});

    private static final HashSet<Item> foodsSet;
    private final String entityName;
    private final String fancyName;
    private final ItemStack[] validFoods;
    private final ItemStack[] drops;

    static {
        foodsSet = new HashSet<Item>();
        for (Animals a : values()) {
            for (ItemStack i : a.getValidFoods()) {
                foodsSet.add(i.getItem());
            }
        }
    }

    Animals(String entityName, String fancyName, ItemStack[] validFoods, ItemStack[] drops) {
        this.entityName = entityName;
        this.fancyName = fancyName;
        this.validFoods = validFoods;
        this.drops = drops;
    }

    public static Animals getAnimalsFromID(int id) {
        if ((id < 0) || (id >= values().length)) {
            return null;
        }
        return values()[id];
    }

    public static Animals getAnimalsFromEntityName(String entityName) {
        Animals[] animalsList = values();
        for (Animals anAnimalsList : animalsList) {
            if (anAnimalsList.entityName.equals(entityName)) {
                return anAnimalsList;
            }
        }
        return null;
    }

    public ItemStack[] getDrops() {
        return this.drops;
    }

    public ItemStack[] getValidFoods() {
        return this.validFoods;
    }

    public int getID() {
        return ordinal();
    }

    public String getFancyName() {
        return this.fancyName;
    }

    public String getEntityName() {
        return this.entityName;
    }

    public static HashSet<Item> getFoodsSet() {
        return foodsSet;
    }
}
