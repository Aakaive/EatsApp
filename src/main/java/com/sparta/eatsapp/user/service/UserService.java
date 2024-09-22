package com.sparta.eatsapp.user.service;

import com.sparta.eatsapp.address.entity.Address;
import com.sparta.eatsapp.address.repository.AddressRepository;
import com.sparta.eatsapp.auth.dto.AuthUser;
import com.sparta.eatsapp.user.dto.request.UserPatchRequest;
import com.sparta.eatsapp.user.dto.response.UserResponse;
import com.sparta.eatsapp.user.entity.User;
import com.sparta.eatsapp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final AddressRepository addressRepository;

  public UserResponse getUser(Long userid) {
    User user = userRepository.findById(userid)
        .orElseThrow(() -> new IllegalArgumentException("등록된 유저가 없습니다."));
    isDeleted(user);
    return new UserResponse(user);
  }

  public UserResponse updateUser(AuthUser authUser, Long userid,
      UserPatchRequest userPatchRequest) {
    User user = userRepository.findById(userid)
        .orElseThrow(() -> new IllegalArgumentException("등록되지 않은 유저입니다."));
    isDeleted(user);
    if (!authUser.getId().equals(userid)) {
      throw new IllegalArgumentException("권한이 없습니다.");
    }

    if (!user.getAddresses().containsKey(userPatchRequest.getLocation())) {
      Address address = new Address(userPatchRequest.getAddress(), userPatchRequest.getLocation());
      addressRepository.save(address);
      user.addAddresses(address);
      user.updateNickname(userPatchRequest.getNickname());

      User saveUser = userRepository.save(user);
      return new UserResponse(user);
    }
    user.update(userPatchRequest.getAddress(), userPatchRequest.getNickname(),
        userPatchRequest.getLocation());
    User saveUser = userRepository.save(user);
    return new UserResponse(user);
  }

  public Long deleteUser(Long userid, AuthUser authUser) {
    User user = userRepository.findById(userid)
        .orElseThrow(() -> new IllegalArgumentException("등록되지 않은 유저입니다."));
    if (!user.getId().equals(authUser.getId())) {
      throw new IllegalArgumentException("권한이 없습니다.");
    }
    isDeleted(user);
    user.setDeleted(true);
    User saveUser = userRepository.save(user);
    return saveUser.getId();
  }

  public void isDeleted(User user) {
    if (user.getDeleted() != false) {
      throw new IllegalArgumentException("삭제된 유저입니다.");
    }
  }

}
