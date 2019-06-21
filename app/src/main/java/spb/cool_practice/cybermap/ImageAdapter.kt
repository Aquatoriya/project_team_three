package spb.cool_practice.cybermap

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter


class ImageAdapter : PagerAdapter {
    constructor(context: Context, Images: IntArray) : super() {
        mContext = context
        images = Images
    }

    private lateinit var mContext: Context
    private lateinit var images : IntArray

    override fun getCount(): Int {
        return images.size
    }

    override fun isViewFromObject(p0: View, p1: Any): Boolean {
        return p0 == p1
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imageView = ImageView(mContext)
        imageView.setImageResource(images[position])
        container.addView(imageView)
        return imageView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ImageView)
    }
}