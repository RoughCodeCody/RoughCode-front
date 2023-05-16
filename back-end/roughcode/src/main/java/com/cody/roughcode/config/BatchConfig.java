package com.cody.roughcode.config;

import com.cody.roughcode.alarm.entity.Alarm;
import com.cody.roughcode.alarm.repository.AlarmRepository;
import com.cody.roughcode.alarm.service.AlarmServiceImpl;
import com.cody.roughcode.code.service.CodesServiceImpl;
import com.cody.roughcode.project.service.ProjectsServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final AlarmServiceImpl alarmService;
    private final CodesServiceImpl codesService;
    private final ProjectsServiceImpl projectsService;

    @Bean
    public Job job(){
        return jobBuilderFactory.get("job")
                .start(stepAlarm())
                .next(stepProject())
                .next(stepCode())
                .build();
    }

    @Bean
    public Step stepAlarm() {
        return stepBuilderFactory.get("stepAlarm")
                .tasklet((contribution, chunkContext) -> {
                    log.info("Step! >>>>> Alarm delete");
                    alarmService.deleteLimited();
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step stepProject() {
        return stepBuilderFactory.get("stepProject")
                .tasklet((contribution, chunkContext) -> {
                    log.info("Step! >>>>> Project delete");
                    projectsService.deleteExpiredProject();
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step stepCode() {
        return stepBuilderFactory.get("stepCode")
                .tasklet((contribution, chunkContext) -> {
                    log.info("Step! >>>>> Code delete");
                    codesService.deleteExpiredCode();
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
