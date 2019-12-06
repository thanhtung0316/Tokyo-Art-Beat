package com.thanhtung.mockproject.ui.browse;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import com.thanhtung.mockproject.R;
import com.thanhtung.mockproject.adapter.SearchPagerAdapter;
import com.thanhtung.mockproject.databinding.FragmentListEventByCategoryBinding;
import com.thanhtung.mockproject.model.Category;
import com.thanhtung.mockproject.ui.browse.category.listbydate.BrowseListByDateFragment;
import com.thanhtung.mockproject.ui.browse.category.listbypopular.BrowseListByPopularFragment;

public class BrowseListEventByCategoryFragment extends Fragment implements View.OnClickListener {
    private BrowseListByDateFragment fmbyDate = new BrowseListByDateFragment();
    private BrowseListByPopularFragment fmbyPopular = new BrowseListByPopularFragment();
    private BrowseFragment fmBrowse;
    private FragmentListEventByCategoryBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list_event_by_category,container,false);
        binding.vpBrowseSearch.setAdapter(new SearchPagerAdapter(getChildFragmentManager(),0
                , new String[]{"by Popularity", "by Date"},fmbyPopular,fmbyDate));
        binding.tabBrowseSearch.setupWithViewPager(binding.vpBrowseSearch);
        fmBrowse = (BrowseFragment) getParentFragment();
        binding.imvBack.setOnClickListener(this);
        binding.getRoot().setBackgroundColor(Color.WHITE);
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        fmBrowse.showFragment(fmBrowse.getFmCategory(),android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    public void binData(Category category){
        binding.tvCategoryTitle.setText(category.getName());
    }

    public BrowseListByDateFragment getFmbyDate() {
        return fmbyDate;
    }

    public BrowseListByPopularFragment getFmbyPopular() {
        return fmbyPopular;
    }
}
