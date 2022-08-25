package shop.dalda.user.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -293060468L;

    public static final QUser user = new QUser("user");

    public final QBaseTimeEntity _super = new QBaseTimeEntity(this);

    public final ListPath<BusinessHour, SimplePath<BusinessHour>> businessHours = this.<BusinessHour, SimplePath<BusinessHour>>createList("businessHours", BusinessHour.class, SimplePath.class, PathInits.DIRECT2);

    public final StringPath companyDomain = createString("companyDomain");

    public final StringPath companyIntroduction = createString("companyIntroduction");

    public final StringPath companyLocation = createString("companyLocation");

    public final StringPath companyName = createString("companyName");

    public final StringPath companyPhone = createString("companyPhone");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final ListPath<CompanyLink, SimplePath<CompanyLink>> etcLinks = this.<CompanyLink, SimplePath<CompanyLink>>createList("etcLinks", CompanyLink.class, SimplePath.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath instaLink = createString("instaLink");

    public final DatePath<java.time.LocalDate> latestAt = createDate("latestAt", java.time.LocalDate.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final StringPath oauthId = createString("oauthId");

    public final StringPath profile = createString("profile");

    public final StringPath qnaLink = createString("qnaLink");

    public final EnumPath<Role> role = createEnum("role", Role.class);

    public final StringPath username = createString("username");

    public final StringPath userPhone = createString("userPhone");

    public final BooleanPath withdraw = createBoolean("withdraw");

    public final DatePath<java.time.LocalDate> withdrawAt = createDate("withdrawAt", java.time.LocalDate.class);

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

