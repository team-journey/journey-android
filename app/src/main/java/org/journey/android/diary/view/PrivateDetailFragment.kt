package org.journey.android.diary.view

import android.app.Dialog
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.bumptech.glide.request.RequestOptions
import jp.wasabeef.glide.transformations.BlurTransformation
import jp.wasabeef.glide.transformations.ColorFilterTransformation
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import org.journey.android.R
import org.journey.android.databinding.FragmentPrivateDetailBinding
import org.journey.android.diary.dto.ResponseDiaryDislikeData
import org.journey.android.diary.dto.ResponseDiaryLikeData
import org.journey.android.diary.dto.ResponseDiaryPrivateDetailData
import org.journey.android.login.view.userJwt
import org.journey.android.main.model.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PrivateDetailFragment: Fragment() {

    private lateinit var  binding : FragmentPrivateDetailBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?{
        binding = FragmentPrivateDetailBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var journeyMood = 0
        val displaymetricsPrivateDetailFragment = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(
            displaymetricsPrivateDetailFragment
        )
        val heightPrivateDetailFragmentDisplay =
            displaymetricsPrivateDetailFragment.heightPixels * 0.5
        val widthPrivateDetailFragmentDisplay =
            displaymetricsPrivateDetailFragment.widthPixels * 0.9
        var postNumCurrentPage = 0
        val call: Call<ResponseDiaryPrivateDetailData> = RetrofitService.diaryPrivateDetailService
            .getPrivateDetailDiary(
                detailPostId,
                userJwt
            )
        call.enqueue(object : Callback<ResponseDiaryPrivateDetailData> {
            override fun onResponse(
                call: Call<ResponseDiaryPrivateDetailData>,
                responseDetail: Response<ResponseDiaryPrivateDetailData>
            ) {
                if (responseDetail.isSuccessful) {
                    val data = responseDetail.body()?.data
                    if (data != null) {
                        postNumCurrentPage = data.postId
                        binding.textviewPrivateDetailNickname.text = data.nickname
                        var privateDetailNowDate = data.year + "."
                        privateDetailNowDate = if (data.month.toInt() < 10)
                            privateDetailNowDate + "0" + data.month + "."
                        else
                            privateDetailNowDate + data.month + "."
                        if (data.day.toInt() < 10)
                            privateDetailNowDate = privateDetailNowDate + "0" + data.day
                        else
                            privateDetailNowDate = privateDetailNowDate + data.day
                        privateDetailNowDate = privateDetailNowDate + "(" + data.week + ")"
                        binding.textviewPrivateDetailDate.text = privateDetailNowDate
                        binding.buttonPrivateDetailLike.text=data.likeCount.toString()

                        val multi = MultiTransformation<Bitmap>(
                            GranularRoundedCorners(50F,50F,0F,0F)
                        )

                        Glide.with(view)
                            .load(data.mainImage)
                            .apply(RequestOptions.bitmapTransform(multi))
                            .into(binding.imageviewPrivateDetailBack)

                        journeyMood=data.mood
                        if(journeyMood==0)
                            binding.imageviewPrivateDetailFront.setImageResource(R.drawable.ic_diary_journey_bad_face)
                        else if(journeyMood==1)
                            binding.imageviewPrivateDetailFront.setImageResource(R.drawable.ic_diary_journey_soso_face)
                        else if(journeyMood==2)
                            binding.imageviewPrivateDetailFront.setImageResource(R.drawable.ic_diary_journey_good_face)


                        val testHashtagString = data.hashtags.joinToString(" ")
                        if (testHashtagString.isEmpty()) {
                            binding.textviewPrivateDetailHashtag.text = testHashtagString
                        } else if (testHashtagString.length in 1..19) {
                            val shortHashtagString = "\n" + testHashtagString
                            binding.textviewPrivateDetailHashtag.isVisible = true
                            binding.textviewPrivateDetailHashtag.text = shortHashtagString
                        } else if (testHashtagString.length > 19) {
                            val longHashtagString = testHashtagString.substring(0, 18) + "\n" + testHashtagString.substring(19, testHashtagString.length - 1)
                            binding.textviewPrivateDetailHashtag.isVisible = true
                            binding.textviewPrivateDetailHashtag.text = longHashtagString
                        }

                        val testContentString = data.content
                        if(testContentString.length>=18)
                        {
                            val longContentString = testContentString.substring(0,16)+"\n"+testContentString.substring(17, testContentString.length-1)
                            binding.textviewPrivateDetailContent.text=longContentString
                        }
                        else{
                            binding.textviewPrivateDetailContent.text = testContentString+"\n"
                        }
                    }
                }
                else{
                    Log.d("ClientTest", "Client Error")
                }
            }
            override fun onFailure(call: Call<ResponseDiaryPrivateDetailData>, t: Throwable) {
                Log.d("NetworkTest", "error:$t")
            }
        }
        )

        binding.buttonPrivateDelete.setOnClickListener()
        {
            val deleteDialog = activity?.let { it1 -> Dialog(it1) }
            val deleteDialogInflater: LayoutInflater = LayoutInflater.from(activity)
            val mView: View =
                deleteDialogInflater.inflate(R.layout.private_delete_message_dialog, null)
            val back: Button = mView.findViewById(R.id.button_do_not_delete)
            val delete: Button = mView.findViewById(R.id.button_real_delete)
            val window = deleteDialog?.window
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            if (deleteDialog != null) {
                deleteDialog.setContentView(mView)
                deleteDialog.create()
                deleteDialog.show()
                deleteDialog.window?.setLayout(
                    widthPrivateDetailFragmentDisplay.toInt(),
                    heightPrivateDetailFragmentDisplay.toInt()
                )
            }
            back.setOnClickListener {
                if (deleteDialog != null) {
                    deleteDialog.dismiss()
                    deleteDialog.cancel()
                }
            }
            delete.setOnClickListener {
                val call: Call<Unit> = RetrofitService.diaryDeleteService
                    .deleteDiary(
                        postNumCurrentPage,
                        userJwt
                    )
                call.enqueue(object : Callback<Unit> {
                    override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                        if (response.isSuccessful) {
                            Toast.makeText(requireContext(), "삭제되었습니다",Toast.LENGTH_SHORT).show()
                        } else {
                        }
                    }

                    override fun onFailure(call: Call<Unit>, t: Throwable) {
                        Log.d("Delete Diary NT Error", "Delete Error!")
                    }
                })
            }
        }

        binding.buttonPrivateDetailLike.setOnClickListener{
            if(binding.buttonPrivateDetailLike.isSelected==false)
            {
                val call:Call<ResponseDiaryLikeData> = RetrofitService.diaryLikeService
                    .changeLike(detailPostId, userJwt)
                call.enqueue(object:Callback<ResponseDiaryLikeData>{
                    override fun onResponse(
                        call: Call<ResponseDiaryLikeData>,
                        response: Response<ResponseDiaryLikeData>
                    ) {
                        if(response.isSuccessful)
                        {
                            Toast.makeText(requireContext(),"Like!",Toast.LENGTH_SHORT).show()
                            binding.buttonPrivateDetailLike.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_icnheartfull,0,0,0)
                            binding.buttonPrivateDetailLike.text =(binding.buttonPrivateDetailLike.text.toString().toInt() + 1).toString()
                            binding.buttonPrivateDetailLike.setTextColor(resources.getColor(R.color.journey_pink2))
                            binding.buttonPrivateDetailLike.isSelected=true
                        }
                        else{
                            Log.d("CT ERROR", "CT ERROR")
                        }
                    }
                    override fun onFailure(call: Call<ResponseDiaryLikeData>, t: Throwable) {
                        Log.d("NT ERROR", "NT ERROR")
                    }
                })
            }
            else{
                val call:Call<ResponseDiaryDislikeData> = RetrofitService.diaryDislikeService
                    .changeDislike(detailPostId, userJwt)
                call.enqueue(object:Callback<ResponseDiaryDislikeData>{
                    override fun onResponse(
                        call: Call<ResponseDiaryDislikeData>,
                        response: Response<ResponseDiaryDislikeData>
                    ) {
                        if(response.isSuccessful)
                        {
                            Toast.makeText(requireContext(),"Dislike!",Toast.LENGTH_SHORT).show()
                            binding.buttonPrivateDetailLike.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_diary_private_heart,0,0,0)
                            binding.buttonPrivateDetailLike.text =(binding.buttonPrivateDetailLike.text.toString().toInt() - 1).toString()
                            binding.buttonPrivateDetailLike.setTextColor(resources.getColor(R.color.white))
                            binding.buttonPrivateDetailLike.isSelected=false
                        }
                        else{
                            Log.d("CT ERROR", "CT ERROR")
                        }
                    }
                    override fun onFailure(call: Call<ResponseDiaryDislikeData>, t: Throwable) {
                        Log.d("NT ERROR", "NT ERROR")
                    }
                })
            }
        }

        binding.buttonPrivateCancel.setOnClickListener {
            findNavController().navigate(R.id.action_privateDetailFragment_to_privateFragment)
        }
    }


}