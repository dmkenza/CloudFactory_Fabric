package com.kenza.cloud.block

import CraftingComponent
import com.kenza.cloud.CloudFactoryMod
import com.kenza.cloud.block.CloudGeneratorHandler.Companion.CRAFTING_COMPONENT_ID
import com.kenza.cloud.block.base.BaseBlockEntity
import com.kenza.cloud.block.base.GuiSyncableComponent
import com.kenza.cloud.block.base.trackObject
import com.kenza.cloud.debug
import com.kenza.cloud.item.AlumentumItem
import com.kenza.cloud.utils.ImplementedInventory
import com.kenza.cloud.utils.isRenderThread
import net.minecraft.block.BlockState
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventories
import net.minecraft.inventory.SimpleInventory
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtList
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket
import net.minecraft.screen.NamedScreenHandlerFactory
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.ScreenHandlerContext
import net.minecraft.text.Text
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World
import java.util.*

class CloudGeneratorBlockEntity (pos: BlockPos, state: BlockState) :
    BaseBlockEntity(CloudFactoryMod.CLOUD_GENERATOR_TYPE, pos, state),
    ImplementedInventory,
    NamedScreenHandlerFactory {

    override var guiSyncableComponent: GuiSyncableComponent = GuiSyncableComponent()

    var isMarkedForUpdate: Boolean = true


    private val INVENTORY_SIZE = 3;

    var inventoriesItems: DefaultedList<ItemStack> = DefaultedList.ofSize(INVENTORY_SIZE, ItemStack.EMPTY)
    private var inventoryTouched = true


    var craftingComponent: CraftingComponent = CraftingComponent(this)


    override fun setStack(slot: Int, stack: ItemStack?) {
        if(isRenderThread()){
//            debug("setStack  = ${stack.toString()}")
        }

        super.setStack(slot, stack)
    }

    override fun removeStack(slot: Int): ItemStack {

        return super.removeStack(slot).also {
        }
    }

    override fun canInsert(slot: Int, stack: ItemStack?, side: Direction?): Boolean {

        val canInsert = when(side){
            Direction.UP ->  {
                slot == 0
            }
            Direction.DOWN , Direction.NORTH , Direction.SOUTH , Direction.WEST, Direction.EAST -> {
                slot == 2 && stack?.item is AlumentumItem
            }
            else -> false
        }

        return canInsert
    }



    override fun canExtract(slot: Int, stack: ItemStack?, side: Direction?): Boolean {

        val canExtract = when(side){
            Direction.DOWN ->  {
                slot == 1
            }
            else -> false
        }

        return canExtract
    }

    init {
        trackObject(CRAFTING_COMPONENT_ID, craftingComponent)
    }


    override fun readNbt(nbt: NbtCompound) {
        super.readNbt(nbt)
//        Inventories.readNbt(nbt, items)
//        craftingComponent.readNbt(nbt)

        val tagList = nbt.get("Inventory") as NbtList? ?: NbtList()
        tagList.indices.forEach { i ->
            val stackTag = tagList.getCompound(i)
            val slot = stackTag.getInt("Slot")
            setStack(slot, ItemStack.fromNbt(stackTag))
        }
//        itemConfig.readNbt(tag)
    }

    public override fun writeNbt(nbt: NbtCompound) {
//        Inventories.writeNbt(nbt, items)

        val tagList = NbtList()
        for (i in 0 until items.size) {
            val stackTag = NbtCompound()
            stackTag.putInt("Slot", i)
            tagList.add(getStack(i).writeNbt(stackTag))
        }
        nbt.put("Inventory", tagList)


        super.writeNbt(nbt)
//        craftingComponent.writeNbt(nbt)
    }

    override fun toUpdatePacket(): BlockEntityUpdateS2CPacket {
        return BlockEntityUpdateS2CPacket.create(this)
    }

    override fun toInitialChunkDataNbt(): NbtCompound {
        val nbt = super.toInitialChunkDataNbt()
        writeNbt(nbt)
//        nbt.putBoolean("#c", true)
        return nbt
    }


    override fun markDirty() {
        super<BaseBlockEntity>.markDirty()
    }


    override fun getItems(): DefaultedList<ItemStack> {
        inventoryTouched = true
        return inventoriesItems
    }

    override fun createMenu(syncId: Int, inv: PlayerInventory, player: PlayerEntity?): ScreenHandler? {
        return CloudGeneratorHandler(
            syncId,
            inv,
            ScreenHandlerContext.create(world, pos)
        )
    }

    override fun getDisplayName(): Text {
        return Text.literal("CloudGenerator")
    }




    fun tick(world: World, blockPos: BlockPos, blockState: BlockState) {
        craftingComponent.tick(world, blockPos, blockState)

        if (isMarkedForUpdate) {
            markDirty()
            sync()
            isMarkedForUpdate = false
        }
    }

}