package com.kunminx.rxmagic.ui;
/*
 * Copyright (c) 2018-2019. KunMinX
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.kunminx.rxmagic.R;
import com.kunminx.rxmagic.databinding.FragmentRxGuideBinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

/**
 * Create by KunMinX at 19/4/22
 */
public class RxGuideFragment extends Fragment {


    private FragmentRxGuideBinding mBinding;

    public static RxGuideFragment newInstance() {
        Bundle args = new Bundle();
        RxGuideFragment fragment = new RxGuideFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rx_guide, container, false);
        mBinding = FragmentRxGuideBinding.bind(view);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBinding.toolbar.setTitle(R.string.guide);
        mBinding.toolbar.setNavigationIcon(R.drawable.ic_drawer_menu);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mBinding.toolbar);

        mBinding.webView.getSettings().setUseWideViewPort(true);
//        mBinding.webView.getSettings().setJavaScriptEnabled(true);
        mBinding.webView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        mBinding.webView.loadUrl("https://github.com/KunMinX/RxJava2-Operators-Sample/blob/master/README.md");
        mBinding.webView.setWebChromeClient(new WebChromeClientProgress());

        mBinding.btnGot.setOnClickListener(v -> {
            Snackbar.make(mBinding.btnGot, getString(R.string.tip_developing), Snackbar.LENGTH_SHORT)
                    .setAnchorView(mBinding.btnGot)
                    .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_FADE)
                    .show();
        });
    }

    private class WebChromeClientProgress extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int progress) {
            mBinding.progress.setProgress(progress);
            if (progress == 100) {
                mBinding.progress.setVisibility(View.GONE);
            }
            super.onProgressChanged(view, progress);
        }
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
