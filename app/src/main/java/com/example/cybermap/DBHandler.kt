package com.example.cybermap

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.os.Debug
import android.util.Log

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

    fun addAllComputerClubs() {
        var tempId = 0
        var tempName = "Gamer"
        var tempAddress = "ул. Есенина, 9к2, Санкт-Петербург, 194354"
        var tempPhone = "8 (812) 599-39-53"
        var tempSite = ""
        var tempHours = "круглосуточно"
        var tempIsAvailable = 0
        var tempCoordinates = "60.040150, 30.334697"

        var values = ContentValues()
        values.put(DBHandler._id, tempId)
        values.put(DBHandler.name, tempName)
        values.put(DBHandler.address, tempAddress)
        values.put(DBHandler.phone, tempPhone)
        values.put(DBHandler.site, tempSite)
        values.put(DBHandler.hours, tempHours)
        values.put(DBHandler.isAvailableOnlineBooking, tempIsAvailable)
        values.put(DBHandler.coordinates, tempCoordinates)
        this.addComputerClub(values)

        tempId = 1
        tempName = "CTRL PLAY"
        tempAddress = "пр. Энгельса, 27д, Санкт-Петербург, 194292, Burger King, 1й этаж "
        tempPhone = "8 (812) 426-34-78"
        tempSite = "http://ctrlplay.ru/"
        tempHours = "круглосуточно"
        tempIsAvailable = 0
        tempCoordinates = "60.007866, 30.327241"

        values = ContentValues()
        values.put(DBHandler._id, tempId)
        values.put(DBHandler.name, tempName)
        values.put(DBHandler.address, tempAddress)
        values.put(DBHandler.phone, tempPhone)
        values.put(DBHandler.site, tempSite)
        values.put(DBHandler.hours, tempHours)
        values.put(DBHandler.isAvailableOnlineBooking, tempIsAvailable)
        values.put(DBHandler.coordinates, tempCoordinates)
        this.addComputerClub(values)

        tempId = 2
        tempName = "CTRL PLAY"
        tempAddress = "Приморский просп., 97, Санкт-Петербург, 197374, Burger King, 2й этаж"
        tempPhone = "8 (812) 389-34-78"
        tempSite = "http://ctrlplay.ru/"
        tempHours = "круглосуточно"
        tempIsAvailable = 0
        tempCoordinates = "59.984548, 30.237849"

        values = ContentValues()
        values.put(DBHandler._id, tempId)
        values.put(DBHandler.name, tempName)
        values.put(DBHandler.address, tempAddress)
        values.put(DBHandler.phone, tempPhone)
        values.put(DBHandler.site, tempSite)
        values.put(DBHandler.hours, tempHours)
        values.put(DBHandler.isAvailableOnlineBooking, tempIsAvailable)
        values.put(DBHandler.coordinates, tempCoordinates)
        this.addComputerClub(values)

        tempId = 3
        tempName = "Portal"
        tempAddress = "16-я линия В.О., дом 43, Санкт-Петербург, 199178"
        tempPhone = "8 (812) 900-95-97"
        tempSite = "https://portalclubspb.ru/"
        tempHours = "09:00-08:00"
        tempIsAvailable = 0
        tempCoordinates = "59.940019, 30.265670"

        values = ContentValues()
        values.put(DBHandler._id, tempId)
        values.put(DBHandler.name, tempName)
        values.put(DBHandler.address, tempAddress)
        values.put(DBHandler.phone, tempPhone)
        values.put(DBHandler.site, tempSite)
        values.put(DBHandler.hours, tempHours)
        values.put(DBHandler.isAvailableOnlineBooking, tempIsAvailable)
        values.put(DBHandler.coordinates, tempCoordinates)
        this.addComputerClub(values)

        tempId = 4
        tempName = "Стелс"
        tempAddress = "пр. Большевиков, 3, корп. 1, литера \"Д\", Санкт-Петербург, 193231"
        tempPhone = "8 (812) 588-40-61"
        tempSite = "http://stels-klub.ru/"
        tempHours = "круглосуточно"
        tempIsAvailable = 0
        tempCoordinates = "59.917966, 30.470055"

        values = ContentValues()
        values.put(DBHandler._id, tempId)
        values.put(DBHandler.name, tempName)
        values.put(DBHandler.address, tempAddress)
        values.put(DBHandler.phone, tempPhone)
        values.put(DBHandler.site, tempSite)
        values.put(DBHandler.hours, tempHours)
        values.put(DBHandler.isAvailableOnlineBooking, tempIsAvailable)
        values.put(DBHandler.coordinates, tempCoordinates)
        this.addComputerClub(values)

        tempId = 5
        tempName = "Фобос"
        tempAddress = "Будапештская ул., 19/1, Санкт-Петербург, 192212"
        tempPhone = "8 (812) 774-31-47"
        tempSite = "http://fobos.pro/"
        tempHours = "10:00-08:00"
        tempIsAvailable = 0
        tempCoordinates = "59.864250, 30.372669"

        values = ContentValues()
        values.put(DBHandler._id, tempId)
        values.put(DBHandler.name, tempName)
        values.put(DBHandler.address, tempAddress)
        values.put(DBHandler.phone, tempPhone)
        values.put(DBHandler.site, tempSite)
        values.put(DBHandler.hours, tempHours)
        values.put(DBHandler.isAvailableOnlineBooking, tempIsAvailable)
        values.put(DBHandler.coordinates, tempCoordinates)
        this.addComputerClub(values)

        tempId = 6
        tempName = "Cyberside"
        tempAddress = "Варшавская ул., 29, к.3, Санкт-Петербург, 196191"
        tempPhone = "8 (812) 921-13-66"
        tempSite = "http://cyberside.pro/"
        tempHours = "09:00-08:00"
        tempIsAvailable = 0
        tempCoordinates = "59.862614, 30.311709"

        values = ContentValues()
        values.put(DBHandler._id, tempId)
        values.put(DBHandler.name, tempName)
        values.put(DBHandler.address, tempAddress)
        values.put(DBHandler.phone, tempPhone)
        values.put(DBHandler.site, tempSite)
        values.put(DBHandler.hours, tempHours)
        values.put(DBHandler.isAvailableOnlineBooking, tempIsAvailable)
        values.put(DBHandler.coordinates, tempCoordinates)
        this.addComputerClub(values)

        tempId = 7
        tempName = "Пять и Три"
        tempAddress = "Большой проспект ПС, 35, Санкт-Петербург, 197198"
        tempPhone = "8 (812) 670-04-53"
        tempSite = "hhttps://www.fiveandthree.ru/"
        tempHours = "Пн-Сб 09:30-23:00"
        tempIsAvailable = 0
        tempCoordinates = "59.959287, 30.301971"

        values = ContentValues()
        values.put(DBHandler._id, tempId)
        values.put(DBHandler.name, tempName)
        values.put(DBHandler.address, tempAddress)
        values.put(DBHandler.phone, tempPhone)
        values.put(DBHandler.site, tempSite)
        values.put(DBHandler.hours, tempHours)
        values.put(DBHandler.isAvailableOnlineBooking, tempIsAvailable)
        values.put(DBHandler.coordinates, tempCoordinates)
        this.addComputerClub(values)

        tempId = 8
        tempName = "ЦЕНТР КИБЕРСПОРТА"
        tempAddress = "Г. САНКТ-ПЕТЕРБУРГ, КРЕМЕНЧУГСКАЯ УЛ. Д. 11К1"
        tempPhone = "8 (929) 101-59-86"
        tempSite = "http://cybcentr.ru/sankt-peterburg"
        tempHours = "круглосуточно"
        tempIsAvailable = 0
        tempCoordinates = "59.922878, 30.371222"

        values = ContentValues()
        values.put(DBHandler._id, tempId)
        values.put(DBHandler.name, tempName)
        values.put(DBHandler.address, tempAddress)
        values.put(DBHandler.phone, tempPhone)
        values.put(DBHandler.site, tempSite)
        values.put(DBHandler.hours, tempHours)
        values.put(DBHandler.isAvailableOnlineBooking, tempIsAvailable)
        values.put(DBHandler.coordinates, tempCoordinates)
        this.addComputerClub(values)

        tempId = 9
        tempName = "Restart"
        tempAddress = "Большая Зеленина ул., 1, Санкт-Петербург, 197110"
        tempPhone = "8 (812) 235-17-79"
        tempSite = "https://vk.com/ccrestart"
        tempHours = "круглосуточно"
        tempIsAvailable = 0
        tempCoordinates = "59.959588, 30.295863"

        values = ContentValues()
        values.put(DBHandler._id, tempId)
        values.put(DBHandler.name, tempName)
        values.put(DBHandler.address, tempAddress)
        values.put(DBHandler.phone, tempPhone)
        values.put(DBHandler.site, tempSite)
        values.put(DBHandler.hours, tempHours)
        values.put(DBHandler.isAvailableOnlineBooking, tempIsAvailable)
        values.put(DBHandler.coordinates, tempCoordinates)
        this.addComputerClub(values)

        tempId = 10
        tempName = "Вега"
        tempAddress = "Промышленная ул., 6, Санкт-Петербург, 198095"
        tempPhone = "8 (921) 941-34-18"
        tempSite = "https://vega98.ru/"
        tempHours = "круглосуточно"
        tempIsAvailable = 0
        tempCoordinates = "59.898774, 30.272929"

        values = ContentValues()
        values.put(DBHandler._id, tempId)
        values.put(DBHandler.name, tempName)
        values.put(DBHandler.address, tempAddress)
        values.put(DBHandler.phone, tempPhone)
        values.put(DBHandler.site, tempSite)
        values.put(DBHandler.hours, tempHours)
        values.put(DBHandler.isAvailableOnlineBooking, tempIsAvailable)
        values.put(DBHandler.coordinates, tempCoordinates)
        this.addComputerClub(values)

        tempId = 11
        tempName = "Ladoga"
        tempAddress = "2-я Красноармейская ул., 2, Санкт-Петербург, 190005"
        tempPhone = "8 (812) 316-37-50"
        tempSite = "https://vk.com/ladogaclubspb"
        tempHours = "круглосуточно"
        tempIsAvailable = 0
        tempCoordinates = "59.915183, 30.317431"

        values = ContentValues()
        values.put(DBHandler._id, tempId)
        values.put(DBHandler.name, tempName)
        values.put(DBHandler.address, tempAddress)
        values.put(DBHandler.phone, tempPhone)
        values.put(DBHandler.site, tempSite)
        values.put(DBHandler.hours, tempHours)
        values.put(DBHandler.isAvailableOnlineBooking, tempIsAvailable)
        values.put(DBHandler.coordinates, tempCoordinates)
        this.addComputerClub(values)

        tempId = 12
        tempName = "Pandagreen"
        tempAddress = "Гжатская ул., 22 корпус 3, Санкт-Петербург, 195220"
        tempPhone = "8 (911) 027-64-57"
        tempSite = "https://vk.com/pandagreengameclub"
        tempHours = "круглосуточно"
        tempIsAvailable = 0
        tempCoordinates = "60.012543, 30.387635"

        values = ContentValues()
        values.put(DBHandler._id, tempId)
        values.put(DBHandler.name, tempName)
        values.put(DBHandler.address, tempAddress)
        values.put(DBHandler.phone, tempPhone)
        values.put(DBHandler.site, tempSite)
        values.put(DBHandler.hours, tempHours)
        values.put(DBHandler.isAvailableOnlineBooking, tempIsAvailable)
        values.put(DBHandler.coordinates, tempCoordinates)
        this.addComputerClub(values)

        tempId = 13
        tempName = "Baza"
        tempAddress = "Торфяная дорога, 7В"
        tempPhone = "8 (812) 995-49-95"
        tempSite = "https://gg-baza.ru/"
        tempHours = "круглосуточно"
        tempIsAvailable = 0
        tempCoordinates = "59.989360, 30.257774"

        values = ContentValues()
        values.put(DBHandler._id, tempId)
        values.put(DBHandler.name, tempName)
        values.put(DBHandler.address, tempAddress)
        values.put(DBHandler.phone, tempPhone)
        values.put(DBHandler.site, tempSite)
        values.put(DBHandler.hours, tempHours)
        values.put(DBHandler.isAvailableOnlineBooking, tempIsAvailable)
        values.put(DBHandler.coordinates, tempCoordinates)
        this.addComputerClub(values)

        tempId = 14
        tempName = "Colizeum Komenda"
        tempAddress = "г. Санкт-Петербург, проспект Испытателей, д. 39 , литера А, ТРЦ «Миллер», 1 этаж "
        tempPhone = "8 (812) 318-40-42"
        tempSite = "https://colizeumarena.com/club/colizeum_komenda"
        tempHours = "круглосуточно"
        tempIsAvailable = 0
        tempCoordinates = "60.007920, 30.264269"

        values = ContentValues()
        values.put(DBHandler._id, tempId)
        values.put(DBHandler.name, tempName)
        values.put(DBHandler.address, tempAddress)
        values.put(DBHandler.phone, tempPhone)
        values.put(DBHandler.site, tempSite)
        values.put(DBHandler.hours, tempHours)
        values.put(DBHandler.isAvailableOnlineBooking, tempIsAvailable)
        values.put(DBHandler.coordinates, tempCoordinates)
        this.addComputerClub(values)

        tempId = 15
        tempName = "Colizeum Industrial"
        tempAddress = "г. Санкт-Петербург, Индустриальный проспект, 24, литера А, ТК Июнь"
        tempPhone = "8 (812) 389-34-78"
        tempSite = "https://colizeumarena.com/club/colizeum_industrial"
        tempHours = "скоро открытие!"
        tempIsAvailable = 0
        tempCoordinates = "59.946202, 30.474448"

        values = ContentValues()
        values.put(DBHandler._id, tempId)
        values.put(DBHandler.name, tempName)
        values.put(DBHandler.address, tempAddress)
        values.put(DBHandler.phone, tempPhone)
        values.put(DBHandler.site, tempSite)
        values.put(DBHandler.hours, tempHours)
        values.put(DBHandler.isAvailableOnlineBooking, tempIsAvailable)
        values.put(DBHandler.coordinates, tempCoordinates)
        this.addComputerClub(values)

        tempId = 16
        tempName = "WINSTRIKE Corner"
        tempAddress = "ТРК Мега Дыбенко, Мурманское шоссе 12 км, 1 Кудрово, Всеволожский"
        tempPhone = "8 (800) 444-13–22"
        tempSite = "https://piter.winstrike.gg/"
        tempHours = "10:00-23:00"
        tempIsAvailable = 0
        tempCoordinates = "59.893716, 30.515164"

        values = ContentValues()
        values.put(DBHandler._id, tempId)
        values.put(DBHandler.name, tempName)
        values.put(DBHandler.address, tempAddress)
        values.put(DBHandler.phone, tempPhone)
        values.put(DBHandler.site, tempSite)
        values.put(DBHandler.hours, tempHours)
        values.put(DBHandler.isAvailableOnlineBooking, tempIsAvailable)
        values.put(DBHandler.coordinates, tempCoordinates)
        this.addComputerClub(values)

        tempId = 17
        tempName = "Киберспортивный центр"
        tempAddress = "15-я линия В.О., 76 МО №8 \"Васильевский\", Василеостровский район, Санкт-Петербург"
        tempPhone = "8 (911) 762-96-31"
        tempSite = "https://vk.com/club176866377"
        tempHours = "круглосуточно"
        tempIsAvailable = 0
        tempCoordinates = "59.935561, 30.273405"

        values = ContentValues()
        values.put(DBHandler._id, tempId)
        values.put(DBHandler.name, tempName)
        values.put(DBHandler.address, tempAddress)
        values.put(DBHandler.phone, tempPhone)
        values.put(DBHandler.site, tempSite)
        values.put(DBHandler.hours, tempHours)
        values.put(DBHandler.isAvailableOnlineBooking, tempIsAvailable)
        values.put(DBHandler.coordinates, tempCoordinates)
        this.addComputerClub(values)

        tempId = 18
        tempName = "PandaBanda"
        tempAddress = "Кораблестроителей, 30/1 МО №11 \"Остров Декабристов\", Василеостровский район, Санкт-Петербург"
        tempPhone = "8 (800) 302-56-66"
        tempSite = "http://pandabanda.club/"
        tempHours = "круглосуточно"
        tempIsAvailable = 0
        tempCoordinates = "59.943395, 30.216029"

        values = ContentValues()
        values.put(DBHandler._id, tempId)
        values.put(DBHandler.name, tempName)
        values.put(DBHandler.address, tempAddress)
        values.put(DBHandler.phone, tempPhone)
        values.put(DBHandler.site, tempSite)
        values.put(DBHandler.hours, tempHours)
        values.put(DBHandler.isAvailableOnlineBooking, tempIsAvailable)
        values.put(DBHandler.coordinates, tempCoordinates)
        this.addComputerClub(values)

        tempId = 19
        tempName = "PandaBanda"
        tempAddress = "Гжатская, 22 к3 МО №19 \"Академическое\", Калининский район, Санкт-Петербург"
        tempPhone = "8 (800) 302-56-66"
        tempSite = "http://pandabanda.club/"
        tempHours = "круглосуточно"
        tempIsAvailable = 0
        tempCoordinates = "60.004870, 30.385685"

        values = ContentValues()
        values.put(DBHandler._id, tempId)
        values.put(DBHandler.name, tempName)
        values.put(DBHandler.address, tempAddress)
        values.put(DBHandler.phone, tempPhone)
        values.put(DBHandler.site, tempSite)
        values.put(DBHandler.hours, tempHours)
        values.put(DBHandler.isAvailableOnlineBooking, tempIsAvailable)
        values.put(DBHandler.coordinates, tempCoordinates)
        this.addComputerClub(values)

        tempId = 20
        tempName = "Кибер World"
        tempAddress = "Санкт-Петербург, Байконурская 14 ТЦ «КОНТИНЕНТ», второй этаж"
        tempPhone = "8 (904) 511‒77‒11"
        tempSite = "http://kiberworld.ru/"
        tempHours = "круглосуточно"
        tempIsAvailable = 0
        tempCoordinates = "60.002169, 30.272800"

        values = ContentValues()
        values.put(DBHandler._id, tempId)
        values.put(DBHandler.name, tempName)
        values.put(DBHandler.address, tempAddress)
        values.put(DBHandler.phone, tempPhone)
        values.put(DBHandler.site, tempSite)
        values.put(DBHandler.hours, tempHours)
        values.put(DBHandler.isAvailableOnlineBooking, tempIsAvailable)
        values.put(DBHandler.coordinates, tempCoordinates)
        this.addComputerClub(values)

        tempId = 21
        tempName = "Headshot"
        tempAddress = "Виленский переулок, 7 МО №80 \"Смольнинское\", Центральный район, Санкт-Петербург"
        tempPhone = "8 (911) 757-92-44"
        tempSite = "https://www.headshot.spb.ru/"
        tempHours = "круглосуточно"
        tempIsAvailable = 0
        tempCoordinates = "59.940114, 30.365653"

        values = ContentValues()
        values.put(DBHandler._id, tempId)
        values.put(DBHandler.name, tempName)
        values.put(DBHandler.address, tempAddress)
        values.put(DBHandler.phone, tempPhone)
        values.put(DBHandler.site, tempSite)
        values.put(DBHandler.hours, tempHours)
        values.put(DBHandler.isAvailableOnlineBooking, tempIsAvailable)
        values.put(DBHandler.coordinates, tempCoordinates)
        this.addComputerClub(values)

        tempId = 22
        tempName = "Теккен Арена"
        tempAddress = "ТК ПИТЕР, Типанова, 21 лит А МО №45 \"Гагаринское\", Московский район, Санкт-Петербург"
        tempPhone = "8 (953) 164–99–04"
        tempSite = "https://www.tekken.ru/reservation/spb"
        tempHours = "круглосуточно"
        tempIsAvailable = 1
        tempCoordinates = "59.853077, 30.340575"

        values = ContentValues()
        values.put(DBHandler._id, tempId)
        values.put(DBHandler.name, tempName)
        values.put(DBHandler.address, tempAddress)
        values.put(DBHandler.phone, tempPhone)
        values.put(DBHandler.site, tempSite)
        values.put(DBHandler.hours, tempHours)
        values.put(DBHandler.isAvailableOnlineBooking, tempIsAvailable)
        values.put(DBHandler.coordinates, tempCoordinates)
        this.addComputerClub(values)

        tempId = 23
        tempName = "CyberPoint"
        tempAddress = "пл. Чернышевского, 6, Санкт-Петербург "
        tempPhone = "8 (812) 425-36-65"
        tempSite = "https://cyberpointspb.ru/"
        tempHours = "круглосуточно"
        tempIsAvailable = 1
        tempCoordinates = "59.868478, 30.317395"

        values = ContentValues()
        values.put(DBHandler._id, tempId)
        values.put(DBHandler.name, tempName)
        values.put(DBHandler.address, tempAddress)
        values.put(DBHandler.phone, tempPhone)
        values.put(DBHandler.site, tempSite)
        values.put(DBHandler.hours, tempHours)
        values.put(DBHandler.isAvailableOnlineBooking, tempIsAvailable)
        values.put(DBHandler.coordinates, tempCoordinates)
        this.addComputerClub(values)

    }

}

