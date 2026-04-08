import "../css/QuestionCard.css";
import TagComponent from "../components/TagComponent";
import type { ProblemProps } from "../types/Problem";
import RadialGraph from "./RadialGraph";

const ProblemCard: React.FC<ProblemProps> = ({
  question,
  confidenceScore = 95,
  showQuestion = true,
  solved = false,
  tags = [], // Default to empty array.
}) => {

  return (
    <>
      <div className="card-container">
        <div className="card-header">
          <div className="question">
            {/* Question */}
            {showQuestion && question}
          </div>
          <div className="">
            {/* Confidence Score */}
            <RadialGraph value={confidenceScore} size={50} strokeWidth={5} secondaryColor={`var(--txt-secondary)`}/>
            {/* {confidenceScore !== undefined && <span>{confidenceScore}</span>} */}
          </div>
        </div>
        <div className="card-tags">
          {tags.map((tag, index) => (
            <TagComponent
              key={tag.name + index}
              name={tag.name}
              difficulty={tag.difficulty}
              id={tag.id}
            />
          ))}
        </div>

        <div className="card-input">

        </div>
      </div>
    </>
  );
};

export default ProblemCard;
