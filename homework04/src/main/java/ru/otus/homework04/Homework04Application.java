package ru.otus.homework04;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;
import ru.otus.homework04.domain.Defendant;
import ru.otus.homework04.domain.Judgment;

@IntegrationComponentScan
@ComponentScan
@Configuration
@EnableIntegration
public class Homework04Application {

    private static final String[] LIST_OF_DEFENDANTS = {"bad guy", "good guy"};

    @Bean
    public QueueChannel defendantsChannel() {
        return MessageChannels.queue(10).get();
    }

    @Bean
    public PublishSubscribeChannel judgmentChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller() {
        return Pollers.fixedRate(100).maxMessagesPerPoll(2).get();
    }

    @Bean
    public IntegrationFlow cafeFlow() {
        return IntegrationFlows.from("defendantsChannel")
                .handle("tribunalService", "sentence")
                .<Judgment, Judgment>transform(Judgment -> new Judgment(transformToRealJudgment(Judgment.getJudgment())))
                .channel("judgmentChannel")
                .get();
    }

    public static void main(String[] args) throws Exception {
        AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(Homework04Application.class);

        Tribunal tribunal = ctx.getBean(Tribunal.class);

        while (true) {
            Thread.sleep(1000);

            Defendant defendant = generateOrderItem();
            System.out.println("New defendant: " + defendant.getDefendantName());
            Judgment judgment = tribunal.process(defendant);
            System.out.println("Judgment for: " + defendant.getDefendantName() + " is " + judgment.getJudgment());
        }
    }

    private static Defendant generateOrderItem() {
        return new Defendant(LIST_OF_DEFENDANTS[RandomUtils.nextInt(0, LIST_OF_DEFENDANTS.length)]);
    }

    private String transformToRealJudgment(String judgment) {
        String realJudgment = "guilty";
        if (judgment.equals("good guy")) {
            realJudgment = "not guilty";
        }
        return realJudgment;
    }
}
