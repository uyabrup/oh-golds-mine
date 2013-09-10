package vn.luis.goldsmine.money;

import vn.luis.goldsmine.R;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class StatisticsMoneyFragment extends Fragment {
	public static Fragment newInstance(Context context) {
		StatisticsMoneyFragment f = new StatisticsMoneyFragment();
        return f;
    }
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_statistics_money, null);
        return root;
    }
}
