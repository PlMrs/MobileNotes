package com.example.mobilesnotes

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import android.content.Context

class MainActivity : AppCompatActivity() {
    private lateinit var noteEditText: EditText
    private lateinit var addButton: Button
    private lateinit var noteListView: ListView
    private lateinit var notes: MutableList<String>
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialisation des éléments de l'interface utilisateur
        noteEditText = findViewById(R.id.editTextNote)
        addButton = findViewById(R.id.buttonAddNote)
        noteListView = findViewById(R.id.listViewNotes)

        // Initialize the list of notes
        notes = mutableListOf()
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, notes)
        noteListView.adapter = adapter

        // Chargez les notes depuis les préférences partagées
        notes.addAll(loadNotesFromSharedPreferences())

        // Écouteur de clic sur le bouton "Ajouter"
        addButton.setOnClickListener {
            val newNote = noteEditText.text.toString()
            if (newNote.isNotBlank()) {
                // Ajouter la nouvelle note à la liste
                notes.add(newNote)
                adapter.notifyDataSetChanged()
                noteEditText.text.clear()
            }
        }

        // Écouteur de clic sur un élément de la liste pour permettre la modification
        noteListView.setOnItemClickListener { _, _, position, _ ->
            val selectedNote = notes[position]
            showEditDialog(selectedNote, position)
        }

        // Écouteur de clic long sur un élément de la liste pour la suppression
        noteListView.setOnItemLongClickListener { _, _, position, _ ->
            notes.removeAt(position)
            adapter.notifyDataSetChanged()
            true
        }
    }

    private fun showEditDialog(note: String, position: Int) {
        val dialog = Note(this, note, position)
        dialog.show()
    }

    fun updateNoteAtPosition(position: Int, updatedNote: String) {
        notes[position] = updatedNote
        adapter.notifyDataSetChanged()

        // Enregistrez les notes mises à jour dans les préférences partagées
        saveNotesToSharedPreferences()

    }

    fun removeNote(position: Int) {
        notes.removeAt(position)
        adapter.notifyDataSetChanged()

        saveNotesToSharedPreferences()
    }

    // Sauvegarder les notes en locale
    private fun saveNotesToSharedPreferences() {
        val sharedPreferences = getSharedPreferences("MyNotes", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        println(notes)
        editor.putStringSet("notes", HashSet(notes))
        editor.apply()
    }

    // Charger les notes en locales
    private fun loadNotesFromSharedPreferences(): List<String> {
        val sharedPreferences = getSharedPreferences("MyNotes", Context.MODE_PRIVATE)
        println(sharedPreferences.all)
        val noteSet = sharedPreferences.getStringSet("notes", HashSet())
        println(noteSet)
        return ArrayList(noteSet)
    }
}
