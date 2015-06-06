/*******************************************************************************
 * eGov suite of products aim to improve the internal efficiency,transparency, 
 *    accountability and the service delivery of the government  organizations.
 * 
 *     Copyright (C) <2015>  eGovernments Foundation
 * 
 *     The updated version of eGov suite of products as by eGovernments Foundation 
 *     is available at http://www.egovernments.org
 * 
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     any later version.
 * 
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 * 
 *     You should have received a copy of the GNU General Public License
 *     along with this program. If not, see http://www.gnu.org/licenses/ or 
 *     http://www.gnu.org/licenses/gpl.html .
 * 
 *     In addition to the terms of the GPL license to be adhered to in using this
 *     program, the following additional terms are to be complied with:
 * 
 * 	1) All versions of this program, verbatim or modified must carry this 
 * 	   Legal Notice.
 * 
 * 	2) Any misrepresentation of the origin of the material is prohibited. It 
 * 	   is required that all modified versions of this material be marked in 
 * 	   reasonable ways as different from the original version.
 * 
 * 	3) This license does not grant any rights to any user of the program 
 * 	   with regards to rights under trademark law for use of the trade names 
 * 	   or trademarks of eGovernments Foundation.
 * 
 *   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org
 ******************************************************************************/
/*
 * PropertyStatusValues.java Created on Oct 20, 2005
 *
 * Copyright 2005 eGovernments Foundation. All rights reserved.
 * EGOVERNMENTS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.egov.ptis.domain.entity.property;

import java.util.Date;

import org.egov.exceptions.EGOVRuntimeException;
import org.egov.infstr.models.BaseModel;

/**
 * <p>
 * This class defines Property Status i.e A Property has a Status indicating its
 * current state.
 * </p>
 * PropertyStatusValues can be Assessed, UnAssessed etc.
 * 
 * @author Gayathri Joshi
 * @version 2.00
 * @see
 * @see
 * @since 1.00
 */
public class PropertyStatusValues extends BaseModel {

	private BasicProperty basicProperty;
	private PropertyStatus propertyStatus;
	private Date referenceDate;
	private String referenceNo;
	private String remarks;
	private String isActive;
	private String extraField1;
	private String extraField2;
	private String extraField3;
	private BasicProperty referenceBasicProperty;
	private Integer buildingPermissionNo;
	private Date buildingPermissionDate;

	/**
	 * @return Returns if the given Object is equal to PropertyStatusValues
	 */

	public boolean equals(Object that) {
		if (that == null)
			return false;

		if (this == that)
			return true;

		if (that.getClass() != this.getClass())
			return false;
		final PropertyStatusValues thatPropStatus = (PropertyStatusValues) that;

		if (this.getId() != null && thatPropStatus.getId() != null) {
			if (getId().equals(thatPropStatus.getId())) {
				return true;
			} else
				return false;
		} else if (this.getBasicProperty() != null && thatPropStatus.getBasicProperty() != null) {
			if (getBasicProperty().equals(thatPropStatus.getBasicProperty())) {
				return true;
			} else
				return false;
		} else
			return false;
	}

	/**
	 * @return Returns the hashCode
	 */
	public int hashCode() {
		int hashCode = 0;
		if (this.getId() != null) {
			hashCode += this.getId().hashCode();
		} else if (this.getBasicProperty() != null) {
			hashCode += this.getBasicProperty().hashCode();
		}
		return hashCode;
	}

	/**
	 * @return Returns the boolean after validating the current object
	 */
	public boolean validatePropStatusValues() {
		if (getBasicProperty() == null)
			throw new EGOVRuntimeException(
					"In PropertyStatusValues Validate : 'ID_Property' Attribute is Not Set, Please Check !!");
		if (getPropertyStatus() == null)
			throw new EGOVRuntimeException(
					"In PropertyStatusValues Validate : 'ID_Status' Attribute is Not Set, Please Check !!");
		return true;
	}

	public BasicProperty getBasicProperty() {
		return basicProperty;
	}

	public void setBasicProperty(BasicProperty basicProperty) {
		this.basicProperty = basicProperty;
	}

	public PropertyStatus getPropertyStatus() {
		return propertyStatus;
	}

	public void setPropertyStatus(PropertyStatus propertyStatus) {
		this.propertyStatus = propertyStatus;
	}

	public Date getReferenceDate() {
		return referenceDate;
	}

	public void setReferenceDate(Date referenceDate) {
		this.referenceDate = referenceDate;
	}

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getExtraField1() {
		return extraField1;
	}

	public void setExtraField1(String extraField1) {
		this.extraField1 = extraField1;
	}

	public String getExtraField2() {
		return extraField2;
	}

	public void setExtraField2(String extraField2) {
		this.extraField2 = extraField2;
	}

	public String getExtraField3() {
		return extraField3;
	}

	public void setExtraField3(String extraField3) {
		this.extraField3 = extraField3;
	}

	public BasicProperty getReferenceBasicProperty() {
		return referenceBasicProperty;
	}

	public void setReferenceBasicProperty(BasicProperty referenceBasicProperty) {
		this.referenceBasicProperty = referenceBasicProperty;
	}
	
	public Integer getBuildingPermissionNo() {
		return buildingPermissionNo;
	}

	public void setBuildingPermissionNo(Integer buildingPermissionNo) {
		this.buildingPermissionNo = buildingPermissionNo;
	}

	public Date getBuildingPermissionDate() {
		return buildingPermissionDate;
	}

	public void setBuildingPermissionDate(Date buildingPermissionDate) {
		this.buildingPermissionDate = buildingPermissionDate;
	}

	@Override
	public String toString() {
		StringBuilder objStr = new StringBuilder();

		objStr.append("Id: ").append(getId()).append("|BasicProperty: ");
		objStr = (getBasicProperty() != null) ? objStr.append(getBasicProperty().getUpicNo()) : objStr.append("NULL");
		objStr.append("|PropertyStatus : ").append(null!=getPropertyStatus()?getPropertyStatus().getName():"").append("|RefDate: ").append(
				getReferenceDate()).append("|RefNo: ").append(getReferenceNo()).append("|Remarks: ").append(
				getRemarks()).append("|isActive: ").append(getIsActive()).append("|extraField1: ").append(
				getExtraField1()).append("|extraField2: ").append(getExtraField2()).append("|extraField3: ").append(
				getExtraField3()).append("|RefBasicProperty: ").append(getReferenceBasicProperty());

		return objStr.toString();

	}
}
