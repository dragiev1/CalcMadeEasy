import { useState } from "react";
import "../css/LatexInputDropdown.css";

interface LatexDropdownProps {
  onInsert: (latex: string) => void;
  onClose: () => void;
}

const LatexDropdown: React.FC<LatexDropdownProps> = ({ onInsert, onClose }) => {
  const [latexInput, setLatexInput] = useState('');

  const latexSnippets = [
    { label: "½", value: "\\frac{1}{2}", title: "Fraction" },
    { label: "√", value: "\\sqrt{}", title: "Square Root" },
    { label: "x²", value: "^{2}", title: "Exponent" },
    { label: "x₁", value: "_{1}", title: "Subscript" },
    { label: "α", value: "\\alpha", title: "Alpha" },
    { label: "π", value: "\\pi", title: "Pi" },
    { label: "∑", value: "\\sum", title: "Sum" },
    { label: "∫", value: "\\int", title: "Integral" },
    { label: "≠", value: "\\neq", title: "Not Equal" },
    { label: "±", value: "\\pm", title: "Plus-Minus" },
  ];

  const handleInsert = () => {
    if (latexInput.trim()) {
      onInsert(latexInput);
      setLatexInput('');
    }
    onClose();
  };

  const handleSnippetClick = (value: string) => {
    setLatexInput(prev => prev + value + " ");
    handleInsert();
  };

  return (
    <div className="latex-dropdown-panel">
      {/* Quick Insert Grid */}
      <div className="snippet-grid">
        {latexSnippets.map((snippet, idx) => (
          <button
            key={idx}
            className="snippet-chip"
            onClick={() => handleSnippetClick(snippet.value)}
            title={snippet.title}
            type="button"
          >
            {snippet.label}
          </button>
        ))}
      </div>

      {/* Input Row
      <div className="dropdown-input-row">
        <input
          type="text"
          className="latex-inline-input"
          value={latexInput}
          onChange={(e) => setLatexInput(e.target.value)}
          onKeyDown={handleKeyDown}
          placeholder="Type LaTeX... (Ctrl/Cmd + Enter to insert)"
          autoFocus
        />
        <button 
          className="btn-insert-small" 
          onClick={handleInsert}
          disabled={!latexInput.trim()}
        >
          Insert
        </button>
      </div> */}
    </div>
  );
};

export default LatexDropdown;