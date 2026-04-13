import React, { useState } from "react";
import { EditableMathField, addStyles } from "react-mathquill";

addStyles(); 

interface LatexInputModalProps {
  onClose: () => void;
  onInsert: (latex: string) => void;
}

const LatexInputModal: React.FC<LatexInputModalProps> = ({ onClose, onInsert }) => {
  const [latex, setLatex] = useState("");

  const handleInsert = () => {
    if (latex.trim()) {
      onInsert(latex);
    }
  };

  return (
    <div className="latex-modal-overlay" onClick={onClose}>
      <div className="latex-modal" onClick={(e) => e.stopPropagation()}>
        <div className="latex-modal-header">
          <span>Insert LaTeX</span>
          <button className="latex-modal-close" onClick={onClose}>✕</button>
        </div>
        <div className="latex-modal-body">
          <EditableMathField
            latex={latex}
            onChange={(mathField) => setLatex(mathField.latex())}
            className="latex-mathquill-field"
          />
          <p className="latex-modal-preview-label">LaTeX output: <code>{latex}</code></p>
        </div>
        <div className="latex-modal-footer">
          <button className="latex-modal-cancel" onClick={onClose}>Cancel</button>
          <button className="latex-modal-insert" onClick={handleInsert} disabled={!latex.trim()}>
            Insert
          </button>
        </div>
      </div>
    </div>
  );
};

export default LatexInputModal;