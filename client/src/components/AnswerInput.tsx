// AnswerInput.tsx
import React, { useState, useEffect, useRef, useCallback } from "react";
import type { MathfieldElement } from "mathlive";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faCheck,
  faArrowRightToBracket,
  faX,
  faSpinner,
} from "@fortawesome/free-solid-svg-icons";
import "../css/AnswerInput.css";

// Import mathlive once to register the custom element
import "mathlive";

export interface AnswerInputProps {
  value: string;
  onChange: (value: string) => void;
  onSubmit: () => void;
  placeholder?: string;
  disabled?: boolean;
  className?: string;
  "aria-label"?: string;
  onLaTeXMenuOpen?: () => void;
  evaluationStatus?: "idle" | "checking" | "correct" | "incorrect";
}

const AnswerInput: React.FC<AnswerInputProps> = ({
  value,
  onChange,
  onSubmit,
  disabled = false,
  className = "",
  "aria-label": ariaLabel = "Math answer input",
  evaluationStatus = "idle",
}) => {
  const [isFocused, setIsFocused] = useState(false);

  // Ref to access the actual <math-field> DOM element
  const mfref = useRef<MathfieldElement>(null);

  // Sync external `value` prop to math-field (e.g., when reset after submit)
  useEffect(() => {
    const mf = mfref.current;
    if (mf && mf.value !== value) {
      mf.setValue(value, { silenceNotifications: true });
    }
  }, [value]);

  // Handle Enter key submission on the math-field
  const handleKeyDown = useCallback(
    (evt: KeyboardEvent) => {
      if (evt.key === "Enter" && !evt.shiftKey) {
        evt.preventDefault();
        if (value.trim() && !disabled) onSubmit();
      }
    },
    [value, disabled, onSubmit],
  );

  // Attach/detach native keydown listener to math-field
  useEffect(() => {
    const mf = mfref.current;
    if (!mf) return;

    mf.addEventListener("keydown", handleKeyDown as EventListener);
    return () => {
      mf.removeEventListener("keydown", handleKeyDown as EventListener);
    };
  }, [handleKeyDown]);

  const hasValue = value.trim().length > 0;

  const getButtonState = () => {
    if (evaluationStatus === "checking")
      return { className: "checking", icon: faSpinner, label: "Checking..." };
    if (evaluationStatus === "correct")
      return { className: "correct", icon: faCheck, label: "Correct!" };
    if (evaluationStatus === "incorrect")
      return { className: "incorrect", icon: faX, label: "Try again" };
    return {
      className: "idle",
      icon: faArrowRightToBracket,
      label: "Submit answer",
    };
  };

  const buttonState = getButtonState();

  return (
    <div className={`answer-input-wrapper ${className}`}>
      <div
        className={`answer-input-container ${isFocused ? "focused" : ""} ${hasValue ? "has-value" : ""} ${evaluationStatus}`}
      >
        {/* Math Field Input */}
        {disabled ? (
          // READ-ONLY DISPLAY MODE (After submission / disabled)
          <div
            className="answer-display-field"
            aria-label="Submitted answer"
            role="textbox"
            aria-readonly="true"
          >
            {value || "No answer submitted"}
          </div>
        ) : (
          <>
            <math-field
              ref={mfref}
              className="answer-input-field"
              onInput={(evt) => {
                const target = evt.target as MathfieldElement;
                onChange(target.value);
              }}
              onFocus={() => setIsFocused(true)}
              onBlur={() => setIsFocused(false)}
              aria-label={ariaLabel}
            >
              {value}
            </math-field>
            
            <div className="input-right-actions">
              <button
                type="button"
                onClick={onSubmit}
                disabled={
                  disabled || !value.trim() || evaluationStatus === "checking"
                }
                className={`submit-btn ${buttonState.className} ${disabled || !value.trim() ? "disabled" : "active"}`}
                aria-label={buttonState.label}
                title={buttonState.label}
              >
                <FontAwesomeIcon
                  className={`submit-icon ${evaluationStatus === "checking" ? "spinning" : ""}`}
                  icon={buttonState.icon}
                  spin={evaluationStatus === "checking"}
                />
              </button>
            </div>
          </>
        )}
      </div>
    </div>
  );
};

export default AnswerInput;
