import "../css/Tag.css"
import type { TagProps } from "../types/Tag";

const TagComponent: React.FC<TagProps> = ({
  name,
  difficulty,
}) => {
  return (
    <>
      <div className="tag-container">
        <div className="tag-name">{name}</div>
        <div>{difficulty}</div>
      </div>
    </>
  );
};

export default TagComponent;