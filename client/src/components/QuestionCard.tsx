import "../css/QuestionCard.css";
import TagComponent from "../components/TagComponent";
import type { ProblemProps } from "../types/Problem";
import RadialGraph from "./RadialGraph";
import AnswerInput from "./AnswerInput";
import { useState } from "react";

const ProblemCard: React.FC<ProblemProps> = ({
  question,
  position,
  confidenceScore = 95,
  showQuestion = true,
  solved = false,
  tags = [], // Default to empty array.
}) => {

  const [userAnswer, setUserAnswer] = useState('');
  const [isSubmitting, setIsSubmitting] = useState(false);

  const handleSubmit = () => {
    if(!userAnswer.trim() || isSubmitting) return;
    setIsSubmitting(true);
    console.log('Checking answer:' , userAnswer);  
    // TODO: delete line after testing.
    setTimeout(() => { 
      setIsSubmitting(false);
      setUserAnswer('');
    }, 800);
  };

  return (
    <>
      <div className="card-container">
        <div className="card-header">
          <div className="question-wrapper">
            <span className="question-number">{position}.{")"}</span>
            <div className="question-text">
              {/* Question */}
              {showQuestion && question}
            </div>
          </div>
          <div className="">
            {/* Confidence Score */}
            <RadialGraph value={confidenceScore} className="confidence-score" size={50} strokeWidth={5} secondaryColor={"gray"}/>
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

        <AnswerInput 
          value={userAnswer} 
          onChange={setUserAnswer}
          onSubmit={handleSubmit}
          disabled={isSubmitting || solved}
          aria-label={`Answer for: ${question}`} 
        />
      </div>
    </>
  );
};

export default ProblemCard;
