package shop.dalda.user.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.dalda.exception.user.auth.UserNotFoundException;
import shop.dalda.exception.user.company.DomainNotFoundException;
import shop.dalda.exception.user.company.InvalidDomainException;
import shop.dalda.security.auth.user.CustomOAuth2User;
import shop.dalda.user.domain.User;
import shop.dalda.user.domain.repository.UserRepository;
import shop.dalda.user.ui.dto.*;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserAuthResponse getUserAuthById(Long id) {
        return userRepository.getUserAuthById(id).orElseThrow(UserNotFoundException::new);
    }
    @Transactional
    public void saveOrUpdate(User user) {
        // 최근 로그인 시간 갱신
        user.setLatestAt();
        userRepository.save(user);
    }

    @Transactional
    public void updateUser(CustomOAuth2User currentUser, UserUpdateRequest requestDto) {
        User findUser = userRepository.findById(currentUser.getId()).orElseThrow(UserNotFoundException::new);
        findUser.updateUserInfo(requestDto);
        userRepository.save(findUser);
    }

    @Transactional
    public void updateUserProfileImage(CustomOAuth2User currentUser, UserProfileImageRequest requestDto) {
        User findUser = userRepository.findById(currentUser.getId()).orElseThrow(UserNotFoundException::new);
        findUser.updateProfile(requestDto);
        userRepository.save(findUser);
    }

    @Transactional
    public void saveOrUpdateCompany(CustomOAuth2User user, UserCompanyRequest requestDto) throws JsonProcessingException, DataIntegrityViolationException {
        User findUser = userRepository.findById(user.getId()).orElseThrow(UserNotFoundException::new);
        findUser.setCompany(requestDto);
        userRepository.save(findUser);
    }

    public UserCompanyResponse getCompanyPage(String companyDomain) {
        if (companyDomain.equals("도메인")) {
            throw new InvalidDomainException();
        }
        return userRepository.getCompanyByDomain(companyDomain).orElseThrow(DomainNotFoundException::new);
    }

    public List<UserCompanyListResponse> getCompanyList() {
        return userRepository.getCompanyListByRecentLogin();
    }
}
