package com.example.matija.myapplication.Fragments.RasporedFragments;

import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.matija.myapplication.Database.RasporedPart;
import com.example.matija.myapplication.R;
import com.example.matija.myapplication.Views.RasporedView;
import com.pes.androidmaterialcolorpickerdialog.ColorPicker;
import com.pes.androidmaterialcolorpickerdialog.ColorPickerCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by matija on 23.08.17..
 */

public class RasporedPartFragment extends Fragment {
    @BindView(R.id.text_color)
    TextView textColor;
    @BindView(R.id.text_color_picker)
    Button textColorPicker;
    @BindView(R.id.background_color_picker)
    Button backgroundColorPicker;
    @BindView(R.id.background_color)
    TextView backgroundColor;
    @BindView(R.id.text_part)
    TextView textPart;
    @BindView(R.id.text_input_part)
    EditText textInputPart;
    @BindView(R.id.confirm_raspored_part)
    Button confirmRasporedPart;
    Unbinder unbinder;

    int TextColorInt;
    int backgroundColorInt;

    RasporedView rasporedView;
    String textInput;

    public static RasporedPartFragment newInstance(@Nullable RasporedView parent) {
        if(parent != null){
            RasporedPartFragment frag = new RasporedPartFragment();
            Bundle args = new Bundle();
            args.putSerializable("values",parent);
            frag.setArguments(args);
            return frag;
        }else{
            return new RasporedPartFragment();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.raspored_part_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);

        Bundle args = getArguments();
        if(args != null) {

            RasporedView part = (RasporedView) args.getSerializable("values");
            if(part.getRasporedPart() != null){
                TextColorInt = part.getRasporedPart().getFontColor();
                backgroundColorInt = part.getRasporedPart().getBackroundColor();
                textColorPicker.setBackgroundColor(TextColorInt);
                backgroundColorPicker.setBackgroundColor(backgroundColorInt);
                textInputPart.setText(part.getRasporedPart().getTitle());

            }

            rasporedView = part;
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.text_color_picker, R.id.background_color_picker, R.id.confirm_raspored_part})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.text_color_picker:
                getColor(textColorPicker);
                break;
            case R.id.background_color_picker:
                getColor(backgroundColorPicker);
                break;
            case R.id.confirm_raspored_part:
                //rasporedView.setBackgroundColor(backgroundColorInt);
                //rasporedView.setTextColor(TextColorInt);
                String text = textInputPart.getText().toString();
                //rasporedView.setText(text);

                RasporedPart part = new RasporedPart(TextColorInt, backgroundColorInt, text);
                rasporedView.setRasporedPart(part);
                rasporedView.setView();

              //  rasporedView.setText("taddei");
                getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
                getActivity().getSupportFragmentManager().popBackStack();
                break;
        }
    }

    public void getColor(final Button button){
        final ColorPicker cp = new ColorPicker(getActivity(), 0, 0, 0);
        /* Show color picker dialog */
        cp.show();
        cp.setCallback(new ColorPickerCallback() {
            @Override
            public void onColorChosen(@ColorInt int color) {

               if(button.getId() == R.id.text_color_picker) {
                   TextColorInt = color;
               }else{
                   backgroundColorInt = color;
               }

                button.setBackgroundColor(color);
                cp.cancel();
            }
        });

    }
}
