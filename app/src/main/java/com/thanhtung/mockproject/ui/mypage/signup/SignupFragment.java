package com.thanhtung.mockproject.ui.mypage.signup;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.thanhtung.mockproject.MyShared;
import com.thanhtung.mockproject.R;
import com.thanhtung.mockproject.api.ApiBuilder;
import com.thanhtung.mockproject.api.ApiResult;
import com.thanhtung.mockproject.databinding.FragmentMypageSignupBinding;
import com.thanhtung.mockproject.ui.mypage.MyPageFragment;
import com.thanhtung.mockproject.ui.mypage.login.LoginFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupFragment extends Fragment implements View.OnClickListener, TextWatcher, Callback<ApiResult> {
    private FragmentMypageSignupBinding binding;
    private MyPageFragment myPageFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_mypage_signup, container, false);
        binding.btnSignup.setOnClickListener(this);
        binding.tvAlreadyAccount.setOnClickListener(this);
        binding.edtFullName.addTextChangedListener(this);
        binding.edtEmail.addTextChangedListener(this);
        binding.edtPassword.addTextChangedListener(this);
        return binding.getRoot();
    }

    private boolean checkInfo() {
        if (!isEmailValid(binding.edtEmail.getText())) {
            binding.tvEmailWarning.setVisibility(View.VISIBLE);
            return false;
        }
        else if (binding.edtPassword.getText().length() < 6 || binding.edtPassword.getText().length() > 16) {
            binding.tvPassWarning.setVisibility(View.VISIBLE);
            return false;
        }
        else return !isEmpty();
    }

    private boolean isEmpty() {
        return binding.edtFullName.getText().length() == 0 || binding.edtEmail
                .getText().length() == 0 || binding.edtPassword.getText().length() == 0;
    }

    private boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    public void onClick(View v) {
        myPageFragment = (MyPageFragment) getParentFragment();
        switch (v.getId()) {
            case R.id.btn_signup:
                if (checkInfo()) {
                    ApiBuilder.getInstance().register(binding.edtFullName.getText().toString()
                            , binding.edtEmail.getText().toString()
                            , binding.edtPassword.getText().toString()).enqueue(this);
                }
                break;
            case R.id.tv_already_account:
                myPageFragment.showFragment(myPageFragment.getFmLogin(),R.anim.slide_in_right,R.anim.slide_out_left);
                break;
        }


    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (isEmpty()) {
            binding.btnSignup.setEnabled(false);
        } else {
            binding.btnSignup.setEnabled(true);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onResponse(Call<ApiResult> call, Response<ApiResult> response) {
        if (response.body()!= null) {
            if (response.body().getStatus()!=0){
                myPageFragment.showFragment(myPageFragment.getFmLogin(),R.anim.slide_in_right,R.anim.slide_out_left);
                Toast.makeText(getContext(), "Đăng ký thành công, Vui lòng đăng nhập", Toast.LENGTH_SHORT).show();
                myPageFragment.getFmLogin().setData(binding.edtEmail.getText().toString()
                        , binding.edtPassword.getText().toString());
            } else {
                Toast.makeText(getContext(), "Đăng kí thất bại, Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
            }


        } else {
            Toast.makeText(getContext(), "Lỗi hệ thống", Toast.LENGTH_SHORT).show();

        }


    }

    @Override
    public void onFailure(Call<ApiResult> call, Throwable t) {
        Toast.makeText(getContext(), "Đăng kí thất bại!", Toast.LENGTH_SHORT).show();
    }

}
