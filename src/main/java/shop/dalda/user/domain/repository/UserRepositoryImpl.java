package shop.dalda.user.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import shop.dalda.user.ui.dto.QUserCompanyResponse;
import shop.dalda.user.ui.dto.UserCompanyResponse;

import javax.persistence.EntityManager;


import static shop.dalda.user.domain.QUser.user;

public class UserRepositoryImpl implements UserRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public UserRepositoryImpl(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public UserCompanyResponse getCompanyByDomain(String companyDomain) {
        return queryFactory.select(new QUserCompanyResponse(
                user.companyName,
                user.companyLocation,
                user.companyPhone,
                user.companyIntroduction,
                user.businessHours,
                user.profile,
                user.qnaLink,
                user.instaLink,
                user.etcLinks
        )).from(user)
                .where(user.companyDomain.eq(companyDomain))
                .fetchOne();
    }
}
