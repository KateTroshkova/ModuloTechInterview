package com.noveogroup.modulotechinterview.data.database.dao

import androidx.room.*

internal abstract class BaseDao<T> {

    private val FAILED_OPERATION: Long get() = -1L

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(obj: T): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(obj: List<T>): List<Long>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun update(obj: T): Int

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun update(obj: List<T>): Int

    @Delete
    abstract suspend fun delete(obj: T): Int

    @Delete
    abstract suspend fun delete(obj: List<T>): Int

    @Transaction
    open suspend fun upsert(obj: T) {
        val result = insert(obj)
        if (result == FAILED_OPERATION) {
            update(obj)
        }
    }

    @Transaction
    open suspend fun upsert(objList: List<T>) {
        val result = insert(objList)
        val updateIndex = result.indices.filter { result[it] == FAILED_OPERATION }
        updateIndex.takeIf { it.isNotEmpty() }
            ?.let { list -> update(list.map { objList[it] }) }
    }

}
