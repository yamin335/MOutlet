package com.rtchubs.edokanpat.local_db.db

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rtchubs.edokanpat.local_db.dao.CartDao
import com.rtchubs.edokanpat.local_db.dbo.CartItem

/**
 * Main database.
 */
@Database(
    entities = [
        CartItem::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao

    companion object {

        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(app: Application): AppDatabase = INSTANCE
            ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(
                        app
                    )
                        .also { INSTANCE = it }
            }

        private fun buildDatabase(app: Application) =
            Room.databaseBuilder(app, AppDatabase::class.java, "edokanpat")
                // prepopulate the database after onCreate was called
//                .addCallback(object : Callback() {
//                    override fun onCreate(db: SupportSQLiteDatabase) {
//                        super.onCreate(db)
//                        // Do database operations through coroutine or any background thread
//                        val handler = CoroutineExceptionHandler { _, exception ->
//                            println("Caught during database creation --> $exception")
//                        }
//
//                        CoroutineScope(Dispatchers.IO).launch(handler) {
//                            prePopulateAppDatabase(getInstance(app).loginDao())
//                        }
//                    }
//                })
                .build()

//        suspend fun prePopulateAppDatabase(loginDao: LoginDao) {
//            val admin = Login(0, "Administrator", "1234", 1, isActive = true, isAdmin = true, isLogged = false)
//            loginDao.insertLoginData(admin)
//        }
    }
}