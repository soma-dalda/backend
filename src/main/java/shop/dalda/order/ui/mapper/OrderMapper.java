package shop.dalda.order.ui.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import shop.dalda.order.domain.Order;
import shop.dalda.order.ui.dto.response.OrderUpdateResponseDto;

@Mapper
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(source = "company.id", target = "companyId")
    @Mapping(source = "consumer.id", target = "consumerId")
    @Mapping(source = "template.id", target = "templateId")
    OrderUpdateResponseDto orderToDto(Order order);
}
