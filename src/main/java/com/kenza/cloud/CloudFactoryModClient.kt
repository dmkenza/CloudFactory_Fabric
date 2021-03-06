package com.kenza.cloud

import com.kenza.cloud.CloudFactoryMod.Companion.CLOUD_GENERATOR_TYPE
import com.kenza.cloud.block.Blocks
import com.kenza.cloud.block.Blocks.CLOUD_BLOCKS
import com.kenza.cloud.block.Blocks.CLOUD_FENCE_BLOCKS
import com.kenza.cloud.block.Blocks.CLOUD_SLAB_BLOCKS
import com.kenza.cloud.block.Blocks.CLOUD_STAIRS_BLOCKS
import com.kenza.cloud.block.Blocks.CLOUD_WALL_BLOCKS
import com.kenza.cloud.gui.factory.IRInventoryScreen
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry
import net.minecraft.client.gui.screen.ingame.HandledScreens
import net.minecraft.client.render.RenderLayer


//[08:43:53] [Render thread/INFO] (Minecraft) [STDOUT]: white_discholder
//[08:43:53] [Render thread/INFO] (Minecraft) [STDOUT]: orange_discholder
//[08:43:53] [Render thread/INFO] (Minecraft) [STDOUT]: magenta_discholder
//[08:43:53] [Render thread/INFO] (Minecraft) [STDOUT]: light_blue_discholder
//[08:43:53] [Render thread/INFO] (Minecraft) [STDOUT]: yellow_discholder
//[08:43:53] [Render thread/INFO] (Minecraft) [STDOUT]: lime_discholder
//[08:43:53] [Render thread/INFO] (Minecraft) [STDOUT]: pink_discholder
//[08:43:53] [Render thread/INFO] (Minecraft) [STDOUT]: gray_discholder
//[08:43:53] [Render thread/INFO] (Minecraft) [STDOUT]: light_gray_discholder
//[08:43:53] [Render thread/INFO] (Minecraft) [STDOUT]: cyan_discholder
//[08:43:53] [Render thread/INFO] (Minecraft) [STDOUT]: purple_discholder
//[08:43:53] [Render thread/INFO] (Minecraft) [STDOUT]: blue_discholder
//[08:43:53] [Render thread/INFO] (Minecraft) [STDOUT]: brown_discholder
//[08:43:53] [Render thread/INFO] (Minecraft) [STDOUT]: green_discholder
//[08:43:53] [Render thread/INFO] (Minecraft) [STDOUT]: red_discholder
//[08:43:53] [Render thread/INFO] (Minecraft) [STDOUT]: black_discholder


class CloudFactoryModClient : ClientModInitializer {
    override fun onInitializeClient() {

//        BlockEntityRendererRegistry.register(CLOUD_GENERATOR_TYPE) {
//        }

        (CLOUD_BLOCKS + CLOUD_STAIRS_BLOCKS + CLOUD_SLAB_BLOCKS + CLOUD_WALL_BLOCKS
                + CLOUD_FENCE_BLOCKS + Blocks.CLOUD_GATE_BLOCKS).forEach { block ->
                BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getTranslucent())
        }

//
//        CLOUD_BLOCKS.map { block ->
//            BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getTranslucent())
//        }
//        CLOUD_STAIRS_BLOCKS.map { block ->
//            BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getTranslucent())
//        }
////
//        CLOUD_SLAB_BLOCKS.map { block ->
//        }
//
//        CLOUD_WALL_BLOCKS.map { block ->
//            BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getTranslucent())
//        }

//        Blocks.CLOUD_FENCE_BLOCKS

        HandledScreens.register(CloudFactoryMod.CLOUD_GENERATOR_HANDLER) { controller, inv, _ -> IRInventoryScreen(controller, inv.player) }

    }
}