package com.ermans.bottledanimals.block;

import com.ermans.bottledanimals.BottledAnimalsTab;
import com.ermans.bottledanimals.reference.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public abstract class BlockBase extends Block {

    public static final SoundType soundTypeMachineFrame = new SoundType("stone", 1.0F, 1.0F) {

        @Override
        public String getBreakSound() {
            return "dig.glass";
        }
    };

    protected final String blockName;


    public BlockBase(String blockName) {
        super(new Material(MapColor.ironColor));
        this.blockName = blockName;
        setHardness(0.5F);
        setUnlocalizedName(blockName);
        setStepSound(Block.soundTypeMetal);
        setHarvestLevel("pickaxe", 0);
        setCreativeTab(BottledAnimalsTab.tabBottledAnimals);
    }


//
//    @Override
//    @SideOnly(Side.CLIENT)
//    public void registerBlockIcons(TextureUtils.IIconRegister iconRegister) {
//        this.blockIcon = iconRegister.registerIcon(String.format("%s", getUnwrappedUnlocalizedName(getUnlocalizedName())));
//    }


    @Override
    public String getUnlocalizedName() {
        return String.format("block.%s%s", Reference.MOD_ID_LOWERCASE + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    private String getUnwrappedUnlocalizedName(String unlocalizedName) {
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
    }

    protected String getBlockName() {
        return this.blockName;
    }

//    public static class SoundType extends Block.SoundType {
//
//        public SoundType(String soundName, float volume, float frequency) {
//            super(soundName, volume, frequency);
//        }
//
//        public SoundType(String soundName) {
//            this(soundName, 1.0F, 1.0F);
//        }
//
//        @Override
//        public float getVolume() {
//            return this.volume;
//        }
//
//        @Override
//        public float getPitch() {
//            return this.frequency;
//        }
//
//        @Override
//        public String getBreakSound() {
//            return "dig." + this.soundName;
//        }
//
//        @Override
//        public String getStepResourcePath() {
//            return "step." + this.soundName;
//        }
//
//        @Override
//        public String func_150496_b() {
//            return getBreakSound();
//        }
//    }
}
