package com.ferreusveritas.dynamictreesphc;

import com.ferreusveritas.dynamictrees.api.client.ModelHelper;
import com.ferreusveritas.dynamictrees.items.Seed;
import com.ferreusveritas.dynamictrees.trees.TreeFamily;

import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModModels {

	@SideOnly(Side.CLIENT)
	public static void register(ModelRegistryEvent event) {
		
		//Register Meshers for Branches
		for(TreeFamily tree: ModTrees.phcTrees) {
			ModelHelper.regModel(tree.getDynamicBranch());//Register Branch itemBlock
			ModelHelper.regModel(tree);//Register custom state mapper for branch
		}
		
		ModTrees.phcSpecies.values().stream().filter(s -> s.getSeed() != Seed.NULLSEED).forEach(s -> ModelHelper.regModel(s.getSeed()));//Register Seed Item Models
	}
	
}
