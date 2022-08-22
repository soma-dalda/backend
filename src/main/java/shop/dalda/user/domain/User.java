package shop.dalda.user.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class User extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String oauthId;

    private String companyName;

    private String username;

    @Column(length = 11)
    private String phone;

    private String profile;

    private String qnaLink;

    @Convert(converter = StringListConverter.class)
    @Column(columnDefinition = "json")
    private List<String> urlList;

    private LocalDate latestAt;

    private boolean withdraw;

    private LocalDate withdrawAt;

    @Builder
    public User(String oauthId, String username) {
        this.oauthId = oauthId;
        this.username = username;
        this.role = Role.GUEST;
        this.latestAt = LocalDate.now();
    }

    public void setLatestAt() {
        this.latestAt = LocalDate.now();
    }
}
