import { useEffect, useRef, useState } from "react";

export const useScrollReveal = () => {
  const ref = useRef<HTMLDivElement>(null);
  const [isVisible, setIsVisible] = useState(false);

  useEffect(() => {
    // 1. Add a tiny delay to bypass React Strict Mode timing bugs
    const timer = setTimeout(() => {
      const element = ref.current;
      
      if (!element) {
        console.warn("⚠️ useScrollReveal: No element found for ref!");
        return;
      }
      
      const observer = new IntersectionObserver(
        (entries) => {
          entries.forEach((entry) => {
            if (entry.isIntersecting) {
              setIsVisible(true);
              observer.unobserve(entry.target);
            }
          });
        },
        {
          threshold: 0.1,
          rootMargin: "0px 0px -50px 0px"
        }
      );

      observer.observe(element);

      // Cleanup function for the observer
      return () => {
        if (element) observer.unobserve(element);
      };
    }, 100); // 100ms delay

    // Cleanup function for the timer
    return () => clearTimeout(timer);
  }, []);

  return [ref, isVisible] as const;
};