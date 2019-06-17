package com.example.cybermap

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder

class DBHandler(context: Context) : SQLiteOpenHelper(context, DBName, null, DBVersion) {
    companion object {
        val DBName = "ComputerClubsDB"
        val DBVersion = 1
        val tableName = "computerClubsTable"
        val _id = "id"
        val name = "Name"
        val address = "Address"
        val phone = "Phone"
        val site = "Site"
        val hours = "Hours"
        val isAvailableOnlineBooking = "IsAvailableOnlineBooking"
        val coordinates = "Coordinates"
    }

    var sqlObj: SQLiteDatabase = this.writableDatabase

    override fun onCreate(db: SQLiteDatabase?) {
        var sql1: String =
            "CREATE TABLE IF NOT EXISTS $tableName ( $_id  INTEGER PRIMARY KEY, $name TEXT, $address TEXT, $phone TEXT, $site TEXT, $hours TEXT, $isAvailableOnlineBooking TEXT, $coordinates TEXT);"
        db!!.execSQL(sql1);

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("Drop table IF EXISTS $tableName")
        onCreate(db)

    }

    fun addComputerClub(values: ContentValues) = sqlObj.insert(tableName, "", values)

    fun removeComputerClub(id: Int) = sqlObj.delete(tableName, "id=?", arrayOf(id.toString()))

    fun updateComputerClub(values: ContentValues, id: Int) =
        sqlObj.update(tableName, values, "id=?", arrayOf(id.toString()))

    fun listComputerClubs(key: String): ArrayList<ComputerClubData> { // key = % - найти все записи
        var arraylist = ArrayList<ComputerClubData>()
        var sqlQB = SQLiteQueryBuilder()
        sqlQB.tables = tableName
        var cols = arrayOf(_id, name, address, phone, site, hours, isAvailableOnlineBooking, coordinates)
        var selectArgs = arrayOf(key)
        var cursor = sqlQB.query(sqlObj, cols, "$name like ?", selectArgs, null, null, name)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(_id))
                val Name = cursor.getString(cursor.getColumnIndex(name))
                val Address = cursor.getString(cursor.getColumnIndex(address))
                val Phone = cursor.getString(cursor.getColumnIndex(phone))
                val Site = cursor.getString(cursor.getColumnIndex(site))
                val Hours = cursor.getString(cursor.getColumnIndex(hours))
                val IsAvailableOnlineBooking = cursor.getShort(cursor.getColumnIndex(isAvailableOnlineBooking))

                val tempCoordinates = cursor.getString(cursor.getColumnIndex(coordinates)).split(", ")
                val Coordinates = arrayListOf<Double>(tempCoordinates[0].toDouble(), tempCoordinates[1].toDouble())

                arraylist.add(ComputerClubData(id, Name, Address, Phone, Site, Hours, IsAvailableOnlineBooking, Coordinates))

            } while (cursor.moveToNext())
        }
        return arraylist
    }

}

