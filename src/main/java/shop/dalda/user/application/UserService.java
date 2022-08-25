package shop.dalda.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.dalda.user.domain.User;
import shop.dalda.user.domain.repository.UserRepository;

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
}
