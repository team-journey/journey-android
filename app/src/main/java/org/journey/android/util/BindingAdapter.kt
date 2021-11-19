package org.journey.android.util

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import coil.load
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.ShapeAppearanceModel
import org.journey.android.R
import java.lang.System.load


object BindingAdapter {
    @JvmStatic
    @BindingAdapter("setImageURL")
    fun ImageView.setImageURL(url: String?) { if(!url.isNullOrEmpty()) load(url) }

    @JvmStatic
    @BindingAdapter("setCharacterImageURL")
    fun ImageView.setCharacterImageURL(url: String?) {
        if(!url.isNullOrEmpty()) load(url) else setBackgroundColor(ContextCompat.getColor(context, R.color.browser_actions_bg_grey))
    }

    @JvmStatic
    @BindingAdapter("corner_radius")
    fun ShapeableImageView.setCornerRadius(radius: Float) {
        shapeAppearanceModel = ShapeAppearanceModel().withCornerSize(radius)
    }

    @JvmStatic
    @BindingAdapter("loadDrawable")
    fun loadDrawable(imageView: ImageView, drawable: Int?) {
        if (drawable == null) {
            imageView.load(R.drawable.ic_launcher_background)
        } else {
            imageView.load(drawable)
        }
    }
    @JvmStatic
    @BindingAdapter("text_with_string_resources")
    fun TextView.setTextWithStringResources(resourceId: Int) {
        text = resources.getString(resourceId)
    }

    @JvmStatic
    @BindingAdapter("setBackgroundColorRes")
    fun View.setBackgroundColorRes(color: Int) {
        setBackgroundColor(context.getColor(color))
    }

}