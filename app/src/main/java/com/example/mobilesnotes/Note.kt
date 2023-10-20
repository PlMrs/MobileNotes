package com.example.mobilesnotes

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class Note(con: Context, private val note: String, private val position: Int) : Dialog(con) {
    private lateinit var editText: EditText
    private lateinit var saveButton: Button
    private lateinit var deleteButton: Button
    private val context = con

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_note)

        editText = findViewById(R.id.editTextEditedNote)
        saveButton = findViewById(R.id.buttonSaveNote)
        deleteButton = findViewById(R.id.buttonDeleteNote)

        editText.setText(note)

        saveButton.setOnClickListener {
            val editedNote = editText.text.toString()
            println(context)
            if (editedNote.isNotBlank()) {
                if (context is MainActivity) {
                    (context as MainActivity).updateNoteAtPosition(position, editedNote)
                }
                println("wadade")
                dismiss()
            }
        }
        deleteButton.setOnClickListener {
                if (context is MainActivity) {
                    (context as MainActivity).removeNote(position)
                }
                dismiss()
        }
    }
}
