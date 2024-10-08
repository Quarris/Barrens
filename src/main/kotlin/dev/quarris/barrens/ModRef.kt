package dev.quarris.barrens

import net.minecraft.resources.ResourceLocation
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

object ModRef {
    const val ID: String = "barrens"
    val Logger: Logger = LogManager.getLogger(ID)

    fun res(name: String): ResourceLocation {
        return ResourceLocation(ID, name)
    }

    fun key(group: String, key: String): String {
        return "${group}.${ID}.${key}"
    }
}