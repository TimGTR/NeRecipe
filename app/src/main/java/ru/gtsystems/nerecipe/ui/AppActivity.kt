package ru.gtsystems.nerecipe.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.snackbar.Snackbar
import ru.gtsystems.nerecipe.R
import ru.gtsystems.nerecipe.databinding.ActivityAppBinding


class AppActivity : AppCompatActivity()
//{

//    private lateinit var binding: ActivityAppBinding
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        val binding = ActivityAppBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        handleIntent(intent)
//
//    }
//
//
//    override fun onNewIntent(intent: Intent?) {
//        super.onNewIntent(intent)
//        handleIntent(intent)
//    }
//
//    private fun handleIntent(intent: Intent?) {
//        intent ?: return
//        if (intent.action != Intent.ACTION_SEND) return
//
//        val text = intent.getStringExtra(Intent.EXTRA_TEXT)
//        if (text.isNullOrBlank()) {
//            Snackbar.make(binding.root, "Null text", Snackbar.LENGTH_INDEFINITE)
//                .setAction(android.R.string.ok) { finish() }
//                .show()
//        } else {
//
//            val fragment =
//                supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
//            fragment.navController
//                .navigate(R.id.action_favoriteFragment_to_feedFragment, Bundle().apply {
//                    //textArg = text
//                })
//
//        }
//
//    }
//}