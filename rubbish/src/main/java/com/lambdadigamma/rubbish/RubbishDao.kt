package com.lambdadigamma.rubbish

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RubbishDao {

    @Query("SELECT * FROM rubbish_collection_items")
    fun loadAllRubbishCollectionItems(): LiveData<List<RubbishCollectionItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRubbishCollectionItems(items: List<RubbishCollectionItem>)

    @Delete
    fun deleteRubbishCollectionItems(vararg items: RubbishCollectionItem)

}