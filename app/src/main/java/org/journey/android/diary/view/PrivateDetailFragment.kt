package org.journey.android.diary.view

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.chip.Chip
import org.journey.android.R
import org.journey.android.databinding.FragmentPrivateDetailBinding
import org.journey.android.diary.service.FeedRequestToServer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


var postDetail = HashMap<String, Any>()

class PrivateDetailFragment: Fragment() {

    private lateinit var  binding : FragmentPrivateDetailBinding

    // 공감 이모션 리스트
    var likeList : MutableList<String> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?{
        binding = FragmentPrivateDetailBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setButtonClickListener(context)

        val displaymetricsPrivateDetailFragment = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(
            displaymetricsPrivateDetailFragment
        )
        val heightPrivateDetailFragmentDisplay =
            displaymetricsPrivateDetailFragment.heightPixels * 0.5
        val widthPrivateDetailFragmentDisplay =
            displaymetricsPrivateDetailFragment.widthPixels * 0.9

        if(postDetail.get("mood")==2)
        {
            binding.imageviewPrivateDetailFront.setImageResource(R.drawable.ic_feel_third)
        }
        else if(postDetail.get("mood")==1)
        {
            binding.imageviewPrivateDetailFront.setImageResource(R.drawable.ic_feel_second)
        }
        else if(postDetail.get("mood")==0)
        {
            binding.imageviewPrivateDetailFront.setImageResource(R.drawable.ic_feel_first)
        }

        Log.d("privateDetail", "${postDetail}")
        Glide.with(this)
            .load(postDetail.get("image"))
            .into(binding.imageviewPrivateDetailBack)

        binding.textviewPrivateDetailNickname.text = postDetail.get("nickname").toString()
        binding.textviewPrivateDetailDate.text = postDetail.get("date").toString()
        binding.textviewPrivateDetailTitle.text = postDetail.get("title").toString()
        binding.textviewPrivateDetailContent.text = postDetail.get("content").toString()

//        for (i in 0 until postDetail.get("emoji").size()) {
//            addChipToGroup(postDetail.get("emoji")[i][0],postDetail.get("emoji")[i][1])
//        }

        Log.d("private emoji", "${postDetail.get("emoji")}")

        binding.buttonPrivateDelete.setOnClickListener()
        {
            val deleteDialog = activity?.let { it1 -> Dialog(it1) }
            val deleteDialogInflater: LayoutInflater = LayoutInflater.from(activity)
            val mView: View =
                deleteDialogInflater.inflate(R.layout.dialog_detail_delete, null)
            val deleteBtn: Button = mView.findViewById(R.id.button_dialog_delete)
            val closeBtn: Button = mView.findViewById(R.id.button_dialog_close)
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
            closeBtn.setOnClickListener {
                if (deleteDialog != null) {
                    deleteDialog.dismiss()
                    deleteDialog.cancel()
                }
            }
            deleteBtn.setOnClickListener {
                val call: Call<Unit> = FeedRequestToServer.service
                    .deletePrivateDetail(
                        postDetail.get("id") as Int,
                        "application/json",
                        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjp7ImlkIjo3N30sImlhdCI6MTYzNDk4MTg1N30.c4ZBhK4vd9AG_LqFyzOfud6x7e_9Flko6_1J098oKsk"
                    )
                call.enqueue(object : Callback<Unit> {
                    override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                        if (response.isSuccessful) {
                            Toast.makeText(requireContext(), "삭제되었습니다",Toast.LENGTH_SHORT).show()
                            deleteDialog?.dismiss()
                            findNavController().popBackStack()
                        } else {
                        }
                    }

                    override fun onFailure(call: Call<Unit>, t: Throwable) {
                        Log.d("Delete Diary NT Error", "Delete Error!")
                    }
                })
            }
        }
    }

    private fun setButtonClickListener(ctxt: Context?){
        with(binding){
            buttonPrivateCancel.setOnClickListener { findNavController().popBackStack() }

            buttonPrivateDetailLike.setOnClickListener{
                val mDialogViewEmoji =
                    LayoutInflater.from(ctxt).inflate(R.layout.dialog_myfeed_emoji, null)
                val mBuilderEmoji = AlertDialog.Builder(ctxt)
                    .setView(mDialogViewEmoji)
                val alertDialogEmoji = mBuilderEmoji.create()

                mDialogViewEmoji.setBackgroundColor(Color.TRANSPARENT)
                val windowEmoji = alertDialogEmoji.window
                windowEmoji?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                val dialogClose = mDialogViewEmoji.findViewById<ImageButton>(R.id.imagebutton_emoji_close)
                val dialogFirst = mDialogViewEmoji.findViewById<ImageButton>(R.id.imagebutton_emoji_first)
                val diaglogSecond = mDialogViewEmoji.findViewById<ImageButton>(R.id.imagebutton_emoji_second)
                val dialogThird = mDialogViewEmoji.findViewById<ImageButton>(R.id.imagebutton_emoji_third)
                val dialogFourth = mDialogViewEmoji.findViewById<ImageButton>(R.id.imagebutton_emoji_fourth)
                val dialogFifth = mDialogViewEmoji.findViewById<ImageButton>(R.id.imagebutton_emoji_fifth)
                val dialogSixth = mDialogViewEmoji.findViewById<ImageButton>(R.id.imagebutton_emoji_sixth)

                dialogClose.setOnClickListener {
                    alertDialogEmoji.dismiss()
                }
                dialogFirst.setOnClickListener {
                    addChipToGroup(1,1)
                    alertDialogEmoji.dismiss()
                }
                diaglogSecond.setOnClickListener {
                    addChipToGroup(2,1)
                    alertDialogEmoji.dismiss()
                }
                dialogThird.setOnClickListener {
                    addChipToGroup(3,1)
                    alertDialogEmoji.dismiss()
                }
                dialogFourth.setOnClickListener {
                    addChipToGroup(4,1)
                    alertDialogEmoji.dismiss()
                }
                dialogFifth.setOnClickListener {
                    addChipToGroup(5,1)
                    alertDialogEmoji.dismiss()
                }
                dialogSixth.setOnClickListener {
                    addChipToGroup(6,1)
                    alertDialogEmoji.dismiss()
                }
                
                alertDialogEmoji.show()
            }

//            imagebuttonPrivateDetailReport.setOnClickListener {
//                val reportDialog = activity?.let { it1 -> BottomSheetDialog(it1) }
//                val view = layoutInflater.inflate(R.layout.dialog_detail_report, null)
//                val window = reportDialog?.window
//                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//
//                val reportBtn = view.findViewById<Button>(R.id.button_dialog_report)
//
//                reportBtn.setOnClickListener {
//                    reportDialog?.dismiss()
//                }
//
//                reportDialog?.setContentView(view)
//                reportDialog?.show()
//
//            }
        }
    }

    fun addChipToGroup(emotion: Int, cnt: Int) {
        if (binding.chipgroupLike.childCount < 6) {
            val chip = Chip(context)
            chip.chipBackgroundColor =
                ColorStateList.valueOf(resources.getColor(R.color.mohaeng_yellow_b))
            chip.chipStrokeColor = ColorStateList.valueOf(resources.getColor(R.color.mohaeng_yellow))
            chip.chipStrokeWidth = 2F
            chip.setTextColor(ColorStateList.valueOf(resources.getColor(R.color.mohaeng_yellow2)))
            chip.text = cnt.toString()
            chip.textSize = 12F
            when(emotion){
                1-> chip.chipIcon =
                    ContextCompat.getDrawable(requireContext(), R.drawable.ic_emoji_tears)
                2-> chip.chipIcon =
                    ContextCompat.getDrawable(requireContext(), R.drawable.ic_emoji_cong)
                3-> chip.chipIcon =
                    ContextCompat.getDrawable(requireContext(), R.drawable.ic_emoji_medal)
                4-> chip.chipIcon =
                    ContextCompat.getDrawable(requireContext(), R.drawable.ic_emoji_good)
                5-> chip.chipIcon =
                    ContextCompat.getDrawable(requireContext(), R.drawable.ic_emoji_twinkle)
                6-> chip.chipIcon =
                    ContextCompat.getDrawable(requireContext(), R.drawable.ic_emoji_lucky)
            }

            chip.isChipIconVisible = true
            chip.iconStartPadding = 30F
            chip.iconEndPadding = 5F


//            chip.isCloseIconVisible = false
//            chip.closeIcon =
//                ContextCompat.getDrawable(requireContext(), R.drawable.ic_diary_hash_tag_close)
//            chip.closeIconSize = 36F
//            chip.closeIconStartPadding = -10F
//            chip.closeIconEndPadding = 30F
//            chip.closeIconTint =
//                ColorStateList.valueOf(resources.getColor(R.color.journey_pink))
            chip.isClickable = true
            chip.isCheckable = false
            likeList.add(chip.text.toString())
            binding.chipgroupLike.addView(chip as View)
            chip.setOnCloseIconClickListener {
                binding.chipgroupLike.removeView(chip as View)
                likeList.remove(chip.text.toString())
            }
        }
    }

}