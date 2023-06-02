package com.blubank.doctorappointment.service;

import com.blubank.doctorappointment.dto.UserDto;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
public class UserValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return UserDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserDto user = (UserDto) target;

        if(user.getPassword().length() <6){
            errors.rejectValue("password","Length", "Password must be at least 6 characters");
            errors.rejectValue("message","Length", "حداقل تعداد کاراکتر ها باید 6 باشد .");
        }

        if(!user.getPassword().equals(user.getConfirmPassword())){
            errors.rejectValue("confirmPassword","Match", "Passwords must match");
            errors.rejectValue("message","Match", "تکرار پسوورد مغایرت دارد .");
        }

        //confirmPassword



    }
}
