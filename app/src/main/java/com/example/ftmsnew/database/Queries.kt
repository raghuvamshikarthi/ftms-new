package com.example.ftmsnew.database;

class Queries {
    companion object {

        lateinit var getInstance: Any
        private var instance: Queries? = null

        fun getInstance(): Queries {
            if (instance == null) {
                instance = Queries()
            }
            return instance!!
        }
    }


    fun deleteTableData(): String {
        return "delete from %s"
    }


    fun UpgradeCount(): String {
        return "SELECT count(*) FROM Users "
    }

  }