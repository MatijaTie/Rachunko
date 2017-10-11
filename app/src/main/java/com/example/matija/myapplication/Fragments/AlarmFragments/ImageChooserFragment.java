package com.example.matija.myapplication.Fragments.AlarmFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.matija.myapplication.Events.ChooseImageEvent;
import com.example.matija.myapplication.R;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class ImageChooserFragment extends Fragment {

    @BindView(R.id.s1)
    ImageView s1;
    @BindView(R.id.s2)
    ImageView s2;
    @BindView(R.id.s3)
    ImageView s3;
    @BindView(R.id.s4)
    ImageView s4;
    @BindView(R.id.s5)
    ImageView s5;
    @BindView(R.id.s6)
    ImageView s6;
    @BindView(R.id.s7)
    ImageView s7;
    @BindView(R.id.s8)
    ImageView s8;
    @BindView(R.id.s9)
    ImageView s9;
    @BindView(R.id.s10)
    ImageView s10;
    @BindView(R.id.s11)
    ImageView s11;
    @BindView(R.id.s12)
    ImageView s12;
    @BindView(R.id.s13)
    ImageView s13;
    @BindView(R.id.s14)
    ImageView s14;
    @BindView(R.id.s15)
    ImageView s15;
    @BindView(R.id.s16)
    ImageView s16;
    @BindView(R.id.s17)
    ImageView s17;
    @BindView(R.id.s18)
    ImageView s18;
    @BindView(R.id.s19)
    ImageView s19;
    @BindView(R.id.s20)
    ImageView s20;
    @BindView(R.id.s21)
    ImageView s21;
    Unbinder unbinder;

    public ImageChooserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ImageChooserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ImageChooserFragment newInstance() {
        ImageChooserFragment fragment = new ImageChooserFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_image_chooser, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.s1, R.id.s2, R.id.s3, R.id.s4, R.id.s5, R.id.s6, R.id.s7, R.id.s8, R.id.s9, R.id.s10, R.id.s11, R.id.s12, R.id.s13, R.id.s14, R.id.s15, R.id.s16, R.id.s17, R.id.s18, R.id.s19, R.id.s20, R.id.s21})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.s1:
                EventBus.getDefault().post(new ChooseImageEvent(R.drawable.slike8));
                getActivity().getSupportFragmentManager().popBackStack();
                break;
            case R.id.s2:
                EventBus.getDefault().post(new ChooseImageEvent(R.drawable.slike4));
                getActivity().getSupportFragmentManager().popBackStack();
                break;
            case R.id.s3:
                EventBus.getDefault().post(new ChooseImageEvent(R.drawable.slike3));
                getActivity().getSupportFragmentManager().popBackStack();
                break;
            case R.id.s4:
                EventBus.getDefault().post(new ChooseImageEvent(R.drawable.slike20));
                getActivity().getSupportFragmentManager().popBackStack();
                break;
            case R.id.s5:
                EventBus.getDefault().post(new ChooseImageEvent(R.drawable.slike19));
                getActivity().getSupportFragmentManager().popBackStack();
                break;
            case R.id.s6:
                EventBus.getDefault().post(new ChooseImageEvent(R.drawable.slike18));
                getActivity().getSupportFragmentManager().popBackStack();
                break;
            case R.id.s7:
                EventBus.getDefault().post(new ChooseImageEvent(R.drawable.slike17));
                getActivity().getSupportFragmentManager().popBackStack();
                break;
            case R.id.s8:
                EventBus.getDefault().post(new ChooseImageEvent(R.drawable.slike14));
                getActivity().getSupportFragmentManager().popBackStack();
                break;
            case R.id.s9:
                EventBus.getDefault().post(new ChooseImageEvent(R.drawable.slike16));
                getActivity().getSupportFragmentManager().popBackStack();
                break;
            case R.id.s10:
                EventBus.getDefault().post(new ChooseImageEvent(R.drawable.slike13));
                getActivity().getSupportFragmentManager().popBackStack();
                break;
            case R.id.s11:
                EventBus.getDefault().post(new ChooseImageEvent(R.drawable.slike12));
                getActivity().getSupportFragmentManager().popBackStack();
                break;
            case R.id.s12:
               EventBus.getDefault().post(new ChooseImageEvent(R.drawable.slike11));
                getActivity().getSupportFragmentManager().popBackStack();
                break;
            case R.id.s13:
                EventBus.getDefault().post(new ChooseImageEvent(R.drawable.slike10));
                getActivity().getSupportFragmentManager().popBackStack();
                break;
            case R.id.s14:
                EventBus.getDefault().post(new ChooseImageEvent(R.drawable.slike1));
                getActivity().getSupportFragmentManager().popBackStack();
                break;
            case R.id.s15:
                EventBus.getDefault().post(new ChooseImageEvent(R.drawable.slike15));
                getActivity().getSupportFragmentManager().popBackStack();
                break;
            case R.id.s16:
                EventBus.getDefault().post(new ChooseImageEvent(R.drawable.slike9));
                getActivity().getSupportFragmentManager().popBackStack();
                break;
            case R.id.s17:
                EventBus.getDefault().post(new ChooseImageEvent(R.drawable.slike2));
                getActivity().getSupportFragmentManager().popBackStack();
                break;
            case R.id.s18:
                EventBus.getDefault().post(new ChooseImageEvent(R.drawable.add));
                getActivity().getSupportFragmentManager().popBackStack();
                break;
            case R.id.s19:
                EventBus.getDefault().post(new ChooseImageEvent(R.drawable.slike7));
                getActivity().getSupportFragmentManager().popBackStack();
                break;
            case R.id.s20:
                EventBus.getDefault().post(new ChooseImageEvent(R.drawable.slike6));
                getActivity().getSupportFragmentManager().popBackStack();
                break;
            case R.id.s21:
                EventBus.getDefault().post(new ChooseImageEvent(R.drawable.trash));
                getActivity().getSupportFragmentManager().popBackStack();
                break;
        }
    }
}
