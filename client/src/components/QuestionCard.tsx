import "../css/QuestionCard.css";
import type { TagProps } from "../types/Tag";
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
  {
    /* TODO: DELETE AFTER TESTING */
  }

  const mockTags: TagProps[] = [
    { name: "Algebra", difficulty: 0.2, id: 1 },
    { name: "Derivative", difficulty: 0.65, id: 2 },
    { name: "Integration By Parts", difficulty: 0.9, id: 3 },
  ];

  const displayTags = tags.length > 0 ? tags : mockTags;

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
            <RadialGraph value={50} size={70} strokeWidth={5} secondaryColor={"black"}/>
            {/* {confidenceScore !== undefined && <span>{confidenceScore}</span>} */}
          </div>
        </div>
        <div className="card-tags">
          {displayTags.map((tag, index) => (
            <TagComponent
              key={tag.name + index}
              name={tag.name}
              difficulty={tag.difficulty}
              id={tag.id}
            />
          ))}
        </div>

        <div className="card-input">{/* User's solution */}</div>
      </div>
    </>
  );
};

export default ProblemCard;
