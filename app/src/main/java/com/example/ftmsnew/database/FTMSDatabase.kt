package com.example.ftmsnew.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.math.BigInteger

class FTMSDatabase(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        val createZones = "CREATE TABLE Zones (\n" +
                "   Id Integer PrimariKey Auto IncreMent,\n" +
                "    Code            VARCHAR (6)                         NOT NULL,\n" +
                "    Name            VARCHAR (100)                       NOT NULL,\n" +
                "    StateId         INT                                 NOT NULL,\n" +
                "    VillageId       INT                                 NOT NULL,\n" +
                "    IsActive        BIT                                 NOT NULL,\n" +
                "    CreatedDate     DATETIME                            NOT NULL,\n" +
                "    CreatedByUserId INT                                 NOT NULL,\n" +
                "    UpdatedDate     DATETIME                            NOT NULL,\n" +
                "    UpdatedByUserId INT                                 NOT NULL\n" +
                ");\n"
        db!!.execSQL(createZones)

        val createVillage = "CREATE TABLE Village (\n" +
                "   Id Integer PrimariKey Auto IncreMent,\n" +
                "    Code            VARCHAR (6)                         NOT NULL,\n" +
                "    Name            VARCHAR (100)                       NOT NULL,\n" +
                "    MandalId        INT                                 NOT NULL,\n" +
                "    IsActive        BIT                                 NOT NULL,\n" +
                "    CreatedDate     DATETIME                            NOT NULL,\n" +
                "    CreatedByUserId INT                                 NOT NULL,\n" +
                "    UpdatedDate     DATETIME                            NOT NULL,\n" +
                "    UpdatedByUserId INT                                 NOT NULL\n" +

                ");\n"
        db.execSQL(createVillage)

        val createStaes = "CREATE TABLE States(\n" +
                "Id Integer PrimariKey Auto IncreMent\n" +
                "Code varchar(6) NOT NULL,\n" +
                "Name varchar(100) NOT NULL,\n" +
                "IsActive bit NOT NULL,\n" +
                "CreatedDate datetime NOT NULL,\n" +
                "CreatedByUserId int NOT NULL,\n" +
                "UpdatedDate datetime NOT NULL,\n" +
                "UpdatedByUserId int NOT NULL)\n"
        db.execSQL(createStaes)

        val createResorchStation = "\n" +
                "CREATE TABLE ResearchStations(\n" +
                "Id Integer PrimariKey Auto IncreMent,\n" +
                "Code varchar(6) NOT NULL,\n" +
                "Name varchar(100) NOT NULL,\n" +
                "ZoneId int NOT NULL,\n" +
                "VillageId int NOT NULL,\n" +
                "MobileNumber bigint NOT NULL,\n" +
                "Address varchar(250) NOT NULL,\n" +
                "IsActive bit NOT NULL,\n" +
                "CreatedDate datetime NOT NULL,\n" +
                "CreatedByUserId int NOT NULL,\n" +
                "UpdatedDate datetime NOT NULL,\n" +
                "UpdatedByUserId int NOT NULL)"
        db.execSQL(createResorchStation)
        val createMandal = "CREATE TABLE Mandal(\n" +
                "Id Integer PrimariKey Auto IncreMent,\n" +
                "Code varchar(6) NOT NULL,\n" +
                "Name varchar(100) NOT NULL,\n" +
                "DistrictId int NOT NULL,\n" +
                "IsActive bit NOT NULL,\n" +
                "CreatedDate datetime NOT NULL,\n" +
                "CreatedByUserId int NOT NULL,\n" +
                "UpdatedDate datetime NOT NULL,\n" +
                "UpdatedByUserId int NOT NULL)\n"
        db.execSQL(createMandal)
        val createDistrict = "\n" +
                "CREATE TABLE District(\n" +
                "Id Integer PrimariKey Auto IncreMent,\n" +
                "Code varchar(6) NOT NULL,\n" +
                "Name varchar(100) NOT NULL,\n" +
                "ZoneId int NOT NULL,\n" +
                "IsActive bit NOT NULL,\n" +
                "CreatedDate datetime NOT NULL,\n" +
                "CreatedByUserId int NOT NULL,\n" +
                "UpdatedDate datetime NOT NULL,\n" +
                "UpdatedByUserId int NOT NULL)"
        db.execSQL(createDistrict)
        val createCrop = "CREATE TABLE Crop(\n" +
                "Id Integer PrimariKey Auto IncreMent,\n" +
                "Code varchar(6) NOT NULL,\n" +
                "Name varchar(50) NOT NULL,\n" +
                "IsActive bit NOT NULL,\n" +
                "CreatedByUserId int NOT NULL,\n" +
                "CreatedDate datetime NOT NULL,\n" +
                "UpdatedByUserId int NOT NULL,\n" +
                "UpdatedDate datetime NOT NULL)\n"
        db.execSQL(createCrop)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
    }


    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "FTMSDb"

    }
}