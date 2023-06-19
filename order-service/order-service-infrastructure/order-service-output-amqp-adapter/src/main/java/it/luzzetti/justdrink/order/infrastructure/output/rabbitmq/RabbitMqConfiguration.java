package it.luzzetti.justdrink.order.infrastructure.output.rabbitmq;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RabbitMqConfiguration {

  /***
   * Sarebbe da fixare il converter, per usare il simpleName della classe, anziché il fullyQualified.
   * Così facendo, non dovrei creare un Listener per classe
   * https://stackoverflow.com/questions/64233253/tell-jackson2jsonmessageconverter-to-use-my-own-class-in-amqp-inboundadapter
   */

  @Bean
  public MessageConverter messageConverter() {
    return new Jackson2JsonMessageConverter();
  }
}
