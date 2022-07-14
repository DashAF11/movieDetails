package com.example.moviedetails.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseAdapter<T, VB : ViewBinding>(
    comparator: DiffUtil.ItemCallback<T>, @LayoutRes private final val layoutId: Int
) : ListAdapter<T, BaseAdapter<T, VB>.VH>(comparator) {

    lateinit var listener: ((view: View, item: T, position: Int) -> Unit)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                layoutId,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(currentList[position], position)
    }

    inner class VH(private val viewBinding: VB) : RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(item: T, position: Int) {//name surname city email
            bind(viewBinding, item, position)
        }
    }

    /**
     * it binds the [ViewBinding][VB] from the [T] item
     * @param [ViewBinding][VB] is a [ViewBinding][VB], which going to bind with the item [T]
     * @param item is data, which going to bind with the [ViewBinding][VB]
     */
    abstract fun bind(viewBinding: VB, item: T, position: Int)
}