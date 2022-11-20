package shop.dalda.template.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import shop.dalda.order.domain.Order;
import shop.dalda.order.ui.dto.response.OrderUpdateResponseDto;
import shop.dalda.template.domain.Template;
import shop.dalda.template.ui.dto.response.TemplateResponseDto;
import shop.dalda.template.ui.dto.response.TemplateUpdateResponseDto;

@Mapper
public interface TemplateMapper {

    TemplateMapper INSTANCE = Mappers.getMapper(TemplateMapper.class);

    @Mapping(source = "user.id", target = "userId")
    TemplateResponseDto template2Dto(Template template);

    @Mapping(source = "user.id", target = "userId")
    TemplateUpdateResponseDto template2UpdateDto(Template template);
}

