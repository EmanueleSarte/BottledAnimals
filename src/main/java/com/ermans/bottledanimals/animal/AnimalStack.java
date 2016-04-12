package com.ermans.bottledanimals.animal;

import net.minecraft.item.ItemStack;

import java.security.InvalidParameterException;
import java.util.Random;

public class AnimalStack {

    private ItemStack itemStack;
    // 30, 1, 40, 2, 30, 3 means there is a 30% to get 1 as quantity
    // 40% to get 2 as quantity and another 30% to get 3 as quantity;
    // 20, 1, 40, 2, 40, 0 means 20%->1, 40%-> 2, 40%->0
    // 80, 3, 20, 4 means 80%->3, 20% 4;
    // 1, 2, 5, 6, 7, 3 means 16%->1, 16%->2, 16%->5, 16%->6, 16%->7, 3%->8 (note: the last has 100-total%)
    // 1 means 100%->1;
    private int[] probability;
    private int[] quantity;
    private int maxQuantity;
    private int minQuantity;

    private static final Random random = new Random();


    public AnimalStack(ItemStack itemStack, int... probability) {
        this.itemStack = itemStack;

        if (probability.length != 0) {

            boolean onlyQuantity;
            if (probability.length % 2 != 0) {
                onlyQuantity = true;
            } else {
                int prob = 0;
                for (int i = 0; i < probability.length; i += 2) {
                    prob += probability[i];
                }
                onlyQuantity = prob != 100;
            }


            ///there is no probability, just quantity ex: 1,4,7,6 so assign an equal probability to each one
            if (onlyQuantity) {
                this.probability = new int[probability.length];
                this.quantity = probability;

                int lastProbability = 0;
                for (int i = 0; i < this.probability.length - 1; i++) {
                    lastProbability = this.probability[i] = 100 / this.probability.length + lastProbability;
                }
                this.probability[this.probability.length - 1] = 100;

            } else {

                this.probability = new int[probability.length / 2];
                this.quantity = new int[probability.length / 2];

                int lastProbability = 0;
                for (int i = 0; i < probability.length - 2; i += 2) {
                    lastProbability = this.probability[i / 2] = lastProbability + probability[i];
                    this.quantity[i / 2] = probability[i + 1];
                }

                this.probability[this.probability.length - 1] = 100;
                this.quantity[this.quantity.length - 1] = probability[probability.length - 1];

                minQuantity = maxQuantity = this.quantity[0];
            }

            for (int value : this.quantity) {
                if (value < minQuantity) {
                    minQuantity = value;
                }
                if (value > maxQuantity) {
                    maxQuantity = value;
                }
            }

        } else {
            throw new InvalidParameterException("Probability array length is 0 when creating AnimalStack with ItemStack " + itemStack.toString());
        }
    }

    public ItemStack get() {
        int prob = random.nextInt(100) + 1;
        int quantityByProb = getQuantityByProb(prob);
        if (quantityByProb > 0) {
            return new ItemStack(itemStack.getItem(), quantityByProb, itemStack.getItemDamage());
        } else {
            return null;
        }
    }

    private int getQuantityByProb(int prob) {
        for (int i = 0; i < probability.length; i++) {
            if (prob <= probability[i]) {
                return quantity[i];
            }
        }
        //It shouldn't go here
        return minQuantity;
    }

    public ItemStack getOriginal() {
        return itemStack;
    }

    public ItemStack getMaximum() {
        return new ItemStack(itemStack.getItem(), maxQuantity, itemStack.getItemDamage());
    }

    public ItemStack getMinimum() {
        return new ItemStack(itemStack.getItem(), minQuantity, itemStack.getItemDamage());
    }

    public int getMaxQuantity() {
        return maxQuantity;
    }

    public int getMinQuantity() {
        return minQuantity;
    }

}
