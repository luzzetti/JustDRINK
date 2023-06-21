package it.luzzetti.justdrink.order.infrastructure.output.rabbitmq;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/***
 *
 * https://reflectoring.io/amqp-request-response/
 * https://hevodata.com/learn/spring-message-queue/#Create-a-RabbitMQ-Message-Receiver
 */

@Configuration
@RequiredArgsConstructor
public class RabbitMqConfiguration {

  /***
   * Sarebbe da fixare il converter, per usare il simpleName della classe, anziché il fullyQualified.
   * Così facendo, non dovrei creare un Listener per classe
   * https://stackoverflow.com/questions/64233253/tell-jackson2jsonmessageconverter-to-use-my-own-class-in-amqp-inboundadapter
   * https://stackoverflow.com/questions/70956462/spring-amqp-detect-similar-payload-types-to-use-in-rabbithandler
   * https://stackoverflow.com/questions/38563283/spring-amqp-rabbitlistener-runtime-type-problems
   * https://stackoverflow.com/questions/36447519/how-to-set-typeidpropertyname-in-mappingjackson2messageconverter
   */

  @Bean
  public MessageConverter messageConverter() {
    return new Jackson2JsonMessageConverter();
  }

}
