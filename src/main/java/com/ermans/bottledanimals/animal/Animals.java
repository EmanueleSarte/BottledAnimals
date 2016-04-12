package com.ermans.bottledanimals.animal;


import com.ermans.bottledanimals.Log;
import com.ermans.bottledanimals.init.ModFluids;
import com.ermans.bottledanimals.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.registry.EntityRegistry;

import java.util.*;

public class Animals {

    public static Animals COW;
    public static Animals SHEEP;
    public static Animals PIG;
    public static Animals CHICKEN;
    public static Animals SQUID;
    public static Animals WOLF;
    public static Animals MOOSHROOM;
    public static Animals OCELOT;
    public static Animals HORSE;
    public static Animals RABBIT;


    public static final Map<String, Animals> animalsMap = new HashMap<String, Animals>();
    public static final Map<Class<? extends Entity>, Animals> classToAnimalsMap = new HashMap<Class<? extends Entity>, Animals>();
    public static final Map<Integer, Animals> idToAnimalsMap = new HashMap<Integer, Animals>();
    public static final List<Animals> animalsList = new ArrayList<Animals>();
    private static Map<String, ItemStack> nameToEggsMap = new HashMap<String, ItemStack>();


    private static final Set<String> foodBreedSet = new HashSet<String>();


    private String entityName;
    private String fancyName;
    private ItemStack[] breedingItems;
    private AnimalStack[] dropItems;
    private AnimalStack rareDrop;
    private AnimalStack ranchableItem;
    private FluidStack ranchableFluid;
    private ItemStack egg;
    private int ranchableTime;
    private int id;

    private static int idCounter = 0;

    private Animals() {
    }


    private static Animals newAnimal(String entityName, String fancyName) {
        Animals animal = new Animals().setEntityName(entityName).setFancyName(fancyName);
        animal.id = idCounter;
        animal.egg = nameToEggsMap.get(entityName);

        animalsMap.put(entityName, animal);
        idToAnimalsMap.put(animal.id, animal);
        animalsList.add(animal);
        classToAnimalsMap.put(EntityList.stringToClassMapping.get(entityName), animal);

        idCounter++;
        return animal;
    }

    public static Animals addAnimal(String entityName, String fancyName) {
        if (EntityList.stringToClassMapping.containsKey(entityName)) {

            if (EntityAnimal.class.isAssignableFrom(EntityList.stringToClassMapping.get(entityName))) {

                return newAnimal(entityName, fancyName);

            } else {
                Log.warn("Attempt to register an not-animal entity: " + entityName);
            }
        } else {
            Log.warn("Attempt to register an unknown entity: " + entityName);
        }
        return null;
    }


    public static Animals getAnimalsFromID(int id) {
        return idToAnimalsMap.get(id);
    }

    public static boolean isValidBreedFood(ItemStack itemStack) {
        return foodBreedSet.contains(itemStack.getItem().getRegistryName() + itemStack.getItemDamage());
    }


    public Animals setEntityName(String entityName) {
        this.entityName = entityName;
        return this;
    }

    public Animals setFancyName(String fancyName) {
        this.fancyName = fancyName;
        return this;
    }

    public Animals setBreedingItems(ItemStack[] breedingItems) {
        for (ItemStack itemStack : breedingItems) {
            foodBreedSet.add(itemStack.getItem().getRegistryName() + itemStack.getItemDamage());
        }
        this.breedingItems = breedingItems;
        return this;
    }

    public Animals setDropItems(AnimalStack[] dropItems) {
        this.dropItems = dropItems;
        return this;
    }

    public Animals setRareDrop(AnimalStack getRareDrop) {
        this.rareDrop = getRareDrop;
        return this;
    }

    public Animals setRanchableItem(AnimalStack ranchableItem) {
        this.ranchableItem = ranchableItem;
        return this;
    }

    public Animals setRanchableFluid(FluidStack ranchableFluid) {
        this.ranchableFluid = ranchableFluid;
        return this;
    }

    public Animals setRanchableTime(int ranchableTime) {
        this.ranchableTime = ranchableTime;
        return this;
    }

    public String getEntityName() {
        return entityName;
    }

    public String getFancyName() {
        return fancyName;
    }

    public int getID() {
        return id;
    }

    public ItemStack getEgg() {
        return egg;
    }

    public ItemStack[] getBreedingItems() {
        return breedingItems;
    }

    public AnimalStack[] getDropItems() {
        return dropItems;
    }

    public AnimalStack getRareDrop() {
        return rareDrop;
    }

    public AnimalStack getRanchableItem() {
        return ranchableItem;
    }

    public FluidStack getRanchableFluid() {
        return ranchableFluid;
    }

    public int getRanchableTime() {
        return ranchableTime;
    }

    ///To be less verbose
    private static ItemStack i(Block block) {
        return new ItemStack(block);
    }

    private static ItemStack i(Block block, int metadata) {
        return new ItemStack(block, 1, metadata);
    }


    private static ItemStack i(Item item) {
        return new ItemStack(item);
    }

    private static ItemStack i(Item item, int metadata) {
        return new ItemStack(item, 1, metadata);
    }


    public static void findEgg(ItemStack stack) {
        if (stack.getItem() == Items.spawn_egg) {
            EntityList.EntityEggInfo eggInfo = getEggInfo(stack);
            if (eggInfo != null) {
                nameToEggsMap.put(eggInfo.name, stack);
            }
        }
    }

