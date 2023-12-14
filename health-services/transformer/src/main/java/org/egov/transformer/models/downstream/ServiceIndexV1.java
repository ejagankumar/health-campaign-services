package org.egov.transformer.models.downstream;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import digit.models.coremodels.AuditDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.transformer.models.upstream.AttributeValue;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ServiceIndexV1 {
    @JsonProperty("id")
    private String id;
    @JsonProperty("createdTime")
    private Long createdTime;
    @JsonProperty("createdBy")
    private String createdBy;
    @JsonProperty("supervisorLevel")
    private String supervisorLevel;
    @JsonProperty("checklistName")
    private String checklistName;
    @JsonProperty("projectId")
    private String projectId;
    @JsonProperty("serviceDefinitionId")
    private String serviceDefinitionId;
    @JsonProperty("userName")
    private String userName;
    @JsonProperty("role")
    private String role;
    @JsonProperty("province")
    private String province;
    @JsonProperty("district")
    private String district;
    @JsonProperty("administrativeProvince")
    private String administrativeProvince;
    @JsonProperty("locality")
    private String locality;
    @JsonProperty("village")
    private String village;
    @JsonProperty("county")
    private String county;
    @JsonProperty("community")
    private String community;
    @JsonProperty("tenantId")
    private String tenantId;
    @JsonProperty("userId")
    private String userId;
    @JsonProperty("attributes")
    private List<AttributeValue> attributes = new ArrayList<>();
    @JsonProperty("clientReferenceId")
    private String clientReferenceId;
    @JsonProperty("syncedTimeStamp")
    private String syncedTimeStamp;
    @JsonProperty("syncedTime")
    private Long syncedTime;
}
