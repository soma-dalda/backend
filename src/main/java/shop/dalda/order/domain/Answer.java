package shop.dalda.order.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashSet;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Answer {
    String question;
    LinkedHashSet<String> answer;
}
