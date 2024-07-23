package com.sjw.mgmarket

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sjw.mgmarket.databinding.FragmentDetailBinding

private const val KEY = "product_data"

class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding

    private var param: Product? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param = it.getParcelable(KEY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var isChecked = true

        with(binding) {
            detailUserImg.clipToOutline = true
            param?.let {
                detailImg.setImageResource(it.imgSrc)
                detailNickname.text = it.seller
                detailLocation.text = it.address
                detailTitle.text = it.name
                detailContent.text = it.content
                detailPrice.text = it.price

                detailFavoriteBtn.setOnClickListener {
                    if (isChecked) {
                        param!!.favorite += 1
                        detailFavoriteBtn.setImageResource(R.drawable.ic_favorite_full)
                        isChecked = false
                    } else {
                        param!!.favorite -= 1
                        detailFavoriteBtn.setImageResource(R.drawable.ic_favorite)
                        isChecked = true
                    }
                }
            }
            detailUserImg.setImageResource(R.drawable.profile_img)
            detailTemperature.text = "39.3℃"
            detailExpression.setImageResource(R.drawable.smile_img)
            detailBackBtn.setOnClickListener {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, ProductListFragment())
                    .addToBackStack(null)
                    .commit()
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(productData: Product) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY, productData)
                }
            }
    }
}
