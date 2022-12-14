package com.noveogroup.modulotechinterview.domain.entity.device

import com.noveogroup.modulotechinterview.domain.entity.type.ProductType

sealed class Device(
    val id: String,
    val deviceName: String,
    val productType: ProductType
) : java.io.Serializable
