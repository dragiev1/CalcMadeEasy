import { useCallback, useEffect, useState } from "react";

export const useScrollReveal = () => {
  const [element, setElement] = useState<HTMLElement | null>(null);
  const [isVisible, setIsVisible] = useState(false);
  const ref = useCallback((node: HTMLElement | null) => {
    setElement(node);
  }, []);

  useEffect(() => {
    // Add a tiny delay to bypass React Strict Mode timing bugs
    const timer = setTimeout(() => {      
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
  }, [element]);

  return [ref, isVisible] as const;
};