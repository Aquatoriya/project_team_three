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
        val images = "Images"
    }

    var sqlObj: SQLiteDatabase = this.writableDatabase

    override fun onCreate(db: SQLiteDatabase?) {
        var sql1: String =
            "CREATE TABLE IF NOT EXISTS $tableName ( $_id  INTEGER PRIMARY KEY, $name TEXT, $address TEXT, $phone TEXT, $site TEXT, $hours TEXT, $isAvailableOnlineBooking TEXT, $coordinates TEXT, $images TEXT);"
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
        var cols = arrayOf(_id, name, address, phone, site, hours, isAvailableOnlineBooking, coordinates, images)
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

                val tempImages = cursor.getString(cursor.getColumnIndex(images))
                var ImagesList = emptyList<Int>()
                if (tempImages.isNotEmpty()) {
                    ImagesList = tempImages.subSequence(1, tempImages.length-1).split(", ").map {it.toInt()}
                }
                arraylist.add(ComputerClubData(id, Name, Address, Phone, Site, Hours, IsAvailableOnlineBooking, Coordinates, ImagesList))

            } while (cursor.moveToNext())
        }
        return arraylist
    }

    fun addComputerClub(){}

    fun addAllComputerClubs() {
        var values = addComputerCLub(
            0, "Gamer", "ул. Есенина, 9к2, Санкт-Петербург, 194354",
            "8 (812) 599-39-53", "",
            "круглосуточно", 0, "60.040150, 30.334697", listOf(R.drawable.no_photo, R.drawable.ctrlplay0_1).toString()
        )


        values = addComputerCLub(
            1, "CTRL PLAY", "пр. Энгельса, 27д, Санкт-Петербург, 194292, Burger King, 1й этаж ",
            "8 (812) 426-34-78", "https://vk.com/ctrlplayru_spb",
            "круглосуточно", 0, "60.007866, 30.327241", listOf(R.drawable.ctrlplay0_0, R.drawable.ctrlplay0_1).toString()
        )

        values = addComputerCLub(
            2, "CTRL PLAY", "Приморский просп., 97, Санкт-Петербург, 197374, Burger King, 2й этаж",
            "8 (812) 389-34-78", "https://vk.com/ctrlplayru_spb",
            "круглосуточно", 0, "59.984548, 30.237849", ""
        )


        values = addComputerCLub(
            3, "Portal", "16-я линия В.О., дом 43, Санкт-Петербург, 199178",
            "8 (812) 900-95-97", "https://portalclubspb.ru/",
            "09:00-08:00", 0, "59.940019, 30.265670", ""
        )


        values = addComputerCLub(
            4, "Стелс", "пр. Большевиков, 3, корп. 1, литера \"Д\", Санкт-Петербург, 193231",
            "8 (812) 588-40-61", "http://stels-klub.ru/",
            "круглосуточно", 0, "59.917966, 30.470055", ""
        )


        values = addComputerCLub(
            5, "Фобос", "Будапештская ул., 19/1, Санкт-Петербург, 192212",
            "8 (812) 774-31-47", "http://fobos.pro/",
            "10:00-08:00", 0, "59.864250, 30.372669", ""
        )


        values = addComputerCLub(
            6, "Cyberside", "Варшавская ул., 29, к.3, Санкт-Петербург, 196191",
            "8 (812) 921-13-66", "http://cyberside.pro/",
            "09:00-08:00", 0, "59.862614, 30.311709", ""
        )


        values = addComputerCLub(
            7, "Пять и Три", "Большой проспект ПС, 35, Санкт-Петербург, 197198",
            "8 (812) 670-04-53", "hhttps://www.fiveandthree.ru/",
            "Пн-Сб 09:30-23:00", 0, "59.959287, 30.301971", ""
        )


        values = addComputerCLub(
            8, "ЦЕНТР КИБЕРСПОРТА", "Г. САНКТ-ПЕТЕРБУРГ, КРЕМЕНЧУГСКАЯ УЛ. Д. 11К1",
            "8 (929) 101-59-86", "http://cybcentr.ru/sankt-peterburg",
            "круглосуточно", 0, "59.922878, 30.371222", ""
        )


        values = addComputerCLub(
            9, "Restart", "Большая Зеленина ул., 1, Санкт-Петербург, 197110",
            "8 (812) 235-17-79", "https://vk.com/ccrestart",
            "круглосуточно", 0, "59.959588, 30.295863", ""
        )


        values = addComputerCLub(
            10, "Вега", "Промышленная ул., 6, Санкт-Петербург, 198095",
            "8 (921) 941-34-18", "https://vega98.ru/",
            "круглосуточно", 0, "59.898774, 30.272929", ""
        )


        values = addComputerCLub(
            11, "Ladoga", "2-я Красноармейская ул., 2, Санкт-Петербург, 190005",
            "8 (812) 316-37-50", "https://vk.com/ladogaclubspb",
            "круглосуточно", 0, "59.915183, 30.317431", ""
        )


        values = addComputerCLub(
            12, "Pandagreen", "Гжатская ул., 22 корпус 3, Санкт-Петербург, 195220",
            "8 (911) 027-64-57", "https://vk.com/pandagreengameclub",
            "круглосуточно", 0, "60.012543, 30.387635", ""
        )


        values = addComputerCLub(
            13, "Baza", "Торфяная дорога, 7В",
            "8 (812) 995-49-95", "https://gg-baza.ru/",
            "круглосуточно", 0, "59.989360, 30.257774", ""
        )


        values = addComputerCLub(
            14, "Colizeum Komenda", "г. Санкт-Петербург, проспект Испытателей, д. 39 , литера А, ТРЦ «Миллер», 1 этаж ",
            "8 (812) 318-40-42", "https://colizeumarena.com/club/colizeum_komenda",
            "круглосуточно", 0, "60.007920, 30.264269", ""
        )

        values = addComputerCLub(
            15, "Colizeum Industrial", "г. Санкт-Петербург, Индустриальный проспект, 24, литера А, ТК Июнь",
            "8 (812) 389-34-78", "https://colizeumarena.com/club/colizeum_industrial",
            "скоро открытие!", 0, "59.946202, 30.474448", ""
        )


        values = addComputerCLub(
            16, "WINSTRIKE Corner", "ТРК Мега Дыбенко, Мурманское шоссе 12 км, 1 Кудрово, Всеволожский",
            "8 (800) 444-13–22", "https://piter.winstrike.gg/",
            "10:00-23:00", 0, "59.893716, 30.515164", ""
        )


        values = addComputerCLub(
            17, "Киберспортивный центр", "15-я линия В.О., 76 МО №8 \"Васильевский\", Василеостровский район, Санкт-Петербург",
            "8 (911) 762-96-31", "https://vk.com/club176866377",
            "круглосуточно", 0, "59.935561, 30.273405", ""
        )


        values = addComputerCLub(
            18, "PandaBanda", "Кораблестроителей, 30/1 МО №11 \"Остров Декабристов\", Василеостровский район, Санкт-Петербург",
            "8 (800) 302-56-66", "http://pandabanda.club/",
            "круглосуточно", 0, "59.943395, 30.216029", ""
        )


        values = addComputerCLub(
            19,
            "PandaBanda", "Гжатская, 22 к3 МО №19 \"Академическое\", Калининский район, Санкт-Петербург",
            "8 (800) 302-56-66", "http://pandabanda.club/",
            "круглосуточно", 0, "60.004870, 30.385685", ""
        )


        values = addComputerCLub(
            20,
            "Кибер World", "Санкт-Петербург, Байконурская 14 ТЦ «КОНТИНЕНТ», второй этаж",
            "8 (904) 511‒77‒11", "http://kiberworld.ru/",
            "круглосуточно", 0, "60.002169, 30.272800", ""
        )


        values = addComputerCLub(
            21,
            "Headshot", "Виленский переулок, 7 МО №80 \"Смольнинское\", Центральный район, Санкт-Петербург",
            "8 (911) 757-92-44", "https://www.headshot.spb.ru/",
            "круглосуточно", 0, "59.940114, 30.365653", ""
        )


        values = addComputerCLub(
            22, "Теккен Арена", "ТК ПИТЕР, Типанова, 21 лит А МО №45 \"Гагаринское\", Московский район, Санкт-Петербург",
            "8 (953) 164–99–04", "https://www.tekken.ru/reservation/spb",
            "круглосуточно", 1, "59.853077, 30.340575", ""
        )


        values = addComputerCLub(
            23,
            "CyberPoint", "пл. Чернышевского, 6, Санкт-Петербург",
            "8 (812) 425-36-65", "https://cyberpointspb.ru/",
            "круглосуточно", 1, "59.868478, 30.317395", ""
        )

    }

    private fun addComputerCLub(
        tempId: Int,
        tempName: String,
        tempAddress: String,
        tempPhone: String,
        tempSite: String,
        tempHours: String,
        tempIsAvailable: Int,
        tempCoordinates: String,
        tempImages: String
    ): ContentValues {
        var values = ContentValues()
        values.put(_id, tempId)
        values.put(name, tempName)
        values.put(address, tempAddress)
        values.put(phone, tempPhone)
        values.put(site, tempSite)
        values.put(hours, tempHours)
        values.put(isAvailableOnlineBooking, tempIsAvailable)
        values.put(coordinates, tempCoordinates)
        values.put(images, tempImages)
        this.addComputerClub(values)
        return values
    }

}

