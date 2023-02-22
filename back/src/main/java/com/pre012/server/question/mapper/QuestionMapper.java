package com.pre012.server.question.mapper;

import com.pre012.server.question.dto.QuestionDto;
import com.pre012.server.question.entity.Question;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

    Question questionPostToQuestion(QuestionDto.Post requestBody);
    Question questionPatchToQuestion(QuestionDto.Patch requestBody);

    QuestionDto.Response questionToQuestionResponse(Question question);
    List<QuestionDto.Response> questionsToQuestionResponses(List<Question> questions);


//    default Order orderPostDtoToOrder(OrderPostDto orderPostDto) {
//        Order order = new Order();
//        Member member = new Member();
//        member.setMemberId(orderPostDto.getMemberId());
//
//        List<OrderCoffee> orderCoffees = orderPostDto.getOrderCoffees().stream()
//                .map(orderCoffeeDto -> {
//                    OrderCoffee orderCoffee = new OrderCoffee();
//                    Coffee coffee = new Coffee();
//                    coffee.setCoffeeId(orderCoffeeDto.getCoffeeId());
//                    orderCoffee.addOrder(order);
//                    orderCoffee.addCoffee(coffee);
//                    orderCoffee.setQuantity(orderCoffeeDto.getQuantity());
//                    return orderCoffee;
//                }).collect(Collectors.toList());
//        order.setMember(member);
//        order.setOrderCoffees(orderCoffees);
//
//        return order;
//    }
}
