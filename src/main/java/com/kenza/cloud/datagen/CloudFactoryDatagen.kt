package com.kenza.cloud.datagen

import com.kenza.cloud.CloudFactoryMod.Companion.MOD_ID
import com.kenza.cloud.block.clouds.CloudAttribute
import com.kenza.cloud.block.clouds.CloudBlock
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider
import net.minecraft.block.Material
import net.minecraft.tag.BlockTags
import net.minecraft.util.registry.Registry

class CloudFactoryDatagen : DataGeneratorEntrypoint {
    override fun onInitializeDataGenerator(datagen: FabricDataGenerator) {

        datagen.output

        datagen.addProvider(CloudFactoryDatagen::IRBlockTagProvider)
    }

    class IRBlockTagProvider(datagen: FabricDataGenerator) : FabricTagProvider.BlockTagProvider(datagen) {
        override fun generateTags() {
            Registry.BLOCK.ids.forEach { id ->
                if (id.namespace == MOD_ID) {
                    val block = Registry.BLOCK.get(id)
                    if (block.defaultState.material == Material.METAL || block.defaultState.material == Material.STONE) {
                        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(block)
                    }
                    if (block.defaultState.material == Material.WOOD) {
                        getOrCreateTagBuilder(BlockTags.AXE_MINEABLE).add(block)
                    }

                    if (block is CloudAttribute) {
                        getOrCreateTagBuilder(BlockTags.SHOVEL_MINEABLE).add(block)
                    }
                }
            }
        }

    }
}