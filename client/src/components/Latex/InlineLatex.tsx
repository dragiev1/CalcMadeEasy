// components/Latex/InlineLatex.tsx
import { useEffect, useRef, memo } from 'react';
import katex from 'katex';
import 'katex/dist/katex.min.css';

export interface InlineLatexProps {
  children: string;
  className?: string;
  'aria-label'?: string;
}

export const InlineLatex: React.FC<InlineLatexProps> = memo(({ 
  children: latex, 
  className = '',
  'aria-label': ariaLabel,
}) => {
  const ref = useRef<HTMLSpanElement>(null);

  useEffect(() => {
    if (ref.current) {
      try {
        katex.render(latex, ref.current, { 
          displayMode: false, 
          throwOnError: false,
          errorColor: '#cc0000'
        });
      } catch {
        if (ref.current) ref.current.textContent = latex;
      }
    }
  }, [latex]);

  return (
    <span 
      ref={ref} 
      className={`katex-container ${className}`.trim()}
      aria-label={ariaLabel}
      style={{ display: 'inline-block', minHeight: '1em' }}
    />
  );
});
InlineLatex.displayName = 'InlineLatex';