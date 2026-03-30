import "../css/QuestionCard.css";
import type { TagProps } from "../types/Tag";
import TagComponent from "../components/TagComponent";

interface ProblemCardProps {
  question: string;
  confidenceScore?: number;
  showQuestion?: boolean;
  solved: boolean;
  tags: TagProps[];
}

const ProblemCard: React.FC<ProblemCardProps> = ({
  question,
  confidenceScore,
  showQuestion = true,
  solved = false,
  tags = [], // Default to empty array.
}) => {
  {
    /* TODO: DELETE AFTER TESTING */
  }

  const mockTags: TagProps[] = [
    { name: "Algebra", difficulty: 0.4, id: 1 },
    { name: "Derivative", difficulty: 0.65, id: 2 },
    { name: "Integration By Parts", difficulty: 0.9, id: 3 },
  ];

  const displayTags = tags.length > 0 ? tags : mockTags;

  return (
    <>
      <div className="card-container">
        <div className="card-header">
          <div className="confidence-score">
            {/* Confidence Score */}
            {confidenceScore !== undefined && <span>{confidenceScore}%</span>}
          </div>
          <div className="question">
            {/* Question */}
            {showQuestion && <p>{question}</p>}
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
