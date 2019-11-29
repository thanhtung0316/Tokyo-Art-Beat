package com.thanhtung.mockproject.ui.mypage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.thanhtung.mockproject.MyShared;
import com.thanhtung.mockproject.R;
import com.thanhtung.mockproject.adapter.MyPagePagerAdapter;
import com.thanhtung.mockproject.databinding.FragmentMypageBinding;
import com.thanhtung.mockproject.ui.mypage.forgotpassword.ForgotPasswordFragment;
import com.thanhtung.mockproject.ui.mypage.login.LoginFragment;
import com.thanhtung.mockproject.ui.mypage.signup.SignupFragment;

import static com.thanhtung.mockproject.ui.mypage.login.LoginFragment.KEY_TOKEN;


public class MyPageFragment extends Fragment {
    private MyPagePagerAdapter pagerAdapter;
    private FragmentMypageBinding binding;
    private LoginFragment fmLogin = new LoginFragment();
    private SignupFragment fmSignup = new SignupFragment();
    private ForgotPasswordFragment fmForgot = new ForgotPasswordFragment();
    private MyPageStatusFragment fmStatus = new MyPageStatusFragment();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_mypage, container, false);
        initFragment();
        if (isLogin()){
            showFragment(fmStatus,R.anim.slide_in_right,R.anim.slide_out_left);
        } else {
            showFragment(fmSignup,R.anim.slide_in_right,R.anim.slide_out_left);
        }

        return binding.getRoot();
    }

    public void initFragment() {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.ly_frame, fmSignup);
        transaction.add(R.id.ly_frame, fmLogin);
        transaction.add(R.id.ly_frame, fmForgot);
        transaction.add(R.id.ly_frame,fmStatus);
        transaction.commit();
    }

    public void showFragment(Fragment fmShow, int in, int out) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.setCustomAnimations(in,out);
        transaction.hide(fmSignup);
        transaction.hide(fmLogin);
        transaction.hide(fmForgot);
        transaction.hide(fmStatus);
        transaction.show(fmShow);
        transaction.commit();
    }

    public boolean isLogin() {
        MyShared myShared = new MyShared(getContext());
        return !myShared.get(KEY_TOKEN).equals("");
    }

    public LoginFragment getFmLogin() {
        return fmLogin;
    }

    public SignupFragment getFmSignup() {
        return fmSignup;
    }

    public ForgotPasswordFragment getFmForgot() {
        return fmForgot;
    }

    public MyPageStatusFragment getFmStatus() {
        return fmStatus;
    }
}