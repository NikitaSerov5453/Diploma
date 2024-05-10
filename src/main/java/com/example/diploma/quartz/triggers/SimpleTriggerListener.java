package com.example.diploma.quartz.triggers;

import com.example.diploma.dto.AddresseeDto;
import com.example.diploma.dto.ReportDto;
import com.example.diploma.dto.SQLAuthorisationDto;
import com.example.diploma.dto.SQLRequestDto;
import com.example.diploma.mapper.ReportMapper;
import com.example.diploma.quartz.schedule.MailScheduleService;
import com.example.diploma.repository.ReportRepository;
import com.example.diploma.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.TriggerListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class SimpleTriggerListener implements TriggerListener {

    private final MailScheduleService mailScheduleService;


    @Override
    public String getName() {
        return SimpleTriggerListener.class.getSimpleName();
    }

    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext jobExecutionContext) {
//        String id = trigger.getKey().getName();
//
//        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
//        ReportDto reportDto = (ReportDto) jobDataMap.get(id);

        List<AddresseeDto> addresseeDtoList = new ArrayList<>();
        addresseeDtoList.add(AddresseeDto.builder().email("dante5453@gmail.com").build());
        addresseeDtoList.add(AddresseeDto.builder().email("dante5453@mail.ru").build());

        List<SQLRequestDto> sqlRequestDtoList = new ArrayList<>();
        sqlRequestDtoList.add(SQLRequestDto.builder()
                .request("SELECT * FROM Addresses")
                .build());

        List<SQLAuthorisationDto> sqlAuthorisationDtoList = new ArrayList<>();
        sqlAuthorisationDtoList.add(SQLAuthorisationDto.builder()
                .url("jdbc:postgresql://localhost:5432/postgres")
                .login("postgres")
                .password("")
                .sqlRequests(sqlRequestDtoList)
                .build());

        ReportDto reportDto = ReportDto.builder()
                .name("name")
                .cronExpression("0 * * * * ?")
                .addresses(addresseeDtoList)
                .sqlAuthorisations(sqlAuthorisationDtoList)
                .build();

        mailScheduleService.updateReportDto(reportDto);
    }

    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext jobExecutionContext) {
        return false;
    }

    @Override
    public void triggerMisfired(Trigger trigger) {

    }

    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext jobExecutionContext, Trigger.CompletedExecutionInstruction completedExecutionInstruction) {

    }
}
