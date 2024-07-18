package com.sjw.mgmarket

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import com.sjw.mgmarket.databinding.FragmentProductListBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProductListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProductListFragment : Fragment() {
    private lateinit var binding: FragmentProductListBinding

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentProductListBinding.inflate(inflater, container, false)

        val productAdapter = ProductListAdapter(Dummy.productList)
        productAdapter.itemClick = object : ProductListAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                val dataToSend = productAdapter.items[position]
                val fragment = DetailFragment.newInstance(dataToSend)

                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit()
            }
        }
        with(binding) {
            with(productRV) {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = productAdapter
            }
            toolbarNotificationBtn.setOnClickListener {
                createNotificationChannel()
                showNotification()
            }
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProductListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private val myNotificationID = 1
    private val channelID = "default"

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // Android 8.0
            val channel = NotificationChannel(channelID, "default channel",
                NotificationManager.IMPORTANCE_DEFAULT)
            channel.description = "description text of this channel."
            val notificationManager = requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun showNotification() {
        val builder = NotificationCompat.Builder(requireContext(), channelID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("키워드 알림")
            .setContentText("설정한 키워드에 대한 알림이 도착했습니다.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        NotificationManagerCompat.from(requireContext()).notify(myNotificationID, builder.build())
    }
}