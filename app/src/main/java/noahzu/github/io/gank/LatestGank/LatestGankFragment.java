package noahzu.github.io.gank.LatestGank;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import noahzu.github.io.gank.Data.Constants;
import noahzu.github.io.gank.Data.entity.Gank;
import noahzu.github.io.gank.R;

/**
 * 最新的gank
 */
public class LatestGankFragment extends Fragment implements TabLayout.OnTabSelectedListener,LatestGankContract.View{

    private TabLayout mTablayout;
    private ListView mGankList;
    private LatestGankContract.Presenter mPresenter;
    private ProgressBar mLoadingProgress;
    private LatestGankListAdapter mAdapter;

    public LatestGankFragment() {

    }

    public static LatestGankFragment newInstance() {
        LatestGankFragment fragment = new LatestGankFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initView();
        mAdapter = new LatestGankListAdapter(new ArrayList<Gank>(0),getContext());
        return inflater.inflate(R.layout.fragment_latest_gank, container, false);
    }

    private void initView() {
        mTablayout = (TabLayout) getView().findViewById(R.id.latest_gank_tab);
        mGankList = (ListView) getView().findViewById(R.id.latest_gank_list);
        mLoadingProgress = (ProgressBar) getView().findViewById(R.id.loading_pro);
        initTab();
    }

    private void initTab() {
        mTablayout.addTab(mTablayout.newTab().setText(Constants.android_));
        mTablayout.addTab(mTablayout.newTab().setText(Constants.ios));
        mTablayout.addTab(mTablayout.newTab().setText(Constants.video));
        mTablayout.addTab(mTablayout.newTab().setText(Constants.welfare));
        mTablayout.addTab(mTablayout.newTab().setText(Constants.extendSource));
        mTablayout.addTab(mTablayout.newTab().setText(Constants.front));
        mTablayout.addTab(mTablayout.newTab().setText(Constants.recommand));
        mTablayout.addTab(mTablayout.newTab().setText(Constants.app));

        mTablayout.setOnTabSelectedListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        mPresenter.onTabSelect(tab.getText().toString());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        mPresenter.onTabSelect(tab.getText().toString());
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        mPresenter.onTabSelect(tab.getText().toString());
    }

    @Override
    public void showMessage(String msg) {
        Snackbar.make(getView(),msg,Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        mLoadingProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mLoadingProgress.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showGanks(List<Gank> ganks) {
        mAdapter.refreshData(ganks);
        hideLoading();
    }

    @Override
    public void showGankDetails(Gank gank) {
        // TODO: 2016/6/29 0029 页面跳转
    }

    @Override
    public void setPresenter(LatestGankContract.Presenter presenter) {
        this.mPresenter = presenter;
    }
}
