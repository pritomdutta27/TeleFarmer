package bio.medico.patient.common.ui

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding


/**
Created by Samiran Kumar on 10,September,2023
 **/


/**
 * Base view holder to standardize and simplify initialization for this component.
 *
 * @param binding View data binding generated class instance.
 * @see RecyclerView.ViewHolder
 */
abstract class BaseViewHolder<T : ViewBinding>(
    val binding: T
) : RecyclerView.ViewHolder(binding.root)
