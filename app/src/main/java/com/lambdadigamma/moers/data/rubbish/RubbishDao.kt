package com.lambdadigamma.moers.data.rubbish

import androidx.room.*

@Dao
interface RubbishDao {

    @Query("SELECT * FROM rubbish_collection_items")
    fun loadAllRubbishCollectionItems(): Array<RubbishCollectionItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRubbishCollectionItems(vararg items: RubbishCollectionItem)

    @Delete
    fun deleteRubbishCollectionItems(vararg items: RubbishCollectionItem)
    
}