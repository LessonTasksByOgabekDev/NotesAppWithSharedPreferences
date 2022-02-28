package dev.ogabek.noteswithsharedpreferences.activity

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dev.ogabek.noteswithsharedpreferences.R
import dev.ogabek.noteswithsharedpreferences.adapter.NoteAdapter
import dev.ogabek.noteswithsharedpreferences.manager.PrefsManager
import dev.ogabek.noteswithsharedpreferences.model.Note

class MainActivity : AppCompatActivity() {

    private lateinit var add: FloatingActionButton
    private lateinit var notes: RecyclerView

    private val TAG = MainActivity::class.java.simpleName

    private var noteList: ArrayList<Note> = ArrayList()

    val adapter = NoteAdapter(this, noteList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        PrefsManager(this).deleteData()

        noteList.addAll(PrefsManager.getInstance(this)!!.getData("post"))

        initViews()

    }

    private fun initViews() {

        add = findViewById(R.id.btn_add)
        notes = findViewById(R.id.rv_notes)

        add.setOnClickListener {
            val view = layoutInflater.inflate(R.layout.custom_dialog, null)
            val alertDialog = AlertDialog.Builder(this).create()
            alertDialog.setTitle("New Note")
            alertDialog.setCancelable(true)


            val text: EditText = view.findViewById(R.id.etComments)

            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Save") { _, _ ->
                val manager = PrefsManager.getInstance(this)!!
                val note = Note(manager.getData("post").size, text.text.toString())
                noteList.add(note)
                adapter.notifyDataSetChanged()
                manager.saveData("post", note)
            }

            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel") { _, _ ->
                alertDialog.cancel()
            }

            alertDialog.setView(view)
            alertDialog.show()
        }

        notes.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        notes.adapter = adapter
    }
}