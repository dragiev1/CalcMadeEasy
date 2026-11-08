import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCirclePlus } from "@fortawesome/free-solid-svg-icons"; // <-- Add faPlus here
import "../css/Tag.css";
import type { TagProps } from "../types/Tag";

const TagComponent: React.FC<TagProps> = ({ name, difficulty = 0.0 }) => {
  // Clamp difficulty strictly between 0.0 and 1.0
  const lvl = Math.max(0.0, Math.min(1.0, difficulty));

  // Calculate gradient position (0% to 100%)
  const gradientPos = lvl * 100;

  return (
    <div
      className="tag-container group"
      style={{ backgroundPosition: `${gradientPos}% 0` }}
      aria-label={`${name} - Difficulty ${(lvl * 100).toFixed(0)}%`}
    >
      <span className="tag-name"><FontAwesomeIcon icon={faCirclePlus} className="plus-icon"/>{name}</span>
    </div>
  );
};

export default TagComponent;