import "../css/Tag.css";
import type { TagProps } from "../types/Tag";

const TagComponent: React.FC<TagProps> = ({ name, difficulty = 0.0 }) => {
  // Difficulty between 0.0 - 1.0 to avoid out of bounds difficulity
  const lvl = Math.max(0.0, Math.min(1.0, difficulty));

  // Map difficulty to CSS variable
  const gradientPos = lvl * 100;

  return (
    <>
      <div
        style={{ backgroundPosition: `${gradientPos}% 0` }}
        className="tag-container"
        aria-label={`${name} - Difficulty ${(lvl * 100).toFixed(0)}%`}
      >
        <div className="tag-name">{name}</div>
        {/* Testing */}
        {/* <div>{difficulty}</div> */}
      </div>
    </>
  );
};

export default TagComponent;
