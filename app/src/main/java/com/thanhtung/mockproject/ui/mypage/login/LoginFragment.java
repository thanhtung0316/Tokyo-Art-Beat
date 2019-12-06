package com.thanhtung.mockproject.ui.mypage.login;

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
import com.thanhtung.mockproject.databinding.FragmentMypageLoginBinding;
import com.thanhtung.mockproject.ui.mypage.MyPageFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment implements LoginListener, TextWatcher, Callback<ApiResult> {
    private FragmentMypageLoginBinding binding;
    private MyPageFragment myPageFragment;
    public static final String KEY_TOKEN = "KEY_TOKEN";
    private MyShared myShared;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_mypage_login
                ,container,false);
        myShared = new MyShared(getContext());
        binding.setListener(this);
        binding.edtEmail.addTextChangedListener(this);
        binding.edtPassword.addTextChangedListener(this);
        myPageFragment = (MyPageFragment) getParentFragment();
        return binding.getRoot();
    }


    public void setData(String email, String password){
        binding.edtEmail.setText(email);
        binding.edtPassword.setText(password);
    }


    @Override
    public void onbackPress() {
        myPageFragment.showFragment(myPageFragment.getFmSignup(),android.R.anim.slide_in_left,android.R.anim.slide_out_right);
        }
    @Override
    public void onforgotPassword() {
        myPageFragment.showFragment(myPageFragment.getFmForgot(),R.anim.slide_in_right,R.anim.slide_out_left);
    }

    @Override
    public void onButtonClicked() {
        if (isEmailValid(binding.edtEmail.getText())){
            ApiBuilder.getInstance().login(binding.edtEmail.getText().toString()
                    ,binding.edtPassword.getText().toString()).enqueue(this);
        } else {
            binding.tvEmailAlert.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (binding.edtEmail.getText().length()!=0&&binding.edtPassword.getText().length()!=0){
            binding.btnSignup.setEnabled(true);
        } else {
            binding.btnSignup.setEnabled(false);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
    public boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    public void onResponse(Call<ApiResult> call, Response<ApiResult> response) {
        if (response.body()!=null){
            if (response.body().getStatus()!=0){
                Toast.makeText(getContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                String token = response.body().getResponse().getToken();
                myShared.put(KEY_TOKEN,token);
                myPageFragment.showFragment(myPageFragment.getFmStatus(),R.anim.slide_in_right,R.anim.slide_out_left);
            } else {
                Toast.makeText(getContext(), "Đăng nhập thất bại! "+response.body().getError_code()+" "
                        +response.body().getError_message() , Toast.LENGTH_SHORT).show();
            }

        }else {
            Toast.makeText(getContext(), "System Error", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onFailure(Call<ApiResult> call, Throwable t) {
        Toast.makeText(getContext(), "Faill", Toast.LENGTH_SHORT).show();
    }
}
