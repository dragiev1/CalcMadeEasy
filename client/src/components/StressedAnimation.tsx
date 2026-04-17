import React from "react";
import stressedAnim from "../assets/Stress Icon.webm";
import stressedAnim2 from "../assets/Sleep Icon.webm";
import "../css/StressedAnimation.css";

interface StressedAnimationProps {
  className?: string;
  alt?: string;
  autoPlay?: boolean;
  loop?: boolean;
  muted?: boolean;
}

const StressAnimation: React.FC<StressedAnimationProps> = ({
  className = "",
  alt = "Stressed Animation",
  autoPlay = true,
  loop = true,
  muted = true,
}) => {
  return (
    <div className="animation-wrapper">
      <video 
        src={stressedAnim}
        className={`stressed-animation ${className}`}
        autoPlay={autoPlay}
        loop={loop}
        muted={muted}
        playsInline
        preload="auto"
        aria-label={alt}
        role="img"
      >
        <source src={stressedAnim} type="video/webm"/>
        {/* Fallback for older browsers */}
        <img src={stressedAnim.replace(".webm", ".png")} alt={alt} className="animation-fallback" />
        {/* Text Fallback */}
        <p className="sr-only">{alt}</p>
      </video>
      <div className="bar"></div>
      <video 
        src={stressedAnim2}
        className={`stressed-animation ${className}`}
        autoPlay={autoPlay}
        loop={loop}
        muted={muted}
        playsInline
        preload="auto"
        aria-label={alt}
        role="img"
      >
        <source src={stressedAnim} type="video/webm"/>
      </video>
    </div>
  )
}

export default StressAnimation;