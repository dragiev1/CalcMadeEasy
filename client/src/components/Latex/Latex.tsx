import type { InlineLatexProps } from '../Latex/InlineLatex';
import { InlineLatex } from '../Latex/InlineLatex';
import { BlockLatex } from '../Latex/BlockLatex';

export interface LatexProps extends InlineLatexProps {
  displayMode?: boolean;
}

/**
 * For displaying latex that is not to do with the MathField element.
 * ( Ex: Displaying user's last incorrect attempt )
 * @param displayMode: whether it is inline latex or block.  
 * @returns Latex element
 */
const Latex: React.FC<LatexProps> = ({ displayMode = false, ...props }) => {
  return displayMode 
    ? <BlockLatex {...props} /> 
    : <InlineLatex {...props} />;
};

export default Latex;