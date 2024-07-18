package com.sjw.mgmarket

import android.content.DialogInterface
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.sjw.mgmarket.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setFragmnet(ProductListFragment())

        with(binding) {

        }
    }

    private fun setFragmnet(fragment: Fragment) {
        supportFragmentManager.commit {
            replace(R.id.fragment_container, fragment)
            setReorderingAllowed(true)
            addToBackStack("")
        }
    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("종료")
        builder.setIcon(R.drawable.sad_img)
        builder.setMessage("정말 종료하시겠습니까?")

        val listener = DialogInterface.OnClickListener { dialog, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> {
                    super.onBackPressed()
                    finish()
                }
                DialogInterface.BUTTON_NEGATIVE -> dialog.dismiss()
            }
        }

        builder.setPositiveButton("확인", listener)
        builder.setNegativeButton("취소", listener)
        builder.show()
    }
}