package shop.dalda.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.dalda.user.domain.User;
import shop.dalda.user.domain.repository.UserRepository;
import shop.dalda.user.ui.dto.UserCompanyRequest;
import shop.dalda.user.ui.dto.UserCompanyResponse;
import shop.dalda.user.ui.dto.UserUpdateRequest;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void saveOrUpdate(User user) {
        // 최근 로그인 시간 갱신
        user.setLatestAt();
        userRepository.save(user);
    }

    @Transactional
    public void updateUser(Long userId, UserUpdateRequest requestDto) throws Exception {
        User findUser = userRepository.findById(userId).orElseThrow(() -> new Exception("존재하지 않는 유저입니다."));
        findUser.updateUserInfo(requestDto);
        userRepository.save(findUser);
    }

    @Transactional
    public void saveOrUpdateCompany(Long userId, UserCompanyRequest requestDto) throws Exception {
        User findUser = userRepository.findById(userId).orElseThrow(() -> new Exception("존재하지않는 유저입니다"));
        findUser.setCompany(requestDto);
        userRepository.save(findUser);
    }

    public UserCompanyResponse getCompanyPage(String companyDomain) {
        return userRepository.getCompanyByDomain(companyDomain);
    }
}
