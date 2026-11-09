import { faArrowDown } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import demo from "../assets/kawaii_pi.svg";
import derivativeDefinition from "../assets/math-animations/DerivativeDefinition.mp4";
import "../css/LearnMoreSection.css";

// import UserStats from "./UserStats";
import QuestionCard from "./ProblemCard";
import { useScrollReveal } from "../utils/useScrollReveal";
import { type Problem } from "../types/Problem";
import { getProblemById } from "../api/problemService";
import { useEffect, useState } from "react";
import ReviewCard from "./ReviewCard";

const LearnMoreSection = () => {
  const [sec1Ref, sec1Visible] =useScrollReveal();
  const [sec2Ref, sec2Visible] =useScrollReveal();
  const [sec3Ref, sec3Visible] =useScrollReveal();
  // const [sec4Ref, sec4Visible] =useScrollReveal();

  // Fake Reviews
  // TODO: Replace with real google reviews later!
  const fakeReviews = [
    {
      name: "Mr. Anonymous",
      rating: 5,
      text: "I always struggled with the intuition behind derivatives in college. The interactive animations on this site completely changed how I see calculus. It’s not just memorizing formulas anymore; I actually understand WHY things work. The homework problems are challenging but incredibly fair."
    },
    {
      name: "Igor Ivanov",
      rating: 4.5,
      text: "Very good."
    },
    {
      name: "Mrs. Professor",
      rating: 5,
      text: "As a professor, this website teaches math how I like to teach it. Simple, easy to follow explanations that shape the mind to think, rather than to guess. Beautiful work."
    },
    {
      name: "Sharon L.",
      rating: 5,
      text: "I used this to prep for my AP Calc BC exam and ended up getting an A! The lessons are actually so fun, which I never thought I'd say about math. The progress tracking keeps me motivated, and the tag hints on the homework saved me when I got stuck. Would love to see more advanced topics added soon!"
    },
  ];

  /**
   * Testing: Fetching an example problem and passing the info in the QuestionCard component.
   */
  const problemId = "123e4567-e89b-12d3-a456-426614174000";  
  const [problem, setProblem] = useState<Problem | null>(null);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const fetchProblem = async () => {
      try {
        setIsLoading(true);
        // Fetch problem.
        const data = await getProblemById(problemId);
        setProblem(data); 
      } catch (e) { 
        console.error("Failed to fetch problem! Using fallback data.", e);
        
        // FALLBACK: If the API fails, use this mock data so the UI doesn't break.
        setProblem({
          id: problemId,
          description: "Example Question?",
          points: 10,
          isChallenge: false,
          solution: "2x",
          solutionType: "NUMERICAL",
          tags: [
            { id: "1", name: "Example", difficulty: 0.1 },
            { id: "2", name: "Example", difficulty: 0.2 },
            { id: "3", name: "Example", difficulty: 0.5 },
            { id: "4", name: "Example", difficulty: 0.6 },
            { id: "5", name: "Example", difficulty: 0.9 },
            { id: "6", name: "Example", difficulty: 1.0 },
          ]
        });
      } finally {
        setIsLoading(false); 
      }
    };

    fetchProblem();
  }, [problemId]);

  // Keep the loading screen, but remove the null return so it falls back gracefully
  if (isLoading) return <p style={{ textAlign: 'center', padding: '2rem' }}>Loading problem...</p>; 
  if (!problem) return <p style={{ textAlign: 'center', padding: '2rem' }}>Demo could not be loaded</p>

  return (
    <div className="learn-more-wrapper">
      <div className="divider">
        <h3>
          <FontAwesomeIcon icon={faArrowDown} /> Learn More{" "}
          <FontAwesomeIcon icon={faArrowDown} />
        </h3>
      </div>
      <div className="learn-container">
        {/* Section 1 */}
        <div className={`section ${sec1Visible ? 'visible' : ''}`} ref={sec1Ref}>
          <div className="explanation">
            <div className="explanation-title">See Math; Be Comfortable With It</div>
            <div className="explanation-body">
              Stop memorizing formulas. Our interactive animations reveal the{" "}
              <i>why</i> behind "alien" concepts, transforming abstract symbols
              into intuitive understanding that sticks.
            </div>
          </div>
          <div className="demo">
            <video
            src={derivativeDefinition}
            autoPlay
            loop={true}
            muted
            playsInline
            className="lorenz-animation"
          >
            {" "}
            Your browser does not support the video tag.
          </video>
          </div>
        </div>

        {/* Section 2 */}
        <div className={`section ${sec2Visible ? 'visible' : ''}`} ref={sec2Ref}>
          <div className="demo">
            <img src={demo} alt="logo" className="mr-3"/>
          </div>
          <div className="explanation flex flex-col items-end">
            <div className="explanation-title text-right">Learn at the Speed of Understanding</div>

            <div className="explanation-body text-right">
              No rushing. No stress. Just clear, professional explanations that
              respect your intelligence. Master mathematics deeply at your own
              pace.
            </div>
          </div>
        </div>

        {/* Section 3 */}
        <div className={`section ${sec3Visible ? 'visible' : ''}`} ref={sec3Ref}>
          <div className="explanation">
            <div className="explanation-title">
              Problems Crafted by Educators, Not AI
            </div>

            <div className="explanation-body">
              Every lesson and problem is hand-designed with purposeful tags,
              calibrated difficulty, and intelligent verification. We
              learn your patterns, record, and display it.
            </div>
          </div>
          <div className="demo">
            <QuestionCard 
              id={problem.id}
              description={problem.description}
              tags={problem.tags || []}
              position={3} 
            />
          </div>
        </div>

        {/* Section 4
        <div className={`section ${sec4Visible ? 'visible' : ''}`} ref={sec4Ref}>
          <div className="demo">
            <UserStats />
          </div>
          <div className="explanation">
            <div className="explanation-title">
              We Measure Growth, Not Just Grades
            </div>

            <div className="explanation-body">
              See your readiness by topic. See improvements. Every exam is a
              checkpoint in your journey. It is not a judgment, but a map
              forward.
            </div>
          </div>
        </div> */}
      </div>

      <div className="divider-bottom"></div>

      {/* Reviews */}
      <div className="columns-1 md:columns-2 lg:columns-3 max-w-[75%] mt-20 mx-auto mb-20">
        {fakeReviews.map((r, i) => (
          <ReviewCard key={i} {...r} />
        ))}
      </div>

      <div className="divider-bottom"></div>

    </div>
  );
};

export default LearnMoreSection;
