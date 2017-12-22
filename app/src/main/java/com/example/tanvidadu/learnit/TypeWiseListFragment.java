package com.example.tanvidadu.learnit;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class TypeWiseListFragment extends Fragment  {

    private OnFragmentInteractionListener mListener;
    private ArrayList<RobesForRent> robeToBeDisplayed;




    private final String List_Items = "List_Items";

    public TypeWiseListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView =  inflater.inflate(R.layout.fragment_type_wise_list, container, false);
         if( savedInstanceState != null && robeToBeDisplayed == null){
             robeToBeDisplayed = savedInstanceState.getParcelableArrayList(List_Items);
         }

        final ListView listView ;
        final RobesAdapter robesAdapter = new RobesAdapter(getActivity(), robeToBeDisplayed);
        listView = (ListView) rootView.findViewById(R.id.List_View_items);

        if( robeToBeDisplayed != null ) {


             listView.setAdapter(robesAdapter);



            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    mListener.onFragmentInteraction(position);

                }
            });

        }





        return rootView;
    }

    private void runOnUiThread(Runnable runnable) {
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(int position) {
        if (mListener != null) {
            mListener.onFragmentInteraction(position);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
       try{
            mListener = (OnFragmentInteractionListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString()
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(int position);
    }

    public void setRobeToBeDisplayed(ArrayList<RobesForRent> robeToBeDisplayed) {
        this.robeToBeDisplayed = robeToBeDisplayed;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(List_Items , robeToBeDisplayed);
        super.onSaveInstanceState(outState);
    }
}
