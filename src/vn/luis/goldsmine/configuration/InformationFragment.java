package vn.luis.goldsmine.configuration;

import vn.luis.goldsmine.R;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class InformationFragment extends Fragment{
	public static Fragment newInstance(Context context) {
		InformationFragment f = new InformationFragment();
        return f;
    }
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_setting, null);
        return root;
    }
}
