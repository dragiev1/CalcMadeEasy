import Navbar from "../components/Navbar";
import "../css/Home.css";
import {useState} from "react";

import gaussianIntegral from "../assets/math-animations/GaussianIntegral.mp4";
import riemannSums from "../assets/math-animations/RiemannSums.mp4";
import lorenzAttractor from "../assets/math-animations/LorenzAttractorWithEquations.mp4";

const mathAnimations = [
    gaussianIntegral,
    lorenzAttractor,
    riemannSums,
  ]

function Home() {
  const [currentVideoIndex, setCurrentVideoIndex] = useState(0);

  const handleVideoEnd = () => {
    setCurrentVideoIndex((prev) => (prev + 1) % mathAnimations.length);
  };

  return ( 
    <>
      <Navbar />

      <div className="hero-container">
        <div className="full-logo-container">
          <img src={'src/assets/full_logo.svg'} className="full-logo" alt="CalcMadeEasy Logo" ></img>
          <div className="subtitle">Where math clicks. </div>
        </div>
        
        <div className="animation-player">
          <video 
            key={currentVideoIndex}
            src={mathAnimations[currentVideoIndex]}
            autoPlay
            muted
            playsInline
            className="lorenz-animation" 
            onEnded={handleVideoEnd}
          > Your browser does not support the video tag.
          </video>
        </div>
      </div>


      <div className="divider" />
      
    </>
  );
};

export default Home;
