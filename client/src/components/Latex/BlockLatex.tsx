// components/Latex/InlineLatex.tsx
import { useEffect, useRef, memo } from 'react';
import katex from 'katex';
import 'katex/dist/katex.min.css';

export interface BlockLatexProps {
  children: string;
  className?: string;
  'aria-label'?: string;
}

export const BlockLatex: React.FC<BlockLatexProps> = memo(({ 
  children: latex, 
  className = '',
  'aria-label': ariaLabel,
}) => {
  const ref = useRef<HTMLDivElement>(null);

  useEffect(() => {
    if (ref.current) {
      try {
        katex.render(latex, ref.current, { 
          displayMode: true, 
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
      style={{ display: 'block', minHeight: '2em' }}
    />
  );
});
BlockLatex.displayName = 'BlockLatex';