package edu.bistu.computer.calculator;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "id";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditText[] et;
    private TextView[] tv;

    private OnFragmentInteractionListener mListener;

    public DetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailFragment newInstance(String param1, String param2) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        tv = new TextView[4];
        TextView tv01 = (TextView) view.findViewById(R.id.tv01);
        TextView tv02 = (TextView) view.findViewById(R.id.tv02);
        TextView tv03 = (TextView) view.findViewById(R.id.tv03);
        TextView tv04 = (TextView) view.findViewById(R.id.tv04);
        final Button okConversion = (Button) view.findViewById(R.id.ok_conversion);
        final Button clearCoversion = (Button) view.findViewById(R.id.clear_conversion);
        tv[0] = tv01;
        tv[1] = tv02;
        tv[2] = tv03;
        tv[3] = tv04;

        switch (mParam1) {
            case "1":
                tv01.setText("km");
                tv02.setText("m");
                tv03.setText("mm");
                tv04.setText("um");
                break;
            case "2":
                tv01.setText("t");
                tv02.setText("kg");
                tv03.setText("g");
                tv04.setText("mg");
                break;
            case "3":
                tv01.setText("m3");
                tv02.setText("dm3");
                tv03.setText("cm3");
                tv04.setText("mm3");
                break;
            case "4":
                tv01.setText("h");
                tv02.setText("m");
                tv03.setText("s");
                tv04.setText("ms");
                break;
            default:
                break;

        }

        et = new EditText[4];
        et[0] = (EditText) view.findViewById(R.id.et01);
        et[1] = (EditText) view.findViewById(R.id.et02);
        et[2] = (EditText) view.findViewById(R.id.et03);
        et[3] = (EditText) view.findViewById(R.id.et04);

        okConversion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int scale = 0;

                switch (tv[0].getText().toString()) {
                    case "km":
                    case "t":
                    case "m3":
                        scale = 1000;
                        break;
                    case "h":
                        scale = 60;
                        break;
                    default:
                        break;
                }

                int i;
                for (i = 0; i < et.length; i++) {
                    if (!"".equals(et[i].getText().toString())) {
                        break;
                    }
                }

                switch (i) {
                    case 0:
                        et[1].setText(Double.parseDouble(et[0].getText().toString())
                                * scale + "");
                        et[2].setText(Double.parseDouble(et[0].getText().toString())
                                * scale * scale + "");
                        et[3].setText(Double.parseDouble(et[0].getText().toString())
                                * scale * scale + "");
                        break;
                    case 1:
                        et[0].setText(Double.parseDouble(et[1].getText().toString())
                                / scale + "");
                        et[2].setText(Double.parseDouble(et[1].getText().toString())
                                * scale + "");
                        et[3].setText(Double.parseDouble(et[1].getText().toString())
                                * scale * scale + "");
                        break;
                    case 2:
                        et[0].setText(Double.parseDouble(et[2].getText().toString())
                                / scale / scale + "");
                        et[1].setText(Double.parseDouble(et[2].getText().toString())
                                / scale + "");
                        et[3].setText(Double.parseDouble(et[2].getText().toString())
                                * scale + "");
                        break;
                    case 3:
                        et[0].setText(Double.parseDouble(et[3].getText().toString())
                                / scale / scale / scale + "");
                        et[1].setText(Double.parseDouble(et[3].getText().toString())
                                / scale / scale + "");
                        et[2].setText(Double.parseDouble(et[3].getText().toString())
                                / scale + "");
                }
            }
        });

        clearCoversion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (EditText edit : et) {
                    edit.setText("");
                }
            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
