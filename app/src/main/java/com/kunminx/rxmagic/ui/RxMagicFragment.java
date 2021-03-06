package com.kunminx.rxmagic.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.kunminx.rxmagic.R;
import com.kunminx.rxmagic.bean.RxExpression;
import com.kunminx.rxmagic.bean.RxOperator;
import com.kunminx.rxmagic.databinding.FragmentRxmagicBinding;
import com.kunminx.rxmagic.ui.adapter.RxExpressionAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import thereisnospon.codeview.CodeViewTheme;

/**
 * Create by KunMinX at 19/4/20
 */
public class RxMagicFragment extends Fragment {

    private FragmentRxmagicBinding mBinding;
    private RxExpressionAdapter mAdapter;
    private List<RxExpression> mRxExpressions = new ArrayList<>();

    public static RxMagicFragment newInstance() {
        Bundle args = new Bundle();
        RxMagicFragment fragment = new RxMagicFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rxmagic, container, false);
        mBinding = FragmentRxmagicBinding.bind(view);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBinding.toolbar.setTitle(R.string.app_name);
        mBinding.toolbar.setNavigationIcon(R.drawable.ic_drawer_menu);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mBinding.toolbar);

        mAdapter = new RxExpressionAdapter(getContext());
        mAdapter.setList(mRxExpressions);
        mBinding.rv.setAdapter(mAdapter);

        mBinding.code.setTheme(CodeViewTheme.ARDUINO_LIGHT).fillColor();
        mBinding.code.showCode(getString(R.string.test_code));

        mBinding.btnAdd.setOnClickListener(v -> {
            //TODO testData
            RxOperator rxOperator = new RxOperator();
            rxOperator.setName("Just");
            rxOperator.setGroup("Creator");
            RxExpression expression = new RxExpression();
            expression.setRxOperator(rxOperator);
            mAdapter.getList().add(expression);
            mAdapter.notifyItemInserted(mAdapter.getList().size() - 1);
            if (!mBinding.btnDelete.isEnabled()) {
                mBinding.btnDelete.setEnabled(true);
            }
        });

        mBinding.btnDelete.setOnClickListener(v -> {
            Snackbar.make(mBinding.btnPreview, getString(R.string.tip_developing), Snackbar.LENGTH_SHORT)
                    .setAnchorView(mBinding.btnPreview)
                    .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_FADE)
                    .show();
        });

        mBinding.btnPreview.setOnClickListener(v -> {
            Snackbar.make(mBinding.btnPreview, getString(R.string.tip_developing), Snackbar.LENGTH_SHORT)
                    .setAnchorView(mBinding.btnPreview)
                    .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_FADE)
                    .show();
        });
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.rxmagic_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                ((MainActivity) getActivity()).openDrawer();

                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
