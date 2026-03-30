import React, { useState, useEffect } from 'react';
import "../css/RadialGraph.css"


interface RadialGraphProps {
  value: number;
  maxValue?: number;
  size?: number;
  strokeWidth?: number;
  primaryColor?: string;
  secondaryColor?: string;
  textColor?: string;
  showLabel?: boolean;
  label?: string;
  animated?: boolean;
  className?: string;
}

const RadialGraph: React.FC<RadialGraphProps> = ({
  value,
  maxValue = 100,
  size = 150,
  strokeWidth = 15,
  primaryColor = 'var(--accent-primary)',
  secondaryColor = 'var(--txt-secondary)',
  textColor = 'var(--txt-primary)',
  showLabel = true,
  label = '%',
  animated = true,
  className = '',
}) => {
  const [displayValue, setDisplayValue] = useState<number>(0);

  const radius: number = (size - strokeWidth) / 2;
  const circumference: number = 2 * Math.PI * radius;
  const percentage: number = Math.min(Math.max(value, 0), maxValue) / maxValue;
  const strokeDashoffset: number = circumference - percentage * circumference;

  const centerX: number = size / 2;
  const centerY: number = size / 2;

  useEffect(() => {
    if (animated) {
      const timer = setTimeout(() => {
        setDisplayValue(value);
      }, 100);
      return () => clearTimeout(timer);
    } else {
      setDisplayValue(value);
    }
  }, [value, animated]);

  const currentPercentage: number = Math.min(
    Math.max(displayValue, 0),
    maxValue
  ) / maxValue;
  const currentStrokeDashoffset: number =
    circumference - currentPercentage * circumference;

  return (
    <div className={`relative inline-flex items-center justify-center ${className}`}>
      <svg
        width={size}
        height={size}
        className="transform -rotate-90"
        viewBox={`0 0 ${size} ${size}`}
      >
        {/* Background Circle */}
        <circle
          cx={centerX}
          cy={centerY}
          r={radius}
          fill="none"
          stroke={secondaryColor}
          strokeWidth={strokeWidth}
        />

        {/* Progress Circle */}
        <circle
          cx={centerX}
          cy={centerY}
          r={radius}
          fill="none"
          stroke={primaryColor}
          strokeWidth={strokeWidth}
          strokeDasharray={circumference}
          strokeDashoffset={currentStrokeDashoffset}
          strokeLinecap="round"
          className={animated ? 'transition-all duration-1000 ease-out' : ''}
        />
      </svg>

      {/* Center Label */}
      {showLabel && (
        <div
          className="radial-graph"
          style={{ color: textColor }}
        >
          <span className="text-3xl font-bold">
            {currentPercentage * 100}
          </span>
          <span className="text-sm opacity-70">{label}</span>
        </div>
      )}
    </div>
  );
};

export default RadialGraph;