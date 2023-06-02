package com.blubank.doctorappointment.service;

import com.blubank.doctorappointment.dao.meeting.MeetingRepository;
import com.blubank.doctorappointment.dao.user.UserRepository;
import com.blubank.doctorappointment.dto.MeetingDtoManager;
import com.blubank.doctorappointment.dto.UserDto;
import com.blubank.doctorappointment.exception.CustomException;
import com.blubank.doctorappointment.model.User;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private MeetingDtoManager meetingDtoManager = Mappers.getMapper(MeetingDtoManager.class);


    @Transactional
    public Long save(UserDto userDto){
        if(userRepository.findUserByMobile(userDto.getMobile()).isPresent())
            throw new CustomException("کاربر در سیستم موجود می باشد .");
        else {
            Long userId = userRepository.save(meetingDtoManager.transferDtoToUserEntity(userDto)).getId();
            return userId;
        }

    }

    public UserDto findUserByMobile(String mobile) {
      UserDto userDto = new UserDto();
      Optional<User> user = userRepository.findUserByMobile(mobile);
      if(user.isPresent()){
          userDto = meetingDtoManager.transferUserToDto(user.get());
          return userDto;
      }
      else{
          throw new CustomException("کاربر یافت نشد .");
      }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findUserByMobile(username);
    }
}
