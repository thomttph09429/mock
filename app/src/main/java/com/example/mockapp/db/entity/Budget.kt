package com.example.mockapp.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mockapp.util.Constant.BUDGET_TABLE
import com.example.mockapp.util.Constant.COLUMN_BACKGROUND_COLOR
import com.example.mockapp.util.Constant.COLUMN_ID
import com.example.mockapp.util.Constant.COLUMN_IMAGE
import com.example.mockapp.util.Constant.COLUMN_TITLE
import com.example.mockapp.util.Constant.COLUMN_VALUE

@Entity(tableName = BUDGET_TABLE)
data class Budget(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = COLUMN_ID)
    var budgetId: Int ,
    @ColumnInfo(name = COLUMN_IMAGE)
    var budgetImage: Int,
    @ColumnInfo(name = COLUMN_TITLE)
    var budgetTitle: String,
    @ColumnInfo(name = COLUMN_VALUE)
    var budgetValue: Long,
    @ColumnInfo(name = COLUMN_BACKGROUND_COLOR)
    var backgroundColor: Int

)