import React, { useState, useEffect, useCallback } from 'react';
import '../css/RadialGraph.css';

export interface RadialGraphProps {
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
  'aria-label'?: string;
}

const RadialGraph: React.FC<RadialGraphProps> = ({
  value,
  maxValue = 100,
  size = 60,
  strokeWidth = 6,
  primaryColor = 'var(--accent-primary, #3b82f6)',
  secondaryColor = 'var(--txt-secondary, rgb(60, 60, 60))',
  textColor = 'var(--txt-primary, #1e293b)',
  showLabel = true,
  label = '',
  animated = true,
  className = '',
  'aria-label': ariaLabel,
}) => {
  const [displayValue, setDisplayValue] = useState<number>(0);
  
  const radius = (size - strokeWidth) / 2;
  const circumference = 2 * Math.PI * radius;

  const clampedPercentage = Math.min(Math.max(value, 0), maxValue) / maxValue;
  const targetOffset = circumference - clampedPercentage * circumference;

  useEffect(() => {
    if (animated) {
      const timer = setTimeout(() => setDisplayValue(value), 50);
      return () => clearTimeout(timer);
    }
    setDisplayValue(value);
  }, [value, animated]);

  const currentPercentage = Math.min(Math.max(displayValue, 0), maxValue) / maxValue;
  const currentOffset = circumference - currentPercentage * circumference;

  return (
    <div 
      className={`radial-graph ${className}`} 
      style={{ width: size, height: size }}
      role="progressbar"
      aria-valuenow={Math.round(currentPercentage * 100)}
      aria-valuemin={0}
      aria-valuemax={100}
      aria-label={ariaLabel ?? `Confidence: ${Math.round(currentPercentage * 100)}%`}
    >
      <svg
        width={size}
        height={size}
        className="radial-graph-svg"
        viewBox={`0 0 ${size} ${size}`}
      >
        <circle
          cx={size / 2}
          cy={size / 2}
          r={radius}
          fill="none"
          stroke={secondaryColor}
          strokeWidth={strokeWidth}
        />
        <circle
          cx={size / 2}
          cy={size / 2}
          r={radius}
          fill="none"
          stroke={primaryColor}
          strokeWidth={strokeWidth}
          strokeDasharray={circumference}
          strokeDashoffset={currentOffset}
          strokeLinecap="round"
          className="radial-graph-progress"
        />
      </svg>

      {showLabel && (
        <div className="radial-graph-label" style={{ color: textColor, fontSize: size * 0.3 }}>
          <span>{Math.round(currentPercentage * 100)}</span>
          <span className="radial-graph-suffix">{label}</span>
        </div>
      )}
    </div>
  );
};

export default RadialGraph;