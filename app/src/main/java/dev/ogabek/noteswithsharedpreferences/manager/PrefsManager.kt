package dev.ogabek.noteswithsharedpreferences.manager

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dev.ogabek.noteswithsharedpreferences.model.Note
import java.lang.reflect.Type
import java.util.ArrayList

class PrefsManager(context: Context) {

    private val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    companion object {
        var prefsManager: PrefsManager? = null
        fun getInstance(context: Context): PrefsManager? {
            if (prefsManager == null) {
                prefsManager = PrefsManager(context)
            }
            return prefsManager
        }
    }

    private fun saveData(key: String, value: ArrayList<Note>) {
        val prefsEditor = sharedPreferences!!.edit()
        prefsEditor.putString(key, Gson().toJson(value))
        prefsEditor.apply()
    }

    fun saveData(key: String, value: Note) {
        val notes = getData(key)
        notes.add(value)
        saveData(key, notes)
    }

    fun getData(key: String): ArrayList<Note> {
        if (sharedPreferences.contains(key)) {
            val type: Type = object : TypeToken<ArrayList<Note>>() {}.type
            return Gson().fromJson(sharedPreferences.getString(key, ""), type)
        }
        return ArrayList()
    }

    fun deleteData() {
        val prefsEditor = sharedPreferences!!.edit()
        prefsEditor.clear()
        prefsEditor.apply()
    }

}