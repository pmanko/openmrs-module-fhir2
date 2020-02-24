package org.openmrs.module.fhir2;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.openmrs.BaseOpenmrsData;
import org.openmrs.Concept;

@Data(staticConstructor = "of")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
public abstract class FhirTaskParam extends BaseOpenmrsData {

	@OneToOne
	@JoinColumn(name="task_id")
	protected FhirTask task;

	@Column(name="type")
	protected Concept type;

	@Column(name="value_datetime")
	protected Date valueDatetime;

	@Column(name="value_numeric")
	protected Double valueNumeric;

	@Column(name="value_text")
	protected String valueText;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="value_reference_id")
	protected FhirReference valueReference;
}
