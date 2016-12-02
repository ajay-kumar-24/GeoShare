package com.vuforia.gis.geoshare.app.TextRecognition;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vuforia.gis.geoshare.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class popupFragment extends Fragment {

    public popupFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_popup, container, false);
    }
}