    private static EntityList.EntityEggInfo getEggInfo(ItemStack stack) {
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey("entity_name", 8))
            return EntityRegistry.getEggs().get(stack.getTagCompound().getString("entity_name"));
        return EntityList.entityEggs.get(stack.getMetadata());
    }


    public static void initializeAnimals() {


        PIG = newAnimal("Pig", "Pig")
                .setBreedingItems(new ItemStack[]{i(Items.carrot), i(Items.potato)})
                .setDropItems(new AnimalStack[]{new AnimalStack(i(Items.porkchop), 1, 2, 3)})
                .setRareDrop(new AnimalStack(i(Items.saddle), 99, 0, 1, 1));

        SHEEP = newAnimal("Sheep", "Sheep")
                .setBreedingItems(new ItemStack[]{i(Items.wheat)})
                .setDropItems(new AnimalStack[]{new AnimalStack(i(Items.mutton), 1, 2), new AnimalStack(i(Blocks.wool), 1)})
                .setRanchableItem(new AnimalStack(i(Blocks.wool), 1, 2, 3)).setRanchableTime(1200)
                .setRareDrop(new AnimalStack(i(Items.lead), 2, 1, 98, 0));

        COW = newAnimal("Cow", "Cow")
                .setBreedingItems(new ItemStack[]{i(Items.wheat)})
                .setDropItems(new AnimalStack[]{new AnimalStack(i(Items.beef), 1, 2, 3), new AnimalStack(i(Items.leather), 1, 2)})
                .setRanchableFluid(new FluidStack(ModFluids.milk, 1000)).setRanchableTime(200)
                .setRareDrop(new AnimalStack(i(Items.name_tag), 2, 1, 98, 0));

        CHICKEN = newAnimal("Chicken", "Chicken")
                .setBreedingItems(new ItemStack[]{i(Items.wheat_seeds)})
                .setDropItems(new AnimalStack[]{new AnimalStack(i(Items.chicken), 1), new AnimalStack(i(Items.feather), 0, 1, 2)})
                .setRanchableItem(new AnimalStack(i(Items.egg), 66, 1, 34, 2)).setRanchableTime(2000)
                .setRareDrop(new AnimalStack(i(Items.dye, 3), 15, 1, 85, 0));

        SQUID = newAnimal("Squid", "Squid")
                .setBreedingItems(new ItemStack[]{i(ModItems.itemSquidFood)})
                .setDropItems(new AnimalStack[]{new AnimalStack(i(Items.dye, 0), 1, 2, 3)})
                .setRanchableItem(new AnimalStack(i(Items.dye, 0), 66, 1, 34, 2)).setRanchableTime(1000)
                .setRareDrop(new AnimalStack(i(Items.glass_bottle), 15, 1, 85, 0));

        WOLF = newAnimal("Wolf", "Wolf")
                .setBreedingItems(new ItemStack[]{i(Items.beef), i(Items.cooked_beef), i(Items.porkchop), i(Items.cooked_porkchop), i(Items.mutton), i(Items.cooked_mutton), i(Items.rabbit), i(Items.cooked_rabbit),});

        MOOSHROOM = newAnimal("MushroomCow", "Mooshroom Cow")
                .setBreedingItems(new ItemStack[]{i(Items.wheat)})
                .setDropItems(new AnimalStack[]{new AnimalStack(i(Items.beef), 1, 2, 3), new AnimalStack(i(Items.leather), 1, 2)})
                .setRanchableFluid(new FluidStack(ModFluids.milk, 1000)).setRanchableTime(200)
                .setRareDrop(new AnimalStack(i(Blocks.mycelium), 1, 1, 99, 0));

        OCELOT = newAnimal("Ozelot", "Ocelot")
                .setBreedingItems(new ItemStack[]{i(Items.fish, 0), i(Items.fish, 1), i(Items.fish, 2)});

        HORSE = newAnimal("EntityHorse", "Horse")
                .setBreedingItems(new ItemStack[]{i(Items.golden_apple), i(Items.golden_carrot)})
                .setDropItems(new AnimalStack[]{new AnimalStack(i(Items.leather), 0, 1, 2)})
                .setRareDrop(new AnimalStack(i(Items.saddle), 1, 1, 99, 0));

        RABBIT = newAnimal("Rabbit", "Rabbit")
                .setBreedingItems(new ItemStack[]{i(Items.carrot), i(Items.golden_carrot), i(Blocks.yellow_flower), i(Blocks.red_flower, 0), i(Blocks.red_flower, 1), i(Blocks.red_flower, 2), i(Blocks.red_flower, 3), i(Blocks.red_flower, 4), i(Blocks.red_flower, 5), i(Blocks.red_flower, 6), i(Blocks.red_flower, 7), i(Blocks.red_flower, 8)})
                .setDropItems(new AnimalStack[]{new AnimalStack(i(Items.rabbit_hide), 0, 1), new AnimalStack(i(Items.rabbit), 0, 1), new AnimalStack(i(Items.rabbit_foot), 10, 1, 90, 0)})
                .setRareDrop(new AnimalStack(i(Items.cookie), 20, 1, 80, 0));

        nameToEggsMap = null;
    }

}
