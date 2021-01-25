package org.vimteam.notes.ui.providers

import android.content.Context
import org.vimteam.notes.domain.contracts.ResourcesProviderContract

class ResourcesProvider(private val context: Context) : ResourcesProviderContract{

    override fun getString(id: Int) = context.getString(id)

    override fun getString(stringName: String) = context.getString(context.resources.getIdentifier(stringName, "string", context.packageName))

}