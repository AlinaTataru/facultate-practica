package com.example.programare_examene.common.exception.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.util.ArrayList;
import java.util.List;





import javax.validation.Valid;

/**
 * ErrorsDTO
 */
@lombok.Builder @lombok.AllArgsConstructor

@JsonTypeName("Errors")
public class ErrorsDTO {

	@Valid
	private List<@Valid ErrorDTO> errors;

	public ErrorsDTO errors(List<@Valid ErrorDTO> errors) {
		this.errors = errors;
		return this;
	}

	public ErrorsDTO addErrorsItem(ErrorDTO errorsItem) {
		if (this.errors == null) {
			this.errors = new ArrayList<>();
		}
		this.errors.add(errorsItem);
		return this;
	}

	/**
	 * Get errors
	 * @return errors
	 */
	@Valid
	@JsonProperty("errors")
	public List<@Valid ErrorDTO> getErrors() {
		return errors;
	}

	public void setErrors(List<@Valid ErrorDTO> errors) {
		this.errors = errors;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		ErrorsDTO errors = (ErrorsDTO) o;
		return Objects.equals(this.errors, errors.errors);
	}

	@Override
	public int hashCode() {
		return Objects.hash(errors);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class ErrorsDTO {\n");
		sb.append("    errors: ").append(toIndentedString(errors)).append("\n");
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Convert the given object to string with each line indented by 4 spaces
	 * (except the first line).
	 */
	private String toIndentedString(Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}
}


