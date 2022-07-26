package shop.dalda.user.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import shop.dalda.user.domain.Role;
import shop.dalda.user.ui.dto.*;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static shop.dalda.user.domain.QUser.user;

public class UserRepositoryImpl implements UserRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public UserRepositoryImpl(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Optional<UserAuthResponse> getUserAuthById(Long id) {
        return Optional.ofNullable(queryFactory.select(new QUserAuthResponse(
                user.id,
                user.username,
                user.userPhone,
                user.role,
                user.companyName,
                user.companyIntroduction,
                user.companyLocation,
                user.companyDomain,
                user.businessHours,
                user.qnaLink,
                user.instaLink,
                user.etcLinks
        ))
                .from(user)
                .where(user.id.eq(id))
                .fetchOne());
    }

    @Override
    public Optional<UserCompanyResponse> getCompanyByDomain(String companyDomain) {
        return Optional.ofNullable(queryFactory.select(new QUserCompanyResponse(
                user.id,
                user.companyName,
                user.companyLocation,
                user.companyPhone,
                user.companyIntroduction,
                user.businessHours,
                user.profileImage,
                user.qnaLink,
                user.instaLink,
                user.etcLinks
        ))
                .from(user)
                .where(user.companyDomain.eq(companyDomain))
                .fetchOne());
    }

    @Override
    public List<UserCompanyListResponse> getCompanyListByRecentLogin() {
        return queryFactory.select(new QUserCompanyListResponse(
                user.companyName,
                user.companyDomain,
                user.profileImage
        ))
                .from(user)
                .where(user.role.eq(Role.ROLE_COMPANY))
                .orderBy(user.latestAt.desc())
                .limit(10)
                .fetch();
    }
}
