/*
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.fhir2;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.openmrs.BaseOpenmrsData;

/**
 * FHIR Reference - https://www.hl7.org/fhir/references.html
 */
@Data(staticConstructor = "of")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "fhir_reference")
public class FhirReference extends BaseOpenmrsData {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "reference_id")
	private Integer id;

	@Column(name="type")
	private String type;

	@Column(name="element_id")
	private String reference;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "based_on_task_id")
	private FhirTask basedOnTask;

	@OneToOne(mappedBy = "forReference")
	private FhirTask forTask;

	@OneToOne(mappedBy = "encounterReference")
	private FhirTask encounterTask;

	@OneToOne(mappedBy = "ownerReference")
	private FhirTask ownerTask;

	@OneToOne(mappedBy = "valueReference")
	private FhirTaskInput taskParamValue;
}
