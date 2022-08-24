package shop.dalda.template;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "templates")
public class Template {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private User user;

    @Column(columnDefinition = "json")
    private String content;

    @Column
    private LocalDateTime createdAt;

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void updateContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Template{" +
                "id=" + id +
                ", user=" + user +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
