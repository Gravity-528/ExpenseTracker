package com.Project.ExpenseTracker.service;

import com.Project.ExpenseTracker.entities.UserInfo;
import com.Project.ExpenseTracker.models.UserInfoDto;
import com.Project.ExpenseTracker.repository.UserRepository;
import com.Project.ExpenseTracker.util.VailidationUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;

@Component
@AllArgsConstructor
@Data
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        UserInfo user= userRepository.findByUsername(username);
        if(user==null){
            throw new UsernameNotFoundException("could not find the user");
        }
        return new CustomUserDetails(user);
    }

    public UserInfo checkIfUserAlreadyExist(UserInfoDto userInfoDto){
        return userRepository.findByUsername(userInfoDto.getUserName());
    }

    public Boolean signupUser(UserInfoDto userInfoDto){
        Boolean check=VailidationUser.checkPassword(userInfoDto.getPassword());
        if(!check){
//            throw new Exception("password is not valid give a suitable password of atleast 8 numbers");
            return false;
        }
        userInfoDto.setPassword(passwordEncoder.encode(userInfoDto.getPassword()));
        if(Objects.nonNull(checkIfUserAlreadyExist(userInfoDto))){
            return false;
        }
        String userId= UUID.randomUUID().toString();
        userRepository.save(new UserInfo(userId,userInfoDto.getUserName(),userInfoDto.getPassword(),new HashSet<>()));
        return true;
    }
}
