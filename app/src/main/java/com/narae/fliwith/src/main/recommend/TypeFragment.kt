package com.narae.fliwith.src.main.recommend

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.narae.fliwith.R
import com.narae.fliwith.databinding.FragmentTypeBinding
import com.narae.fliwith.databinding.LayoutSelectAiBinding
import com.narae.fliwith.databinding.LayoutSelectBtnAiBinding
import com.narae.fliwith.src.main.MainActivity
import com.narae.fliwith.src.main.recommend.models.RecommendViewModel

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

// 여행지 유형 선택
class TypeFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentTypeBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var mainActivity: MainActivity
    private val viewModel: RecommendViewModel by activityViewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTypeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.typeAllBtn.layoutRecommendSelectDetailBtn.text = "전체"
        binding.typeTourBtn.layoutRecommendSelectDetailBtn.text = "관광지"
        binding.typeCulturalBtn.layoutRecommendSelectDetailBtn.text = "문화시설"
        binding.typeLodgmentBtn.layoutRecommendSelectDetailBtn.text = "숙박"
        binding.typeShoppingBtn.layoutRecommendSelectDetailBtn.text = "쇼핑"
        binding.typeStoreBtn.layoutRecommendSelectDetailBtn.text = "음식점"

        val buttons = arrayOf(
            binding.typeAllBtn,
            binding.typeShoppingBtn,
            binding.typeStoreBtn,
            binding.typeLodgmentBtn,
            binding.typeCulturalBtn,
            binding.typeTourBtn
        )

        for (button in buttons) {
            button.layoutRecommendSelectDetailBtn.setOnClickListener {
                for (btn in buttons) {
                    // 클릭된 버튼을 선택하고, 다른 버튼들을 비활성화
                    if (btn != button) {
                        btn.layoutRecommendSelectDetailBtn.isSelected = false
                        btn.layoutRecommendSelectDetailBtn.isEnabled = true
                    } else {
                        btn.layoutRecommendSelectDetailBtn.isSelected = true
                        btn.layoutRecommendSelectDetailBtn.isEnabled = false
                    }
                }
            }

            // 이전에 선택했던 항목을 선택 상태로 보여주기 위함
            val selected = viewModel.selectedTypeButtonText.value
            if (selected == button.layoutRecommendSelectDetailBtn.text) {
                button.layoutRecommendSelectDetailBtn.isSelected = true
                button.layoutRecommendSelectDetailBtn.isEnabled = false
            }
        }

        view.rootView.findViewById<AppCompatButton>(R.id.send_recommend_detail_btn)
            .setOnClickListener {
                // 클릭된 버튼의 text를 selectedButtonText에 저장
                for (btn in buttons) {
                    if (btn.layoutRecommendSelectDetailBtn.isSelected)
                        viewModel.setSelectedTypeButtonText(btn.layoutRecommendSelectDetailBtn.text.toString())
                }
                findNavController().popBackStack()
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TypeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}