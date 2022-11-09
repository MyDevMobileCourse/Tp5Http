package com.example.tp5

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import com.example.tp5.api.Offre
import com.example.tp5.api.RestApiService
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout

class Form : AppCompatActivity() {
    lateinit var autocomplete: AutoCompleteTextView
    lateinit var intitue: TextInputLayout
    lateinit var specialité: TextInputLayout
    lateinit var société: TextInputLayout
    lateinit var pays: TextInputLayout
    lateinit var nbpostes: TextInputLayout
    lateinit var submit: Button
    lateinit var cancel: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)
        init()
    }

    fun init() {
        autocomplete = findViewById(R.id.autocomplete)
        val languages = resources.getStringArray(R.array.countries)
        val adapter = ArrayAdapter(applicationContext, R.layout.class_item, languages)
        autocomplete.setAdapter(adapter)

        submit = findViewById(R.id.submit)
        cancel = findViewById(R.id.cancel)

        initInputs()
        initButtons()
    }

    fun initInputs() {

        intitue = findViewById(R.id.intitue)
        specialité = findViewById(R.id.specialité)
        société = findViewById(R.id.société)
        pays = findViewById(R.id.pays)
        nbpostes = findViewById(R.id.nbpostes)

        arrayListOf(intitue, specialité, société, pays, nbpostes).forEach {
            it.editText?.setOnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {
                    validate(it)
                }
            }
        }

    }

    fun initButtons() {
        submit.setOnClickListener {
            if (validateAll()) {
                submit()
            } else {
                submit.isEnabled = false
            }
        }
        cancel.setOnClickListener {
            if (atLeastOneNotEmpty()) {
                clearAll()
            } else {
                finish()
            }
        }
    }

    fun validateAll(): Boolean {
        var valid = true
        arrayListOf(intitue, specialité, société, pays, nbpostes).forEach {
            if (!validate(it)) {
                valid = false
            }
        }
        return valid
    }

    fun atLeastOneNotEmpty(): Boolean {
        var valid = false
        arrayListOf(intitue, specialité, société, pays, nbpostes).forEach {
            if (it.editText?.text.toString().isNotEmpty()) {
                valid = true
            }
        }
        return valid
    }

    fun clearAll() {
        arrayListOf(intitue, specialité, société, pays, nbpostes).forEach {
            it.editText?.setText("")
        }
    }


    fun validate(it: TextInputLayout): Boolean {
        if (it.editText?.text.toString().isEmpty()) {
            it.error = "Champ obligatoire"
            return false
        } else {
            it.error = null
        }
        if (it != pays && it != nbpostes && it.editText?.text.toString().length < 3) {
            it.error = "Champ doit contenir au moins 3 caractères"
            return false
        } else {
            it.error = null
        }
        submit.isEnabled = true
        return true

    }

    fun submit() {
        val intitulé = intitue.editText?.text.toString()
        val specialité = specialité.editText?.text.toString()
        val societe = société.editText?.text.toString()
        val pays = pays.editText?.text.toString()
        val nbpostes = nbpostes.editText?.text.toString().toInt()
        val offre = Offre(
            code = 0,
            intitulé = intitulé,
            specialité = specialité,
            société = societe,
            nbpostes = nbpostes,
            pays = pays
        )
        val apiService = RestApiService()
        apiService.addOffre(offre) {
            if (it != null) {
                finish()
            } else {
                val snackbar = Snackbar.make(
                    findViewById(R.id.form),
                    "Erreur lors de l'ajout",
                    Snackbar.LENGTH_LONG
                ).setAction(
                    "Retry"
                ) {
                    submit()
                }
                snackbar.setActionTextColor(Color.RED)
                snackbar.show()
            }
        }

    }


}