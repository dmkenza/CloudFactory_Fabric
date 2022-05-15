package com.kenza.cloud

import com.github.charlyb01.xpstorage_trinkets.XpstorageTrinkets
import com.kenza.cloud.utils.openLastWorldOnInit
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.block.Block
import net.minecraft.block.Material
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.sound.BlockSoundGroup
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.LogManager.getLogger
import org.slf4j.Logger
import java.util.logging.Level
import java.util.logging.LogManager.getLogManager

class CloudFactoryMod : ModInitializer {


    //data get entity @music_disc_cleopona.json SelectedItem
    //give @p iron_pickaxe{Damage:10000} 20


    override fun onInitialize() {

        onConfig()
        openLastWorldOnInit()
    }


    fun onConfig() {


        XpstorageTrinkets.settings




        registerBlock("cloud",
            CloudBlock(
                FabricBlockSettings.of(Material.POWDER_SNOW).strength(0.25f).sounds(BlockSoundGroup.SNOW).dynamicBounds().nonOpaque()
            )
        )?.apply {
            CLOUD_BLOCKS.add(this)
        }
    }

    private fun registerBlockWithoutBlockItem(name: String, block: Block): Block? {
        return Registry.register(Registry.BLOCK, Identifier(MOD_ID, name), block)
    }

    private fun registerBlock(name: String, block: Block): Block? {
        registerBlockItem(name, block)
        return Registry.register(Registry.BLOCK, Identifier(MOD_ID, name), block)
    }

    private fun registerBlockItem(name: String, block: Block): Item? {
        return Registry.register(
            Registry.ITEM, Identifier(MOD_ID, name),
            BlockItem(block, FabricItemSettings().group(ItemGroup.MISC))
        )
    }



    companion object {

        var CLOUD_BLOCKS = ArrayList<Block>()

        @JvmField
        val MOD_ID = "cloud_factory"


        @JvmField
        val LOGGER = getLogger("cloud_factory")


        @JvmStatic
        fun debug(msg: String) {
            System.out.println("cloud_factory $msg")
//            CloudFactoryMod.LOGGER.log( org.apache.logging.log4j.Level.ALL, msg)
        }


    }
}

fun Any.debug(msg: String) {
    CloudFactoryMod.LOGGER.debug(msg)
}