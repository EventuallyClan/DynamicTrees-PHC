package com.ferreusveritas.dynamictreesphc.trees;

import com.ferreusveritas.dynamictrees.api.treedata.ILeavesProperties;
import com.ferreusveritas.dynamictrees.systems.featuregen.FeatureGenFruit;
import com.ferreusveritas.dynamictrees.trees.SpeciesRare;
import com.ferreusveritas.dynamictrees.trees.TreeFamily;
import com.pam.harvestcraft.blocks.BlockRegistry;
import com.pam.harvestcraft.blocks.growables.BlockPamFruit;
import com.pam.harvestcraft.blocks.growables.BlockPamSapling.SaplingType;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.BiomeDictionary.Type;

public class SpeciesFruit extends SpeciesRare {
	
	public final String fruitName;
	public final SaplingType saplingType;
	public IBlockState fruitBlockState;
	private int fruitingRadius = 5;
	
	public SpeciesFruit(ResourceLocation name, TreeFamily treeFamily, ILeavesProperties leavesProperties, String fruitName, SaplingType saplingType) {
		super(name, treeFamily, leavesProperties);
		this.fruitName = fruitName;
		this.saplingType = saplingType;
		
		fruitTreeDefaults();
		
		switch(saplingType) {
			default:
			case TEMPERATE:
				envFactor(Type.COLD, 0.75f);
				envFactor(Type.HOT, 0.75f);
				break;
			case COLD:
				envFactor(Type.HOT, 0.50f);
				break;
			case WARM:
				envFactor(Type.COLD, 0.50f);
				break;
		}
		
		generateSeed();
		
		Block fruitBlock = BlockRegistry.blocks.stream().filter(b -> b.getRegistryName().getResourcePath().equals("pam" + fruitName)).findFirst().get();
		IBlockState ripeFruit = fruitBlock.getDefaultState().withProperty(BlockPamFruit.AGE, 2);
		IBlockState unripeFruit = fruitBlock.getDefaultState().withProperty(BlockPamFruit.AGE, 0);
		
		fruitBlockState = fruitBlock.getDefaultState();
		addGenFeature(new FeatureGenFruit(unripeFruit, ripeFruit).setRayDistance(4).setFruitingRadius(fruitingRadius));
	}
	
	@Override
	public ResourceLocation getSaplingName() {
		String dtModId = com.ferreusveritas.dynamictrees.ModConstants.MODID;
		
		switch(saplingType) {
			default:
			case TEMPERATE: return new ResourceLocation(dtModId, "oak");
			case COLD: return new ResourceLocation(dtModId, "spruce");
			case WARM: return new ResourceLocation(dtModId, "jungle");
		}
	}
	
	protected void fruitTreeDefaults() {
		setBasicGrowingParameters(0.3f, 8.0f, 1, 4, 1.0f, fruitingRadius);
	}
	
	protected SpeciesFruit setBasicGrowingParameters(float tapering, float energy, int upProbability, int lowestBranchHeight, float growthRate, int fruitingRadius) {
		setBasicGrowingParameters(tapering, energy, upProbability, lowestBranchHeight, growthRate);
		this.fruitingRadius = fruitingRadius;
		return this;
	}
	
	public BlockPamFruit getFruitBlock() {
		return (BlockPamFruit) fruitBlockState.getBlock();
	}
	
	@Override
	public void addJoCodes() {
		joCodeStore.addCodesFromFile(this, "assets/" + getRegistryName().getResourceDomain() + "/trees/fruit.txt");
	}
	
}
