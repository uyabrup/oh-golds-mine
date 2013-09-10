package vn.luis.goldsmine.golduser;

import vn.luis.goldsmine.R;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ChartGoldUserFragment extends Fragment{
	public static Fragment newInstance(Context context) {
		ChartGoldUserFragment f = new ChartGoldUserFragment();
        return f;
    }
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_chart_gold, null);
        return root;
    }
}
