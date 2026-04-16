import React, { useState, type KeyboardEvent, useCallback } from "react";
import { MathfieldElement } from "mathlive";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faCheck,
  faArrowRightToBracket,
  faSquareRootVariable, // LaTeX symbol icon
  faX,
  faSpinner,
} from "@fortawesome/free-solid-svg-icons";
import "../css/AnswerInput.css";
import LatexDropdown from "./LatexInputDropdown";

export interface AnswerInputProps {
  value: string;
  onChange: (value: string) => void;
  onSubmit: () => void;
  placeholder?: string;
  disabled?: boolean;
  className?: string;
  "aria-label"?: string;
  onLaTeXMenuOpen?: () => void; // Callback for LaTeX menu
  evaluationStatus?: "idle" | "checking" | "correct" | "incorrect";
}

const AnswerInput: React.FC<AnswerInputProps> = ({
  value,
  onChange,
  onSubmit,
  placeholder = "Type your answer...",
  disabled = false,
  className = "",
  "aria-label": ariaLabel = "Math answer input",
  onLaTeXMenuOpen,
  evaluationStatus = "idle",
}) => {
  const [isFocused, setIsFocused] = useState(false);
  const [isModalOpen, setIsDropdownOpen] = useState(false);
  const mfe = new MathfieldElement();
  mfe.value = "\\pi/4"

  const handleLaTeXInsert = (latex: string) => {
    onChange(value + (value ? " " : "") + `\\(${latex}\\)`);
    setIsDropdownOpen(false);
  };

  const handleKeyDown = useCallback(
    (e: KeyboardEvent<HTMLInputElement>) => {
      if (e.key === "Enter" && !e.shiftKey) {
        e.preventDefault();
        if (value.trim() && !disabled) onSubmit();
      }
    },
    [value, disabled, onSubmit],
  );

  const hasValue = value.trim().length > 0;
  const isSubmitted =
    evaluationStatus === "correct" || evaluationStatus === "incorrect";

  const getButtonState = () => {
    if (evaluationStatus === "checking")
      return { className: "checking", icon: faSpinner, label: "Checking..." };
    if (evaluationStatus === "correct")
      return { className: "correct", icon: faCheck, label: "Checking..." };
    if (evaluationStatus === "incorrect")
      return { className: "incorrect", icon: faX, label: "Checking..." };
    if (evaluationStatus === "idle")
      return {
        className: "idle",
        icon: faArrowRightToBracket,
        label: "Checking...",
      };
  };

  return (
    <div className={`answer-input-wrapper ${className}`}>
      <div
        className={`answer-input-container ${isFocused ? "focused" : ""} ${hasValue ? "has-value" : ""}`}
      >
        {/* Input Field */}
        <input
          type="text"
          value={value}
          onChange={(e) => onChange(e.target.value)}
          onKeyDown={handleKeyDown}
          onFocus={() => setIsFocused(true)}
          onBlur={() => setIsFocused(false)}
          placeholder={placeholder}
          disabled={disabled}
          className="answer-input-field"
          aria-label={ariaLabel}
          autoComplete="off"
        />

        <button
          type="button"
          className="input-action-btn latex-btn"
          onClick={() => setIsDropdownOpen(true)}
          aria-label="Insert LaTeX symbol"
          title="LaTeX Symbols"
        >
          <FontAwesomeIcon icon={faSquareRootVariable} />
        </button>

        {/* Actions */}
        <div className="input-right-actions">
          {/* Submit Button */}
          <button
            type="button"
            onClick={onSubmit}
            disabled={disabled || !value.trim()}
            className={`submit-btn ${disabled || !value.trim() ? "disabled" : "active"}`}
            aria-label={hasValue ? "Submit answer" : "Answer complete"}
          >
            <FontAwesomeIcon
              className={`submit-icon`}
              icon={disabled || !value.trim() ? faArrowRightToBracket : faCheck}
            />
          </button>
        </div>

        {/* LaTeX Modal */}
        {isModalOpen && (
          <LatexDropdown
            onClose={() => setIsDropdownOpen(false)}
            onInsert={handleLaTeXInsert}
          />
        )}
      </div>
    </div>
  );
};

export default AnswerInput;
