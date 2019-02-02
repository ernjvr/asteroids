package com.ernjvr.asteroids

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.ernjvr.asteroids.data.service.DataService
import com.ernjvr.asteroids.model.Instruction
import kotlinx.android.synthetic.main.activity_instructions.*

class InstructionsActivity : AppCompatActivity() {

    private lateinit var adapter: ArrayAdapter<Instruction>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instructions)

        adapter = ArrayAdapter(this, R.layout.list_text_view, DataService.instructions)
        lv_instructions.adapter = adapter
    }
}