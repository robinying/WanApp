package com.yubin.wanapp.activity.splash;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yubin.wanapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link SplashFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SplashFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_SECTION_NUMBER = "section_num";
    @BindView(R.id.section_img)
    ImageView sectionImg;
    @BindView(R.id.section_label)
    AppCompatTextView sectionLabel;
    @BindView(R.id.section_intro)
    AppCompatTextView sectionIntro;
    Unbinder unbinder;
    private int page;


    public SplashFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SplashFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SplashFragment newInstance(int sectionNumber) {
        SplashFragment fragment = new SplashFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            page = getArguments().getInt(ARG_SECTION_NUMBER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_splash, container, false);
        unbinder = ButterKnife.bind(this, view);
        switch (page){
            case 0:
                sectionImg.setImageResource(R.drawable.ic_splash1);
                sectionLabel.setText(R.string.section_1);
                sectionIntro.setText(R.string.intro_1);
                break;

            case 1:
                sectionImg.setImageResource(R.drawable.ic_splash2);
                sectionLabel.setText(R.string.section_2);
                sectionIntro.setText(R.string.intro_2);
                break;

            case 2:
                sectionImg.setImageResource(R.drawable.ic_splash3);
                sectionLabel.setText(R.string.section_3);
                sectionIntro.setText(R.string.intro_3);
                break;

            default:break;
        }
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
