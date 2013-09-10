package vn.luis.goldsmine.money;

import vn.luis.goldsmine.R;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ListMoneyFragment extends Fragment {
	public static Fragment newInstance(Context context) {
		ListMoneyFragment f = new ListMoneyFragment();
        return f;
    }
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_list_money, null);
        return root;
    }
}
