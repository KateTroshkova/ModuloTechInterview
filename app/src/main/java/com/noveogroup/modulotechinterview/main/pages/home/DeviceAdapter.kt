package com.noveogroup.modulotechinterview.main.pages.home

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.noveogroup.modulotechinterview.R
import com.noveogroup.modulotechinterview.common.android.adapter.BaseRecyclerAdapter
import com.noveogroup.modulotechinterview.common.android.ext.inflateChild
import com.noveogroup.modulotechinterview.databinding.ItemDeviceBinding
import com.noveogroup.modulotechinterview.domain.entity.device.Device
import com.noveogroup.modulotechinterview.domain.entity.device.Heater
import com.noveogroup.modulotechinterview.domain.entity.device.Light
import com.noveogroup.modulotechinterview.domain.entity.device.Shutter
import com.noveogroup.modulotechinterview.domain.entity.type.ProductType

class DeviceAdapter(
    private val onClick: (device: Device) -> Unit,
    private val onLongClick: (device: Device) -> Unit
) : BaseRecyclerAdapter<Device, DeviceAdapter.DeviceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
        return DeviceViewHolder(parent)
    }

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        holder.bind(items[position], onClick, onLongClick)
    }

    inner class DeviceViewHolder(
        parent: ViewGroup,
        private val binding: ItemDeviceBinding = parent.inflateChild(
            ItemDeviceBinding::inflate
        )
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: Device,
            onClick: (device: Device) -> Unit,
            onLongClick: (device: Device) -> Unit
        ) = with(itemView) {
            with(binding) {
                deviceImageView.setImageResource(
                    when (item.productType) {
                        ProductType.LIGHT -> R.drawable.ic_light
                        ProductType.SHUTTER -> R.drawable.ic_shutters
                        ProductType.HEATER -> R.drawable.ic_heater
                    }
                )
                nameTextView.text = item.deviceName
                currentValueTextView.text = when (item) {
                    is Light -> item.intensity.toString()
                    is Heater -> item.temperature.toString()
                    is Shutter -> item.position.toString()
                    else -> ""
                }
                backgroundLayout.setOnClickListener {
                    onClick(item)
                }
                backgroundLayout.setOnClickListener {
                    onLongClick(item)
                }
            }
        }
    }
}
