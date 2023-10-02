package ru.kpfu.itis.gimaletdinova.alculatorprobabilitytheory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.kpfu.itis.gimaletdinova.alculatorprobabilitytheory.databinding.ActivityMainBinding

// TODO: library instead of binding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
    }
}