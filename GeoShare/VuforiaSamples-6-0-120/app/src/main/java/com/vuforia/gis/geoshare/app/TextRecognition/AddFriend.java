package com.vuforia.gis.geoshare.app.TextRecognition;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.vuforia.gis.geoshare.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddFriend#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFriend extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    static final String FR = "friends"; // parent node
    static final String FR_ID = "id";
    static final String FR_NAME = "name";
    static final String NO_F = "no_f";
    static final String FR_IMG = "img";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ListView list_v;
    CustomList adapter;
    View v;
    private OnFragmentInteractionListener mListener;
    int user_id;
    public AddFriend() {
        // Required empty public constructor
    }
    public void get_user_id(){
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null) {
            user_id = extras.getInt("user_id");
            System.out.println("user id in new activity is "+user_id);
            //The key argument here must match that used in the other activity
        }
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddFriend.
     */
    // TODO: Rename and change types and number of parameters
    public static AddFriend newInstance(String param1, String param2) {
        AddFriend fragment = new AddFriend();

        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        get_user_id();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

         v =  inflater.inflate(R.layout.fragment_add_friend, container, false);
        call_aync(v);
        return v;

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

    int userId;
    TextView t;
    private void call_aync(View v){
        send_request request_obj = new send_request();
        ArrayList<HashMap<String,String>>  list = new ArrayList<HashMap<String, String>>();
        try {
            String temp = request_obj.execute("addfriends?user="+user_id).get();
            Toast.makeText(getActivity(),temp,Toast.LENGTH_SHORT);
            String[] names =temp.split(",");
            for (String n:names)
            {
                HashMap<String,String> info = new HashMap<String, String>();
                info.put(FR_NAME,n);
                info.put(FR_IMG,"/Users/AJ/Documents/GeoShare/GeoShare/app/src/main/res/drawable/abc.png");
                list.add(info);
            }
            list_v = (ListView) v.findViewById(R.id.list);
            adapter=new CustomList(getActivity(), list);
            list_v.setAdapter(adapter);
            list_v.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }


            });

            System.out.println(temp);
            if(temp.equals("no entry found")){
                System.out.println("same intent");
            }
            else{


            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
