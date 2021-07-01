package `in`.sajin.tab.room

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * data model
 */
@Entity(tableName = "my_table")
data class Person(
    val firstName: String,
    val lastName: String,
    val year_of_birth: String,
    val profilePhoto: Bitmap
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}