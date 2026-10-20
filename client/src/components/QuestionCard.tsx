import "../css/QuestionCard.css";
import TagComponent from "../components/TagComponent";
import { type ProblemProps } from "../types/Problem";
import AnswerInput from "./AnswerInput";
import { useEffect, useState } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faTag } from "@fortawesome/free-solid-svg-icons";
import { verifyUserSolution } from "../api/problemService";

const ProblemCard: React.FC<ProblemProps> = ({
  id,
  description,
  position,
  showQuestion = true,
  solved = false,
  tags = [], // Default to empty array.
  userSolution,
}) => {
  const [userAnswer, setUserAnswer] = useState(
    solved ? userSolution : "",
  );

  const [isSubmitting, setIsSubmitting] = useState(false);
  const [showTags, setShowTags] = useState(true);

  const [status, setStatus] = useState<"idle" | "checking" | "correct" | "incorrect">("idle");

  useEffect(() => {
    if(userSolution !== undefined) {
      setUserAnswer(userSolution);
      if (solved) setStatus("correct");
    }
  }, [solved, userSolution]);

  const handleSubmit = async () => {
    if (!userAnswer?.trim() || isSubmitting) return;

    setIsSubmitting(true);
    setStatus("checking");
    
    try {
      // Call API to verify and fetch result:
      const result = await verifyUserSolution(id, userAnswer);
    
      if(result.isCorrect) setStatus("correct");
      else setStatus("incorrect");
    } catch (e) {
      console.error("Verification failed: ", e);
      setStatus("incorrect");
    } finally {
      setIsSubmitting(false);
    }
  };

  const toggleTags = () => setShowTags((prev) => !prev);

  // Determine visual status, prioritize live submission status, fallback is idle.
  const evaluationStatus = status
    ? status
    : (solved ? "correct" : "idle");

  return (
    <>
      <div className="card-container">
        <div className="card-header">
          <div className="question-wrapper">
            <span className="question-number">
              {position}.{")"}
            </span>
            <div className="question-text">
              {/* Question */}
              {showQuestion && description}
            </div>
          </div>
          <div
            className="tag-toggle-btn"
            onClick={toggleTags}
            aria-expanded={showTags}
            aria-label={showTags ? "Hide tags" : "Show tags"}
          >
            <FontAwesomeIcon
              icon={faTag}
              className={`tag-icon ${showTags ? "active" : ""}`}
            />
          </div>
        </div>

        <div className="card-tags">
          {showTags &&
            tags.map((tag: any, index: number) => (
              <TagComponent
                key={`${tag.name}-${index}`}
                name={tag.name}
                difficulty={tag.difficulty}
                id={tag.id}
              />
          ))}
        </div>

        <AnswerInput
          value={userAnswer || ""}
          onChange={setUserAnswer}
          onSubmit={handleSubmit}
          disabled={isSubmitting || status === "correct"}
          aria-label={`Answer for: ${description}`}
          className={evaluationStatus}
          evaluationStatus={evaluationStatus}
        />
      </div>
    </>
  );
};

export default ProblemCard;
