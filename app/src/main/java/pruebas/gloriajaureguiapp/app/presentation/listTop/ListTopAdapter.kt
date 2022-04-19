package pruebas.gloriajaureguiapp.app.presentation.listTop

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import pruebas.gloriajaureguiapp.R
import pruebas.gloriajaureguiapp.apiservice.entities.ListTopResultset
import pruebas.gloriajaureguiapp.apiservice.entities.ValueType
import pruebas.gloriajaureguiapp.app.utils.formatAsCurrency
import pruebas.gloriajaureguiapp.app.utils.roundHalfUp
import pruebas.gloriajaureguiapp.databinding.ItemHeaderBinding
import pruebas.gloriajaureguiapp.databinding.ItemListBinding

class ListTopAdapter internal constructor(
    private val items: List<ItemList>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var context: Context

    companion object {
        private const val TYPE_HEADER = 0
        private var TYPE_ITEM = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        return if (viewType == TYPE_ITEM) {
            val inflater =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_list, parent, false)
            val binding = ItemListBinding.bind(inflater)
            ItemViewHolder(binding)
        } else {
            val inflater =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_header, parent, false)
            val binding = ItemHeaderBinding.bind(inflater)
            HeaderViewHolder(binding)
        }
    }

    inner class ItemViewHolder(val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ListTopResultset) {
            binding.tvConcept.text = item.issueId
            binding.tvAmount.text = item.lastPrice.formatAsCurrency()
            binding.tvPercenteje.text = context.getString(
                R.string.lbl_list_percentage,
                item.percentageChange.roundHalfUp(2)
            )
            binding.tvPercenteje.setTextColor(
                ContextCompat.getColor(
                    binding.root.context,
                    tintPercentage(item.riseLowTypeId.title)
                )
            )
        }
    }

    inner class HeaderViewHolder(val binding: ItemHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.tvConcept.text = item
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            TYPE_HEADER -> {
                (holder as HeaderViewHolder).bind((items[position] as ItemList.Header).date)
            }
            TYPE_ITEM -> {
                (holder as ItemViewHolder).bind((items[position] as ItemList.Item).data)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is ItemList.Header -> TYPE_HEADER
            else -> TYPE_ITEM
        }
    }

    private fun tintPercentage(type: String): Int {
        return when (type) {
            ValueType.TYPE_RISE.title -> R.color.type_rise
            ValueType.TYPE_LOW.title -> R.color.type_low
            ValueType.TYPE_VOLUME.title -> R.color.type_rise
            else -> R.color.type_rise
        }
    }
}