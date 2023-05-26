package com.`as`.truthordare

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import java.util.Random

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var arrow: ImageView
    private lateinit var openMenuButton: ImageView
    private var currentPoint: Int = 0
    private var nextPoint: Int = 0
    lateinit var headingTV: TextView
    lateinit var buttonHolder: LinearLayout
    private lateinit var truth: Button
    private lateinit  var dare: Button
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        arrow = findViewById(R.id.arrow)
        headingTV = findViewById(R.id.heading)
        buttonHolder = findViewById(R.id.buttonHolderLayout)
        truth = findViewById(R.id.truthButton)
        dare = findViewById(R.id.dareButton)
        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView = findViewById(R.id.nav_View)
        openMenuButton = findViewById(R.id.openMenuIV)
        arrow.setOnClickListener{
            spinArrow()
        }
        truth.setOnClickListener {
            val intent = Intent(this@MainActivity, TruthOrDareActivity::class.java)
            intent.putExtra("flag", "1")
            startActivity(intent)
        }

        dare.setOnClickListener {
            val intent = Intent(this@MainActivity, TruthOrDareActivity::class.java)
            intent.putExtra("flag", "2")
            startActivity(intent)
        }

        openMenuButton.setOnClickListener({ drawerLayout.openDrawer(GravityCompat.START) })
        navigationView.setNavigationItemSelectedListener(this@MainActivity)
    }

    private fun spinArrow() {
        //generating a random number
        nextPoint = Random().nextInt(10000)
        val px = arrow.width / 2
        val py = arrow.height / 2
        val rotate: Animation =
            RotateAnimation(currentPoint.toFloat(), nextPoint.toFloat(), px.toFloat(), py.toFloat())
        rotate.setDuration(3000)
        rotate.setFillAfter(true)
        currentPoint = nextPoint
        arrow.startAnimation(rotate)

        // listener on rotate
        rotate.setAnimationListener(object : Animation.AnimationListener {
            @SuppressLint("SetTextI18n")
            override fun onAnimationStart(animation: Animation) {
                headingTV.text = "Rotating..."
                buttonHolder.visibility = View.GONE
            }

            @SuppressLint("SetTextI18n")
            override fun onAnimationEnd(animation: Animation) {
                headingTV.text = "Choose Truth or Dare?"
                buttonHolder.visibility = View.VISIBLE
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
    }

    @SuppressLint("NonConstantResourceId")
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.menu_about -> {
                val builder = AlertDialog.Builder(this@MainActivity)
                builder.setIcon(R.drawable.baseline_info_24)
                builder.setTitle("About")
                builder.setMessage(R.string.about)
                builder.setPositiveButton(
                    "Ok",
                    { dialog: DialogInterface, which: Int -> dialog.cancel() })
                builder.show()
            }
            R.id.menu_rate -> {
                val uri = Uri.parse("https://play.google.com/store/apps/details?id=com.as.truthordare")
                val i = Intent(Intent.ACTION_VIEW)
                i.setData(uri)
                startActivity(i)
            }
            R.id.menu_share -> {
                val sendIntent = Intent()
                sendIntent.setAction(Intent.ACTION_SEND)
                sendIntent.putExtra(
                    Intent.EXTRA_TEXT, """
         Found an amazing game!
         https://play.google.com/store/apps/details?id=com.as.truthordare
         """.trimIndent()
                )
                sendIntent.setType("text/plain")
                sendIntent.putExtra(Intent.EXTRA_TITLE, "Sharing the App!")
                val shareIntent: Intent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
            }
            R.id.menu_email -> {
                val email = "ayushojha1512@gmail.com"
                val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$email"))
                startActivity(Intent.createChooser(emailIntent, "Mail"))
            }
            R.id.menu_exit -> {
                finishAffinity()
            }
        }
        return false
    }
}