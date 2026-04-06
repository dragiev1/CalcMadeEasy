import type { InlineLatexProps } from '../Latex/InlineLatex';
import { InlineLatex } from '../Latex/InlineLatex';
import { BlockLatex } from '../Latex/BlockLatex';

export interface LatexProps extends InlineLatexProps {
  displayMode?: boolean;
}

const Latex: React.FC<LatexProps> = ({ displayMode = false, ...props }) => {
  return displayMode 
    ? <BlockLatex {...props} /> 
    : <InlineLatex {...props} />;
};

export default Latex;