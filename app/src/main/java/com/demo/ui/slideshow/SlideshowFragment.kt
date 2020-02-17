package com.demo.ui.slideshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.request.RequestOptions
import com.demo.common.BaseFragment

import com.demo.R
import com.glide.slider.library.SliderLayout
import com.glide.slider.library.slidertypes.TextSliderView

class SlideshowFragment : BaseFragment() {
    lateinit var slideshowContainer:FrameLayout
    lateinit var slidershowViewModel:SlidershowViewModel
    var slider:SliderLayout?=null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        slidershowViewModel=ViewModelProvider(this).get(SlidershowViewModel::class.java)
        val root=inflater.inflate(R.layout.fragment_slideshow, container, false)
        slideshowContainer=root.findViewById(R.id.id007)
        return root
    }

    override fun onStart() {
        super.onStart()
        arguments?.let{
            Boolean::class.java.simpleName.let { key->
                if(it.getBoolean(key)) {
                    sharedViewModel.dataSlider.value = null
                    it.putBoolean(key,false)
                }
            }
        }

        when {
            sharedViewModel.dataSlider.value==null -> findNavController().navigate(R.id.nav_slideshow_request)
            sharedViewModel.dataSlider.value?.isEmpty()==true -> findNavController().navigate(R.id.nav_empty_content)
            else -> {
                sharedViewModel.dataSlider.observe(this, Observer { t ->
                    sharedViewModel.dataSlider.removeObservers(this)
                    slideshowContainer.removeAllViews()
                    val r= RequestOptions().centerCrop()
                    slider= SliderLayout(context)
                    t.forEach {display_url->
                        slider?.setPresetTransformer(java.util.Random().nextInt(SliderLayout.Transformer.values().size))
                        val textSliderView = TextSliderView(context)
                        textSliderView.image(display_url)
                        textSliderView.setRequestOption(r)
                        slider?.addSlider(textSliderView)
                    }
                    slideshowContainer.addView(slider)
                    slider?.restoreHierarchyState(slidershowViewModel.state)
                })
            }
        }
    }

    override fun onDestroyView() {
        slider?.saveHierarchyState(slidershowViewModel.state)
        super.onDestroyView()
    }
}