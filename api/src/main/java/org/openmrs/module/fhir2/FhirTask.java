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

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import java.util.Collection;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.openmrs.BaseOpenmrsData;

@Data(staticConstructor = "of")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "fhir_task")
public class FhirTask extends BaseOpenmrsData {
	
	// Based on https://www.hl7.org/fhir/task.html v4.0.1
	// TODO: Support this valueset: https://www.hl7.org/fhir/valueset-task-status.html
	public enum TaskStatus {
		REQUESTED,
		REJECTED,
		ACCEPTED,
		COMPLETED
	};
	
	// TODO: Support this valueset: https://www.hl7.org/fhir/valueset-task-intent.html
	public enum TaskIntent {
		ORDER
	}
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "task_id")
	private Integer id;
	
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private TaskStatus status;
	
	@Column(name = "status_reason")
	private String statusReason;
	
	@Column(name = "intent")
	@Enumerated(EnumType.STRING)
	private TaskIntent intent;

	/**
	 * BasedOn refers to a higher-level authorization that triggered the creation of the task. It references a "request" resource such as a ServiceRequest, MedicationRequest, ServiceRequest, CarePlan, etc. which is distinct from the "request" resource the task is seeking to fulfill. This latter resource is referenced by FocusOn. For example, based on a ServiceRequest (= BasedOn), a task is created to fulfill a procedureRequest ( = FocusOn ) to collect a specimen from a patient.
	 */
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "based_on_reference_id", referencedColumnName = "reference_id")
	private Collection<FhirReference> basedOnReferences;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "for_reference_id", referencedColumnName = "reference_id")
	private FhirReference forReference;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "encounter_reference_id", referencedColumnName = "reference_id")
	private FhirReference encounterReference;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "owner_reference_id", referencedColumnName = "reference_id")
	private FhirReference ownerReference;

	@OneToMany(cascade= CascadeType.ALL)
	@JoinColumn(name="task_id")
	private Collection<FhirTaskInput> input;

	@OneToMany(cascade= CascadeType.ALL)
	@JoinColumn(name="task_id")
	private Collection<FhirTaskOutput> output;
}
