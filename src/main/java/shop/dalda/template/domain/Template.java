package shop.dalda.template.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.dalda.template.domain.content.Content;
import shop.dalda.template.domain.converter.JPAJsonConverter;
import shop.dalda.user.domain.BaseTimeEntity;
import shop.dalda.user.domain.User;

import javax.persistence.*;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "templates")
public class Template extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private User user;

    @Column
    private String title;

    @Convert(converter = JPAJsonConverter.class)
    @Column(columnDefinition = "json", name = "content")
    private List<Content> contentList;

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateContent(List<Content> contentList) {
        this.contentList = contentList;
    }

    @Override
    public String toString() {
        return "Template{" +
                "id=" + id +
                ", user=" + user +
                ", contentList='" + contentList + '\'' +
                '}';
    }
}
