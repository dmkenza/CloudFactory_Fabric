package com.kenza.cloud.datagen.generators

import com.kenza.cloud.datagen.base.DataGenerator
import net.minecraft.util.Identifier
import java.io.File

class BlockGenerator(
    val rootAssests: File,
    val rootData: File,
    namespace: String,
    fallback: (String) -> PattersFactory<String>
) : DataGenerator<String, String?>(File(rootAssests, "models/block"), namespace, fallback) {


    val blockstatesOutput = File(rootAssests, "blockstates")
    val loottablesOutput = File(rootData, "loot_tables/blocks")
    val recipesOutput = File(rootData, "recipes")


    val tags = HashMap<String, String>()

    override fun generate(): Int {
        var count = 0
        generators.forEach { (key, _) ->
            val tag = tags[key]

            if (generate(Identifier(tag), key))
                count++

        }
        return count / 3
    }

    fun register(material: String, color: String) {

        val tag = material + "_" + POSTFIX
        tags[tag] = tag
        register(tag, PatternsFactory.MODELS_BLOCKS_PART(material, namespace, POSTFIX))

        val tagBlock = material + "_" + POSTFIX
        val key = "blockstates_$tagBlock"
        tags[key] = tagBlock
        register(key, PatternsFactory.BLOCKSTATES_PART(material, namespace, POSTFIX, blockstatesOutput))


        val key1 = "dropself_$tagBlock"
        tags[key1] = tagBlock
        register(key1, PatternsFactory.LOOTTABLES_SELFDROP(tagBlock, namespace, loottablesOutput))


        val key2 = "recipes_$tagBlock"
        tags[key2] = tagBlock
        val tagMaterial = "${material}_block"

        register(key2,
            PatternsFactory.RECIPES2(tagBlock, namespace, tagMaterial, BLOCK_POSTFIX, color ,recipesOutput)
        )



    }

    companion object {
        val BLOCK_POSTFIX = "cloud_block"
        val POSTFIX = "block"
    }
}

