package com.thanhtung.mockproject.ui.mypage.forgotpassword;

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
import com.thanhtung.mockproject.R;
import com.thanhtung.mockproject.api.ApiBuilder;
import com.thanhtung.mockproject.api.ApiResult;
import com.thanhtung.mockproject.databinding.FragmentForgotPasswordBinding;
import com.thanhtung.mockproject.ui.mypage.MyPageFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordFragment extends Fragment implements ForgotPasswordListener, TextWatcher, Callback<ApiResult> {
    private FragmentForgotPasswordBinding binding;
    private MyPageFragment myPageFragment;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_forgot_password
                ,container,false);
        myPageFragment = (MyPageFragment) getParentFragment();
        binding.edtEmail.addTextChangedListener(this);
        binding.setListener(this);
        return binding.getRoot();
    }

    @Override
    public void onBackPress() {
        myPageFragment.showFragment(myPageFragment.getFmLogin(),android.R.anim.slide_in_left,android.R.anim.slide_out_right);
    }

    @Override
    public void onResetPassword() {

        String email = binding.edtEmail.getText().toString();
        if (myPageFragment.getFmLogin().isEmailValid(email)){
            ApiBuilder.getInstance().reset(email).enqueue(this);
            binding.tvEmailWarning.setVisibility(View.INVISIBLE);
            binding.edtEmail.setEnabled(false);
            binding.btnReset.setEnabled(false);
        } else {
            binding.tvEmailWarning.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (binding.edtEmail.getText().length()!=0){
            binding.btnReset.setEnabled(true);
        } else {
            binding.btnReset.setEnabled(false);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    @Override
    public void onResponse(Call<ApiResult> call, Response<ApiResult> response) {
        binding.edtEmail.setEnabled(true);
        binding.btnReset.setEnabled(true);
        if (response.body()!=null){
            if(response.body().getStatus()==1){
                Toast.makeText(getContext(), "Mật khẩu đã được khôi phục, vui lòng đăng nhập!", Toast.LENGTH_SHORT).show();
                myPageFragment.showFragment(myPageFragment.getFmLogin(),android.R.anim.slide_in_left,android.R.anim.slide_out_right);
            } else {
                Toast.makeText(getContext(), "Email không thuộc bất cứ tài khoản nào!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "Lỗi hệ thống!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(Call<ApiResult> call, Throwable t) {
        Toast.makeText(getContext(), "Lỗi hệ thống", Toast.LENGTH_SHORT).show();
        binding.edtEmail.setEnabled(true);
        binding.btnReset.setEnabled(true);
    }
}
