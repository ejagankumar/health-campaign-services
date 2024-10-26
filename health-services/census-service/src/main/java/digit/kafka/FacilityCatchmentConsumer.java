package digit.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import digit.repository.CensusRepository;
import digit.service.CensusService;
import digit.util.CommonUtil;
import digit.web.models.Census;
import digit.web.models.CensusRequest;
import digit.web.models.CensusResponse;
import digit.web.models.plan.PlanFacilityDTO;
import digit.web.models.plan.PlanFacilityRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static digit.config.ServiceConstants.FACILITY_ID_FIELD;

@Component
@Slf4j
public class FacilityCatchmentConsumer {

    private ObjectMapper objectMapper;

    private CensusService service;

    private CommonUtil commonUtil;

    public FacilityCatchmentConsumer(ObjectMapper objectMapper, CensusService service, CommonUtil commonUtil) {
        this.objectMapper = objectMapper;
        this.service = service;
        this.commonUtil = commonUtil;
    }

    @KafkaListener(topics = {"${plan.facility.update.topic}"})
    public void listen(Map<String, Object> consumerRecord, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        try {
            PlanFacilityRequestDTO planFacilityRequestDTO = objectMapper.convertValue(consumerRecord, PlanFacilityRequestDTO.class);
            PlanFacilityDTO planFacilityDTO = planFacilityRequestDTO.getPlanFacilityDTO();

            CensusResponse censusResponse = service.search(commonUtil.getCensusSearchRequest(planFacilityDTO.getTenantId(), planFacilityDTO.getPlanConfigurationId(), planFacilityDTO.getServiceBoundaries(), planFacilityDTO.getInitiallySetServiceBoundaries(), planFacilityRequestDTO.getRequestInfo()));
            List<Census> censusFromSearch = censusResponse.getCensus();

            String facilityId = planFacilityRequestDTO.getPlanFacilityDTO().getFacilityId();

            Set<String> boundariesWithFacility = new HashSet<>(List.of(planFacilityDTO.getServiceBoundaries().split(",")));
            Set<String> boundariesWithNoFacility = new HashSet<>(planFacilityDTO.getInitiallySetServiceBoundaries());

            // Unassigning facilities to the boundaries which were initially assigned that facility
            censusFromSearch.stream()
                    .filter(census -> boundariesWithNoFacility.contains(census.getBoundaryCode()))
                    .forEach(census -> {
                        census.setAdditionalDetails(commonUtil.removeFieldFromAdditionalDetails(census.getAdditionalDetails(), FACILITY_ID_FIELD));
                        census.setFacilityAssigned(Boolean.FALSE);
                        census.setPartnerAssignmentValidationEnabled(Boolean.FALSE);
                        service.update(CensusRequest.builder().requestInfo(planFacilityRequestDTO.getRequestInfo()).census(census).build());
                    });

            // Assigning facilities to the boundaries in the update request.
            censusFromSearch.stream()
                    .filter(census -> boundariesWithFacility.contains(census.getBoundaryCode()))
                    .forEach(census -> {
                        census.setAdditionalDetails(commonUtil.updateFieldInAdditionalDetails(census.getAdditionalDetails(), FACILITY_ID_FIELD, facilityId));
                        census.setFacilityAssigned(Boolean.TRUE);
                        census.setPartnerAssignmentValidationEnabled(Boolean.FALSE);
                        service.update(CensusRequest.builder().requestInfo(planFacilityRequestDTO.getRequestInfo()).census(census).build());
                    });

        } catch (Exception exception) {
            log.error("Error in census consumer", exception);
        }
    }
}
