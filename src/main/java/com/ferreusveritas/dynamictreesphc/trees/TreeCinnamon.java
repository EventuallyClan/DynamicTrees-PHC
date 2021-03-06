package com.ferreusveritas.dynamictreesphc.trees;

import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictrees.trees.TreeFamily;
import com.ferreusveritas.dynamictreesphc.ModBlocks;
import com.ferreusveritas.dynamictreesphc.ModConstants;
import com.ferreusveritas.dynamictreesphc.dropcreators.DropCreatorFruitLogProduct;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class TreeCinnamon extends TreeFamilyPHC {
	
	public static final String speciesName = "cinnamon";
	
	//Species need not be created as a nested class.  They can be created after the tree has already been constructed.
	public class TreeCinnamonSpecies extends Species {
		
		public TreeCinnamonSpecies(TreeFamily treeFamily) {
			super(treeFamily.getName(), treeFamily, ModBlocks.cinnamonLeavesProperties);
			
			setBasicGrowingParameters(0.25f, 11.0f, getUpProbability(), getLowestBranchHeight(), 1.0f);
			setupStandardSeedDropping();
			
			addDropCreator(new DropCreatorFruitLogProduct((TreeFamilyPHC) treeFamily));
		}
		
		@Override
		public boolean isBiomePerfect(Biome biome) {
			return BiomeDictionary.hasType(biome, Type.JUNGLE);
		}
		
	}
		
	public TreeCinnamon() {
		super(new ResourceLocation(ModConstants.MODID, speciesName));
		
		//Set up primitive log. This controls what is dropped on harvest.
		setPrimitiveLog(ModBlocks.primCinnamonLog.getDefaultState());
		
		ModBlocks.cinnamonLeavesProperties.setTree(this);
	}

	@Override
	public void createSpecies() {
		setCommonSpecies(new TreeCinnamonSpecies(this));
		getCommonSpecies().generateSeed();
	}
	
}
