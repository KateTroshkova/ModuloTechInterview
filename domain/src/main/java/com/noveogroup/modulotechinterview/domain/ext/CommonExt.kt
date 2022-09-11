package com.noveogroup.modulotechinterview.domain.ext

inline fun <reified T> Any.safeCast(): T? = this as? T