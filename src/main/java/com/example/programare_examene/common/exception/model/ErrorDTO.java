package com.example.programare_examene.common.exception.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * A common error response
 */
@Builder
@AllArgsConstructor
@JsonTypeName("Error")
public class ErrorDTO {

	private Integer errorCode;

	private String errorMessage;

	/**
	 * Level of error
	 */
	public enum ErrorLevelEnum {
		INFO("info"),

		WARNING("warning"),

		ERROR("error"),

		CRITICAL("critical");

		private String value;

		ErrorLevelEnum(String value) {
			this.value = value;
		}

		@JsonValue
		public String getValue() {
			return value;
		}

		@Override
		public String toString() {
			return String.valueOf(value);
		}

		@JsonCreator
		public static ErrorLevelEnum fromValue(String value) {
			for (ErrorLevelEnum b : ErrorLevelEnum.values()) {
				if (b.value.equals(value)) {
					return b;
				}
			}
			throw new IllegalArgumentException("Unexpected value '" + value + "'");
		}
	}

	private ErrorLevelEnum errorLevel = ErrorLevelEnum.INFO;

	/**
	 * Type of error
	 */
	public enum ErrorTypeEnum {
		FUNCTIONAL("functional"),

		TECHNICAL("technical");

		private String value;

		ErrorTypeEnum(String value) {
			this.value = value;
		}

		@JsonValue
		public String getValue() {
			return value;
		}

		@Override
		public String toString() {
			return String.valueOf(value);
		}

		@JsonCreator
		public static ErrorTypeEnum fromValue(String value) {
			for (ErrorTypeEnum b : ErrorTypeEnum.values()) {
				if (b.value.equals(value)) {
					return b;
				}
			}
			throw new IllegalArgumentException("Unexpected value '" + value + "'");
		}
	}

	private ErrorTypeEnum errorType;

	private String documentationUrl;

	private String tips;

	public ErrorDTO errorCode(Integer errorCode) {
		this.errorCode = errorCode;
		return this;
	}

	/**
	 * Error Code
	 * @return errorCode
	 */

	@JsonProperty("errorCode")
	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public ErrorDTO errorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
		return this;
	}

	/**
	 * Message
	 * @return errorMessage
	 */

	@JsonProperty("errorMessage")
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public ErrorDTO errorLevel(ErrorLevelEnum errorLevel) {
		this.errorLevel = errorLevel;
		return this;
	}

	/**
	 * Level of error
	 * @return errorLevel
	 */

	@JsonProperty("errorLevel")
	public ErrorLevelEnum getErrorLevel() {
		return errorLevel;
	}

	public void setErrorLevel(ErrorLevelEnum errorLevel) {
		this.errorLevel = errorLevel;
	}

	public ErrorDTO errorType(ErrorTypeEnum errorType) {
		this.errorType = errorType;
		return this;
	}

	/**
	 * Type of error
	 * @return errorType
	 */

	@JsonProperty("errorType")
	public ErrorTypeEnum getErrorType() {
		return errorType;
	}

	public void setErrorType(ErrorTypeEnum errorType) {
		this.errorType = errorType;
	}

	public ErrorDTO documentationUrl(String documentationUrl) {
		this.documentationUrl = documentationUrl;
		return this;
	}

	/**
	 * Url of the documentation
	 * @return documentationUrl
	 */

	@JsonProperty("documentationUrl")
	public String getDocumentationUrl() {
		return documentationUrl;
	}

	public void setDocumentationUrl(String documentationUrl) {
		this.documentationUrl = documentationUrl;
	}

	public ErrorDTO tips(String tips) {
		this.tips = tips;
		return this;
	}

	/**
	 * Tips/help for addressing the error
	 * @return tips
	 */

	@JsonProperty("tips")
	public String getTips() {
		return tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		ErrorDTO error = (ErrorDTO) o;
		return Objects.equals(this.errorCode, error.errorCode) &&
				Objects.equals(this.errorMessage, error.errorMessage) &&
				Objects.equals(this.errorLevel, error.errorLevel) &&
				Objects.equals(this.errorType, error.errorType) &&
				Objects.equals(this.documentationUrl, error.documentationUrl) &&
				Objects.equals(this.tips, error.tips);
	}

	@Override
	public int hashCode() {
		return Objects.hash(errorCode, errorMessage, errorLevel, errorType, documentationUrl, tips);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class ErrorDTO {\n");
		sb.append("    errorCode: ").append(toIndentedString(errorCode)).append("\n");
		sb.append("    errorMessage: ").append(toIndentedString(errorMessage)).append("\n");
		sb.append("    errorLevel: ").append(toIndentedString(errorLevel)).append("\n");
		sb.append("    errorType: ").append(toIndentedString(errorType)).append("\n");
		sb.append("    documentationUrl: ").append(toIndentedString(documentationUrl)).append("\n");
		sb.append("    tips: ").append(toIndentedString(tips)).append("\n");
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


