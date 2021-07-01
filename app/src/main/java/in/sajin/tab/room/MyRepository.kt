package `in`.sajin.tab.room

import androidx.lifecycle.LiveData

class MyRepository(private val myDao: MyDao) {

    val readPerson: LiveData<List<Person>> = myDao.readPerson()

    suspend fun insertPerson(person: Person){
        myDao.insertPerson(person)
    }

}