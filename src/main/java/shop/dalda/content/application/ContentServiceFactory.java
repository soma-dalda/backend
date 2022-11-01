package shop.dalda.content.application;

import shop.dalda.content.domain.Content;
import shop.dalda.content.domain.ObjectiveContent;
import shop.dalda.content.domain.SubjectiveContent;
import shop.dalda.exception.template.TemplateInvalidException;

public class ContentServiceFactory {

    private static final ObjectiveContentService objectiveContentService = new ObjectiveContentService();
    private static final SubjectiveContentService subjectiveContentService = new SubjectiveContentService();

    public static ContentService contentServiceFactory(Content content) {
        if (content.getClass() == ObjectiveContent.class) {
            return objectiveContentService;
        } else if (content.getClass() == SubjectiveContent.class) {
            return subjectiveContentService;
        } else {
            throw new TemplateInvalidException();
        }
    }
}
